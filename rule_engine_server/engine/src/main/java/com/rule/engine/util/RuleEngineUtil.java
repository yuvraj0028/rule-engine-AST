package com.rule.engine.util;

import com.rule.engine.DTO.RuleEvaluateRequestDTO;
import com.rule.engine.model.MetaDataModel;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RuleEngineUtil {

    private static final String VALID_ATTRIBUTES = "age|department|salary|experience";
    private static final String VALID_OPERATORS = "<|>|=|AND|OR";
    private static final String VALID_PARENS = "\\(|\\)";
    private static final String VALID_TOKEN = VALID_ATTRIBUTES + "|" + VALID_OPERATORS + "|" + VALID_PARENS;

    public static boolean validateMetaData(MetaDataModel metaDataModel) {
        return !Objects.isNull(metaDataModel) && !Objects.isNull(metaDataModel.getUploadedBy()) && !metaDataModel.getUploadedBy().trim().isEmpty();
    }

    public static boolean validateRules(List<String> rules) {
        if(Objects.isNull(rules) || rules.isEmpty()) return false;

        for (String rule : rules) {
            if(Objects.isNull(rule) || rule.trim().isEmpty() || !isValidRule(rule.trim())) return false;
        }
        return true;
    }

    private static boolean isValidRule(String rule) {
        // Step 1: Tokenize the rule string
        StringTokenizer tokenizer = new StringTokenizer(rule, " ()", true);
        boolean expectOperand = true;
        int parenCount = 0;

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();
            if (token.isEmpty()) continue;

            // Check if the token is valid
            if (!Pattern.matches(VALID_TOKEN, token) && !isNumeric(token) && !isQuotedString(token)) {
                return false;
            }

            // Check the balance of parentheses
            if (token.equals("(")) {
                parenCount++;
            } else if (token.equals(")")) {
                parenCount--;
                if (parenCount < 0) {
                    return false;
                }
            }

            if (Pattern.matches(VALID_OPERATORS, token)) {
                if (expectOperand) return false;
                expectOperand = true;
            } else if (Pattern.matches(VALID_ATTRIBUTES, token) || isNumeric(token) || isQuotedString(token)) {
                if (!expectOperand) return false;
                expectOperand = false;
            }
        }

        if (parenCount != 0) return false;
        return !expectOperand;
    }

    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isQuotedString(String str) {
        return str.startsWith("'") && str.endsWith("'");

    }

    private static final Pattern tokenPattern = Pattern.compile(
        "\\(|\\)|\\bAND\\b|\\bOR\\b|>|<|=|\\w+|'[^']+'"
    );

    public static List<String> tokenize(String expression) {
        List<String> tokens = new ArrayList<>();
        Matcher matcher = tokenPattern.matcher(expression);
        while (matcher.find()) {
            tokens.add(matcher.group());
        }
        return tokens;
    }

    public static Map<String,Object> convertRequestToAttributeMap(RuleEvaluateRequestDTO request) {
        validateRequest(request);

        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("age",request.getAge());
        requestMap.put("salary",request.getSalary());
        requestMap.put("experience",request.getExperience());
        requestMap.put("department",request.getDepartment().trim());

        return requestMap;
    }

    private static void validateRequest(RuleEvaluateRequestDTO request) {
        if(request.getAge() == null || request.getSalary() == null || request.getExperience() == null || request.getDepartment() == null) {
            throw new IllegalArgumentException("missing required parameter");
        }

        if(request.getAge() < 0 || request.getSalary() < 0 || request.getExperience() < 0) {
            throw new IllegalArgumentException("invalid parameter values must me more than 0 for age, salary and experience");
        }

        if(request.getDepartment().trim().isEmpty()) {
            throw new IllegalArgumentException("department cannot be empty");
        }
    }

    public static Map<String,Object> responseJson(Object responseData, Object errorMessage) {
        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("responseData",responseData);
        responseMap.put("errorMessage",errorMessage);
        return responseMap;
    }
}
