package com.spring.codejp.comment.domain;

import com.spring.codejp.board.domain.Board;
import com.spring.codejp.user.domain.User;
import com.spring.codejp.util.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.*;

@Slf4j
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comment")
@Getter
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @Column(name = "comment_content")
    private String content;

    private boolean isRemoved = false;

    @OneToMany(mappedBy = "parent")
    @OrderBy(value = "id desc")
    private List<Comment> childList = new ArrayList<>();

    @Builder
    public Comment(User user, Board board, Comment parent, String content, boolean isRemoved, List<Comment> childList) {
        this.user = user;
        this.board = board;
        this.parent = parent;
        this.content = content;
        this.isRemoved = isRemoved;
        this.childList = childList;
    }

    public void confirmParent(Comment parent) {
        this.parent = parent;
        parent.addChild(this);
    }

    public void addChild(Comment child) {
        childList.add(child);
    }

    public static Comment createComment(User user, Board board, Comment parent, String content) {
        Comment comment = Comment.builder()
                            .user(user)
                            .board(board)
                            .content(content)
                            .build();
        if(parent != null) {
            comment.confirmParent(parent);
        }
        return comment;
    }

    public Comment updateComment(String content) {
        this.content = content;
        return this;
    }

    public void remove() {
        this.isRemoved = true;
    }

    // 현재 댓글의 댓글의 댓글(3개 이상일시)이 있을 때 삭제가 안됨
    public List<Comment> findRemovableList() {

        List<Comment> result = new ArrayList<>();

        Optional.ofNullable(this.parent).ifPresentOrElse(

                parentComment ->{//대댓글인 경우 (부모가 존재하는 경우)
                    if( parentComment.isRemoved()&& parentComment.isAllChildRemoved()){
                        result.addAll(parentComment.getChildList());
                        result.add(parentComment);
                    }
                },

                () -> {//댓글인 경우
                    if (isAllChildRemoved()) {
                        result.add(this);
                        result.addAll(this.getChildList());
                    }
                }
        );
        return result;
    }

    // 모든 자식 댓글이 삭제되었는지 확인
    private boolean isAllChildRemoved() {
        return getChildList().stream()
                .map(Comment::isRemoved)
                .filter(isRemove -> !isRemove)
                .findAny()
                .orElse(true);
    }

    class childComparator implements Comparator<Comment> {
        @Override
        public int compare(Comment c1, Comment c2) {
            if (c1.id > c2.id) {
                return 1;
            } else if (c1.id < c2.id) {
                return -1;
            }
            return 0;
        }
    }

}
