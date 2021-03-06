import tester.Tester;

//to represent a List-of Att
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

}

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

  // to determine if the given attribute is the same with this attribute
  public boolean sameAttribute(Att att) {
    return (this.name.equals(att.name) && (this.value.equals(att.value)));
  }

}

// to represent a tag
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

  // to determine if this IXMLFrag is the same as the given IXMLFrag
  boolean sameXMLFrag(IXMLFrag given);

  // to determine whether this IXMLFrag is a Plaintext
  boolean isText();

  // to determine whether this IXMLFrag is a Tagged
  boolean isTagged();

}

// to represent plaintext
class Plaintext implements IXMLFrag {
  String txt;

  // the constructor
  Plaintext(String txt) {
    this.txt = txt;
  }

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

  // to determine whether this Plaintext is same as the given one
  public boolean sameText(Plaintext given) {
    return this.txt.equals(given.txt);
  }

  // to determine whether this IXMLFrag is a Plaintext
  public boolean isText() {
    return true;
  }

  // to determine whether this IXMLFrag is a Tagged
  public boolean isTagged() {
    return false;
  }

  // to determine if this IXMLFrag is the same as the given IXMLFrag
  public boolean sameXMLFrag(IXMLFrag given) {
    if (given.isText()) {
      return this.sameText((Plaintext) given);
    }
    else {
      return false;
    }

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

  // to determine whether this Taaged is same as the given one
  public boolean sameTagged(Tagged given) {
    return this.tag.equals(given.tag) && this.content.sameXMLDoc(given.content);
  }

  // to determine whether this IXMLFrag is a Plaintext
  public boolean isText() {
    return false;
  }

  // to determine whether this IXMLFrag is a Tagged
  public boolean isTagged() {
    return true;
  }

  // to determine if this IXMLFrag is the same as the given IXMLFrag
  public boolean sameXMLFrag(IXMLFrag given) {
    if (given.isTagged()) {
      return this.sameTagged((Tagged) given);
    }
    else {
      return false;
    }
  }

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

  // to determine if this XML is the same as the given XML
  boolean sameXMLDoc(ILoXMLFrag given);

  // to determine if this ILoXMLFrag is the same as the given MtLoXMLFrag
  boolean sameMtLoXMLFrag(ILoXMLFrag given);

  // to determine if this ILoXMLFrag is the same as the given ConsLoXMLFrag
  boolean sameConsLoXMLFrag(ConsLoXMLFrag given);

}

// to represent an empty List-of XMLFrag
class MtLoXMLFrag implements ILoXMLFrag {

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

  // to determine if this XML is the same as the given XML
  public boolean sameXMLDoc(ILoXMLFrag given) {
    return given.sameMtLoXMLFrag(this);
  }

  // to determine if this ILoXMLFrag is the same as the given MtLoXMLFrag
  public boolean sameMtLoXMLFrag(ILoXMLFrag given) {
    return true;
  }

  // to determine if this ILoXMLFrag is the same as the given ConsLoXMLFrag
  public boolean sameConsLoXMLFrag(ConsLoXMLFrag given) {
    return false;
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

  // to determine if this ILoXMLFrag is the same as the given ILoXMLFrag
  public boolean sameXMLFrag(IXMLFrag given) {
    return true;
  }

  // to determine if this XML is the same as the given XML
  public boolean sameXMLDoc(ILoXMLFrag given) {
    return given.sameConsLoXMLFrag(this);
  }

  // to determine if this ILoXMLFrag is the same as the given MtLoXMLFrag
  public boolean sameMtLoXMLFrag(ILoXMLFrag given) {
    return false;
  }

  // to determine if this ILoXMLFrag is the same as the given ConsLoXMLFrag
  public boolean sameConsLoXMLFrag(ConsLoXMLFrag given) {
    if (this.first.sameXMLFrag(given.first)) {
      return this.rest.sameXMLDoc(given.rest);
    }
    else {
      return false;
    }
  }

}

class ExamplesXML {

