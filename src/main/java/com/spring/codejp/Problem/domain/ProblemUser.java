package com.spring.codejp.Problem.domain;

import com.spring.codejp.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "problem_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProblemUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_user_id")
    private Long id;

    @Column(name = "problem_user_is_answer")
    private boolean isAnswer; // 정답이면 true 정답이 아니면 false 안 풀었을 경우 null

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problem problem;
}
