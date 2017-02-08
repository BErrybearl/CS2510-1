import tester.Tester;

// Assignment 2
// Cumming Connor
// connorc0405
// Changzong  Liu 
// changzongliu

//to represent a tag
class Tag {
  String name;
  ILoAtt atts;

  // the constructor
  Tag(String name, ILoAtt atts) {
    this.name = name;
    this.atts = atts;
  }

  // to determine if this Tag has a given attribute
  public boolean hasAttribute(String attName) {
    return this.atts.hasAttribute(attName);
  }

  // to determine if this Tag has a given attribute name within a given tag name
  public boolean hasAttributeInTag(String attName, String tagName) {
    return this.name.equals(tagName) && this.atts.hasAttribute(attName);
  }

  // to change the values of all this Tag's Atts with a given name to a given
  // value
  public Tag updateAttribute(String attName, String attValue) {
    return new Tag(this.name, this.atts.updateAttribute(attName, attValue));
  }
  
  public boolean sameAttribute(Att att) {
    return this.atts.sameAttribute(att);
  }

}

/*
 * TEMPLATE: Fields: ... this.name ... -- String ... this.atts ... -- ILoAtt
 * METHODS: ... this.hasAttribute(String attName) ... -- boolean ...
 * this.hasAttributeInTag(String attName, String tagName) ... -- boolean ...
 * this.updateAttribute(String attName, String attValue) ... -- Tag METHODS FOR
 * FIELDS: ... this.atts.hasAttribute(String attName)... -- boolean ...
 * this.atts.renderAsString() ... -- String ... this.atts.updateAttribute(String
 * attName, String attValue) ... -- ILoAtt
 */

// to represent an attribute
class Att {
  String name;
  String value;

  // the constructor
  Att(String name, String value) {
    this.name = name;
    this.value = value;
  }

  // to change the values of this Att's value with a given value
  public Att updateAttribute(String attName, String attValue) {
    if (this.name.equals(attName)) {
      return new Att(this.name, attValue);
    }
    else {
      return this;
    }
  }

  /*
   * TEMPLATE: Fields: ... this.name ... -- String ... this.value ... -- String
   * METHODS: ... this.updateAttribute(String attName, String attValue) ... --
   * Att
   */

}

// to represent an XMLFrag
interface IXMLFrag {

  // to compute the number of characters in this IXMLFrag
  int contentLength();

  // to determine if this IXMLFrag has a given tag
  boolean hasTag(String tagName);

  // to determine if this IXMLFrag has a given attribute
  boolean hasAttribute(String attName);

  // to determine if this IXMLFrag has a given attribute name within a given tag
  // name
  boolean hasAttributeInTag(String attName, String tagName);

  // to convert this IXMLFrag to a String without tags or attributes
  String renderAsString();

  // to change the values of all this IXMLFrag's Atts with a given name to a
  // given value
  IXMLFrag updateAttribute(String attName, String attValue);

  boolean sameAttribute(Att att);
  
  boolean sameXMLFrag(IXMLFrag given);
  
}

// to represent a List-of XMLFrag
interface ILoXMLFrag {

  // to compute the number of characters in this ILoXMLFrag
  int contentLength();

  // to determine if this ILoXMLFrag has a given tag
  boolean hasTag(String tagName);

  // to determine if this ILoXMLFrag has a given attribute
  boolean hasAttribute(String attName);

  // to determine if this ILoXMLFrag has a given attribute name within a given
  // tag name
  boolean hasAttributeInTag(String attName, String tagName);

  // to convert this ILoXMLFrag to a String without tags or attributes
  String renderAsString();

  // to change the values of all this ILoXMLFrag's Atts with a given name to a
  // given value
  ILoXMLFrag updateAttribute(String attName, String attValue);
  
  IXMLFrag getHelper();

}

// to represent a List-of Att
interface ILoAtt {

  // to determine if this ILoAtt has a given attribute
  boolean hasAttribute(String attName);

  // to convert this ILoAtt to a String without tags or attributes
  String renderAsString();

  // to change the values of all this ILoAtt's Atts with a given name to a given
  // value
  ILoAtt updateAttribute(String attName, String attValue);
  
  boolean sameAttribute(Att att);

}

// to represent plaintext
class Plaintext implements IXMLFrag {
  String txt;

