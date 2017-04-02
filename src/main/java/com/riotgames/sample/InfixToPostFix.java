package com.riotgames.sample;

import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Created by corona10 on 2017. 4. 2..
 */
public class InfixToPostFix {

    private Stack<String> _stack;
    private String[] _infix;
    private ArrayList<String> _postfix;

    public InfixToPostFix() {
        this._postfix = new ArrayList<String>();
        this._stack = new Stack<String>();
    }

    public String[] getPostFix(String[] infix) {
        this._infix = infix;
        this._postfix.clear();
        this._stack.clear();

        for (String in : _infix) {
            if (IsOperator(in)) {
                if (in.equals(")")) {
                    while (!this._stack.empty() && !this._stack.peek().equals("(")) {
                        String popOp = this._stack.pop();
                        this._postfix.add(popOp);
                    }

                    if (!this._stack.empty() && this._stack.peek().equals("(")) {
                        this._stack.pop();
                    }
                } else {
                    while (!this._stack.empty()
                            && !IsHigherPrirorty(in, _stack.peek())) {
                        String compareOp = this._stack.pop();
                        if (!in.equals("(")) {
                            _postfix.add(compareOp);
                        } else {
                            in = compareOp;
                        }
                    }
                    this._stack.push(in);
                }
            } else if (IsNumber(in)) {
                this._postfix.add(in);
            }
        }

        while (!this._stack.empty()) {
            this._postfix.add(this._stack.pop());
        }
        String[] result = new String[this._postfix.size()];
        this._postfix.toArray(result);
        return result;
    }

    private boolean IsNumber(String s) {
        String numRegex = "\\d+(\\.\\d*)?|\\.\\d+";
        Pattern p = Pattern.compile(numRegex);
        return p.matcher(s).matches();
    }

    private boolean IsOperator(String s) {
        if (s.equals("+") || s.equals("*") || s.equals("/") || s.equals("-")
                || s.equals("(") || s.equals(")")) {
            return true;
        }

        return false;
    }

    private boolean IsHigherPrirorty(String op1, String op2) {

        if (op1.equals("+") || op1.equals("-")) {
            if (op2.equals("/") || op2.equals("*")) {
                return false;
            }
            return true;
        }

        if (op1.equals("*") || op1.equals("/")) {
            if (op2.equals("+") || op2.equals("-")) {
                return true;
            }
            return false;
        }

        return false;
    }
}
