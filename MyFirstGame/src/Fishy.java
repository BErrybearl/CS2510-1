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
  
  WorldImage blobImage() {
    return new CircleImage(this.radius, "solid", this.col);
}
  
  /**
   * move this blob 5 pixels in the direction given by the ke or change its
   * color to Green, Red or Yellow
   */
  public Fish moveFish(String ke) {
      if (ke.equals("right")) {
          return new Fish(new Posn(this.center.x + 5, this.center.y),
                  this.radius, this.col);
      } else if (ke.equals("left")) {
          return new Fish(new Posn(this.center.x - 5, this.center.y),
                  this.radius, this.col);
      } else if (ke.equals("up")) {
          return new Fish(new Posn(this.center.x, this.center.y - 5),
                  this.radius, this.col);
      } else if (ke.equals("down")) {
          return new Fish(new Posn(this.center.x, this.center.y + 5),
                  this.radius, this.col);
      }
      // change the color to Y, G, R
      else if (ke.equals("Y")) {
          return new Fish(this.center, this.radius, Color.YELLOW);
      } else if (ke.equals("G")) {
          return new Fish(this.center, this.radius, Color.GREEN);
      } else if (ke.equals("R")) {
          return new Fish(this.center, this.radius, Color.RED);

      } else
          return this;
      
      // Make fish sink down to the middle of the canvas
      Fish Startpoint() {
            if ()
  }
}
  
  

