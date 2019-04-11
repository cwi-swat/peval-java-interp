import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyExpr {
	public static void main(String[] args) {
		System.out.println(myExpr(new HashMap<String, Integer>()));
	
	}
	
	public static int myExpr(Map<String, Integer> env) {
		Map<String, Integer> env2 = new HashMap<String, Integer>();
		env2.putAll(env);
		env2.put("x", Lit_0(env));
		return Let_9(env2);
	}

	private static int Lit_0(Map<String, Integer> env) {
		return 3;
	}

	private static int Let_9(Map<String, Integer> env) {
		Map<String, Integer> env2 = new HashMap<String, Integer>();
		env2.putAll(env);
		env2.put("y", Lit_1(env));
		return Add_8(env2);
	}

	private static int Lit_1(Map<String, Integer> env) {
		return 2323;
	}

	private static int Add_8(Map<String, Integer> env) {
		return Lit_2(env) + Add_7(env);
	}

	private static int Lit_2(Map<String, Integer> env) {
		return 1;
	}

	private static int Add_7(Map<String, Integer> env) {
		return Seq_5(env) + Var_6(env);
	}

	private static int Seq_5(Map<String, Integer> env) {
		int result = 0;
		{
			{
				result = Var_3(env);
			}
			{
				result = Lit_4(env);
			}
		}
		return result;
	}

	private static int Var_3(Map<String, Integer> env) {
		return env.get("x");
	}

	private static int Lit_4(Map<String, Integer> env) {
		return 2;
	}

	private static int Var_6(Map<String, Integer> env) {
		return env.get("y");
	}
}