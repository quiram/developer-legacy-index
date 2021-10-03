package com.github.quiram.developerlegacyindex;

class LinearlyWeightedAggregator extends Aggregator {
    LinearlyWeightedAggregator() {
        super(Aggregator::age, "linear weight");
    }
}
