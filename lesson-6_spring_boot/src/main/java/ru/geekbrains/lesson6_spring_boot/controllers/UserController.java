package ru.geekbrains.lesson6_spring_boot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.geekbrains.lesson6_spring_boot.entities.Role;
import ru.geekbrains.lesson6_spring_boot.entities.User;
import ru.geekbrains.lesson6_spring_boot.services.RoleService;
import ru.geekbrains.lesson6_spring_boot.services.UserService;

import java.util.List;

@Controller
public class UserController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/login")
    public String showMyLoginPage() {
        return "login";
    }

    @GetMapping("/users")
    public String getUsers(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("users/edit")
    public String createUser(Model model){
        List<Role> roles = roleService.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("user", new User());
        return "user";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(Model model, @PathVariable("id") int id){
        User user = userService.findById(id);
        List<Role> roles = roleService.findAll();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "user";
    }

    @GetMapping("/users/edit/delete/{id}")
    public String deleteUser(@PathVariable("id") int id){
        userService.deleteById(id);
        return "redirect:/users";
    }

    @PostMapping("/users/edit/update")
    public String saveUser(@ModelAttribute(name = "user") User user, Model model){
        boolean passMismatch = user.getPassword().equals(user.getConfirmPassword());
        if (!passMismatch) {
            String pasMismatchErr = "passwordsNotMatchException";
            List<Role> roles = roleService.findAll();
            model.addAttribute("pasMismatchErr", pasMismatchErr);
            model.addAttribute("user", user);
            model.addAttribute("roles", roles);
            return "user";
        } else {
            User existUser = userService.findByLogin(user.getLogin());
            if(existUser != null)
                userService.deleteUser(existUser);
            userService.save(user);
        }
        return "redirect:/users";
    }
}
