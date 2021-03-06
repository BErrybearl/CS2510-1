import tester.Tester;

interface IComparator<T> {

  int compare(T t1, T t2);

}

class BooksByTitle implements IComparator<Book> {

  public int compare(Book b1, Book b2) {
    return b1.title.compareTo(b2.title);

  }

}

class BooksByAuthor implements IComparator<Book> {

  public int compare(Book b1, Book b2) {
    return b1.author.compareTo(b2.author);
  }

}

class BooksByPrice implements IComparator<Book> {

  public int compare(Book b1, Book b2) {
    return b1.price - b2.price;

  }

}

abstract class ABST<T> {

  IComparator<T> order;

  ABST(IComparator<T> order) {
    this.order = order;
  }

  abstract boolean isLeaf();

  abstract ABST<T> insert(T t);

  abstract T getLeast();

  abstract ABST<T> getAllButLeast();

  abstract boolean sameLeaf(Leaf<T> given);

  abstract boolean sameNode(Node<T> given);

  abstract boolean sameTree(ABST<T> given);

  abstract boolean sameData(ABST<T> given);

  abstract boolean sameAsList(IList<T> given);

  abstract IList<T> buildList(IList<T> list);

}

class Leaf<T> extends ABST<T> {

  Leaf(IComparator<T> order) {
    super(order);
  }

  boolean isLeaf() {
    return true;
  }

  ABST<T> insert(T t) {
    return new Node<T>(this.order, t, new Leaf<T>(this.order), new Leaf<T>(this.order));
  }

  T getLeast() {
    throw new RuntimeException("No least item of an empty tree");
  }

  ABST<T> getAllButLeast() {
    throw new RuntimeException("No items in an empty tree");
  }

  boolean sameLeaf(Leaf<T> given) {
    return true;
  }

  boolean sameNode(Node<T> given) {
    return false;
  }

  boolean sameTree(ABST<T> given) {
    return given.sameLeaf(this);
  }

  boolean sameData(ABST<T> given) {
    return given.sameLeaf(this);
  }

  boolean sameAsList(IList<T> given) {
    return given.isMt();
  }

  IList<T> buildList(IList<T> list) {
    return list;
  }
}

class Node<T> extends ABST<T> {

  T data;
  ABST<T> left;
  ABST<T> right;

  Node(IComparator<T> order, T data, ABST<T> left, ABST<T> right) {
    super(order);
    this.data = data;
    this.left = left;
    this.right = right;
  }

  boolean isLeaf() {
    return false;
  }

  ABST<T> insert(T t) {
    if (this.order.compare(this.data, t) < 0) {
      return new Node<T>(this.order, this.data, this.left, this.right.insert(t));
    }
    else {
      return new Node<T>(this.order, this.data, this.left.insert(t), this.right);
    }
  }

  T getLeast() {
    if (this.left.isLeaf()) {
      return this.data;
    }
    else {
      return this.left.getLeast();
    }
  }

  ABST<T> getAllButLeast() {

    if (this.order.compare(data, this.getLeast()) == 0) {
      return this.right;
    }

    else {
      return new Node<T>(this.order, this.data, this.left.getAllButLeast(), this.right);
    }

  }

  boolean sameLeaf(Leaf<T> given) {
    return false;
  }

  boolean sameNode(Node<T> given) {
    return this.order == given.order && this.data == given.data && this.left.sameTree(given.left)
        && this.right.sameTree(given.right);
  }

  boolean sameTree(ABST<T> given) {
    return given.sameNode(this);
  }

  boolean sameData(ABST<T> given) {
    return this.order.compare(this.getLeast(), given.getLeast()) == 0
        && this.getAllButLeast().sameData(given.getAllButLeast());
  }

  boolean sameAsList(IList<T> given) {
    return this.buildList(new MtList<T>()).reverse().sameList(given);
  }

