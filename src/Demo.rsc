module Demo

import PEval;

data AExpr 
  = Lit(int val)
  | Add(AExpr lhs, AExpr rhs)
  | Seq(list[AExpr] exprs)
  | Var(str name)
  ;
  
AExpr myExpr() = Add(Lit(1), Add(Seq([Var("x"), Lit(2)]), Var("y")));
  

void runIt() {
  compileAST("MyExpr", #AExpr, myExpr(), |project://peval-java-interpreter/src/Eval.java|);
}
