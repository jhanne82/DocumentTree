package com.github.jhanne82.documenttree.component;


import java.util.LinkedList;
import java.util.List;



public class DocumentNode<T> {

   private List<DocumentNode<T>> leaves = new LinkedList<>();
   private DocumentNode<T>       parent = null;
   public DocumentNode<T>       left = null;
   public DocumentNode<T>       right = null;
   private Document<T>           document;


   public DocumentNode( Document<T> document ) {
      this.document = document;
   }



   public DocumentNode( Document<T> document, DocumentNode<T> left, DocumentNode<T> right ) {
      this.document = document;
      this.left = left;
      addChildLeaf( left );
      this.right = right;
      addChildLeaf( right);
   }


   public DocumentNode(){}


   public void setParent( DocumentNode<T> parent ) {
      this.parent = parent;
   }


   public List<DocumentNode<T>> getChildLeaves() {
      return leaves;
   }


   public void addChildLeaf( Document<T> document ) {

      DocumentNode<T> childLeaf = new DocumentNode<>( document );
      addChildLeaf( childLeaf );
   }


   public void addChildLeaf( DocumentNode<T> childLeaf ) {
      childLeaf.setParent( this );
      this.leaves.add( childLeaf );
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
