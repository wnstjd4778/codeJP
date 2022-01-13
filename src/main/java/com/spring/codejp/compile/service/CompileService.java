package com.spring.codejp.compile.service;

import com.spring.codejp.compile.dto.CompileRequestDto;
import com.spring.codejp.compile.dto.CompileResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class CompileService {

    // restTemplate을 통해 컴파일 서버에 컴파일을 요청한다.
    public CompileResponseDto compileLanguage(CompileRequestDto requestDto) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("input", requestDto.getInput());
        map.add("sourceCode", requestDto.getSourceCode());
        map.add("language", requestDto.getLanguage());
        CompileResponseDto compileResponseDto
                = restTemplate.postForObject("http://localhost:8081/compile/" + requestDto.getLanguage(), map, CompileResponseDto.class);
        return compileResponseDto;
    }
}
