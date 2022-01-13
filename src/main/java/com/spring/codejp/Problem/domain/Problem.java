package com.spring.codejp.Problem.domain;

import com.spring.codejp.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "problem")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id")
    private Long id;

    @Column(name = "problem_content")
    private String content;

    @Column(name = "title")
    private String title;

    @Column(name = "problem_time_limit")
    private int timeLimit;

    @Column(name = "problem_memory_limit")
    private int memoryLimit;

    @Column(name = "problem_category")
    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Problem(Long id, String content, String title, int timeLimit, int memoryLimit, Category category, User user) {
        this.id = id;
        this.content = content;
        this.title = title;
        this.timeLimit = timeLimit;
        this.memoryLimit = memoryLimit;
        this.category = category;
        this.user = user;
    }

    public static Problem createProblem(String content, String title, int timeLimit, int memoryLimit, Category category, User user) {
        return Problem.builder()
                .category(category)
                .content(content)
                .memoryLimit(memoryLimit)
                .title(title)
                .timeLimit(timeLimit)
                .user(user)
                .build();
    }

    public Problem updateProblem(String content, String title, int timeLimit, int memoryLimit, Category category) {
        this.content = content;
        this.title = title;
        this.timeLimit = timeLimit;
        this.memoryLimit = memoryLimit;
        this.category = category;
        return this;
    }
}
