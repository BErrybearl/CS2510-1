import tester.Tester;

class Book {
  String title;
  String author;
  int price;

  Book(String title, String author, int price) {
    this.title = title;
    this.author = author;
    this.price = price;
  }

}

class Circle {
  int radius;

  Circle(int r) {
    this.radius = r;
  }

}

interface IList<T> {

  IList<T> append(IList<T> lot);

  <U> IList<U> map(IFunc<T, U> func);

  IList<T> filter(IPred<T> pred);

  <U> U accept(IListVisitor<T, U> v);

}

class MtList<T> implements IList<T> {

  public IList<T> append(IList<T> lot) {
    return lot;
  }

  public <U> IList<U> map(IFunc<T, U> func) {
    return new MtList<U>();
  }

  public IList<T> filter(IPred<T> pred) {
    return this;
  }

  public <U> U accept(IListVisitor<T, U> v) {
    return v.visit(this);
  }

}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  public IList<T> append(IList<T> lot) {
    return new ConsList<T>(this.first, this.rest.append(lot));
  }

  public <U> IList<U> map(IFunc<T, U> func) {
    return new ConsList<U>(func.apply(this.first), this.rest.map(func));
  }

  // filter this ConsList<T> using a given IPred<T>
  public IList<T> filter(IPred<T> pred) {
    if (pred.apply(this.first)) {
      return new ConsList<T>(this.first, this.rest.filter(pred));
    }
    else {
      return this.rest.filter(pred);
    }
  }

  public <U> U accept(IListVisitor<T, U> v) {
    return v.visit(this);
  }

}

interface IPred<T> {

  // takes a T and produces a boolean
  boolean apply(T arg);

}

// represents a function object that determines if the given String is the
// letter "I" (case insensitive)
class IsLetterI implements IPred<String> {

  // determines if the given String is the letter "I" (case insensitive)
  public boolean apply(String arg) {
    return arg.equalsIgnoreCase("I");
  }

}

// represents a function object that determines if the Book's author field is an
// empty String
class IsEmptyAuthor implements IPred<Book> {

  // determines if the Book's author field is an empty String
  public boolean apply(Book arg) {
    return arg.author.equals("");
  }
}

interface IFunc<T, U> {
  U apply(T t);

}

class BookTitle implements IFunc<Book, String> {
  public String apply(Book book) {
    return book.title;
  }

}

class CircleArea implements IFunc<Circle, Double> {

  public Double apply(Circle cir) {
    return Math.PI * cir.radius * cir.radius;
  }
}

interface IListVisitor<T, U> {

  U visit(MtList<T> mt);

  U visit(ConsList<T> cons);

}

class MapVisitor<T, U> implements IListVisitor<T, IList<U>> {
  IFunc<T, U> f;

  MapVisitor(IFunc<T, U> f) {
    this.f = f;
  }

  public IList<U> visit(MtList<T> mt) {
    return new MtList<U>();
  }

  public IList<U> visit(ConsList<T> cons) {
    return new ConsList<U>(f.apply(cons.first), cons.rest.accept(this));
  }

  public IList<U> apply(IList<T> t) {
    return t.accept(this);
  }

}

class FilterVisitor<T> implements IListVisitor<T, IList<T>> {
  IPred<T> p;

  FilterVisitor(IPred<T> p) {
    this.p = p;
  }

  public IList<T> visit(MtList<T> mt) {
    return new MtList<T>();
  }

  public IList<T> visit(ConsList<T> cons) {
    if (p.apply(cons.first)) {
      return new ConsList<T>(cons.first, cons.rest.accept(this));
    }
    else {
      return cons.rest.accept(this);
    }
  }

  public IList<T> apply(IList<T> t) {
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

  // Circles
  Circle c1 = new Circle(10);
  Circle c2 = new Circle(30);
  Circle c3 = new Circle(30);

  // lists of Books
  IList<Book> mtlist = new MtList<Book>();
  IList<Book> list1 = new ConsList<Book>(this.book1, new ConsList<Book>(this.book2, this.mtlist));
  IList<Book> someBookList = new ConsList<Book>(this.book3, this.list1);

  // Lists of Circles
  IList<Circle> mtcir = new MtList<Circle>();
  IList<Circle> list2 = new ConsList<Circle>(this.c1,
      new ConsList<Circle>(this.c2, new ConsList<Circle>(this.c3, this.mtcir)));

  // IFunc
  IFunc<Book, String> bookTitle = new BookTitle();
  IPred<String> letter = new IsLetterI();
  IFunc<Circle, Double> circleArea = new CircleArea();
  IPred<Book> mtauthor = new IsEmptyAuthor();

  // Visitor
  MapVisitor<Book, String> mapBook2TitleVisitor = new MapVisitor<Book, String>(this.bookTitle);
  MapVisitor<Circle, Double> mapCircle = new MapVisitor<Circle, Double>(this.circleArea);

  // test Map and MapVisitor
  void testMap(Tester t) {
    t.checkExpect(someBookList.map(bookTitle), someBookList.accept(mapBook2TitleVisitor));

    t.checkExpect(list2.map(circleArea), list2.accept(mapCircle));

    t.checkExpect(mtcir.map(circleArea), mtcir.accept(mapCircle));

  }

  boolean testIsLetterI(Tester t) {
    return t.checkExpect(new IsLetterI().apply("i"), true)
        && t.checkExpect(new IsLetterI().apply("I"), true)
        && t.checkExpect(new IsLetterI().apply("a"), false);
  }

}