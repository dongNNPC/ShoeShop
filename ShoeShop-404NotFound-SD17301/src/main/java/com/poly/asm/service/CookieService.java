package com.poly.asm.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CookieService {
    @Autowired HttpServletRequest request;
    @Autowired HttpServletResponse response;
   
    public Cookie get(String name) {
    	//lấy cookie từ request và trả về một mảng
        Cookie[] cookies = request.getCookies();
        //nếu khác null
        if (cookies != null) {
        	//tạo một vòng lặp từ cookie trong danh sách
            for (Cookie cookie : cookies) {
            	//đọc cookie
                if (cookie.getName().equals(name)) {
                	//trả về đối tượng cookie
                    return cookie;
                }
            }
        }
        //khi không tìm thấy cookie sẽ trả về null
        return null;
    }

    
    public String getValue(String name) {
    	//tìm cookie
        Cookie cookie = get(name);
        //nếu khác null
        if (cookie != null) {
        	//trả về chuỗi của cookie đó
            return cookie.getValue();
        }
        //nếu không sẽ trả về rõng
        return "";
    }

   //thêm mới cookie
    public Cookie add(String name, String value, int hours) {
    	//tạo một  cookie mới
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(hours * 3600); // Thời hạn tính bằng giây, nên nhân cho 3600 để chuyển đổi thành giờ
        cookie.setPath("/");//set đường dẫn
        response.addCookie(cookie);//thêm vào cookie
        return cookie;//trả về cookie đó
    }

    //xoá
    public void remove(String name) {
    	//lấy name từ cookie
        Cookie cookie = get(name);
        if (cookie != null) {
        	//set thành chuỗi rỗng
            cookie.setValue("");
            //set thời gian về 0
            cookie.setMaxAge(0);
            // "/" có hiệu lực toàn web
            cookie.setPath("/");
            //chỉnh sữa response
            response.addCookie(cookie);
        }
    }
}

