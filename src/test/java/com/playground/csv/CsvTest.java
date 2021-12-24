package com.playground.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CsvTest {

    @Test
    void testCsv() throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream("redash_ids.csv");
        assertNotNull(is);

        try (
                Reader reader = new InputStreamReader(Objects.requireNonNull(is));
                CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
        ) {
            // Reading Records One by One in a String array
            List<String[]> redashIds = csvReader.readAll();
            System.out.println(redashIds);
        }
    }
}
