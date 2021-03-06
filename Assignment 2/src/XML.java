import tester.*;

interface ILoAtt {

  boolean hasAttribute(String given);

  ILoAtt updateAttribute(String g1, String g2);
}

class MtLoAtt implements ILoAtt {
  public boolean hasAttribute(String given) {
    return false;
  }

  public ILoAtt updateAttribute(String g1, String g2) {
    return new MtLoAtt();
  }
}

class ConsLoAtt implements ILoAtt {

  Att first;
  ILoAtt rest;

  ConsLoAtt(Att first, ILoAtt rest) {
    this.first = first;
    this.rest = rest;
  }

  public boolean hasAttribute(String given) {
    if (this.first.name.equals(given)) {
      return true;
    }
    else {
      return this.rest.hasAttribute(given);
    }
  }

  public ILoAtt updateAttribute(String g1, String g2) {
    return new ConsLoAtt(this.first.updateAttribute(g1, g2), this.rest.updateAttribute(g1, g2));
  }
}

class Att {

  String name;
  String value;

  Att(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public Att updateAttribute(String g1, String g2) {
    if (this.name.equals(g1)) {
      return new Att(this.name, g2);
    }
    else {
      return this;
    }
  }
}

class Tag {

  String name;
  ILoAtt atts;

  Tag(String name, ILoAtt atts) {
    this.name = name;
    this.atts = atts;
  }

  public boolean hasAttribute(String given) {
    return this.atts.hasAttribute(given);
  }

  public Tag updateAttribute(String g1, String g2) {
    return new Tag(this.name, this.atts.updateAttribute(g1, g2));
  }
}


interface IXMLFrag {

  int contentLength();

  boolean hasTag(String given);

  boolean hasAttribute(String given);

  boolean hasAttributeInTag(String tgiven, String agiven);

  String renderAsString();

  IXMLFrag updateAttribute(String g1, String g2);

}

class Plaintext implements IXMLFrag {
  String txt;

  Plaintext(String plaintext) {
    this.txt = plaintext;
  }

  public int contentLength() {
    return this.txt.length();
  }

  public boolean hasTag(String given) {
    return false;
  }

  public boolean hasAttribute(String given) {
    return false;
  }

  public boolean hasAttributeInTag(String tgiven, String agiven) {
    return false;
  }

  public String renderAsString() {
    return this.txt;
  }

  public IXMLFrag updateAttribute(String g1, String g2) {
    return this;
  }
}

class Tagged implements IXMLFrag {

  Tag tag;
  ILoXMLFrag content;

  Tagged(Tag tag, ILoXMLFrag content) {
    this.tag = tag;
    this.content = content;
  }

  public int contentLength() {
    return this.content.contentLength();
  }

  public boolean hasTag(String given) {
    if (this.tag.name.equals(given)) {
      return true;
    }
    else {
      return this.content.hasTag(given);
    }
  }

  public boolean hasAttribute(String given) {
    if (this.tag.hasAttribute(given)) {
      return true;
    }
    else {
      return this.content.hasAttribute(given);
    }
  }

  public boolean hasAttributeInTag(String tgiven, String agiven) {
    if (this.tag.name.equals(tgiven) && this.tag.hasAttribute(agiven)) {
      return true;
    }
    else {
      return this.content.hasAttributeInTag(tgiven, agiven);
    }
  }

  public String renderAsString() {
    return content.renderAsString();
  }

  public IXMLFrag updateAttribute(String g1, String g2) {
    return new Tagged(this.tag.updateAttribute(g1, g2), this.content.updateAttribute(g1, g2));
  }

}

interface ILoXMLFrag {

  int contentLength();

  boolean hasAttribute(String given);

  boolean hasTag(String given);

  boolean hasAttributeInTag(String tgiven, String agiven);

  String renderAsString();

  ILoXMLFrag updateAttribute(String g1, String g2);

}

class MtLoXMLFrag implements ILoXMLFrag {
  public int contentLength() {
    return 0;
  }

  public boolean hasTag(String given) {
    return false;
  }

  public boolean hasAttribute(String given) {
    return false;
  }

  public boolean hasAttributeInTag(String tgiven, String agiven) {
    return false;
  }

  public String renderAsString() {
    return "";
  }

  public ILoXMLFrag updateAttribute(String g1, String g2) {
    return new MtLoXMLFrag();
  }
}

class ConsLoXMLFrag implements ILoXMLFrag {

  IXMLFrag first;
  ILoXMLFrag rest;

  ConsLoXMLFrag(IXMLFrag first, ILoXMLFrag rest) {
    this.first = first;
    this.rest = rest;
  }

  public int contentLength() {
    return this.first.contentLength() + this.rest.contentLength();
  }

