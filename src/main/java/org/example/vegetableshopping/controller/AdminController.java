package org.example.vegetableshopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vegetable-shopping")
public class AdminController {

    @GetMapping("/admin/index")
    public String getIndex() {
        return "admin/index";
    }

}
