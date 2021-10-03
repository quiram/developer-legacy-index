package com.github.quiram.developerlegacyindex;

import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.List;
import java.util.function.ToLongFunction;

import static java.util.Comparator.comparingLong;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

class NonWeightedAggregator implements Aggregator {
    @Override
    public List<Pair<String, Long>> aggregate(List<Pair<String, LocalDate>> contributions) {
        return contributions.stream()
                .map(Pair::getKey)
                .collect(groupingBy(identity(), counting()))
                .entrySet().stream()
                .map(entry -> Pair.of(entry.getKey(), entry.getValue()))
                .sorted(comparingLong((ToLongFunction<Pair<String, Long>>) Pair::getValue).reversed())
                .collect(toList());
    }
}