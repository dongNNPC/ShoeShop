package com.poly.asm.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.poly.asm.dao.ProductRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class GlobalInterceptor implements HandlerInterceptor {
    @Autowired
   ProductRepository dao;

    //lab 7 bài 4
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        String decoratedUri = decorateUri(uri);
        request.setAttribute("decoratedUri", decoratedUri);
        
        if (uri.equalsIgnoreCase("/shoeshop")) {
            String redirectUri = "/shoeshop/index";
            response.sendRedirect(redirectUri);
            return false; // Ngăn chặn việc xử lý tiếp theo
        }
        
      
        
        return true;
    }


    private String decorateUri(String uri) {
        String[] segments = uri.split("/");

        StringBuilder decoratedUri = new StringBuilder();
        for (String segment : segments) {
            decoratedUri.append("");
            if (segment.equalsIgnoreCase("shoeshop")) {
                decoratedUri.append("<span style=\" font-size: 15px;font-weight: bold; text-transform: uppercase;\">");
                decoratedUri.append(segment);
                decoratedUri.append("</span style=\"font-size: 10px;color: rgb(126, 132, 137);\">");
            } else {
                decoratedUri.append(segment);
            }
            decoratedUri.append(" / ");
        }

        return decoratedUri.toString().trim();
    }





    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse res,
            Object handler, ModelAndView mv) throws Exception {
        req.setAttribute("product", dao.findAll());
    }
}
