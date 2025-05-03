package com.coderush.dto.response.problem;

import com.coderush.domain.problem.ProblemDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProblemSetDTO {
    private List<ProblemDTO> problems;
}
