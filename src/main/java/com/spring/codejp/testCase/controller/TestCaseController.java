package com.spring.codejp.testCase.controller;

import com.spring.codejp.security.UserPrincipal;
import com.spring.codejp.testCase.dto.TestCaseInsertRequestDto;
import com.spring.codejp.testCase.dto.TestCaseUpdateRequestDto;
import com.spring.codejp.testCase.service.TestCaseService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test-case")
public class TestCaseController {

    private final TestCaseService testCaseService;

    @PostMapping("/{problemId}")
    public ResponseEntity<String> insertTestCase(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                 @RequestBody TestCaseInsertRequestDto testCaseInsertRequestDto,
                                                 @PathVariable Long problemId) throws NotFoundException {
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
}
