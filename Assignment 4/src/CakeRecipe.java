import tester.Tester;

//contains the ingredients for a cake recipe
public class CakeRecipe {

  double flour;
  double sugar;
  double eggs;
  double butter;
  double milk;

  CakeRecipe(double flour, double sugar, double eggs, double butter, double milk) {
    if (flour == sugar && eggs == butter && (eggs + milk) == sugar) {
      this.flour = flour;
      this.sugar = sugar;
      this.eggs = eggs;
      this.butter = butter;
      this.milk = milk;
    }
    else {
      throw new IllegalArgumentException("Not a perfect cake recipe");
    }
  }

  CakeRecipe(double flour, double eggs, double milk) {
    this(flour, flour, eggs, eggs, milk);
  }

  CakeRecipe(double flour, double eggs, double milk, boolean areVolumes) {
    this(4.25 * flour, 4.25 * flour, 1.75 * eggs, 1.75 * eggs, 8 * milk);
  }

  /**
   * This method checks to see if this cake and another cake have the same
   * recipe.
   * @param other:This is the other cake that is being tested.
   * @return: The method returns true if and only if the recipes have the same
   *          values.
   */
  public boolean sameRecipe(CakeRecipe other) {
    return (Math.abs(this.flour - other.flour) <= .001)
        && (Math.abs(this.sugar - other.sugar) <= .001)
        && (Math.abs(this.eggs - other.eggs) <= .001)
        && (Math.abs(this.butter - other.butter) <= .001)
        && (Math.abs(this.milk - other.milk) <= .001);
  }

}

class ExamplesCakeRecipe {

  CakeRecipe r1 = new CakeRecipe(2, 2, 1, 1, 1);
  CakeRecipe r2 = new CakeRecipe(1326, 1326, 238, 238, 1088);

  CakeRecipe r3 = new CakeRecipe(312, 136, 136, true);

  // Bad Example
  // CakeRecipe bad = new CakeRecipe(1, 1, 1, 1, 1);

  boolean testSameRecipe(Tester t) {
    return t.checkExpect(r1.sameRecipe(r2), false) 
        && t.checkExpect(r1.sameRecipe(r3), false)
        && t.checkExpect(r2.sameRecipe(r3), true);
  }
}
