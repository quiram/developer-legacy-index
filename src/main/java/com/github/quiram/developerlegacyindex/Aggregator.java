package com.github.quiram.developerlegacyindex;

import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToLongFunction;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.*;

abstract class Aggregator {
    private final Function<LocalDate, Long> ageFactor;

    Aggregator(Function<LocalDate, Long> ageFactor) {
        this.ageFactor = ageFactor;
    }

    final List<Pair<String, Long>> aggregate(List<Pair<String, LocalDate>> contributions) {
        return contributions.stream()
                .map(pair -> Pair.of(pair.getKey(), ageFactor.apply(pair.getValue())))
                .collect(groupingBy(Pair::getKey, summingLong(Pair::getValue))).entrySet().stream()
                .map(entry -> Pair.of(entry.getKey(), entry.getValue()))
                .sorted(comparingLong((ToLongFunction<Pair<String, Long>>) Pair::getValue).reversed())
                .collect(toList());
    }
}
