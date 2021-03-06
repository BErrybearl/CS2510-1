import tester.Tester;

// compare objects in different ways
interface IComparator<T> {

  // compare two Ts
  int compare(T t1, T t2);
}

// compare the titles of two Books
class BooksByTitle implements IComparator<Book> {

  /* METHODS
   * this.compare(Book b1, Book b2)   -- int
   */

  // compare two Ts
  public int compare(Book b1, Book b2) {
    return b1.title.compareTo(b2.title);
  }
}

// compare the authors of two Books
class BooksByAuthor implements IComparator<Book> {

  /* METHODS
   * this.compare(Book b1, Book bt)   -- int
   */

  // compare two Books
  public int compare(Book b1, Book b2) {
    return b1.author.compareTo(b2.author);
  }
}

//compare the price of these two Books
class BooksByPrice implements IComparator<Book> {

  /* METHODS
   * this.compare(Book b1, Book b2)   -- int
   */

  // compare two Books
  public int compare(Book b1, Book b2) {
    return b1.price - b2.price;
  }
}

// represents elements of binary trees
abstract class ABST<T> { 
  IComparator<T> order;

  // the constructor
  ABST(IComparator<T> order) {
    this.order = order;
  }

  /* FIELDS:
   * this.order                         -- IComparator<T>
   * 
   * METHODS:
   * this.insert(T that)                -- ABST<T>
   * this.getLeast()                    -- T
   * this.isLeaf                        -- boolean
   * this.getAllButLeast()              -- ABST<T>
   * this.sameTree(ABST<T> given)       -- boolean
   * this.sameNode(Node<T> given)       -- boolean
   * this.sameLeaf(Leaf<T> given)       -- boolean
   * this.sameData(ABST<T> given)       -- boolean
   * this.sameAsList(IList<T> given)    -- boolean
   * this.buildList(IList<T> given)     -- IList<T>
   * 
   * METHODS OF FIELDS:
   * order.compare(t1, t2)              -- int
   * 
   */

  // determine if this ABST<T> is a Leaf<T>
  abstract boolean isLeaf();

  // insert the given T into this ABST<T>
  abstract ABST<T> insert(T that);

  // get the smallest item in this ABST<T>
  abstract T getLeast();

  // return the ABST<T> containing everything except the least item in this ABST<T>
  abstract ABST<T> getAllButLeast();

  // determine if this ABST<T> is the same as the given ABST<T>
  abstract boolean sameTree(ABST<T> given);

  // determine if this ABST<T> is the same as the given Node<T>
  abstract boolean sameNode(Node<T> given);

  // determine if this ABST<T> is the same as the given Leaf<T>
  abstract boolean sameLeaf(Leaf<T> given);

  // determine if this ABST<T> has the same data as the given ABST<T>
  abstract boolean sameData(ABST<T> given);

  // determine if this ABST<T> is the same as the given IList<T>
  abstract boolean sameAsList(IList<T> given);

  // insert the items of this ABST<T> into the given IList<T>
  abstract IList<T> buildList(IList<T> given);
}

// represent a leaf
class Leaf<T> extends ABST<T> {

  // the constructor
  Leaf(IComparator<T> order) {
    super(order);
  }
  
  /* FIELDS:
    * this.order                         -- IComparator<T>
    * 
    * METHODS:
    * this.insert(T that)                 -- ABST<T>
    * this.getLeast()                     -- T
    * this.isLeaf                         -- boolean
    * this.getAllButLeast()               -- ABST<T>
    * this.sameTree(ABST<T> given)        -- boolean
    * this.sameNode(Node<T> given)        -- boolean
    * this.sameLeaf(Leaf<T> given)        -- boolean
    * this.sameData(ABST<T> given)        -- boolean
    * this.sameAsList(IList<T> given)     -- boolean
    * this.buildList(IList<T> given)      -- IList<T>
    */

  // insert the given T into this NodeT>
  ABST<T> insert(T that) {
    /* METHODS FOR PARAMETERS: Methods of T */
    return new Node<T>(this.order, that, this, this);
  }

