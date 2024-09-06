package org.example.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public final class JsonUtil {
    private static ObjectMapper mapper;

    public static <T> List<T> getTestObjects(Class<T> tClass) {
        String databaseType =
                System.getenv("DATABASE_TYPE") == null ? "postgres" :
                        System.getenv("DATABASE_TYPE");

        List<T> list = new ArrayList<>();

        File jsonFile = new File(
                "../datastore/src/test/resources/data/" + databaseType + "/"
                        + tClass.getName()
                        .substring(tClass.getName().lastIndexOf('.') + 1) +
                        ".json");
        if (!jsonFile.exists()) {
            jsonFile = new File("../datastore/src/test/resources/data/"
                    + tClass.getName()
                    .substring(tClass.getName().lastIndexOf('.') + 1) +
                    ".json");
        }

        try (JsonParser jsonParser = getObjectMapper().getFactory()
                .createParser(jsonFile)) {
            if (jsonParser.nextToken() != JsonToken.START_ARRAY) {
                throw new IllegalArgumentException("illicalstate of array",
                        null);
            }
            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                T entity = getObjectMapper().readValue(jsonParser, tClass);
                list.add(entity);
            }

        } catch (IOException e) {
            /* Unreachable */
            e.printStackTrace();
        }
        return list;
    }

    public static String getJSONString(Object obj) {
        try {
            return getObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ObjectMapper getObjectMapper() {
        if (mapper == null) {
            mapper = new ObjectMapper();
            mapper.findAndRegisterModules();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            SimpleModule bitSetModule = new SimpleModule("BitSetModule");
            bitSetModule.addSerializer(BitSet.class, new BitSetSerializer());
            bitSetModule.addDeserializer(BitSet.class, new BitSetDeserializer());
            mapper.registerModule(bitSetModule);

        }

        return mapper;
    }
}
