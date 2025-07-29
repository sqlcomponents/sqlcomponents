package org.sqlcomponents.compiler.java.mapper.postgresql;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;

import java.util.Set;

@Disabled
public class JsonTest extends DataTypeTest<JsonNode>{
    @Override
    Set<JsonNode> values() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return Set.of(objectMapper.readTree("""
                    {
                        "a": "a",
                        "b": "b"
                    }
                    """));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    String dataType() {
        return "JSON";
    }
}
