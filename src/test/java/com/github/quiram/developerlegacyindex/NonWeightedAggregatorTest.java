package com.github.quiram.developerlegacyindex;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class NonWeightedAggregatorTest {
    private final Aggregator aggregator = new NonWeightedAggregator();

    @Test
    void noDataIfNoContributions() {
        assertThat(aggregator.aggregate(emptyList()), is(empty()));
    }

    @Test
    void linesForSameUserOnDifferentDaysWillBeAggregated() {
        List<Pair<String, LocalDate>> contributions = Arrays.asList(
                Pair.of("user1", LocalDate.now()),
                Pair.of("user1", LocalDate.now())
        );
        final List<Pair<String, Long>> result = aggregator.aggregate(contributions);
        assertThat(result, hasSize(1));
        final Pair<String, Long> statsForUser1 = result.get(0);
        assertThat(statsForUser1, is(Pair.of("user1", 2L)));
    }

    @Test
    void linesForDifferentUsersAreAccountedForSeparately() {
        List<Pair<String, LocalDate>> contributions = Arrays.asList(
                Pair.of("user1", LocalDate.now()),
                Pair.of("user2", LocalDate.now())
        );
        final List<Pair<String, Long>> result = aggregator.aggregate(contributions);
        assertThat(result, hasSize(2));
        assertThat(result.get(0), is(Pair.of("user1", 1L)));
        assertThat(result.get(1), is(Pair.of("user2", 1L)));
    }
}