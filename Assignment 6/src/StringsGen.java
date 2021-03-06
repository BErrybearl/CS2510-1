import tester.*;

interface IComparator<T> {
  // Returns a negative number if t1 comes before t2 in this ordering
  // Returns zero if t1 is the same as t2 in this ordering
  // Returns a positive number if t1 comes after t2 in this ordering
  int compare(T t1, T t2);
}

class StringLengthCompGen implements IComparator<String> {

  public int compare(String s1, String s2) {
    if (s1.length() < s2.length()) {
      return -1;
    }
    else if (s1.length() == s2.length()) {
      return 0;
    }
    else {
      return 1;
    }
  }
}

class StringZetabetCompGen implements IComparator<String> {

  public int compare(String t1, String t2) {
    return t1.compareTo(t2);
  }
}

interface IList<T> {

  boolean isSorted(IComparator<T> c);

  boolean isSortedHelp(T s, IComparator<T> c);

  IList<T> merge(IList<T> given, IComparator<T> c);

  IList<T> mergeHelper(T given, IComparator<T> c);

  IList<T> insert(T given, IComparator<T> c);

  IList<T> sort(IComparator<T> c);

  boolean sameList(IList<T> given);

  boolean isMt();
}

class MtList<T> implements IList<T> {

  public boolean isSorted(IComparator<T> c) {
    return true;
  }

  public boolean isSortedHelp(T s, IComparator<T> c) {
    return true;
  }

  public boolean isMt() {
    return true;
  }

  public IList<T> merge(IList<T> given, IComparator<T> c) {
    return given;
  }

  public IList<T> mergeHelper(T given, IComparator<T> c) {
    return this;
  }

  @Override
  public IList<T> insert(T given, IComparator<T> c) {
    return new ConsList<T>(given, this);
  }

  public IList<T> sort(IComparator<T> c) {
    return this;
  }

  public boolean sameMt(MtList<T> given) {
    return given.isMt();

  }

  class ConsList<T> implements IList<T> {

    T first;
    IList<T> rest;

    ConsList(T first, IList<T> rest) {
      this.first = first;
      this.rest = rest;
    }

    @Override
    public boolean isSorted(IComparator<T> c) {
      return this.rest.isSortedHelp(first, c);
    }

    public boolean isSortedHelp(T s, IComparator<T> c) {
      if (c.compare(s, first) <= 0) {
        return this.rest.isSorted(c);
      }
      else {
        return false;
      }
    }

    public boolean isMt() {
      return false;
    }

    public IList<T> merge(IList<T> given, IComparator<T> c) {
      if (given.isMt()) {
        return this;
      }
      else {
        return this.mergeCons((ConsList<T>) given, c);
      }
    }

    IList<T> mergeCons(ConsList<T> given, IComparator<T> c) {
      return this.mergeHelper(given.first, c).merge(rest, c);
    }

    public IList<T> mergeHelper(T given, IComparator<T> c) {
      if (c.compare(given, first) <= 0) {
        return new ConsList<T>(given, this);
      }
      else {
        return new ConsList<T>(this.first, this.rest.mergeHelper(given, c));
      }
    }

    public IList<T> insert(T given, IComparator<T> c) {
      if (c.compare(given, first) <= 0) {
        return new ConsList<T>(given, this);
      }
      else {
        return new ConsList<T>(this.first, this.rest.insert(given, c));
      }
    }

    public IList<T> sort(IComparator<T> c) {
      return this.rest.sort(c).insert(this.first, c);
    }

    public boolean sameList(IList<T> given) {
      return (given instanceof ConsList) && this.sameListHelper((ConsList<T>) given);
    }

    boolean sameListHelper(ConsList<T> given) {
      return this.first.equals(given.first) && this.rest.sameList(given.rest);
    }
  }

  public boolean sameList(IList<T> given) {
    return false;
  }
}

/*
 * class ExamplesStringsGenTests {
 * 
 * 
 * IList<String> los1 = new MtList<String>(); IList<String> los2 = new
 * ConsList<String>("universally", los1); IList<String> los3 = new
 * ConsList<String>("distortion", los2); IList<String> los4 = new
 * ConsList<String>("fuzz", los3); IList<String> los5 = new
 * ConsList<String>("dream", los1); IList<String> los6 = new
 * ConsList<String>("excessive", los5); IList<String> los7 = new
 * ConsList<String>("web", los6);
 * 
 * IComparator<String> lexcomp = new StringZetabetCompGen(); IComparator<String>
 * lencomp = new StringLengthCompGen();
 * 
 * 
 * }
 * 
 * @Override public boolean sameList(IList<T> given) { // TODO Auto-generated
 * method stub return false; }
 */
