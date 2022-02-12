package com.spring.codejp.Problem.repository;


import com.spring.codejp.Problem.domain.Problem;
import com.spring.codejp.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

    Optional<Problem> findById(Long problemId);
    List<Problem> findAllByUser(User user);
}