  // the constructor
  Plaintext(String txt) {
    this.txt = txt;
  }

  /*
   * TEMPLATE: Fields: ... this.txt ... -- String METHODS: ...
   * this.contentLength() ... -- int ... this.hasTag(String tagName) ... --
   * boolean ... this.hasAttribute(String attName)... -- boolean ...
   * this.hasAttributeInTag(String attName, String tagName) ... -- boolean ...
   * this.renderAsString() ... -- String ... this.updateAttribute(String
   * attName, String attValue) ... -- IXMLFrag
   */

  // to compute the number of characters in this Plaintext
  public int contentLength() {
    return this.txt.length();
  }

  // to determine if this Plaintext has a given tag
  public boolean hasTag(String tagName) {
    return false;
  }

  // to determine if this Plaintext has a given attribute
  public boolean hasAttribute(String attName) {
    return false;
  }

  // to determine if this Plaintext has a given attribute name within a given
  // tag name
  public boolean hasAttributeInTag(String attName, String tagName) {
    return false;
  }

  // to convert this Plaintext to a String without tags or attributes
  public String renderAsString() {
    return this.txt;
  }

  // to change the values of all this Plaintext's Atts with a given name to a
  // given value
  public Plaintext updateAttribute(String attName, String attValue) {
    return this;
  }

  public boolean sameAttribute(Att att) {
    return false;
  }
  public boolean sameXMLFrag(IXMLFrag given) {
    return false;
    
  }

}

// to represent a tagged XMLFrag
class Tagged implements IXMLFrag {
  Tag tag;
  ILoXMLFrag content;

  // the constructor
  Tagged(Tag tag, ILoXMLFrag content) {
    this.tag = tag;
    this.content = content;
  }

  /*
   * TEMPLATE: Fields: ... this.tag ... -- Tag ... this.content ... --
   * ILoXMLFrag METHODS: ... this.contentLength() ... -- int ...
   * this.hasTag(String tagName) ... -- boolean ... this.hasAttribute(String
   * attName)... -- boolean ... this.hasAttributeInTag(String attName, String
   * tagName) ... -- boolean ... this.renderAsString() ... -- String ...
   * this.updateAttribute(String attName, String attValue) ... -- IXMLFrag
   * METHODS FOR FIELDS: ... this.tag.hasAttribute(String attName) ... --
   * boolean ... this.tag.hasAttributeInTag(String attName, String tagName) ...
   * -- boolean ... this.tag.updateAttribute(String attName, String attValue)
   * ... -- IXMLFrag ... this.content.contentLength() ... -- int ...
   * this.content.hasTag(String tagName) ... -- boolean ...
   * this.content.hasAttribute(String attName) ... -- boolean ...
   * this.content.hasAttributesInTag(String attName, String tagName) ... --
   * boolean ... this.content.renderAsString() ... -- String ...
   * this.content.updateAttribute(String attName, String tagName) ... --
   * ILoXMLFrag
   */

  // to compute the number of characters in this Tagged
  public int contentLength() {
    return this.content.contentLength();
  }

  // to determine if this Tagged has a given tag
  public boolean hasTag(String tagName) {
    return this.tag.name.equals(tagName) || this.content.hasTag(tagName);
  }

  // to determine if this Tagged has a given attribute
  public boolean hasAttribute(String attName) {
    return this.tag.hasAttribute(attName) || this.content.hasAttribute(attName);
  }

  // to determine if this Tagged has a given attribute name within a given tag
  // name
  public boolean hasAttributeInTag(String attName, String tagName) {
    return this.tag.hasAttributeInTag(attName, tagName)
        || this.content.hasAttributeInTag(attName, tagName);
  }

  // to convert this Tagged to a String without tags or attributes
  public String renderAsString() {
    return this.content.renderAsString();
  }

  // to change the values of all this Tagged's Atts with a given name to a given
  // value
  public Tagged updateAttribute(String attName, String attValue) {
    return new Tagged(this.tag.updateAttribute(attName, attValue),
        this.content.updateAttribute(attName, attValue));
  }

  public boolean sameAttribute(Att att) {
    return this.tag.sameAttribute(att);
  }

  @Override
  public boolean sameXMLFrag(IXMLFrag given) {
    // TODO Auto-generated method stub
    return false;
  }

}

