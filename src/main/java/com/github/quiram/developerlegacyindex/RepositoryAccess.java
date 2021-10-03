package com.github.quiram.developerlegacyindex;

import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.blame.BlameResult;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class RepositoryAccess {
    private final Git git;
    private final Repository repository;

    @SneakyThrows
    RepositoryAccess(String projectFolder) {
        repository = FileRepositoryBuilder.create(new File(projectFolder + "/.git"));
        git = new Git(repository);
    }

    @SneakyThrows
    Set<String> getFiles() {
        // from https://stackoverflow.com/questions/19941597/use-jgit-treewalk-to-list-files-and-folders
        Ref head = repository.getRefDatabase().findRef("HEAD");

        // a RevWalk allows walking over commits based on some filtering that is defined
        RevWalk walk = new RevWalk(repository);

        RevCommit commit = walk.parseCommit(head.getObjectId());
        RevTree tree = commit.getTree();

        // now use a TreeWalk to iterate over all files in the Tree recursively
        // you can set Filters to narrow down the results if needed
        TreeWalk treeWalk = new TreeWalk(repository);
        treeWalk.addTree(tree);
        treeWalk.setRecursive(false);
        final HashSet<String> result = new HashSet<>();
        while (treeWalk.next()) {
            if (treeWalk.isSubtree()) {
                treeWalk.enterSubtree();
            } else {
                result.add(treeWalk.getPathString());
            }
        }

        return result;
    }

    @SneakyThrows
    List<Pair<String, LocalDate>> getContributions(String filePath) {
        final BlameResult blameResult = git.blame()
                .setFilePath(filePath)
                .call();
        int index = 0;
        final ArrayList<Pair<String, LocalDate>> result = new ArrayList<>();
        while (true) {
            try {
                final PersonIdent sourceAuthor = blameResult.getSourceAuthor(index++);
                result.add(Pair.of(sourceAuthor.getEmailAddress().toLowerCase(), sourceAuthor.getWhen().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
            } catch (ArrayIndexOutOfBoundsException e) {
                break;
            }
        }
        return result;
    }
}
