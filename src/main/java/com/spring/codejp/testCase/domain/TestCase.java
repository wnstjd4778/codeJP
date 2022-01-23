package com.spring.codejp.testCase.domain;

import com.spring.codejp.Problem.domain.Problem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "test_case")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TestCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_case_id")
    private Long id; // 테스트 케이스 고유 번호

    @Column(name = "test_case_expected_data")
    private String expectedData; // 테스트 케이스 예상 정답

    @Column(name = "test_case_parameter")
    private String parameter; // 테스트 케이스 입력값

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem; // 문제 고유 번호

    @Builder
    public TestCase(String expectedData, String parameter, Problem problem) {
        this.expectedData = expectedData;
        this.parameter = parameter;
        this.problem = problem;
    }

    public static TestCase createTestCase(String expectedData, String parameter, Problem problem) {
        return TestCase.builder()
                .expectedData(expectedData)
                .parameter(parameter)
                .problem(problem)
                .build();
    }

    public TestCase updateTestCase(String expectedData, String parameter) {
        this.expectedData = expectedData;
        this.parameter = parameter;
        return this;
    }
}
