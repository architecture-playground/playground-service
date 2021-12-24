package com.playground.redash_vs_elastic.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class ElasticAdHits {

    private ElasticTotal total;
    private List<ElasticAdHit> hits;
}
