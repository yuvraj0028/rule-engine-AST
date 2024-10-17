package com.rule.engine.controller;

import com.rule.engine.DTO.RuleCombineRequestDTO;
import com.rule.engine.DTO.RuleCreateRequestDTO;
import com.rule.engine.DTO.RuleEvaluateRequestDTO;
import com.rule.engine.model.RuleModel;
import com.rule.engine.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rule")
public class RuleController {
    @Autowired
    private RuleService ruleService;

    @PostMapping(value = "/create_rule",produces = "application/json", consumes = "application/json",name = "createRule")
    public ResponseEntity<RuleModel> create(@RequestBody RuleCreateRequestDTO request) {
        return ruleService.create(request);
    }

    @GetMapping(value="/get_all_rules",produces = "application/json",name = "getAllRules")
    public ResponseEntity<List<RuleModel>> getAllRules() {
        return ruleService.getAllRules();
    }

    @GetMapping(value="/evaluate_rule",produces = "application/json", consumes = "application/json",name = "evaluateRule")
    public ResponseEntity<RuleEvaluateRequestDTO> evaluateRule(@RequestBody RuleEvaluateRequestDTO request) {
        return ruleService.evaluateRule(request);
    }

    @PostMapping(value="/combine_rule",produces = "application/json", consumes = "application/json",name = "combineRule")
    public ResponseEntity<RuleModel> combineRule(@RequestBody RuleCombineRequestDTO request) {
        return ruleService.combineRule(request);
    }
}
