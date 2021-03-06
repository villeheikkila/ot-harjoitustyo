package itsepalvelupos.database;

import itsepalvelupos.domain.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AccountDao implements Dao<Account, Integer> {

    private Database database;

    public AccountDao(Database database) {
        this.database = database;
    }

    /**
     * Metodi hakee käyttäjän tiedot tietokantataulusta.
     *
     * @param   key   Käyttäjän id tietokantataulussa.
     *
     * @return palauttaa Account olion.
     *
     * @throws SQLException mikäli tapahtuu virhe.
     *
     */

    @Override
    public Account findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Accounts WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet resultSet = stmt.executeQuery();
        boolean hasOne = resultSet.next();
        if (!hasOne) {
            stmt.close();
            connection.close();
            resultSet.close();
            return null;
        }

        Account account = new Account(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password"), resultSet.getBoolean("admin"), resultSet.getInt("balance"));

        resultSet.close();
        stmt.close();
        connection.close();

        return account;
    }

    /**
     * Metodi hakee kaikki käyttäjät tietokantataulusta.
     *
     * @return palauttaa listan Account olioita.
     *
     * @throws SQLException mikäli tapahtuu virhe.
     *
     */

    @Override
    public List<Account> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Accounts");

        ResultSet resultSet = stmt.executeQuery();
        List<Account> accounts = new ArrayList<>();
        while (resultSet.next()) {
            Integer id = resultSet.getInt("id");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            Boolean admin = resultSet.getBoolean("admin");
            Integer balance = resultSet.getInt("balance");
            accounts.add(new Account(id, username, password, admin, balance));
        }

        resultSet.close();
        stmt.close();
        connection.close();

        return accounts;
    }

    /**
     * Metodi poistaa käyttäjän tietokantataulusta.
     *
     * @param   key   Käyttäjän id tietokantataulussa.
     *
     * @throws SQLException mikäli tapahtuu virhe.
     */

    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Accounts WHERE id = (?)");
        stmt.setObject(1, key);

        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }

    /**
     * Metodi hakee käyttäjän tiedot tietokantataulusta.
     *
     * @param   userName   Käyttäjän nimi tietokantataulussa.
     *
     * @return palauttaa Account olion, jos käyttäjä löytyy tai null, mikäli käyttäjää ei löydy.
     *
     * @throws SQLException mikäli tapahtuu virhe.
     *
     */

    public Account findName(String userName) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Accounts WHERE username = ?");
        stmt.setString(1, userName);

        ResultSet resultSet = stmt.executeQuery();

        boolean hasOne = resultSet.next();
        if (!hasOne) {
            stmt.close();
            connection.close();
            resultSet.close();
            return null;
        }

        Account account = new Account(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password"), resultSet.getBoolean("admin"), resultSet.getInt("balance"));

        resultSet.close();
        stmt.close();
        connection.close();

        return account;
    }

    /**
     * Metodi lisää käyttäjän tiedot tietokantaan.
     *
     * @param  account  Lisättävä Account olio.
     *
     * @throws SQLException mikäli tapahtuu virhe.
     *
     */

    public void add(Account account) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Accounts (username, password, admin, balance) VALUES (?, ?, ?, ?)");
        stmt.setString(1, account.getUsername());
        stmt.setString(2, account.getPassword());
        stmt.setBoolean(3, account.isAdmin());
        stmt.setInt(4, account.getBalance());

        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }

    /**
     * Metodi päivittää käyttäjän tiedot tietokantataulusta.
     *
     * @param  account Account olio, jonka tiedoilla korvataan vanhat tiedot.
     *
     * @throws SQLException mikäli tapahtuu virhe.
     *
     */

    public void update(Account account) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("UPDATE Accounts SET username = ?, password = ?, admin = ?, balance = ? WHERE id = ?");
        stmt.setString(1, account.getUsername());
        stmt.setString(2, account.getPassword());
        stmt.setBoolean(3, account.isAdmin());
        stmt.setInt(4, account.getBalance());
        stmt.setInt(5, account.getId());

        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }
}