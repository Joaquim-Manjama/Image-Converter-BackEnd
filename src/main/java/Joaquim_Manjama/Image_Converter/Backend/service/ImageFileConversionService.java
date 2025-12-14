package Joaquim_Manjama.Image_Converter.Backend.service;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;

@Service
public class ImageFileConversionService {

    private static final String[] SUPPORTED_TYPES = {"jpeg", "png", "gif", "bmp", "wbmp"};

    public ResponseEntity<byte[]> convert(MultipartFile file, String outputFormat) throws IOException {
        if (!isSupportedType(file.getContentType())) {
            System.out.println("Input File Type Not Supported");
            return new ResponseEntity<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }

        System.out.println("Converting " + file.getOriginalFilename() + " to converted-image." + outputFormat + "...");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Thumbnails.of(file.getInputStream())
                .scale(1.0)              // keep original size
                .outputFormat(outputFormat)     // convert to Desired format
                .toOutputStream(outputStream);

        System.out.println("Conversion Completed: Returning Image Blob...");

        switch (outputFormat) {
            case "jpeg":
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .header(
                                HttpHeaders.CONTENT_DISPOSITION,
                                "attachment; filename=\"converted-image."+ outputFormat + "\""
                        )
                        .body(outputStream.toByteArray());

            case "gif":
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_GIF)
                        .header(
                                HttpHeaders.CONTENT_DISPOSITION,
                                "attachment; filename=\"converted-image."+ outputFormat + "\""
                        )
                        .body(outputStream.toByteArray());

            default:
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_PNG)
                        .header(
                                HttpHeaders.CONTENT_DISPOSITION,
                                "attachment; filename=\"converted-image."+ outputFormat + "\""
                        )
                        .body(outputStream.toByteArray());
        }

    }

    private boolean isSupportedType(String type) {
        String input = type.replace("image/", "");

        for (String supportedType : SUPPORTED_TYPES) {
            if (supportedType.equals(input)) {
                return true;
            }
        }
        return false;
    }
}
