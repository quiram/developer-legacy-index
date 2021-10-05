package com.github.quiram.developerlegacyindex;

import org.junit.jupiter.api.Test;

import static com.github.quiram.utils.Random.randomString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class ArgsProcessorTest {
    private final ArgsProcessor argsProcessor = new ArgsProcessor();

    @Test
    void failIfNoArguments() {
        final Exception exception = assertThrows(Exception.class, () -> argsProcessor.process(new String[0]));
        assertThat(exception.getMessage(), containsString("repositoryPath"));
    }

    @Test
    void provideTheRepositoryPathIfProvided() {
        final String repositoryPath = randomString();
        final Options options = argsProcessor.process(new String[]{repositoryPath});
        assertThat(options.getRepositoryPath(), is(repositoryPath));
    }

    @Test
    void useNonWeightedAggregatorByDefault() {
        final Options options = argsProcessor.process(new String[]{randomString()});
        assertThat(options.getAggregator(), is(instanceOf(NonWeightedAggregator.class)));
    }

    @Test
    void useLinearlyWeightedAggregatorIfOptionSpecified() {
        final Options options = argsProcessor.process(new String[]{randomString(), "--linear-weight"});
        assertThat(options.getAggregator(), is(instanceOf(LinearlyWeightedAggregator.class)));
    }

    @Test
    void useExponentialWeightAggregatorIfOptionSpecified() {
        final Options options = argsProcessor.process(new String[]{randomString(), "--exponential-weight"});
        assertThat(options.getAggregator(), is(instanceOf(ExponentiallyWeightedAggregator.class)));
    }

    @Test
    void supportSpecifyingNonWeightedAggregatorExplicitly() {
        final String repositoryPath = randomString();
        final Options options = argsProcessor.process(new String[]{repositoryPath, "--no-weight"});
        assertThat(options.getAggregator(), is(instanceOf(NonWeightedAggregator.class)));
        assertThat(options.getRepositoryPath(), is(repositoryPath));
    }

    @Test
    void failIfUnrecognisedOption() {
        final Exception exception = assertThrows(Exception.class, () -> argsProcessor.process(new String[]{randomString(), "--not-a-real-option"}));
        assertThat(exception.getMessage(), containsString("unknown"));
        assertThat(exception.getMessage(), containsString("--not-a-real-option"));
    }

    @Test
    void doNotNormaliseResultAsDefault() {
        final Options options = argsProcessor.process(new String[]{randomString()});
        assertFalse(options.isNormalisedResult());
    }

    @Test
    void normaliseResultIfRequested() {
        final Options options = argsProcessor.process(new String[]{randomString(), "--normalise-result"});
        assertTrue(options.isNormalisedResult());
    }

    @Test
    void groupByEmailByDefault() {
        final Options options = argsProcessor.process(new String[]{randomString()});
        assertFalse(options.groupByName());
    }

    @Test
    void groupByNameIfRequired() {
        final Options options = argsProcessor.process(new String[]{randomString(), "--group-by-name"});
        assertTrue(options.groupByName());
    }
}