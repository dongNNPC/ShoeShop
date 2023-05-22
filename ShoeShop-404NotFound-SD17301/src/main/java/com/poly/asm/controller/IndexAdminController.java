package com.poly.asm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shoeshop/admin")
public class IndexAdminController {
    @RequestMapping("/index")
    public String indexAdmin() {

        return "/admin/index";
    }

    @RequestMapping("/ui-user")
    public String listUIUserAdmin() {

        return "/admin/views/ui-user";
    }

    @RequestMapping("/ui-brand")
    public String listUIBrandAdmin() {

        return "/admin/views/ui-brand";
    }

    @RequestMapping("/ui-category")
    public String listUICategoryAdmin() {

        return "/admin/views/ui-category";
    }

    @RequestMapping("/ui-product")
    public String listUIProductAdmin() {

        return "/admin/views/ui-product";
    }

    @RequestMapping("/list-brand")
    public String listBardAdmin() {

        return "/admin/views/list-brand";
    }

    @RequestMapping("/list-category")
    public String listCategoryAdmin() {

        return "/admin/views/list-category";
    }

    @RequestMapping("/list-product")
    public String listProductAdmin() {

        return "/admin/views/list-product";
    }

    @RequestMapping("/list-user")
    public String listUserAdmin() {

        return "/admin/views/list-user";
    }

}
