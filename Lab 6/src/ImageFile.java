
// Represents an image file
class ImageFile {
    String name;
    int width;
    int height;
    String kind;

    ImageFile(String name, int width, int height, String kind) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.kind = kind;
    }
  
  /* Template:
     Fields:
     ... this.name ...     -- String
     ... this.width ...    -- int
     ... this.height ...   -- int
     ... this.kind ...     -- String
     
     Methods:
     ... this.size() ...                    -- int
     ... this.sameImageFile(ImageFile) ...  -- boolean
   */
   
    // Calculate the size of this image
    public int size(){
        return this.width * this.height;
    }

    // Is this image file the same as the given one?
    public boolean sameImageFile(ImageFile that) {
        return (this.name.equals(that.name) &&
                this.width == that.width &&
                this.height == that.height &&
                this.kind.equals(that.kind));
    }
}

//to represent a predicate for ImageFile-s
interface ISelectImageFile {

// Return true if the given ImageFile
// should be selected
 boolean apply(ImageFile f);

}

/* Select image files smaller than 40000 */
class SmallImageFile implements ISelectImageFile {
 
  /* Return true if the given ImageFile is smaller than 40000 */
  public boolean apply(ImageFile f) {
    return f.height * f.width < 40000;
  }
  
}

class NameShorterThan4 implements ISelectImageFile {
  
  public boolean apply(ImageFile f) {
    return f.name.length() < 4;
  }
  
}

class GivenKind implements ISelectImageFile {
  
  String desiredkind;
  
  GivenKind(String kind) {
    this.desiredkind = kind;
    
  }
  
  public boolean apply(ImageFile f) {
    return f.kind.equals(desiredkind);
  }
}
