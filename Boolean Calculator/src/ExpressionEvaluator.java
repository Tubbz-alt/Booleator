import java.awt.List;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

/**
 * Class for evaluating and converting infix to postfix operations
 *
 */
public class ExpressionEvaluator {
	/**
	 * Stack used for storing Arithmetic operations to convert from infix to postfix
	 **/
	Stack Storestack = new Stack();
	/**
	 * Stack used to store the numbers used in a specific operation ( postfix
	 * operation)
	 **/
	Stack Evaluate = new Stack();
	/** Variable used to count the characters in a specif operation **/
	private int chars = 0;
	/** Variable used to count the operations **/
	private int symbols = 0;
	LinkedList<Character> variables = new LinkedList();
	LinkedList<String> statements = new LinkedList();
	LinkedList<Map<Integer, Integer>> values = new LinkedList<Map<Integer, Integer>>();
	LinkedList<Map<Integer, Integer>> valuesOfStats = new LinkedList<Map<Integer, Integer>>();
	LinkedList<String> statsandvars = new LinkedList();
	Stack exp = new Stack();
	Stack temp2 = new Stack();
	Stack temp1 = new Stack();
	boolean brackets = false; // to see if expression has brackets or not

	LinkedList result = new LinkedList();

	/**
	 * This method converts infix expression into postfix expression using the stack
	 * implementation
	 * 
	 * @parameter expression (infix expression)
	 * @return postfix expression
	 */

	public String infixToPostfix(String expression) {

		setvars(expression);
		/** Stores the expression and adjusts it **/
		StringBuilder Postfix = new StringBuilder(expression.length());

		/** checks whether invalid operations exist **/
		for (int i = 0; i < expression.length(); i++) {
			if ((expression.charAt(i) >= 'a' && expression.charAt(i) <= 'z')
					|| (expression.charAt(i) >= 'A' && expression.charAt(i) <= 'Z')) {
				chars++;
			} else if (expression.charAt(i) == '+' || expression.charAt(i) == '^' || expression.charAt(i) == '~') {
				symbols++;
			}

		}

		/** checks whether expression is null or empty **/

		while (expression.length() == 0 || expression == null) {
			System.out.println("Enter a valid Statement");
		}

		/**
		 * For loops that contains all necessary operations and conditions for each
		 * symbol ( '+' , '-' , '*','/')
		 **/

		for (int i = 0; i < expression.length(); i++) {
			if (expression.charAt(i) == '(') {
				Storestack.push(expression.charAt(i));
				brackets = true;

			} else if (expression.charAt(i) == '^') {
				if (Storestack.isEmpty() || Storestack.peek().equals('(')) {
					Storestack.push(expression.charAt(i));
				} else if (!Storestack.peek().equals('(') && !Storestack.isEmpty()) {
					while (!Storestack.isEmpty()) {
						Postfix.append(Storestack.peek());
						Postfix.append(' ');
						Storestack.pop();
						if (Storestack.isEmpty() || Storestack.peek().equals('(')) {
							break;
						}
					}
					Storestack.push(expression.charAt(i));
				}

			} else if (expression.charAt(i) == '+') {

				if (Storestack.isEmpty() || Storestack.peek().equals('(')) {
					Storestack.push(expression.charAt(i));
				} else if (!Storestack.peek().equals('(') && !Storestack.isEmpty()) {
					while (!Storestack.isEmpty()) {
						Postfix.append(Storestack.peek());
						Postfix.append(' ');
						Storestack.pop();
						if (Storestack.isEmpty() || Storestack.peek().equals('(')) {
							break;
						}
					}
					Storestack.push(expression.charAt(i));
				}
			} else if (expression.charAt(i) == '~') {

				if (Storestack.isEmpty() || Storestack.peek().equals('+') || Storestack.peek().equals('^')
						|| Storestack.peek().equals('(')) {
					Storestack.push(expression.charAt(i));
				} else {

					Postfix.append(Storestack.peek());
					Postfix.append(' ');
					Storestack.pop();
					Storestack.push(expression.charAt(i));
				}
			}

			else if (expression.charAt(i) == ')') {
				while (!Storestack.peek().equals('(')) {
					Postfix.append(Storestack.peek());
					Postfix.append(' ');
					Storestack.pop();
					if (Storestack.peek().equals('(')) {
						Storestack.pop();
						break;
					}
				}
			}

			else if (expression.charAt(i) != ' ') {
				Postfix.append(expression.charAt(i));
				Postfix.append(' ');
			}

		}

		/** checks that stack is empty at last **/
		while (!Storestack.isEmpty()) {
			if (Storestack.size() == 1) {
				Postfix.append(Storestack.peek());
				Storestack.pop();
			} else {
				Postfix.append(Storestack.peek());
				Postfix.append(' ');
				Storestack.pop();
			}
		}
		/** new expression after converting to postfix **/
		String newexpression = new String(Postfix.toString());
		getstatements (newexpression);

		return newexpression;

	}

