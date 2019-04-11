import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MyExpr {
	public static int toplevel(Map<String, Integer> env) {
		return Lit_0(env) + Add_5(env);
	}

	public static int Lit_0(Map<String, Integer> env) {
		return 1;
	}

	public static int Add_5(Map<String, Integer> env) {
		return Seq_3(env) + Var_4(env);
	}

	public static int Seq_3(Map<String, Integer> env) {
		int result = 0;
		{
			{
				result = Var_1(env);
			}
			{
				result = Lit_2(env);
			}
		}
		return result;
	}

	public static int Var_1(Map<String, Integer> env) {
		return env.get("x");
	}

	public static int Lit_2(Map<String, Integer> env) {
		return 2;
	}

	public static int Var_4(Map<String, Integer> env) {
		return env.get("y");
	}
}