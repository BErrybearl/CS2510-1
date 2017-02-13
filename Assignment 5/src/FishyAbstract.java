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
    else if (ke.equals("Y")) {
      return new Fish(this.center, this.radius, Color.YELLOW);
    }
    else if (ke.equals("G")) {
      return new Fish(this.center, this.radius, Color.GREEN);
    }
    else if (ke.equals("R")) {
      return new Fish(this.center, this.radius, Color.RED);
    }
    else
      return this;
  }

  // determine whether this fish can eat the given fish
  public boolean canEat(AFish given) {
    if ((this.radius >= given.radius) && (this.distance(given) <= this.radius + given.radius)) {
      return true;
    }
    else {
      return false;
    }
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
    if ((this.radius > given.radius) && (this.distance(given) <= this.radius + given.radius)) {
      return true;
    }
    else {
      return false;
    }
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
    else
      return this.rest.getEatenRadius(that);
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
    else
      return new FishyWorld(this.player.moveFish(ke), otherfish);
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

  // to draw the canvas and all fish including Player fish and background fish
  public WorldScene makeScene() {
    return renderFish(this.getEmptyScene());
  }

  /// to draw the image of all fish
  public WorldScene renderFish(WorldScene accu) {
    return this.otherfish.renderFish(
        accu.placeImageXY(this.player.fishImage(), this.player.center.x, this.player.center.y));
  }
}

// examples and tests for Fishy game
class FishyExamples {

  Fish p1 = new Fish(new Posn(FishyWorld.WIDTH / 2, FishyWorld.HEIGHT / 2), 6, Color.black);
  Fish p2 = new Fish(new Posn(FishyWorld.WIDTH / 2, FishyWorld.HEIGHT / 2), 4, Color.black);

  OtherFish f1 = new OtherFish(new Posn(0, FishyWorld.HEIGHT / 2), 2, Color.red, false);
  OtherFish f2 = new OtherFish(new Posn(FishyWorld.WIDTH, 50), 5, Color.red, true);
  OtherFish f3 = new OtherFish(new Posn(FishyWorld.WIDTH, 150), 10, Color.red, true);

  ILoFish mt = new MtLoFish();
  ILoFish o1 = new ConsLoFish(this.f1,
      new ConsLoFish(this.f2, new ConsLoFish(this.f3, mt)));
  ILoFish o2 = new ConsLoFish(this.f2, new ConsLoFish(f3, mt));
  ILoFish o3 = new ConsLoFish(this.f3, mt);

  FishyWorld w1 = new FishyWorld(p1, o1);
  FishyWorld w2 = new FishyWorld(p1, o2);
  FishyWorld w3 = new FishyWorld(p2, o3);

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

    if (Math.pow(x, n) < 0) {
      return false;
    }
    return true;

  }

  // 
  public ILoFish makeFishlist(int n) {

    Random r = new Random();

    ILoFish list = new MtLoFish();

    for (int i = 0; i < n; i++) {
      list = new ConsLoFish(new OtherFish(new Posn(startSide(i), r.nextInt(FishyWorld.HEIGHT)),
          r.nextInt(20), Color.red, moveDir(i)), list);
    }

    return list;
  }

  // Change this number to change the number of background of fish
  int numberofFish = 10;

  FishyWorld fw = new FishyWorld(p1, makeFishlist(numberofFish));
  boolean runAnimation = this.fw.bigBang(FishyWorld.WIDTH, FishyWorld.HEIGHT, 0.1);
  
  
  boolean testcanEat(Tester t) {
    return t.checkExpect(p1.canEat(f1), false)
        && t.checkExpect(p1.canEat(f2), false)
        && t.checkExpect(p1.canEat(f3), false)
        && t.checkExpect(f3.canEat(p1), false)
        && t.checkExpect(o1.canEat(p1), false)
        && t.checkExpect(mt.canEat(p1), false);
  }
 /* 
  boolean testdistance(Tester t) {
    return t.checkExpect(p1.distance(f1), 200)
        && t.checkExpect(p1.distance(f2), 250)
        && t.checkExpect(p1.distance(f3), 206);
  }
  boolean testmoveFish(Tester t) {
    return t.checkExpect(p1.canEat(f1), true)
        && t.checkExpect(p1.canEat(f2), true)
        && t.checkExpect(p1.canEat(f3), false);
  }
  boolean testupdateFish(Tester t) {
    return t.checkExpect(p1.canEat(f1), true)
        && t.checkExpect(p1.canEat(f2), true)
        && t.checkExpect(p1.canEat(f3), false);
  }
  boolean testgoingLeft(Tester t) {
    return t.checkExpect(p1.canEat(f1), true)
        && t.checkExpect(p1.canEat(f2), true)
        && t.checkExpect(p1.canEat(f3), false);
  }
  boolean testmoveOtherfish(Tester t) {
    return t.checkExpect(p1.canEat(f1), true)
        && t.checkExpect(p1.canEat(f2), true)
        && t.checkExpect(p1.canEat(f3), false);
  }
  boolean testgetRadius(Tester t) {
    return t.checkExpect(p1.canEat(f1), true)
        && t.checkExpect(p1.canEat(f2), true)
        && t.checkExpect(p1.canEat(f3), false);
  }
  boolean testmoveAllOtherfish(Tester t) {
    return t.checkExpect(p1.canEat(f1), true)
        && t.checkExpect(p1.canEat(f2), true)
        && t.checkExpect(p1.canEat(f3), false);
  }
  boolean testcompareSize(Tester t) {
    return t.checkExpect(p1.canEat(f1), true)
        && t.checkExpect(p1.canEat(f2), true)
        && t.checkExpect(p1.canEat(f3), false);
  }
  boolean testupdateList(Tester t) {
    return t.checkExpect(p1.canEat(f1), true)
        && t.checkExpect(p1.canEat(f2), true)
        && t.checkExpect(p1.canEat(f3), false);
  }
  boolean testgetEatenRadius(Tester t) {
    return t.checkExpect(p1.canEat(f1), true)
        && t.checkExpect(p1.canEat(f2), true)
        && t.checkExpect(p1.canEat(f3), false);
  }
  boolean testrenderFish(Tester t) {
    return t.checkExpect(p1.canEat(f1), true)
        && t.checkExpect(p1.canEat(f2), true)
        && t.checkExpect(p1.canEat(f3), false);
  }
  boolean testonKeyEvent(Tester t) {
    return t.checkExpect(p1.canEat(f1), true)
        && t.checkExpect(p1.canEat(f2), true)
        && t.checkExpect(p1.canEat(f3), false);
  }
  boolean testOnTick(Tester t) {
    return t.checkExpect(p1.canEat(f1), true)
        && t.checkExpect(p1.canEat(f2), true)
        && t.checkExpect(p1.canEat(f3), false);
  }
  boolean testworldEnds(Tester t) {
    return t.checkExpect(p1.canEat(f1), true)
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