// to represent an empty List-of XMLFrag
class MtLoXMLFrag implements ILoXMLFrag {

  /*
   * TEMPLATE: METHODS: ... this.contentLength() ... -- int ...
   * this.hasTag(String tagName) ... -- boolean ... this.hasAttribute(String
   * attName)... -- boolean ... this.hasAttributeInTag(String attName, String
   * tagName) ... -- boolean ... this.renderAsString() ... -- String ...
   * this.updateAttribute(String attName, String attValue) ... -- ILoXMLFrag
   */

  // to compute the number of characters in this MtLoXMLFrag
  public int contentLength() {
    return 0;
  }

  // to determine if this MtLoXMLFrag has a given tag
  public boolean hasTag(String tagName) {
    return false;
  }

  // to determine if this MtLoXMLFrag has a given attribute
  public boolean hasAttribute(String attName) {
    return false;
  }

  // to determine if this MtLoXMLFrag has a given attribute name within a given
  // tag name
  public boolean hasAttributeInTag(String attName, String tagName) {
    return false;
  }

  // to convert this MtLoXMLFrag to a String without tags or attributes
  public String renderAsString() {
    return "";
  }

  // to change the values of all this MtLoXMLFrag's Atts with a given name to a
  // given value
  public MtLoXMLFrag updateAttribute(String attName, String attValue) {
    return this;
  }

  @Override
  public IXMLFrag getHelper() {
    return null;
  }

}

// to represent a non-empty List-of XMLFrag
class ConsLoXMLFrag implements ILoXMLFrag {
  IXMLFrag first;
  ILoXMLFrag rest;

  // the constructor
  ConsLoXMLFrag(IXMLFrag first, ILoXMLFrag rest) {
    this.first = first;
    this.rest = rest;
  }

  /*
   * TEMPLATE: Fields: ... this.first ... -- IXMLFrag ... this.rest ... --
   * ILoXMLFrag METHODS: ... this.contentLength() ... -- int ...
   * this.hasTag(String tagName) ... -- boolean ... this.hasAttribute(String
   * attName)... -- boolean ... this.hasAttributeInTag(String attName, String
   * tagName) ... -- boolean ... this.renderAsString() ... -- String ...
   * this.updateAttribute(String attName, String attValue) ... -- ILoXMLFrag
   * METHODS FOR FIELDS: ... this.first.contentLength() ... -- int ...
   * this.first.hasTag(String tagName) ... -- boolean ...
   * this.first.hasAttribute(String attName)... -- boolean ...
   * this.first.hasAttributeInTag(String attName, String tagName) ... -- boolean
   * ... this.first.renderAsString() ... -- String ...
   * this.first.updateAttribute(String attName, String attValue) ... -- IXMLFrag
   * ... this.rest.contentLength() ... -- int ... this.rest.hasTag(String
   * tagName) ... -- boolean ... this.rest.hasAttribute(String attName)... --
   * boolean ... this.rest.hasAttributeInTag(String attName, String tagName) ...
   * -- boolean ... this.rest.renderAsString() ... -- String ...
   * this.rest.updateAttribute(String attName, String attValue) ... --
   * ILoXMLFrag
   */

  // to compute the number of characters in this ConsLoXMLFrag
  public int contentLength() {
    return this.first.contentLength() + this.rest.contentLength();
  }

  // to determine if this ConsLoXMLFrag has a given tag
  public boolean hasTag(String tagName) {
    return this.first.hasTag(tagName) || this.rest.hasTag(tagName);
  }

  // to determine if this ConsLoXMLFrag has a given attribute
  public boolean hasAttribute(String attName) {
    return this.first.hasAttribute(attName) || this.rest.hasAttribute(attName);
  }

  // to determine if this ConsLoXMLFrag has a given attribute name within a
  // given tag name
  public boolean hasAttributeInTag(String attName, String tagName) {
    return this.first.hasAttributeInTag(attName, tagName)
        || this.rest.hasAttributeInTag(attName, tagName);
  }

  // to convert this ConsLoXMLFrag to a String without tags or attributes
  public String renderAsString() {
    return this.first.renderAsString() + this.rest.renderAsString();
  }

