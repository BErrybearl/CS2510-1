class Dequeue<T> {
  Sentinel<T> header;

  Dequeue(Sentinel<T> header) {
    this.header = header;
  }
}

abstract class ANode<T> {
  ANode<T> next;
  ANode<T> prev;
}

class Sentinel<T> extends ANode<T> {

  Sentinel() {
    this.next = this;
    this.prev = this;
  }
}

class Node<T> extends ANode<T> {
  
  T data;
  
  Node(T data) {
    this.data = data;
    this.next = null;
    this.prev = null;
  }
  
  Node(T data, ANode<T> next, ANode<T> prev) {
    this.data = data;
    
    if (next == null || prev == null) {
      throw new IllegalArgumentException("Trying to add null to Node");
  }
    else {
      this.next=next;
      this.prev=prev;
      }
    }
}
