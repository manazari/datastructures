import java.util.EmptyStackException;       // used for LinkedStack
import java.util.Scanner;                   // used for tester
import java.lang.Exception;                 // extended by InvalidExpressionException
import java.lang.Math;                      // used for calculating exponentials

/**
 * This class is a tester which tests
 * the calculator class through creating
 * an GUI in the terminal called Nazarmos
 * where you can repeatedly enter expressions
 * which the calculator will evaluate and
 * print. For all invalid expressions, the
 * exceptions are handled properly and
 * printed in the calculator
 * 
 * StacksA4 Calculator Project
 * @author Matthew Nazari
 * @since 10 Jan 2019
 */
public class CalculatorProj_Nazari {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculator nazarmos = new Calculator();

        System.out.println("\n####################################");
        System.out.println("##  Nazarmos (tm)      v1.0  #######");
        System.out.println("##  ~~~~~~~~~~~~~~~~~~~~~~~  #######");
        System.out.println("##  'off' to turn off  #############");
        System.out.println("####################################");

        while (true) {
            System.out.print(" solve >> ");
            String expression = scanner.nextLine().trim();
            try {
                if (expression.equals("off")) {
                    System.out.println(" change the world... my final message...\n");
                    break;
                }
                Double solution = nazarmos.evaluate(expression);
                System.out.println("  = " + solution);
            } catch (InvalidExpressionException e) { System.out.println("  uh oh! " + e.getMessage()); }
        }
    }
}

/**
 * This class wraps a method which
 * is able to handle intrafix
 * expressions through implementing
 * the "reverse polish" technique
 * which requires two stacks which are
 * kept as references. Several
 * helper methods are defined for
 * determining the asserting the
 * validity of an expression and so
 * forth
 */
class Calculator {
    LinkedStack<Character> postfixStack = new LinkedStack<>();
    LinkedStack<Double> solutionStack = new LinkedStack<>();

    /**
     * This method utilizes the reverse
     * polish technique to convert
     * intrafix expressions into postfix
     * expressions. After doing this
     * through utilizing the postfixStack,
     * the postfix expression is then
     * evaluated by utlizing the 
     * solutionStack.
     * 
     * @param expression intrafix expression
     * @return solution to intrafix expression
     */
    public double evaluate(String expression) throws InvalidExpressionException {
        assertProperExpression(expression);
        
        // creating postfix
        String postfix = "";
        while (expression.length() > 0) {
            char item = expression.charAt(0);
            expression = expression.substring(1);
            // disregard spaces by
            if (item == ' ') continue;
            // add digits straight to postfix expression
            else if (isDigit(item)) postfix += item;
            // always push '(' on stack no matter what
            else if (item == '(') postfixStack.push('(');
            // dump stack until you hit end parenthesis which is voided
            else if (item == ')') {
                while (postfixStack.peek() != '(') {
                    postfix += postfixStack.pop();
                }
                postfixStack.pop();
                continue;
            }
            // item must be operator, so push onto stack after removing higher precedents below it
            else if (isOperator(item)) {
                while (!postfixStack.isEmpty() &&
                    isOperator(item) &&
                    precedenceOf(postfixStack.peek()) >= precedenceOf(item)) postfix += postfixStack.pop();
                postfixStack.push(item);
            }
        }
        while (!postfixStack.isEmpty()) postfix += postfixStack.pop();

        // solving the postfix expression        
        while (postfix.length() > 0) {
            char item = postfix.charAt(0);
            postfix = postfix.substring(1);

            if (isDigit(item)) solutionStack.push((double) (item - '0'));
            if (isOperator(item)) {
                double y = solutionStack.pop();
                double x = solutionStack.pop();
                if (item == '^') solutionStack.push(Math.pow(x, y));
                if (item == '*') solutionStack.push(x * y);
                if (item == '/') solutionStack.push(x / y);
                if (item == '+') solutionStack.push(x + y);
                if (item == '-') solutionStack.push(x - y);
            }
        }

        Double solution = solutionStack.pop();
        return solution;
    }

    /**
     * This method asserts whether
     * an equation is a valid intrafix
     * expression through throwing
     * an InvalidExpressionException
     * for a set of requirements for
     * any valid intrafix expression
     * 
     * @param expression intrafix to check
     */
    private void assertProperExpression(String expression) throws InvalidExpressionException {
        char lastItem = '^';
        int parentheseseCount = 0;

        for (int i = 0; i < expression.length(); i++) {
            char item = expression.charAt(i);
            // ignore spaces
            if (item == ' ') continue;

            // asserts that item is digit or operator
            if (!isDigit(item) && !isOperator(item) && !isParenthesis(item))
                throw new InvalidExpressionException("INVALID CHARACTER", i);
            // asserts that here are more open parentheses than closed
            if (item == '(') parentheseseCount++;
            if (item == ')') parentheseseCount--;
            if (parentheseseCount < 0)
                throw new InvalidExpressionException("TOO MANY CLOSING PARENTHESES", i);          
            // asserts there are never two digits in a row
            if (isDigit(lastItem) && isDigit(item))
                throw new InvalidExpressionException("TOO MANY DIGITS IN NUMBER", i);
            // asserts coefficients to expressions in parentheses have astrisks: "6(1)" invalid
            if (isDigit(lastItem) && item == '(')
                throw new InvalidExpressionException("USE ASTRISK FOR MULTIPLICATION", i);
            // asserts there are never two operators in a row, except for parentheses
            if (isOperator(lastItem) && isOperator(item))
                throw new InvalidExpressionException("TOO MANY OPERATORS IN A ROW", i);
            // asserts there are no parenthesese open or closed on operators: "+)" or "(+"
            if (isOperator(lastItem) && item == ')' || lastItem == '(' && isOperator(item))
                throw new InvalidExpressionException("PARENTHESIS NEXT TO OPERATOR", i);
            // asserts there are no empty parenthesis: "()"
            if (lastItem == '(' && item == ')')
                throw new InvalidExpressionException("EMPTY PARENTHESESE ARE INVALID", i);
            // asserts the expression does not end with an operator
            if (i == expression.length()-1 && isOperator(item))
                throw new InvalidExpressionException("INVALID OPERATOR AT END OF EXPRESSION", i);

            lastItem = item;
        }

        // asserts parenthesese were all closed
        if (parentheseseCount != 0)
            throw new InvalidExpressionException("TOO MANY OPENING PARENTHESES");
    }

