package com.spring.codejp.testCase.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class TestCaseInsertRequestDto {

    private String expectedData;
    private String parameter;
}
