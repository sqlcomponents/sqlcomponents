package org.example.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.AzaguRaja;
import org.example.model.AzaguRajaReference;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;

public final class JsonUtil {
    public static List<AzaguRajaReference> getAzaguRajaReferences(final String databaseType)  {
        List<AzaguRajaReference> azaguRajaReferences
                = null;
        try {
            azaguRajaReferences = fromJSON(new TypeReference<List<AzaguRajaReference>>() {}
            , new FileReader("../datastore/src/test/resources/data/"+databaseType+"/AzaguRajaReference.json"));
        } catch (FileNotFoundException e) {
            // Unreachable
            e.printStackTrace();
        }
        return azaguRajaReferences;
    }

    public static List<AzaguRaja> getAzaguRajas(final String databaseType) {
        List<AzaguRaja> azaguRajas = null;
        try {
            azaguRajas = fromJSON(new TypeReference<List<AzaguRaja>>() {}
                    , new FileReader("../datastore/src/test/resources/data/"+databaseType+"/AzaguRaja.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return azaguRajas;
    }

    private static <T> T fromJSON(final TypeReference<T> type,
                                 final Reader reader) {
        T data = null;

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        try {
            data = mapper.readValue(reader, type);
        } catch (Exception e) {
            // Handle the problem
        }
        return data;
    }
}
