import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyExpr {
	
	public static void main(String[] args) {
		Map<String,Integer> env = new HashMap<String, Integer>();
		env.put("x", 3);
		env.put("y", 2323);
		System.out.println(myExpr(env));
	
	}
	 
	public static int myExpr(Map<String, Integer> env) {
		return Lit_0(env) + Add_5(env);
	}

	private static int Lit_0(Map<String, Integer> env) {
		return 1;
	}

	private static int Add_5(Map<String, Integer> env) {
		return Seq_3(env) + Var_4(env);
	}

	private static int Seq_3(Map<String, Integer> env) {
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

	private static int Var_1(Map<String, Integer> env) {
		return env.get("x");
	}

	private static int Lit_2(Map<String, Integer> env) {
		return 2;
	}

	private static int Var_4(Map<String, Integer> env) {
		return env.get("y");
	}
}