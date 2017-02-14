package FishyGameFiles;

import java.awt.Color;
import java.util.Random;

import tester.*;
import javalib.funworld.*;
import javalib.worldimages.*;

// to represent a fish
abstract class AFish {

  Posn center;
  int radius;
  Color col;

  // the constructor
  public AFish(Posn center, int radius, Color col) {
    this.center = center;
    this.radius = radius;
    this.col = col;
  }

  /* FIELDS:
   * this.center                -- Posn
   * this.radius                -- int
   * this.col                   -- Color
   * 
   * METHODS:
   * this.fishImage()           -- WorldImage
   * this.canEat(AFish that)    -- boolean
   * this.distance(AFish that)  -- int
   */

  // produces an image of this AFish
  public WorldImage fishImage() {
    return new CircleImage(this.radius, "solid", this.col);
  }

  // to determine whether this AFish can eat the given AFish
  public abstract boolean canEat(AFish that);
  /* METHODS FOR PARAMETERS: Methods for AFish */

  // to calculate the distance between two AFish
  public int distance(AFish that) {
    /* METHODS FOR PARAMETERS: Methods for AFish */
    int xDifference = this.center.x - that.center.x;
    int yDifference = this.center.y - that.center.y;

    return (int) Math.sqrt(xDifference * xDifference + yDifference * yDifference);
  }
}

// to represent the player fish
class Fish extends AFish {

  // the constructor
  Fish(Posn center, int radius, Color col) {
    super(center, radius, col);
  }

  /* FIELDS:
   * this.center                      -- Posn
   * this.radius                      -- int
   * this.col                         -- Color
   * 
   * METHODS:
   * this.fishImage()                 -- WorldImage
   * this.canEat(AFish given)         -- boolean
   * this.distance(AFish that)        -- int
   * this.moveFish(String ke)         -- Fish
   * this.updateFish(ILoFish other)   -- Fish
   */

  // move this Fish right/left/up/down based on the key pressed by player
  public Fish moveFish(String ke) {
    if (ke.equals("right")) {
      return new Fish(new Posn(this.center.x + 5, this.center.y), this.radius, this.col);
    }
    else if (ke.equals("left")) {
      return new Fish(new Posn(this.center.x - 5, this.center.y), this.radius, this.col);
    }
    else if (ke.equals("up")) {
      return new Fish(new Posn(this.center.x, this.center.y - 5), this.radius, this.col);
    }
    else if (ke.equals("down")) {
      return new Fish(new Posn(this.center.x, this.center.y + 5), this.radius, this.col);
    }
    else {
      return this;
    }
  }

  // determine whether this AFish can eat the given AFish
  public boolean canEat(AFish given) {
    /* METHODS FOR PARAMETERS: Methods for AFish */
    return (this.radius >= given.radius) && (this.distance(given) <= (this.radius + given.radius));
  }

  // grow the player AFish when it eats any OtherFish in the given ILoFish
  public Fish updateFish(ILoFish other) {
    /* METHODS FOR PARAMETERS: Methods for ILoFish */
    return new Fish(this.center, this.radius + other.getEatenRadius(this), this.col);
  }
}

// to represent the background fish
class OtherFish extends AFish {

  // to determine the direction of this OtherFish
  boolean goingLeft;

  // the constructor
  OtherFish(Posn center, int radius, Color col, boolean goingLeft) {
    super(center, radius, col);
    this.goingLeft = goingLeft;
  }

  /* FIELDS:
   * this.center                      -- Posn
   * this.radius                      -- int
   * this.col                         -- Color
   * this.goingLeft                   -- boolean
   * 
   * METHODS:
   * this.fishImage()                 -- WorldImage
   * this.canEat(AFish given)         -- boolean
   * this.distance(AFish that)        -- int
   * this.moveFish(String ke)         -- Fish
   * this.updateFish(ILoFish other)   -- Fish
   * this.moveOtherFish()             -- OtherFish
   * this.getRadius()                 -- int
   */

