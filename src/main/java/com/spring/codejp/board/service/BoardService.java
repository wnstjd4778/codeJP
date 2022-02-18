package com.spring.codejp.board.service;

import com.spring.codejp.Problem.domain.Problem;
import com.spring.codejp.Problem.repository.ProblemRepository;
import com.spring.codejp.board.domain.Board;
import com.spring.codejp.board.dto.BoardSaveRequestDto;
import com.spring.codejp.board.repository.BoardRepository;
import com.spring.codejp.user.domain.User;
import com.spring.codejp.user.repository.UserRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final ProblemRepository problemRepository;

    //board를 저장한다.
    public void saveBoard(BoardSaveRequestDto requestDto, Long problemId, String email) throws NotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("해당 유저를 찾을 수 없습니다."));

        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new NotFoundException("해당 문제를 찾을 수 없습니다."));

        Board board = Board.createBoard(requestDto.getTitle(), requestDto.getContent(), problem, user);
        boardRepository.save(board);
    }

    //board를 수정한다.
    public void updateBoard(BoardSaveRequestDto requestDto, Long problemId, String email, Long boardId) throws NotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("해당 유저를 찾을 수 없습니다."));

        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new NotFoundException("해당 문제를 찾을 수 없습니다."));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException("해당 게시글을 찾을 수 업습니다."));

        if(user.getEmail() != board.getUser().getEmail()) {
            throw new NotFoundException("권한이 없습니다.");
        }

        board = board.updateBoard(requestDto.getTitle(), requestDto.getContent());
        boardRepository.save(board);
    }

    //board를 삭제한다.
    public void deleteBoard(Long problemId, String email, Long boardId) throws NotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("해당 유저를 찾을 수 없습니다."));

        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new NotFoundException("해당 문제를 찾을 수 없습니다."));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException("해당 게시글을 찾을 수 업습니다."));

        if(user.getEmail() != board.getUser().getEmail()) {
            throw new NotFoundException("권한이 없습니다.");
        }
        boardRepository.delete(board);
    }

    //board들을 가져온다.
    public List<Board> getBoards(Long problemId) throws NotFoundException {

        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new NotFoundException("해당 문제를 찾을 수 없습니다."));

        List<Board> boards = boardRepository.findAllByProblem(problem);
        return boards;
    }

    //board를 가져온다.
    public Board getBoard(Long problemId, Long boardId) throws NotFoundException {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException("게시판을 찾을 수 없습니다."));

        board.increaseHits();
        boardRepository.save(board);
        return board;
    }
}
