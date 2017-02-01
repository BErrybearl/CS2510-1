
// Assignment 3
// Changzong Liu
// changzong liu

import tester.*;

interface ILoString {

  String combine();

  ILoString sort();

  ILoString insert(String prev);

  boolean isSorted();

  // helper function
  boolean isSortedHelp(String prev);

  ILoString interleave(ILoString given);

  // helper function
  ILoString interleaveHelp(ILoString rest);

  ILoString merge(ILoString given);

  ILoString reverse();

  // adds the given String to the end of this ILoString
  ILoString addend(String end);

  boolean isDoubledList();

  public boolean isDoubledListHelp(String prev);

  boolean isPalindromeList();
}

// to represent an empty list of Strings
class MtLoString implements ILoString {

  public String combine() {
    return "";
  }

  public ILoString sort() {
    return this;
  }

  // insert a string into empty list
  // as the only element in the list
  public ILoString insert(String prev) {
    return new ConsLoString(prev, this);
  }

  public boolean isSorted() {
    return true;
  }

  public boolean isSortedHelp(String prev) {
    return true;
  }

  // Since this ILoString is an MtLoString, interleaving
  // this and given should just return given.
  public ILoString interleave(ILoString given) {
    return given;
  }

  // helps interleave(given) interleave the two lists
  public ILoString interleaveHelp(ILoString rest) {
    return rest;
  }

  // merging an ILoString with an MtLoString
  public ILoString merge(ILoString given) {
    return given;
  }

  // reverse empty list
  public ILoString reverse() {
    return this;
  }

  public ILoString addend(String end) {
    return new ConsLoString(end, new MtLoString());
  }

  // an MtLoString is a doubled list
  public boolean isDoubledList() {
    return true;
  }

  public boolean isDoubledListHelp(String prev) {
    return false;
  }

  public boolean isPalindromeList() {
    return true;
  }
}

class ConsLoString implements ILoString {
  String first;
  ILoString rest;

  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }

  // combine all Strings in this list into one
  public String combine() {
    return this.first.concat(this.rest.combine());
  }

  // application of the insertion sort algorithm
  public ILoString sort() {
    return this.rest.sort().insert(this.first);
  }

  // inserts a given String into this sorted list in the appropriate
  // location so given resultant ILoString is still sorted.
  public ILoString insert(String prev) {
    if (prev.toLowerCase().compareTo(this.first.toLowerCase()) <= 0) {
      return new ConsLoString(prev, this.rest.insert(this.first));
    }
    return new ConsLoString(this.first, this.rest.insert(prev));
  }

  // a ConsLoString is sorted if it is not changed when it is sorted.
  public boolean isSorted() {
    return this.rest.isSortedHelp(this.first);
  }

  // helper function
  public boolean isSortedHelp(String prev) {
    return (prev.toLowerCase().compareTo(this.first.toLowerCase()) <= 0) && this.isSorted();
  }

  public ILoString interleave(ILoString given) {
    return new ConsLoString(this.first, given.interleaveHelp(this.rest));
  }

  // helps interleave(given) interleave the two lists
  public ILoString interleaveHelp(ILoString rest) {
    return new ConsLoString(this.first, rest.interleave(this.rest));
  }

  // merging a ConsLoString with an ILoString can be broken down
  // into interleaving the two lists and sorting the result.
  public ILoString merge(ILoString given) {
    return this.interleave(given).sort();
  }

  // reverse the first elements to the end of the list
  public ILoString reverse() {
    return this.rest.reverse().addend(this.first);
  }

  public ILoString addend(String end) {
    return new ConsLoString(this.first, this.rest.addend(end));
  }

  public boolean isDoubledList() {
    return this.rest.isDoubledListHelp(this.first);
  }

  // helper function
  public boolean isDoubledListHelp(String prev) {
    return (this.first.equals(prev)) && this.rest.isDoubledList();
  }

  // judge whether the list is palindrome
  public boolean isPalindromeList() {
    return this.interleave(this.reverse()).isDoubledList();
  }
}

