// Assignment 1
// Changzong Liu
// changzongliu

class Dog {
  String name;
  String breed;
  int yob;
  String state;
  boolean hypoallergenic;

  Dog(String name, String breed, int yob, String state, boolean hypoallergnic) {
    this.name = name;
    this.breed = breed;
    this.yob = yob;
    this.state = state;
    this.hypoallergenic = hypoallergnic;
  }
}

class ExamplesDog {

  Dog d1 = new Dog("Hufflepuff", "Wheaten Terrier", 2012, "TX", true);
  Dog d2 = new Dog("Pearl", "Labrador Retriever", 2016, "MA", false);

}