  // get the smallest item in this Leaf<T>
  T getLeast() {
    throw new RuntimeException("No least item of an empty tree");
  }

  // determine if this Leaf<T> is a Leaf<T>
  boolean isLeaf() {
    return true;
  }

  // return the Leaf<T> containing everything except the least item in this Leaf<T>
  ABST<T> getAllButLeast() {
    throw new RuntimeException("No items in an empty tree");
  }

  // determine if this Leaf<T> is the same as the given ABST<T>
  boolean sameTree(ABST<T> given) {
    /* METHODS FOR PARAMETERS: Methods of ABST<T> */
    return given.sameLeaf(this);
  }

  // determine if this Leaf<T> is the same as the given Node<T>
  boolean sameNode(Node<T> given) {
    /* METHODS FOR PARAMETERS: Methods of Node<T> */
    return false;
  }

  // determine if this Leaf<T> is the same as the given Leaf<T>
  boolean sameLeaf(Leaf<T> given) {
    /* METHODS FOR PARAMETERS: Methods of Leaf<T> */
    return true;
  }

  // determine if this Leaf<T> has the same data as the given ABST<T>
  boolean sameData(ABST<T> given) {
    /* METHODS FOR PARAMETERS: Methods of ABST<T> */
    return given.sameLeaf(this);
  }

  // determine if this ABST<T> is the same as the given IList<T>
  boolean sameAsList(IList<T> given) {
    /* METHODS FOR PARAMETERS: Methods of IList<T> */
    return given.isMt();
  }

  // insert the items of this Leaf<T> into the given IList<T>
  IList<T> buildList(IList<T> given) {
    /* METHODS FOR PARAMETERS: Methods of ILisT<T> */
    return given;
  }
}

// represent a node
class Node<T> extends ABST<T> {
  T data;
  ABST<T> left;
  ABST<T> right;

  // the constructor
  Node(IComparator<T> order, T data, ABST<T> left, ABST<T> right) {
    super(order);
    this.data = data;
    this.left = left;
    this.right = right;
  }
  
  /* FIELDS:
   * this.order                         -- IComparator<T>
   * this.data                          -- T
   * this.left                          -- ABST<T>
   * this.right                         -- ABST<T>
   * 
   * METHODS:
   * this.insert(T that)                 -- ABST<T>
   * this.getLeast()                     -- T
   * this.isLeaf                         -- boolean
   * this.getAllButLeast()               -- ABST<T>
   * this.sameTree(ABST<T> given)        -- boolean
   * this.sameNode(Node<T> given)        -- boolean
   * this.sameLeaf(Leaf<T> given)        -- boolean
   * this.sameData(ABST<T> given)        -- boolean
   * this.sameAsList(IList<T> given)     -- boolean
   * this.buildList(IList<T> given)      -- IList<T>
   * 
   * METHODS OF FIELDS:
   * order.compare(t1, t2)               -- int
   * left.insert(T that)                 -- ABST<T>
   * left.getLeast()                     -- T
   * left.isLeaf                         -- boolean
   * left.getAllButLeast()               -- ABST<T>
   * left.sameTree(ABST<T> given)        -- boolean
   * left.sameNode(Node<T> given)        -- boolean
   * left.sameLeaf(Leaf<T> given)        -- boolean
   * left.sameData(ABST<T> given)        -- boolean
   * left.sameAsList(IList<T> given)     -- boolean
   * left.buildList(IList<T> given)      -- IList<T>
   * right.insert(T that)                -- ABST<T>
   * right.getLeast()                    -- T
   * right.isLeaf                        -- boolean
   * right.getAllButLeast()              -- ABST<T>
   * right.sameTree(ABST<T> given)       -- boolean
   * right.sameNode(Node<T> given)       -- boolean
   * right.sameLeaf(Leaf<T> given)       -- boolean
   * right.sameData(ABST<T> given)       -- boolean
   * right.sameAsList(IList<T> given)    -- boolean
   * right.buildList(IList<T> given)     -- IList<T>
   */

