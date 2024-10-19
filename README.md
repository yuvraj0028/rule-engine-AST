# Rule Engine Server

This backend application is a rule engine that accepts conditional rules as strings through API calls, builds an [Abstract Syntax Tree (AST)](https://en.wikipedia.org/wiki/Abstract_syntax_tree) from these rules, stores them in a MongoDB database, and evaluates incoming data against the saved rules. The project uses **Spring Boot** for backend development and **MongoDB** as the database.

## Features

- **Spring Boot** framework for backend logic.
- **MongoDB** for storing rules.
- **REST APIs** to create, combine, and evaluate rules.
- **Validation logic** for rules and evaluation response data.
- **Exception handling and logging** mechanisms for better debugging.
- **AST (Abstract Syntax Tree)** to represent and evaluate complex rules.

## Prerequisites

Before running the application, ensure that the following are installed:

1. **Java 21** - [Download Java](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).
2. **Gradle 8** - [Download Gradle](https://gradle.org/install/).
3. **MongoDB** - [Download MongoDB](https://www.mongodb.com/try/download/community).
4. **Docker** (optional for running MongoDB) - [Download Docker](https://docs.docker.com/get-docker/).

## MongoDB Setup via Docker

You can set up MongoDB using Docker with the following commands:

```bash
# Pull the MongoDB Docker image
docker pull mongo:latest

# Run the MongoDB container
docker run -d -p 27017:27017 --name=rule_engine mongo:latest

# Verify the state
docker ps
```

## Running the Spring Boot Application

Once Java, Gradle, and MongoDB are installed, follow these steps:

1. Clone the repository

```bash
git clone https://github.com/yuvraj0028/rule-engine-AST
```

2. Change your directory

```bash
cd rule-engine-AST/rule_engine_server/engine
```

3. Build the application

```bash
gradlew build
```

4. Run the application

```bash
gradlew bootRun
```

The application will be accessible at `http://localhost:9191`.

## Application Port

The application runs on port `9191`. To access the APIs, use the base URL `http://localhost:9191`.

## API Endpoints

Here are the available APIs in the application, with details of required parameters and expected responses:

1. **Create Rule (POST `/application/api/rule/create_rule`)**
   This endpoint allows you to create a new rule.

#### Request Body

```json
{
  "metaData": {
    "uploadedBy": "string"
  },
  "ruleExpression": "string"
}
```

#### Response Body

```json
{
  "errorMessage": "<errorMessage> or null",
  "responseData": {
    "id": "objectID",
    "metaData": {
      "uploadedBy": "string",
      "createdAt": "date"
    },
    "ruleExpression": "string",
    "ruleAST": {
      "type": "string",
      "left": "<RuleNodeModel Instance> or null",
      "right": "<RuleNodeModel Instance> or null",
      "value": "string"
    }
  }
}
```

2. **Combine Rules (POST `/application/api/rule/combine_rule`)**
   This endpoint combines multiple existing rules into one rule.

#### Request Body

```json
{
  "metaData": {
    "uploadedBy": "string"
  },
  "ruleExpression": "List<string>"
}
```

#### Response Body

```json
{
  "errorMessage": "<errorMessage> or null",
  "responseData": {
    "id": "objectID",
    "metaData": {
      "uploadedBy": "string",
      "createdAt": "date"
    },
    "ruleExpression": "string",
    "ruleAST": {
      "type": "string",
      "left": "<RuleNodeModel Instance> or null",
      "right": "<RuleNodeModel Instance> or null",
      "value": "string"
    }
  }
}
```

3. **Evaluate Rule (POST `/application/api/rule/evaluate_rule`)**
   This endpoint evaluates incoming data against an existing rule and return a field `isEligible` for the same.

#### Request Body

```json
{
  "age": "number",
  "department": "string",
  "salary": "number",
  "experience": "number"
}
```

#### Response Body

```json
{
  "errorMessage": "<errorMessage> or null",
  "responseData": {
    "age": "number",
    "department": "string",
    "salary": "number",
    "experience": "number",
    "isEligible": "boolean"
  }
}
```

4. **Get All Rules (GET `/application/api/rule/get_all_rules`)**
   This endpoint retrieves all stored rules from the database.

#### Request Body

```json
not required
```

#### Response Body

```json
{
  "errorMessage": "<errorMessage> or null",
  "responseData": [
    {
      "id": "objectID",
      "metaData": {
        "uploadedBy": "string",
        "createdAt": "date"
      },
      "ruleExpression": "string",
      "ruleAST": {
        "type": "string",
        "left": "<RuleNodeModel Instance> or null",
        "right": "<RuleNodeModel Instance> or null",
        "value": "string"
      }
    }
  ]
}
```

## Error Handling & Logging

The application includes proper error handling for invalid rule expressions, evaluation errors, and MongoDB connectivity issues. Comprehensive logging is implemented to track request and response data, aiding in debugging and improving traceability.

## Validation

The application also implements validation logic:

- Rules Validation: Ensures that rule expressions are well-formed and valid.
- Evaluation Data Validation: Validates the input data used in the rule evaluation.

## Exception Handling

A detailed exception-handling mechanism is in place to address issues such as invalid rule formats, malformed requests, and database connection problems. These errors are logged for debugging.

## Running Test Cases

Below are some test cases to ensure the proper functionality of the application:

### Test Case 1: Create Rule

```json
// POST /application/api/rule/create_rule
{
  "metaData": {
    "uploadedBy": "testUser"
  },
  "ruleExpression": "(age > 30 AND department = 'Sales')"
}
```

**Expected Output**: Rule should be successfully created and saved in the database.

### Test Case 2: Combine Rules

```json
// POST /application/api/rule/combine_rule
{
  "metaData": {
    "uploadedBy": "testUser"
  },
  "ruleExpression": [
    "((age > 30 AND department = 'Sales') OR (age < 25 AND department = 'Marketing')) AND (salary > 50000 OR experience > 5)",
    "((age > 35 OR department = 'Sales')) AND (salary > 20000 OR experience > 5)"
  ]
}
```

**Expected Output**: A new combined rule should be created and saved.

### Test Case 3: Evaluate Rule

```json
// POST /application/api/rule/evaluate_rule
{
  "age": 35,
  "department": "Sales",
  "salary": 60000,
  "experience": 6
}
```

**Expected Output**: `true` if the data satisfies the rule present in database, otherwise `false`.

# Rule Engine Client

This project also includes a frontend developed using Angular 18 with Bootstrap and Material UI for styling and responsiveness. The frontend allows users to create rules, evaluate them, and view results with a modern, intuitive, and responsive UI.

## Features

- **Rule Creation**: A form-based UI to input rule expressions.
- **Rule Evaluation**: Evaluate input data against created rules with a clean and interactive design.
- **Responsive UI**: Ensures smooth experience across different screen sizes using Bootstrap and Material UI components.

## Frontend Tech Stack

- **Angular** 18: Framework for building the UI.
- **Angular** CLI: For project scaffolding and development.
- **Node.js**: Backend for development tools and server-side rendering.
- **Bootstrap**: For responsive grid and components.
- **Material UI**: For enhanced user interface design.

## Running the Frontend Application

Before running the frontend, ensure Node.js and Angular CLI are installed.

1. **Install Node.js** - [Download Node.js](https://nodejs.org/en/download/package-manager)
2. **Install Angular CLI** - [Angular CLI Installation Guide](https://v17.angular.io/cli)

## Steps to Run:

1. Navigate to the frontend directory:

```bash
cd rule-engine-AST/rule_engine_client
```

2. Install dependencies:

```bash
npm install
```

3. Run the frontend application:

```bash
npm start
```

The frontend will be accessible at `http://localhost:4200`.

## Ports and Backend Integration

- **Frontend Port**: 4200
- **Backend Port**: 9191

The frontend interacts with the backend rule engine described above, allowing seamless rule creation and evaluation.

## Conclusion

This rule engine provides both a robust backend for complex rule evaluation and a responsive frontend UI for easy interaction. It can be further extended to support more advanced rules and evaluation conditions.

The application can be further extended with additional APIs, more advanced rule structures, or additional validation mechanisms for more complex use cases.