	public LinkedList getvars() {
		return variables;
	}

	public void setvars(String expression) {
		boolean notfound = false;
		variables = new LinkedList<Character>();
		for (int i = 0; i < expression.length(); i++) {
			if ((expression.charAt(i) >= 'a' && expression.charAt(i) <= 'z')
					|| (expression.charAt(i) >= 'A' && expression.charAt(i) <= 'Z')) {

				for (int u = 0; u < variables.size() || variables.size() == 0; u++) {
					notfound = false;
					if (variables.size() != 0) {
						if (Character.toLowerCase(variables.get(u)) != Character.toLowerCase(expression.charAt(i))) {
							notfound = true;
						} else {
							notfound = false;
							break;
						}
					} else
						variables.add(expression.charAt(i));
				}
				if (notfound) {
					variables.add(expression.charAt(i));
				} else {
				}
			}
		}

	}

	public LinkedList<String> getstats() {
		return statements;
	}

	public int getcharnum() {
		return chars;
	}

	public LinkedList getstatements(String expression) {

		StringBuilder statement = new StringBuilder();
		StringBuilder st1 = new StringBuilder();
		StringBuilder st2 = new StringBuilder();
		statements = new LinkedList<String>();
		// used to add all the statements in the stack
		temp1 = new Stack();

		for (int i = 0; i < expression.length(); i++) {
			if (expression.charAt(i) == '+') {
				statement = new StringBuilder();
				st1 = new StringBuilder();
				st2 = new StringBuilder();
				st2.append(Evaluate.peek());
				Evaluate.pop();
				st1.append(Evaluate.peek());
				Evaluate.pop();
				statement.insert(0, st2);
				statement.insert(0, expression.charAt(i));
				statement.insert(0, st1);
				if (brackets) {
					statement.append(')');
					statement.insert(0, '(');
				}
				temp1.push(statement);
				Evaluate.push(statement);

			} else if (expression.charAt(i) == '^') {
				statement = new StringBuilder();
				st1 = new StringBuilder();
				st2 = new StringBuilder();
				st2.append(Evaluate.peek());
				Evaluate.pop();
				st1.append(Evaluate.peek());
				Evaluate.pop();
				statement.insert(0, st2);
				statement.insert(0, expression.charAt(i));
				statement.insert(0, st1);
				if (brackets) {
					statement.append(')');
					statement.insert(0, '(');
				}
				temp1.push(statement);
				Evaluate.push(statement);

			} else if (expression.charAt(i) == '~') {
				statement = new StringBuilder();
				st1 = new StringBuilder();
				st2 = new StringBuilder();
				st2.append(Evaluate.peek());
				Evaluate.pop();
				statement.insert(0, st2);
				statement.insert(0, expression.charAt(i));
				if (brackets) {
					statement.append(')');
					statement.insert(0, '(');
				}
				temp1.push(statement);
				Evaluate.push(statement);

			}

			/** for loop to delete any spaces **/
			else if (expression.charAt(i) == ' ') {
				continue;
				/** pushes any digit to perform operation **/
			} else if (Character.isAlphabetic(expression.charAt(i))) {
				Evaluate.push((expression.charAt(i)));
				/** Validates any wring entry **/
			} else {
				throw null;
			}
		}
		// used to reaarange the statements
		temp2 = new Stack<String>();
		int size = temp1.size();
		{
			for (int i = 0; i < size; i++) {
				temp2.push(temp1.peek().toString());
				temp1.pop();
			}
		}
		for (int i = 0; i < size; i++) {
			statements.add(temp2.peek().toString());
			temp2.pop();
		}
		return statements;

	}

	// to set the list of variables and statements
	public LinkedList setstatementsandvars() {
		for (Character i : variables) {
			statsandvars.add(i.toString());
		}
		for (String s : statements) {
			statsandvars.add(s);
		}
		return statsandvars;
	}

	public void settable(String expression) {
		int rows = (int) Math.pow(2, variables.size());
		int value = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = variables.size() - 1; j >= 0; j--) {
				// map for each row 0 to n-1
				Map<Integer, Integer> m = new HashMap();
				values.add(m);
				value = (i / (int) Math.pow(2, j)) % 2;
				values.get(i).put(j, value);
			}
		}
