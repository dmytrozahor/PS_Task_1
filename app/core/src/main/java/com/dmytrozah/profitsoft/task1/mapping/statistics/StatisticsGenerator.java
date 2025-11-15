package com.dmytrozah.profitsoft.task1.mapping.statistics;

import com.dmytrozah.profitsoft.task1.core.entities.statistics.Statistics;
import com.dmytrozah.profitsoft.task1.core.entities.statistics.StatisticsAttributeType;
import com.dmytrozah.profitsoft.task1.core.StatisticsService;
import com.dmytrozah.profitsoft.task1.core.entities.Bookshelf;
import com.dmytrozah.profitsoft.task1.mapping.reader.EntityFSProvider;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;

public class StatisticsGenerator {
    static final ObjectMapper MAPPER = new ObjectMapper();

    public Statistics generate(
            final EntityFSProvider fsProvider,
            final StatisticsService service,
            final Path path)
    {
        Statistics statistics = service.getStatistics(path);

        try {
            try (final JsonParser parser = MAPPER.getFactory().createParser(fsProvider.getInputStream(path))) {

                JsonToken token = parser.nextToken();

                if (token != JsonToken.START_ARRAY) {
                    throw new IllegalStateException("Expected an array token");
                }

                while (parser.nextToken() != JsonToken.END_ARRAY) {
                    final String fieldName = parser.nextFieldName();

                    if (fieldName == null) {
                        continue;
                    }

                    if (!fieldName.equals(service.getAttributeType().getKey())) {
                        continue;
                    }

                   String textValue = parser.nextTextValue();

                    if (textValue == null) {
                        JsonToken current = parser.getCurrentToken();

                        if (current == JsonToken.VALUE_NUMBER_INT || current == JsonToken.VALUE_NUMBER_FLOAT) {
                            textValue = parser.getValueAsString();
                        } else {
                            parser.skipChildren();
                            continue;
                        }
                    }

                    if (textValue == null) {
                        continue;
                    }

                    if (service.getAttributeType().isCommaSeparated()) {
                        statistics = populateCommaSeparatedStatistics(textValue, statistics);
                    } else {
                        statistics = statistics.increaseOccurrences(textValue, 1);
                    }

                    parser.nextToken();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return statistics;
    }

    protected Statistics populateCommaSeparatedStatistics(final String csField, final Statistics statistics){
        final String[] genres = csField.split(", ");

        for (String genre : genres) {
            statistics.increaseOccurrences(genre, 1L);
        }

        return statistics;
    }

}
