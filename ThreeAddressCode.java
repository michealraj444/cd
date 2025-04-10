import java.util.*;

public class ThreeAddressCode {
    static int tempVarCount = 0;

    public static List<String> generateTAC(String expr) {
        Stack<String> operands = new Stack<>();
        
        Stack<Character> operators = new Stack<>();
        
        List<String> tacList = new ArrayList<>();
        
        Map<Character, Integer> precedence = Map.of('+', 1, '-', 1, '*', 2, '/', 2);

        for (char ch : expr.toCharArray()) {
            if (Character.isLetterOrDigit(ch)) {
                operands.push(String.valueOf(ch));
            } else {
                while (!operators.isEmpty() && precedence.get(operators.peek()) >= precedence.get(ch)) {
                    
                	tacList.add(processOp(operands, operators.pop()));
                }
                operators.push(ch);
            }
        }
        while (!operators.isEmpty()) tacList.add(processOp(operands, operators.pop()));
        return tacList;
    }

    private static String processOp(Stack<String> operands, char op) {
        String right = operands.pop();
        String left = operands.pop();
        String t = "t" + tempVarCount++;
        String tac = t + " = " + left + " " + op + " " + right; // Correct order
        operands.push(t);
        return tac;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter an expression (e.g., a+b*c): ");
        String expr = scanner.nextLine();
        scanner.close();

        System.out.println("Three Address Code:");
        generateTAC(expr).forEach(System.out::println);
    }
}
