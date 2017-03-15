import tester.*;

// represent the interface IPredicator
interface IPred<T> {
  boolean apply(T t);
}

// Compare whether two strings are equal
class SameString implements IPred<String> {
  String s1;

  SameString(String s1) {
    this.s1 = s1;
  }

  public boolean apply(String s) {
    return s1.equals(s);
  }
}

class Deque<T> {
  Sentinel<T> header;

  Deque() {
    this.header = new Sentinel<T>();
  }

  Deque(Sentinel<T> s) {
    this.header = s;
  }

  // count the number of nodes in the deque
  int size() {
    return this.header.next.sizeHelp();
  }

  // inserts a Node with the given T as its data
  // at the front of this Deque
  void addAtHead(T toAdd) {
    Node<T> add = new Node<T>(toAdd);
    add.add(this.header, this.header.next);
  }

  // inserts a Node with the given T as its data
  // at the end of this Deque
  void addAtTail(T toAdd) {
    Node<T> add = new Node<T>(toAdd);
    add.add(this.header.prev, this.header);
  }

  // removes the first node from this Deque
  T removeFromHead() {
    return this.header.next.remove();
  }

  // removes the last node from this Deque
  T removeFromTail() {
    return this.header.prev.remove();
  }

  // finds the first ANode<T> in this deque that satisfies the given IPred<T>
  // if no Node<T> found, return the Sentinel<T>
  ANode<T> find(IPred<T> pred) {
    return this.header.next.findHelp(pred);
  }

  // removes the given ANode<T> from this deque
  // if given a Sentinel<T>, do nothing
  void removeNode(ANode<T> toRemove) {
    if (toRemove != (ANode<T>) this.header) {
      toRemove.remove();
    }
  }
}

// represents a union of data:
// - Sentinel<T>
// - Node<T>
abstract class ANode<T> {
  ANode<T> prev;
  ANode<T> next;

  // helps size() determine the size of the Deque
  int sizeHelp() {
    return 1 + this.next.sizeHelp();
  }

  // helps find(pred) determine the first ANode<T> that satsifies pred
  abstract ANode<T> findHelp(IPred<T> pred);

  // removes this ANode<T> and returns the data
  // of this ANode<T> (if this ANode<T> instanceOf Node<T>)
  abstract T remove();
}

// represents a sentinel object in a deque, which is the "start" of the deque
class Sentinel<T> extends ANode<T> {
  // constructor for the Sentinel object
  Sentinel() {
    this.prev = this;
    this.next = this;
  }

  // helps size() determine the length of the deque
  public int sizeHelp() {
    return 0;
  }

  // helps find(IPred<T>) determine the first ANode<T> that satsifies pred
  public ANode<T> findHelp(IPred<T> pred) {
    return this;
  }

  // if trying to remove a Sentinel<T>, throws exception
  public T remove() {
    throw new RuntimeException("attempting to remove from empty deque");
  }
}

// represents a node object in a deque, which is where
// the data objects of the deque are located
class Node<T> extends ANode<T> {
  T data;

  // constructor for a Node with no prev or next
  Node(T data) {
    this.data = data;
    this.prev = null;
    this.next = null;
  }

  // constructor for a Node with a given prev and next
  // throws exception if the given prev/next is null
  Node(T data, ANode<T> next, ANode<T> prev) {
    this.data = data;
    if ((prev == null) || (next == null)) {
      throw new IllegalArgumentException("given node is null");
    }
    this.prev = prev;
    this.next = next;
    this.prev.next = this;
    this.next.prev = this;
  }

  // helps find(IPred<T>) determine the first ANode<T> that satsifies pred
  public ANode<T> findHelp(IPred<T> pred) {
    if (pred.apply(this.data)) {
      return this;
    }
    else {
      return this.next.findHelp(pred);
    }
  }