  // to change the values of all this ConsLoXMLFrag's Atts with a given name to
  // a given value
  public ConsLoXMLFrag updateAttribute(String attName, String attValue) {
    return new ConsLoXMLFrag(this.first.updateAttribute(attName, attValue),
        this.rest.updateAttribute(attName, attValue));
  }

  public boolean sameXMLFrag(IXMLFrag given) {
    return this.first
  }
  @Override
  public IXMLFrag getHelper() {
    return this.first;
  }

}

// to represent an empty List-of Attributes
class MtLoAtt implements ILoAtt {

  // to determine if this MtLoAtt has a given attribute
  public boolean hasAttribute(String attName) {
    return false;
  }

  // to convert this MtLoAtt to a String without tags or attributes
  public String renderAsString() {
    return "";
  }

  // to change the values of all this MtLoAtt's Atts with a given name to a
  // given value
  public MtLoAtt updateAttribute(String attName, String attValue) {
    return this;
  }

  /*
   * TEMPLATE: METHODS: ... this.hasAttribute(String attName) ... -- boolean ...
   * this.renderAsString() ... -- String ... this.updateAttribute(String
   * attName, String attValue) ... -- ILoATT
   */
  
  public boolean sameAttribute(Att att) {
    return false;
  }
}

// to represent a non-empty List-of Attributes
class ConsLoAtt implements ILoAtt {
  Att first;
  ILoAtt rest;

  // the constructor
  ConsLoAtt(Att first, ILoAtt rest) {
    this.first = first;
    this.rest = rest;
  }

  // to determine if this ConsLoAtt has a given attribute
  public boolean hasAttribute(String attName) {
    return this.first.name.equals(attName) || this.rest.hasAttribute(attName);
  }

  // to convert this ConsLoAtt to a String without tags or attributes
  public String renderAsString() {
    return "";
  }

  // to change the values of all this ConsLoAtt's Atts with a given name to a
  // given value
  public ConsLoAtt updateAttribute(String attName, String attValue) {
    return new ConsLoAtt(this.first.updateAttribute(attName, attValue),
        this.rest.updateAttribute(attName, attValue));
  }
  
  public boolean sameAttribute(Att att) {
    if (first.name.equals(att.name)) {
      return true;
    }
    else {
      return this.rest.sameAttribute(att);
    }
  }

  /*
   * TEMPLATE: Fields: ... this.first ... -- Att ... this.rest ... -- ILoAtt
   * METHODS: ... this.hasAttribute(String attName) ... -- boolean ...
   * this.renderAsString() ... -- String ... this.updateAttribute(String
   * attName, String attValue) ... -- ILoAtt METHODS FOR FIELDS: ...
   * this.first.updateAttribute(String attName, String attValue) ... -- Att ...
   * this.rest.hasAttribute(String attName) ... -- boolean ...
   * this.rest.renderAsString() ... -- String ...
   * this.rest.updateAttribute(String attName, String attValue) ... -- ILoAtt
   */

}

class ExamplesXML {
  ILoXMLFrag xml1 = new ConsLoXMLFrag(new Plaintext("I am XML!"), new MtLoXMLFrag());
  ILoXMLFrag xml2 = new ConsLoXMLFrag(new Plaintext("I am "),
      new ConsLoXMLFrag(
          new Tagged(new Tag("yell", new MtLoAtt()),
              new ConsLoXMLFrag(new Plaintext("XML"), new MtLoXMLFrag())),
          new ConsLoXMLFrag(new Plaintext("!"), new MtLoXMLFrag())));
  ILoXMLFrag xml3 = new ConsLoXMLFrag(new Plaintext("I am "),
      new ConsLoXMLFrag(
          new Tagged(new Tag("yell", new MtLoAtt()),
              new ConsLoXMLFrag(
                  new Tagged(new Tag("italic", new MtLoAtt()),
                      new ConsLoXMLFrag(new Plaintext("X"), new MtLoXMLFrag())),
                  new ConsLoXMLFrag(new Plaintext("ML"), new MtLoXMLFrag()))),
          new ConsLoXMLFrag(new Plaintext("!"), new MtLoXMLFrag())));
  ILoXMLFrag xml4 = new ConsLoXMLFrag(new Plaintext("I am "),
      new ConsLoXMLFrag(
          new Tagged(new Tag("yell", new ConsLoAtt(new Att("volume", "30db"), new MtLoAtt())),
              new ConsLoXMLFrag(
                  new Tagged(new Tag("italic", new MtLoAtt()),
                      new ConsLoXMLFrag(new Plaintext("X"), new MtLoXMLFrag())),
                  new ConsLoXMLFrag(new Plaintext("ML"), new MtLoXMLFrag()))),
          new ConsLoXMLFrag(new Plaintext("!"), new MtLoXMLFrag())));
  ILoXMLFrag xml5 = new ConsLoXMLFrag(new Plaintext("I am "),
      new ConsLoXMLFrag(
          new Tagged(
              new Tag("yell",
                  new ConsLoAtt(new Att("volume", "30db"),
                      new ConsLoAtt(new Att("duration", "5sec"), new MtLoAtt()))),
              new ConsLoXMLFrag(
                  new Tagged(new Tag("italic", new MtLoAtt()),
                      new ConsLoXMLFrag(new Plaintext("X"), new MtLoXMLFrag())),
                  new ConsLoXMLFrag(new Plaintext("ML"), new MtLoXMLFrag()))),
          new ConsLoXMLFrag(new Plaintext("!"), new MtLoXMLFrag())));

