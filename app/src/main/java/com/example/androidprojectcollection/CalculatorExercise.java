package com.example.androidprojectcollection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class CalculatorExercise extends AppCompatActivity {
    StringBuilder number, equation;
    List<String> listEquation;
    String SequentialResult, tempSequential;

    // Buttons
    List<Button> buttonNumbers, buttonOperations;
    Button btnClear;
    Button btnNumber9, btnNumber8, btnNumber7, btnNumber6, btnNumber5, btnNumber4, btnNumber3, btnNumber2, btnNumber1, btnNumber0;
    Button btnOpDiv, btnOpMul, btnOpSub, btnOpAdd, btnOpEquals, btnOpPeriod;

    // TextView
    TextView tv_equation, tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_calculator_exercise);

        number = new StringBuilder();
        equation = new StringBuilder();

        assignButtonsAndTextView();
        assignOnClickListeners();

        listEquation = new ArrayList<>();
    }

    private void assignOnClickListeners() {
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCalculator();
            }
        });

        for (Button num: buttonNumbers){
            num.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StringBuilder curNum = new StringBuilder(num.getText().toString());

                    if (number.length() == 1 && number.charAt(0) == '0'){
                        number.setCharAt(0, curNum.charAt(0));
                    } else {
                        number.append(curNum);
                    }

                    /* show number on result textview */
//                    if (number.length() == 1){
//                        tv_result.setText(curNum);
//                    } else {
//                        tv_result.append(curNum);
//                    }

                    tv_equation.setText(equation.toString());
                    tv_equation.append(number.toString());


                    tv_result.setText(calculateSequential());
                }
            });
        }

        btnOpPeriod.setOnClickListener(v -> {
            String point = btnOpPeriod.getText().toString();
            if (number.indexOf(point) == -1){
                /* ADDS a DECIMAL point IF the number does not contain any decimal point (.) */
                number.append(point);
                tv_equation.append(point);
            } else if (number.charAt(number.length()-1) == '.'){
                /* REMOVES the DECIMAL POINT if it was the last input */
                number.setLength(number.length()-1);
                tv_equation.setText(equation.toString());
                tv_equation.append(number.toString());
            }
        });

        btnOpEquals.setOnClickListener(view -> {
            /* IF the last input was a NUMBER */
            if (number.length() != 0){
                /* if LAST input was a DECIMAL point, remove it */
                if (number.charAt(number.length()-1) == '.'){
                    number.setLength(number.length()-1);
                }

                /* ADD number into the EQUATION */
                listEquation.add(number.toString());
                equation.append(number.toString());
            }

            /* IF the last input was an OPERATOR
            /* REMOVE the operator */
            if (!Character.isDigit(equation.charAt(equation.length()-1))){
                equation.setLength(equation.length()-1);
                listEquation.remove(listEquation.size()-1);
            }

//            /* FOR CHECKING ONLY !! */
//            System.out.println("\nEQUATION: " + equation);
//            System.out.print("BEFORE MDAS: ");
//            for (String s: listEquation){
//                System.out.print(s + " ");
//            }
//            System.out.println("list size: " + listEquation.size());

            /* ADD the "=" into the TEXTVIEW showing the whole equation */
            String finalEquation = equation.toString() + "=";

            /* GET MDAS result */
            String result = calculateMDAS();

            /* CLEAR calculator */
            clearCalculator();

            /* SHOW RESULT */
            tv_equation.setText(finalEquation);
            tv_result.setText(result);
        });

        for (Button op: buttonOperations){
            op.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    char ope = op.getText().charAt(0);
                    if (!listEquation.isEmpty() || number.length() != 0){
                        /* Operator will be added to the equation when
                        /* 1: Equation is not empty (layk dili siya maoy first thing na imo ibutang sa calc)
                        /* 2: There is an operand that is yet to be added to the equation */
                        if (number.length() == 0){
                            /* IF the last INPUT was an OPERATOR
                            /* REMOVE last inputted operator */
                            listEquation.remove(listEquation.size()-1);
                            equation.setCharAt(equation.length()-1, ope);
                        } else {
                            /* IF the last INPUT was an OPERAND */
                            if (number.charAt(number.length()-1) == '.') {
                                /* IF the last INPUT was a decimal poibt, REMOVE the decimal point */
                                number.setLength(number.length()-1);
                            }

                            /* ADD the INPUTTED number into the EQUATION */
                            listEquation.add(number.toString());

                            equation.append(number.toString());
                            equation.append(ope);

                            /* CLEAR the temporary variable that holds the inputted NUMBER */
                            number.setLength(0);

                            /* SHOW SEQUENTIAL RESULT */
                            tv_result.setText(SequentialResult);
                        }

                        /* ADD operator into the EQUATION */
                        listEquation.add(String.valueOf(ope));
                        tv_equation.setText(equation);
                    }
                }
            });
        }
    }

    private String calculateSequential(){
        if (listEquation.isEmpty()){
            SequentialResult = number.toString();
            return "";
        }

        if (number.length() == 1){
            tempSequential = SequentialResult;
        }

        SequentialResult = tempSequential;
        if (SequentialResult.equals("ERROR")){
            return SequentialResult;
        }

        BigDecimal left = new BigDecimal(SequentialResult);
        BigDecimal right = new BigDecimal(number.toString());
        char op = listEquation.get(listEquation.size()-1).charAt(0);

        BigDecimal tempRes = new BigDecimal(0);

        switch (op){
            case '+':
                tempRes = left.add(right);
                break;
            case '-':
                tempRes = left.subtract(right);
                break;
            case '×':
                tempRes = left.multiply(right);
                break;
            case '÷':
                try {
                    tempRes = left.divide(right);
                } catch (ArithmeticException a){
                    if (Objects.requireNonNull(a.getMessage()).contains("Division by zero")){
                        SequentialResult = "ERROR";
                        return "ERROR";
                    }
                    tempRes = left.divide(right, 11, RoundingMode.HALF_EVEN);
                }
                break;
        }

        SequentialResult = tempRes.toString();

        return SequentialResult;
    }
    private String calculateMDAS(){
        /* FOR CHECKING ONLY */
//        System.out.print("INFIX: ");
//        for (String s: listEquation){
//            System.out.print(s + " ");
//        }

        /* CONVERT the INFIX expression into POSTFIX */
        getPostfixExpression();

        /* FOR CHECKING ONLY */
//        System.out.print("\nPOSTFIX: ");
//        for (String s: listEquation){
//            System.out.print(s + " ");
//        }

        /* STACK for calculating POSTFIX */
        Stack<String> calculations = new Stack<>();

        /* GOING THROUGH EVERY OPERAND/OPERATORS in the EXPRESSION */
        for (String s: listEquation){
            /* IF current STRING is an OPERAND, PUSH into the STACK
            /* ELSE, POP 2 times to get the OPERANDS in the STACK & PERFORM operation */
            if (s.contains(".") || Character.isDigit(s.charAt(0))) {
                calculations.push(s);
            } else {
                BigDecimal right = new BigDecimal(calculations.pop());
                BigDecimal left = new BigDecimal(calculations.pop());
                System.out.println(left + " " + s + " " + right + " = ");
                switch (s.charAt(0)){
                    case '+':
                        calculations.push(left.add(right).toString());
                        break;
                    case '-':
                        calculations.push(left.subtract(right).toString());
                        break;
                    case '×':
                        calculations.push(left.multiply(right).toString());
                        break;
                    case '÷':
                        try {
                            calculations.push(left.divide(right).toString());
                        } catch (ArithmeticException a){
                            String error = a.getMessage();
                            if (Objects.requireNonNull(error).contains("Division by zero")){
                                return "ERROR Cannot divide by 0";
                            }
                            calculations.push(left.divide(right, 11, RoundingMode.HALF_EVEN).toString());
                        }
                        break;
                }
            }
        }

        return calculations.pop();
    }

    private void getPostfixExpression(){
        // arraylist for the postfix expression
        List<String> pf = new ArrayList<>();

        // stack for operators
        Stack<String> ops = new Stack<>();

        for (String s: listEquation){
            /* Operands will be added directly to the pf (postfix expression) */
            if (s.contains(".") || Character.isDigit(s.charAt(0))){
                pf.add(s);
            } else {
                /* If the ops stack for operators is empty, current operator can be added directly */
                if (ops.empty()){
                    ops.add(s);
                } else {
                    /* If the current accessed string is an operator

                    /* STEP 1: Compare the current operator with the last added operator (AKA TOP of the ops stack) if is less in precedence
                    /* STEP 2.1: If it is less in precedence, POP the last added operator -> ADD it into the postfix expression
                    /* STEP 3.1: Repeat STEP 1 & 2.1 (if applicable) until the current operator is less in precedence than the one on TOP of the stack
                    /* or until the stack is empty

                    /* STEP 2.2: If it is greater in precedence, add it directly to the ops stack */
                    while (!ops.empty() && getPrecedence(s.charAt(0)) <= getPrecedence(ops.peek().charAt(0))){
                        pf.add(ops.pop());
                    }
                    ops.push(s);
                }
            }
        }
        /* EMPTY the ops stack -> ADD operators into the POSTFIX expression */
        while (!ops.empty()){
            pf.add(ops.pop());
        }

        listEquation = pf;
    }

    // getting the precedence priority?? sort of
    // 3 - highest, 1 - lowest
    // M > D > AS
    private int getPrecedence(char op){
        if (op == '×'){
            return 3;
        } else if (op == '÷'){
            return 2;
        }

        return 1;
    }

    // resetting calculator
    private void clearCalculator(){
        tv_result.setText("");
        tv_equation.setText("");
        number.setLength(0);
        equation.setLength(0);
        listEquation.clear();
        SequentialResult = null;
    }
    private void assignButtonsAndTextView(){
        btnClear = findViewById(R.id.btnClear);

        btnNumber0 = findViewById(R.id.btnNumber0);
        btnNumber1 = findViewById(R.id.btnNumber1);
        btnNumber2 = findViewById(R.id.btnNumber2);
        btnNumber3 = findViewById(R.id.btnNumber3);
        btnNumber4 = findViewById(R.id.btnNumber4);
        btnNumber5 = findViewById(R.id.btnNumber5);
        btnNumber6 = findViewById(R.id.btnNumber6);
        btnNumber7 = findViewById(R.id.btnNumber7);
        btnNumber8 = findViewById(R.id.btnNumber8);
        btnNumber9 = findViewById(R.id.btnNumber9);

        btnOpAdd = findViewById(R.id.btnOpAdd);
        btnOpSub = findViewById(R.id.btnOpSub);
        btnOpMul = findViewById(R.id.btnOpMul);
        btnOpDiv = findViewById(R.id.btnOpDiv);
        btnOpEquals = findViewById(R.id.btnOpEquals);
        btnOpPeriod = findViewById(R.id.btnOpPeriod);

        tv_equation = findViewById(R.id.textview_partialRes);
        tv_result = findViewById(R.id.textview_Res);

        buttonNumbers = new ArrayList<>();
        buttonOperations = new ArrayList<>();

        buttonNumbers.add(btnNumber0);
        buttonNumbers.add(btnNumber1);
        buttonNumbers.add(btnNumber2);
        buttonNumbers.add(btnNumber3);
        buttonNumbers.add(btnNumber4);
        buttonNumbers.add(btnNumber5);
        buttonNumbers.add(btnNumber6);
        buttonNumbers.add(btnNumber7);
        buttonNumbers.add(btnNumber8);
        buttonNumbers.add(btnNumber9);

        buttonOperations.add(btnOpAdd);
        buttonOperations.add(btnOpMul);
        buttonOperations.add(btnOpDiv);
        buttonOperations.add(btnOpSub);
    }
}

