package com.spring.codejp.submit.service;

import com.spring.codejp.Problem.domain.Problem;
import com.spring.codejp.Problem.repository.ProblemRepository;
import com.spring.codejp.compile.dto.CompileResponseDto;
import com.spring.codejp.submit.dto.SubmitRequestDto;
import com.spring.codejp.testCase.domain.TestCase;
import com.spring.codejp.testCase.repository.TestCaseRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

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
        for(int i = 0; i < testCases.size(); i++) {

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("input", testCases.get(i).getParameter());
            map.add("sourceCode", requestDto.getContent());
            map.add("language", requestDto.getLanguage());
            CompileResponseDto compileResponseDto
                    = restTemplate.postForObject("http://localhost:8081/compile/" + requestDto.getLanguage(), map, CompileResponseDto.class);
        }
    }
}
