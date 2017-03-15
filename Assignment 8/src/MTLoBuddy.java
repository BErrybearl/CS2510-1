// represents an empty list of Person's buddies
public class MTLoBuddy implements ILoBuddy {

  // returns true if that Person is in this list
  public boolean hasDirectBuddy(Person that) {
    return false;
  }
  
  // returns the number of people that are direct buddies
  // of both this and that person
  public int countCommonBuddies(Person that) {
    return 0;
  }

  // Helper method for hasExtendBuddies
  // to check whether the given person is indirectly invited
  public boolean indirectInv(Person that, ILoBuddy acc) {
    return false;
  }

  // Helper method for partyCount
  // count the buddies in the cons list
  public int countHelper(ILoBuddy that) {
    return 0;
  }

}
