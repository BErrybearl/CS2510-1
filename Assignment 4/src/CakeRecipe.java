import tester.*;

class CakeRecipe {
  
  double flour;
  double sugar;
  double eggs;
  double butter;
  double milk;
  
  CakeRecipe(double flour, double sugar, double eggs, double butter) {
    this.flour = this.validNumber1(flour, sugar, "Flour");
    this.sugar = this.validNumber1(sugar, flour, "Sugar");
    this.eggs = this.validNumber1(eggs, butter, "Eggs");
    this.butter = this.validNumber1(butter, eggs, "Butter");
  }
  
  CakeRecipe(double flour, double eggs, double milk) {
    this.flour = this.validNumber2(flour, eggs, milk, "Flour");
    this.eggs = this.validNumber2(flour, eggs, milk, "Eggs");
    this.milk = this.validNumber2(flour, eggs, milk, "Milk");
  }
  

  
  double validNumber1(double a, double b, String type) {
    if ((a == b)) {
      return a;
    }
    else {
      throw new IllegalArgumentException("Invalid " + type + ": " + Double.toString(a));
    }
  }
  
  
  double validNumber2(double f, double e, double m, String type) {
    if ((e + m) == f) {
      return m;
    }
    else {
      throw new IllegalArgumentException("Bad combo:");
    }
  }
}