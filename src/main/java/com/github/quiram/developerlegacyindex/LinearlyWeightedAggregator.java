package com.github.quiram.developerlegacyindex;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

class LinearlyWeightedAggregator extends Aggregator {
    LinearlyWeightedAggregator() {
        super(date -> DAYS.between(date, LocalDate.now()), "linear weight");
    }
}