// to represent examples of lists of strings
// and test the methods written for list of strings
class ExamplesStrings {

  // ""
  ILoString empty = new MtLoString();

  // "jack had a little pen."
  ILoString jack = new ConsLoString("jack ", new ConsLoString("had ", new ConsLoString("a ",
      new ConsLoString("little ", new ConsLoString("pen.", new MtLoString())))));

  // "had a little pen."
  ILoString jackRest = new ConsLoString("had ", new ConsLoString("a ",
      new ConsLoString("little ", new ConsLoString("pen.", new MtLoString()))));

  // "a c e g h i "
  ILoString oddL = new ConsLoString("a ", new ConsLoString("c ", new ConsLoString("e ",
      new ConsLoString("g ", new ConsLoString("h ", new ConsLoString("i ", new MtLoString()))))));

  // "b d f "
  ILoString evenL = new ConsLoString("b ",
      new ConsLoString("d ", new ConsLoString("f ", new MtLoString())));

  // "a b d g h m "
  ILoString sixLetters = new ConsLoString("a ", new ConsLoString("b ", new ConsLoString("d ",
      new ConsLoString("g ", new ConsLoString("h ", new ConsLoString("m ", new MtLoString()))))));

  // "c f n "
  ILoString threeLetters = new ConsLoString("c ",
      new ConsLoString("f ", new ConsLoString("n ", new MtLoString())));

  // "a b c d f g h m n "
  ILoString mergedLetters = new ConsLoString("a ",
      new ConsLoString("b ",
          new ConsLoString("c ",
              new ConsLoString("d ",
                  new ConsLoString("f ", new ConsLoString("g ", new ConsLoString("h ",
                      new ConsLoString("m ", new ConsLoString("n ", new MtLoString())))))))));

  // "a had pen.little jack "
  ILoString jackSorted = new ConsLoString("a ", new ConsLoString("had ", new ConsLoString("pen.",
      new ConsLoString("little ", new ConsLoString("jack ", new MtLoString())))));

  ILoString jackSortedRest = new ConsLoString("had ", new ConsLoString("pen.",
      new ConsLoString("little ", new ConsLoString("jack ", new MtLoString()))));

  // "aabbcc"
  ILoString doubledList = new ConsLoString("a", new ConsLoString("a", new ConsLoString("b",
      new ConsLoString("b", new ConsLoString("c", new ConsLoString("c", new MtLoString()))))));

  // "aabbc"
  ILoString almostDoubledList = new ConsLoString("a", new ConsLoString("a",
      new ConsLoString("b", new ConsLoString("b", new ConsLoString("c", new MtLoString())))));

  // "abbcc"
  ILoString doubledListRest = new ConsLoString("a", new ConsLoString("b",
      new ConsLoString("b", new ConsLoString("c", new ConsLoString("c", new MtLoString())))));

  // "abcbc"
  ILoString palendrome = new ConsLoString("a", new ConsLoString("b",
      new ConsLoString("c", new ConsLoString("b", new ConsLoString("a", new MtLoString())))));

  // "Short "
  ILoString shortList = new ConsLoString("Short ", new MtLoString());

  boolean testCombine(Tester t) {
    return t.checkExpect(this.jack.combine(), "jack had a little pen.");
  }

  boolean testSort(Tester t) {
    return t.checkExpect(this.jack.sort(),
        new ConsLoString("a ", new ConsLoString("had ", new ConsLoString("jack ",
            new ConsLoString("little ", new ConsLoString("pen.", new MtLoString()))))));
  }

  boolean testInsert(Tester t) {
    return t.checkExpect(this.jackSorted.insert("zed"), this.jackSorted.addend("zed"))
        && t.checkExpect(this.evenL.insert("c "),
            new ConsLoString("b ",
                new ConsLoString("c ",
                    new ConsLoString("d ", new ConsLoString("f ", new MtLoString())))))
        && t.checkExpect(this.empty.insert("test"), new ConsLoString("test", new MtLoString()));
  }

