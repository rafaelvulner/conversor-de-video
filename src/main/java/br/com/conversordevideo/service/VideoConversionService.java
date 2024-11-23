package br.com.conversordevideo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class VideoConversionService {

    public String convertMovToMp4(String inputFilePath) throws IOException, InterruptedException {
        log.info("Converting mov to mp4 to video");

        File fileExit = new File("/Users/rafae/OneDrive/Documentos/Apps/Project-ProductStore/ConversorDeVideo/videos/output.mp4");
        Path inputPath = Paths.get(inputFilePath);
        Path outputPath = Paths.get( fileExit.getAbsolutePath());

        File ffmpeg = new File("/ProgramData/chocolatey/bin/ffmpeg.exe");

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
        parameters.add( "C:\\videos\\output.mp4");


        ProcessBuilder processBuilder = new ProcessBuilder(parameters);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                log.info("FFmpeg Output: {}", line);
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Processo FFmpeg falhou com código de saída: " + exitCode);
        }

        log.info("Conversão concluída com sucesso!");
        return outputPath.toString();
    }
}

