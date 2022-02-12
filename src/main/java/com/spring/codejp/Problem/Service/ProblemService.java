package com.spring.codejp.Problem.Service;

import com.spring.codejp.Problem.domain.Problem;
import com.spring.codejp.Problem.dto.ProblemInsertRequestDto;
import com.spring.codejp.Problem.repository.ProblemRepository;
import com.spring.codejp.user.domain.User;
import com.spring.codejp.user.repository.UserRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final UserRepository userRepository;
    private final ProblemRepository problemRepository;


    // 문제를 만든다.
    public void createProblem(ProblemInsertRequestDto requestDto, String email) throws NotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));

        Problem problem = Problem.createProblem(requestDto.getContent(), requestDto.getTitle(), requestDto.getTimeLimit(), requestDto.getMemoryLimit(), requestDto.getCategory(), user);

        problemRepository.save(problem);
    }

    // 문제를 만든다.
    public void updateProblem(ProblemInsertRequestDto requestDto, Long problemId, String email) throws NotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));

        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new NotFoundException("문제를 찾을 수 없습니다."));
        if(problem.getUser().getEmail() != user.getEmail()) {
            throw new NotFoundException("해당 문제를 변경할 수 없습니다.");
        }
        problem = problem.updateProblem(requestDto.getContent(), requestDto.getTitle(), requestDto.getTimeLimit(), requestDto.getMemoryLimit(), requestDto.getCategory());
        problemRepository.save(problem);
    }

    //문제를 수정한다.
    public void deleteProblem(Long problemId, String email) throws NotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));

        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new NotFoundException("문제를 찾을 수 없습니다."));
        if(problem.getUser().getEmail() != user.getEmail()) {
            throw new NotFoundException("해당 문제를 삭제할 수 없습니다.");
        }
        problemRepository.delete(problem);
    }

    // 문제들을 가져온다
    public List<Problem> getProblems() {
        List<Problem> problems = problemRepository.findAll();
        return problems;
    }

    // 해당 id의 문제들을 가져온다
    public Problem getProblem(Long problemId) throws NotFoundException {
        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new NotFoundException("해당 문제를 찾을 수 없습니다."));
        return problem;
    }

}
