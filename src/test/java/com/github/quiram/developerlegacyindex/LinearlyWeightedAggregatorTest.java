package com.github.quiram.developerlegacyindex;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class LinearlyWeightedAggregatorTest {
    private final Aggregator aggregator = new LinearlyWeightedAggregator();

    @Test
    void noDataIfNoContributions() {
        assertThat(aggregator.aggregate(emptyList()), is(empty()));
    }

    @Test
    @DisplayName("Yesterday's contribution counts as one, today's counts as zero")
    void yesterdayAndTodayCommit() {
        List<Pair<String, LocalDate>> contributions = Arrays.asList(
                Pair.of("user1", LocalDate.now().minusDays(1)),
                Pair.of("user1", LocalDate.now())
        );
        final List<Pair<String, Long>> result = aggregator.aggregate(contributions);
        assertThat(result, hasSize(1));
        final Pair<String, Long> statsForUser1 = result.get(0);
        assertThat(statsForUser1, is(Pair.of("user1", 1L)));
    }

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
    void outputIsOrderedByHighestContribution() {
        List<Pair<String, LocalDate>> contributions = Arrays.asList(
                Pair.of("user1", LocalDate.now().minusDays(1)),
                Pair.of("user2", LocalDate.now().minusDays(2))
        );
        final List<Pair<String, Long>> result = aggregator.aggregate(contributions);
        assertThat(result.get(0).getKey(), is("user2"));
    }
}