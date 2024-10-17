package com.rule.engine.util;

import com.rule.engine.model.RuleNodeModel;

import java.util.List;

public class ASTBuilder {
    private List<String> tokens;
    private int currentTokenIndex = 0;

    public RuleNodeModel parse(List<String> expressions) {
        RuleNodeModel root = null;

        for (String expression : expressions) {
            RuleNodeModel currentAST = parseSingleExpression(expression.trim());
            if (root == null) {
                root = currentAST;
            } else {
                root = new RuleNodeModel("operator", root, currentAST);
                root.setValue("AND");
            }
        }
        return root;
    }

    public RuleNodeModel parseSingleExpression(String expression) {
        this.tokens = RuleEngineUtil.tokenize(expression);
        currentTokenIndex = 0;
        return parseExpression();
    }

    private RuleNodeModel parseExpression() {
        RuleNodeModel left = parseTerm();
        while (currentTokenIndex < tokens.size() && tokens.get(currentTokenIndex).equals("OR")) {
            currentTokenIndex++;
            RuleNodeModel right = parseTerm();
            left = new RuleNodeModel("operator", left, right);
            left.setValue("OR");
        }
        return left;
    }

    private RuleNodeModel parseTerm() {
        RuleNodeModel left = parseFactor();
        while (currentTokenIndex < tokens.size() && tokens.get(currentTokenIndex).equals("AND")) {
            currentTokenIndex++;
            RuleNodeModel right = parseFactor();
            left = new RuleNodeModel("operator", left, right);
            left.setValue("AND");
        }
        return left;
    }

    private RuleNodeModel parseFactor() {
        String token = tokens.get(currentTokenIndex);

        if (token.equals("(")) {
            currentTokenIndex++;
            RuleNodeModel expression = parseExpression();
            if (currentTokenIndex < tokens.size() && tokens.get(currentTokenIndex).equals(")")) {
                currentTokenIndex++;
            } else {
                throw new IllegalArgumentException("Missing closing parenthesis");
            }
            return expression;
        } else {
            return parseCondition();
        }
    }

    private RuleNodeModel parseCondition() {
        if (currentTokenIndex + 2 >= tokens.size()) {
            throw new IllegalArgumentException("Condition is not well-formed: " + String.join(" ", tokens));
        }
        String left = tokens.get(currentTokenIndex++);
        String operator = tokens.get(currentTokenIndex++);
        String right = tokens.get(currentTokenIndex++);
        String condition = left + " " + operator + " " + right;
        return new RuleNodeModel("operand", condition);
    }
}
