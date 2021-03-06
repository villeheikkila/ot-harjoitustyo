package itsepalvelupos.domain;


import itsepalvelupos.database.Database;
import itsepalvelupos.database.ProductDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class ProductServiceTest {

    private Database database;
    private ProductService productService;
    private ProductDao productDao;


    @Before
    public void setUp() {
        database = new Database("test.db");
        database.initDatabase();

        productDao = new ProductDao(database);
        productService = new ProductService(productDao);
    }

    @After
    public void removeDatabase() throws SQLException {
        database.removeDatabase();
    }

    @Test
    public void addProduct() throws SQLException {
        assertTrue(productService.addProduct("product", 10, 1));
        assertEquals(productDao.findOne(4).getName(), "product");
    }

    @Test
    public void addProductFail() throws SQLException {
        assertFalse(productService.addProduct("pr", 10, 1));
        assertNull(productDao.findOne(4));
    }

    @Test
    public void listProducts() throws SQLException {
        productService.addProduct("product", 10, 1);
        productService.addProduct("productTwo", 10, 1);
        assertEquals(productService.listProducts().get(3).getName(), "product");
        assertEquals(productService.listProducts().get(4).getName(), "productTwo");
    }

    @Test
    public void buyProduct() throws SQLException {
        productService.addProduct("product", 10, 2);
        assertEquals(productService.buyProduct(4), 10);
        assertEquals(productDao.findOne(4).getInventory(), 1);
    }

    @Test
    public void buyProductFail() throws SQLException {
        assertEquals(productService.buyProduct(4), 0);
    }
}
