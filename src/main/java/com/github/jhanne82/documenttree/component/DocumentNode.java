package com.github.jhanne82.documenttree.component;


import java.util.LinkedList;
import java.util.List;



public class DocumentNode<T> {

   private List<DocumentNode<T>> leaves = new LinkedList<>();
   private DocumentNode<T>       parent = null;
   private DocumentNode<T>       left = null;
   private DocumentNode<T>       right = null;
   private Document<T>           document;


   public DocumentNode( Document<T> document ) {
      this.document = document;
   }



   public DocumentNode( Document<T> document, DocumentNode<T> left, DocumentNode<T> right ) {
      this.document = document;
      addLeftChildLeaf( left );
      addRightChildLeaf( right);
   }


   public DocumentNode(){}


   public void setParent( DocumentNode<T> parent ) {
      this.parent = parent;
   }


   public List<DocumentNode<T>> getChildLeaves() {
      return leaves;
   }



   public DocumentNode<T> getLeftChild() {
       return left;
   }


   public DocumentNode<T> getRightChild() {
       return right;
   }



   public void addLeftChildLeaf( DocumentNode<T> childLeaf ) {
      childLeaf.setParent( this );
      this.leaves.add( childLeaf );
      this.left = childLeaf;
   }



    public void addRightChildLeaf( DocumentNode<T> childLeaf ) {
        childLeaf.setParent( this );
        this.leaves.add( childLeaf );
        this.right = childLeaf;
    }


   public Document<T> getDocument() {
      return this.document;
   }


   public boolean isRootNode() {
      return this.parent == null;
   }


   public boolean isLeaf() {
      return this.leaves.isEmpty();
   }
}
