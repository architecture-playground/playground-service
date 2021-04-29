package com.playground.config;

import com.playground.PlaygroundIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@PlaygroundIntegrationTest
public class InjectCollectionOfValuesInPropertiesTest {

    @Autowired
    private InjectCollectionOfValuesInProperties properties;

    @Test
    void testInjectSetOfValues() {
        assertThat(properties.discountsCountries).hasSize(1);
        assertThat(properties.discountsCountries).contains("IT");
    }
}
