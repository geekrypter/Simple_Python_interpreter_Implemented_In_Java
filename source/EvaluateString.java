package myevaluatestring;
import java.util.Stack;
import myexception.*;
//This class is for generating the required output by evaluating the given input in form of string
public class EvaluateString
{
    // This method applies the given operator 'givenOperator' on operands 'a' and 'b' and returns the result.
    public static Double applyOperations(char givenOperator, Double num2, Double num1) throws DZException
    {
        switch (givenOperator)
        {
        case '+':
            return num1 + num2;
        case '-':
            return num1 - num2;
        case '*':
            return num1 * num2;
        case '/':
            if (num2 == 0)
                {
                throw new DZException();
                }
            return num1 / num2;
        }
        return 0.00;
    }
 
    // This method returns true if 'operator2' has higher or same precedence as 'operator1',otherwise returns false.
    public static boolean hasPrecedence(char operator1, char operator2)
    {
        if (operator2 == '(' || operator2 == ')')
            return false;
        if ((operator1 == '*' || operator1 == '/') && (operator2 == '+' || operator2 == '-'))
            return false;
        else
            return true;
    }

    public static Double evaluate(String givenExpression) throws DZException
    {
        char[] tokens = givenExpression.toCharArray();
        // Stack for numbers
        Stack<Double> valueStack = new Stack<Double>();
        // Stack for operators
        Stack<Character> operatorStack = new Stack<Character>();
        for (int i = 0; i < tokens.length; i++)
        { 
            // If current token is a number, push it to stack for numbers
            if ((tokens[i] >= '0' && tokens[i] <= '9'))
            {
                StringBuffer strbuff = new StringBuffer();
                // When there may be more than one digit in a number, this 'while loop' takes care of it
                while (i < tokens.length && ((tokens[i] >= '0' && tokens[i] <= '9')||tokens[i]=='.'))
                    strbuff.append(tokens[i++]);
                valueStack.push(Double.valueOf(strbuff.toString()));
                i--;
            }
            // If current token is an opening brace, push it to 'operatorStack'
            else if (tokens[i] == '(')
                operatorStack.push(tokens[i]);
            // If closing brace encountered, solve entire brace
            else if (tokens[i] == ')')
            {
                while (operatorStack.peek() != '(')
                  valueStack.push(applyOperations(operatorStack.pop(), valueStack.pop(), valueStack.pop()));
                operatorStack.pop();
            }
            // If current token is an operator
            else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/')
            {
                // While top of 'operatorStack' has same or greater precedence to current
                // token, which is an operator. Apply operator on top of 'operatorStack'
                // to top two elements in 'valueStack' 
                while (!operatorStack.empty() && hasPrecedence(tokens[i], operatorStack.peek()))
                  valueStack.push(applyOperations(operatorStack.pop(), valueStack.pop(), valueStack.pop()));
                // Push current token to 'operatorStack'.
                operatorStack.push(tokens[i]);
            }
        }
        // Entire expression has been parsed at this point, apply remaining 'operatorStack' to remaining 'valueStack'
        while (!operatorStack.empty())
            valueStack.push(applyOperations(operatorStack.pop(), valueStack.pop(), valueStack.pop()));
        // Top of 'valueStack' contains result and returns it
        return valueStack.pop();
    }
 
    public static Double evaluateString(String givenString) throws DZException
	{
        return (EvaluateString.evaluate(givenString));
    }
}
