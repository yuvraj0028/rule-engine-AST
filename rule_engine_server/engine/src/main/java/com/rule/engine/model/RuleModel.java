package com.rule.engine.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document
@AllArgsConstructor
@NoArgsConstructor
public class RuleModel {
    @Id
    private String id;
    private MetaDataModel metaData;
    private String ruleExpression;
    private RuleNodeModel ruleAST;
}
