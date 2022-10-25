package com.zuzex.spark.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zuzex.spark.model.DataModel;
import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

@UtilityClass
public class InputUtils {
    private static final Pattern COMMA_PATTERN = Pattern.compile(",");
    private static final String[] SCHEMA = new String[]{"DATABASE_ENGINE", "PHONE", "RANDOM_CHARACTER", "IP_ADDRESS", "JOB_TYPE", "COUNTRY_CODE", "UUID", "IP_ADDRESS"};
    private static final String INSERT_QUERY_TEMPLATE = "INSERT INTO data(id, data) VALUES ('%s'::uuid, '%s') RETURNING id;";

    public static JsonNode inputConvert(final String inputRecord) {
        final ObjectNode dataObj = JsonNodeFactory.instance.objectNode();
        final String[] splitInput = COMMA_PATTERN.split(inputRecord);

        for (int i = 0; i < splitInput.length; i++) {
            dataObj.put(SCHEMA[i], splitInput[i]);
        }

        return dataObj;
    }

    public static String sqlInsert(final DataModel input) {
        return String.format(INSERT_QUERY_TEMPLATE, input.getId().toString(), input.getData().toPrettyString());
    }
}
