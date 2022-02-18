package com.spring.codejp.comment.repository;

import com.spring.codejp.board.domain.Board;
import com.spring.codejp.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByBoard(Board board);
}
