module Demo

import PEval;

data AExpr 
  = Lit(int val)
  | Add(AExpr lhs, AExpr rhs)
  | Seq(list[AExpr] exprs)
  | Var(str name)
  | Let(str x, AExpr arg, AExpr body)
  ;
  
AExpr myExpr() = 
	Let("x", Lit(3),
	  Let("y", Lit(2323), 
	    Add(Lit(1), Add(Seq([Var("x"), Lit(2)]), Var("y")))));
  

void runIt() {
  compileAST("MyExpr", #AExpr, myExpr(), |project://peval-java-interpreter/src/Eval.java|);
}

/*
public static void main(String[] args) {
		Map<String,Integer> env = new HashMap<String, Integer>();
		env.put("x", 3);
		env.put("y", 2323);
		System.out.println(myExpr(env));
	
	}
*/
	