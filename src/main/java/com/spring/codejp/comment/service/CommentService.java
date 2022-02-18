package com.spring.codejp.comment.service;

import com.spring.codejp.board.domain.Board;
import com.spring.codejp.board.repository.BoardRepository;
import com.spring.codejp.comment.domain.Comment;
import com.spring.codejp.comment.dto.CommentSaveRequestDto;
import com.spring.codejp.comment.repository.CommentRepository;
import com.spring.codejp.user.domain.User;
import com.spring.codejp.user.repository.UserRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    // 처음 댓글을 추가한다.
    public void saveComment(CommentSaveRequestDto requestDto, String email, Long boardId) throws NotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("해당 유저를 찾을 수 없습니다."));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException("해당 게시글을 찾을 수 없습니다."));

        Comment comment = Comment.createComment(user, board, null, requestDto.getContent());
        commentRepository.save(comment);
    }

    // 대댓글의 추가한다.
    public void saveChildComment(CommentSaveRequestDto requestDto, String email, Long boardId, Long commentId) throws NotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("해당 유저를 찾을 수 없습니다."));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException("해당 게시글을 찾을 수 없습니다."));

        Comment parent = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("해당 댓글을 찾을 수 없습니다."));

        Comment comment = Comment.createComment(user, board, parent, requestDto.getContent());
        commentRepository.save(comment);
    }

    // 댓글을 수정한다.
    public void updateComment(CommentSaveRequestDto requestDto, String email, Long boardId, Long commentId) throws NotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("해당 유저를 찾을 수 없습니다."));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("해당 댓글을 찾을 수 없습니다."));

        if(comment.getUser().getEmail() != user.getEmail()) {
            throw new NotFoundException("해당 권한이 없습니다.");
        }

        comment = comment.updateComment(requestDto.getContent());
        commentRepository.save(comment);
    }

    // 댓글을 삭제한다.
    public void deleteComment(String email, Long boardId, Long commentId) throws NotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("해당 유저를 찾을 수 없습니다."));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("해당 댓글을 찾을 수 없습니다."));

        if(comment.getUser().getEmail() != user.getEmail()) {
            throw new NotFoundException("해당 권한이 없습니다.");
        }

        comment.remove();
        List<Comment> removableCommentList = comment.findRemovableList();
        commentRepository.deleteAll(removableCommentList);
    }

    // 게시판의 댓글을 가져온다
    public List<Comment> getComments(Long boardId) throws NotFoundException {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException("해당 게시글을 찾을 수 없습니다."));

        List<Comment> comments = commentRepository.findAllByBoard(board);

        return comments;
    }
}
