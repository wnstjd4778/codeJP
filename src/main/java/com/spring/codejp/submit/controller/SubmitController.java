package com.spring.codejp.submit.controller;

import com.spring.codejp.security.UserPrincipal;
import com.spring.codejp.submit.domain.Submit;
import com.spring.codejp.submit.dto.SubmitRequestDto;
import com.spring.codejp.submit.service.SubmitService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("problems/{problemId}/submits")
public class SubmitController {

    private final SubmitService submitService;

    @PostMapping("")
    public ResponseEntity<String> submitCode(@RequestBody SubmitRequestDto requestDto,
                                             @PathVariable Long problemId,
                                             @AuthenticationPrincipal UserPrincipal userPrincipal) throws NotFoundException {
        log.info(requestDto.getCode() + requestDto.getLanguage());
        submitService.submitCode(requestDto, problemId, userPrincipal.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
