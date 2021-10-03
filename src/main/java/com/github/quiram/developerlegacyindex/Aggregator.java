package com.github.quiram.developerlegacyindex;

import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.List;

interface Aggregator {
    List<Pair<String, Long>> aggregate(List<Pair<String, LocalDate>> contributions);
}
