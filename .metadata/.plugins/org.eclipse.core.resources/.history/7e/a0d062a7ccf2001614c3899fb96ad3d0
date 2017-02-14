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
}
