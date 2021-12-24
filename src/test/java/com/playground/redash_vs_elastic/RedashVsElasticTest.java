package com.playground.redash_vs_elastic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.playground.redash_vs_elastic.model.ElasticAdHit;
import com.playground.redash_vs_elastic.model.ElasticAdHits;
import com.playground.redash_vs_elastic.model.ElasticAdResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RedashVsElasticTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testConsistency() throws Exception {
        Set<String> redashIds = readRedashIds("redash_ids.csv");
        Set<String> elasticIds = readElasticIds("elastic_ids.json");

        Set<String> idsExistedInRedashOnly = new HashSet<String>(CollectionUtils.subtract(redashIds, elasticIds));
        Set<String> idsExistedInElasticOnly = new HashSet<String>(CollectionUtils.subtract(elasticIds, redashIds));
        Set<String> idsExistedInBoth = new HashSet<String>(CollectionUtils.intersection(elasticIds, redashIds));

        System.out.println("count idsExistedInRedashOnly: " + idsExistedInRedashOnly.size());
        System.out.println("count idsExistedInElasticOnly: " + idsExistedInElasticOnly.size());
        System.out.println("count idsExistedInBoth: " + idsExistedInBoth.size());

        System.out.println("\n\n  -------------");
        System.out.println("idsExistedInRedashOnly: " + idsExistedInRedashOnly);

        System.out.println("\n\n  -------------");
        System.out.println("idsExistedInElasticOnly: " + idsExistedInElasticOnly);

        System.out.println("\n\n  -------------");
        System.out.println("idsExistedInBoth: " + idsExistedInBoth);

        System.out.println("\n\n  -------------");
        System.out.println("query: " + createSelectWhereIdsInQuery(idsExistedInElasticOnly));
    }

    private String createSelectWhereIdsInQuery(Set<String> idsExistedInElasticOnly) {
        StringBuilder builder = new StringBuilder("select " +
                "id, published, unpublished_reason, unpublished_at " +
                "from wkda_classifieds.ad\n" +
                "where id in (");

        String idsList = idsExistedInElasticOnly.stream()
                .map(RedashVsElasticTest::quote)
                .collect(Collectors.joining(", "));

        builder.append(idsList);
        builder.append(")");
        return builder.toString();
    }

    /**
     * Read ids from Redash payload:
     * <p>
     * query:
     * <p>
     * select id from wkda_classifieds.ad
     * where published = 'true' and country_code = 'DE'
     * <p>
     * payload like:
     * <p>
     * id
     * 63ee8270-f556-4441-92ad-99ac4475f61a
     * 7ce0fd94-b2ee-4af7-b1c5-0da85f5e569c
     * ...
     */
    private Set<String> readRedashIds(String redashFile) throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(redashFile);
        assertNotNull(is);

        try (
                Reader reader = new InputStreamReader(Objects.requireNonNull(is));
                CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
        ) {
            // Reading Records One by One in a String array

            return csvReader.readAll().stream()
                    .map(Arrays::asList)
                    .flatMap(List::stream)
                    .collect(Collectors.toSet());
        }
    }

    /**
     * Read ids from Elastic payload:
     * <p>
     * query:
     * <p>
     * {
     * "track_total_hits": true,
     * "from": 0,
     * "size": 5000,
     * "query": {
     * "bool": {
     * "must": [
     * {
     * "match": {
     * "published": "true"
     * }
     * },
     * {
     * "match": {
     * "countryCode": "DE"
     * }
     * }
     * ]
     * }
     * },
     * "_source": false
     * }
     * <p>
     * payload like: see elastic_ids.json
     */
    private Set<String> readElasticIds(String elasticFile) throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(elasticFile);
        assertThat(is).isNotNull();

        String json = IOUtils.toString(is, StandardCharsets.UTF_8.name());
        ElasticAdResponse elasticAdResponse = objectMapper.readValue(json, ElasticAdResponse.class);

        ElasticAdHits hits = elasticAdResponse.getBody().getHits();
        return hits.getHits().stream()
                .map(ElasticAdHit::getId)
                .collect(Collectors.toSet());
    }

    public static String quote(String s) {
        return '\'' + s + '\'';
    }
}
