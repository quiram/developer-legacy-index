package com.github.quiram.developerlegacyindex;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
class Options {
    @NonNull Aggregator aggregator;
    @NonNull String repositoryPath;
    @Builder.Default
    boolean normalisedResult = false;
}
