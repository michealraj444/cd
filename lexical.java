import java.util.*;

public class LexicalAnalyzer
{
    public static void parse(String str)
    {
        Set<String> keywords = new HashSet<>(Arrays.asList("if", "else", "while", "do", "break", "continue", "int", "double", "float","return", "char", "case", "sizeof", "long", "short", "typedef", "switch", "unsigned","void", "static", "struct", "goto"));
        int left = 0, right = 0, len = str.length();
        while (right <= len)
        {
            if (right < len && " +-*/,:;<>={}()[]".indexOf(str.charAt(right)) == -1) 
            right++;
            else
            {
                if (left != right)
                {
                    String subStr = str.substring(left, right);
                    if (keywords.contains(subStr)) System.out.println("'" + subStr + "' IS A KEYWORD");
                    else if (subStr.matches("\\d+")) System.out.println("'" + subStr + "' IS AN INTEGER");
                    else if (subStr.matches("\\d+\\.\\d+")) System.out.println("'" + subStr + "' IS A REAL NUMBER");
                    else if (!Character.isDigit(subStr.charAt(0))) System.out.println("'" + subStr + "' IS A VALID IDENTIFIER");
                    else System.out.println("'" + subStr + "' IS NOT A VALID IDENTIFIER");
                }
                if (right < len && "+-*/><=".indexOf(str.charAt(right)) != -1) 
                    System.out.println("'" + str.charAt(right) + "' IS AN OPERATOR");
                right++;
                left = right;
            }
        }
    }

    public static void main(String[] args)
    {
        parse("int a = b + 1c;");
    }
}
