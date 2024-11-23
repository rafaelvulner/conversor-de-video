package br.com.conversordevideo.controller;

import br.com.conversordevideo.service.VideoConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    private final VideoConversionService videoConversionService;

    public VideoController(VideoConversionService videoConversionService) {
        this.videoConversionService = videoConversionService;
    }

    @PostMapping("/convert")
    public ResponseEntity<?> convertVideo(@RequestParam("file") MultipartFile file) {
        try {

            File tempFile = File.createTempFile("input", ".mov");
            file.transferTo(tempFile);

            String outputPath = videoConversionService.convertMovToMp4(tempFile.getAbsolutePath());

            return ResponseEntity.ok("Vídeo convertido com sucesso: " + outputPath);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao converter o vídeo: " + e.getMessage());
        }
    }
}
