package org.example.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
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


        try (JsonParser jsonParser = mapper.getFactory()
                .createParser(new File("../datastore/src/test/resources/data/"+databaseType+"/"+tClass.getName().substring(tClass.getName().lastIndexOf('.')+1)+".json"))) {
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


}