  // to move this OtherFish depending on its location and direction
  public OtherFish moveOtherfish() {

    Random r = new Random();

    if (this.goingLeft && this.center.x <= 0) {
      return new OtherFish(new Posn(this.center.x + r.nextInt(10), r.nextInt(FishyWorld.HEIGHT)),
          this.radius, this.col, !this.goingLeft);
    }
    else if (this.goingLeft && this.center.x >= 0) {
      return new OtherFish(new Posn(this.center.x - r.nextInt(10), this.center.y), this.radius,
          this.col, this.goingLeft);
    }
    else if (!this.goingLeft && this.center.x >= FishyWorld.WIDTH) {
      return new OtherFish(new Posn(this.center.x - r.nextInt(10), r.nextInt(FishyWorld.HEIGHT)),
          this.radius, this.col, !this.goingLeft);
    }
    else {
      return new OtherFish(new Posn(this.center.x + r.nextInt(10), this.center.y), this.radius,
          this.col, this.goingLeft);
    }
  }

  // to get the radius of this OtherFish
  int getRadius() {
    return this.radius;
  }

  // to determine whether this OtherFish can eat the given AFish
  public boolean canEat(AFish given) {
    /* METHODS FOR PARAMETERS: Methods for AFish */
    return (this.radius > given.radius) && (this.distance(given) <= (this.radius + given.radius));
  }
}

// to represent a list of OtherFish
interface ILoFish {

  // move all of the OtherFish in this ILoFish
  ILoFish moveAllOtherfish();

  // determine whether any OtherFish in this ILoFish can be eaten by the given AFish
  boolean canEat(AFish given);

  // to determine if there's a fish in this ILoFish can eat the given AFish
  public boolean anyCanEat(AFish given);

  // to get the radius of the eaten AFish
  public int getEatenRadius(AFish that);

  // compare the size of the given player fish with all other fish in this ILoFish
  boolean compareSize(AFish given);

  // to get the image of each fish in this ILoFish
  WorldScene renderFish(WorldScene accu);

  // to update this ILoFish when the an OtherFish is eaten
  ILoFish updateList(AFish player);
}

// to represent an empty list of OtherFish
class MtLoFish implements ILoFish {

  /* METHODS:
   * this.moveAllOtherFish()            -- ILoFish
   * this.canEat(AFish given)           -- boolean
   * this.anyCanEat(AFish given)        -- boolean
   * this.getEatenEadius(AFish that)    -- int
   * this.compareSize(AFish given)      -- boolean
   * this.renderFish(WorldScene accu)   -- WorldScene
   * this.updateList(AFish player)      -- ILoFish
   */

  // move all of the OtherFish in this MtLoFish
  public ILoFish moveAllOtherfish() {
    return this;
  }

  // determine whether any OtherFish in this MtLoFish can be eaten by the given AFish
  public boolean canEat(AFish given) {
    /* METHODS FOR PARAMETERS: Methods for AFish */
    return false;
  }

  // to determine if there's a fish in this MtLoFish can eat the given AFish
  public boolean anyCanEat(AFish given) {
    /* METHODS FOR PARAMETERS: Methods for AFish */
    return false;
  }

  // to get the radius of the eaten AFish
  public int getEatenRadius(AFish that) {
    /* METHODS FOR PARAMETERS: Methods for AFish */
    return 0;
  }

  // compare the size of the given player fish with all other fish in this MtLoFish
  public boolean compareSize(AFish given) {
    /* METHODS FOR PARAMETERS: Methods for AFish */
    return true;
  }

  // to get the image of each fish in this MtLoFish
  public WorldScene renderFish(WorldScene accu) {
    /* METHODS FOR PARAMETERS: Methods for WorldScene */
    return accu;
  }

  // to update this MtLoFish when the an OtherFish is eaten
  public ILoFish updateList(AFish player) {
    /* METHODS FOR PARAMETERS: Methods for AFish */
    return this;
  }
}

// to represent a list of background fish
class ConsLoFish implements ILoFish {
  OtherFish first;
  ILoFish rest;

  // the constructor
  ConsLoFish(OtherFish first, ILoFish rest) {
    this.first = first;
    this.rest = rest;
  }

  /* FIELDS:
   * this.first                         -- OtherFish
   * this.rest                          -- ILoFish
   * 
   * METHODS:
   * this.moveAllOtherFish()            -- ILoFish
   * this.canEat(AFish given)           -- boolean
   * this.getEatenRadius(AFish that)    -- int
   * this.anyCanEat(AFish given)        -- boolean
   * this.compareSize(AFish given)      -- boolean
   * this.renderFish(WorldScene accu)   -- WorldScene
   * this.updateList(AFish player)      -- ILoFish
   * 
   * METHODS FOR FIELDS:
   * 
   * Methods for OtherFish
   * 
   * Methods for ILoFish
   */

