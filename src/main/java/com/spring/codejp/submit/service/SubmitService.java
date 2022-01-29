package com.spring.codejp.submit.service;

import com.spring.codejp.Problem.domain.Problem;
import com.spring.codejp.Problem.repository.ProblemRepository;
import com.spring.codejp.compile.dto.CompileResponseDto;
import com.spring.codejp.submit.dto.SubmitRequestDto;
import com.spring.codejp.testCase.domain.TestCase;
import com.spring.codejp.testCase.repository.TestCaseRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.HttpHead;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmitService {

    private final TestCaseRepository testCaseRepository;

    private final ProblemRepository problemRepository;

    // 문제를 제출한다.
    public void submitCode(SubmitRequestDto requestDto, Long problemId) throws NotFoundException {

        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new NotFoundException("문제를 찾을 수 없습니다."));

        List<TestCase> testCases = testCaseRepository.findAllByProblem(problem);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        List<CompileResponseDto> compileResponses = new LinkedList<>();
        String serverUrl = "http://localhost:8081/compile/";
        for(int i = 0; i < testCases.size(); i++) {
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            File input = new File(testCases.get(i).getParameter());
            File output = new File(testCases.get(i).getExpectedData());
            body.add("input", new FileSystemResource(input));
            body.add("output", new FileSystemResource(output));
            // body.add("sourceCode", new FileSystemResource()); 소스 코드를 파일로 만들어야됨
            body.add("language", requestDto.getLanguage());
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            CompileResponseDto compileResponseDto
                    = restTemplate.postForObject( serverUrl + requestDto.getLanguage(), requestEntity, CompileResponseDto.class);
            compileResponses.add(compileResponseDto);
            // 데이터 가공해서 내보내기(제대로 되는지 확인!!!)
        }

        
    }
}
