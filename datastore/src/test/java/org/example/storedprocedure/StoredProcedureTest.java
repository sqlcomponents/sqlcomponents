package org.example.storedprocedure;

import org.example.RajaManager;
import org.example.util.DataSourceProvider;
import org.example.util.EncryptionUtil;
import org.junit.jupiter.api.Test;

class StoredProcedureTest {

    private final RajaManager rajaManager;

    StoredProcedureTest() {
        rajaManager =
                RajaManager.getManager(DataSourceProvider.dataSource(),
                        EncryptionUtil::enAnDecrypt,
                        EncryptionUtil::enAnDecrypt);
    }

    @Test
    void basicCall() {
        rajaManager.call().createCache();
    }


}