  // determine if this Node<T> is a Leaf<T>
  boolean isLeaf() {
    return false;
  }

  // insert the given T into this Leaf<T>
  ABST<T> insert(T that) {
    /* METHODS FOR PARAMETERS: Methods of T */
    if (this.order.compare(that, this.data) < 0) {
      return new Node<T>(this.order, this.data, this.left.insert(that), this.right);
    }
    else {
      return new Node<T>(this.order, this.data, this.left, this.right.insert(that));
    }
  }

  // get the smallest item in this Node<T>
  T getLeast() {
    if (this.left.isLeaf()) {
      return this.data;
    }
    else {
      return this.left.getLeast();
    }
  }

  // return the Node<T> containing everything except the least item in this Node<T>
  ABST<T> getAllButLeast() {
    if (this.order.compare(this.data, this.getLeast()) == 0) {
      return this.right;
    }
    else {
      return new Node<T>(this.order, this.data, this.left.getAllButLeast(), this.right);
    }
  }

  // determine if this Node<T> is the same as the given ABST<T>
  boolean sameTree(ABST<T> given) {
    /* METHODS FOR PARAMETERS: Methods of ABST<T> */
    return given.sameNode(this);
  }

  // determine if this Node<T> is the same as the given Node<T>
  boolean sameNode(Node<T> given) {
    /* METHODS FOR PARAMETERS: Methods of Node<T> */
    return this.order.compare(this.data, given.data) == 0
        && this.left.sameTree(given.left)
        && this.right.sameTree(given.right);
  }

  // determine if this Node<T> is the same as the given Leaf<T>
  boolean sameLeaf(Leaf<T> given) {
    /* METHODS FOR PARAMETERS: Methods of Lesf<T> */
    return false;
  }

  // determine if this Node<T> has the same data as the given ABST<T>
  boolean sameData(ABST<T> given) {
    /* METHODS FOR PARAMETERS: Methods of ABST<T> */
    return this.order.compare(this.getLeast(), given.getLeast()) == 0
        && this.getAllButLeast().sameData(given.getAllButLeast());
  }

  // determine if this ABST<T> is the same as the given IList<T>
  boolean sameAsList(IList<T> given) {
    /* METHODS FOR PARAMETERS: Methods of IList<T> */
    return this.buildList(new MtList<T>()).reverse().sameList(given);
  }

  // insert the items of this Node<T> into the given IList<T>
  IList<T> buildList(IList<T> given) {
    /* METHODS FOR PARAMETERS: Methods of IList<T> */
    return this.getAllButLeast().buildList(new ConsList<T>(this.getLeast(), given));
  }
}

// represent a list of T
interface IList<T> { 

  // determine if this IList<T> is empty
  boolean isMt();

  // reverse this IList<T>
  IList<T> reverse();

  // help reverse this IList<T>
  IList<T> addend(T t);

  // determine if this IList<T> is the same as the given IList<T>
  boolean sameList(IList<T> given);

  // insert the items of this IList<T> into the given ABST<T>
  ABST<T> buildTree(ABST<T> given);
}

//represent an empty list of T
class MtList<T> implements IList<T> { 

  /* METHODS
   * this.buildTree(ABST<T> given)      -- ABST<T>
   * this.reverse()                     -- IList<T>
   * this.addend(T accum)               -- IList<T>
   * this.sameList(IList<T> given)      -- boolean
   * this.isMt()                        -- boolean
   */

  // determine if this MtList<T> is empty
  public boolean isMt() {
    return true;
  }

  // reverse this MtList<T>
  public IList<T> reverse() {
    return this;
  }

  // help reverse this IList<T>
  public IList<T> addend(T accum) {
    /* METHODS FOR PARAMETERS: T methods */
    return new ConsList<T>(accum, new MtList<T>());
  }

