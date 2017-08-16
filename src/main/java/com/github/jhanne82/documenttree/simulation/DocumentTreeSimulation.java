package com.github.jhanne82.documenttree.simulation;

import com.github.jhanne82.documenttree.document.Document;
import com.github.jhanne82.documenttree.document.DocumentTree;

import java.util.List;

public abstract class DocumentTreeSimulation <T> {


    // Bewertungskriterien
    private int averageSearchTime = 0;
    private int missRate = 0;
    private int hitRate = 0;
    private int averageCountOfRepositioning = 0;
    private int conformityOfOptimalDocumentTree = 0;
    private int conformityOfStressReducedDocumentTree = 0;
    private int distanceToOptimalPosition = 0;



    private DocumentTree<T> documentTreeWithGlobalKnowledge;
    private DocumentTree<T> documentTreeWithLocalKnowledge;
    private DocumentTree<T> stressReducedDocumentTree;
    private Document<T>[]   optimalDocumentTree;



    public DocumentTree<T> getDocumentTreeWithGlobalKnowledge() {
        return documentTreeWithGlobalKnowledge;
    }



    public void setDocumentTreeWithGlobalKnowledge( DocumentTree<T> documentTreeWithGlobalKnowledge ) {
        this.documentTreeWithGlobalKnowledge = documentTreeWithGlobalKnowledge;
    }



    public DocumentTree<T> getDocumentTreeWithLocalKnowledge() {
        return documentTreeWithLocalKnowledge;
    }



    public void setDocumentTreeWithLocalKnowledge( DocumentTree<T> documentTreeWithLocalKnowledge ) {
        this.documentTreeWithLocalKnowledge = documentTreeWithLocalKnowledge;
    }



    public DocumentTree<T> getStressReducedDocumentTree() {
        return stressReducedDocumentTree;
    }



    public void setStressReducedDocumentTree( DocumentTree<T> stressReducedDocumentTree ) {
        this.stressReducedDocumentTree = stressReducedDocumentTree;
    }



    public Document<T>[] getOptimalDocumentTree() {
        return optimalDocumentTree;
    }



    public void setOptimalDocumentTree( Document<T>[] optimalDocumentTree ) {
        this.optimalDocumentTree = optimalDocumentTree;
    }



    public void runSimulation( List<T[]> searchTermVectorList,
                               SearchType searchType,
                               int limitForLocalKnowledge,
                               int countOfSearchesForRepositioning ) {

        int i = 0;
        for( T[] searchTerm : searchTermVectorList ) {
            search( searchTerm, searchType, limitForLocalKnowledge );
            System.out.println("Repositierung START...");
            documentTreeWithLocalKnowledge.repositioning( countOfSearchesForRepositioning );
            documentTreeWithGlobalKnowledge.repositioning( countOfSearchesForRepositioning );
            System.out.println("Repositierung END...");
        }
    }

    private void search( T[] searchTermVector, SearchType searchType, int limitForLocalKnowledge ) {

        System.out.println( "search START...");
        new GlobalTreeSearch(searchType, searchTermVector).start();
        switch ( searchType ) {
            case DEPTH_FIRST:
                //documentTreeWithGlobalKnowledge.depthFirstSearch( 0, searchTermVector );
                documentTreeWithLocalKnowledge.depthFirstSearch( limitForLocalKnowledge, searchTermVector );
                stressReducedDocumentTree.depthFirstSearch( limitForLocalKnowledge, searchTermVector );
                break;
            case BREADTH_FIRST:
                //documentTreeWithGlobalKnowledge.breadthFirstSearch( 0, searchTermVector );
                documentTreeWithLocalKnowledge.breadthFirstSearch( limitForLocalKnowledge, searchTermVector );
                stressReducedDocumentTree.breadthFirstSearch( limitForLocalKnowledge, searchTermVector );
                break;
            case RANDOM_WALKER:
            default:
                throw new UnsupportedOperationException();
        }

        searchOnOptimalDocumentTree( searchTermVector );

        System.out.println( "search END... ");
    }


    protected abstract void searchOnOptimalDocumentTree( T[] searchTermVector );


    public class GlobalTreeSearch extends Thread {

        private SearchType searchType;
        private T[] searchTermVector;

        public GlobalTreeSearch( SearchType searchType, T[] searchTermVector ) {
            this.searchType = searchType;
            this.searchTermVector = searchTermVector;
        }

        @Override
        public void run() {

            switch ( searchType ) {
                case DEPTH_FIRST:
                    documentTreeWithGlobalKnowledge.depthFirstSearch( 0, searchTermVector );
                    break;
                case BREADTH_FIRST:
                    documentTreeWithGlobalKnowledge.breadthFirstSearch( 0, searchTermVector );
                    break;
                case RANDOM_WALKER:
                default:
                    throw new UnsupportedOperationException();
            }
            super.run();
        }
    }


}
