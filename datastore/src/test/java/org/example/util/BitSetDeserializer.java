package org.example.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.BitSet;

public class BitSetDeserializer extends JsonDeserializer<BitSet> {
    @Override
    public BitSet deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        BitSet ret = new BitSet();
        int i = 0;
        JsonToken token;
        while (!JsonToken.END_ARRAY.equals(token = jsonParser.nextValue())) {
            //if (JsonToken.VALUE_TRUE.equals(token)) {
                ret.set(i);
            //} else {
            //    ret.set(i, 0);
            //}
            i++;
        }

        return ret;
    }
}
