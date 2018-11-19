package itsepalvelupos.domain;

import java.util.Objects;

public class Product {
    private int id;
    private String name;
    private int inventory;
    private int price;

    public Product(int id, String name, int inventory, int price) {
        this.id = id;
        this.name = name;
        this.inventory = inventory;
        this.price = price;
    }

    public boolean reduceInventory(int amount) {
        if (this.inventory >= amount) {
            this.inventory -= amount;
            return true;
        } return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id &&
                Objects.equals(name, product.name);
    }

}
