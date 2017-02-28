interface IArith {
  <R> R accept(IArithVisitor<R> visitor);
}

class Const implements IArith {
  double num;

  Const(double num) {
    this.num = num;
  }

  public <R> R accept(IArithVisitor<R> visitor) {
    return visitor.visitConst(this);
  }
}

class Formula implements IArith {
  IFunc2<Double, Double, Double> fun;
  String name;
  IArith left;
  IArith right;

  Formula(IFunc2<Double, Double, Double> fun, String name, IArith left, IArith right) {
    this.fun = fun;
    this.name = name;
    this.left = left;
    this.right = right;
  }

  public <R> R accept(IArithVisitor<R> visitor) {
    return visitor.visitFormula(this);
  }
}

interface IFunc2<A1, A2, R> {
  R apply(A1 a1, A2 a2);
}

class Div implements IFunc2<Double, Double, Double> {
  public Double apply(Double a1, Double a2) {
    return a1 / a2;
  }
}

class Min implements IFunc2<Double, Double, Double> {
  public Double apply(Double a1, Double a2) {
    return a1 - a2;
  }
}

interface IArithVisitor<R> {
  R visitConst(Const c);

  R visitFormula(Formula f);
}

class EvalVisitor implements IArithVisitor<Double> {
  public Double visitConst(Const c) {
    return c.num;
  }

  public Double visitFormula(Formula f) {
    return f.fun.apply(f.left.accept(this), f.right.accept(this));
  }

  public Double apply(IArith arg) {
    return arg.accept(this);
  }
}

class PrintVisitor implements IArithVisitor<String> {
  public String visitConst(Const c) {
    return Double.toString(c.num);
  }

  public String visitFormula(Formula f) {
    return "(" + f.name + " " + f.left.accept(this) + " " + f.right.accept(this) + ")";
  }

  public String apply(IArith arg) {
    return arg.accept(this);
  }
}

class DoublerVisitor implements IArithVisitor<IArith> {
  public IArith visitConst(Const c) {
    return new Const(2 * c.num);
  }

  public IArith visitFormula(Formula f) {
    return new Formula(f.fun, f.name, f.left.accept(this), f.right.accept(this));
  }

  public IArith apply(IArith arg) {
    return arg.accept(this);
  }
}

class AllSmallVisitor implements IArithVisitor<Boolean> {
  public Boolean visitConst(Const c) {
    return c.num < 10;
  }

  public Boolean visitFormula(Formula f) {
    return f.left.accept(this) && f.right.accept(this);
  }

  public boolean apply(IArith arg) {
    return arg.accept(this);
  }
}

class NoDivBy0 implements IArithVisitor<Boolean> {
  
  public Boolean visitConst(Const c) {
    return (c.num >= 0.0001 || c.num <= -0.0001) && c.num != 0.0;
  }

  public Boolean visitFormula(Formula f) {
    if (f.name.equals("div")) {
      return new Const(f.right.accept(new EvalVisitor())).accept(this) && f.right.accept(this);
    }
    else {
      return true;
    }
  }

  public boolean apply(IArith arg) {
    return arg.accept(this);
  }
}