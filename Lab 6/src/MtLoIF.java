
// Represents an empty list of ImageFiles
class MtLoIF implements ILoIF {

    MtLoIF() { }

    // Does this empty list contain that ImageFile   
    public boolean contains(ImageFile that) { 
        return false;
    }

    @Override
    public ILoIF filter(ISelectImageFile pick) {
      return this;
    }

    @Override
    public boolean allSmallerThan40000(SmallImageFile small) {
      return true;
    }

    @Override
    public boolean allNamesShorterThan4(NameShorterThan4 less4) {
      return true;
    }

    @Override
    public boolean allSuchImageFile(ISelectImageFile given) {
      return true;
    }

    @Override
    public boolean allJPEG(GivenKind gk) {
      return true;
    }

    @Override
    public ILoIF filterImageFile(ISelectImageFile given) {
      return this;
    }

   
} 