  // determine if this MtList<T> is the same as the given IList<T>
  public boolean sameList(IList<T> given) {
    /* METHODS FOR PARAMETERS: IList<T> methods */
    return given.isMt();
  }
 

  /* METHODS
   * this.buildTree(ABST<T> given)      -- ABST<T>
   * this.reverse()                     -- IList<T>
   * this.addend(T accum)               -- IList<T>
   * this.sameList(IList<T> given)      -- boolean
   * this.isMt()                        -- boolean
   */
  
  // insert the items of this MtList<T> into the given ABST<T>
  public ABST<T> buildTree(ABST<T> given) {
    /* METHODS FOR PARAMETERS: ABST<T> methods */
    return given;
  }

}

//represent a populated list of T
class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  // the constructor
  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  // determine if this ConsList<T> is empty
  public boolean isMt() {
    return false;
  }

  /* FIELDS:
   * this.first         -- T
   * this.rest          -- IList<T>
   * 
   * METHODS:
   * this.buildTree(ABST<T> given)          -- ABST<T>
   * this.reverse()                         -- IList<T>
   * this.addend(T accum)                   -- IList<T>
   * this.sameList(IList<T> given)          -- boolean
   * this.sameListHelper(ConsList<T> lot)   -- boolean
   * this.isMt()                            -- boolean
   * rest.reverse()                         -- IList<T>
   * rest.addend(T t)                       -- IList<T>
   * rest.sameList(IList<T> given)          -- boolean
   * rest.isMt()                            -- boolean
   */

  // reverse this ConsList<T>
  public IList<T> reverse() {
    return this.rest.reverse().addend(this.first);
  }

  // help reverse this IList<T>
  public IList<T> addend(T accum) {
    /* METHODS FOR PARAMETERS: Methods for T */
    return new ConsList<T>(this.first, this.rest.addend(accum));
  }

  // determine if this ConsList<T> is the same as the given IList<T>
  public boolean sameList(IList<T> given) {
    /* METHODS FOR PARAMETERS: Methods for IList<T> */
    if (given.isMt()) {
      return false;
    }
    else {
      return this.sameListHelper((ConsList<T>) given);
    }
  }

  // help determine if this ConsList<T> is the same as the given IList<T>
  public boolean sameListHelper(ConsList<T> lot) {
    /* METHODS FOR PARAMETERS:  Same as above */
    return this.first.equals(lot.first) && this.rest.sameList(lot.rest);
  }

  /* FIELDS:
   * this.first         -- T
   * this.rest          -- IList<T>
   * 
   * METHODS:
   * this.buildTree(ABST<T> given)          -- ABST<T>
   * this.reverse()                         -- IList<T>
   * this.addend(T accum)                   -- IList<T>
   * this.sameList(IList<T> given)          -- boolean
   * this.sameListHelper(ConsList<T> lot)   -- boolean
   * this.isMt()                            -- boolean
   * rest.reverse()                         -- IList<T>
   * rest.addend(T t)                       -- IList<T>
   * rest.sameList(IList<T> given)          -- boolean
   * rest.isMt()                            -- boolean
   */
  
  // insert the items of this ConsList<T> into the given ABST<T>
  public ABST<T> buildTree(ABST<T> given) {
    /* METHODS FOR PARAMETERS: Methods for ABST<T> */
    return this.rest.buildTree(given.insert(this.first));
  }
}

// represent a book
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
  
  /* FIELDS
   * this.title     -- String
   * this.author    -- String
   * this.price     -- int
   */
}

