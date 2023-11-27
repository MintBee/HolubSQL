package com.designpattern.model;

import com.designpattern.repository.ProductRepository;
import com.designpattern.repository.StockRepository;

import java.util.List;

// @galaxy821
// ModelVisitor를 구현해서 하려고 했으나, Product와 Stock의 visit 메소드음 다르기 때문에 (파라미터가 필요 없)
// ModelVisitor를 구현해서 하지 않고 자체 구현 클래스로 작성했습니다.
// 기존 코드와 통일성을 유지하기 위해 DB에서 가져오는 부분은 동일한 형식을 가지도록 작성했습니다.
// TODO : DbSelectVisitor 구현된 코드에 대해 논의

public class DbSelectVisitor{
    private static final ProductRepository productRepository = new ProductRepository();
    private static final StockRepository stockRepository = new StockRepository();

    public List<Product> visitProduct() {
        List<Product> products;
        try {
            products = productRepository.findByAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    public List<Stock> visitStock() {
        List<Stock> stocks;
        try {
            stocks = stockRepository.findByAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return stocks;
    }
}