/* USING STACK FOR SEQUENTIAL CALCULATION */

//public class CalculatorExercise extends AppCompatActivity {
//    StringBuilder number;
//    StringBuilder equation;
//    List<String> listEquation;
//    Stack<String> stackCalculation;
//
//    // Buttons
//    List<Button> buttonNumbers;
//    List<Button> buttonOperations;
//    Button btnClear;
//    Button btnNumber9;
//    Button btnNumber8;
//    Button btnNumber7;
//    Button btnNumber6;
//    Button btnNumber5;
//    Button btnNumber4;
//    Button btnNumber3;
//    Button btnNumber2;
//    Button btnNumber1;
//    Button btnNumber0;
//
//    Button btnOpDiv;
//    Button btnOpMul;
//    Button btnOpSub;
//    Button btnOpAdd;
//    Button btnOpEquals;
//    Button btnOpPeriod;
//
//    // TextView
//    TextView tv_equation;
//    TextView tv_result;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Objects.requireNonNull(getSupportActionBar()).hide();
//        setContentView(R.layout.activity_calculator_exercise);
//
//        number = new StringBuilder();
//        equation = new StringBuilder();
//
//        assignButtonsAndTextView();
//        assignOnClickListeners();
//
//        listEquation = new ArrayList<>();
//        stackCalculation = new Stack<>();
//    }

