import java.util.Scanner;

public class DFAEndsWithABC {

    public static boolean endsWithABC(String input) {
        String state = "q0";

        for (char ch : input.toCharArray()) {
            switch (state) {
                case "q0":
                    if (ch == 'a') state = "q1";
                    else state = "q0";
                    break;

                case "q1":
                    if (ch == 'b') state = "q2";
                    else if (ch == 'a') state = "q1";
                    else state = "q0";
                    break;

                case "q2":
                    if (ch == 'c') state = "q3";
                    else if (ch == 'a') state = "q1";
                    else state = "q0";
                    break;

                case "q3":
                    if (ch == 'a') state = "q1";
                    else state = "q0";
                    break;
            }
        }

        return state.equals("q3");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a string (only a, b, c): ");
        String input = scanner.nextLine();

        if (endsWithABC(input)) {
            System.out.println("✅ String is accepted (ends with 'abc')");
        } else {
            System.out.println("❌ String is not accepted");
        }

        scanner.close();
    }
}
