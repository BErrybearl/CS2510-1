import java.awt.Color;
import java.util.Random;

import tester.*;
import javalib.funworld.*;
import javalib.worldimages.*;

class Fish {

  Posn center;
  int radius;
  Color col;

  /** The constructor */
  Fish(Posn center, int radius, Color col) {
    this.center = center;
    this.radius = radius;
    this.col = col;
  }

  WorldImage fishImage() {
    return new CircleImage(this.radius, "solid", this.col);
  }

  /**
   * move this blob 5 pixels in the direction given by the ke or change its
   * color to Green, Red or Yellow
   */
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
    // change the color to Y, G, R
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

  public boolean canEat(OtherFish given) {
    if ((this.radius > given.radius) && (this.distance(given) <= this.radius + given.radius)) {
      return true;
    }
    else {
      return false;
    }
  } 
  


  public int distance(OtherFish other) {
    int xDifference = this.center.x - other.center.x;
    int yDifference = this.center.y - other.center.y;

    return (int) Math.sqrt(xDifference * xDifference + yDifference * yDifference);
  }
  
  public Fish updateFish(ILoFish other) {
    if (other.canEat(this)) {
      return new Fish(this.center, this.radius + other.getRadius(), this.col);
    }
    return this;
  }
  


}

class OtherFish {

  Posn center;
  int radius;
  Color col;
  boolean goingLeft;

  OtherFish(Posn center, int radius, Color col, boolean goingLeft) {
    this.center = center;
    this.radius = radius;
    this.col = col;
    this.goingLeft = goingLeft;
  }

  WorldImage fishImage() {
    return new CircleImage(this.radius, "solid", this.col);

  }



  public OtherFish crossScene(int n) {
    if (this.goingLeft && this.center.x <= 0) {
      return new OtherFish(new Posn(this.center.x + n, this.center.y), this.radius, this.col, !this.goingLeft);
    }
    else if (this.goingLeft && this.center.x >= 0) {
      return new OtherFish(new Posn(this.center.x - n, this.center.y), this.radius, this.col, this.goingLeft);
    }
    else if (!this.goingLeft && this.center.x >= 200) {
      return new OtherFish(new Posn(this.center.x - n, this.center.y), this.radius, this.col, !this.goingLeft);
    }
    else {
      return new OtherFish(new Posn(this.center.x + n, this.center.y), this.radius, this.col, this.goingLeft);
    }
  }

  int getRadius() {
    return this.radius;
  }

  /* public boolean canEat(OtherFish given) {
    if ((this.radius > given.radius) && (this.distance(given) <= this.radius + given.radius)) {
      return true;
    }
    else {
      return false;
    }
  } */


  public int distance(Fish given) {
    int xDifference = this.center.x - given.center.x;
    int yDifference = this.center.y - given.center.y;

    return (int) Math.sqrt(xDifference * xDifference + yDifference * yDifference);
  }

  public boolean canEat(Fish given) {
    if ((this.radius > given.radius) && (this.distance(given) <= this.radius + given.radius)) {
      return true;
    }
    else {
      return false;
    }
  }
}

interface ILoFish {
  ILoFish crossScene(int n);

  int getRadius();

  boolean compareSize(Fish given);

  WorldScene renderFish(WorldScene accu);
 

  boolean canEat(Fish given);
  
  ILoFish updateList(Fish player);
  
  public boolean canEatAny(Fish given);

}

class MtLoFish implements ILoFish {

  public ILoFish crossScene(int n) {
    return this;
  }

  public boolean compareSize(Fish given) {
    return true;
  }

  public WorldScene renderFish(WorldScene accu) {
    return accu;
  }


 @Override
  public boolean canEat(Fish given) {
    return false;
  }

  @Override
  public ILoFish updateList(Fish player) {
return this;}

  @Override
  public int getRadius() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public boolean canEatAny(Fish given) {
    return true;
  }

}

class ConsLoFish implements ILoFish {
  OtherFish first;
  ILoFish rest;

