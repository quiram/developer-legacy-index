package com.github.quiram.developerlegacyindex;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class ExponentiallyWeightedAggregatorTest {
    private final Aggregator aggregator = new ExponentiallyWeightedAggregator();

    @Test
    @DisplayName("Contribution from day before yesterday counts double than yesterday's")
    void yesterdayAndDayBeforeYesterdayCommit() {
        List<Pair<String, LocalDate>> contributions = Arrays.asList(
                Pair.of("user1", LocalDate.now().minusDays(1)),
                Pair.of("user2", LocalDate.now().minusDays(2))
        );
        final List<Pair<String, Long>> result = aggregator.aggregate(contributions);
        assertThat(result, hasSize(2));
        assertThat(result, hasItem(Pair.of("user1", 1L)));
        assertThat(result, hasItem(Pair.of("user2", 2L)));
    }

    @Test
    @DisplayName("Contribution from ten days ago counts more than ten times that of yesterday's")
    void yesterdayAndThreeDaysAgoCommit() {
        List<Pair<String, LocalDate>> contributions = Arrays.asList(
                Pair.of("user1", LocalDate.now().minusDays(1)),
                Pair.of("user2", LocalDate.now().minusDays(10))
        );
        final List<Pair<String, Long>> result = aggregator.aggregate(contributions);
        assertThat(result, hasSize(2));
        assertThat(result, hasItem(Pair.of("user1", 1L)));
        final Pair<String, Long> user2Result = result.get(0);
        assertThat(user2Result.getKey(), is("user2"));
        assertThat(user2Result.getValue(), is(greaterThan(10L)));
    }
}