// Assignment 1
// Changzong Liu
// changzongliu

interface IIcecream {

}

class EmptyServing implements IIcecream {
  boolean cone;

  EmptyServing(boolean cone) {
    this.cone = cone;
  }

}

class Scooped implements IIcecream {
  IIcecream more;
  String flavor;

  Scooped(IIcecream more, String flavor) {
    this.more = more;
    this.flavor = flavor;
  }
}

class ExamplesIceCream {

  IIcecream cup = new EmptyServing(false);
  IIcecream flavor1 = new Scooped(this.cup, "mint chip");
  IIcecream flavor2 = new Scooped(this.flavor1, "coffee");
  IIcecream flavor3 = new Scooped(this.flavor2, "black raspberry");
  IIcecream order1 = new Scooped(this.flavor3, "caramel swirl");

  IIcecream cone = new EmptyServing(true);
  IIcecream flavor4 = new Scooped(this.cone, "chocolate");
  IIcecream flavor5 = new Scooped(this.flavor1, "vanilla");
  IIcecream order2 = new Scooped(this.flavor2, "bstrawberry");

}