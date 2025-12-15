package Joaquim_Manjama.Image_Converter.Backend.controller;

import Joaquim_Manjama.Image_Converter.Backend.service.ImageFileConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class ImageFileConversionController {

    private ImageFileConversionService service;

    public ImageFileConversionController(ImageFileConversionService service) {
        this.service = service;
    }

    @PostMapping("/convert-image")
    public ResponseEntity<byte[]> save(@RequestParam("image") MultipartFile file, @RequestParam("format") String outputFormat) throws IOException {
        System.out.println("Server Received Request");

        if (file.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return this.service.convert(file, outputFormat);
    }

    @GetMapping("/supported-types")
    public ResponseEntity<List<String>> getSupportedTypes() throws IOException {
        return this.service.getSupportedTypes();
    }

    @GetMapping("/type-descriptions")
    public ResponseEntity<List<String>> getTypeDescriptions() throws IOException {
        return this.service.getTypesDescriptions();
    }

}
