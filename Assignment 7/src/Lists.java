import tester.Tester;

// class representing a book
class Book {
  String title;
  String author;
  int price;

  // the constructor
  Book(String title, String author, int price) {
    this.title = title;
    this.author = author;
    this.price = price;
  }

  /* FIELDS:
   * this.title     -- String
   * this.author    -- Author
   * this.price     -- int
   */
}

// class representing a circle
class Circle {
  int radius;

  // the constructor
  Circle(int r) {
    this.radius = r;
  }

  /* FIELDS:
   * this.radius     -- int
   */
}

// represents a list of T
interface IList<T> {

  // represent a list of T
  IList<T> append(IList<T> lot);

  // map this IList<T> to an IList<U> using the given IFunc<T, U>)
  <U> IList<U> map(IFunc<T, U> func);

  // filter this IList<T> using a given IFunc<T, U>
  IList<T> filter(IPred<T> pred);

  // return the result of applying the given visitor to this IList<T>
  <U> U accept(IListVisitor<T, U> v);
}

//represent an empty IList<T>
class MtList<T> implements IList<T> {

  /* METHODS
   * this.append(IList<T> lot)            -- IList<T>
   * this.map(IFunc<T, U> func)           -- IList<U>
   * this.filter(IPred<T> pred)           -- IList<T>
   * this.accept(IListVisitor<T, U> v)    -- U
   */

  //append this MtList<T> to the given IList<T>
  public IList<T> append(IList<T> lot) {
    /* METHODS FOR PARAMETERS: Methods of IList<T> */
    return lot;
  }

  //map this MtList<T> to an IList<U> using the given IFunc<T, U>)
  public <U> IList<U> map(IFunc<T, U> func) {
    /* METHODS FOR PARAMETERS: Methods of IFunc<T, U> */
    return new MtList<U>();
  }

  //filter this MtList<T> using a given IFunc<T, U>
  public IList<T> filter(IPred<T> pred) {
    /* METHODS FOR PARAMETERS: Methods of IPred<T> */
    return this;
  }

  //return the result of applying the given visitor to this MtList<T>
  public <U> U accept(IListVisitor<T, U> v) {
    /* METHODS FOR PARAMETERS: Methods of IListVisitor<T, U> */
    return v.visit(this);
  }
}

//represent a populated IList<T>
class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  //the constructor
  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  /* FIELDS:
   * this.first                           -- T
   * this.rest                            -- IList<T>
   * 
   * METHODS
   * this.append(IList<T> lot)            -- IList<T>
   * this.map(IFunc<T, U> func)           -- IList<U>
   * this.filter(IPred<T> pred)           -- IList<T>
   * this.accept(IListVisitor<T, U> v)    -- U
   * 
   * METHODS OF FIELDS:
   * this.rest.append(IList<T> lot)            -- IList<T>
   * this.rest.map(IFunc<T, U> func)           -- IList<U>
   * this.rest.filter(IPred<T> pred)           -- IList<T>
   * this.rest.accept(IListVisitor<T, U> v)    -- U
   */

  //append this ConsList<T> to the given IList<T>
  public IList<T> append(IList<T> lot) {
    /* METHODS FOR PARAMETERS: Methods of IList<T> */
    return new ConsList<T>(this.first, this.rest.append(lot));
  }

  //map this ConsList<T> to an IList<U> using the given IFunc<T, U>)
  public <U> IList<U> map(IFunc<T, U> func) {
    /* METHODS FOR PARAMETERS: Methods of IFunc<T, U> */
    return new ConsList<U>(func.apply(this.first), this.rest.map(func));
  }

  // filter this ConsList<T> using a given IPred<T>
  public IList<T> filter(IPred<T> pred) {
    /* METHODS FOR PARAMETERS: Methods of IPred<T> */
    if (pred.apply(this.first)) {
      return new ConsList<T>(this.first, this.rest.filter(pred));
    }
    else {
      return this.rest.filter(pred);
    }
  }

  //return the result of applying the given visitor to this ConsList<T>
  public <U> U accept(IListVisitor<T, U> v) {
    /* METHODS FOR PARAMETERS: Methods of IListVisitor<T, U> */
    return v.visit(this);
  }
}

