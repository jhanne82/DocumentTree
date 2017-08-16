package com.github.jhanne82.documenttree.simulation.numberdocument;

import com.github.jhanne82.documenttree.document.Document;
import com.github.jhanne82.documenttree.document.DocumentTree;
import com.github.jhanne82.documenttree.document.Term;
import com.github.jhanne82.documenttree.document.numberdocument.NumberDocumentTree;
import com.github.jhanne82.documenttree.simulation.Distribution;
import com.github.jhanne82.documenttree.simulation.DocumentTreeSimulation;
import com.github.jhanne82.documenttree.utils.RandomNumberGenerator;

import java.util.ArrayList;
import java.util.List;

public class NumberSimulationUtils {


    public static DocumentTreeSimulation setupSimulation( Distribution distributionOfDocumentTerms,
                                                          int maxCountOfTerms,
                                                          int maxCountOfTermsWithQuantifier,
                                                          int maxCountOfDocuments,
                                                          boolean cluster) {


        System.out.println( "SimulationSetup START....");

        DocumentTreeSimulation<Double> simulation = new NumberDocumentTreeSimulation();

        DocumentTree<Double> documentTreeWithGlobalKnowledge = new NumberDocumentTree();
        DocumentTree<Double> documentTreeWithLocalKnowledge = new NumberDocumentTree();
        DocumentTree<Double> stressReducedDocumentTree = new NumberDocumentTree();

        Document<Double>[] optimalDocumentTree = createDocuments( distributionOfDocumentTerms,
                                                                     maxCountOfTerms,
                                                                     maxCountOfTermsWithQuantifier,
                                                                     maxCountOfDocuments );

        documentTreeWithGlobalKnowledge.level_order_insert( null, optimalDocumentTree, 0, maxCountOfDocuments );
        //documentTreeWithLocalKnowledge.level_order_insert( null, optimalDocumentTree, 0, maxCountOfDocuments );
        stressReducedDocumentTree.level_order_insert( null, optimalDocumentTree, 0, maxCountOfDocuments );

        simulation.setDocumentTreeWithGlobalKnowledge( documentTreeWithGlobalKnowledge );
       // simulation.setDocumentTreeWithLocalKnowledge( documentTreeWithLocalKnowledge );
        simulation.setStressReducedDocumentTree( stressReducedDocumentTree );
        simulation.setOptimalDocumentTree( optimalDocumentTree );


        System.out.println( "SimulationSetup END....");


        return simulation;

    }



    public static List<List<Term<Double>>> createSearchTermVectorList(Distribution distributionOfSearchTerms,
                                                                      int maxCountOfTerms,
                                                                      int maxCountOfTermsWithQuantifier,
                                                                      int maxCountOfSearches ) {

        List<List<Term<Double>>> searchTermVectorList = new ArrayList<>( maxCountOfSearches );
        RandomNumberGenerator randomNumberForIndex = new RandomNumberGenerator( distributionOfSearchTerms,
                                                                                maxCountOfTerms );
        RandomNumberGenerator randomNumberForTerm  = new RandomNumberGenerator( distributionOfSearchTerms,
                                                                                10 );

        for( int i = 0; i < maxCountOfSearches; i++ ) {

            List<Term<Double>> searchTermList = new ArrayList<>(maxCountOfTermsWithQuantifier );

            for( int termsWithQuantifier = 0; termsWithQuantifier < maxCountOfTermsWithQuantifier; ) {
                int index = randomNumberForIndex.getInt();

                Term<Double> term = searchTermList.stream().filter( t -> t.getIndex() == index ).findFirst().orElse( null );
                if( term == null ) {
                    searchTermList.add( new Term<Double>( index, randomNumberForTerm.getDouble() ) );
                    termsWithQuantifier++;
                }
            }
            searchTermVectorList.add( searchTermList );
        }
        return searchTermVectorList;
    }


    private static Document<Double>[] createDocuments( Distribution distributionOfDocuemntTerms,
                                                       int maxCountOfTerms,
                                                       int maxCountOfTermsWithQuantifier,
                                                       int maxCountOfDocuments ) {


        System.out.println( "CreateDocuments START....");

        Document<Double>[] documentArray = new Document[maxCountOfDocuments];

        int i = 0;
        for( List<Term<Double>> termList: createSearchTermVectorList( distributionOfDocuemntTerms,
                                                           maxCountOfTerms,
                                                           maxCountOfTermsWithQuantifier,
                                                           maxCountOfDocuments ) ) {

            documentArray[i++] = new Document<>( termList, "Document " + i );
        }
        System.out.println( "CreateDocuments END....");
        return documentArray;
    }


}
