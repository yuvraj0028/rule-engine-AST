package com.rule.engine.repository;

import com.rule.engine.model.RuleModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleEngineRepository extends MongoRepository<RuleModel,String> { }