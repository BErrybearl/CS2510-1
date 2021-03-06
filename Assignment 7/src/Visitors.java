import tester.Tester;

// represent an arithmetic expression
interface IArith {

  // accept the given IArithVisitor<R>
  <R> R accept(IArithVisitor<R> visitor);
}

// Represent a Double-precision number
class Const implements IArith {

  double num;

  // the constructor
  Const(Double num) {
    this.num = num;
  }

  /* FIELDS:
   * this.num                                 -- double
   * 
   * METHODS:
   * this.accept(IArithVisitor<R> visitor)    -- R
   */

  // accept the given IArithVisitor<R>
  public <R> R accept(IArithVisitor<R> visitor) {
    return visitor.visitConst(this);
  }
}

// represent an arithmetic expression
class Formula implements IArith {

  IFunc2<Double, Double, Double> fun;
  String name;
  IArith left;
  IArith right;

  // the constructor
  Formula(IFunc2<Double, Double, Double> fun, String name, IArith left, IArith right) {
    this.fun = fun;
    this.name = name;
    this.left = left;
    this.right = right;
  }

  /* FIELDS:
   * this.fun                                       -- IFunc2<Double, Double, Double>
   * this.name                                      -- String
   * this.left                                      -- IArith
   * this.right                                     -- IArith
   * 
   * METHODS:
   * this.accept(IArithVisitor<R> visitor)          -- R
   * 
   * METHODS OF FIELDS:
   * this.fun.apply(Double a1, Double a2)           -- Double
   * this.left.accept(IArithVisitor<R> visitor)     -- R
   * this.right.accept(IArithVisitor<R> visitor)    -- R
   */

  // accept the given IArithVisitor<R>
  public <R> R accept(IArithVisitor<R> visitor) {
    return visitor.visitFormula(this);
  }
}

// take two As and produce an R
interface IFunc2<A1, A2, R> {

  // apply function using two As
  R apply(A1 a1, A2 a2);
}

// function object to add two Doubles
class FuncAdd implements IFunc2<Double, Double, Double> {

  /* METHODS:
   * this.apply(Double d1, Double d2)   -- Double
   */

  // add the values of the two given Doubles
  public Double apply(Double d1, Double d2) {
    return d1 + d2;
  }
}

// function object to subtract two Doubles
class FuncSub implements IFunc2<Double, Double, Double> {

  /* METHODS:
   * this.apply(Double d1, Double d2)   -- Double
   */

  // add the values of the two given Doubles
  public Double apply(Double d1, Double d2) {
    return d1 - d2;
  }
}

// function object to multiply two doubles
class FuncMul implements IFunc2<Double, Double, Double> {

  /* METHODS:
   * this.apply(Double d1, Double d2)   -- Double
   */

  // add the values of the two given Consts
  public Double apply(Double d1, Double d2) {
    return d1 * d2;
  }
}

// function object to multiply two doubles
class FuncDiv implements IFunc2<Double, Double, Double> {

  /* METHODS:
   * this.apply(Double d1, Double d2)   -- Double
   */

  // add the values of the two given Consts
  public Double apply(Double d1, Double d2) {
    return d1 / d2;
  }
}

// function object works over IAriths
interface IArithVisitor<R> {

  // visit the given Const
  R visitConst(Const arg);

  // visit the given Formula
  R visitFormula(Formula arg);

  // apply this visitor to the given IArith
  R apply(IArith arg);
}

// visitor that evaluates the given IArith and produces a Double
class EvalVisitor implements IArithVisitor<Double> {

  /* METHODS:
   * this.visitConst(Const arg)       -- Double
   * this.visitFormula(Formula arg)   -- Double
   * this.apply(IArith arg)           -- Double
   */

  // visit the given Const
  public Double visitConst(Const arg) {
    return new Double(arg.num);
  }

  // visit the given Formula
  public Double visitFormula(Formula arg) {
    return arg.fun.apply(arg.left.accept(this), arg.right.accept(this));
  }

  // apply this EvalVisitor to the given IArith
  public Double apply(IArith arg) {
    return arg.accept(this);
  }
}

// visitor that produces a String expression for the given IArith
class PrintVisitor implements IArithVisitor<String> {

  /* METHODS:
   * this.visitConst(Const arg)       -- String
   * this.visitFormula(Formula arg)   -- String
   * this.apply(IArith arg)           -- String
   */

  // visit the given Const
  public String visitConst(Const arg) {
    return Double.toString(arg.num);
  }

  // visit the given Formula
  public String visitFormula(Formula arg) {
    return "(" + arg.name + " " + arg.left.accept(this) + " " + arg.right.accept(this) + ")";
  }

  // apply this PrintVisitor to the given IArith
  public String apply(IArith arg) {
    return arg.accept(this);
  }
}

// visitor that doubles every Const in the given IArith
class DoublerVisitor implements IArithVisitor<IArith> {

  /* METHODS:
   * this.visitConst(Const arg)       -- IArith
   * this.visitFormula(Formula arg)   -- IArith
   * this.apply(IArith arg)           -- IArith
   */

  // visit the given Const
  public IArith visitConst(Const arg) {
    return new Const(arg.num * 2);
  }

  // visit the given Formula
  public IArith visitFormula(Formula arg) {
    return new Formula(arg.fun, arg.name, arg.left.accept(this), arg.right.accept(this));
  }

  // apply this DoublerVisitor to the given IArith
  public IArith apply(IArith arg) {
    return arg.accept(this);
  }
}

// visitor that determines if every Const in the IArith is less than 10
class AllSmallVisitor implements IArithVisitor<Boolean> {

