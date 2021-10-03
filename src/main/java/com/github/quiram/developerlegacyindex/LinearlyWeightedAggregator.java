package com.github.quiram.developerlegacyindex;

import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.List;
import java.util.function.ToLongFunction;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.*;

class LinearlyWeightedAggregator implements Aggregator {
    @Override
    public List<Pair<String, Long>> aggregate(List<Pair<String, LocalDate>> contributions) {
        return contributions.stream()
                .map(pair -> Pair.of(pair.getKey(), ageOf(pair.getValue())))
                .collect(groupingBy(Pair::getKey, summingLong(Pair::getValue)))
                .entrySet().stream()
                .map(entry -> Pair.of(entry.getKey(), entry.getValue()))
                .sorted(comparingLong((ToLongFunction<Pair<String, Long>>) Pair::getValue).reversed())
                .collect(toList());
    }

    private long ageOf(LocalDate oldestDate) {
        return DAYS.between(oldestDate, LocalDate.now());
    }
}
