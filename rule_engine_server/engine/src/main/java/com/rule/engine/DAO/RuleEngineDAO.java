package com.rule.engine.DAO;

import com.rule.engine.DTO.RuleCombineRequestDTO;
import com.rule.engine.DTO.RuleCreateRequestDTO;
import com.rule.engine.DTO.RuleEvaluateRequestDTO;
import com.rule.engine.model.RuleModel;

import java.util.List;

public interface RuleEngineDAO {
    public RuleModel createRule(RuleCreateRequestDTO request) throws Exception;

    public List<RuleModel> getAllRules() throws Exception;

    public boolean evaluateRule(RuleEvaluateRequestDTO request) throws Exception;

    public RuleModel combineRule(RuleCombineRequestDTO request) throws Exception;
}
