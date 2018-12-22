package itsepalvelupos.database;

import itsepalvelupos.domain.Store;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class StoreDaoTest {

    private Database database;
    private Store store;
    private StoreDao storeDao;

    @Before
    public void setUp() throws SQLException {
        database = new Database("test.db");
        database.initDatabase();

        storeDao = new StoreDao(database);
        store = new Store("store", 1000);
        storeDao.add(store);
    }

    @After
    public void removeDatabase() throws SQLException {
        database.removeDatabase();
    }


    @Test
    public void getStore() throws SQLException {
        assertEquals(storeDao.getStore(1).getName(), "store");
        assertEquals(storeDao.getStore(1).getCash(), 1000);
    }

    @Test
    public void getStoreFalse() throws SQLException {
        assertNull(storeDao.getStore(2));
    }

    @Test
    public void update() throws SQLException {
        store.setName("newStore");
        store.setCash(500);
        store.setId(1);
        storeDao.update(store);
        assertEquals(storeDao.getStore(1).getName(), "newStore");
        assertEquals(storeDao.getStore(1).getCash(), 500);
    }
}