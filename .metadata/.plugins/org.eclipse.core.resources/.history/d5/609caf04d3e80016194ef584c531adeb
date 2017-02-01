import tester.*;                // The tester library
import javalib.worldimages.*;   // images, like RectangleImage or OverlayImages
import javalib.funworld.*;      // the abstract World class and the big-bang library
import java.awt.Color;   
 
class ExamplesProbability {
  
  
  boolean testProbs(Tester t) {
    // In this experiment:
    // Rolling a 2 results in "Even", rolling a 5 results in "Odd"
    IProbabilityExperiment pe1 =
        new DiceRollIsEven(new Result("Even"), new Result("Odd"));
    IProbabilityExperiment pe2 =new StandardDiceRoll(new Result("Cat"), new Result("Dog"),
        new Result("Hamster"), new Result("Bird"),
        new Result("Dog"), new Result("Cat"));
 
    return
         // After rolling a 4, the expected result is "Even"
         pe1.testEval(t, new ConsLoDice(4, new MtLoDice()), "Even")
         // The expected probability of getting a result of "Odd" is 50%
      && pe1.testProbOfOutcome(t, "Odd", 0.5)
      && pe2.testEval(t, new ConsLoDice(3, new MtLoDice()), "Odd")
         // Make sure you've found all the broken implementations
      && pe1.foundAllErrors(t);
  }
}