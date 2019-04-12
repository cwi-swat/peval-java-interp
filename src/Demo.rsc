module Demo

import PEval;

data Expr 
  = Lit(int val)
  | Add(Expr lhs, Expr rhs)
  | Seq(list[Expr] exprs)
  | Var(str name)
  | Let(str x, Expr arg, Expr body)
  ;
  
Expr myExpr() = 
	Let("x", Lit(3),
	  Let("y", Lit(2323), 
	    Add(Lit(1), Add(Seq([Var("x"), Lit(2)]), Var("y")))));
  

void runIt() {
  compileAST("MyExpr", #Expr, myExpr(), |project://peval-java-interpreter/src/Eval.java|);
}


data Machine
  = machine(str name, list[State] states);
  
data State
  = state(str name, list[Trans] trans);
  
data Trans
  = trans(str event, str target);
  

Machine doors() = machine("Doors", [
  state("closed", [
    trans("open", "opened"),
    trans("lock", "locked")
  ]),
  state("opened", [
    trans("close", "closed")
  ]),
  state("locked", [
    trans("unlocked", "closed")
  ])
]);

void compileDoors() {
  compileAST("Doors", #Machine, doors(), |project://peval-java-interpreter/src/RunStm.java|);
}

	