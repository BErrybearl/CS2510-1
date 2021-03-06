
// Represents a nonempty list of ImageFiles
public class ConsLoIF implements ILoIF {

    public ImageFile first;
    public ILoIF rest;

    public ConsLoIF(ImageFile first, ILoIF rest) {
        this.first=first;
        this.rest=rest;
    }

    // Does this nonempty list contain that ImageFile   
    public boolean contains(ImageFile that) { 
        return (this.first.sameImageFile(that) ||
                this.rest.contains(that));
    }

    public ILoIF filter(ISelectImageFile pick) {
      if (pick.apply(first)) {
        return new ConsLoIF(this.first, this.rest.filter(pick));
      }
      else {
        return this.rest.filter(pick);
      }
      
    }

    @Override
    public boolean allSmallerThan40000(SmallImageFile small) {
      return small.apply(first) && this.rest.allSmallerThan40000(small);
    }

    @Override
    public boolean allNamesShorterThan4(NameShorterThan4 less4) {
   return less4.apply(first) && this.rest.allNamesShorterThan4(less4);
    }

    @Override
    public boolean allSuchImageFile(ISelectImageFile given) {
      return given.apply(first) && this.rest.allSuchImageFile(given);
    }

    @Override
    public boolean allJPEG(GivenKind gk) {
      return gk.apply(first) && this.rest.allJPEG(gk);
    }

    @Override
    public ILoIF filterImageFile(ISelectImageFile given) {
      // TODO Auto-generated method stub
      return null;
    }


}
