package com.github.quiram.developerlegacyindex;

import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingLong;

class LinearlyWeightedAggregator extends Aggregator {
    @Override
    protected Map<String, Long> aggregateContributions(List<Pair<String, LocalDate>> contributions) {
        return contributions.stream()
                .map(pair -> Pair.of(pair.getKey(), ageOf(pair.getValue())))
                .collect(groupingBy(Pair::getKey, summingLong(Pair::getValue)));
    }

    private long ageOf(LocalDate oldestDate) {
        return DAYS.between(oldestDate, LocalDate.now());
    }
}
