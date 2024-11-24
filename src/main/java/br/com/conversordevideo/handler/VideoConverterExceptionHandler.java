package br.com.conversordevideo.handler;

import br.com.conversordevideo.exception.VideoConversionException;
import br.com.conversordevideo.exception.VideoSystemicException;
import br.com.conversordevideo.model.dto.response.ErrorApiResponse;
import br.com.conversordevideo.model.dto.response.ErrorSystemicResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class VideoConverterExceptionHandler {

    @ExceptionHandler(VideoConversionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorApiResponse> handleVideoConversionException(VideoConversionException ex) {
        return ResponseEntity.badRequest().body(ErrorApiResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.toString())
                .build());
    }

    @ExceptionHandler(VideoSystemicException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorSystemicResponse> handleVideoSystemicException(VideoConversionException ex) {
        return ResponseEntity.badRequest().body(ErrorSystemicResponse.builder()
                .message("Process FFmpeg failed, please check logs and official documentation")
                .code(ex.getMessage())
                .build());
    }
}
