
//Assignment 4

import tester.*;

//to represent different types of news media
interface INewsMedia {

  // compute the length of this INewsMedia
  int length();

  // formats the title, corporation, and episode number (if it exists) of this
  // INewsMedia as a String
  String format();

  // is the corporation of this media the same
  // as the corporation of the given media?
  boolean sameCorporation(INewsMedia that);

  boolean sameCorporationHelper(String corp);
}

abstract class ANewsMedia implements INewsMedia {

  String title;
  String corporation;

  ANewsMedia(String title, String corporation) {
    this.title = title;
    this.corporation = corporation;
  }

  public boolean sameCorporation(INewsMedia that) {
    return that.sameCorporationHelper(this.corporation);
  }

  public boolean sameCorporationHelper(String corp) {
    return this.corporation.equals(corp);
  }
}

// to represent a newspaper article
class Newspaper extends ANewsMedia {

  int columns; // number of columns the article spans
  int inches; // number of inches in each column

  Newspaper(String title, String corporation, int columns, int inches) {
    super(title, corporation);
    this.columns = columns;
    this.inches = inches;
  }

  // compute the size of this Newspaper article, measured in column-inches
  public int length() {
    return this.columns * this.inches;
  }

  // formats the title and corporation of this Newspaper article as a String
  public String format() {
    return this.title + ", " + this.corporation;
  }

  // is the corporation of this Newspaper the same
  // as that of the given INewsMedia?
}

// to represent a TV news program
class Television extends ANewsMedia {

  int minutes;
  int episodeNum;

  Television(String title, String corporation, int minutes, int episodeNum) {
    super(title, corporation);
    this.minutes = minutes;
    this.episodeNum = episodeNum;
  }

  // compute the size of this Television news program
  public int length() {
    return this.minutes;
  }

  // formats the title, corporation and episode number of this Television
  // program article as a String
  public String format() {
    return this.title + ", " + this.corporation + ", " + "Episode " + Integer.toString(episodeNum);
  }

}

// to represent a radio news program
class Radio extends ANewsMedia {

  int minutes;
  int episodeNum;

  Radio(String title, String corporation, int minutes, int episodeNum) {
    super(title, corporation);
    this.minutes = minutes;
    this.episodeNum = episodeNum;
  }

  // compute the size of this Radio program
  public int length() {
    return this.minutes;
  }

  // formats the title, corporation and episode number of this Radio program
  // article as a String
  public String format() {
    return this.title + ", " + this.corporation + ", " + "Episode " + Integer.toString(episodeNum);
  }

}

class ExamplesNewsMedia {

  INewsMedia tennisOpen = new Newspaper("Williams Sisters to Meet in Australian Open",
      "Boston Globe", 3, 6);
  INewsMedia worldNews = new Television("World News", "ABC", 30, 2180);
  INewsMedia morningEdition = new Radio("Morning Edition", "NPR", 90, 1250);

  // test the method length for the classes that represent news media
  boolean testLength(Tester t) {
    return t.checkExpect(this.tennisOpen.length(), 18) && t.checkExpect(this.worldNews.length(), 30)
        && t.checkExpect(this.morningEdition.length(), 90);
  }

  // test the method for formatting the information in a news media class
  boolean testFormat(Tester t) {
    return t.checkExpect(this.tennisOpen.format(),
        "Williams Sisters to Meet in Australian Open, Boston Globe")
        && t.checkExpect(this.worldNews.format(), "World News, ABC, Episode 2180")
        && t.checkExpect(this.morningEdition.format(), "Morning Edition, NPR, Episode 1250");
  }

  boolean testsameCorporation(Tester t) {
    return t.checkExpect(this.tennisOpen.sameCorporation(this.tennisOpen), true)
        && t.checkExpect(this.tennisOpen.sameCorporation(this.worldNews), false);
  }
}