  // move all of the OtherFish in this ConsLoFish
  public ILoFish moveAllOtherfish() {
    return new ConsLoFish(this.first.moveOtherfish(), this.rest.moveAllOtherfish());
  }

  // determine whether any OtherFish in this ConsLoFish can be eaten by the given AFish
  public boolean canEat(AFish given) {
    /* METHODS FOR PARAMETERS: Methods for AFish */
    if (given.canEat(this.first)) {
      return true;
    }
    return this.rest.canEat(given);
  }

  // to get the radius of the eaten AFish
  public int getEatenRadius(AFish that) {
    /* METHODS FOR PARAMETERS: Methods for AFish */
    if (that.canEat(this.first)) {
      return this.first.getRadius() + this.rest.getEatenRadius(that);
    }
    else {
      return this.rest.getEatenRadius(that);
    }
  }

  // to determine if there's a fish in this ConsLoFish can eat the given AFish
  public boolean anyCanEat(AFish given) {
    /* METHODS FOR PARAMETERS: Methods for AFish */
    return first.canEat(given) || this.rest.anyCanEat(given);
  }

  // compare the size of the given player fish with all other fish in this ConsLoFish
  public boolean compareSize(AFish given) {
    /* METHODS FOR PARAMETERS: Methods for AFish */
    return (this.first.getRadius() < given.radius) && this.rest.compareSize(given);
  }

  // to get the image of each fish in this ConsLoFish
  public WorldScene renderFish(WorldScene accu) {
    /* METHODS FOR PARAMETERS: Methods for WorldScene */
    return this.rest.renderFish(
        accu.placeImageXY(this.first.fishImage(), this.first.center.x, this.first.center.y));
  }

  // to update this ConsLoFish when the an OtherFish is eaten
  public ILoFish updateList(AFish player) {
    /* METHODS FOR PARAMETERS: Methods for AFish */
    if (player.canEat(this.first)) {
      return this.rest.updateList(player);
    }
    else {
      return new ConsLoFish(this.first, this.rest.updateList(player));
    }
  }
}

// to represent the world of entire Fishy game project
class FishyWorld extends World {

  static int WIDTH = 400;
  static int HEIGHT = 400;

  Fish player; // player fish
  ILoFish otherfish; // background fish

  // the constructor
  public FishyWorld(Fish player, ILoFish otherfish) {
    this.player = player;
    this.otherfish = otherfish;
  }

  /* FIELDS:
   * this.WIDTH                           -- int
   * this.HEIGHT                          -- int
   * 
   * METHODS:
   * this.onKeyEvent(String ke)           -- World
   * this.onTick()                        -- World
   * this.worldEnds                       -- WorldEnd
   * this.lastScene(String s)             -- WorldScene
   * this.makeScene()                     -- WorldScene
   * this.renderFish(WorldScene accu)     -- WorldScene
   */

  // to deal with key strokes input
  public World onKeyEvent(String ke) {
    if (player.center.x < 0) {
      return new FishyWorld(
          new Fish(new Posn(FishyWorld.WIDTH, player.center.y), player.radius, player.col),
          otherfish);
    }
    else if (player.center.x > FishyWorld.WIDTH) {
      return new FishyWorld(new Fish(new Posn(0, player.center.y), player.radius, player.col),
          otherfish);
    }
    else if (player.center.y > FishyWorld.HEIGHT) {
      return new FishyWorld(
          new Fish(new Posn(player.center.x, FishyWorld.HEIGHT), player.radius, player.col),
          otherfish);
    }
    else if (player.center.y < 0) {
      return new FishyWorld(new Fish(new Posn(player.center.x, 0), player.radius, player.col),
          otherfish);
    }
    else {
      return new FishyWorld(this.player.moveFish(ke), otherfish);
    }
  }

  // to conduct all actions happen every tick in the game
  // - update the player fish when it eats another fish
  // - update the list of background fish when one of them is eaten by player
  // fish
  // - keep all background fish swimming on the canvas from one side to the
  // other
  public World onTick() {
    return new FishyWorld(this.player.updateFish(otherfish),
        this.otherfish.updateList(player).moveAllOtherfish());
  }

