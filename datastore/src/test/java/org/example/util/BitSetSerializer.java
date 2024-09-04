package org.example.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.BitSet;

public class BitSetSerializer extends JsonSerializer<BitSet> {

    @Override
    public void serialize(BitSet value, JsonGenerator gen,
                          SerializerProvider serializers) throws IOException, JsonProcessingException {
        gen.writeStartArray();
        for (int i = 0; i < value.length(); i++) {
            gen.writeBoolean(value.get(i));
        }
        gen.writeEndArray();
    }

}