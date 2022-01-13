package com.spring.codejp.compile.dto;

import lombok.Data;

@Data
public class CompileRequestDto {

    private String input;
    private String sourceCode;
    private String language;
}
