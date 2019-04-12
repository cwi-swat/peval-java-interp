import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Eval {

	
	
	// also a marker for AST
	interface IEval {
		int eval(Map<String, Integer> env);
	}
	
	static abstract class Expr implements IEval {
		
	}
	
	static class Lit extends Expr {
		private final int value;
		
		public Lit(int value) {
			this.value = value;
		}

		public int eval(Map<String, Integer> env) {
			return this.value;
		}
	}
	
	static class Add extends Expr {
		private final Expr lhs, rhs;

		public Add(Expr lhs, Expr rhs) {
			this.lhs = lhs;
			this.rhs = rhs;
		}
		
		public int eval(Map<String, Integer> env) {
			return this.lhs.eval(env) + this.rhs.eval(env);
		}
	}
	
	static class Seq extends Expr {
		private final List<Expr> exprs;
		
		public Seq(List<Expr> exprs) {
			this.exprs = Collections.unmodifiableList(exprs);
		}

		public int eval(Map<String, Integer> env) {
			int result = 0;
			for (Expr expr: this.exprs) 
				result = expr.eval(env);
			return result;
		}
		
	}
	
	static class Var extends Expr {
		private final String name;
		
		public Var(String name) {
			this.name = name;
		}

		public int eval(Map<String, Integer> env) {
			return env.get(this.name);
		}
		
	}
	
	static class Let extends Expr {
		private final String x;
		private final Expr arg, body;
		
		public Let(String x, Expr arg, Expr body) {
			this.x = x;
			this.arg = arg;
			this.body = body;
		}

		public int eval(Map<String, Integer> env) {
			Map<String,Integer> env2 = new HashMap<String, Integer>();
			env2.putAll(env);
			env2.put(this.x, this.arg.eval(env));
			return this.body.eval(env2);
		}
		
	}
	
}