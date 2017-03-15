import tester.*;

// runs tests for the buddies problem
public class ExamplesBuddies {

  Person ann;
  Person bob;
  Person cole;
  Person dan;
  Person ed;
  Person fay;
  Person gabi;
  Person hank;
  Person jan;
  Person kim;
  Person len;

  ILoBuddy mt = new MTLoBuddy();

  // initialize
  void initBuddies() {

    ann = new Person("Ann");
    bob = new Person("Bob");
    cole = new Person("Cole");
    dan = new Person("Dan");
    ed = new Person("Ed");
    fay = new Person("Fay");
    gabi = new Person("Gabi");
    hank = new Person("Hank");
    jan = new Person("Jan");
    kim = new Person("Kim");
    len = new Person("Len");

    ann.addBuddies(cole);
    ann.addBuddies(bob);
    bob.addBuddies(hank);
    bob.addBuddies(ed);
    bob.addBuddies(ann);
    cole.addBuddies(dan);
    dan.addBuddies(cole);
    ed.addBuddies(fay);
    fay.addBuddies(gabi);
    fay.addBuddies(ed);
    gabi.addBuddies(fay);
    gabi.addBuddies(ed);
    jan.addBuddies(len);
    jan.addBuddies(kim);
    kim.addBuddies(len);
    kim.addBuddies(jan);
    len.addBuddies(kim);
    len.addBuddies(jan);

  }

  // test method hasDirectBuddy
  boolean testHasDirectBuddy(Tester t) {
    initBuddies();
    return t.checkExpect(ann.hasDirectBuddy(bob), true)
        && t.checkExpect(ann.hasDirectBuddy(cole), true)
        && t.checkExpect(ann.hasDirectBuddy(dan), false)
        && t.checkExpect(hank.hasDirectBuddy(ann), false);
  }

  // test method countCommonBuddies
  boolean testCountCommonBuddies(Tester t) {
    initBuddies();
    return t.checkExpect(ann.countCommonBuddies(bob), 0)
        && t.checkExpect(ann.countCommonBuddies(hank), 0)
        && t.checkExpect(ann.countCommonBuddies(dan), 1)
        && t.checkExpect(jan.countCommonBuddies(kim), 1)
        && t.checkExpect(ann.buddies.countCommonBuddies(kim), 0)
        && t.checkExpect(bob.buddies.countCommonBuddies(ann), 0)
        && t.checkExpect(jan.buddies.countCommonBuddies(kim), 1)
        && t.checkExpect(hank.buddies.countCommonBuddies(kim), 0);
  }

  // test method hasExtendedBuddy
  boolean testHasExtendedBuddy(Tester t) {
    initBuddies();
    return t.checkExpect(ann.hasExtendedBuddy(bob), true)
        && t.checkExpect(ann.hasExtendedBuddy(fay), true)
        && t.checkExpect(ann.hasExtendedBuddy(jan), false)
        && t.checkExpect(hank.hasExtendedBuddy(ann), false);
  }

  // test method partyCount
  boolean testPartyCount(Tester t) {
    initBuddies();
    return t.checkExpect(ann.partyCount(), 8) 
        && t.checkExpect(dan.partyCount(), 2)
        && t.checkExpect(jan.partyCount(), 3) 
        && t.checkExpect(hank.partyCount(), 1);
  }

  // test helper method indirectInv
  boolean testIndirectInv(Tester t) {
    initBuddies();
    return t.checkExpect(ann.buddies.indirectInv(bob, mt), true)
        && t.checkExpect(len.buddies.indirectInv(cole, mt), false)
        && t.checkExpect(jan.buddies.indirectInv(kim, mt), true)
        && t.checkExpect(hank.buddies.indirectInv(bob, mt), false);
  }
  
  // test helper method 
  boolean testHasExtendBuddyHelper(Tester t) {
    initBuddies();
    return t.checkExpect(ann.hasExtendBuddyHelper(ann, mt), true) 
        && t.checkExpect(dan.hasExtendBuddyHelper(dan, mt), true)
        && t.checkExpect(jan.hasExtendBuddyHelper(kim, mt), true) 
        && t.checkExpect(hank.hasExtendBuddyHelper(ann, mt), false);
  }
}