  /* METHODS:
   * this.visitConst(Const arg)       -- Boolean
   * this.visitFormula(Formula arg)   -- Boolean
   * this.apply(IArith arg)           -- Boolean
   */

  // visit the given Const
  public Boolean visitConst(Const arg) {
    return arg.num < 10;
  }

  // visit the given Formula
  public Boolean visitFormula(Formula arg) {
    return arg.left.accept(this) && arg.right.accept(this);
  }

  // apply this AllSmallVisitor to the given IArith
  public Boolean apply(IArith arg) {
    return arg.accept(this);
  }
}

// visitor that determines if any formula in the given IArith do not divide by 0
class NoDivBy0 implements IArithVisitor<Boolean> {

  /* METHODS:
   * this.visitConst(Const arg)       -- Boolean
   * this.visitFormula(Formula arg)   -- Boolean
   * this.apply(IArith arg)           -- Boolean
   */

  // visit the given Const
  public Boolean visitConst(Const arg) {
    return true;
  }

  // visit the given Formula
  public Boolean visitFormula(Formula arg) {
    if (arg.name.equals("div")) {
      return arg.left.accept(this) 
          && arg.right.accept(this) 
          && Math.abs(arg.right.accept(new EvalVisitor())) > 0.0001;
    }
    else {
      return arg.left.accept(this) && arg.right.accept(this);
    }
  }

  // apply this NoDivBy0 to the given IArith
  public Boolean apply(IArith arg) {
    return arg.accept(this);
  }
}

class ExamplesVisitors {

  // functions
  IFunc2<Double, Double, Double> funcAdd = new FuncAdd();
  IFunc2<Double, Double, Double> funcSub = new FuncSub();
  IFunc2<Double, Double, Double> funcMul = new FuncMul();
  IFunc2<Double, Double, Double> funcDiv = new FuncDiv();

  // visitors
  IArithVisitor<Double> evalVisitor = new EvalVisitor();
  IArithVisitor<String> printVisitor = new PrintVisitor();
  IArithVisitor<IArith> doublerVisitor = new DoublerVisitor();
  IArithVisitor<Boolean> allSmallVisitor = new AllSmallVisitor();
  IArithVisitor<Boolean> noDivBy0Visitor = new NoDivBy0();

  // examples
  IArith c0 = new Const(0.0);
  IArith c1 = new Const(5.0);
  IArith c2 = new Const(10.0);
  IArith f1 = new Formula(funcAdd, "plus", c1, c1);
  IArith f2 = new Formula(funcSub, "minus", c2, c1);
  IArith f3 = new Formula(funcMul, "mul", c1, c2);
  IArith f4 = new Formula(funcDiv, "div", c2, c1);
  IArith f5 = new Formula(funcAdd, "plus", c1, f1);
  IArith f6 = new Formula(funcDiv, "div", c1, c0);

  IArith c1AfterDoublerVisitor = new Const(10.0);
  IArith f1AfterDoublerVisitor = new Formula(funcAdd, "plus", c1AfterDoublerVisitor,
      c1AfterDoublerVisitor);
  IArith f5AfterDoublerVisitor = new Formula(funcAdd, "plus", c1AfterDoublerVisitor,
      f1AfterDoublerVisitor);

  // test EvalVisitor
  boolean testEvalVisitor(Tester t) {
    return t.checkExpect(c1.accept(evalVisitor), 5.0) 
        && t.checkExpect(f1.accept(evalVisitor), 10.0)
        && t.checkExpect(f2.accept(evalVisitor), 5.0) 
        && t.checkExpect(f3.accept(evalVisitor), 50.0)
        && t.checkExpect(f4.accept(evalVisitor), 2.0)
        && t.checkExpect(f5.accept(evalVisitor), 15.0);
  }

  // test PrintVisitor
  boolean testPrintVisitor(Tester t) {
    return t.checkExpect(c1.accept(printVisitor), "5.0")
        && t.checkExpect(f1.accept(printVisitor), "(plus 5.0 5.0)")
        && t.checkExpect(f2.accept(printVisitor), "(minus 10.0 5.0)")
        && t.checkExpect(f3.accept(printVisitor), "(mul 5.0 10.0)")
        && t.checkExpect(f4.accept(printVisitor), "(div 10.0 5.0)")
        && t.checkExpect(f5.accept(printVisitor), "(plus 5.0 (plus 5.0 5.0))");
  }

  // test DoublerVisitor
  boolean testDoublerVisitor(Tester t) {
    return t.checkExpect(c1.accept(doublerVisitor), c1AfterDoublerVisitor)
        && t.checkExpect(f1.accept(doublerVisitor), f1AfterDoublerVisitor)
        && t.checkExpect(f5.accept(doublerVisitor), f5AfterDoublerVisitor);
  }

  // test AllSmallVisitor
  boolean testAllSmallVisitor(Tester t) {
    return t.checkExpect(c1.accept(allSmallVisitor), true)
        && t.checkExpect(c2.accept(allSmallVisitor), false)
        && t.checkExpect(f1.accept(allSmallVisitor), true)
        && t.checkExpect(f2.accept(allSmallVisitor), false)
        && t.checkExpect(f4.accept(allSmallVisitor), false)
        && t.checkExpect(f5.accept(allSmallVisitor), true);
  }

  // test NoDivBy0
  boolean testNoDivBy0(Tester t) {
    return t.checkExpect(f1.accept(noDivBy0Visitor), true)
        && t.checkExpect(f4.accept(noDivBy0Visitor), true)
        && t.checkExpect(f6.accept(noDivBy0Visitor), false);
  }
}
