import java.util.*;

public class ShortParser {
    static List<String> nonTerms = new ArrayList<>();
    static String[][] grammar;
    static String[] first, follow;
    static Map<String, Map<Character, String>> table = new HashMap<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of productions: ");
        int n = sc.nextInt(); sc.nextLine();
        grammar = new String[n][2];
        first = new String[n];
        follow = new String[n];

        System.out.println("Enter productions (use '@' for epsilon):");
        for (int i = 0; i < n; i++) {
            String[] parts = sc.nextLine().split("->");
            grammar[i][0] = parts[0].trim();
            grammar[i][1] = parts[1].trim();
            nonTerms.add(grammar[i][0]);
            table.put(grammar[i][0], new HashMap<>());
        }

        for (int i = 0; i < n; i++) first[i] = getFirst(i);
        for (int i = 0; i < n; i++) follow[i] = getFollow(i);

        for (int i = 0; i < n; i++)
            for (char c : first[i].toCharArray())
                table.get(grammar[i][0]).put(c, getRule(grammar[i][1], c));

        System.out.print("Enter string to check: ");
        String input = sc.nextLine() + "$";
        System.out.println(parse(input) ? "Accepted" : "Rejected");
    }

    static String getFirst(int i) {
        String[] prods = grammar[i][1].split("\\|");
        String res = "";
        for (String p : prods) {
            char ch = p.charAt(0);
            if (!nonTerms.contains(ch + "")) res += ch;
            else res += getFirst(nonTerms.indexOf(ch + ""));
        }
        return res;
    }

    static String getFollow(int i) {
        Set<Character> set = new HashSet<>();
        if (i == 0) set.add('$');
        for (int j = 0; j < grammar.length; j++) {
            for (String prod : grammar[j][1].split("\\|")) {
                int idx = prod.indexOf(nonTerms.get(i));
                if (idx != -1 && idx < prod.length() - 1) {
                    char next = prod.charAt(idx + 1);
                    if (!nonTerms.contains(next + "")) set.add(next);
                    else set.addAll(firstSet(getFirst(nonTerms.indexOf(next + ""))));
                } else if (idx == prod.length() - 1 && j != i) {
                    set.addAll(followSet(getFollow(j)));
                }
            }
        }
        return toString(set);
    }

    static boolean parse(String input) {
        Stack<Character> stack = new Stack<>();
        stack.push('$');
        stack.push(nonTerms.get(0).charAt(0));
        int i = 0;
        while (!stack.isEmpty()) {
            char top = stack.pop();
            char cur = input.charAt(i);
            if (top == cur) i++;
            else if (table.containsKey(top + "") && table.get(top + "").containsKey(cur)) {
                String prod = table.get(top + "").get(cur);
                if (!prod.equals("@"))
                    for (int j = prod.length() - 1; j >= 0; j--) stack.push(prod.charAt(j));
            } else return false;
        }
        return i == input.length();
    }

    static String getRule(String prodStr, char c) {
        for (String p : prodStr.split("\\|")) {
            char ch = p.charAt(0);
            if (!nonTerms.contains(ch + "") && ch == c) return p;
            else if (nonTerms.contains(ch + "") && getFirst(nonTerms.indexOf(ch + "")).contains(c + "")) return p;
        }
        return "@";
    }

    static Set<Character> firstSet(String s) {
        Set<Character> set = new HashSet<>();
        for (char c : s.toCharArray()) if (c != '@') set.add(c);
        return set;
    }

    static Set<Character> followSet(String s) {
        Set<Character> set = new HashSet<>();
        for (char c : s.toCharArray()) set.add(c);
        return set;
    }

    static String toString(Set<Character> set) {
        StringBuilder sb = new StringBuilder();
        for (char c : set) sb.append(c);
        return sb.toString();
    }
}
