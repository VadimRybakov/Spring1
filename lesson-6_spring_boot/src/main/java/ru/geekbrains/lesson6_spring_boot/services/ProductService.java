package ru.geekbrains.lesson6_spring_boot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.lesson6_spring_boot.exceptions.NotFoundException;
import ru.geekbrains.lesson6_spring_boot.entities.Product;
import ru.geekbrains.lesson6_spring_boot.repositories.ProductRepository;
import ru.geekbrains.lesson6_spring_boot.repositories.ProductSpecification;


import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Product findById(int id) { return productRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public void delete(Product product) { productRepository.delete(product);
    }

    @Transactional
    public void update(Product product) { productRepository.saveAndFlush(product);
    }

    public Page<Product> findByFilters(BigDecimal min, BigDecimal max, PageRequest page) {

        Specification<Product> spec = ProductSpecification.trueLiteral();

        if(max == null && min != null) {
            spec = spec.and(ProductSpecification.filterByMinPrice(min));
        } else if(max != null && min == null){
            spec = spec.and(ProductSpecification.filterByMaxPrice(max));
        } else if(max != null && min != null){
            spec = spec.and(ProductSpecification.filterByMinAndMaxPrice(min, max));
        }
        return productRepository.findAll(spec, page);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
