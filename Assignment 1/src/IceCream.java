interface IIceCream {

}

class EmptyServing implements IIceCream {
  boolean cone;

  EmptyServing(boolean cone) {
    this.cone = cone;
  }

}

class Scooped implements IIceCream {
  IIceCream more;
  String flavor;

  Scooped(IIceCream more, String flavor) {
    this.more = more;
    this.flavor = flavor;
  }
}

class ExamplesIceCream {

  IIceCream cup = new EmptyServing(false);
  IIceCream flavor1 = new Scooped(this.cup, "mint chip");
  IIceCream flavor2 = new Scooped(this.flavor1, "coffee");
  IIceCream flavor3 = new Scooped(this.flavor2, "black raspberry");
  IIceCream order1 = new Scooped(this.flavor3, "caramel swirl");

  IIceCream cone = new EmptyServing(true);
  IIceCream flavor4 = new Scooped(this.cone, "chocolate");
  IIceCream flavor5 = new Scooped(this.flavor1, "vanilla");
  IIceCream order2 = new Scooped(this.flavor2, "bstrawberry");

}
