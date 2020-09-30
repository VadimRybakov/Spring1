package ru.geekbrains.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.entities.Product;
import ru.geekbrains.repositories.ProductRepository;
import ru.geekbrains.repositories.ProductSpecification;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(int id) { return productRepository.findById(id).orElse(new Product());
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

/*    public List<Product> findByMinPrice(BigDecimal min) { return productRepository.findByMinPrice(min);
    }

    public List<Product> findByMaxPrice(BigDecimal max) {return productRepository.findByMaxPrice(max);
    }

    public List<Product> findByMinAndMaxPrice(BigDecimal min, BigDecimal max) {return productRepository.findByMinAndMaxPrice(min, max);
    }*/
}
