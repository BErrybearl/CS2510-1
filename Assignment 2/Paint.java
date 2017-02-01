import java.awt.Color;

import tester.*;

interface IPaint {

  Color getFinalColor();

  int countPaints();

  int countMixes();

  int formulaDepth();

  String mixingFormula(int depth);

  String getName();

}

class Solid implements IPaint {

  String name;
  Color color;

  public String getName() {
    return this.name;
  }

  Solid(String name, Color color) {
    this.name = name;
    this.color = color;
  }

  public Color getFinalColor() {
    return this.color;
  }

  public int countPaints() {
    return 1;
  }

  public int countMixes() {
    return 0;
  }

  public int formulaDepth() {
    return 0;
  }

  public String mixingFormula(int depth) {
    return name;
  }

}

class Combo implements IPaint {

  String name;
  IMixture operation;

  public String getName() {
    return name;
  }

  Combo(String name, IMixture mixture) {
    this.name = name;
    this.operation = mixture;
  }

  public Color getFinalColor() {
    return this.operation.getFinalColor();
  }

  public int countPaints() {
    return this.operation.countPaints();
  }

  public int countMixes() {
    return this.operation.countMixes();
  }

  public int formulaDepth() {
    return this.operation.formulaDepth();
  }

  public String mixingFormula(int depth) {
    if (depth == 0) {
      return name;
    }
    else {
      return this.operation.mixingFormula(depth);
    }
  }

}

interface IMixture {

  Color getFinalColor();

  int countPaints();

  int countMixes();

  int formulaDepth();

  String mixingFormula(int depth);

}

class Darken implements IMixture {

  IPaint paint;

  Darken(IPaint paint) {
    this.paint = paint;
  }

  public Color getFinalColor() {
    return this.paint.getFinalColor().darker();
  }

  public int countPaints() {
    return this.paint.countPaints() + 1;
  }

  public int countMixes() {
    return this.paint.countMixes() + 1;
  }

  public int formulaDepth() {
    return this.paint.formulaDepth() + 1;
  }

  public String mixingFormula(int depth) {
    if (depth == 0) {
      return "(" + this.paint.getName() + ")";
    }
    else {
      return "darken(" + this.paint.mixingFormula(depth - 1) + ")";
    }
  }

}

class Brighten implements IMixture {

  IPaint paint;

  Brighten(IPaint paint) {
    this.paint = paint;
  }

  public Color getFinalColor() {
    return this.paint.getFinalColor().brighter();
  }

  public int countPaints() {
    return this.paint.countPaints() + 1;
  }

  public int countMixes() {
    return this.paint.countMixes() + 1;
  }

  public int formulaDepth() {
    return this.paint.formulaDepth() + 1;
  }

  public String mixingFormula(int depth) {
    if (depth == 0) {
      return "(" + this.paint.getName() + ")";
    }
    else {
      return "brighten(" + this.paint.mixingFormula(depth - 1) + ")";
    }
  }

}

class Blend implements IMixture {

  IPaint paint1;
  IPaint paint2;

  Blend(IPaint color1, IPaint color2) {
    this.paint1 = color1;
    this.paint2 = color2;
  }

  public Color getFinalColor() {
    int red = (paint1.getFinalColor().getRed() + paint2.getFinalColor().getRed()) / 2;
    int blue = (paint1.getFinalColor().getBlue() + paint2.getFinalColor().getBlue()) / 2;
    int green = (paint1.getFinalColor().getGreen() + paint2.getFinalColor().getGreen()) / 2;
    return new Color(red, green, blue);
  }

  public int countPaints() {
    return this.paint1.countPaints() + this.paint2.countPaints();
  }

  public int countMixes() {
    return this.paint1.countMixes() + this.paint2.countMixes() + 1;
  }

  public int formulaDepth() {
    return (int) Math.max(this.paint1.formulaDepth(), this.paint2.formulaDepth()) + 1;
  }

  public String mixingFormula(int depth) {
    if (depth == 0) {
      return "(" + this.paint1.getName() + ", " + this.paint2.getName() + ")";
    }
    else {
      return "blend(" + this.paint1.mixingFormula(depth - 1) + ", "
          + this.paint2.mixingFormula(depth - 1) + ")";
    }
  }

}

class ExamplesPaint {

  IPaint red = new Solid("red", new Color(255, 0, 0));
  IPaint green = new Solid("green", new Color(0, 255, 0));
  IPaint blue = new Solid("blue", new Color(0, 0, 255));

  IPaint purple = new Combo("purple", new Blend(red, blue));
  IPaint darkPurple = new Combo("dark purple", new Darken(purple));
  IPaint khaki = new Combo("khaki", new Blend(red, green));
  IPaint yellow = new Combo("yellow", new Brighten(khaki));
  IPaint mauve = new Combo("mauve", new Blend(purple, khaki));
  IPaint pink = new Combo("pink", new Brighten(mauve));
  IPaint coral = new Combo("coral", new Blend(pink, khaki));
  IPaint navyBlue = new Combo("navy blue", new Darken(blue));
  IPaint lightBlue = new Combo("light blue", new Brighten(blue));
  IPaint orange = new Combo("orange", new Blend(red, yellow));

  boolean testGetFinalColor(Tester t) {
    return t.checkExpect(this.red.getFinalColor(), new Color(255, 0, 0))
        && t.checkExpect(this.purple.getFinalColor(), new Color(127, 0, 127))
        && t.checkExpect(this.khaki.getFinalColor(), new Color(127, 127, 0))
        && t.checkExpect(this.mauve.getFinalColor(), new Color(127, 63, 63));
  }

  boolean testcountPaints(Tester t) {
    return t.checkExpect(this.red.countPaints(), 1) && t.checkExpect(this.purple.countPaints(), 2)
        && t.checkExpect(this.mauve.countPaints(), 4) && t.checkExpect(this.coral.countPaints(), 7);
  }

  boolean testcountMixes(Tester t) {
    return t.checkExpect(this.red.countMixes(), 0) && t.checkExpect(this.purple.countMixes(), 1)
        && t.checkExpect(this.darkPurple.countMixes(), 2)
        && t.checkExpect(this.mauve.countMixes(), 3);
  }

  boolean testformulaDepth(Tester t) {
    return t.checkExpect(this.red.formulaDepth(), 0) && t.checkExpect(this.purple.formulaDepth(), 1)
        && t.checkExpect(this.darkPurple.formulaDepth(), 2)
        && t.checkExpect(new Blend(this.darkPurple, this.darkPurple).formulaDepth(), 3);
  }

  boolean testmixingFormula(Tester t) {
    return t.checkExpect(this.red.mixingFormula(0), "red")
        && t.checkExpect(this.pink.mixingFormula(0), "pink")
        && t.checkExpect(this.pink.mixingFormula(1), "brighten(mauve)")
        && t.checkExpect(this.pink.mixingFormula(2), "brighten(blend(purple, khaki))")
        && t.checkExpect(this.pink.mixingFormula(3),
            "brighten(blend(blend(red, blue), blend(red, green)))");
  }

}