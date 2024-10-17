package com.rule.engine.service;

import com.rule.engine.DTO.RuleCombineRequestDTO;
import com.rule.engine.DTO.RuleCreateRequestDTO;
import com.rule.engine.DTO.RuleEvaluateRequestDTO;
import com.rule.engine.model.RuleModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RuleService {
    public ResponseEntity<RuleModel> create(RuleCreateRequestDTO request);

    public ResponseEntity<List<RuleModel>> getAllRules();

    public  ResponseEntity<RuleEvaluateRequestDTO> evaluateRule(RuleEvaluateRequestDTO request);

    public ResponseEntity<RuleModel> combineRule(RuleCombineRequestDTO request);
}
