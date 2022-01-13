package com.spring.codejp.Problem.dto;

import com.spring.codejp.Problem.domain.Category;
import lombok.Data;

import javax.persistence.*;

@Data
public class ProblemInsertRequestDto {

    private String content;
    private String title;
    private int timeLimit;
    private int memoryLimit;
    private Category category;

}
