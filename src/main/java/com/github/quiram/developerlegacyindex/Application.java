package com.github.quiram.developerlegacyindex;

import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

class Application {
    static public void main(String[] args) {
        final Options options = new ArgsProcessor().process(args);
        final RepositoryAccess repositoryAccess = new RepositoryAccess(options.getRepositoryPath());
        final List<Pair<String, LocalDate>> contributions = repositoryAccess.getFiles()
                .stream()
                .map(repositoryAccess::getContributions)
                .flatMap(List::stream)
                .collect(toList());

        System.out.println(options.getAggregator().aggregate(contributions));
    }
}
