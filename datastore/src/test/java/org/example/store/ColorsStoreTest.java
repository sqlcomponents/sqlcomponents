package org.example.store;

import org.example.DataManager;
import org.example.model.Colors;
import org.example.model.types.ValidColorsType;
import org.example.util.DataSourceProvider;
import org.example.util.EncryptionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

public class ColorsStoreTest {
    private final ColorsStore colorsStore;

    public ColorsStoreTest () {
        DataManager dataManager =
                DataManager.getManager(DataSourceProvider.dataSource(),
                        EncryptionUtil::enAnDecrypt,
                        EncryptionUtil::enAnDecrypt);
        this.colorsStore = dataManager.getColorsStore();
    }

    @BeforeEach
    void init() throws SQLException {
        this.colorsStore.delete().execute();
    }

    @Test
    public void testInsertColors() throws SQLException {
        Colors colors = new Colors(ValidColorsType.blue);
        this.colorsStore.insert().values(colors).execute();
        List<Colors> colorsList = this.colorsStore.select().execute();
        Assertions.assertNotNull(colorsList);
        Assertions.assertEquals(1, colorsList.size());
    }
}
