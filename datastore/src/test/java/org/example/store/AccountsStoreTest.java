package org.example.store;

import org.example.DataManager;
import org.example.model.Accounts;
import org.example.util.DataSourceProvider;
import org.example.util.EncryptionUtil;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.SQLException;

class AccountsStoreTest {

    private final DataSource dataSource;
    private final AccountsStore accountsStore;
//    private final List<Accounts> accountsToTest;

    public AccountsStoreTest() {
        this.dataSource = DataSourceProvider.dataSource();
        DataManager dataManager =
                DataManager.getManager(
                        EncryptionUtil::enAnDecrypt,
                        EncryptionUtil::enAnDecrypt);
        // Stores used for testing
        this.accountsStore = dataManager.getAccountsStore();

    }

    @Test
    void getAccountsStore() throws SQLException {
        Accounts accounts = new Accounts(null, "TEST-1", 234.3);
        this.accountsStore.insert().values(accounts).execute(dataSource);
    }
}