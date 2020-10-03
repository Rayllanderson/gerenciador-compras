package com.ray.model.dao;

import java.util.List;

import com.ray.model.entities.Product;

public interface ProductDao {

    void save(Product obj);
    void update(Product obj);
    void deletById(Long id);
    Product findById(Long id);
    List<Product> findAll();
    List<Product> findByName(String name);
}
