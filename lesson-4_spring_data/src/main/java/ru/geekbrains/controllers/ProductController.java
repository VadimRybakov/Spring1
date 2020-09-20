package ru.geekbrains.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.entities.Product;
import ru.geekbrains.services.ProductService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public String getAllProducts(Model model,
                                 @RequestParam(name = "min_price", required = false)BigDecimal min,
                                 @RequestParam(name = "max_price", required = false)BigDecimal max){
        List<Product> products;
        if(min ==null && max == null) {
            products = productService.findAll();
        } else if (min == null) {
            products = productService.findByMaxPrice(max);
        } else if(max == null){
            products = productService.findByMinPrice(min);
        } else {
            products = productService.findByMinAndMaxPrice(min, max);
        }
        model.addAttribute("products",products);
        return "products";
    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable("id") int id, Model model){
        Product product = productService.findById(id);
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
        Product product = productService.findById(id);
        productService.delete(product);
        return "redirect:/products";
    }

    @PostMapping("/update")
    public String updateUser(Product product) {
        productService.update(product);
        return "redirect:/products";
    }

}