  public boolean hasTag(String given) {
    if (this.first.hasTag(given)) {
      return true;
    }
    else {
      return this.rest.hasTag(given);
    }
  }

  public boolean hasAttribute(String given) {
    if (this.first.hasAttribute(given)) {
      return true;
    }
    else {
      return this.rest.hasAttribute(given);
    }
  }

  public boolean hasAttributeInTag(String tgiven, String agiven) {
    if (this.first.hasAttributeInTag(tgiven, agiven)) {
      return true;
    }
    else {
      return this.rest.hasAttributeInTag(tgiven, agiven);
    }
  }

  public String renderAsString() {
    return this.first.renderAsString() + this.rest.renderAsString();
  }

  public ILoXMLFrag updateAttribute(String g1, String g2) {
    return new ConsLoXMLFrag(this.first.updateAttribute(g1, g2), this.rest.updateAttribute(g1, g2));
  }
}

class ExamplesXML {

  Att att1 = new Att("volume", "30db");
  Att att2 = new Att("duration", "5sec");
  Att att3 = new Att("volume", "50db");

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

  // tag yell on XML
  IXMLFrag frag1 = new Tagged(this.tag1,
      new ConsLoXMLFrag(this.txt6, new MtLoXMLFrag()));
  // tag italic on X
  IXMLFrag frag10 = new Tagged(this.tag2, new ConsLoXMLFrag(this.txt3, new MtLoXMLFrag()));
  // <yell><italic>X</italic>ML</yell>
  IXMLFrag frag2 = new Tagged(this.tag1,
      new ConsLoXMLFrag(this.frag10, new ConsLoXMLFrag(this.txt4, new MtLoXMLFrag())));

  // tag with volume
  IXMLFrag frag3 = new Tagged(this.tag3,
      new ConsLoXMLFrag(this.frag10, new ConsLoXMLFrag(this.txt4, new MtLoXMLFrag())));
  
  // tag with volume and duration
  IXMLFrag frag4 = new Tagged(this.tag4,
      new ConsLoXMLFrag(this.frag10, new ConsLoXMLFrag(this.txt4, new MtLoXMLFrag())));
  IXMLFrag frag3up = new Tagged(this.tag3up,
      new ConsLoXMLFrag(this.frag10, new ConsLoXMLFrag(this.txt4, new MtLoXMLFrag())));

  
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

  boolean testContentLength(Tester t) {
    return t.checkExpect(this.xml1.contentLength(), 9)
        && t.checkExpect(this.xml2.contentLength(), 9)
        && t.checkExpect(this.xml3.contentLength(), 9)
        && t.checkExpect(this.xml4.contentLength(), 9)
        && t.checkExpect(this.xml5.contentLength(), 9);
  }

  boolean testHasTag(Tester t) {
    return t.checkExpect(this.xml1.hasTag("yell"), false)
        && t.checkExpect(this.xml2.hasTag("italic"), false)
        && t.checkExpect(this.xml3.hasTag("italic"), true)
        && t.checkExpect(this.xml4.hasTag("yell"), true)
        && t.checkExpect(this.xml5.hasTag("yell"), true);
  }

  boolean testHasAttribute(Tester t) {
    return t.checkExpect(this.xml1.hasAttribute("volume"), false)
        && t.checkExpect(this.xml2.hasAttribute("duration"), false)
        && t.checkExpect(this.xml3.hasAttribute("volume"), false)
        && t.checkExpect(this.xml4.hasAttribute("duration"), false)
        && t.checkExpect(this.xml5.hasAttribute("duration"), true);
  }

  boolean testHasAttributeInTag(Tester t) {
    return t.checkExpect(this.xml1.hasAttributeInTag("italic", "volume"), false)
        && t.checkExpect(this.xml2.hasAttributeInTag("yell", "duration"), false)
        && t.checkExpect(this.xml3.hasAttributeInTag("italic", "volume"), false)
        && t.checkExpect(this.xml4.hasAttributeInTag("yell", "volume"), true)
        && t.checkExpect(this.xml5.hasAttributeInTag("italic", "duration"), false);
  }

  boolean testRenderAsString(Tester t) {
    return t.checkExpect(this.xml1.renderAsString(), "I am XML!")
        && t.checkExpect(this.xml2.renderAsString(), "I am XML!")
        && t.checkExpect(this.xml3.renderAsString(), "I am XML!")
        && t.checkExpect(this.xml4.renderAsString(), "I am XML!")
        && t.checkExpect(this.xml5.renderAsString(), "I am XML!");
  }

  boolean testUpdateAttribute(Tester t) {
    return t.checkExpect(this.xml1.updateAttribute("volume", "30db"), this.xml1)
        && t.checkExpect(this.xml4.updateAttribute("volume", "50db"), this.xml4up);

  }
}
