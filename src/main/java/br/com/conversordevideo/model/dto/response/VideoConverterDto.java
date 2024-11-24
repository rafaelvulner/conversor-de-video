package br.com.conversordevideo.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class VideoConverterDto {

    private List<String> logs;
    private List<ErrorSystemicResponse> errors;
    private String convertedVideo;
}
