package com.github.jhanne82.documenttree.simulation.utils;

import com.github.jhanne82.documenttree.document.Document;
import com.github.jhanne82.documenttree.simulation.configuration.Configuration;
import com.github.jhanne82.documenttree.simulation.configuration.enumeration.Distribution;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * Utility class with some helper methods
 */
public class Utility {


    /**
     * Method to write the result of a simulation into files.
     *
     * @param path defines the path where the files should be stored.
     * @param simulationResult defines the stored results of the simulation.
     * @throws IOException if the named file exists.
     */
    public static void writeSimulationResults( String path, SimulationResult simulationResult )
            throws IOException {

        path += simulationResult.getParameter().isCluster() ? File.separator + "clustered"
                                                            : File.separator + "not_clustered";

        path = path + File.separator + simulationResult.getParameter().getSearchType()
               + File.separator + simulationResult.getParameter().getDistributionForDocument()
               + File.separator + simulationResult.getParameter().getDistributionForSearch();

        FileUtils.forceMkdir( new File( path ) );

        writeInFile( path + File.separator + "HitMissRate.txt",
                     Integer.toString( simulationResult.getHits() ) );
        writeInFile( path + File.separator + "RequiredSearches.txt",
                     Arrays.toString( simulationResult.getRequiredSearches().toArray() ) );
        writeInFile( path + File.separator + "RequiredRepositionings.txt",
                     Arrays.toString( simulationResult.getRequiredRepositionings().toArray() ) );
        writeInFile( path + File.separator + "DocumentsOnCorrectLevelInTree.txt",
                     String.valueOf( simulationResult.getDocumentOnCorrectLevel() ) );
        writeInFile( path + File.separator + "DistanceToCorrectLevel.txt",
                     Arrays.toString( simulationResult.getDistanceToOptimalPosition().toArray() ));
    }



    private static void writeInFile( String path, String content )
            throws IOException {
        PrintWriter writer = new PrintWriter( new BufferedWriter( new FileWriter( path, true ) ) );
        writer.println( content );
        writer.close();
    }


    /**
     * Method to create a list of documents. The documents will be created like defined in the configuration parameter.
     *
     * @param parameter defines the Configuration used for the simulation which defines the distribution and the number of terms.
     * @param clusterMap defines the term cluster.
     * @return an array of documents.
     */
    public static Document<Double>[] createDocuments( Configuration parameter, Map<Double, Double> clusterMap ) {
        RandomNumberGenerator numberGenerator = new RandomNumberGenerator();
        Document<Double>[] documentArray = new Document[parameter.getMaxCountOfCreatedDocuments()];

        for( int i = 0; i < documentArray.length; i++ ) {
            documentArray[i] = new Document<>( createTermVector( parameter.getDistributionForDocument(),
                                                                 parameter.getMaxCountOfTermsUsedToDefineVector(),
                                                                 parameter.getMaxCountOfTermsWithQuantifier(),
                                                                 parameter.isCluster(),
                                                                 clusterMap,
                                                                 numberGenerator ),
                                               "Document " + i );
        }
        return documentArray;
    }



    /**
     * Method to create an array with double values which can represent the term vector of a document and/or searches.
     *
     * @param distribution defines the distribution of the terms.
     * @param maxCountOfTerms defines the maximum count of terms which will defined the vector.
     * @param maxCountOfTermsWithQuantifier defines the maximum count of terms with a value greater than zero.
     * @param cluster defines if cluster will be used.
     * @param clusterMap defines the cluster map
     * @param numberGenerator defines the Pseudo-Number generator
     * @return an array of double values
     */
    public static Double[] createTermVector( Distribution distribution,
                                              int maxCountOfTerms,
                                              int maxCountOfTermsWithQuantifier,
                                              boolean cluster,
                                              Map<Double, Double> clusterMap,
                                              RandomNumberGenerator numberGenerator ) {

        Double[] termList = new Double[maxCountOfTerms];

        for( int termsWithQuantifier = 0; termsWithQuantifier < maxCountOfTermsWithQuantifier; ) {
            int index = numberGenerator.getInt( maxCountOfTerms );

            if( termList[index] == null || termList[index] == 0 ) {
                double term = numberGenerator.getDouble(distribution, 10 );
                if( cluster && clusterMap.containsKey( term ) ) {
                    term = clusterMap.get( term );
                }
                termList[index] = term;
                termsWithQuantifier++;
            }
        }

        return termList;
    }



    /**
     * Method to create a cluster map. It contains
     * @return a cluster map.
     */
    public static Map<Double, Double> createClusterMap() {
        RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();
        Map<Double, Double> clusterMap = new HashMap<>();

        for( int i = 0; i< 3; i++ ) {
            double bunch = randomNumberGenerator.getDouble( Distribution.EQUALLY, 10 );

            for (int j = 0; j < 3; j++ ) {
                double singleItem = randomNumberGenerator.getDouble( Distribution.EQUALLY, 10 );
                while( singleItem == bunch ) {
                    singleItem = randomNumberGenerator.getDouble( Distribution.EQUALLY, 10 );
                }
                clusterMap.put( singleItem, bunch );
            }
        }
        return clusterMap;
    }



    /**
     * Helper method to define if the found document is the searched one to increase the number of hits of the SimulationResults.
     *
     * @param foundDocument defines the document with the highest relevance which was found.
     * @param bestMatch defines the best match/ searched document to compare with the found document.
     * @param result defines the SimulationResult where the hit should be added.
     */
    public static void addSearchHit(Document foundDocument, Document bestMatch, SimulationResult result ) {

        if(   Arrays.equals(foundDocument.getTermVector(), bestMatch.getTermVector() )
           || bestMatch.getLatestCalculatedRelevance() == foundDocument.getLatestCalculatedRelevance()      ) {
            result.setHits( result.getHits() + 1 );
        }
    }

}