  Att att1 = new Att("volume", "30db");
  Att att2 = new Att("duration", "5sec");
  Att att3 = new Att("volume", "50db");
  Att att4 = new Att("volume", "30db");

  Plaintext txt1 = new Plaintext("I am XML!");
  Plaintext txt2 = new Plaintext("I am ");
  Plaintext txt3 = new Plaintext("X");
  Plaintext txt4 = new Plaintext("ML");
  Plaintext txt5 = new Plaintext("!");
  Plaintext txt6 = new Plaintext("XML");

  Tag tag1 = new Tag("yell", new MtLoAtt());
  Tag tag2 = new Tag("italic", new MtLoAtt());
  Tag tag3 = new Tag("yell", new ConsLoAtt(this.att1, new MtLoAtt()));
  Tag tag4 = new Tag("yell", new ConsLoAtt(this.att1, new ConsLoAtt(this.att2, new MtLoAtt())));
  Tag tag3up = new Tag("yell", new ConsLoAtt(this.att3, new MtLoAtt()));
  Tag tag5 = new Tag("yell", new MtLoAtt());

  // tag yell on XML
  IXMLFrag frag1 = new Tagged(this.tag1,
      new ConsLoXMLFrag(this.txt6, new MtLoXMLFrag()));
  // tag italic on X
  IXMLFrag nfrag1 = new Tagged(this.tag2, new ConsLoXMLFrag(this.txt3, new MtLoXMLFrag()));
  // <yell><italic>X</italic>ML</yell>
  IXMLFrag frag2 = new Tagged(this.tag1,
      new ConsLoXMLFrag(this.nfrag1, new ConsLoXMLFrag(this.txt4, new MtLoXMLFrag())));

  // tag with volume
  IXMLFrag frag3 = new Tagged(this.tag3,
      new ConsLoXMLFrag(this.nfrag1, new ConsLoXMLFrag(this.txt4, new MtLoXMLFrag())));
  
  // tag with volume and duration
  IXMLFrag frag4 = new Tagged(this.tag4,
      new ConsLoXMLFrag(this.nfrag1, new ConsLoXMLFrag(this.txt4, new MtLoXMLFrag())));
  IXMLFrag frag3up = new Tagged(this.tag3up,
      new ConsLoXMLFrag(this.nfrag1, new ConsLoXMLFrag(this.txt4, new MtLoXMLFrag())));

  
  ILoXMLFrag xml1 = new ConsLoXMLFrag(this.txt1, new MtLoXMLFrag());

  ILoXMLFrag xml2 = new ConsLoXMLFrag(this.txt2,
      new ConsLoXMLFrag(frag1, new ConsLoXMLFrag(this.txt5, new MtLoXMLFrag())));

  ILoXMLFrag xml3 = new ConsLoXMLFrag(this.txt2,
      new ConsLoXMLFrag(this.frag2, new ConsLoXMLFrag(this.txt5, new MtLoXMLFrag())));
  ILoXMLFrag xml4 = new ConsLoXMLFrag(this.txt2,
      new ConsLoXMLFrag(this.frag3, new ConsLoXMLFrag(this.txt5, new MtLoXMLFrag())));
  ILoXMLFrag xml5 = new ConsLoXMLFrag(this.txt2,
      new ConsLoXMLFrag(this.frag4, new ConsLoXMLFrag(this.txt5, new MtLoXMLFrag())));

  ILoXMLFrag xml4up = new ConsLoXMLFrag(this.txt2,
      new ConsLoXMLFrag(this.frag3up, new ConsLoXMLFrag(this.txt5, new MtLoXMLFrag())));
  
  ILoXMLFrag xml6 = new MtLoXMLFrag();

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
        && t.checkExpect(this.xml2.hasTag("italic"), false)
        && t.checkExpect(this.xml3.hasTag("italic"), true)
        && t.checkExpect(this.xml4.hasTag("yell"), true)
        && t.checkExpect(this.xml5.hasTag("yell"), true);
  }

