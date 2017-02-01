import tester.*; // The tester library
import javalib.worldimages.*; // images, like RectangleImage or OverlayImages
import javalib.funworld.*; // the abstract World class and the big-bang library
import java.awt.Color;

interface IMobile {

  int totalWeight();

  int totalHeight();

  boolean isBalanced();

  IMobile buildMobile(IMobile given, int l, int total);

  int buildHelper(IMobile given, int total, int acc);

  int curWidth();

  int countLeft();

  int countRight();

  WorldImage drawMobile();
}

class Simple implements IMobile {

  int length;
  int weight;
  Color color;

  Simple(int length, int weight, Color color) {
    this.length = length;
    this.weight = weight;
    this.color = color;
  }

  public int totalWeight() {
    return this.weight;
  }

  public int totalHeight() {
    if ((this.weight % 10) == 0) {
      return this.length + this.weight / 10;
    }
    else {
      return this.length + (this.weight - (this.weight % 10)) / 10;
    }
  }

  public boolean isBalanced() {
    return true;
  }

  public IMobile buildMobile(IMobile given, int l, int total) {
    return new Complex(l, this.buildHelper(given, total, 0),
        total - this.buildHelper(given, total, 0), this, given);
  }

  public int buildHelper(IMobile given, int total, int acc) {
    if (new Complex(this.length, total - acc, acc, this, given).isBalanced()) {
      return total - acc;
    }
    else {
      return this.buildHelper(given, total, acc + 1);
    }
  }

  public int curWidth() {
    if ((this.weight % 10) == 0) {
      return this.weight / 10;
    }
    else {
      return (this.weight - (this.weight % 10)) / 10 + 1;
    }
  }

  public int countLeft() {
    if ((this.weight % 10) == 0) {
      return this.weight / 20;
    }
    else {
      return (this.weight - (this.weight % 10)) / 20 + 1;
    }
  }

  public int countRight() {
    if ((this.weight % 10) == 0) {
      return this.weight / 20;
    }
    else {
      return (this.weight - (this.weight % 10)) / 20 + 1;
    }
  }

  @Override
  public WorldImage drawMobile() {
    // TODO Auto-generated method stub
    return null;
  }
}

class Complex implements IMobile {

  int length;
  int leftside;
  int rightside;
  IMobile left;
  IMobile right;

  Complex(int length, int leftside, int rightside, IMobile left, IMobile right) {
    this.length = length;
    this.leftside = leftside;
    this.rightside = rightside;
    this.left = left;
    this.right = right;
  }

  public int totalWeight() {
    return this.left.totalWeight() + this.right.totalWeight();
  }

  public int totalHeight() {
    if (this.left.totalHeight() > this.right.totalHeight()) {
      return this.length + this.left.totalHeight();
    }
    else {
      return this.length + this.right.totalHeight();
    }
  }

  public boolean isBalanced() {
    if (this.leftside * this.left.totalWeight() == this.rightside * this.right.totalWeight()) {
      return this.left.isBalanced() && this.right.isBalanced();
    }
    else {
      return false;
    }
  }

  public IMobile buildMobile(IMobile given, int l, int total) {
    return new Complex(l, this.buildHelper(given, total, 0),
        total - this.buildHelper(given, total, 0), this, given);
  }

  public int buildHelper(IMobile given, int total, int acc) {
    if (new Complex(this.length, total - acc, acc, this, given).isBalanced()) {
      return total - acc;
    }
    else {
      return this.buildHelper(given, total, acc + 1);
    }
  }

  public int curWidth() {
    return Math.max(this.leftside + this.left.countLeft(), this.right.countLeft() - this.rightside)
        + Math.max(this.left.countRight() - this.leftside,
            this.rightside + this.right.countRight());
  }

  public int countLeft() {
    return Math.max(this.leftside + this.left.countLeft(), this.right.countLeft() - this.rightside);
  }

  public int countRight() {
    return Math.max(this.left.countRight() - this.leftside,
        this.rightside + this.right.countRight());
  }

  @Override
  public WorldImage drawMobile() {
    // TODO Auto-generated method stub
    return null;
  }
}

class ExamplesMobiles {

  IMobile exampleSimple = new Simple(2, 20, Color.BLUE);

  IMobile example1 = new Simple(1, 36, Color.BLUE);
  IMobile example2 = new Simple(1, 12, Color.RED);
  IMobile example3 = new Simple(2, 36, Color.RED);
  IMobile example4 = new Simple(1, 60, Color.GREEN);

  IMobile example5 = new Complex(2, 5, 3, example3, example4);
  IMobile example6 = new Complex(2, 8, 1, example2, example5);

  IMobile exampleComplex = new Complex(1, 9, 3, example1, example6);

  IMobile example11 = new Complex(1, 1, 1, exampleComplex, exampleComplex);

  IMobile example7 = new Complex(1, 6, 2, exampleSimple, example4);
  IMobile example8 = new Complex(1, 4, 1, example1, exampleComplex);
  IMobile example9 = new Complex(1, 2, 1, exampleComplex, example11);

  boolean testtotalWeight(Tester t) {
    return t.checkExpect(exampleSimple.totalWeight(), 20)
        && t.checkExpect(example1.totalWeight(), 36) && t.checkExpect(example5.totalWeight(), 96)
        && t.checkExpect(exampleComplex.totalWeight(), 144);
  }

  boolean testtotalHeight(Tester t) {
    return t.checkExpect(exampleSimple.totalHeight(), 4) && t.checkExpect(example1.totalHeight(), 4)
        && t.checkExpect(example5.totalHeight(), 9)
        && t.checkExpect(exampleComplex.totalHeight(), 12);
  }

  boolean testisBalanced(Tester t) {
    return t.checkExpect(exampleSimple.isBalanced(), true)
        && t.checkExpect(exampleComplex.isBalanced(), true);
  }

  boolean testbuildMobile(Tester t) {
    return t.checkExpect(exampleSimple.buildMobile(example4, 1, 8), example7)
        && t.checkExpect(example1.buildMobile(exampleComplex, 1, 5), example8)
        && t.checkExpect(exampleComplex.buildMobile(example11, 1, 3), example9);
  }

  boolean testbuildHelper(Tester t) {
    return t.checkExpect(exampleSimple.buildHelper(example4, 8, 0), 6)
        && t.checkExpect(example1.buildHelper(exampleComplex, 5, 0), 4)
        && t.checkExpect(exampleComplex.buildHelper(example11, 3, 0), 2);
  }

  boolean testcurWidth(Tester t) {
    return t.checkExpect(exampleSimple.curWidth(), 2) && t.checkExpect(example1.curWidth(), 4)
        && t.checkExpect(example5.curWidth(), 13) && t.checkExpect(exampleComplex.curWidth(), 21);
  }

}