//represents a function object that takes a T and produces a boolean
interface IPred<T> {

  // takes a T and produces a boolean
  boolean apply(T arg);
}

// represents a function object that determines if the given String is the
// letter "I" (case insensitive)
class IsLetterI implements IPred<String> {

  /* METHODS:
   * this.apply(String arg)     -- boolean
   */

  // determines if the given String is the letter "I" (case insensitive)
  public boolean apply(String arg) {
    return arg.equalsIgnoreCase("I");
  }
}

// represents a function object that determines if the Book's author field is an
// empty String
class IsEmptyAuthor implements IPred<Book> {

  /* METHODS:
   * this.apply(Book arg)     -- boolean
   */

  // determines if the Book's author field is an empty String
  public boolean apply(Book arg) {
    return arg.author.equals("");
  }
}

//represent a function object that takes a T and produces a U
interface IFunc<T, U> {
  U apply(T t);
}

// represents a function object that takes a Book and returns its title
class BookTitle implements IFunc<Book, String> {

  /* METHODS:
   * this.apply(Book book)     -- String
   */

  // return the title of the given Book
  public String apply(Book book) {
    return book.title;
  }
}

//represents a function object that takes a Circle and returns its area
class CircleArea implements IFunc<Circle, Double> {

  /* METHODS:
   * this.apply(Circle cir)     -- Double
   */

  // compute the area of the given Circle
  public Double apply(Circle cir) {
    return Math.PI * cir.radius * cir.radius;
  }
}

// represents a function object that extracts the title and author from the
// given Book
class BookToString implements IFunc<Book, String> {

  /* METHODS:
   * this.apply(Book arg)     -- String
   */

  // extracts the title and author from the given Book
  public String apply(Book arg) {
    return arg.title + " by " + arg.author;
  }
}

// represents a function object that computes the circumference of a Circle
class CircleCircumference implements IFunc<Circle, Double> {

  /* METHODS:
   * this.apply(Circle arg)     -- Double
   */

  // computes the circumference of a given Circle
  public Double apply(Circle arg) {
    return 2 * Math.PI * arg.radius;
  }
}

// represents a function object that adds one to the given Integer
class PlusOne implements IFunc<Integer, Integer> {

  /* METHODS:
   * this.apply(Integer arg)     -- Integer
   */

  // add one to the given Integer
  public Integer apply(Integer arg) {
    return arg + 1;
  }
}

// represents a function object that adds "!" to the end of the given String
class AddExclamationPoint implements IFunc<String, String> {

  /* METHODS:
   * this.apply(String arg)     -- String
   */

  // add "!" to the end of the given String
  public String apply(String arg) {
    return arg + "!";
  }
}

//An IListVisitor is a function over ILists
interface IListVisitor<T, U> {

  // visit the given MtList<T>
  U visit(MtList<T> mt);

  // visit the given ConsList<T>
  U visit(ConsList<T> cons);
}

//A MapVisitor is a function over ILists
class MapVisitor<T, U> implements IListVisitor<T, IList<U>> {
  IFunc<T, U> f;

  // the constructor
  MapVisitor(IFunc<T, U> f) {
    this.f = f;
  }

  /* FIELDS:
   * this.f                     -- IFunc<T, U>
   * 
   * METHODS:
   * this.visit(MtList<T> mt)     -- IList<U>
   * this.visit(ConsList<T> cons) -- IList<U>
   * this.apply(IList<T> t)       -- IList<U>
   * 
   * METHODS OF FIELDS:
   * this.f.apply(T t)            -- U
   */

  // visit the given MtList<T>
  public IList<U> visit(MtList<T> mt) {
    /* METHODS FOR PARAMETERS: Methods of MtList<T> */
    return new MtList<U>();
  }

  // visit the given ConsList<T>
  public IList<U> visit(ConsList<T> cons) {
    /* METHODS FOR PARAMETERS: Methods of ConsList<T> */
    return new ConsList<U>(f.apply(cons.first), cons.rest.accept(this));
  }

