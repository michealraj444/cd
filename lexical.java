import java.util.*;

public class SimpleLexicalAnalyzer {

    // Set of keywords
    private static final Set<String> keywords = new HashSet<>(Arrays.asList(
            "int", "float", "if", "else", "while", "for", "return", "void", "char", "double"
    ));

    // Set of operators
    private static final Set<String> operators = new HashSet<>(Arrays.asList(
            "+", "-", "*", "/", "=", "==", "<", ">", "<=", ">=", "!="
    ));

    // Set of separators
    private static final Set<String> separators = new HashSet<>(Arrays.asList(
            ";", ",", "(", ")", "{", "}"
    ));

    public static void analyze(String input) {
        // Split input using regex to separate tokens
        StringTokenizer tokenizer = new StringTokenizer(input, " +-*/=;(){}<>,\t\n", true);

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();

            if (token.isEmpty()) continue;

            if (keywords.contains(token)) {
                System.out.println(token + " → Keyword");
            } else if (operators.contains(token)) {
                System.out.println(token + " → Operator");
            } else if (separators.contains(token)) {
                System.out.println(token + " → Separator");
            } else if (token.matches("[0-9]+")) {
                System.out.println(token + " → Number");
            } else if (token.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
                System.out.println(token + " → Identifier");
            } else {
                System.out.println(token + " → Unknown Token");
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your code line:");
        String input = scanner.nextLine();

        System.out.println("\n--- Lexical Analysis Result ---");
        analyze(input);
        scanner.close();
    }
}
