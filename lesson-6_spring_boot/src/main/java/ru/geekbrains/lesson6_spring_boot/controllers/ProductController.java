package ru.geekbrains.lesson6_spring_boot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.lesson6_spring_boot.entities.Product;
import ru.geekbrains.lesson6_spring_boot.exceptions.NotFoundException;
import ru.geekbrains.lesson6_spring_boot.services.ProductService;

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
                                 @RequestParam("sortBy") Optional<String> sortBy,
                                 @RequestParam("direction") Optional<String> direction
    ){
        Sort sort;
        String param;
        if(sortBy.isPresent()) {
            if (sortBy.get().equals("price")) param = "price";
            else if (sortBy.get().equals("title")) throw new NotFoundException();
            else param = "id";
        } else param = "id";
        if(direction.isPresent()) {
            if (direction.get().equals("DESC")) sort = Sort.by(Sort.Direction.DESC, param);
            else sort = Sort.by(Sort.Direction.ASC, param);
        } else sort = Sort.by(Sort.Direction.ASC, param);
        PageRequest pageRequest = PageRequest.of(page.orElse(1) - 1, size.orElse(5), sort);
        Page<Product> products = productService.findByFilters(min, max, pageRequest);
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") int id, Model model){
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "products_form";
    }

    @GetMapping("/edit/form")
    public String productForm(Model model){
        model.addAttribute("product", new Product());
        return "products_form";
    }

    @GetMapping("/edit/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        Product product = productService.findById(id);
        productService.delete(product);
        return "redirect:/products";
    }

    @PostMapping("/edit/update")
    public String updateUser(Product product) {
        productService.update(product);
        return "redirect:/products";
    }
}
