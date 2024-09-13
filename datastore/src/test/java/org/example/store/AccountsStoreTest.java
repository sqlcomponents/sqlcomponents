package org.example.store;

import org.example.DatabaseManager;
import org.example.model.Accounts;
import org.example.util.DataSourceProvider;
import org.example.util.EncryptionUtil;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class AccountsStoreTest {

    private final AccountsStore accountsStore;
//    private final List<Accounts> accountsToTest;

    public AccountsStoreTest() {
        DatabaseManager databaseManager =
                DatabaseManager.getManager(DataSourceProvider.dataSource(),
                        EncryptionUtil::enAnDecrypt,
                        EncryptionUtil::enAnDecrypt);
        // Stores used for testing
        this.accountsStore = databaseManager.getAccountsStore();

    }

    @Test
    void getAccountsStore() throws SQLException {
        Accounts accounts = new Accounts(null, "TEST-1", 234.3);
        this.accountsStore.insert().values(accounts).execute();
    }
}