  // test the method hasAttribute
  boolean testHasAttribute(Tester t) {
    return t.checkExpect(this.xml1.hasAttribute("volume"), false)
        && t.checkExpect(this.xml2.hasAttribute("duration"), false)
        && t.checkExpect(this.xml3.hasAttribute("volume"), false)
        && t.checkExpect(this.xml4.hasAttribute("duration"), false)
        && t.checkExpect(this.xml5.hasAttribute("duration"), true);
  }

  // test the method hasAtributeInTag
  boolean testHasAttributeInTag(Tester t) {
    return t.checkExpect(this.xml1.hasAttributeInTag("italic", "volume"), false)
        && t.checkExpect(this.xml2.hasAttributeInTag("yell", "duration"), false)
        && t.checkExpect(this.xml3.hasAttributeInTag("italic", "volume"), false)
        && t.checkExpect(this.xml4.hasAttributeInTag("yell", "volume"), false)
        && t.checkExpect(this.xml5.hasAttributeInTag("italic", "duration"), false);
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
    return t.checkExpect(this.xml1.updateAttribute("volume", "30db"), this.xml1)
        && t.checkExpect(this.xml4.updateAttribute("volume", "50db"), this.xml4up);
  }
  
  // test the method sameAttribute
  boolean testSameAttribute(Tester t) {
    return t.checkExpect(this.att1.sameAttribute(att2), false)
        && t.checkExpect(this.att2.sameAttribute(att3), false)
        && t.checkExpect(this.att3.sameAttribute(att4), false)
        && t.checkExpect(this.att1.sameAttribute(att4), true);
  }
  
  // test the method isTagged
  boolean testIsTagged(Tester t) {
    return t.checkExpect(this.txt1.isTagged(), false)
        && t.checkExpect(this.txt2.isTagged(), false)
        && t.checkExpect(this.frag1.isTagged(), true)
        && t.checkExpect(this.frag2.isTagged(), true)
        && t.checkExpect(this.frag3up.isTagged(), true);
  }
  
  // test the method isText
  boolean testIsText(Tester t) {
    return t.checkExpect(this.txt1.isText(), true)
        && t.checkExpect(this.txt2.isText(), true)
        && t.checkExpect(this.txt3.isText(), true)
        && t.checkExpect(this.frag1.isText(), false)
        && t.checkExpect(this.frag2.isText(), false);
  }
  
  // test the method sameTagged
  boolean testSameTagged(Tester t) {
    return t.checkExpect(((Tagged) this.frag1).sameTagged((Tagged) frag2), false)
        && t.checkExpect(((Tagged) this.frag1).sameTagged((Tagged) frag1), true);
 
  }
  
  // test the method samePlaintext
  boolean testSamePlaintext(Tester t) {
    return t.checkExpect(this.txt1.sameText(txt2), false)
        && t.checkExpect(this.txt1.sameText(txt1), true);
 
  }
  
  // test the method sameIXMLFrag
  boolean testSameIXMLFrag(Tester t) {
    return t.checkExpect(this.frag1.sameXMLFrag(nfrag1), false)
        && t.checkExpect(this.frag1.sameXMLFrag(frag1), true);

  }
  
  // test the method sameMtLoXMLFrag
  boolean testSameMtLoXMLFrag(Tester t) {
    return t.checkExpect(this.xml1.sameMtLoXMLFrag(xml6), false)
        && t.checkExpect(this.xml6.sameMtLoXMLFrag(xml6), true);

  }
  
  // test the method sameConsLoXMLFrag
  boolean testSameConsLoXMLFrag(Tester t) {
    return t.checkExpect(this.xml1.sameConsLoXMLFrag((ConsLoXMLFrag) xml2), false)
        && t.checkExpect(this.xml2.sameConsLoXMLFrag((ConsLoXMLFrag) xml2), true);

  }

  // test the method sameXMLDoc
  boolean testSameXMLDoc(Tester t) {
    return t.checkExpect(this.xml4.sameXMLDoc(xml4up), false)
        && t.checkExpect(this.xml5.sameXMLDoc(xml5), true);

  }
  
  
}