    /**
     * This method simply determines
     * whether the character is a
     * digit from 0 to 9 inclusive
     */
    private boolean isDigit(char x) {
        return x >= '0' && x <= '9';
    }
    
    /**
     * This method will determine
     * whether if the operator
     * is any of the following
     * accomodated mathematical
     * operators:
     * 
     *   [^]  : exponent
     *   [*]  : multiplication
     *   [/]  : division
     *   [+]  : addition
     *   [-]  : subtraction
     */
    private boolean isOperator(char x) {
        return (x == '^' ||
                x == '*' ||
                x == '/' ||
                x == '+' ||
                x == '-');
    }

    /**
     * This method simply determines
     * whether the character is a left
     * or right parenthesis 
     */
    private boolean isParenthesis(char x) {
        return x == '(' || x == ')';
    }

    /**
     * This method calculates the
     * precedence of with operations
     * occur first based on our
     * normal PEMDAS conventions.
     * 
     * Order of precedance as follows
     *   [^]     : 2 (greatest)
     *   [*, /]  : 1
     *   [+, -]  : 0 (least)
     * 
     * @param x operator
     * @return precedence of x
     */
    private int precedenceOf(char x) {
        final int EXPONENT_PRECEDENCE = 2;
        final int MULTIPLY_PRECEDENCE = 1;
        final int ADDITION_PRECEDENCE = 0;

        int precedence = -1;
        if (x == '^') precedence = EXPONENT_PRECEDENCE;
        if (x == '*' || x == '/') precedence = MULTIPLY_PRECEDENCE;
        if (x == '+' || x == '-') precedence = ADDITION_PRECEDENCE;
        return precedence;
    }
}

/**
 * This class is used to better
 * express what aspect of an
 * expression is invalid and adds
 * the character position on the
 * expression
 */
class InvalidExpressionException extends Exception { 
    public InvalidExpressionException(String errorMessage, int index) {
        super(errorMessage + " at position " + index);
    }

    public InvalidExpressionException(String errorMessage) {
        super(errorMessage);
    }
}

/**
 * This interface holds the
 * basic operations of any
 * Stack data structure and
 * has a generic so that it
 * can hold any type of class
 */
interface YoStack<E> {
    public boolean isEmpty();
    public E push(E e);
    public E pop() throws EmptyStackException;
    public E peek() throws EmptyStackException;
}

/**
 * This class is a linked stack which
 * implements basic stack operations
 * from YoStack. LinkedStack is also
 * generic, meaning it will be able
 * to hold any type of class. It behaves
 * as a linked list in how it holds
 * a reference to a first Node which
 * holds data of generic type and the
 * reference to the "next" Node in the
 * stack
 */
class LinkedStack<E> implements YoStack<E> {
    private Node top;

    /**
     * Default constructor just sets
     * the top Node to null
     */
    public LinkedStack() {
        top = null;
    }

    /**
     * This class is a super simple
     * wrapper for data of generic
     * type E and a reference to
     * the next node in the list
     */
    class Node {
        E data;
        Node next;
    }

    /**
     * The stack will be considered
     * empty if the first Node does
     * not exist, meaning the top
     * reference would equal null
     * 
     * @return boolean  if stack is empty
     */
    public boolean isEmpty() {
        return top == null;
    }

    /**
     * This method accepts data to which
     * it adds onto the stack by storing
     * the data in a new Node. This new
     * Node will then become the new
     * top of the stack
     * 
     * @param e  data to add to stack
     * @return E  parameter e
     */
    public E push(E e) {
        Node node = new Node();
        node.data = e;
        node.next = isEmpty() ? null : top;
        top = node;
        return e;
    }

    /**
     * This method will remove the
     * data stored at the first spot
     * on top of the stack
     * 
     * @return E  data just popped
     * @exception ; if stack is empty
     */
    public E pop() throws EmptyStackException {
        if (isEmpty()) throw new EmptyStackException();
        E topData = top.data;
        top = top.next;
        return topData;
    }

    /**
     * This method is useful for
     * checking what is on the top
     * of the stack without actually
     * modifying the stack in any way
     * 
     * @return E  data at top of stack
     * @exception ; if stack is empty
     */
    public E peek() throws EmptyStackException {
        if (isEmpty()) throw new EmptyStackException();
        return top.data;
    }
}