  // adds this Node<T> to a deque between prev and next
  void add(ANode<T> prev, ANode<T> next) {
    prev.next = this;
    next.prev = this;
    this.prev = prev;
    this.next = next;
  }

  // removes this Node<T> from the deque it is in
  T remove() {
    ANode<T> prev = this.prev;
    ANode<T> next = this.next;
    prev.next = next;
    next.prev = prev;
    return this.data;
  }
}

// Examples
class ExamplesDeque {

  Sentinel<String> sent1;
  Sentinel<String> sent2;
  Sentinel<Integer> sent3;
  Sentinel<String> senttest;

  ANode<String> node1;
  ANode<String> node2;
  ANode<String> node3;
  ANode<String> node4;

  ANode<Integer> nodea;
  ANode<Integer> nodeb;
  ANode<Integer> nodec;
  ANode<Integer> noded;
  ANode<Integer> nodee;

  Deque<String> deque1;
  Deque<String> deque2;
  Deque<Integer> deque3;
  Deque<String> dequetest;

  // initialize the Deque list
  void initDeque() {
    sent1 = new Sentinel<String>();
    sent2 = new Sentinel<String>();
    node1 = new Node<String>("abc", sent2, sent2);
    node2 = new Node<String>("bcd", sent2, node1);
    node3 = new Node<String>("cde", sent2, node2);
    node4 = new Node<String>("def", sent2, node3);

    sent3 = new Sentinel<Integer>();
    nodea = new Node<Integer>(1, sent3, sent3);
    nodeb = new Node<Integer>(2, sent3, nodea);
    nodec = new Node<Integer>(3, sent3, nodeb);
    noded = new Node<Integer>(4, sent3, nodec);
    nodee = new Node<Integer>(5, sent3, noded);

    deque1 = new Deque<String>();
    deque2 = new Deque<String>(this.sent2);
    deque3 = new Deque<Integer>(this.sent3);

    senttest = new Sentinel<String>();
    dequetest = new Deque<String>(this.senttest);
  }

  // test the method size
  void testSize(Tester t) {
    // initialize
    this.initDeque();
    t.checkExpect(this.deque2.size(), 4);
    t.checkExpect(this.deque1.size(), 0);
    t.checkExpect(this.deque3.size(), 5);
  }

  // test the method addAtHead
  void testAddAtHead(Tester t) {

    initDeque();
    t.checkExpect(this.sent2.next, this.node1);
    t.checkExpect(this.sent1.next, this.sent1);

    initDeque();
    this.deque1.addAtHead("def");
    this.deque1.addAtHead("cde");
    this.deque1.addAtHead("bcd");
    this.deque1.addAtHead("abc");

    t.checkExpect(deque1, deque2);
  }

  // test the method addAdTail
  void testAddAtTail(Tester t) {

    initDeque();

    this.dequetest.addAtTail("abc");
    this.dequetest.addAtTail("bcd");
    this.dequetest.addAtTail("cde");
    this.dequetest.addAtTail("def");
    t.checkExpect(this.dequetest, this.deque2);

  }

  // test the method removeFromHead
  void testRemoveFromHead(Tester t) {
    // initialize
    initDeque();
    t.checkExpect(this.sent2, this.sent1);
    t.checkException(new RuntimeException("Can't try to remove on a Sentinel!"), sent1,
        "removeFromHead");

    initDeque();
    this.deque2.removeFromHead();
    t.checkExpect(this.deque2.header.next, this.node2);
    t.checkExpect(this.deque2.header.prev, this.node4);
  }

  // test the method removeFromTail
  void testRemoveFromTail(Tester t) {
    initDeque();
    t.checkExpect(this.sent2.next, this.node1);
    t.checkExpect(this.sent2.prev, this.node3);
    t.checkException(new RuntimeException("Can't try to remove on a Sentinel!"), sent1,
        "removeFromTail");

    initDeque();
    this.deque2.removeFromTail();
    t.checkExpect(this.deque2.header.next, this.node1);

  }
}