  boolean testIsSorted(Tester t) {
    return t.checkExpect(this.jack.isSorted(), false)
        && t.checkExpect(
            new ConsLoString("a ",
                new ConsLoString("had ",
                    new ConsLoString("pen.",
                        new ConsLoString("little ", new ConsLoString("jack ", new MtLoString())))))
                            .isSorted(),
            false)
        && t.checkExpect(this.jack.sort().isSorted(),
            true)
        && t.checkExpect(new ConsLoString("a ",
            new ConsLoString("had ",
                new ConsLoString("pen.",
                    new ConsLoString("little ", new ConsLoString("jack ", new MtLoString())))))
                        .isSorted(),
            false)
        && t.checkExpect(this.empty.isSorted(), true);
  }

  boolean testIsSortedHelp(Tester t) {
    return t.checkExpect(this.empty.isSortedHelp("a"), true)
        && t.checkExpect(this.jackRest.isSortedHelp("jack"), false)
        && t.checkExpect(this.jackSortedRest.isSortedHelp("a "), false);
  }

  boolean testInterleave(Tester t) {
    return t
        .checkExpect(this.oddL.interleave(this.evenL),
            new ConsLoString("a ",
                new ConsLoString("b ", new ConsLoString("c ",
                    new ConsLoString("d ", new ConsLoString("e ", new ConsLoString("f ",
                        new ConsLoString("g ",
                            new ConsLoString("h ", new ConsLoString("i ", new MtLoString()))))))))))
        && t.checkExpect(this.empty.interleave(this.jack), this.jack)
        && t.checkExpect(this.jack.interleave(this.empty), this.jack);
  }

  boolean testInterleaveHelp(Tester t) {
    return t.checkExpect(this.empty.interleaveHelp(this.jack), this.jack)
        && t.checkExpect(this.jack.interleaveHelp(this.empty), this.jack)
        && t.checkExpect(this.evenL.interleaveHelp(this.shortList),
            new ConsLoString("b ", new ConsLoString("Short ",
                new ConsLoString("d ", new ConsLoString("f ", new MtLoString())))));
  }

  boolean testMerge(Tester t) {
    return t.checkExpect(empty.merge(this.jackSorted), this.jackSorted)
        && t.checkExpect(this.sixLetters.merge(this.threeLetters), this.mergedLetters);
  }

  boolean testReverse(Tester t) {
    return t.checkExpect(this.jack.reverse(),
        new ConsLoString("pen.", new ConsLoString("little ", new ConsLoString("a ",
            new ConsLoString("had ", new ConsLoString("jack ", new MtLoString()))))));
  }

  boolean testaddend(Tester t) {
    return t.checkExpect(empty.addend("a"), new ConsLoString("a", empty))
        && t.checkExpect(shortList.addend("list"),
            new ConsLoString("Short ", new ConsLoString("list", empty)))
        && t.checkExpect(jack.addend(" The end."),
            new ConsLoString("jack ",
                new ConsLoString("had ", new ConsLoString("a ", new ConsLoString("little ",
                    new ConsLoString("pen.", new ConsLoString(" The end.", new MtLoString())))))));
  }

  boolean testIsDoubledList(Tester t) {
    return t.checkExpect(this.doubledList.isDoubledList(), true)
        && t.checkExpect(this.jack.isDoubledList(), false)
        && t.checkExpect(this.palendrome.isDoubledList(), false)
        && t.checkExpect(this.almostDoubledList.isDoubledList(), false);
  }

  boolean testIsDoubledListHelp(Tester t) {
    return t.checkExpect(this.doubledListRest.isDoubledListHelp("a"), true)
        && t.checkExpect(this.palendrome.isDoubledListHelp("a"), false);
  }

  boolean testIsPalendromeList(Tester t) {
    return t.checkExpect(new MtLoString().isPalindromeList(), true)
        && t.checkExpect(this.palendrome.isPalindromeList(), true)
        && t.checkExpect(this.jack.isPalindromeList(), false);
  }
}