// represents a Person with a user name and a list of buddies
public class Person {

  String username;
  ILoBuddy buddies;

  Person(String username) {
    this.username = username;
    this.buddies = new MTLoBuddy();
  }

  // add person this person's buddy list
  void addBuddies(Person that) {
    this.buddies = new ConsLoBuddy(that, this.buddies);
  }

  // returns true if this Person has that as a direct buddy
  boolean hasDirectBuddy(Person that) {
    return buddies.hasDirectBuddy(that);
  }

  // returns the number of people who will show up at the party
  // given by this person
  int partyCount() {
    return 1 + this.buddies.countHelper(new ConsLoBuddy(this, new MTLoBuddy()));
  }

  // returns the number of people that are direct buddies
  // of both this and that person
  int countCommonBuddies(Person that) {
    return buddies.countCommonBuddies(that);
  }

  // will the given person be invited to a party
  // organized by this person?
  boolean hasExtendedBuddy(Person that) {
    return this.hasDirectBuddy(that) || this.buddies.indirectInv(that, new MTLoBuddy());
  }

  // Helper method for hasExtendedBuddy
  public boolean hasExtendBuddyHelper(Person that, ILoBuddy acc) {
    if (!acc.hasDirectBuddy(this) && this.buddies.hasDirectBuddy(that)) {
      return true;
    }
    else {
      return this.buddies.indirectInv(that, new ConsLoBuddy(this, acc));
    }
  }
}
