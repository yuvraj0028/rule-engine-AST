package com.rule.engine.util;

import com.rule.engine.model.RuleNodeModel;

import java.util.Map;

public class ASTEvaluator {

    public boolean evaluate(RuleNodeModel ruleNodeModel, Map<String, Object> data) {
        if (ruleNodeModel == null) {
            return false;
        }

        if ("operand".equals(ruleNodeModel.getType())) {
            return evaluateCondition(ruleNodeModel.getValue(), data);
        }

        else if ("operator".equals(ruleNodeModel.getType())) {
            boolean leftValue = evaluate(ruleNodeModel.getLeft(), data);
            boolean rightValue = evaluate(ruleNodeModel.getRight(), data);
            return "AND".equals(ruleNodeModel.getValue()) ? leftValue && rightValue : leftValue || rightValue;
        }

        return false;
    }

    private boolean evaluateCondition(String condition, Map<String, Object> data) {
        String[] parts = condition.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Condition is not well-formed: " + condition);
        }

        String leftOperand = parts[0];
        String operator = parts[1];
        String rightOperand = parts[2];

        Object leftValue = data.get(leftOperand);
        Object rightValue = rightOperand.startsWith("'") && rightOperand.endsWith("'")
                ? rightOperand.substring(1, rightOperand.length() - 1)
                : parseRightOperand(rightOperand);

        if (leftValue == null) {
            return false;
        }

        return evaluateComparison(leftValue, operator, rightValue);
    }

    private Object parseRightOperand(String rightOperand) {
        try {
            return Integer.parseInt(rightOperand);
        } catch (NumberFormatException e) {
            return rightOperand;
        }
    }

    private boolean evaluateComparison(Object leftValue, String operator, Object rightValue) {
        switch (operator) {
            case ">":
                return compare(leftValue, rightValue) > 0;
            case "<":
                return compare(leftValue, rightValue) < 0;
            case "=":
                return leftValue.equals(rightValue);
            default:
                throw new UnsupportedOperationException("Unknown operator: " + operator);
        }
    }

    private int compare(Object leftValue, Object rightValue) {
        if (leftValue instanceof Comparable && rightValue instanceof Comparable) {
            return ((Comparable<Object>) leftValue).compareTo(rightValue);
        }
        throw new IllegalArgumentException("Cannot compare values: " + leftValue + " and " + rightValue);
    }
}
