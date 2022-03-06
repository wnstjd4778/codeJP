package com.spring.codejp.testCase.controller;

import com.spring.codejp.security.UserPrincipal;
import com.spring.codejp.testCase.domain.TestCase;
import com.spring.codejp.testCase.dto.TestCaseInsertRequestDto;
import com.spring.codejp.testCase.dto.TestCaseUpdateRequestDto;
import com.spring.codejp.testCase.service.TestCaseService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/testcase")
public class TestCaseController {

    private final TestCaseService testCaseService;

    @PostMapping("/{problemId}")
    public ResponseEntity<String> insertTestCase(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                 @RequestBody TestCaseInsertRequestDto testCaseInsertRequestDto,
                                                 @PathVariable Long problemId) throws NotFoundException, IOException {

        testCaseService.insertTestCase(userPrincipal.getName(), testCaseInsertRequestDto, problemId);
        return ResponseEntity.status(200).build();
    }

    @PutMapping("/{problemId}/{testCaseId}")
    public ResponseEntity<String> updateTestCase(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                 @RequestBody TestCaseUpdateRequestDto requestDto,
                                                 @PathVariable Long problemId,
                                                 @PathVariable Long testCaseId) throws NotFoundException {
        testCaseService.updateTestCase(userPrincipal.getName(), requestDto, problemId, testCaseId);
        return ResponseEntity.status(200).build();
    }

    @DeleteMapping("/{problemId}/{testCaseId}")
    public ResponseEntity<String> updateTestCase(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                 @PathVariable Long problemId,
                                                 @PathVariable Long testCaseId) throws NotFoundException {
        testCaseService.deleteTestCase(userPrincipal.getName(), problemId, testCaseId);
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/{problemId}")
    public ResponseEntity<List<TestCase>> getTestCases(@PathVariable Long problemId) throws NotFoundException, IOException {
        List<TestCase> testCases = testCaseService.getTestCases(problemId);
        return ResponseEntity.status(200).body(testCases);
    }
}