  ILoXMLFrag newxml4 = new ConsLoXMLFrag(new Plaintext("I am "),
      new ConsLoXMLFrag(
          new Tagged(new Tag("yell", new ConsLoAtt(new Att("volume", "60db"), new MtLoAtt())),
              new ConsLoXMLFrag(
                  new Tagged(new Tag("italic", new MtLoAtt()),
                      new ConsLoXMLFrag(new Plaintext("X"), new MtLoXMLFrag())),
                  new ConsLoXMLFrag(new Plaintext("ML"), new MtLoXMLFrag()))),
          new ConsLoXMLFrag(new Plaintext("!"), new MtLoXMLFrag())));
  ILoXMLFrag newxml5 = new ConsLoXMLFrag(new Plaintext("I am "),
      new ConsLoXMLFrag(
          new Tagged(
              new Tag("yell",
                  new ConsLoAtt(new Att("volume", "60db"),
                      new ConsLoAtt(new Att("duration", "5sec"), new MtLoAtt()))),
              new ConsLoXMLFrag(
                  new Tagged(new Tag("italic", new MtLoAtt()),
                      new ConsLoXMLFrag(new Plaintext("X"), new MtLoXMLFrag())),
                  new ConsLoXMLFrag(new Plaintext("ML"), new MtLoXMLFrag()))),
          new ConsLoXMLFrag(new Plaintext("!"), new MtLoXMLFrag())));
  ILoXMLFrag new2xml5 = new ConsLoXMLFrag(new Plaintext("I am "),
      new ConsLoXMLFrag(
          new Tagged(
              new Tag("yell",
                  new ConsLoAtt(new Att("volume", "30db"),
                      new ConsLoAtt(new Att("duration", "30sec"), new MtLoAtt()))),
              new ConsLoXMLFrag(
                  new Tagged(new Tag("italic", new MtLoAtt()),
                      new ConsLoXMLFrag(new Plaintext("X"), new MtLoXMLFrag())),
                  new ConsLoXMLFrag(new Plaintext("ML"), new MtLoXMLFrag()))),
          new ConsLoXMLFrag(new Plaintext("!"), new MtLoXMLFrag())));

  Tag tag1 = new Tag("yell", new MtLoAtt());
  Tag tag2 = new Tag("italic", new MtLoAtt());
  Tag tag3 = new Tag("yell", new ConsLoAtt(new Att("volume", "30db"), new MtLoAtt()));
  Tag tag4 = new Tag("yell", new ConsLoAtt(new Att("volume", "30db"),
      new ConsLoAtt(new Att("duration", "5sec"), new MtLoAtt())));

  // test the method contentLength
  boolean testContentLength(Tester t) {
    return t.checkExpect(this.xml1.contentLength(), 9)
        && t.checkExpect(this.xml2.contentLength(), 9)
        && t.checkExpect(this.xml3.contentLength(), 9)
        && t.checkExpect(this.xml4.contentLength(), 9)
        && t.checkExpect(this.xml5.contentLength(), 9);
  }

