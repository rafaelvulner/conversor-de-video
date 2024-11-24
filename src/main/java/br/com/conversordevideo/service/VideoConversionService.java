package br.com.conversordevideo.service;

import br.com.conversordevideo.exception.VideoConversionException;
import br.com.conversordevideo.exception.VideoSystemicException;
import br.com.conversordevideo.model.dto.response.ErrorSystemicResponse;
import br.com.conversordevideo.model.dto.response.VideoConverterDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class VideoConversionService {

    @Value("${paths.ffmpeg}")
    private String ffmpegPath;

    @Value("${paths.output}")
    private String outputDirectory;

    private static final String OUTPUT_FILE_EXTENSION = ".mp4";

    public VideoConverterDto convertMovToMp4(MultipartFile file) {
        log.info("Converting mov to mp4 to video");

        VideoConverterDto.VideoConverterDtoBuilder converterDtoBuilder = VideoConverterDto.builder();

        File tempFile;
        try {
            tempFile = File.createTempFile("input", ".mov");
            file.transferTo(tempFile);
        } catch (IOException e) {
            throw new VideoConversionException(e.getMessage());
        }
        Path inputPath = Paths.get(tempFile.getAbsolutePath());

        File ffmpeg = new File(this.ffmpegPath);

        List<String> parameters = new ArrayList<>();
        parameters.add(ffmpeg.getAbsolutePath());
        parameters.add("-i");
        parameters.add(inputPath.toString());
        parameters.add("-c:v");
        parameters.add("libx264");
        parameters.add("-c:a");
        parameters.add("aac");
        parameters.add("-strict");
        parameters.add("experimental");
        parameters.add("-preset");
        parameters.add("fast");

        String outputFilePath = Paths.get(outputDirectory, UUID.randomUUID().toString().concat(OUTPUT_FILE_EXTENSION)).toString();
        parameters.add(outputFilePath);


        ProcessBuilder processBuilder = new ProcessBuilder(parameters);
        processBuilder.redirectErrorStream(true);

        Process process;
        try {
            process = processBuilder.start();

            List<String> logs = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    logs.add(line);
                    log.info("FFmpeg Output: {}", line);
                }
            }

            converterDtoBuilder.logs(logs);

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                converterDtoBuilder.errors(List.of(ErrorSystemicResponse.builder()
                        .message("Process FFmpeg failed, please check logs and official documentation")
                        .code(String.valueOf(exitCode))
                        .build()));
                throw new VideoConversionException(String.valueOf(exitCode));
            }

        } catch (IOException | InterruptedException e) {
            throw new VideoSystemicException(e.getMessage());
        }

        log.info("Video converted to mp4 successfully!");
        return converterDtoBuilder
                .convertedVideo(outputFilePath)
                .build();
    }

}

