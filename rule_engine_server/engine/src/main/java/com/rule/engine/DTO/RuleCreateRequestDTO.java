package com.rule.engine.DTO;

import com.rule.engine.model.MetaDataModel;
import lombok.Getter;

@Getter
public class RuleCreateRequestDTO {
    private MetaDataModel metaData;
    private String ruleExpression;
}
