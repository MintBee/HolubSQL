package com.designpattern.repository;

import com.designpattern.exception.NoSuchProductException;
import com.designpattern.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRepository extends DaoRepository<Product> {
    public Product findByName(String name) throws SQLException {
        return findAllBy("SELECT * FROM product WHERE name = '" + name)
            .stream().findFirst().orElseThrow(NoSuchProductException::new);
    }

    public void deleteByName(String name) throws SQLException {
        deleteAllBy("DELETE FROM product WHERE name = '" + name);
    }

    public void updatePriceByName(String name, int price) throws SQLException {
        Product product = findByName(name);
        deleteByName(name);
        save(new Product(product.getName(), price));
    }

    @Override
    protected String getInsertQuery(Product model) {
        return "INSERT INTO product (name, price) VALUES ('" + model.getName() + "', " + model.getPrice() + ")";
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT * FROM product";
    }

    @Override
    protected Product mapToModel(ResultSet resultSet) throws SQLException {
        return new Product(resultSet.getString("name"), resultSet.getInt("price"));
    }

    public void createTable() {
        createTableWith("CREATE TABLE product (name VARCHAR, price INT NOT NULL, PRIMARY KEY(name))");
    }
}
