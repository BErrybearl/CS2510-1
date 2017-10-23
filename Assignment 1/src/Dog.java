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

  Dog huffle = new Dog("Hufflepuff", "Wheaten Terrier", 2012, "TX", true);
  Dog pearl = new Dog("Pearl", "Labrador Retriever", 2016, "MA", false);

}
