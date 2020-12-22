package org.example.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr353.JSR353Module;
import org.example.model.AzaguRaja;
import org.example.model.AzaguRajaReference;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class JsonUtil {

    private static ObjectMapper mapper ;
    public static <T> List<T> getTestObjects(Class<T> tClass)  {
        String databaseType = System.getenv("DATABASE_TYPE") == null
                ? "postgres" : System.getenv("DATABASE_TYPE");

        List<T> list
                = new ArrayList<>();


        File jsonFile  = new File("../datastore/src/test/resources/data/"+databaseType+"/"
                +tClass.getName().substring(tClass.getName().lastIndexOf('.')+1)+".json");
        if(!jsonFile.exists()) {
            jsonFile  = new File("../datastore/src/test/resources/data/"
                    +tClass.getName().substring(tClass.getName().lastIndexOf('.')+1)+".json");
        }

        try (JsonParser jsonParser = getObjectMapper().getFactory()
                .createParser(jsonFile)) {
            if (jsonParser.nextToken() != JsonToken.START_ARRAY) {
                throw new IllegalArgumentException(
                        "illicalstate of array", null);
            }
                while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    T entity = getObjectMapper()
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
        try {
            return getObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ObjectMapper getObjectMapper() {
        if(mapper == null) {
            mapper = new ObjectMapper();
            mapper.findAndRegisterModules();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }

        return mapper;
    }

}
