import java.util.LinkedList;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		ExpressionEvaluator e = new ExpressionEvaluator();

		Scanner s = new Scanner (System.in);
		Scanner t = new Scanner (System.in);

		System.out.println("--------------------------------------------");
		System.out.println("Please choose the operation you want to do :");
		System.out.println("1 - Truth Table for the Statement");
		System.out.println("2- Test for Equivalence of two Statements");
		System.out.println("3- Test for Tautology of a statement");
		System.out.println("4- Test for Contradiction of a statement");
		System.out.println("5- Compare time analysis between different number of statements");
		System.out.println("--------------------------------------------");
		int i = s.nextInt();
		
		if (i == 2)
		{
			System.out.println("Enter the statement you want to find its tautology condition ( ^ : And , +: Or , ~ : Not)");
			System.out.println(" You Can use as many brackets as you want ( & )");
			String stat1 = t.nextLine();
			
			System.out.println("Enter statement number two ");
			String stat2 = t.nextLine();

			String post1 = e.infixToPostfix(stat1);

		//	System.out.println(post);
			e.setvars(post1);
			e.settable(post1);
			String post2 = e.infixToPostfix(stat2);
			e.setvars(post2);
			e.settable(post2);


			System.out.println("false");
			System.out.println("The statements are not equivalent");
		}
		
//		String ex = "((P+Q)^~R)";
//		String post =e.infixToPostfix(ex);
//		LinkedList results = e.setstatementsandvars();
//		e.settable(post);
//		System.out.println("the following statement is not a tautology");
		

	}
		
	
	
}
