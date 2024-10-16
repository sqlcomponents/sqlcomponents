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

import static org.example.store.ColorsStore.color;

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

    @Test
    public void testUpdateColors() throws SQLException {
        Colors colors = new Colors(ValidColorsType.blue);
        this.colorsStore.insert().values(colors).execute();
        List<Colors> colorsList = this.colorsStore.select().execute();
        Assertions.assertNotNull(colorsList);
        Assertions.assertEquals(1, colorsList.size());
        Colors color = colorsList.get(0);
        color = color.withColor(ValidColorsType.red);
        int updatedRows = this.colorsStore.update().set(color).execute();
        Assertions.assertEquals(1, updatedRows);
        colorsList = this.colorsStore.select().execute();
        Assertions.assertNotNull(colorsList);
        Assertions.assertEquals(1, colorsList.size());
        Assertions.assertEquals(ValidColorsType.red, colorsList.get(0).color());
    }

    @Test
    public void testDeleteColors() throws SQLException {
        Colors colors = new Colors(ValidColorsType.blue);
        this.colorsStore.insert().values(colors).execute();
        List<Colors> colorsList = this.colorsStore.select().where(color().isNotNull()).execute();
        Assertions.assertNotNull(colorsList);
        Assertions.assertEquals(1, colorsList.size());
    }

}
