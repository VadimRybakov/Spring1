package ru.geekbrains.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.entities.Product;
import ru.geekbrains.services.ProductService;

import java.math.BigDecimal;
import java.util.Optional;

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
                                 @RequestParam(name = "max_price", required = false)BigDecimal max,
                                 @RequestParam("page") Optional<Integer> page,
                                 @RequestParam("size") Optional<Integer> size,
                                 @RequestParam(name = "sortBy", required = false) String sortBy){
/*        List<Product> products;
        if(min ==null && max == null) {
            products = productService.findAll();
        } else if (min == null) {
            products = productService.findByMaxPrice(max);
        } else if(max == null){
            products = productService.findByMinPrice(min);
        } else {
            products = productService.findByMinAndMaxPrice(min, max);
        }
        model.addAttribute("products",products);*/
        String sortParam;
        if(sortBy.isEmpty())
            sortParam = "id";
        else sortParam = sortBy;
        Sort sort = Sort.by(Sort.Direction.ASC, sortParam);
        PageRequest pageRequest = PageRequest.of(page.orElse(1) - 1, size.orElse(5), sort);
        Page<Product> products = productService.findByFilters(min, max, pageRequest);
        model.addAttribute("products", products);

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
