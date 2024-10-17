package com.rule.engine.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RuleNodeModel {
    String type;
    RuleNodeModel left;
    RuleNodeModel right;
    String value;

    public RuleNodeModel(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public RuleNodeModel(String type, RuleNodeModel left, RuleNodeModel right) {
        this.type = type;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        if ("operand".equals(type)) {
            return value;
        } else {
            return "(" + left + " " + value + " " + right + ")";
        }
    }
}