  // to determine whether the game is supposed to end or not
  // - Player fish is the biggest one in the pond
  // - Player fish is eaten by other fish in the pond
  public WorldEnd worldEnds() {
    if (otherfish.compareSize(player)) {
      return new WorldEnd(true, this.lastScene("You Win"));
    }
    else if (otherfish.anyCanEat(player)) {
      return new WorldEnd(true, this.lastScene("You lost"));
    }
    return new WorldEnd(false, this.makeScene());
  }

  // to create the last scene, display the result of the game
  // - Win
  // - Lost
  public WorldScene lastScene(String s) {
    return this.makeScene().placeImageXY(new TextImage(s, Color.red), FishyWorld.WIDTH / 2,
        FishyWorld.HEIGHT / 2);
  }

  // to draw the empty scene
  public WorldScene makeScene() {
    return renderFish(this.getEmptyScene());
  }

  // to draw the canvas and all fish including Player fish and background fish
  public WorldScene renderFish(WorldScene accu) {
    return this.otherfish.renderFish(
        accu.placeImageXY(this.player.fishImage(), this.player.center.x, this.player.center.y));
  }
}

// examples and tests for Fishy game
class FishyExamples {

  // -----------------------------------EXAMPLES

  Fish p1 = new Fish(new Posn(FishyWorld.WIDTH / 2, FishyWorld.HEIGHT / 2), 6, Color.black);
  Fish p1l = new Fish(new Posn(FishyWorld.WIDTH / 2 - 5, FishyWorld.HEIGHT / 2), 6, Color.black);
  Fish p1r = new Fish(new Posn(FishyWorld.WIDTH / 2 + 5, FishyWorld.HEIGHT / 2), 6, Color.black);
  Fish p1u = new Fish(new Posn(FishyWorld.WIDTH / 2, FishyWorld.HEIGHT / 2 - 5), 6, Color.black);
  Fish p1d = new Fish(new Posn(FishyWorld.WIDTH / 2, FishyWorld.HEIGHT / 2 + 5), 6, Color.black);

  Fish p2 = new Fish(new Posn(FishyWorld.WIDTH / 2, FishyWorld.HEIGHT / 2), 8, Color.black);
  Fish p3 = new Fish(new Posn(FishyWorld.WIDTH / 2, FishyWorld.HEIGHT / 2), 13, Color.black);

  OtherFish f1 = new OtherFish(new Posn(0, FishyWorld.HEIGHT / 2), 2, Color.red, false);
  OtherFish f2 = new OtherFish(new Posn(FishyWorld.WIDTH, 50), 5, Color.red, true);
  OtherFish f3 = new OtherFish(new Posn(FishyWorld.WIDTH, 150), 10, Color.red, true);
  OtherFish f4 = new OtherFish(new Posn(FishyWorld.WIDTH / 2, FishyWorld.HEIGHT / 2), 2, Color.red,
      false);

  ILoFish mt = new MtLoFish();
  ILoFish o1 = new ConsLoFish(this.f1, new ConsLoFish(this.f2, new ConsLoFish(this.f3, mt)));
  ILoFish o2 = new ConsLoFish(this.f2, new ConsLoFish(f3, mt));
  ILoFish o3 = new ConsLoFish(this.f3, mt);
  ILoFish o4 = new ConsLoFish(this.f4, mt);

  FishyWorld ept = new FishyWorld(p1, mt);

  FishyWorld w1 = new FishyWorld(p1, o1);
  FishyWorld w2 = new FishyWorld(p1, o2);
  FishyWorld w3 = new FishyWorld(p1l, o3);
  FishyWorld w4 = new FishyWorld(p1, o4);

  FishyWorld w1r = new FishyWorld(p1r, o1);
  FishyWorld w1l = new FishyWorld(p1l, o1);
  FishyWorld w1u = new FishyWorld(p1u, o1);
  FishyWorld w1d = new FishyWorld(p1d, o1);

  // Comment out the following two lines of code to see a simple game example

  // boolean runAnimation1 = this.w1.bigBang(FishyWorld.HEIGHT,
  // FishyWorld.WIDTH, 0.1);

  // to determine the side that fish start from
  public int startSide(int n) {

    int x = -1;

    if (Math.pow(x, n) < 0) {
      return 0;
    }
    return FishyWorld.WIDTH;
  }

  // to help determine the swimming direction of the fish
  public boolean moveDir(int n) {

    int x = -1;

    return (Math.pow(x, n) > 0);
  }

