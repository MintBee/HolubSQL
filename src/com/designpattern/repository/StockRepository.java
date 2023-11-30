package com.designpattern.repository;

import com.designpattern.exception.NoSuchProductException;
import com.designpattern.model.DecayingStock;
import com.designpattern.model.Product;
import com.designpattern.model.Stock;
import com.designpattern.model.UndecayingStock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class StockRepository extends DaoRepository<Stock> {
    public List<Stock> findByProduct(Product product) {
        try {
            return findAllBy("SELECT * FROM stock WHERE product_name = '" + product.getName() + "'");
        } catch (SQLException e) {
            throw new NoSuchProductException(e);
        }
    }

    public void delete(String id) {
        try {
            deleteAllBy("DELETE FROM stock WHERE id = '" + id + "'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteByProduct(String name) {
        try {
            deleteAllBy("DELETE FROM stock WHERE product_name = '" + name + "'");
        } catch (SQLException e) {
            throw new NoSuchProductException(e);
        }
    }

    public void deleteByExpirationDate(LocalDate expirationDate) {
        try {
            deleteAllBy("DELETE FROM stock WHERE expiration_date = '" + expirationDate + "'");
        } catch (SQLException e) {
            throw new NoSuchProductException(e);
        }
    }


    @Override
    protected String getInsertQuery(Stock stock) {
        if (stock instanceof DecayingStock) {
            return "INSERT INTO stock (product_name, expiration_date) VALUES ('" + stock.getProductName() + "', '" + ((DecayingStock) stock).getExpirationDate() + "')";
        } else {
            return "INSERT INTO stock (product_name, expiration_date) VALUES ('" + stock.getProductName() + "', NULL)";

        }
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT * FROM stock;";
    }

    @Override
    protected Stock mapToModel(ResultSet resultSet) throws SQLException {
        if (resultSet.getString("expiration_date") == null) {
            return new UndecayingStock(resultSet.getString("product_name"));
        } else {
            return new DecayingStock(resultSet.getString("product_name"), resultSet.getDate("expiration_date").toLocalDate());
        }
    }

    public void createTable() {
        createTableWith("CREATE TABLE stock (id INT , product_name VARCHAR NOT NULL, expiration_date DATE, PRIMARY KEY(id))");
    }
}
