package com.github.jhanne82.documenttree;


import com.github.jhanne82.documenttree.document.Document;



/**
 *  Generic representation of a DocumentNode.
 *  The DocumentNode can be used as node element within a binary tree and keeps
 *  references to the parent, and both child elements.
 *
 * @param <T> the type of the elements of terms describing the document.
 */
public class DocumentNode<T> {



    private DocumentNode<T>       parent   = null;
    private DocumentNode<T>       left     = null;
    private DocumentNode<T>       right    = null;
    private Document<T>           document;



    /**
     * Constructs a DocumentNode object with a Document.
     *
     * @param document defines the {@link com.github.jhanne82.documenttree.document.Document} which should be stored in this node.
     */
   public DocumentNode( Document<T> document ) {
      this.document = document;
   }



    /**
     * Get the parent node of the current node.
     * If the parent is null, the current node is a root node.
     * 
     * @return the DocumentNode which is references as parent node.
     */
   public DocumentNode<T> getParent() {
      return parent;
   }



    /**
     * Set the DocumentNode which should be references as left child.
     *
     * @param left defines the left child DocumentNode
     */
   public void setLeftChild( DocumentNode<T> left ) {
      this.left = left;
      if( left != null ) {
          this.parent = this;
      }
   }



    /**
     * Get the DocumentNode which is referenced as left child node.
     *
     * @return the left child
     */
   public DocumentNode<T> getLeftChild() {
       return this.left;
   }



    /**
     * Set the DocumentNode which should be references as rigtht child.
     *
     * @param right defines the left child DocumentNode
     */
   public void setRightChild( DocumentNode<T> right ) {
      this.right = right;
      if( right != null ) {
          right.parent = this;
      }
   }



    /**
     * Get the DocumentNode which is referenced as right child node.
     *
     * @return the right child
     */
   public DocumentNode<T> getRightChild() {
       return this.right;
   }



    /**
     * Get the stored Document from this node.
     *
     * @return the document
     */
   public Document<T> getDocument() {
      return this.document;
   }



    /**
     * Sets the {@link com.github.jhanne82.documenttree.document.Document} to this node.
     *
     * @param document defines the document to be stored on this node.
     */
   public void setDocument( Document<T> document ) {
      this.document = document;
   }

}
