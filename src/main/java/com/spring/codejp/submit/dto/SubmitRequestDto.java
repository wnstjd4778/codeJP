package com.spring.codejp.submit.dto;

import com.spring.codejp.submit.domain.Language;
import lombok.Data;

@Data
public class SubmitRequestDto {

    private Language language;

    private String code;
}
