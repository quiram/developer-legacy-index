package com.github.quiram.developerlegacyindex;

import static java.lang.Math.pow;
import static java.lang.Math.round;

class ExponentiallyWeightedAggregator extends Aggregator {
    ExponentiallyWeightedAggregator() {
        super(date -> round(pow(age(date), 1.1)), "exponential weight");
    }
}
