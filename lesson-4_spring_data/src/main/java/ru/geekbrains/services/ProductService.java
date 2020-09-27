package ru.geekbrains.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.entities.Product;
import ru.geekbrains.repositories.ProductRepository;

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

    public List<Product> findByMinPrice(BigDecimal min) { return productRepository.findByMinPrice(min);
    }

    public List<Product> findByMaxPrice(BigDecimal max) {return productRepository.findByMaxPrice(max);
    }

    public List<Product> findByMinAndMaxPrice(BigDecimal min, BigDecimal max) {return productRepository.findByMinAndMaxPrice(min, max);
    }
}
