import tester.Tester;

// represent a list of T
interface IList<T> { 

  // append this IList<T> to the given IList<T>
  IList<T> append(IList<T> lot);

  // map this IList<T> to an IList<U> using the given IFunc<T, U>)
  <U> IList<U> apply(IFunc<T, U> func);

  // filter this IList<T> using a given IFunc<T, U>
  IList<T> filter(IPred<T> pred);
}

// represent an empty IList<T>
class MtList<T> implements IList<T> { 

  // append this MtList<T> to the given IList<T>
  public IList<T> append(IList<T> lot) {
    return lot;
  }

  // map this MtList<T> to an IList<U> using the given IFunc<T, U>)
  public <U> IList<U> apply(IFunc<T, U> func) {
    return new MtList<U>();
  }

  // filter this MtList<T> using a given IFunc<T, U>
  public IList<T> filter(IPred<T> pred) {
    return new MtList<T>();
  }
}

//represent a populated IList<T>
class ConsList<T> implements IList<T> { 

  T first;
  IList<T> rest;

  // the constructor
  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  // append this ConsList<T> to the given IList<T>
  public IList<T> append(IList<T> lot) {
    return new ConsList<T>(this.first, this.rest.append(lot));
  }

  // map this ConsList<T> to an IList<U> using the given IFunc<T, U>)
  public <U> IList<U> apply(IFunc<T, U> func) {
    return new ConsList<U>(func.apply(this.first), this.rest.apply(func));
  }

  // filter this ConsList<T> using a given IPred<T>
  public IList<T> filter(IPred<T> pred) {
    if (pred.apply(this.first)) {
      return new ConsList<T>(this.first, this.rest.filter(pred));
    }
    else return this.rest.filter(pred);
  }
}

// represents a Circle
class Book {
  String title;
  String author;

  // the constructor
  Book(String title, String author) {
    this.title = title;
    this.author = author;
  }
}

//represents a Circle
class Circle {
  int x, y;
  int radius;

  // the constructor
  Circle(int x, int y, int radius) {
    this.x = x;
    this.y = y;
    this.radius = radius;
  }
}

// represent a function object that takes a T and produces a U
interface IFunc<T, U> {

  // takes a T and produces a U
  U apply(T arg);
}

// represents a function object that adds one to the given Integer
class PlusOne implements IFunc<Integer, Integer> {

  // add one to the given Integer
  public Integer apply(Integer arg) {
    return arg + 1;
  } 
}

// represents a function object that adds "!" to the end of the given String
class AddExclamationPoint implements IFunc<String, String> {

  // add "!" to the end of the given String
  public String apply(String arg) {
    return arg + "!";
  }
}

// represents a function object that extracts the title and author from the given Book
class BookToString implements IFunc<Book, String> {

  // extracts the title and author from the given Book
  public String apply(Book arg) {
    return arg.title + " by " + arg.author;
  }
}

// represents a function object that computes the circumference of a Circle
class CircleCircumference implements IFunc<Circle, Double> {

  // computes the circumference of a given Circle
  public Double apply(Circle arg) {
    return 2 * Math.PI * arg.radius;
  }
}

// represents a function object that takes a T and produces a boolean
interface IPred<T> {

  // takes a T and produces a boolean
  boolean apply(T arg);
}

// represents a function object that determines if the given String is the letter "I" (case insensitive)
class IsLetterI implements IPred<String> {

  // determines if the given String is the letter "I" (case insensitive)
  public boolean apply(String arg) {
    return arg.toLowerCase().equals("i");
  } 
}

// represents a function object that determines if the Book's author field is an empty String
class IsEmptyAuthor implements IPred<Book> {

  // determines if the Book's author field is an empty String
  public boolean apply(Book arg) {
    return arg.author.equals("");
  }
}


// examples and tests
class TestVisitor{ 

  IList<String> s1 = new ConsList<String>("hey", new ConsList<String>("hi", new MtList<String>()));
  IList<String> s1AppendS1 = new ConsList<String>("hey", new ConsList<String>("hi", new ConsList<String>("hey", new ConsList<String>("hi", new MtList<String>()))));

