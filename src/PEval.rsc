module PEval

import Node;
import lang::java::\syntax::Java15;
import Type;
import IO;
import ParseTree;
import String;


// partialer, terpreter, pinterp, pevalier, syntactic partial evaluation

/*
 * Conventions/assumptions on the interpreter:
 *  - method must be called eval and have no @override 
 *  - always use this.x for field access; all fields assumed to be static AST nodes
 *  - can only call eval on fields (no looking inside)
 *  - only foreach on List nodes; no break and continue in such loops
 *  - no modifications of fields (all deeply final)
 *  - currently only supports int,str, list, and node
 *  - static AST classes nested in a single class
 * Peval:
 *  - inline String, int etc literals (this.name => "x")
 *  - inline eval calls to expr, (this.lhs.eval(env) => methodFor_23(env)
 *  - unroll loops (for (Expr expr: this.exprs) {S} => {S_1} {S_2} {S_i}...)
 */

void compileAST(str name, type[node] meta, node n, loc eval) {
  start[CompilationUnit] cu = parse(#start[CompilationUnit], eval);
  newLoc = eval[file = "<name>.java"];
  pkg = packageName(cu);
  imps = importDecs(cu);
  mMap = methodMap(n);
  ClassDec cd = pevalJava(name, meta, n, mMap, cu);
  CompilationUnit newCu;
  if (pkg != "") {
    PackageName pkgName = [PackageName]pkg;
    newCu = (CompilationUnit)`package <PackageName pkgName>;
                             '
                             '<ImportDec* imps>
                             '
                             '<ClassDec cd>`;
  }
  else {
    newCu = (CompilationUnit)`<ImportDec* imps>
    							'
    							'<ClassDec cd>`;
  }
  writeFile(newLoc, "<newCu>");
}

ClassDec pevalJava(str name, type[node] meta, node n, map[node,str] mMap, start[CompilationUnit] cu) {
   Id className = [Id]name;
   cb = methodsForNode(meta, n, mMap, cu, name);
   return (ClassDec)`public class <Id className> <ClassBody cb>`; 
}

ClassBody methodsForNode(type[node] meta, node n, map[node,str] mMap, start[CompilationUnit] cu, str name, bool toplevel = true) {
  ClassBody cb = (ClassBody)`{}`;
  // NB: top-down is essential because toplevel
  top-down visit (n) {
    case node x: {
      MethodDec m = methodForNode(meta, x, mMap, cu, name, toplevel);
      if ((ClassBody)`{<ClassBodyDec* cds>}` := cb) {
        cb = (ClassBody)`{<ClassBodyDec* cds>
                        '  <MethodDec m>
                        '}`;
      }
      toplevel = false;
    }
  }
  return cb;
}

MethodDec methodForNode(type[node] meta, node n, map[node,str] mMap, start[CompilationUnit] cu, str toplevelName, bool toplevel) {

  name = getName(n);
  kids = getChildren(n);
  fMap = fieldMap(meta, name);

  methodName = mMap[n];
  
  MethodDec newMethod = methodForNode(cu, name, toplevel ? uncapitalize(toplevelName) : methodName, toplevel);
  
  
  for (str f <- fMap) {
    Id fieldId = [Id]f;
    switch (kids[fMap[f]]) {
      case str s: {
         newMethod = visit (newMethod) {
           case (Expr)`this.<Id fieldId>` => [Expr]"\"<s>\""
         }
      }
      case int i: {
         newMethod = visit (newMethod) {
           case (Expr)`this.<Id fieldId>` => [Expr]"<i>"
         }
      }
      case node x:  {
         newMethod = visit (newMethod) {
           // matching against fieldId directly doesn't work
           case (Expr)`this.<Id someX>.eval(<{Expr ","}* args>)`: {
              if ("<someX>" == f) {
                Id kidMethodId = [Id]mMap[x];
                insert (Expr)`<Id kidMethodId>(<{Expr ","}* args>)`;
              }
           }
         }
        
      }
      case list[node] xs:  {
        newMethod = visit (newMethod) {
          case (Stm)`for (<Type _> <Id var>: this.<Id someX>) <Stm b>`: {
            if ("<someX>" == f) {
              Block newBlock = (Block)`{}`;
              for (node k <- xs) {
                 Id kidMethodId = [Id]mMap[k];
                 b2 = visit (b) {
                   case (Expr)`<Id var>.eval(<{Expr ","}* args>)` 
                     => (Expr)`<Id kidMethodId>(<{Expr ","}* args>)`
                 }
                 if ((Block)`{<BlockStm* bs>}` := newBlock) {
                   newBlock = (Block)`{<BlockStm* bs>
                                     ' <Stm b2>}`;
                 }
              }
              insert (Stm)`<Block newBlock>`;
            }
          }
        }
      }
    }
  }
  return newMethod;
} 

MethodDec methodForNode(start[CompilationUnit] cu, str name, str methodName, bool toplevel) {
  if (/(ClassDec)`static class <Id x> extends <ClassType _> <ClassBody body>` := cu
     || /(ClassDec)`static class <Id x> <ClassBody body>` := cu, "<x>" == capitalize(name)) {
    if (/(MethodDec)`public <ResultType t> eval(<{FormalParam ","}* fs>) <MethodBody mb>` := body
      || /(MethodDec)`@Override public <ResultType t> eval(<{FormalParam ","}* fs>) <MethodBody mb>` := body) {
      Id mId = [Id]methodName;
      if (toplevel) {
        return (MethodDec)`public static <ResultType t> <Id mId>(<{FormalParam ","}* fs>) <MethodBody mb>`;
      }
      return (MethodDec)`private static <ResultType t> <Id mId>(<{FormalParam ","}* fs>) <MethodBody mb>`;
    }  
  }
  throw "Could not find interpreter class <name>";
}

// NB: there's sharing happening here, but it's ok
// (i.e. same subnodes will map to single method)
map[node, str] methodMap(node n) {
  int i = 0;
  map[node, str] m = ();
  visit (n) {
    case node x: {
      m[x] = "<getName(x)>$<i>";
      i += 1;
    }
  }
  return m;
}

map[str, int] fieldMap(type[node] meta, str name) {
  visit (meta.definitions) {
    case cons(label(name, _), list[Symbol] args, _, _): {
	    map[str, int] m = ();
	    int i = 0; 
	    for (label(str f, _) <- args) {
	      m[f] = i;
	      i += 1;
	    }
	    return m;
    }
  }
}

str packageName(start[CompilationUnit] cu) {
  top-down visit (cu) {
    case (PackageDec)`<Anno* _> package <PackageName pkg>;`:
      return "<pkg>";
  }
  return "";
}

ImportDec* importDecs(start[CompilationUnit] cu) {
  top-down visit (cu) {
    case ImportDec* imps: return imps;
  }
}