//    private void assignOnClickListeners() {
//        btnClear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                clearCalculator();
//            }
//        });
//
//        for (Button num : buttonNumbers) {
//            num.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    StringBuilder curNum = new StringBuilder(num.getText().toString());
//
//                    if (number.length() == 1 && number.charAt(0) == '0') {
//                        number.setCharAt(0, curNum.charAt(0));
//                    } else {
//                        number.append(curNum);
//                    }
//
//                    tv_equation.setText(equation.toString());
//                    tv_equation.append(number.toString());
//                }
//            });
//        }
//
//        for (Button op : buttonOperations) {
//            op.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (!listEquation.isEmpty() || number.length() != 0) {
//                        char ope = op.getText().charAt(0);
//
//                        switch (ope) {
//                            case '+':
//                            case '-':
//                            case '÷':
//                            case '×':
//                                if (number.length() == 0) {
//                                    stackCalculation.pop();
//                                    listEquation.remove(listEquation.size() - 1);
//                                    equation.setCharAt(equation.length() - 1, ope);
//                                } else {
//                                    if (number.charAt(number.length() - 1) == '.') {
//                                        number.setLength(number.length() - 1);
//                                    }
//
//                                    stackCalculation.add(number.toString());
//                                    listEquation.add(number.toString());
//
//                                    if (listEquation.size() >= 3) {
//                                        calculateSequential();
//                                    }
//
//                                    equation.append(number.toString());
//                                    equation.append(ope);
//                                    number.setLength(0);
//
//                                }
//
//                                tv_result.setText(stackCalculation.peek());
//                                stackCalculation.add(String.valueOf(ope));
//                                listEquation.add(String.valueOf(ope));
//                                tv_equation.setText(equation);
//                                break;
//                            case '.':
//                                if (number.indexOf(op.getText().toString()) == -1) {
//                                    number.append(ope);
//                                    tv_equation.append(op.getText().toString());
//                                } else if (number.charAt(number.length() - 1) == '.') {
//                                    number.setLength(number.length() - 1);
//                                    tv_equation.setText(equation.toString());
//                                    tv_equation.append(number.toString());
//                                }
//                                break;
//                            case '=':
//                                if (number.length() != 0) {
//                                    if (number.charAt(number.length() - 1) == '.') {
//                                        number.setLength(number.length() - 1);
//                                    }
//
//                                    listEquation.add(number.toString());
//                                    equation.append(number.toString());
//                                }
//
//                                if (!Character.isDigit(equation.charAt(equation.length() - 1))) {
//                                    equation.setLength(equation.length() - 1);
//                                    listEquation.remove(listEquation.size() - 1);
//                                }
//
//                                String finalEquation = equation.toString() + "=";
//                                String result = calculateMDAS();
//                                clearCalculator();
//                                tv_equation.setText(finalEquation);
//                                tv_result.setText(result);
//                                break;
//                        }
//                    }
//                }
//            });
//        }
//    }

