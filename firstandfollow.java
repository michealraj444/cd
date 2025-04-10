import java.util.*;

public class FirstFollowShort {
    static char[] NT, T;
    static String[][] G;
    static String[] FIRST, FOLLOW;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Ask for non-terminal characters
        System.out.print("Enter non-terminals (e.g., A B C): ");
        String ntInput = sc.nextLine();
        NT = ntInput.replaceAll("\\s+", "").toCharArray();

        // Ask for terminal characters
        System.out.print("Enter terminals (e.g., a b c): ");
        String tInput = sc.nextLine();
        T = tInput.replaceAll("\\s+", "").toCharArray();

        // Create grammar productions
        System.out.print("Enter number of productions: ");
        int n = NT.length;
        G = new String[n][];
        
        for (int i = 0; i < n; i++) {
            System.out.print("Enter the number of productions for " + NT[i] + ": ");
            int p = sc.nextInt();
            sc.nextLine(); // Consume the newline
            G[i] = new String[p];
            for (int j = 0; j < p; j++) {
                System.out.print("Enter production " + (j + 1) + " for " + NT[i] + ": ");
                G[i][j] = sc.nextLine();
            }
        }

        // Initialize FIRST and FOLLOW arrays
        FIRST = new String[n];
        FOLLOW = new String[n];
        for (int i = 0; i < n; i++)
            FIRST[i] = first(i, new HashSet<>());
        for (int i = 0; i < n; i++)
            FOLLOW[i] = follow(i, new HashSet<>());

        // Output FIRST and FOLLOW sets
        System.out.println("\nFIRST and FOLLOW sets:");
        for (int i = 0; i < n; i++)
            System.out.println("First(" + NT[i] + "): " + removeDup(FIRST[i]));
        for (int i = 0; i < n; i++)
            System.out.println("Follow(" + NT[i] + "): " + removeDup(FOLLOW[i]));
    }

    static String first(int i, Set<Integer> visited) {
        if (FIRST[i] != null) return FIRST[i];
        visited.add(i);
        String res = "";
        for (String prod : G[i]) {
            for (int k = 0; k < prod.length(); k++) {
                char c = prod.charAt(k);
                if (!isNT(c)) {
                    res += c; break;
                }
                int idx = index(c);
                if (!visited.contains(idx)) {
                    String f = first(idx, new HashSet<>(visited));
                    res += f.replace("9", "");
                    if (!f.contains("9")) break;
                    if (k == prod.length() - 1) res += "9";
                } else break;
            }
        }
        return res;
    }

    static String follow(int i, Set<Integer> visited) {
        if (FOLLOW[i] != null) return FOLLOW[i];
        visited.add(i);
        String res = i == 0 ? "$" : "";
        for (int a = 0; a < NT.length; a++) {
            for (String prod : G[a]) {
                for (int k = 0; k < prod.length(); k++) {
                    if (prod.charAt(k) == NT[i]) {
                        if (k + 1 < prod.length()) {
                            char next = prod.charAt(k + 1);
                            if (isNT(next)) {
                                String f = FIRST[index(next)];
                                res += f.replace("9", "");
                                if (f.contains("9")) res += follow(a, visited);
                            } else res += next;
                        } else if (a != i) {
                            res += follow(a, visited);
                        }
                    }
                }
            }
        }
        return res;
    }

    static boolean isNT(char c) {
        for (char x : NT) if (x == c) return true;
        return false;
    }

    static int index(char c) {
        for (int i = 0; i < NT.length; i++) if (NT[i] == c) return i;
        return -1;
    }

    static String removeDup(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray())
            if (sb.indexOf(String.valueOf(c)) == -1) sb.append(c);
        return sb.toString();
    }
}
