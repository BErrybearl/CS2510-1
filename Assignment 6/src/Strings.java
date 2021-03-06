import tester.*;

// to represent the Comparator class 
interface IStringsCompare {

  // to determine whether String1 comes before String2
  boolean comesBefore(String s1, String s2);

}

class StringZetabetComp implements IStringsCompare {

  public boolean comesBefore(String s1, String s2) {
    return s1.compareTo(s2) >= 0;
  }

}

class StringLengthComp implements IStringsCompare {

  public boolean comesBefore(String s1, String s2) {
    return s1.length() <= s2.length();
  }
}

interface ILoString {

  boolean isSorted(IStringsCompare c);

  boolean isSortedHelp(String s, IStringsCompare c);

  ILoString merge(ILoString given, IStringsCompare c);

  ILoString mergeHelper(String given, IStringsCompare c);

  ILoString insert(String given, IStringsCompare c);

  ILoString sort(IStringsCompare c);
  
  boolean sameList(ILoString given);
  
  boolean isMt();
 
  
}

class MtLoString implements ILoString {

  public boolean isSorted(IStringsCompare c) {
    return true;
  }

  public boolean isSortedHelp(String s, IStringsCompare c) {
    return true;
  }

  public ILoString merge(ILoString given, IStringsCompare c) {
    return given;
  }

  public ILoString mergeHelper(String given, IStringsCompare c) {
    return this;
  }

  public ILoString insert(String given, IStringsCompare c) {
    return new ConsLoString(given, this);
  }

  @Override
  public ILoString sort(IStringsCompare c) {
    return this;
  }

  public boolean isMt() {
    return true;
  }
  
  public boolean sameList(ILoString given) {
    return given.isMt();
  }


}

class ConsLoString implements ILoString {

  String first;
  ILoString rest;

  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }

  public boolean isSorted(IStringsCompare c) {
    return this.rest.isSortedHelp(first, c);
  }

  public boolean isSortedHelp(String s, IStringsCompare c) {
    return c.comesBefore(s, first) && this.rest.isSorted(c);
  }

  public ILoString merge(ILoString given, IStringsCompare c) {
    if (given.isMt()) {
      return this;
    }
    else {
      return this.mergeCons((ConsLoString) given, (IStringsCompare) c);
    }
  }

  // merge two sorted conslists into one sorted conslist by the given way
  ILoString mergeCons(ConsLoString los, IStringsCompare c) {
    return this.mergeHelper(los.first, c).merge(los.rest, c);
  }

  // insert a string into a sorted list by the give way
  public ILoString mergeHelper(String given, IStringsCompare c) {
    if (c.comesBefore(given, this.first)) {
      return new ConsLoString(given, this);
    }
    else {
      return new ConsLoString(this.first, this.rest.mergeHelper(given, c));
    }
  }

  public ILoString insert(String given, IStringsCompare c) {
    if (c.comesBefore(given, first)) {
      return new ConsLoString(given, this);
    }
    else {
      return new ConsLoString(this.first, this.rest.insert(given, c));
    }
  }

  public ILoString sort(IStringsCompare c) {
    return this.rest.sort(c).insert(this.first, c);
  }
  
  public boolean isMt() {
    return false;
  }
  
  public boolean sameList(ILoString given) {
      if (given.isMt()) {
        return false;
      }
      else {
        return this.first.equals(((ConsLoString) given).first) && this.rest.sameList(given);
      }
  }

}
/*
class ExamplesStringsTests {
 
  ILoString los1 = new MtLoString();
  ILoString los2 = new ConsLoString("universally", los1);
  ILoString los3 = new ConsLoString("distortion", los2);
  ILoString los4 =new ConsLoString("fuzz", los3);
  ILoString los5 = new ConsLoString("dream", los1);
  ILoString los6 = new ConsLoString("excessive", los5);
  ILoString los7 = new ConsLoString("web", los6);
  
  IStringsCompare lexcomp = new StringZetabetComp();
  IStringsCompare lencomp = new StringLengthComp();
  
  boolean testSameList(Tester t) {
    return t.checkExpect(los1.sameList(los1), true)
        && t.checkExpect(los1.sameList(los2), false)
        && t.checkExpect(los1.sameList(los3), false)
        && t.checkExpect(los4.sameList(los4), true);

  }

  
  boolean testSameCons(Tester t) {
    return t.checkExpect(los1.sameMt((MtLoString) los1), true);


  }

}
*/