//    private void calculateSequential(){
//         /* CALCULATING SEQUENTIAL RESULT after typing another operator
//         /* if using stackCalculation
//         /* there's at least 2 operators in the listEquation */
//        BigDecimal right = new BigDecimal(stackCalculation.pop());
//        char op = stackCalculation.pop().charAt(0);
//
////        if (!stackCalculation.peek().equals("ERROR")){
////            BigDecimal left = new BigDecimal(stackCalculation.pop());
////
////            BigDecimal tempRes = new BigDecimal(0);
////            switch (op){
////                case '+':
////                    tempRes = left.add(right);
////                    break;
////                case '-':
//                    tempRes = left.subtract(right);
//                    break;
//                case '×':
//                    tempRes = left.multiply(right);
//                    break;
//                case '÷':
//                    try {
//                        tempRes = left.divide(right);
//                    } catch (ArithmeticException a){
//                        stackCalculation.push("ERROR");
//                        return;
//                    }
//
//                    break;
//            }
//
//            stackCalculation.push(tempRes.toString());
//        }


//    // resetting calculator
//    private void clearCalculator(){
//        tv_result.setText("");
//        tv_equation.setText("");
//        number.setLength(0);
//        equation.setLength(0);
//        listEquation.clear();
//        stackCalculation.clear();
//    }