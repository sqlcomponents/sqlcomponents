package org.example.store;

public class ColorsStoreTest {
//    private final DataSource dataSource;
//
//    private final ColorsStore colorsStore;
//
//    public ColorsStoreTest () {
//        this.dataSource = DataSourceProvider.dataSource();
//        DataManager dataManager =
//                DataManager.getManager(
//                        EncryptionUtil::enAnDecrypt,
//                        EncryptionUtil::enAnDecrypt);
//        this.colorsStore = dataManager.getColorsStore();
//    }
//
//    @BeforeEach
//    void init() throws SQLException {
//        this.colorsStore.delete().execute(dataSource);
//    }
//
//    @Test
//    public void testInsertColors() throws SQLException {
//        Colors colors = new Colors(ValidColorsType.blue);
//        this.colorsStore.insert().values(colors).execute(dataSource);
//        List<Colors> colorsList = this.colorsStore.select().execute(dataSource);
//        Assertions.assertNotNull(colorsList);
//        Assertions.assertEquals(1, colorsList.size());
//    }
//
//    @Test
//    public void testUpdateColors() throws SQLException {
//        Colors colors = new Colors(ValidColorsType.blue);
//        this.colorsStore.insert().values(colors).execute(dataSource);
//        List<Colors> colorsList = this.colorsStore.select().execute(dataSource);
//        Assertions.assertNotNull(colorsList);
//        Assertions.assertEquals(1, colorsList.size());
//        Colors color = colorsList.get(0);
//        color = color.withColor(ValidColorsType.red);
//        int updatedRows = this.colorsStore.update().set(color).execute(dataSource);
//        Assertions.assertEquals(1, updatedRows);
//        colorsList = this.colorsStore.select().execute(dataSource);
//        Assertions.assertNotNull(colorsList);
//        Assertions.assertEquals(1, colorsList.size());
//        Assertions.assertEquals(ValidColorsType.red, colorsList.get(0).color());
//    }
//
//    @Test
//    public void testDeleteColors() throws SQLException {
//        Colors colors = new Colors(ValidColorsType.blue);
//        this.colorsStore.insert().values(colors).execute(dataSource);
//        List<Colors> colorsList = this.colorsStore.select().where(color().isNotNull()).execute(dataSource);
//        Assertions.assertNotNull(colorsList);
//        Assertions.assertEquals(1, colorsList.size());
//    }

}
