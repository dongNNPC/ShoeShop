package com.poly.asm.QRcode;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.data.jpa.domain.JpaSort.Path;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.poly.asm.model.Invoice;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/shoeshop")
public class qrController {

	@Autowired
	ServletContext context;
	
	@GetMapping("/pr/{id}")
	 public void generateQRCode(@PathVariable("id") String id, HttpServletResponse response) throws IOException, WriterException {
        // Lấy URL của trang chi tiết hóa đơn
		Invoice invoice = new Invoice();
		
		 String url = context.getContextPath() + "http://localhost:8088/shoeshop/viewpay/" + id;
		 //+ invoice.getId()

		 System.out.println(url);
        // Tạo mã QR
        BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, 200, 200);

        // Chuyển đổi ma trận bit thành bitmap
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", out);
        byte[] data = out.toByteArray();

        // Trả về mã QR dưới dạng ảnh PNG
        response.setContentType("image/png");
        response.getOutputStream().write(data);
        response.getOutputStream().flush();
    }
//	<img src="@{/shoeshop/pr/{id}(id=${id})}" />
	
//	@GetMapping("/pr/{id}")
//	public ResponseEntity<Resource> generateQRCode(@PathVariable("id") String id) throws IOException, WriterException {
//	    Invoice invoice = new Invoice();
//	    String url = context.getContextPath() + "http://localhost:8088/shoeshop/viewpay/" + id;
//	    
//	    BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, 200, 200);
//	    BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
//
//	    // Lấy đường dẫn tuyệt đối của thư mục "download"
//	    String downloadDir = System.getProperty("user.dir") + "/download";
//
//	    // Tạo thư mục "download" nếu chưa tồn tại
//	    File downloadDirectory = new File(downloadDir);
//	    if (!downloadDirectory.exists()) {
//	        downloadDirectory.mkdirs();
//	    }
//
//	    // Tạo tên tệp tin và đường dẫn tuyệt đối của tệp tin
//	    String fileName = "qrcode_" + id + ".png";
//	    String filePath = downloadDir + "/" + fileName;
//
//	    // Lưu tấm hình mã QR vào thư mục "download"
//	    File outputFile = new File(filePath);
//	    ImageIO.write(qrImage, "png", outputFile);
//
//	    // Tạo đối tượng Resource từ tệp tin đã lưu
//	    Path file = (Path) Paths.get(filePath);
//	    Resource resource = (Resource) new UrlResource(((java.nio.file.Path) file).toUri());
//
//	    // Thiết lập các header phù hợp và trả về resource làm phản hồi
//	    return ResponseEntity.ok()
//	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
//	            .body(resource);
//	}
//	
}
