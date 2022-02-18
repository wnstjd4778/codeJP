package com.spring.codejp.comment.controller;

import com.spring.codejp.comment.domain.Comment;
import com.spring.codejp.comment.dto.CommentSaveRequestDto;
import com.spring.codejp.comment.repository.CommentRepository;
import com.spring.codejp.comment.service.CommentService;
import com.spring.codejp.security.UserPrincipal;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/boards/{boardId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("")
    public ResponseEntity<String> saveComment(@RequestBody CommentSaveRequestDto requestDto,
                                              @AuthenticationPrincipal UserPrincipal userPrincipal,
                                              @PathVariable Long boardId) throws NotFoundException {
        commentService.saveComment(requestDto, userPrincipal.getUsername(), boardId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{commentId}")
    public ResponseEntity<String> saveChildComment(@RequestBody CommentSaveRequestDto requestDto,
                                                   @AuthenticationPrincipal UserPrincipal userPrincipal,
                                                   @PathVariable Long commentId,
                                                   @PathVariable Long boardId) throws NotFoundException {
        commentService.saveChildComment(requestDto, userPrincipal.getUsername(), boardId, commentId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<String> updateComment(@RequestBody CommentSaveRequestDto requestDto,
                                                   @AuthenticationPrincipal UserPrincipal userPrincipal,
                                                   @PathVariable Long commentId,
                                                   @PathVariable Long boardId) throws NotFoundException {
        commentService.updateComment(requestDto, userPrincipal.getUsername(), boardId, commentId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> saveChildComment(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                   @PathVariable Long commentId,
                                                   @PathVariable Long boardId) throws NotFoundException {
        commentService.deleteComment(userPrincipal.getUsername(), boardId, commentId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("")
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long boardId) throws NotFoundException {
        List<Comment> comments = commentService.getComments(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(comments);
    }

}