  IList<T> buildList(IList<T> list) {
    return this.getAllButLeast().buildList(new ConsList<T>(this.getLeast(), list));
  }
}

interface IList<T> {

  boolean isMt();

  boolean sameList(IList<T> given);

  IList<T> reverse();

  IList<T> addend(T t);

  ABST<T> buildTree(ABST<T> btree);

}

class MtList<T> implements IList<T> {

  public boolean isMt() {
    return true;
  }

  public boolean sameList(IList<T> given) {
    return given.isMt();
  }

  public IList<T> reverse() {
    return this;
  }

  public IList<T> addend(T t) {
    return new ConsList<T>(t, new MtList<T>());
  }

  public ABST<T> buildTree(ABST<T> btree) {

    return btree;
  }

}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  public boolean isMt() {
    return false;
  }

  public boolean sameList(IList<T> given) {
    if (given.isMt()) {
      return false;
    }
    else {
      return this.sameListHelper((ConsList<T>) given);
    }
  }

  boolean sameListHelper(ConsList<T> lot) {
    return this.first.equals(lot.first) && this.rest.sameList(lot.rest);
  }

  public IList<T> reverse() {
    return this.rest.reverse().addend(this.first);
  }

  public IList<T> addend(T t) {
    return new ConsList<T>(this.first, this.rest.addend(t));
  }

  public ABST<T> buildTree(ABST<T> btree) {
    return this.rest.buildTree(btree.insert(first));
  }

}

class Book {

  String title;
  String author;
  int price;

  Book(String title, String author, int price) {
    this.title = title;
    this.author = author;
    this.price = price;
  }

  public boolean sameBook(Book given) {
    return this.title.equals(given.title) && this.author.equals(given.author)
        && this.price == given.price;
  }
}

class ExamplesABST {

  Book b1 = new Book("Animal Farm", "George Orwell", 10);
  Book b2 = new Book("How to Design Programs", "Matthias Felleisen", 1);
  Book b3 = new Book("Lord of the Rings", "J.R.R. Tolkien", 5);
  Book b4 = new Book("ZZZZZZ", "Connor", 3);
  Book b5 = new Book("AA", "Connor", 3);
  Book b6 = new Book("qrstuvwxyz", "Connor", 3);

  IComparator<Book> titleComp = new BooksByTitle();
  IComparator<Book> authorComp = new BooksByAuthor();
  IComparator<Book> priceComp = new BooksByPrice();

  ABST<Book> lfTitle = new Leaf<Book>(titleComp);
  ABST<Book> lfAuthor = new Leaf<Book>(authorComp);
  ABST<Book> lfPrice = new Leaf<Book>(priceComp);

  ABST<Book> treeB6 = new Node<Book>(titleComp,
      b6,
      lfTitle,
      lfTitle);

  ABST<Book> treeTitle = new Node<Book>(titleComp,
      b2,
      new Node<Book>(titleComp, b1, lfTitle, lfTitle),
      new Node<Book>(titleComp, b3, lfTitle, lfTitle));

  ABST<Book> treeTitleAfterInsertB4 = new Node<Book>(titleComp,
      b2,
      new Node<Book>(titleComp, b1, lfTitle, lfTitle),
      new Node<Book>(titleComp, b3, lfTitle,
          new Node<Book>(titleComp, b4, new Leaf<Book>(titleComp), new Leaf<Book>(titleComp))));

  ABST<Book> treeTitleAfterInsertB5 = new Node<Book>(titleComp,
      b2,
      new Node<Book>(titleComp, b1, new Node<Book>(titleComp, b5, lfTitle, lfTitle), lfTitle),
      new Node<Book>(titleComp, b3, lfTitle, lfTitle));

  ABST<Book> treeTitleAfterGetAllButLeast = new Node<Book>(titleComp,
      b2,
      new Leaf<Book>(titleComp),
      new Node<Book>(titleComp, b3, lfTitle, lfTitle));

