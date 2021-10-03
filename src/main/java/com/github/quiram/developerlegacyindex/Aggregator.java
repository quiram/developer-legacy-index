package com.github.quiram.developerlegacyindex;

import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.ToLongFunction;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.toList;

abstract class Aggregator {
    final List<Pair<String, Long>> aggregate(List<Pair<String, LocalDate>> contributions) {
        return aggregateContributions(contributions).entrySet().stream()
                .map(entry -> Pair.of(entry.getKey(), entry.getValue()))
                .sorted(comparingLong((ToLongFunction<Pair<String, Long>>) Pair::getValue).reversed())
                .collect(toList());
    }

    protected abstract Map<String, Long> aggregateContributions(List<Pair<String, LocalDate>> contributions);
}