// examples and tests
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
              new Node<Book>(titleComp, b3, lfTitle, lfTitle))), lfTitle);

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

  // test reverse
  boolean testReverse(Tester t) {
    return t.checkExpect(mt.reverse(), mt)
        && t.checkExpect(treeTitleList.reverse(), treeTitleListAfterBuildListmt)
        && t.checkExpect(treeTitleListAfterBuildListmt.reverse(), treeTitleList);
  }

  // test addend
  boolean testAddend(Tester t) {
    return t.checkExpect(mt.addend(b6), b6List)
        && t.checkExpect(b6List.addend(b1), b6Listplus);
  }

  // test sameAsListAddend
  boolean testSameAsListddend(Tester t) {
    return t.checkExpect(lfTitle.sameAsList(mt), true)
        && t.checkExpect(lfTitle.sameAsList(treeTitleList), false)
        && t.checkExpect(treeTitle.sameAsList(treeTitleList), true)
        && t.checkExpect(treePrice.sameAsList(treeTitleList), false);
  }

  // test SameList
  boolean testSameList(Tester t) {
    return t.checkExpect(mt.sameList(mt), true)
        && t.checkExpect(mt.sameList(treeTitleList), false)
        && t.checkExpect(treeTitleList.sameList(treeTitleList), true)
        && t.checkExpect(treeTitleList.sameList(treeAuthorList), false);
  }

  // test isMt
  boolean testIsMt(Tester t) {
    return t.checkExpect(mt.isMt(), true)
        && t.checkExpect(treeAuthorList.isMt(), false)
        && t.checkExpect(treeTitleList.isMt(), false)
        && t.checkExpect(treeTitleListAfterBuildListb6.isMt(), false);
  }

  // test insert
  boolean testInsert(Tester t) {
    return t.checkExpect(treeTitle.insert(b4), treeTitleAfterInsertB4)
        && t.checkExpect(treeTitle.insert(b5), treeTitleAfterInsertB5);
  }

  // test isLeaf
  boolean testIsLeaf(Tester t) {
    return t.checkExpect(lfTitle.isLeaf(), true)
        && t.checkExpect(treeTitle.isLeaf(), false);
  }

  // test getLeast
  boolean testGetLeast(Tester t) {
    return t.checkException(new RuntimeException("No least item of an empty tree"), lfTitle,
        "getLeast")
        && t.checkExpect(treeTitle.getLeast(), b1)
        && t.checkExpect(treeAuthor.getLeast(), b1)
        && t.checkExpect(treePrice.getLeast(), b2);
  }

  // test getAllButLeast
  boolean testGetAllButLeast(Tester t) {
    return t.checkExpect(treeTitle.getAllButLeast(), treeTitleAfterGetAllButLeast);
  }

  // test sameTree
  boolean testSameTree(Tester t) {
    return t.checkExpect(treeTitle.sameTree(treeTitle), true)
        && t.checkExpect(treeAuthor.sameTree(treeAuthor), true)
        && t.checkExpect(treePrice.sameTree(treePrice), true)
        && t.checkExpect(treeTitle.sameTree(treeAuthor), false)
        && t.checkExpect(treePrice.sameTree(treeAuthor), false);
  }

  // test sameNode
  boolean testSameNode(Tester t) {
    return t.checkExpect(treeTitle.sameNode((Node<Book>)treeTitle), true)
        && t.checkExpect(treeAuthor.sameNode((Node<Book>)treeAuthor), true)
        && t.checkExpect(treePrice.sameNode((Node<Book>)treePrice), true)
        && t.checkExpect(treeTitle.sameNode((Node<Book>)treePrice), false);
  }

  //test sameLeaf
  boolean testSameLeaf(Tester t) {
    return t.checkExpect(lfTitle.sameLeaf((Leaf<Book>)lfTitle), true)
        && t.checkExpect(lfAuthor.sameLeaf((Leaf<Book>)lfAuthor), true)
        && t.checkExpect(lfPrice.sameLeaf((Leaf<Book>)lfPrice), true)
        && t.checkExpect(lfTitle.sameLeaf((Leaf<Book>)lfAuthor), true)
        && t.checkExpect(lfTitle.sameLeaf((Leaf<Book>)lfPrice), true);
  }

  // test sameData
  boolean testSameData(Tester t) {
    return t.checkExpect(treeTitle.sameData(treeTitle), true)
        && t.checkExpect(treeTitle.sameData(treeTitleSameData), true);
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