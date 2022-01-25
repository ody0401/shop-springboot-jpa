package com.shop.repository;

import com.shop.constant.Category;
import com.shop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(Category category);

    Page<Product> findByCategory(Category category, Pageable pageable);

    List<Product> findByCategoryOrderByIdDesc(Category category);

    List<Product> findTop4ByOrderByIdDesc();
}