  IList<Integer> intList = new ConsList<Integer>(new Integer(1), new ConsList<Integer>(new Integer(2), new ConsList<Integer>(new Integer(3), new MtList<Integer>())));
  IList<Integer> intListAfterAppend = new ConsList<Integer>(new Integer(2), new ConsList<Integer>(new Integer(3), new ConsList<Integer>(new Integer(4), new MtList<Integer>())));

  IList<String> stringList = new ConsList<String>("Hey", new ConsList<String>("i", new ConsList<String>("hello", new ConsList<String>("hi", new MtList<String>()))));
  IList<String> stringListAfterAddExclamationPoint = new ConsList<String>("Hey!", new ConsList<String>("i!", new ConsList<String>("hello!", new ConsList<String>("hi!", new MtList<String>()))));
  IList<String> stringListAfterFilter = new ConsList<String>("i", new MtList<String>());

  Book b1 = new Book("Animal Farm", "George Orwell");
  Book b2 = new Book("HTDP", "");

  IList<Book> bookList = new ConsList<Book>(b1, new ConsList<Book>(b2, new MtList<Book>()));
  IList<Book> bookListAfterFilterIsEmptyAuthor = new ConsList<Book>(b2, new MtList<Book>());
  IList<String> bookListAfterBookToString = new ConsList<String>("Animal Farm by George Orwell", new ConsList<String>("HTDP by ", new MtList<String>()));

  Circle c1 = new Circle(0, 0, 100);
  Circle c2 = new Circle(10, 5, 10);

  IList<Circle> circleList = new ConsList<Circle>(c1, new ConsList<Circle>(c2, new MtList<Circle>()));
  IList<Double> circleListAfterCircleCircumference = new ConsList<Double>(200 * Math.PI, new ConsList<Double>(20 * Math.PI, new MtList<Double>()));


  // test PlusOne
  boolean testPlusOne(Tester t) {
    return t.checkExpect(new PlusOne().apply(new Integer(1)), new Integer(2));
  }

  // test AddExclamationPoint
  boolean testAddExclamationPoint(Tester t) {
    return t.checkExpect(new AddExclamationPoint().apply("y"), "y!");
  }

  // test IsLetterI
  boolean testIsLetterI(Tester t) {
    return t.checkExpect(new IsLetterI().apply("i"), true)
        && t.checkExpect(new IsLetterI().apply("I"), true)
        && t.checkExpect(new IsLetterI().apply("a"), false);
  }

  // test IsEmptyAuthor
  boolean testIsEmptyAuthor(Tester t) {
    return t.checkExpect(new IsEmptyAuthor().apply(b1), false)
        && t.checkExpect(new IsEmptyAuthor().apply(b2), true);
  }

  // test BookToString
  boolean testBookToString(Tester t) {
    return t.checkExpect(new BookToString().apply(b1), "Animal Farm by George Orwell");
  }

  // test CircleToString
  boolean testCircleCircumference(Tester t) {
    return t.checkExpect(new CircleCircumference().apply(c1), 100 * Math.PI * 2);
  }

  // test append
  boolean testAppend(Tester t) {
    return t.checkExpect(s1.append(new MtList<String>()), s1)
        && t.checkExpect(s1.append(s1), s1AppendS1);
  }

  // test map
  boolean testMap(Tester t) {
    return t.checkExpect(intList.apply(new PlusOne()), intListAfterAppend)
        && t.checkExpect(stringList.apply(new AddExclamationPoint()), stringListAfterAddExclamationPoint)
        && t.checkExpect(bookList.apply(new BookToString()), bookListAfterBookToString)
        && t.checkExpect(circleList.apply(new CircleCircumference()), circleListAfterCircleCircumference);
  }

  // test filter
  boolean testFilter(Tester t) {
    return t.checkExpect(stringList.filter(new IsLetterI()), stringListAfterFilter)
        && t.checkExpect(bookList.filter(new IsEmptyAuthor()), bookListAfterFilterIsEmptyAuthor);
  }

}


/* notes

IDocVisitor<R> {

  R visitBook(Book b)
  R visitWiki(Wiki w)
}

Book {
  <R> R accept(IDocVisitor idv)
    return idv.visitBook(this);
}

Wiki {
  <R> R accept(IDocVisitor idv) {
    return idv.visitWiki(this);
}

class Length implements IDocVisitor<Integer> {
  Integer visitBook(Book b) {
    return b.pages;
  }
  Integer visitWiki(Wiki w) {
    return w.words/2000;
  }

} */
