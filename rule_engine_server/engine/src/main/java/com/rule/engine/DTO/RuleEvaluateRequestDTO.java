package com.rule.engine.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RuleEvaluateRequestDTO {
    private Integer age;
    private Integer salary;
    private Integer experience;
    private String department;
    private boolean isEligible;
}
