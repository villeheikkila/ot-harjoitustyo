package itsepalvelupos.domain;

import itsepalvelupos.database.ProductDao;
import java.sql.SQLException;
import java.util.List;

public class ProductService {

    private ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    /**
     * Metodi lisää tuotteen annetuilla arvoilla Products tietokantatauluun.
     *
     * @param   name   Tuotteen nimi (pituus vähintään 3 kirjainta).
     * @param   price  Tuotteen hinta (positiivinen kokonaisluku).
     * @param   inventory   Tuotteiden lukumäärä (positiivinen kokonaisluku).
     *
     *
     * @return palauttaa true, jos tuotteelle annetut tiedot ovat kelvollisia.
     *
     * @throws SQLException mikäli tapahtuu virhe.
     *
     */

    public boolean addProduct(String name, int price, int inventory) throws SQLException {
        if ((name.length() > 3) && (price >= 0) && (inventory >= 0)) {
            Product product = new Product(name, inventory, price);
            productDao.add(product);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metodi listaa kaikki tuotteet Products tietokantataulussa.
     *
     * @return palauttaa listan Product olioita.
     *
     * @throws SQLException mikäli tapahtuu virhe.
     *
     */

    public List<Product> listProducts() throws SQLException {
        List<Product> products = productDao.findAll();
        return products;
    }

    /**
     * Metodi vähentää annetun tuotteen määrää vastastossa yhdellä.
     *
     * @param   id   Tuotteen id (positiivinen kokonaisluku).
     *
     * @return palauttaa tuotteen hinnan, jos sellainen on olemassa tai muuten 0.
     *
     * @throws SQLException mikäli tapahtuu virhe.
     *
     */

    public int buyProduct(int id) throws SQLException {
        if ((productDao.findOne(id) != null) && (productDao.findOne(id).getInventory() > 0)) {
            Product product = productDao.findOne(id);
            product.reduceInventory();
            productDao.update(product);
            return product.getPrice();
        } else {
            return 0;
        }
    }
}