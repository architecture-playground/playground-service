package com.playground.guava;

import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class SetsDifferenceTest {

    @Test
    void testParseFromString() {
        var allPrevious = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        var targetSet = Set.of(1, 2, 3, 4, 5, 11, 12);

        Sets.SetView<Integer> diff = Sets.difference(targetSet, allPrevious);

        assertThat(diff).containsExactlyInAnyOrder(11, 12);
    }
}