  // test the method hasTag
  boolean testHasTag(Tester t) {
    return t.checkExpect(this.xml1.hasTag("yell"), false)
        && t.checkExpect(this.xml1.hasTag("italic"), false)
        && t.checkExpect(this.xml2.hasTag("yell"), true)
        && t.checkExpect(this.xml2.hasTag("italic"), false)
        && t.checkExpect(this.xml3.hasTag("yell"), true)
        && t.checkExpect(this.xml3.hasTag("italic"), true)
        && t.checkExpect(this.xml4.hasTag("yell"), true)
        && t.checkExpect(this.xml4.hasTag("italic"), true)
        && t.checkExpect(this.xml5.hasTag("yell"), true)
        && t.checkExpect(this.xml5.hasTag("italic"), true);
  }

  // test the method hasAttribute
  boolean testHasAttribute(Tester t) {
    return t.checkExpect(this.xml1.hasAttribute("volume"), false)
        && t.checkExpect(this.xml1.hasAttribute("duration"), false)
        && t.checkExpect(this.xml2.hasAttribute("volume"), false)
        && t.checkExpect(this.xml2.hasAttribute("duration"), false)
        && t.checkExpect(this.xml3.hasAttribute("volume"), false)
        && t.checkExpect(this.xml3.hasAttribute("duration"), false)
        && t.checkExpect(this.xml4.hasAttribute("volume"), true)
        && t.checkExpect(this.xml4.hasAttribute("duration"), false)
        && t.checkExpect(this.xml5.hasAttribute("volume"), true)
        && t.checkExpect(this.xml5.hasAttribute("duration"), true);
  }

  // test the method hasAtributeInTag
  boolean testHasAttributeInTag(Tester t) {
    return t.checkExpect(this.xml1.hasAttributeInTag("volume", "yell"), false)
        && t.checkExpect(this.xml1.hasAttributeInTag("duration", "yell"), false)
        && t.checkExpect(this.xml2.hasAttributeInTag("volume", "yell"), false)
        && t.checkExpect(this.xml2.hasAttributeInTag("duration", "yell"), false)
        && t.checkExpect(this.xml3.hasAttributeInTag("volume", "yell"), false)
        && t.checkExpect(this.xml3.hasAttributeInTag("duration", "yell"), false)
        && t.checkExpect(this.xml4.hasAttributeInTag("volume", "yell"), true)
        && t.checkExpect(this.xml4.hasAttributeInTag("duration", "yell"), false)
        && t.checkExpect(this.xml4.hasAttributeInTag("volume", "italic"), false)
        && t.checkExpect(this.xml4.hasAttributeInTag("duration", "italic"), false)
        && t.checkExpect(this.xml5.hasAttributeInTag("volume", "yell"), true)
        && t.checkExpect(this.xml5.hasAttributeInTag("duration", "yell"), true)
        && t.checkExpect(this.xml5.hasAttributeInTag("volume", "italic"), false)
        && t.checkExpect(this.xml5.hasAttributeInTag("duration", "italic"), false);
  }

  // test the method renderAsString
  boolean testRenderAsString(Tester t) {
    return t.checkExpect(this.xml1.renderAsString(), "I am XML!")
        && t.checkExpect(this.xml2.renderAsString(), "I am XML!")
        && t.checkExpect(this.xml3.renderAsString(), "I am XML!")
        && t.checkExpect(this.xml4.renderAsString(), "I am XML!")
        && t.checkExpect(this.xml5.renderAsString(), "I am XML!");
  }

  // test the method updateAttribute
  boolean testUpdateAttribute(Tester t) {
    return t.checkExpect(this.xml1.updateAttribute("volume", "60db"), xml1)
        && t.checkExpect(this.xml2.updateAttribute("volume", "60db"), xml2)
        && t.checkExpect(this.xml3.updateAttribute("volume", "60db"), xml3)
        && t.checkExpect(this.xml4.updateAttribute("duration", "10sec"), xml4)
        && t.checkExpect(this.xml4.updateAttribute("volume", "60db"), newxml4)
        && t.checkExpect(this.xml5.updateAttribute("volume", "60db"), newxml5)
        && t.checkExpect(this.xml5.updateAttribute("duration", "30sec"), new2xml5);
  }
}