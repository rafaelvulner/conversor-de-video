package br.com.conversordevideo.controller;

import br.com.conversordevideo.model.dto.response.VideoConverterDto;
import br.com.conversordevideo.service.VideoConversionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/videos")
@AllArgsConstructor
public class VideoController {

    private final VideoConversionService videoConversionService;

    @PostMapping("/convert")
    public ResponseEntity<VideoConverterDto> convertVideo(@RequestParam("file") MultipartFile file) {
            return ResponseEntity.ok(this.videoConversionService.convertMovToMp4(file));
    }
}