  ABST<Book> treeTitleSameData = new Node<Book>(titleComp,
      b1,
      lfTitle,
      new Node<Book>(titleComp, b2, lfTitle, new Node<Book>(titleComp, b3, lfTitle, lfTitle)));

  ABST<Book> treeTitleAfterBuildTree = new Node<Book>(titleComp,
      b6,
      new Node<Book>(titleComp, b1, lfTitle, 
          new Node<Book>(titleComp, b2, lfTitle, 
              new Node<Book>(titleComp, b3, lfTitle, lfTitle))),
      lfTitle);
          

  ABST<Book> treeAuthor = new Node<Book>(authorComp,
      b3,
      new Node<Book>(authorComp, b1, lfTitle, lfTitle),
      new Node<Book>(authorComp, b2, lfTitle, lfTitle));

  ABST<Book> treePrice = new Node<Book>(priceComp,
      b3,
      new Node<Book>(priceComp, b2, lfTitle, lfTitle),
      new Node<Book>(priceComp, b1, lfTitle, lfTitle));

  IList<Book> mt = new MtList<Book>();

  IList<Book> b6List = new ConsList<Book>(b6, mt);
  
  IList<Book> b6Listplus = new ConsList<Book>(b6, new ConsList<Book>(b1, mt));

  IList<Book> treeTitleList = new ConsList<Book>(b1, new ConsList<Book>(b2,
      new ConsList<Book>(b3, mt)));

  IList<Book> treeTitleListAfterBuildListmt = new ConsList<Book>(b3, new ConsList<Book>(b2,
      new ConsList<Book>(b1, mt)));

  IList<Book> treeTitleListAfterBuildListb6 = new ConsList<Book>(b3, new ConsList<Book>(b2,
      new ConsList<Book>(b1, new ConsList<Book>(b6, mt))));

  IList<Book> treeAuthorList = new ConsList<Book>(b1, new ConsList<Book>(b3,
      new ConsList<Book>(b2, mt)));
  
  boolean testReverse(Tester t) {
    return t.checkExpect(mt.reverse(), mt)
        && t.checkExpect(treeTitleList.reverse(), treeTitleListAfterBuildListmt)
        && t.checkExpect(treeTitleListAfterBuildListmt.reverse(), treeTitleList);
  }

  boolean testAddend(Tester t) {
    return t.checkExpect(mt.addend(b6), b6List)
        && t.checkExpect(b6List.addend(b1), b6Listplus);
  }
  
  boolean testSameAsListddend(Tester t) {
    return t.checkExpect(lfTitle.sameAsList(mt), true)
        && t.checkExpect(lfTitle.sameAsList(treeTitleList), false)
        && t.checkExpect(treeTitle.sameAsList(treeTitleList), true)
        && t.checkExpect(treePrice.sameAsList(treeTitleList), false);
  }
  
  boolean testSameList(Tester t) {
    return t.checkExpect(mt.sameList(mt), true)
        && t.checkExpect(mt.sameList(treeTitleList), false)
        && t.checkExpect(treeTitleList.sameList(treeTitleList), true)
        && t.checkExpect(treeTitleList.sameList(treeAuthorList), false);
  }
  
  boolean testIsMt(Tester t) {
    return t.checkExpect(mt.isMt(), true)
        && t.checkExpect(treeAuthorList.isMt(), false)
        && t.checkExpect(treeTitleList.isMt(), false)
        && t.checkExpect(treeTitleListAfterBuildListb6.isMt(), false);
  }
  
  // test buildTree
  boolean testBuildTree(Tester t) {
    return t.checkExpect(treeTitleList.buildTree(treeB6), treeTitleAfterBuildTree);
  }

  // test buildList
  boolean testBuildList(Tester t) {
    return t.checkExpect(treeTitle.buildList(mt), treeTitleListAfterBuildListmt)
        && t.checkExpect(treeTitle.buildList(b6List), treeTitleListAfterBuildListb6);
  }
}