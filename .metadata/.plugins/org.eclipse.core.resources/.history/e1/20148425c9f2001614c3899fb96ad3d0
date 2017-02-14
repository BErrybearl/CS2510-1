
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