//		LinkedList l1 = new LinkedList();
//		LinkedList l2 = new LinkedList();
//		l1.add(0);l1.add(0);l1.add(1);l1.add(1);l1.add(1);l1.add(1);l1.add(1);l1.add(1);
//		l2.add(1);l2.add(0);l2.add(1);l2.add(0);l2.add(1);l2.add(0);l2.add(1);l2.add(0);
		LinkedList result = evaluate(expression);
		for (Character c : variables) {
			System.out.print(c + " |");
		}
//		System.out.print("(P+Q)" + " |");
//		System.out.print("(~R)  |");
		System.out.println(statements.getLast() + "|");
		;
		System.out.println();
		for (int i = 0; i < rows; i++) {
			for (int j = variables.size() - 1; j >= 0; j--) {
				
				System.out.print(values.get(i).get(j) + " ");

			}
			System.out.print("  |");
//			System.out.print(l1.get(i) + "  | ");
//			System.out.print(l2.get(i)+ " | ");
			System.out.print(result.get(i));
			System.out.println();
		}

	}

	public long timeanalysis(String expression)
	{
	// for time analysis
	final long startTime = System.currentTimeMillis();
	String post = infixToPostfix(expression);
	evaluate (post);
	final long endTime = System.currentTimeMillis();

	// "Total execution time: " + (endTime - startTime)
	return endTime - startTime;
	}
	public LinkedList evaluate(String expression) {
		/** validates that expression is not null or empty **/
		LinkedList<Integer> valuesofvariables;
		while (expression.length() == 0 || expression == null) {
			System.out.println("Enter a valid Statement");
		}

		Integer num1;
		Integer num2;
		Map<Character, Integer> map = new HashMap();

		int rows = (int) Math.pow(2, variables.size());

		for (int u = 0; u < rows; u++) {
			valuesofvariables = new LinkedList<Integer>();

			for (int j = variables.size() - 1; j >= 0; j--) {

				valuesofvariables.add(values.get(u).get(j));
			}
			for (int i = 0; i < variables.size(); i++) {
				map.put(variables.get(i), valuesofvariables.get(i));
			}

			/** for loops for different operations **/

			for (int i = 0; i < expression.length(); i++) {
				if (expression.charAt(i) == '+') {
					num2 = (Integer) Evaluate.peek();
					Evaluate.pop();
					num1 = (Integer) Evaluate.peek();
					Evaluate.pop();

					Evaluate.push(num1 | num2);

				} else if (expression.charAt(i) == '^') {
					num2 = (Integer) Evaluate.peek();
					Evaluate.pop();
					num1 = (Integer) Evaluate.peek();
					Evaluate.pop();

					Evaluate.push(num1 & num2);

				} else if (expression.charAt(i) == '~') {
					num1 = (Integer) Evaluate.peek();
					Evaluate.pop();

					Evaluate.push((Integer) ~num1);

				}
				/** for loop to delete any spaces **/
				else if (expression.charAt(i) == ' ') {
					continue;
					/** pushes any digit to perform operation **/
				} else if (Character.isAlphabetic(expression.charAt(i))) {
					Evaluate.push((Integer) map.get(expression.charAt(i)));

					/** Validates any wring entry **/
				} else {
					throw null;
				}
			}
			/** Variable thats stores the needed result after each operation **/
			int res = (int) (Evaluate.peek());
			result.add(res);
			Evaluate = new Stack();
		}
		return result;

	}

	// too see if the statement is tautology or contradiction
	public Boolean Tautology(LinkedList result) {
		for (Object i : result) {
			if ((Integer) i != 1) {
				return false;
			} else {
				continue;
			}
		}
		return true;
	}

	public boolean Compare(LinkedList list1, LinkedList list2) {
		if (list1.size() != list2.size()) {
			return false;
		}
		for (int i = 0; i < list1.size(); i++) {
			if ((Integer) list1.get(i) != (Integer) list2.get(i)) {
				return false;
			} else
				continue;
		}
		return true;
	}
	public void save() {
		int slcount = 0;
		try {
			// opens file
			FileOutputStream saveFile = new FileOutputStream("SavedObj.sav");

			// Create an ObjectOutputStream to put objects into save file.
			ObjectOutputStream save = new ObjectOutputStream(saveFile);

			for (int i = 0; i < result.size(); i++) {
				save.writeObject(result.get(i));
			}

			// closes save
			save.close();
		} catch (Exception exc) {
			exc.printStackTrace(); // If there was an error, print the info.
		}
	}

}
