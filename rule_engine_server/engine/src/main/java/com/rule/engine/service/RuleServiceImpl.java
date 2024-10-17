package com.rule.engine.service;

import com.rule.engine.DAO.RuleEngineDAO;
import com.rule.engine.DTO.RuleCombineRequestDTO;
import com.rule.engine.DTO.RuleCreateRequestDTO;
import com.rule.engine.DTO.RuleEvaluateRequestDTO;
import com.rule.engine.model.RuleModel;
import com.rule.engine.util.ExceptionHandler;
import com.rule.engine.util.RuleEngineUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RuleServiceImpl implements RuleService {

    @Autowired
    private RuleEngineDAO ruleEngineDAO;

    @Override
    public ResponseEntity<RuleModel> create(RuleCreateRequestDTO request) {
        ResponseEntity response = null;
        Map<String,Object> responseData = null;
        ExceptionHandler handler = new ExceptionHandler();
        try {
            responseData = RuleEngineUtil.responseJson(ruleEngineDAO.createRule(request), null);
            response =  handler.handle(responseData, HttpStatus.OK);
        } catch (IllegalArgumentException e){
            responseData = RuleEngineUtil.responseJson(null, e.getMessage());
            response = handler.handle(responseData, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            responseData = RuleEngineUtil.responseJson(null, e.getMessage());
            response = handler.handle(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @Override
    public ResponseEntity<List<RuleModel>> getAllRules() {
        ResponseEntity response = null;
        Map<String,Object> responseData = null;
        ExceptionHandler handler = new ExceptionHandler();
        try {
            responseData = RuleEngineUtil.responseJson(ruleEngineDAO.getAllRules(), null);
            response =  handler.handle(responseData, HttpStatus.OK);
        } catch (Exception e) {
            responseData = RuleEngineUtil.responseJson(null, e.getMessage());
            response = handler.handle(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    public ResponseEntity<RuleEvaluateRequestDTO>  evaluateRule(RuleEvaluateRequestDTO request) {
        ResponseEntity response = null;
        Map<String,Object> responseData = null;
        ExceptionHandler handler = new ExceptionHandler();
        try {
            ruleEngineDAO.evaluateRule(request);
            responseData = RuleEngineUtil.responseJson(request, null);
            response =  handler.handle(responseData, HttpStatus.OK);
        } catch(IllegalArgumentException e){
            responseData = RuleEngineUtil.responseJson(null, e.getMessage());
            response = handler.handle(responseData, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            responseData = RuleEngineUtil.responseJson(null, e.getMessage());
            response = handler.handle(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    public ResponseEntity<RuleModel> combineRule(RuleCombineRequestDTO request) {
        ResponseEntity response = null;
        Map<String,Object> responseData = null;
        ExceptionHandler handler = new ExceptionHandler();
        try{
            RuleModel combinedRules = ruleEngineDAO.combineRule(request);
            responseData = RuleEngineUtil.responseJson(combinedRules, null);
            response =  handler.handle(responseData, HttpStatus.OK);
        } catch (IllegalArgumentException e){
            responseData = RuleEngineUtil.responseJson(null, e.getMessage());
            response = handler.handle(responseData, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            responseData = RuleEngineUtil.responseJson(null, e.getMessage());
            response = handler.handle(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
