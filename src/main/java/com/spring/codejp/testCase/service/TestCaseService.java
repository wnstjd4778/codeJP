package com.spring.codejp.testCase.service;

import com.spring.codejp.Problem.domain.Problem;
import com.spring.codejp.Problem.repository.ProblemRepository;
import com.spring.codejp.testCase.domain.TestCase;
import com.spring.codejp.testCase.dto.TestCaseInsertRequestDto;
import com.spring.codejp.testCase.dto.TestCaseUpdateRequestDto;
import com.spring.codejp.testCase.repository.TestCaseRepository;
import com.spring.codejp.user.domain.User;
import com.spring.codejp.user.repository.UserRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestCaseService {

    private final UserRepository userRepository;
    private final ProblemRepository problemRepository;
    private final TestCaseRepository testCaseRepository;

    // 테스트 케이스르 추가한다.
    public void insertTestCase(String email, TestCaseInsertRequestDto requestDto, Long problemId) throws NotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("해당 계정을 찾을 수 없습니다."));

        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new NotFoundException("해당 문제를 찾을 수 없습니다."));

        if(problem.getUser().getId() == user.getId()) {
            TestCase testCase = TestCase.createTestCase(requestDto.getExpectedData(), requestDto.getParameter(), problem);
            testCaseRepository.save(testCase);
        }
    }

    // 테스트 케이스르 수정한다.
    public void updateTestCase(String email, TestCaseUpdateRequestDto requestDto, Long problemId, Long testCaseId) throws NotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("해당 계정을 찾을 수 없습니다."));

        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new NotFoundException("해당 문제를 찾을 수 없습니다."));

        if(problem.getUser().getId() == user.getId()) {
            TestCase testCase = testCaseRepository.findById(testCaseId)
                            .orElseThrow(() -> new NotFoundException("해당 테스트케이스를 찾을 수 없습니다."));
            testCase = testCase.updateTestCase(requestDto.getExpectedData(), requestDto.getParameter());
            testCaseRepository.save(testCase);
        }
    }

    // 테스트 케이스르 삭제한다.
    public void deleteTestCase(String email, Long problemId, Long testCaseId) throws NotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("해당 계정을 찾을 수 없습니다."));

        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new NotFoundException("해당 문제를 찾을 수 없습니다."));

        if(problem.getUser().getId() == user.getId()) {
            TestCase testCase = testCaseRepository.findById(testCaseId)
                    .orElseThrow(() -> new NotFoundException("해당 테스트케이스를 찾을 수 없습니다."));
            if(testCase.getProblem().getId() == problemId) {
                testCaseRepository.delete(testCase);
            }
        }
    }

}
