package com.spring.codejp.submit.domain;

import com.spring.codejp.Problem.domain.Problem;
import com.spring.codejp.user.domain.User;
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
public class Submit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "submit_id")
    private Long id;

    @Column(name = "submit_sourceCode")
    private String sourceCode;

    @Column(name = "submit_status")
    private String status;

    @Column(name = "submit_date")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @Builder
    public Submit(String sourceCode, String status, Date date, User user, Problem problem) {
        this.sourceCode = sourceCode;
        this.status = status;
        this.date = date;
        this.user = user;
        this.problem = problem;
    }

    public static Submit createSubmit(String sourceCode, String status, Date date, User user, Problem problem) {
        return Submit.builder()
                .sourceCode(sourceCode)
                .status(status)
                .date(date)
                .user(user)
                .problem(problem)
                .build();
    }
}
