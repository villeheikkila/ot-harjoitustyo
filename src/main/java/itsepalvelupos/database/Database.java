package itsepalvelupos.database;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseName;


    /**
     * Konstruktori määrittää käytettävän tietokannan nimen
     *
     * @param   databaseName   Tietokannan nimi
     *
     */

    public Database(String databaseName) {
        this.databaseName = databaseName;
    }


    /**
     * Metodi mahdollistaa yhdistämisen tietokantaan
     *
     * @return palauttaa yhteyden käytössä olevaan tietokantaan.
     *
     */


    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + databaseName);
    }

    /**
     * Metodi luo uuden tietokannan
     *
     */

    public void initDatabase() {
        File file = new File(databaseName);
        if (file.exists()) {
            System.out.println("Error: Database already exists");
        } else {
            List<String> statements = statements();
            try (Connection conn = getConnection()) {
                Statement stmt = conn.createStatement();

                for (String statement : statements) {
                    System.out.println("Running command: " + statement);
                    stmt.executeUpdate(statement);
                }

            } catch (Throwable t) {
                System.out.println("Error: " + t.getMessage());
            }
        }
    }

    /**
     * Metodi poistaa nykyisen tietokannan
     *
     */

    public void removeDatabase() {
        File file = new File(databaseName);
        if (!file.exists()) {
            System.out.println("Error: Database doesn't exist");
        } else {
            file.delete();
        }
    }

    private List<String> statements() {
        ArrayList<String> db = new ArrayList<>();

        db.add("CREATE TABLE Products (id integer PRIMARY KEY, name varchar(100), inventory INT, price INT);");
        db.add("CREATE TABLE Accounts (id integer PRIMARY KEY, username varchar(100), password varchar(20), admin boolean, balance integer);");
        db.add("CREATE TABLE Store (id integer PRIMARY KEY, name varchar(100), cash integer);");

        return db;
    }
}