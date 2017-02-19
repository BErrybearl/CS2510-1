interface ILoString {

}

class MtLoString implements ILoString {

}

class ConsLoString implements ILoString {
  String first;
  ILoString rest;

  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }
}

interface IPlot {

  ILoString listPlotTwists();

  boolean isEnd();

  String getString();

  String getdes();

}

class End implements IPlot {
  String description;

  End(String description) {
    this.description = description;
  }

  @Override
  public ILoString listPlotTwists() {
    return new ConsLoString(this.description, new MtLoString());
  }

  @Override
  public boolean isEnd() {
    return true;
  }

  @Override
  public String getString() {
    return this.description;
  }

  @Override
  public String getdes() {
    return this.description;
  }
}

class Event implements IPlot {
  String character;
  String description;
  IPlot left;
  IPlot right;

  Event(String character, String description, IPlot left, IPlot right) {
    this.character = character;
    this.description = description;
    this.left = left;
    this.right = right;
  }

  public boolean isEnd() {
    return false;
  }

  public boolean isleftend() {
    return this.left.isEnd();
  }

  public boolean isrightend() {
    return this.right.isEnd();
  }

  public String getString() {
    if (this.isEnd()) {
      return this.getString();
    }
    else {
      return null;
    }
  }
  
  public String getdes() {
    return this.description;
  }

  
  public ILoString listPlotTwists() {
    if (left.isEnd() && right.isEnd()) {
      return new ConsLoString(this.description, new ConsLoString(this.left.getString(), new ConsLoString(this.right.getString(), new MtLoString())));
    }
    else if (left.isEnd() && !right.isEnd()) {
      return new ConsLoString(this.description, new ConsLoString(this.left.getString(), new ConsLoString(this.right.getString(), this.right.listPlotTwists())));
    }
    else if (!left.isEnd() && right.isEnd()) {
      return new ConsLoString(this.description, new ConsLoString(this.right.getString(), new ConsLoString(this.left.getString(), this.left.listPlotTwists())));
    }
    else {
      return new ConsLoString(this.description, new ConsLoString(this.left.getString(), ));
    }

}
  }
