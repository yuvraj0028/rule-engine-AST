package com.rule.engine.DTO;

import com.rule.engine.model.MetaDataModel;
import lombok.Getter;

import java.util.List;

@Getter
public class RuleCombineRequestDTO {
    private MetaDataModel metaData;
    private List<String> ruleExpression;
}