  ConsLoFish(OtherFish first, ILoFish rest) {
    this.first = first;
    this.rest = rest;
  }

  public ILoFish crossScene(int n) {
    return new ConsLoFish(this.first.crossScene(n), this.rest.crossScene(n));
  }

  @Override
  public boolean compareSize(Fish given) {
    return (this.first.getRadius() < given.radius) && this.rest.compareSize(given);
  }

  @Override
  public WorldScene renderFish(WorldScene accu) {
    return this.rest.renderFish(
        accu.placeImageXY(this.first.fishImage(), this.first.center.x, this.first.center.y));
  }


  public boolean canEat(Fish given) {
    if (given.canEat(this.first)) {
      return true;
    }
    return this.rest.canEat(given);
  }
 //
  public ILoFish updateList(Fish player) {
    if (player.canEat(this.first)) {
      return this.rest.updateList(player);
    }
    else {
      return new ConsLoFish(this.first, this.rest.updateList(player));
    }
  }

  public int getRadius() {
    
  return this.first.radius;
  }

  public boolean canEatAny(Fish given) {
    
   return given.canEat(first) || rest.canEatAny(given);
  }
  
  

}

class FishyWorld extends World {

  int width = 200;
  int height = 200;
  Fish player;
  ILoFish otherfish;

  public FishyWorld(Fish player, ILoFish otherfish) {
    this.player = player;
    this.otherfish = otherfish;
  }

  public World onKeyEvent(String ke) {
    if (player.center.x < 0) {
      return new FishyWorld(new Fish(new Posn(200, player.center.y), player.radius, player.col),
          otherfish);
    }
    else if (player.center.x > 200) {
      return new FishyWorld(new Fish(new Posn(0, player.center.y), player.radius, player.col),
          otherfish);
    }
    else if (player.center.y > 200) {
      return new FishyWorld(new Fish(new Posn(player.center.x, 200), player.radius, player.col),
          otherfish);
    }
    else if (player.center.y < 0) {
      return new FishyWorld(new Fish(new Posn(player.center.x, 0), player.radius, player.col),
          otherfish);
    }

    else
      return new FishyWorld(this.player.moveFish(ke), otherfish);
  }

  public World onTick() { 

    return new FishyWorld(this.player.updateFish(otherfish), this.otherfish.updateList(player).crossScene(5));
  }
  
  


  public WorldEnd worldEnds() {
    if (otherfish.compareSize(player)) {
      return new WorldEnd(true, this.lastScene("You Win"));
    }
    else if (otherfish.canEatAny(player) && !(otherfish.compareSize(player))) {
      return new WorldEnd(true, this.lastScene("You Lost"));
    }
    return new WorldEnd(false, this.makeScene());
  }

  /** produce the last image of this world by adding text to the image */
  public WorldScene lastScene(String s) {
    return this.makeScene().placeImageXY(new TextImage(s, Color.red), 100, 40);
  }

  @Override
  public WorldScene makeScene() {
    return renderFish(this.getEmptyScene());
  }

  public WorldScene renderFish(WorldScene accu) {
    return this.otherfish.renderFish(
        accu.placeImageXY(this.player.fishImage(), this.player.center.x, this.player.center.y));
  }



}

class FishyExamples {

  Fish p1 = new Fish(new Posn(100, 100), 6, Color.black);

  OtherFish f1 = new OtherFish(new Posn(0, 100), 2, Color.red, false);
  OtherFish f2 = new OtherFish(new Posn(200, 50), 5, Color.red, true);
  OtherFish f3 = new OtherFish(new Posn(200, 150), 15, Color.red, true);

  ILoFish o1 = new ConsLoFish(this.f1,
      new ConsLoFish(this.f2, new ConsLoFish(this.f3, new MtLoFish())));

  FishyWorld w1 = new FishyWorld(p1, o1);

  boolean runAnimation1 = this.w1.bigBang(200, 200, 0.5);
}
