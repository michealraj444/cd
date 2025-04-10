import java.util.*;

public class LeftFactoring {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Input productions
        System.out.println("Enter number of productions:");
        int n = sc.nextInt();
        sc.nextLine(); // consume newline
        String[] productions = new String[n];
        System.out.println("Enter productions:");
        for (int i = 0; i < n; i++) {
            productions[i] = sc.nextLine();
        }
        
        // Process left factoring
        for (String production : productions) {
            String[] parts = production.split("->");
            String lhs = parts[0].trim();
            String[] rhs = parts[1].split("\\|");
            
            String prefix = findCommonPrefix(rhs);
            if (!prefix.isEmpty()) {
                System.out.println(lhs + "->" + prefix + lhs + "'");
                for (String r : rhs) {
                    if (r.startsWith(prefix)) {
                        System.out.println(lhs + "'->" + r.substring(prefix.length()));
                    } else {
                        System.out.println(lhs + "'->" + r);
                    }
                }
            }
        }
    }

    // Find common prefix in RHS
    private static String findCommonPrefix(String[] rhs) {
        String prefix = rhs[0];
        for (int i = 1; i < rhs.length; i++) {
            while (!rhs[i].startsWith(prefix)) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) return "";
            }
        }
        return prefix;
    }
}