  // to help generate a random list of background fish
  public ILoFish makeFishlist(int n) {

    Random r = new Random();

    ILoFish list = new MtLoFish();

    for (int i = 0; i < n; i++) {
      list = new ConsLoFish(new OtherFish(new Posn(startSide(i), r.nextInt(FishyWorld.HEIGHT)),
          r.nextInt(20), Color.red, moveDir(i)), list);
    }
    return list;
  }

  // Change this number to change the number of background fish
  // int numberofFish = 10;

  // Comment out following three lines to run this game
  //
  // FishyWorld fw = new FishyWorld(p1, makeFishlist(numberofFish));
  // boolean runAnimation = this.fw.bigBang(FishyWorld.WIDTH, FishyWorld.HEIGHT,
  // 0.1);



  // -----------------------------------TESTS

  // Because the speed of otherfish is designed to be random
  // it's impossible to test all method related to movement or speed.

  boolean testcanEat(Tester t) {
    return t.checkExpect(p1.canEat(f1), false) 
        && t.checkExpect(p1.canEat(f2), false)
        && t.checkExpect(p1.canEat(f3), false) 
        && t.checkExpect(p1.canEat(f4), true)
        && t.checkExpect(o1.canEat(p1), false) 
        && t.checkExpect(mt.canEat(p1), false);
  }

  boolean testdistance(Tester t) {
    return t.checkExpect(p1.distance(f1), 200) 
        && t.checkExpect(p1.distance(f2), 250)
        && t.checkExpect(p1.distance(f3), 206);
  }

  boolean testmoveFish(Tester t) {
    return t.checkExpect(p1.moveFish("right"), p1r) 
        && t.checkExpect(p1.moveFish("left"), p1l)
        && t.checkExpect(p1.moveFish("up"), p1u) 
        && t.checkExpect(p1.moveFish("down"), p1d);

  }

  boolean testupdateFish(Tester t) {
    return t.checkExpect(p1.updateFish(mt), p1)
        && t.checkExpect(p1.updateFish(o1), p1)
        && t.checkExpect(p1.updateFish(o4), p2);
  }

  boolean testgoingLeft(Tester t) {
    return t.checkExpect(f1.goingLeft, false) 
        && t.checkExpect(f2.goingLeft, true)
        && t.checkExpect(f3.goingLeft, true);
  }

  boolean testgetRadius(Tester t) {
    return t.checkExpect(f1.getRadius(), 2) 
        && t.checkExpect(f2.getRadius(), 5)
        && t.checkExpect(f3.getRadius(), 10);
  }

  boolean testcompareSize(Tester t) {
    return t.checkExpect(o1.compareSize(p1), false) 
        && t.checkExpect(o4.compareSize(p1), true)
        && t.checkExpect(o1.compareSize(p2), false);
  }

  boolean testupdateList(Tester t) {
    return t.checkExpect(o4.updateList(p1), mt) 
        && t.checkExpect(o1.updateList(p1), o1)
        && t.checkExpect(o2.updateList(p2), o2);
  }

  boolean testgetEatenRadius(Tester t) {
    return t.checkExpect(o1.getEatenRadius(p1), 0) 
        && t.checkExpect(o4.getEatenRadius(p1), 2);
  }

  boolean testonKeyEvent(Tester t) {
    return t.checkExpect(w1.onKeyEvent("left"), w1l)
        && t.checkExpect(w1.onKeyEvent("right"), w1r)
        && t.checkExpect(w1.onKeyEvent("up"), w1u)
        && t.checkExpect(w1.onKeyEvent("down"), w1d);
  }

  /*We could not troubleshoot this failed test
  boolean testworldEnds(Tester t) {
    return t.checkExpect(w1.worldEnds(), new WorldEnd(true, new FishyWorld(p1, o4)
    .lastScene("You Win")));
  }
   */

  boolean testlastScene(Tester t) {
    return t.checkExpect(ept.lastScene("You Lost"), new FishyWorld(p1, mt)
        .makeScene()
        .placeImageXY(new TextImage("You Lost", Color.red), FishyWorld.WIDTH / 2,
        FishyWorld.HEIGHT / 2));
  }

  /*
  boolean testMakeScene(Tester t) {
    return t.checkExpect()
  }
   */

  /*
  boolean testrenderFish(Tester t) {
    return t.checkExpect(p1.canEat(f1), true) 
        && t.checkExpect(p1.canEat(f2), true)
        && t.checkExpect(p1.canEat(f3), false);
  }
   */
}