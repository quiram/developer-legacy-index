package com.github.quiram.developerlegacyindex;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

@Value
@Builder
class Options {
    @NonNull Aggregator aggregator;
    @NonNull String repositoryPath;
    @Builder.Default @Accessors(fluent = true) boolean normaliseResult = false;
    @Builder.Default @Accessors(fluent = true) boolean groupByName = false;
}
