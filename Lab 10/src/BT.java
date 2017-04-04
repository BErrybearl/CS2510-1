class BST {
  
}


interface INumBinTree {
  
  boolean isLeaf();
  
  NumNode asNode();
  
  boolean validBST();
}

class NumNode implements INumBinTree {

  int value;
  INumBinTree left;
  INumBinTree right;
  
  public boolean isLeaf() {
    return false;
  }

  public NumNode asNode() {
    return this;
  }

  @Override
  public boolean validBST() {
    // TODO Auto-generated method stub
    return false;
  }
  
}

class NumLeaf implements INumBinTree {

  public boolean isLeaf() {
    return true;
  }

  public NumNode asNode() {
    throw new IllegalArgumentException("This is a Leaf");
  }

  @Override
  public boolean validBST() {
    // TODO Auto-generated method stub
    return false;
  }
  
}