  // apply the mapping to the given IList<T>
  public IList<U> apply(IList<T> t) {
    /* METHODS FOR PARAMETERS: Methods of IList<T> */
    return t.accept(this);
  }
}

//A FilterVisitor is a function over ILists
class FilterVisitor<T> implements IListVisitor<T, IList<T>> {
  IPred<T> p;

  // the constructor
  FilterVisitor(IPred<T> p) {
    this.p = p;
  }
  
  /* FIELDS:
   * this.p                     -- IPred<T>
   * 
   * METHODS:
   * this.visit(MtList<T> mt)     -- IList<T>
   * this.visit(ConsList<T> cons) -- IList<T>
   * this.apply(IList<T> t)       -- IList<T>
   * 
   * METHODS OF FIELDS:
   * this.f.apply(IList<T> t)            -- IList<T>
   */

  // visit the given MtList<T>
  public IList<T> visit(MtList<T> mt) {
    /* METHODS FOR PARAMETERS: Methods of MtList<T> */
    return new MtList<T>();
  }

  // visit the the given ConsList<T>
  public IList<T> visit(ConsList<T> cons) {
    /* METHODS FOR PARAMETERS: Methods of ConsList<T> */
    if (p.apply(cons.first)) {
      return new ConsList<T>(cons.first, cons.rest.accept(this));
    }
    else {
      return cons.rest.accept(this);
    }
  }

  // apply the filter to the given IList<T>
  public IList<T> apply(IList<T> t) {
    /* METHODS FOR PARAMETERS: Methods of IList<T> */
    return t.accept(this);
  }
}

class ExamplesList {

  // examples to test append and reverse
  IList<Integer> mt = new MtList<Integer>();
  IList<Integer> l1 = new ConsList<Integer>(1,
      new ConsList<Integer>(2, new ConsList<Integer>(3, mt)));
  IList<Integer> l2 = new ConsList<Integer>(4,
      new ConsList<Integer>(5, new ConsList<Integer>(6, mt)));
  IList<Integer> l3 = new ConsList<Integer>(4,
      new ConsList<Integer>(5, new ConsList<Integer>(6, this.l1)));
  IList<Integer> l4 = new ConsList<Integer>(3,
      new ConsList<Integer>(2, new ConsList<Integer>(1, mt)));
  IList<String> s1 = new ConsList<String>("hey", new ConsList<String>("hi", new MtList<String>()));
  IList<String> s1AppendS1 = new ConsList<String>("hey", new ConsList<String>("hi",
      new ConsList<String>("hey", new ConsList<String>("hi", new MtList<String>()))));

  IList<Integer> intList = new ConsList<Integer>(new Integer(1), new ConsList<Integer>(
      new Integer(2), new ConsList<Integer>(new Integer(3), new MtList<Integer>())));
  IList<Integer> intListAfterAppend = new ConsList<Integer>(new Integer(2), new ConsList<Integer>(
      new Integer(3), new ConsList<Integer>(new Integer(4), new MtList<Integer>())));

  IList<String> stringList = new ConsList<String>("Hey", new ConsList<String>("i",
      new ConsList<String>("hello", new ConsList<String>("hi", new MtList<String>()))));
  IList<String> stringListAfterAddExclamationPoint = new ConsList<String>("Hey!",
      new ConsList<String>("i!",
          new ConsList<String>("hello!", new ConsList<String>("hi!", new MtList<String>()))));
  IList<String> stringListAfterFilter = new ConsList<String>("i", new MtList<String>());

  // test the method append
  void testAppend(Tester t) {
    t.checkExpect(this.l2.append(this.l1), this.l3);
    t.checkExpect(this.mt.append(this.l1), this.l1);
    t.checkExpect(this.l1.append(this.mt), this.l1);
  }

  // Books
  Book book1 = new Book("AB", "PO", 160);
  Book book2 = new Book("CD", "KL", 250);
  Book book3 = new Book("HHK", "FF", 50);

  Book b1 = new Book("Animal Farm", "George Orwell", 10);
  Book b2 = new Book("HTDP", "", 5);

