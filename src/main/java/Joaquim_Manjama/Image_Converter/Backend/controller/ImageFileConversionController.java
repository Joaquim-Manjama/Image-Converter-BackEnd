package Joaquim_Manjama.Image_Converter.Backend.controller;

import Joaquim_Manjama.Image_Converter.Backend.service.ImageFileConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("convert-image")
public class ImageFileConversionController {

    private ImageFileConversionService service;

    public ImageFileConversionController(ImageFileConversionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<byte[]> save(@RequestParam("image") MultipartFile file, @RequestParam("format") String outputFormat) throws IOException {
        System.out.println("Server Received Request");
        return service.convert(file, outputFormat);
    }
}
