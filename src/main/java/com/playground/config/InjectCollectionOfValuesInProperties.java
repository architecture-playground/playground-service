package com.playground.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class InjectCollectionOfValuesInProperties {

    @Value("#{'${wkda.retail.finance.discount.countries:IT}'.split(',')}")
    public Set<String> discountsCountries;
}
