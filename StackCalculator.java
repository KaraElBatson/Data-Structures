import java.util.Stack;

public class StackCalculator {

    // Enum to represent different operators
    public enum Operator {
        ADD, SUBTRACT, MULTIPLY, DIVIDE, BLANK
    }

    public static void main(String[] args) {
        // Define an expression and compute the result
        String expression = "4*2/4";
        StackCalculator calc = new StackCalculator();
        System.out.println(calc.compute(expression));
    }

    // Function to compute the expression result
    public double compute(String sequence) {
        // Stacks to store numbers and operators
        Stack<Double> numberStack = new Stack<>();
        Stack<Operator> operatorStack = new Stack<>();
        for (int i = 0; i < sequence.length(); i++) {
            char c = sequence.charAt(i);
            // If the character is a digit, parse the number and push to the number stack
            if (Character.isDigit(c)) {
                int number = parseNumber(sequence, i);
                numberStack.push((double) number);
                // Update the index to skip the parsed number
                i += Integer.toString(number).length() - 1;
            } else if (c == '(') {
                // For '(', push a BLANK operator to the operator stack
                operatorStack.push(Operator.BLANK);
            } else if (c == ')') {
                // For ')', collapse the top elements with BLANK operator
                collapseTop(numberStack, operatorStack, Operator.BLANK);
            } else {
                // For other operators, parse the operator and collapse elements accordingly
                Operator op = parseOperator(sequence, i);
                collapseTop(numberStack, operatorStack, op);
                operatorStack.push(op);
            }
        }
        // Finish remaining operations
        collapseTop(numberStack, operatorStack, Operator.BLANK);
        // Return the final result from the number stack
        return numberStack.pop();
    }

    // Function to collapse top elements based on operator priorities
    private void collapseTop(Stack<Double> numberStack, Stack<Operator> operatorStack, Operator futureTop) {
        while (numberStack.size() >= 2 && operatorStack.size() >= 1) {
            if (priorityOfOperator(futureTop) <= priorityOfOperator(operatorStack.peek())) {
                double second = numberStack.pop();
                double first = numberStack.pop();
                Operator op = operatorStack.pop();
                double result = applyOp(first, op, second);
                numberStack.push(result);
            } else {
                break;
            }
        }
    }

    // Function to apply the operation between two numbers
    private double applyOp(double left, Operator op, double right) {
        switch (op) {
            case ADD:
                return left + right;
            case SUBTRACT:
                return left - right;
            case MULTIPLY:
                return left * right;
            case DIVIDE:
                return left / right;
            default:
                return right;
        }
    }

    // Function to determine the priority of an operator
    private int priorityOfOperator(Operator op) {
        switch (op) {
            case ADD:
            case SUBTRACT:
                return 1;
            case MULTIPLY:
            case DIVIDE:
                return 2;
            case BLANK:
                return 0;
        }
        return 0;
    }

    // Function to parse a number from the sequence
    private int parseNumber(String sequence, int offset) {
        StringBuilder sb = new StringBuilder();
        while (offset < sequence.length() && Character.isDigit(sequence.charAt(offset))) {
            sb.append(sequence.charAt(offset));
            offset++;
        }
        return Integer.parseInt(sb.toString());
    }

    // Function to parse an operator from the sequence
    private Operator parseOperator(String sequence, int offset) {
        if (offset < sequence.length()) {
            char op = sequence.charAt(offset);
            switch (op) {
                case '+':
                    return Operator.ADD;
                case '-':
                    return Operator.SUBTRACT;
                case '*':
                    return Operator.MULTIPLY;
                case '/':
                    return Operator.DIVIDE;
            }
        }
        return Operator.BLANK;
    }
}
