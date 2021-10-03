package com.github.quiram.developerlegacyindex;

import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

class NonWeightedAggregator extends Aggregator {
    @Override
    protected Map<String, Long> aggregateContributions(List<Pair<String, LocalDate>> contributions) {
        return contributions.stream()
                .map(Pair::getKey)
                .collect(groupingBy(identity(), counting()));
    }
}
