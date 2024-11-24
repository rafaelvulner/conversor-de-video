package br.com.conversordevideo.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ErrorApiResponse {

    private String message;
    private String status;
}
