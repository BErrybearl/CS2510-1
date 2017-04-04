import tester.Tester;
import java.util.ArrayList;

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

class Utils {
  <T> ArrayList<T> reverse(ArrayList<T> source) { 
    
    Stack<T> stack = new Stack<T>();
    ArrayList<T> alist = new ArrayList<T>();
    
    int i;
    for (i = 0; i <= source.size() ; i++);
    if (source.isEmpty())
    
    return alist;
    
  }
}


class Stack<T> {
   
  
  Deque<T> contents;
  
  void push(T item) {
    contents.addAtHead(item);
  } // adds an item to the head of the list
  boolean isEmpty() {
    return contents.size() == 0;
  }
  T pop() {
    return contents.removeFromHead();
  } // removes and returns the head of the list
  
  
}

class Queue<T> {
  Deque<T> contents;
  void enqueue(T item) {
  } // adds an item to the tail of the list
  boolean isEmpty() {
    return false;
  }
  T dequeue() {
    return contents.removeFromHead();
  } // removes and returns the head of the list
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
