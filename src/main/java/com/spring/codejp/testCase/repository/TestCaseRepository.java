package com.spring.codejp.testCase.repository;

import com.spring.codejp.Problem.domain.Problem;
import com.spring.codejp.testCase.domain.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestCaseRepository extends JpaRepository<TestCase, Long> {

    List<TestCase> findAllByProblem(Problem problem);
}
