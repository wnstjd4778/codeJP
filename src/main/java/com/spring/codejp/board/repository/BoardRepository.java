package com.spring.codejp.board.repository;

import com.spring.codejp.Problem.domain.Problem;
import com.spring.codejp.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAllByProblem(Problem problem);
}
