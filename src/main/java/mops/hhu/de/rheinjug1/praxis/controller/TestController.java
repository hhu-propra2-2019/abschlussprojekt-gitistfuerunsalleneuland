package mops.hhu.de.rheinjug1.praxis.controller;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Path;
import java.util.*;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jlefebure.spring.boot.minio.MinioException;
import com.jlefebure.spring.boot.minio.MinioService;


import io.minio.messages.Item;

@SuppressWarnings("PMD")
@RestController
@RequestMapping("/files")
public class TestController {

    @Autowired
    private MinioService minioService;


    @GetMapping("/")
    public List<Item> testMinio() throws MinioException {
    	 System.out.println("test");
        return minioService.list();
 
    }

    @GetMapping("/{object}")
    public void getObject(@PathVariable("object") String object, HttpServletResponse response) throws MinioException, IOException {
        InputStream inputStream = minioService.get(Path.of(object));
        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);

        // Set the content type and attachment header.
        response.addHeader("Content-disposition", "attachment;filename=" + object);
        response.setContentType(URLConnection.guessContentTypeFromName(object));

        // Copy the stream to the response's output stream.
        IOUtils.copy(inputStream, response.getOutputStream());
        response.flushBuffer();
        System.out.println("Hello World.");
    }
}
