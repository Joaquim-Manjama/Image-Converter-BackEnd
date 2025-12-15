package Joaquim_Manjama.Image_Converter.Backend.service;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class ImageFileConversionService {

    private final String[] SUPPORTED_TYPES = {"bmp", "gif", "jpg",
            "png", "tiff", "webp", "jpeg"};

    private final String[] TYPE_DESCRIPTIONS = { "Uncompressed",
            "Animation Support","Small file size (JPEG)",
            "Lossless, transparent", "Tag Image File", "Modern Image Format "  };


    public ResponseEntity<byte[]> convert(MultipartFile file,
                                          String outputFormat) throws IOException {

        System.out.println("Converting " + file.getOriginalFilename()
                + " to converted-image." + outputFormat + "...");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Thumbnails.of(file.getInputStream())
                .scale(1.0)              // keep original size
                .outputFormat(outputFormat)     // convert to Desired format
                .toOutputStream(outputStream);

        MediaType mediaType = MediaType.parseMediaType("image/" + outputFormat);

        System.out.println("Conversion Completed: Returning Image Blob...");
        System.out.println("--------------------------------------------\n\n");

        return ResponseEntity.ok()
                    .contentType(mediaType)
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"converted-image."+ outputFormat + "\""
                    )
                    .body(outputStream.toByteArray());
    }

    public ResponseEntity<List<String>> getSupportedTypes() {
        String[] supportedTypes = new String[SUPPORTED_TYPES.length - 1];

        // REMOVING THE JPEG (EXTRA)
        for (int i = 0; i < SUPPORTED_TYPES.length - 1; i++) {
            supportedTypes[i] = SUPPORTED_TYPES[i];
        }

        return ResponseEntity.ok().body(Arrays.stream(supportedTypes).toList());
    }

    public ResponseEntity<List<String>> getTypesDescriptions() {
        return ResponseEntity.ok().body(Arrays.stream(TYPE_DESCRIPTIONS).toList());
    }
}
