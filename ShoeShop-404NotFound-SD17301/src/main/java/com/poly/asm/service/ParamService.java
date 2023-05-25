 package com.poly.asm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ParamService {
    @Autowired
    HttpServletRequest request;

    
    public String getString(String name, String defaultValue) {
    	//lấy giá trị từ String name
        String value = request.getParameter(name);
        //nếu tồn tại thì trả về name đó còn không tồn tại mặc định defaultValue
        return value != null ? value : defaultValue;
    }

    
    public int getInt(String name, int defaultValue) {
    	//lấy giá trị kiểu số từ String name
        String value = request.getParameter(name);
        //nếu tồn tại thì trả về name đó còn không tồn tại mặc định defaultValue
        return value != null ? Integer.parseInt(value) : defaultValue;
    }

    
    public double getDouble(String name, double defaultValue) {
    	//lấy giá trị kiểu double từ String name
        String value = request.getParameter(name);
        //nếu tồn tại thì trả về name đó còn không tồn tại mặc định defaultValue
        return value != null ? Double.parseDouble(value) : defaultValue;
    }

    
    public boolean getBoolean(String name, boolean defaultValue) {
    	//lấy giá trị kiểu true hoặc false từ String name
        String value = request.getParameter(name);
        //nếu tồn tại thì trả về name đó còn không tồn tại mặc định defaultValue
        return value != null ? Boolean.parseBoolean(value) : defaultValue;
    }

    public Date getDate(String name, String pattern) {
    	//lấy giá trị từ String name 
        String value = request.getParameter(name);
        //nếu khác null
        if (value != null) {
            try {
            	//lấy giá trị từ pattern || nếu đúng thì trả về  true và ngược lại
                SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
                return dateFormat.parse(value);
            } catch (ParseException e) {
                throw new RuntimeException("Lỗi sai định dạng thời gian");
            }
        }
        return null;
    }

   
    public File save(MultipartFile file, String path) {
    	
    	//kiểm tra file tồn tại hay chưa
    	
        if (!file.isEmpty()) {
            try {
            	//tạo đường dẫn tuyệt đối
                String webRootPath = request.getSession().getServletContext().getRealPath("/");
                String fullPath = webRootPath + path;
                File directory = new File(fullPath);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                //tạo file lưu trữ
                File savedFile = new File(fullPath, file.getOriginalFilename());
                file.transferTo(savedFile);
                //trả về đối tượng file
                return savedFile;
            } catch (IOException e) {
                throw new RuntimeException("Lỗi lưu file");
            }
        }
        return null;
    }
}
