package com.shop.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.constant.Category;
import com.shop.entity.Product;
import com.shop.entity.QProduct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Repository
public class ProductDslRepository {

    private final JPAQueryFactory query;

    public ProductDslRepository(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    public List<Product> getProductList() {
        QProduct product = QProduct.product;

        List<Product> products = query.selectFrom(product).fetch();

        return products;
    }

    public List<Product> getSearchProduct(String keyword){
        QProduct product = QProduct.product;

        return query.selectFrom(product).where(product.productName.contains(keyword)).fetch();


    }

    public List<Product> getNewProduct(Category category) {
        QProduct product = QProduct.product;

        List<Product> products = query.selectFrom(product)
                .where(product.category.eq(category))
                .orderBy(product.id.desc()).fetch();

        return products;
    }

    public List<Product> getHighPriceProducts(Category category) {
        QProduct product = QProduct.product;

        List<Product> products = query.selectFrom(product)
                .where(product.category.eq(category))
                .orderBy(product.price.desc()).fetch();

        return products;
    }

    public List<Product> getLowPriceProducts(Category category) {
        QProduct product = QProduct.product;

        List<Product> products = query.selectFrom(product)
                .where(product.category.eq(category))
                .orderBy(product.price.asc()).fetch();

        return products;
    }

    public List<Product> getProductsByName(Category category) {
        QProduct product = QProduct.product;

        List<Product> products = query.selectFrom(product)
                .where(product.category.eq(category))
                .orderBy(product.productName.asc()).fetch();

        return products;
    }
}
