package com.github.jhanne82.documenttree.tree;


import com.github.jhanne82.documenttree.document.Document;

import java.util.LinkedList;
import java.util.List;



public class DocumentNode<T> {

   private List<DocumentNode<T>> leaves = new LinkedList<>();
   private DocumentNode<T>       parent = null;
   private DocumentNode<T>       left = null;
   private DocumentNode<T>       right = null;
   private Document<T> document;


   public DocumentNode( Document<T> document ) {
      this.document = document;
   }



   private void setParent( DocumentNode<T> parent ) {
      this.parent = parent;
   }



   public DocumentNode<T> getParent() {
      return parent;
   }



   public void setLeftChild( DocumentNode<T> left ) {
      this.left = left;
      addChildLeaf( left );
   }



   public DocumentNode<T> getLeftChild() {
       return this.left;
   }


   public void setRightChild( DocumentNode<T> right ) {
      this.right = right;
      addChildLeaf( right );
   }



   public DocumentNode<T> getRightChild() {
       return this.right;
   }


   public List<DocumentNode<T>> getChildLeaves() {
      return leaves;
   }



   private void addChildLeaf( DocumentNode<T> childLeaf ) {
      if( childLeaf != null ) {
         childLeaf.setParent( this );
      }
      this.leaves.add( childLeaf );
   }


   public Document<T> getDocument() {
      return this.document;
   }



   public void setDocument( Document<T> document ) {
      this.document = document;
   }



}
