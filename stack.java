import java.util.Scanner;
import java.util.Stack;

public class Exp9 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Stack<Integer> stack = new Stack<>();
        
        System.out.println("Stack Operations:");
        System.out.println("--------------------------");
        System.out.println("1. Push");
        System.out.println("2. Pop");
        System.out.println("3. Display");
        System.out.println("4. EXIT");

        int choice;
        do {
            System.out.print("\nEnter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter a value to push: ");
                    stack.push(scanner.nextInt());
                    break;
                case 2:
                    if (stack.isEmpty())
                        System.out.println("\nStack underflow");
                    else
                        System.out.println("\nPopped element: " + stack.pop());
                    break;
                case 3:
                    if (stack.isEmpty())
                        System.out.println("\nThe stack is empty.");
                    else {
                        System.out.println("\nStack elements:");
                        System.out.println(stack);
                    }
                    break;
                case 4:
                    System.out.println("\nEXIT");
                    break;
                default:
                    System.out.println("Please enter a valid choice.");
            }
        } while (choice != 4);
        
        scanner.close();
    }
}
