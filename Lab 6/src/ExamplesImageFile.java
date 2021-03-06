import tester.*;  

// Test the use of function objects with lists of ImageFile-s
public class ExamplesImageFile {

    public ExamplesImageFile() { }

    // Sample data to be used for all tests
    public ImageFile img1 = new ImageFile("dog", 300, 200, "jpg");
    public ImageFile img2 = new ImageFile("cat", 200, 200, "png");
    public ImageFile img3 = new ImageFile("bird", 250, 200, "jpg");
    public ImageFile img4 = new ImageFile("horse", 300, 300, "gif");
    public ImageFile img5 = new ImageFile("goat", 100, 200, "gif");
    public ImageFile img6 = new ImageFile("cow", 150, 200, "jpg");
    public ImageFile img7 = new ImageFile("snake", 200, 300, "jpg");

    //empty list
    public ILoIF mt= new MtLoIF();

    // ImageFile list -- all Images
    public ILoIF imglistall = 
        new ConsLoIF(this.img1, 
            new ConsLoIF(this.img2,
                new ConsLoIF(this.img3, 
                    new ConsLoIF(this.img4, 
                        new ConsLoIF(this.img5, 
                            new ConsLoIF(this.img6, 
                                new ConsLoIF(this.img7, this.mt))))))); 

    // ImageFile list - short names (less than 4 characters)
    public ILoIF imglistshortnames = 
        new ConsLoIF(this.img1, 
            new ConsLoIF(this.img2, 
                new ConsLoIF(this.img6, this.mt))); 

    // ImageFile list - small size (< 40000)
    public ILoIF imglistsmall = 
        new ConsLoIF(this.img5, 
            new ConsLoIF(this.img6, this.mt));

    // ImageFile list - small size (< 40000)
    public ILoIF imglistsmall2 = 
        new ConsLoIF(this.img5, 
            new ConsLoIF(this.img6, this.mt));

    // ImageFile list -- large images
    public ILoIF imglistlarge = 
        new ConsLoIF(this.img1, 
            new ConsLoIF(this.img2,
                new ConsLoIF(this.img3, this.mt))); 
    
    public ILoIF imgjpg = new ConsLoIF(img1, new ConsLoIF(img3, new ConsLoIF(img6, new ConsLoIF(img7, mt))));

    // A sample test method
    public boolean testSize(Tester t){
        return (t.checkExpect(this.img1.size(), 60000) &&
                t.checkExpect(this.img2.size(), 40000));
    }

    // A sample test method
    public boolean testContains(Tester t){
        return (t.checkExpect(this.imglistsmall.contains(this.img3), false) &&
                t.checkExpect(this.imglistsmall.contains(this.img6), true));
    }
    
    public boolean testSmallImageFile(Tester t) {
      return t.checkExpect(this.mt.filter(new SmallImageFile()), mt)
          && t.checkExpect(this.imglistall.filter(new SmallImageFile()), imglistsmall);
    }
    
    public boolean testallSmallThan40000(Tester t) {
      return t.checkExpect(this.mt.allSmallerThan40000(new SmallImageFile()), true)
          && t.checkExpect(this.imglistall.allSmallerThan40000(new SmallImageFile()), false)
          && t.checkExpect(this.imglistsmall.allSmallerThan40000(new SmallImageFile()), true);
    }
    
    public boolean testNameShorterThan4(Tester t) {
      return t.checkExpect(this.mt.filter(new NameShorterThan4()), mt)
          && t.checkExpect(this.imglistall.filter(new NameShorterThan4()), imglistshortnames)
          && t.checkExpect(this.imglistshortnames.filter(new NameShorterThan4()), imglistshortnames);
    }
    
    public boolean testallNamesShorterThan4(Tester t) {
      return t.checkExpect(this.mt.allNamesShorterThan4(new NameShorterThan4()), true)
          && t.checkExpect(this.imglistall.allNamesShorterThan4(new NameShorterThan4()), false)
          && t.checkExpect(this.imglistshortnames.allNamesShorterThan4(new NameShorterThan4()), true);
    }
    
    public boolean testallSuchImageFile(Tester t) {
      return t.checkExpect(this.mt.allSuchImageFile(new SmallImageFile()), true)
          && t.checkExpect(this.mt.allSuchImageFile(new NameShorterThan4()), true)
          && t.checkExpect(this.imglistall.allSuchImageFile(new SmallImageFile()), false)
          && t.checkExpect(this.imglistall.allSuchImageFile(new NameShorterThan4()), false)
          && t.checkExpect(this.imglistall.allSuchImageFile(new SmallImageFile()), false)
          && t.checkExpect(this.imglistshortnames.allSuchImageFile(new NameShorterThan4()), true);
    }
    
    public boolean testGivenKind(Tester t) {
      return t.checkExpect(this.mt.allJPEG(new GivenKind("jpg")), true)
          && t.checkExpect(this.imglistall.allJPEG(new GivenKind("jpg")), false)
          && t.checkExpect(this.imgjpg.allJPEG(new GivenKind("jpg")), true)
          && t.checkExpect(this.imglistall.allJPEG(new GivenKind("gif")), false);
    }
    
    
}