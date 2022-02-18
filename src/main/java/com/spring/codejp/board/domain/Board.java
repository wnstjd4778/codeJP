package com.spring.codejp.board.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.codejp.Problem.domain.Problem;
import com.spring.codejp.comment.domain.Comment;
import com.spring.codejp.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity
@Getter
@Table(name = "board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(name = "board_title")
    private String title;

    @Column(name = "board_content")
    private String content;

    @Column(name = "board_hits")
    private int hits; // 조회수

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Board(String title, String content, Problem problem, User user, int hits) {
        this.title = title;
        this.content = content;
        this.problem = problem;
        this.user = user;
        this.hits = hits;
    }

    public static Board createBoard(String title, String content, Problem problem, User user) {

        return Board.builder()
                .title(title)
                .content(content)
                .problem(problem)
                .user(user)
                .hits(0)
                .build();
    }

    public Board updateBoard(String title, String content) {
        this.content = content;
        this.title = title;
        return this;
    }

    public Board increaseHits() {
        this.hits +=1;
        return this;
    }

}
