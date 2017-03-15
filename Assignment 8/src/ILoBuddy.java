// represents a list of Person's buddies
public interface ILoBuddy {
  
  // returns true if that Person is in this list
  boolean hasDirectBuddy(Person that);

  // returns the number of people that are direct buddies
  // of both this and that person
  int countCommonBuddies(Person that);

  // Helper method for hasExtendBuddies
  // to check whether the given person is indirectly invited
  boolean indirectInv(Person that, ILoBuddy acc);

  // Helper method for partyCount
  // count the buddies in the cons list
  int countHelper(ILoBuddy that);

}
