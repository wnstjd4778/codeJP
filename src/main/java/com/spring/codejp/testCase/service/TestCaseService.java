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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestCaseService {

    private final UserRepository userRepository;
    private final ProblemRepository problemRepository;
    private final TestCaseRepository testCaseRepository;

    // 테스트 케이스르 추가한다.
    public void insertTestCase(String email, TestCaseInsertRequestDto requestDto, Long problemId) throws NotFoundException, IOException {

        log.info(requestDto.getExpectedData() + "   " + requestDto.getParameter());
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("해당 계정을 찾을 수 없습니다."));

        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new NotFoundException("해당 문제를 찾을 수 없습니다."));

        if(problem.getUser().getId() == user.getId()) {
            UUID random = UUID.randomUUID();
            String inputPath = "C:\\Problems\\" + problemId +  "\\input";
            String outputPath = "C:\\Problems\\" + problemId +  "\\output";
            File inputFolder = new File(inputPath);
            File outputFolder = new File(inputPath);

            // 폴더를 자동으로 생성해야하는데 생성이 안됨
//            if (!inputFolder.exists()) {
//                try{
//                    inputFolder.mkdir(); //폴더 생성합니다.
//                    System.out.println("폴더가 생성되었습니다.");
//                }
//                catch(Exception e){
//                    e.getStackTrace();
//                }
//            }
//            if (!outputFolder.exists()) {
//                try{
//                    outputFolder.mkdir(); //폴더 생성합니다.
//                    System.out.println("폴더가 생성되었습니다.");
//                }
//                catch(Exception e){
//                    e.getStackTrace();
//                }
//            }
            FileOutputStream input = new FileOutputStream(inputPath + "\\" + random + ".txt");
            FileOutputStream output = new FileOutputStream(outputPath + "\\" + random + ".txt");
            try {
                input.write(requestDto.getParameter().getBytes(StandardCharsets.UTF_8));
                output.write(requestDto.getExpectedData().getBytes(StandardCharsets.UTF_8));
            } catch (Exception e) {
                e.getStackTrace();
            }
            input.close();
            output.close();
            TestCase testCase = TestCase.createTestCase(outputPath + "\\" + random + ".txt", inputPath + "\\" + random + ".txt", problem);
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

    // 테스트 케이스르 수정한다.
    public List<TestCase> getTestCases(Long problemId) throws NotFoundException {

        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new NotFoundException("해당 문제를 찾을 수 없습니다."));

        List<TestCase> testCases = testCaseRepository.findAllByProblem(problem);
        return testCases;
    }
}
