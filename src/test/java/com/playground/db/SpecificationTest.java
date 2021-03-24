package com.playground.db;

import com.playground.PlaygroundIntegrationTest;
import com.playground.model.PlaygroundEntityStatus;
import com.playground.model.SpecificationItem;
import com.playground.repository.SpecificationItemRepository;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static com.playground.model.PlaygroundEntityStatus.*;
import static org.assertj.core.api.Assertions.assertThat;

@PlaygroundIntegrationTest
public class SpecificationTest {

    private static final DateTime _5_DAYS_BEFORE_NOW = DateTime.now().minusDays(5);
    private static final DateTime _2_DAYS_BEFORE_NOW = DateTime.now().minusDays(2);
    private static final DateTime _4_DAYS_BEFORE_NOW = DateTime.now().minusDays(4);

    @Autowired
    private SpecificationItemRepository repository;

    @Test
    void testSearchWithMultipleSpecifications() {
        // given
        SpecificationItem expected1 = repository.save(new SpecificationItem(SUCCESS, _5_DAYS_BEFORE_NOW));
        SpecificationItem expected2 = repository.save(new SpecificationItem(SUCCESS, _5_DAYS_BEFORE_NOW));
        repository.save(new SpecificationItem(SUCCESS, _2_DAYS_BEFORE_NOW));
        repository.save(new SpecificationItem(CANCELED, _4_DAYS_BEFORE_NOW));
        repository.save(new SpecificationItem(INITIAL, _5_DAYS_BEFORE_NOW));

        // when
        Specification<SpecificationItem> spec = Specification
                .where(withStatus(SUCCESS))
                .and(jodaDateTimeBefore(DateTime.now().minusDays(3)));

        List<SpecificationItem> result = repository.findAll(spec);

        // then
        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(expected1, expected2);
    }

    private Specification<SpecificationItem> withStatus(PlaygroundEntityStatus status) {
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }

    private Specification<SpecificationItem> jodaDateTimeBefore(DateTime beforeDate) {
        return (root, query, cb) -> cb.lessThan(root.get("jodaDateTime"), beforeDate);
    }
}
