package ru.geekbrains.repository;

import org.springframework.stereotype.Repository;
import ru.geekbrains.persistance.Product;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {

    private List<Product> list;

    public ProductRepository() {
       list = new ArrayList<>();
       list.add(new Product(1,"banana", 24));
    }

    public List<Product> getAllProducts(){
        return list;
    }

    public Product findById(int id) {
        for(Product product : list){
            if(id == product.getId())
                return product;
        }
        return new Product(id, null, 0);
    }

    public void update(Product product) {
        for(Product p : list){
            if(product.getId() == p.getId()) {
                list.remove(p);
                list.add(product);
                return;
            }
        }
        list.add(product);
    }

    public void delete(Product product) {
        for(Product p : list){
            if(product.getId() == p.getId()) {
                list.remove(p);
                return;
            }
        }
    }


}
