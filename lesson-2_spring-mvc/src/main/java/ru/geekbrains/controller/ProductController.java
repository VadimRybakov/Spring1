package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.persistance.Product;
import ru.geekbrains.persistance.User;
import ru.geekbrains.repository.ProductRepository;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @GetMapping
    public String getAllProducts(Model model){
        List<Product> products = productRepository.getAllProducts();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable("id") int id, Model model) throws SQLException {
        Product product = productRepository.findById(id);
        model.addAttribute("product", product);
        return "products_form";
    }

    @GetMapping("/form")
    public String productForm(Model model){
        model.addAttribute("product", new Product());
        return "products_form";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        Product product = productRepository.findById(id);
        productRepository.delete(product);
        return "redirect:/products";
    }

    @PostMapping("/update")
    public String updateUser(Product product) {
        productRepository.update(product);
        return "redirect:/products";
    }

}