  IList<Book> bookList = new ConsList<Book>(b1, new ConsList<Book>(b2, new MtList<Book>()));
  IList<Book> bookListAfterFilterIsEmptyAuthor = new ConsList<Book>(b2, new MtList<Book>());
  IList<String> bookListAfterBookToString = new ConsList<String>("Animal Farm by George Orwell",
      new ConsList<String>("HTDP by ", new MtList<String>()));

  // Circles
  Circle c1 = new Circle(10);
  Circle c2 = new Circle(30);
  Circle c3 = new Circle(30);
  Circle c4 = new Circle(0);
  Circle c5 = new Circle(10);

  // lists of Books
  IList<Book> mtlist = new MtList<Book>();
  IList<Book> list1 = new ConsList<Book>(this.book1, new ConsList<Book>(this.book2, this.mtlist));
  IList<Book> someBookList = new ConsList<Book>(this.book3, this.list1);

  // Lists of Circles
  IList<Circle> mtcir = new MtList<Circle>();
  IList<Circle> list2 = new ConsList<Circle>(this.c1,
      new ConsList<Circle>(this.c2, new ConsList<Circle>(this.c3, this.mtcir)));
  IList<Circle> circleList = new ConsList<Circle>(c4,
      new ConsList<Circle>(c2, new MtList<Circle>()));
  IList<Double> circleListAfterCircleCircumference = new ConsList<Double>(0.0,
      new ConsList<Double>(60 * Math.PI, new MtList<Double>()));

  // IFunc
  IFunc<Book, String> bookTitle = new BookTitle();
  IPred<String> letter = new IsLetterI();
  IFunc<Circle, Double> circleArea = new CircleArea();
  IPred<Book> mtauthor = new IsEmptyAuthor();

  // Visitor
  MapVisitor<Book, String> mapBook2TitleVisitor = new MapVisitor<Book, String>(this.bookTitle);
  MapVisitor<Circle, Double> mapCircle = new MapVisitor<Circle, Double>(this.circleArea);

  // test PlusOne
  boolean testPlusOne(Tester t) {
    return t.checkExpect(new PlusOne().apply(new Integer(1)), new Integer(2));
  }
  
  // test BookTitle
  boolean testBookTitle(Tester t) {
    return t.checkExpect(new BookTitle().apply(b1), "Animal Farm")
        && t.checkExpect(new BookTitle().apply(b2), "HTDP");
  }
  
  // test CircleArea
  boolean testCircleArea(Tester t) {
    return t.checkExpect(new CircleArea().apply(c1), Math.PI * 100)
        && t.checkExpect(new CircleArea().apply(c2), Math.PI * 900);
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
    return t.checkInexact(new CircleCircumference().apply(c1), 10 * Math.PI * 2, 0.001);
  }

  // test map
  boolean testMap(Tester t) {
    return t.checkExpect(intList.map(new PlusOne()), intListAfterAppend)
        && t.checkExpect(stringList.map(new AddExclamationPoint()),
            stringListAfterAddExclamationPoint)
        && t.checkExpect(bookList.map(new BookToString()), bookListAfterBookToString)
        && t.checkInexact(circleList.map(new CircleCircumference()),
            circleListAfterCircleCircumference, 0.001);
  }

  // test filter
  boolean testFilter(Tester t) {
    return t.checkExpect(stringList.filter(new IsLetterI()), stringListAfterFilter)
        && t.checkExpect(bookList.filter(new IsEmptyAuthor()), bookListAfterFilterIsEmptyAuthor);
  }

  // test Map and MapVisitor
  boolean testMapMapVisitor(Tester t) {
    return t.checkExpect(someBookList.map(bookTitle), someBookList.accept(mapBook2TitleVisitor))
        && t.checkExpect(list2.map(circleArea), list2.accept(mapCircle))
        && t.checkExpect(mtcir.map(circleArea), mtcir.accept(mapCircle));
  }

  // test filter and FilterVisitor
  boolean testFilterFilterVisitor(Tester t) {
    return t.checkExpect(stringList.filter(new IsLetterI()),
        stringList.accept(new FilterVisitor<String>(new IsLetterI())))
        && t.checkExpect(bookList.filter(new IsEmptyAuthor()),
            bookList.accept(new FilterVisitor<Book>(new IsEmptyAuthor())));
  }
}