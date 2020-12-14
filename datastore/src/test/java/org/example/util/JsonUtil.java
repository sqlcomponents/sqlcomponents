package org.example.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.AzaguRaja;
import org.example.model.AzaguRajaReference;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class JsonUtil {
    public static <T> List<T> getTestObjects(Class<T> tClass)  {
        String databaseType = System.getenv("DATABASE_TYPE") == null
                ? "postgres" : System.getenv("DATABASE_TYPE");

        List<T> list
                = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        File jsonFile  = new File("../datastore/src/test/resources/data/"+databaseType+"/"
                +tClass.getName().substring(tClass.getName().lastIndexOf('.')+1)+".json");
        if(!jsonFile.exists()) {
            jsonFile  = new File("../datastore/src/test/resources/data/"
                    +tClass.getName().substring(tClass.getName().lastIndexOf('.')+1)+".json");
        }

        try (JsonParser jsonParser = mapper.getFactory()
                .createParser(jsonFile)) {
            if (jsonParser.nextToken() != JsonToken.START_ARRAY) {
                throw new IllegalArgumentException(
                        "illicalstate of array", null);
            }
                while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    T entity = mapper
                            .readValue(jsonParser, tClass);
                    list.add(entity);
                }

        } catch (IOException e) {
            /* Unreachable */
            e.printStackTrace();
        }
        return list;
    }

    public static String getJSONString(Object obj)  {

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
