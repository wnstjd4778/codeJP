package com.spring.codejp.submit.domain;

import com.spring.codejp.Problem.domain.Problem;
import com.spring.codejp.user.domain.User;
import com.spring.codejp.util.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "submit")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Submit extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "submit_id")
    private Long id;

    @Column(name = "submit_sourceCode")
    private String code;

    @Column(name = "submit_status")
    private String status;

    @Column(name = "submit_is_answer")
    private boolean isAnswer;

    @Column(name = "submit_language")
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @Builder
    public Submit(String code, String status, boolean isAnswer, User user, Problem problem, Language language) {
        this.code = code;
        this.status = status;
        this.isAnswer = isAnswer;
        this.user = user;
        this.problem = problem;
        this.language = language;
    }

    public static Submit createSubmit(String code, String status, boolean isAnswer, Language language, User user, Problem problem) {
        return Submit.builder()
                .code(code)
                .status(status)
                .isAnswer(isAnswer)
                .user(user)
                .problem(problem)
                .language(language)
                .build();
    }
}
