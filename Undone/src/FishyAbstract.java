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

  public AFish(Posn center, int radius, Color col) {
    this.center = center;
    this.radius = radius;
    this.col = col;
  }

  // the fish image on canvas
  public WorldImage fishImage() {
    return new CircleImage(this.radius, "solid", this.col);
  }

  // to determine whether this fish can eat the given fish
  public abstract boolean canEat(AFish that);

  // to calculate the distance between two fish
  public int distance(AFish that) {
    int xDifference = this.center.x - that.center.x;
    int yDifference = this.center.y - that.center.y;

    return (int) Math.sqrt(xDifference * xDifference + yDifference * yDifference);
  }
}

// to represent the player fish
class Fish extends AFish {

  Fish(Posn center, int radius, Color col) {
    super(center, radius, col);
  }

  // move the player fish right/left/up/down based on the key pressed by player
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

  // determine whether this fish can eat the given fish
  public boolean canEat(AFish given) {
    return (this.radius >= given.radius) && (this.distance(given) <= (this.radius + given.radius));

  }

  // grow the player fish when it eats other fish
  public Fish updateFish(ILoFish other) {
    return new Fish(this.center, this.radius + other.getEatenRadius(this), this.col);
  }
}

// to represent the background fish
class OtherFish extends AFish {

  // to determine the direction the background fish swim on the canvas
  boolean goingLeft;

  OtherFish(Posn center, int radius, Color col, boolean goingLeft) {
    super(center, radius, col);
    this.goingLeft = goingLeft;
  }

  // to let the background fish keep swimming from one side to the other
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

  // to get the radius of this fish
  int getRadius() {
    return this.radius;
  }

  // to determine whether this fish can eat the given fish
  public boolean canEat(AFish given) {
    return (this.radius > given.radius) && (this.distance(given) <= (this.radius + given.radius));
  }
}

// to represent a list of fish (background fish)
interface ILoFish {

  // let all the background fish swim cross the canvas
  ILoFish moveAllOtherfish();

  // to determine whether this fish can eat the given fish
  boolean canEat(AFish given);

  // to determine if there's a fish in the list can eat the given fish
  public boolean anyCanEat(AFish given);

  // to get the radius of the eaten fish
  public int getEatenRadius(AFish that);

  // compare the size of player fish with all other fish
  boolean compareSize(AFish given);

  // to get the image of each fish in the list
  WorldScene renderFish(WorldScene accu);

  // to update the list of background fish
  ILoFish updateList(AFish player);
}

// to represent an empty list of background fish
class MtLoFish implements ILoFish {

  public ILoFish moveAllOtherfish() {
    return this;
  }

  public boolean canEat(AFish given) {
    return false;
  }

  public boolean anyCanEat(AFish given) {
    return false;
  }

  public int getEatenRadius(AFish that) {
    return 0;
  }

  public boolean compareSize(AFish given) {
    return true;
  }

  public WorldScene renderFish(WorldScene accu) {
    return accu;
  }

  public ILoFish updateList(AFish player) {
    return this;
  }
}

// to represent a list of background fish
class ConsLoFish implements ILoFish {
  OtherFish first;
  ILoFish rest;

  ConsLoFish(OtherFish first, ILoFish rest) {
    this.first = first;
    this.rest = rest;
  }

  public ILoFish moveAllOtherfish() {
    return new ConsLoFish(this.first.moveOtherfish(), this.rest.moveAllOtherfish());
  }

  public boolean canEat(AFish given) {
    if (given.canEat(this.first)) {
      return true;
    }
    return this.rest.canEat(given);
  }

  public int getEatenRadius(AFish that) {
    if (that.canEat(this.first)) {
      return this.first.getRadius() + this.rest.getEatenRadius(that);
    }
    else {
      return this.rest.getEatenRadius(that);
    }
  }

  public boolean anyCanEat(AFish given) {
    return first.canEat(given) || this.rest.anyCanEat(given);
  }

  public boolean compareSize(AFish given) {
    return (this.first.getRadius() < given.radius) && this.rest.compareSize(given);
  }

  public WorldScene renderFish(WorldScene accu) {
    return this.rest.renderFish(
        accu.placeImageXY(this.first.fishImage(), this.first.center.x, this.first.center.y));
  }

  public ILoFish updateList(AFish player) {
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

  public FishyWorld(Fish player, ILoFish otherfish) {
    this.player = player;
    this.otherfish = otherfish;
  }

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
  
  // Comment out following lines to run this game
  //
  // FishyWorld fw = new FishyWorld(p1, makeFishlist(numberofFish));
  // boolean runAnimation = this.fw.bigBang(FishyWorld.WIDTH, FishyWorld.HEIGHT,
  // 0.1);
  
  

  // -----------------------------------TESTS

  // Because the speed of otherfish is designed to be random
  // it's hard to test all method related to movement or speed.

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

//  boolean testrenderFish(Tester t) {
//    return t.checkExpect(p1.canEat(f1), true) 
//        && t.checkExpect(p1.canEat(f2), true)
//        && t.checkExpect(p1.canEat(f3), false);
//  }

  boolean testonKeyEvent(Tester t) {
    return t.checkExpect(w1.onKeyEvent("left"), w1l);
//        && t.checkExpect(w1.onKeyEvent("right"), w1r)
//        && t.checkExpect(w1.onKeyEvent("up"), w1u)
//        && t.checkExpect(w1.onKeyEvent("down"), w1d);

  }
/*
  boolean testOnTick(Tester t) {
    return t.checkExpect(p1.canEat(f1), true) 
        && t.checkExpect(p1.canEat(f2), true)
        && t.checkExpect(p1.canEat(f3), false);
  }

  boolean testworldEnds(Tester t) {
    return t.checkExpect(p1.worldEnds(f1), true) 
        && t.checkExpect(p1.canEat(f2), true)
        && t.checkExpect(p1.canEat(f3), false);
  }

  boolean testlastSence(Tester t) {
    return t.checkExpect(p1.canEat(f1), true)
        && t.checkExpect(p1.canEat(f2), true)
        && t.checkExpect(p1.canEat(f3), false);
  }

*/
}
