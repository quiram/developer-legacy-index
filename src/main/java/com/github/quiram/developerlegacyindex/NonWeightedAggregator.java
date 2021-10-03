package com.github.quiram.developerlegacyindex;

class NonWeightedAggregator extends Aggregator {
    NonWeightedAggregator() {
        super($ -> 1L, "no weight");
    }
}
