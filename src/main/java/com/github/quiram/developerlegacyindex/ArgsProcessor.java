package com.github.quiram.developerlegacyindex;

class ArgsProcessor {
    Options process(String[] args) {
        final Options.OptionsBuilder optionsBuilder = Options.builder();
        optionsBuilder.aggregator(new NonWeightedAggregator());

        for (String arg : args) {
            switch (arg) {
                case "--no-weight" -> optionsBuilder.aggregator(new NonWeightedAggregator());
                case "--linear-weight" -> optionsBuilder.aggregator(new LinearlyWeightedAggregator());
                case "--exponential-weight" -> optionsBuilder.aggregator(new ExponentiallyWeightedAggregator());
                case "--normalise-result" -> optionsBuilder.normalisedResult(true);
                default -> {
                    if (arg.startsWith("--")) {
                        throw new RuntimeException("unknown option: " + arg);
                    } else {
                        optionsBuilder.repositoryPath(arg);
                    }
                }
            }
        }

        return optionsBuilder.build();
    }
}
