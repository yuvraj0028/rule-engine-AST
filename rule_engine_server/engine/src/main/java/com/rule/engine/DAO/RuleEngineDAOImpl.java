package com.rule.engine.DAO;

import com.rule.engine.DTO.RuleCombineRequestDTO;
import com.rule.engine.DTO.RuleCreateRequestDTO;
import com.rule.engine.DTO.RuleEvaluateRequestDTO;
import com.rule.engine.model.MetaDataModel;
import com.rule.engine.model.RuleModel;
import com.rule.engine.model.RuleNodeModel;
import com.rule.engine.repository.RuleEngineRepository;
import com.rule.engine.util.ASTBuilder;
import com.rule.engine.util.ASTEvaluator;
import com.rule.engine.util.RuleEngineUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Date;

@Slf4j
@Component
public class RuleEngineDAOImpl implements RuleEngineDAO {

    @Autowired
    private RuleEngineRepository ruleEngineRepository;

    @Override
    public RuleModel createRule(RuleCreateRequestDTO request) throws Exception {
        try{
            log.info("request made by user : {} to save rule : {}",request.getMetaData().getUploadedBy(),request.getRuleExpression());

            RuleModel ruleModel = new RuleModel();
            ASTBuilder astBuilder = new ASTBuilder();

            boolean isValidExpression = RuleEngineUtil.validateRules(List.of(request.getRuleExpression()));

            if(!isValidExpression) {
                log.error("invalid expression : {}",request.getRuleExpression());
                throw new IllegalArgumentException("invalid expression");
            }

            MetaDataModel metaData = new MetaDataModel(request.getMetaData());

            RuleNodeModel ruleNode = astBuilder.parse(List.of(request.getRuleExpression()));

            metaData.setCreatedAt(new Date());
            ruleModel.setRuleExpression(request.getRuleExpression());
            ruleModel.setRuleAST(ruleNode);
            ruleModel.setMetaData(metaData);
            log.info("parsed rule : {}",ruleModel.toString());

            RuleModel savedRule = ruleEngineRepository.save(ruleModel);
            log.info("saved rule : {} successfully",savedRule.toString());

            return savedRule;
        } catch (Exception e) {
            log.error("error while saving rule : {}",request.getRuleExpression(),e);
            throw e;
        }
    }

    @Override
    public List<RuleModel> getAllRules() throws Exception{
        try{
            log.info("fetching all rules");
            List<RuleModel> rules = ruleEngineRepository.findAll();
            log.info("fetched all rules of size: {}",rules.size());
            return rules;
        } catch (Exception e) {
            log.error("error while fetching all rules",e);
            throw e;
        }
    }

    @Override
    public boolean evaluateRule(RuleEvaluateRequestDTO request) throws Exception{
        try{
            log.info("evaluating data : {}",request.toString());
            ASTEvaluator evaluator = new ASTEvaluator();
            List<RuleModel> rules = ruleEngineRepository.findAll();

            if(rules.isEmpty()) {
                log.error("no rules found to evaluate data : {}",request.toString());
                return false;
            }

            Map<String,Object> requestMap = RuleEngineUtil.convertRequestToAttributeMap(request);

            for(RuleModel rule : rules) {
                RuleNodeModel ruleAST = rule.getRuleAST();
                if(evaluator.evaluate(ruleAST,requestMap)) {
                    request.setEligible(true);
                    log.info("evaluated data : {}",request.toString());
                    return true;
                }
            }
            log.info("evaluated data : {}",request.toString());
            return false;
        } catch (Exception e) {
            log.error("error while evaluating data : {}",request,e);
            throw e;
        }
    }

    @Override
    public RuleModel combineRule(RuleCombineRequestDTO request) throws Exception {
        try{
            log.info("request made by user : {} to combine rule : {}",request.getMetaData().getUploadedBy(),request.toString());

            boolean validatedExpression = RuleEngineUtil.validateRules(request.getRuleExpression());
            if(!validatedExpression) {
                throw new Exception("invalid expression");
            }

            RuleModel ruleModel = new RuleModel();
            log.info("combining rule : {}",request.toString());
            ASTBuilder astBuilder = new ASTBuilder();
            RuleNodeModel combineRules = astBuilder.parse(request.getRuleExpression());
            log.info("combined rule : {}",combineRules);

            MetaDataModel metaData = new MetaDataModel(request.getMetaData());
            metaData.setCreatedAt(new Date());

            ruleModel.setRuleAST(combineRules);
            ruleModel.setMetaData(metaData);
            ruleModel.setRuleExpression(String.join(",",request.getRuleExpression()));
            log.info("combined rule : {}",ruleModel.toString());

            return ruleEngineRepository.save(ruleModel);
        } catch (Exception e) {
            log.error("error while combining rule : {}",request,e);
            throw e;
        }
    }
}
