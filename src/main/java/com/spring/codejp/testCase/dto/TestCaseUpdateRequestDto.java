package com.spring.codejp.testCase.dto;

import lombok.Data;

@Data
public class TestCaseUpdateRequestDto {

    private String expectedData;
    private String parameter;
}
