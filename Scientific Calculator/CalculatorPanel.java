import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class CalculatorPanel extends JPanel {
    private JTextField display;
    private Stack<String> expressionStack;

    public CalculatorPanel() {
        setLayout(new BorderLayout());

        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        expressionStack = new Stack<>();

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 4));

        String[] buttons = {
            "7", "8", "9", "/", "sin", "cos", "tan", "C",
            "4", "5", "6", "*", "ln", "log", "sqrt", "(",
            "1", "2", "3", "-", "^", "e", "pi", ")",
            "0", ".", "=", "+", "exp", "abs", "mod", "DEL"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("C")) {
                display.setText("");
                expressionStack.clear();
            } else if (command.equals("DEL")) {
                String currentText = display.getText();
                if (!currentText.isEmpty()) {
                    display.setText(currentText.substring(0, currentText.length() - 1));
                }
            } else if (command.equals("=")) {
                String result = evaluateExpression(display.getText());
                display.setText(result);
            } else {
                display.setText(display.getText() + command);
            }
        }
    }

    private String evaluateExpression(String expression) {
        // Simple evaluation logic for the sake of demonstration
        // Note: This implementation is quite basic and for full scientific calculator,
        // you might need a proper mathematical expression parser and evaluator
        try {
            ExpressionParser parser = new ExpressionParser();
            double result = parser.parse(expression).evaluate();
            return String.valueOf(result);
        } catch (Exception e) {
            return "Error";
        }
    }

    // Inner class for simple expression parsing and evaluation
    private class ExpressionParser {
        // Simplified parser implementation
        public Expression parse(String expression) {
            // Tokenize and create Expression object here
            // This example omits detailed implementation for brevity
            return new Expression(expression);
        }
    }

    // Inner class representing a mathematical expression
    private class Expression {
        private String expression;

        public Expression(String expression) {
            this.expression = expression;
        }

        public double evaluate() {
            // Simplified evaluation logic for demonstration
            // In a real implementation, parse and evaluate the expression properly
            try {
                return new Object() {
                    int pos = -1, ch;

                    void nextChar() {
                        ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
                    }

                    boolean eat(int charToEat) {
                        while (ch == ' ') nextChar();
                        if (ch == charToEat) {
                            nextChar();
                            return true;
                        }
                        return false;
                    }

                    double parse() {
                        nextChar();
                        double x = parseExpression();
                        if (pos < expression.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                        return x;
                    }

                    // Grammar:
                    // expression = term | expression `+` term | expression `-` term
                    // term = factor | term `*` factor | term `/` factor
                    // factor = `+` factor | `-` factor | `(` expression `)`
                    //        | number | functionName factor | factor `^` factor

                    double parseExpression() {
                        double x = parseTerm();
                        for (;;) {
                            if      (eat('+')) x += parseTerm(); // addition
                            else if (eat('-')) x -= parseTerm(); // subtraction
                            else return x;
                        }
                    }

                    double parseTerm() {
                        double x = parseFactor();
                        for (;;) {
                            if      (eat('*')) x *= parseFactor(); // multiplication
                            else if (eat('/')) x /= parseFactor(); // division
                            else return x;
                        }
                    }

                    double parseFactor() {
                        if (eat('+')) return parseFactor(); // unary plus
                        if (eat('-')) return -parseFactor(); // unary minus

                        double x;
                        int startPos = this.pos;
                        if (eat('(')) { // parentheses
                            x = parseExpression();
                            eat(')');
                        } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                            while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                            x = Double.parseDouble(expression.substring(startPos, this.pos));
                        } else if (ch >= 'a' && ch <= 'z') { // functions
                            while (ch >= 'a' && ch <= 'z') nextChar();
                            String func = expression.substring(startPos, this.pos);
                            x = parseFactor();
                            if (func.equals("sqrt")) x = Math.sqrt(x);
                            else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                            else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                            else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                            else if (func.equals("ln")) x = Math.log(x);
                            else if (func.equals("log")) x = Math.log10(x);
                            else if (func.equals("exp")) x = Math.exp(x);
                            else if (func.equals("abs")) x = Math.abs(x);
                            else if (func.equals("pi")) x = Math.PI;
                            else if (func.equals("e")) x = Math.E;
                            else throw new RuntimeException("Unknown function: " + func);
                        } else {
                            throw new RuntimeException("Unexpected: " + (char)ch);
                        }

                        if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                        return x;
                    }
                }.parse();
            } catch (Exception e) {
                return Double.NaN;
            }
        }
    }
}
