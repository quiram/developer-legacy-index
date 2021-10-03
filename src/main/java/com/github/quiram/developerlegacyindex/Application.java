package com.github.quiram.developerlegacyindex;

import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

class Application {
    static public void main(String[] args) {
        final Options options = new ArgsProcessor().process(args);
        final String repositoryPath = options.getRepositoryPath();
        final RepositoryAccess repositoryAccess = new RepositoryAccess(repositoryPath);
        final Aggregator aggregator = options.getAggregator();
        System.out.printf("Calculating%s long-term contributions in %s applying %s%n", options.isNormalisedResult() ? " normalised" : "", repositoryPath, aggregator.getName());
        final List<Pair<String, LocalDate>> contributions = repositoryAccess.getFiles()
                .stream()
                .map(repositoryAccess::getContributions)
                .flatMap(List::stream)
                .collect(toList());
        final List<Pair<String, Long>> legacyIndex = aggregator.aggregate(contributions);
        if (options.isNormalisedResult() && !legacyIndex.isEmpty()) {
            long highestScore = legacyIndex.get(0).getValue();
            legacyIndex.stream()
                    .map(pair1 -> Pair.of(pair1.getKey(), ((double) pair1.getValue()) / highestScore))
                    .forEach(pair -> System.out.printf("%s,%.4f%n", pair.getKey(), pair.getValue()));
        } else {
            legacyIndex.forEach(pair -> System.out.printf("%s,%s%n", pair.getKey(), pair.getValue()));
        }
    }
}
