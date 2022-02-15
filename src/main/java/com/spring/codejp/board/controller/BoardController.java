package com.spring.codejp.board.controller;

import com.spring.codejp.board.dto.BoardSaveRequestDto;
import com.spring.codejp.board.service.BoardService;
import com.spring.codejp.security.UserPrincipal;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/problem/{problemId}/boards")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("")
    public ResponseEntity<String> saveBoard(@RequestBody BoardSaveRequestDto requestDto,
                                            @AuthenticationPrincipal UserPrincipal userPrincipal,
                                            @PathVariable Long problemId) throws NotFoundException {
        boardService.saveBoard(requestDto, problemId, userPrincipal.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<String> updateBoard(@RequestBody BoardSaveRequestDto requestDto,
                                            @AuthenticationPrincipal UserPrincipal userPrincipal,
                                            @PathVariable Long problemId,
                                              @PathVariable Long boardId) throws NotFoundException {
        boardService.updateBoard(requestDto, problemId, userPrincipal.getUsername(), boardId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<String> saveBoard(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                            @PathVariable Long problemId,
                                            @PathVariable Long boardId) throws NotFoundException {
        boardService.deleteBoard(problemId, userPrincipal.getUsername(), boardId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
