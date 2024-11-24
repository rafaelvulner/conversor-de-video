package br.com.conversordevideo.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ErrorSystemicResponse {

    private String message;
    private String code;
}
