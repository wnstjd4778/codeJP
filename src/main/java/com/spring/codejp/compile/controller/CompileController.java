package com.spring.codejp.compile.controller;

import com.spring.codejp.compile.dto.CompileRequestDto;
import com.spring.codejp.compile.dto.CompileResponseDto;
import com.spring.codejp.compile.service.CompileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/compile")
public class CompileController {

    private final CompileService compileService;


    @PostMapping("")
    public ResponseEntity<CompileResponseDto> compileLanguage(@RequestBody CompileRequestDto requestDto) {
        CompileResponseDto compileResponseDto = compileService.compileLanguage(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(compileResponseDto);
    }
}
