package org.example.vegetableshopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vegetable-shopping")
public class UserController {

    @GetMapping("/home")
    public String getHome() {
        return "user/index";
    }

    @GetMapping("/orders")
    public String getOrders() {
        return "user/order";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "user/login";
    }

    @GetMapping("/shop-grid")
    public String getShopGrid() {
        return "user/shop-grid";
    }

    @GetMapping("/register")
    public String getRegister() {
        return "user/register";
    }

    @GetMapping("/products/product-detail/{id}")
    public String getProductDetails() {
        return "user/product-detail";
    }

    @GetMapping("/shopping-cart")
    public String getShoppingCart() {
        return "user/shopping-cart";
    }

    @GetMapping("/blogs/blog-detail/{id}")
    public String getBlogDetail() {
        return "user/blog-detail";
    }

    @GetMapping("/shopping-cart/check-out")
    public String getCheckout() {
        return "user/checkout";
    }

}
