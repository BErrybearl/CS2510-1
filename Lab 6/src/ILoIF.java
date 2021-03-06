/*
 *         +----------------------------------+
 *         |               ILoIF              |
 *         +----------------------------------+
 *         | boolean contains(ImageFile that) |
 *         +--------------+-------------------+
 *                       / \
 *                      +---+
 *                        |
 *          +-------------+-----------+
 *          |                         |
 *  +---------------+        +-------------------+
 *  |     MTLoIF    |        |     ConsLoIF      |
 *  +---------------+        +-------------------+
 *  +---------------+     +--| ImageFile  first  |
 *                        |  | ILoIF      rest   |
 *                        |  +-------------------+
 *                        v
 *               +---------------+
 *               | ImageFile     |
 *               +---------------+
 *               | String name   |
 *               | int    width  |
 *               | int    height |
 *               | String kind   |
 *               +---------------+
 */

// Represents a list of ImageFiles
public interface ILoIF {

  // Does this list contain that ImageFile
  public boolean contains(ImageFile that);
  
  public ILoIF filter(ISelectImageFile pick);
  
  public boolean allSmallerThan40000(SmallImageFile small);
  
  public boolean allNamesShorterThan4(NameShorterThan4 less4);
  
  public boolean allSuchImageFile(ISelectImageFile given);
  
  public boolean allJPEG(GivenKind gk);
  
  public ILoIF filterImageFile(ISelectImageFile given);
}
