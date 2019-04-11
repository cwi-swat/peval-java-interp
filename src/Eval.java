import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Eval {

	/*
	 * Conventions: 
	 *  - always use this. for field access; all fields assumed to be static AST nodes
	 *  - can only call eval on fields (no looking inside)
	 *  - only foreach on List nodes
	 * Peval:
	 *  - inline String, int etc literals (this.name => "x")
	 *  - inline eval calls to expr, (this.lhs.eval(env) => methodFor_23(env)
	 *  - unroll loops (for (Expr expr: this.exprs) {S} => {S_1} {S_2} {S_i}...)
	 */
	
	// also a marker for AST
	interface IEval {
		int eval(Map<String, Integer> env);
	}
	
	static abstract class Expr implements IEval {
		
	}
	
	static class Lit extends Expr {
		private int value;
		
		public Lit(int value) {
			this.value = value;
		}

		@Override
		public int eval(Map<String, Integer> env) {
			return this.value;
		}
	}
	
	static class Add extends Expr {
		private Expr lhs, rhs;

		public Add(Expr lhs, Expr rhs) {
			this.lhs = lhs;
			this.rhs = rhs;
		}
		
		@Override
		public int eval(Map<String, Integer> env) {
			return this.lhs.eval(env) + this.rhs.eval(env);
		}
	}
	
	static class Seq extends Expr {
		private List<Expr> exprs;
		
		public Seq(List<Expr> exprs) {
			this.exprs = Collections.unmodifiableList(exprs);
		}

		@Override
		public int eval(Map<String, Integer> env) {
			int result = 0;
			for (Expr expr: this.exprs) {
				result = expr.eval(env);
			}
			return result;
		}
		
	}
	
	static class Var extends Expr {
		private String name;
		
		public Var(String name) {
			this.name = name;
		}

		@Override
		public int eval(Map<String, Integer> env) {
			return env.get(this.name);
		}
		
	}
	

}