import tester.*;

class CakeRecipe {
  
  double flour;
  double sugar;
  double eggs;
  double butter;
  double milk;
  
  CakeRecipe(double flour, double sugar, double eggs, double butter, double milk) {
    this.flour = this.Constrains(flour, sugar, eggs, butter, milk, "Flour");
    this.sugar = this.Constrains(flour, sugar, eggs, butter, milk, "Sugar");
    this.eggs = this.Constrains(flour, sugar, eggs, butter, milk, "Eggs");
    this.butter = this.Constrains(flour, sugar, eggs, butter, milk, "Butter");
    this.milk = this.Constrains(flour, sugar, eggs, butter, milk, "Milk");
  }
  
  double Constrains(double f, double s, double e, double b, double m, String type) {
    if ((f == s) && (e == b) && ((e + m) == f)) {
      return f;
    }
    else {
      throw new IllegalArgumentException("Bad combo: " + type);
    }
  }
}