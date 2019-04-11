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


data Machine
  = machine(str name, list[State] states);
  
data State
  = state(str name, list[Trans] trans);
  
data Trans
  = trans(str event, str target);
  

Machine doors() = machine("Doors", [
  state("closed", [
    trans("open", "opened")
  ]),
  state("opened", [
    trans("close", "closed")
  ])
]);

void compileDoors() {
  compileAST("Doors", #Machine, doors(), |project://peval-java-interpreter/src/RunStm.java|);
}

	