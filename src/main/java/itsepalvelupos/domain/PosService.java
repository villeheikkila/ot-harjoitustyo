package itsepalvelupos.domain;

import itsepalvelupos.database.AccountDao;
import itsepalvelupos.database.Database;
import itsepalvelupos.database.ProductDao;
import itsepalvelupos.database.StoreDao;
import java.sql.SQLException;
import java.util.ArrayList;

public class PosService {

    private Database database;
    private AccountService accountService;
    private ProductService productService;
    private StoreService storeService;

    private AccountDao accountDao;
    private ProductDao productDao;
    private StoreDao storeDao;

    private ArrayList<Integer> shoppingCart;
    private boolean newDatabase;

    public PosService(String db) {
        database = new Database(db);

        newDatabase = database.initDatabase();

        accountDao = new AccountDao(database);
        productDao = new ProductDao(database);
        storeDao = new StoreDao(database);

        accountService = new AccountService(accountDao);
        productService = new ProductService(productDao);
        storeService = new StoreService(storeDao);
    }

    /**
     * Metodi ostaa tuotteen eli vähentää yhden tuotteen varastosta ja siirtää tuotteen hinnan käyttäjän tililtä kassaan.
     *
     * @param   id   Tuotteen id (positiivinen kokonaisluku).
     *
     * @return palauttaa true, jos ostaminen on mahdollista.
     *
     * @throws SQLException mikäli tapahtuu virhe.
     *
     */

    public boolean buy(int id) throws SQLException {
        if ((productDao.findOne(id) != null) && (accountService.getCurrentUser().getBalance() >= productDao.findOne(id).getPrice())) {
            int price = productDao.findOne(id).getPrice();
            int negPrice = -price;
            productService.buyProduct(id);
            accountService.changeBalance(negPrice);
            storeService.changeCash(price);
            return true;
        }

        return false;
    }

    /**
     * Metodi ostaa kaikki tuotteet ostoskorissa ja tyhjentää lopuksi ostoskorin.
     *
     * @throws SQLException mikäli tapahtuu virhe.
     *
     */

    public void buyCart() throws SQLException {
        for (Integer id : shoppingCart) {
            buy(id);
        }
        emptyCart();
    }

    /**
     * Metodi lisää tuotteen ostoskoriin. Saman tuotteen voi lisätä vain kerran.
     *
     * @param   id   Tuotteen id (positiivinen kokonaisluku).
     *
     *
     * @return palauttaa true, jos lisääminen on mahdollista.
     *
     * @throws SQLException mikäli tapahtuu virhe.
     *
     */

    public boolean addToCart(int id) throws SQLException {
        if (productDao.findOne(id).getInventory() > 0) {
            for (Integer cart : shoppingCart) {
                if (cart == id) {
                    return false;
                }
            }
            shoppingCart.add(id);
            return true;
        }
        return false;
    }

    /**
     * Metodi tyhjentää ostoskorin.
     *
     */

    public void emptyCart() {
        this.shoppingCart = null;
    }

    /**
     * Metodi mahdollistaa productService olion käsittelyn posServicen ulkopuolelta.
     *
     * @return palauttaa productService olion.
     */

    public ProductService getProductService() {
        return productService;
    }

    /**
     * Metodi mahdollistaa accountService olion käsittelyn posServicen ulkopuolelta.
     *
     * @return palauttaa accountService olion.
     */

    public AccountService getAccountService() {
        return accountService;
    }

    /**
     * Metodi mahdollistaa database olion käsittelyn posServicen ulkopuolelta.
     *
     * @return palauttaa database olion.
     */

    public Database getDatabase() {
        return database;
    }

    /**
     * Metodi kertoo onko tietokanta luotu tässä instanssissa.
     *
     * @return palauttaa true, jos tietokanta on luotu tässä instanssissa, muuten false.
     */

    public boolean isNewDatabase() {
        return newDatabase;
    }

    /**
     * Metodi mahdollistaa store olion käsittelyn posServicen ulkopuolelta.
     *
     * @return palauttaa store olion.
     */

    public StoreService getStoreService() {
        return storeService;
    }

    /**
     * Metodi poistaa käytössä olevan tietokannan pysyvästi.
     *
     */

    public void deleteDatabase() {
        database.removeDatabase();
    }



}
