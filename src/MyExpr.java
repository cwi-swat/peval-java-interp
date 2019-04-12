import java.util.HashMap;
import java.util.Map;

public class MyExpr {
	public static int myExpr(Map<String, Integer> env) {
		Map<String, Integer> env2 = new HashMap<String, Integer>();
		env2.putAll(env);
		env2.put("x", Lit$0(env));
		return Let$9(env2);
	}

	private static int Lit$0(Map<String, Integer> env) {
		return 3;
	}

	private static int Let$9(Map<String, Integer> env) {
		Map<String, Integer> env2 = new HashMap<String, Integer>();
		env2.putAll(env);
		env2.put("y", Lit$1(env));
		return Add$8(env2);
	}

	private static int Lit$1(Map<String, Integer> env) {
		return 2323;
	}

	private static int Add$8(Map<String, Integer> env) {
		return Lit$2(env) + Add$7(env);
	}

	private static int Lit$2(Map<String, Integer> env) {
		return 1;
	}

	private static int Add$7(Map<String, Integer> env) {
		return Seq$5(env) + Var$6(env);
	}

	private static int Seq$5(Map<String, Integer> env) {
		int result = 0;
		{
			result = Var$3(env);
			result = Lit$4(env);
		}
		return result;
	}

	private static int Var$3(Map<String, Integer> env) {
		return env.get("x");
	}

	private static int Lit$4(Map<String, Integer> env) {
		return 2;
	}

	private static int Var$6(Map<String, Integer> env) {
		return env.get("y");
	}
}