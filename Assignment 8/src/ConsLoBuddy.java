// represents a list of Person's buddies
public class ConsLoBuddy implements ILoBuddy {

  Person first;
  ILoBuddy rest;

  ConsLoBuddy(Person first, ILoBuddy rest) {
    this.first = first;
    this.rest = rest;
  }

  // returns true if that Person is in this list
  public boolean hasDirectBuddy(Person that) {
    return first.username.equals(that.username) || rest.hasDirectBuddy(that);
  }

  // How many common buddy between that person and this cons list?
  public int countCommonBuddies(Person that) {
    if (that.hasDirectBuddy(this.first)) {
      return 1 + this.rest.countCommonBuddies(that);
    }
    else {
      return this.rest.countCommonBuddies(that);
    }
  }

  // Helper method for hasExtendBuddies
  // to check whether the given person is indirectly invited
  public boolean indirectInv(Person that, ILoBuddy acc) {
    if (!acc.hasDirectBuddy(this.first) && this.first.hasExtendBuddyHelper(that, acc)) {
      return true;
    }
    else {
      return this.rest.indirectInv(that, new ConsLoBuddy(this.first, acc));
    }
  }

  // Helper method for partyCount
  // count the buddies in the cons list
  public int countHelper(ILoBuddy given) {
    if (given.hasDirectBuddy(this.first)) {
      return this.rest.countHelper(given);
    }
    else {
      given = new ConsLoBuddy(this.first, given);
      return 1 
             + this.first.buddies.countHelper(given)
             + this.rest.countHelper(given)
             - this.countCommonBuddies(this.first);
    }
  }

}
