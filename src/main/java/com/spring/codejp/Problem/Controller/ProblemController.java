package com.spring.codejp.Problem.Controller;

import com.spring.codejp.Problem.Service.ProblemService;
import com.spring.codejp.Problem.domain.Problem;
import com.spring.codejp.Problem.dto.ProblemInsertRequestDto;
import com.spring.codejp.security.UserPrincipal;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/problem")
public class ProblemController {

    private final ProblemService problemService;

    @PostMapping("")
    public ResponseEntity<String> createProblem(@RequestBody ProblemInsertRequestDto requestDto,
                                                @AuthenticationPrincipal UserPrincipal userPrincipal) throws NotFoundException {
        log.info("{}", requestDto);
        log.info("{}입니다", userPrincipal);
        problemService.createProblem(requestDto, userPrincipal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{problemId}")
    public ResponseEntity<String> updateProblem(@RequestBody ProblemInsertRequestDto requestDto,
                                                @PathVariable Long problemId,
                                                @AuthenticationPrincipal UserPrincipal userPrincipal) throws NotFoundException {
        problemService.updateProblem(requestDto, problemId, userPrincipal.getName());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("{problemId}")
    public ResponseEntity<String> deleteProblem(@PathVariable Long problemId
                                                ,@AuthenticationPrincipal UserPrincipal userPrincipal) throws NotFoundException {
        problemService.deleteProblem(problemId, userPrincipal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("")
    public ResponseEntity<List<Problem>> getProblems() throws NotFoundException {
        List<Problem> problems = problemService.getProblems();
        return ResponseEntity.status(HttpStatus.OK).body(problems);
    }

    @GetMapping("/{problemId}")
    public ResponseEntity<Problem> getProblem(@PathVariable Long problemId) throws NotFoundException {
        Problem problems = problemService.getProblem(problemId);
        return ResponseEntity.status(HttpStatus.OK).body(problems);
    }
}
