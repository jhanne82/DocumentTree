package com.github.jhanne82.documenttree.simulation.utils;

import com.github.jhanne82.documenttree.document.Document;
import com.github.jhanne82.documenttree.simulation.configuration.Configuration;
import com.github.jhanne82.documenttree.simulation.configuration.enumeration.Distribution;
import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Utility {



    public static void writeSimulationResults( String path, Configuration parameter, SimulationResult simulationResult )
            throws IOException {

        path += parameter.isCluster() ? File.separator + "clustered"
                                      : File.separator + "not_clustered";

        path = path + File.separator + parameter.getSearchType()
               + File.separator + parameter.getDistributionForDocument()
               + File.separator + parameter.getDistributionForSearch();

        FileUtils.forceMkdir( new File( path ) );

        writeInFile( path + File.separator + "HitMissRate.txt",
                     simulationResult.getHitRate() + "/" + simulationResult.getMissRate() );
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


    public static void calcHitMissRate( Document foundDocument, Document bestMatch, SimulationResult result ) {

        if(   bestMatch.getDocumentName().equals( foundDocument.getDocumentName() )
                || bestMatch.getLatestCalculatedRelevance() == foundDocument.getLatestCalculatedRelevance()      ) {
            result.setHitRate( result.getHitRate() + 1 );
        } else {
            result.setMissRate( result.getMissRate() + 1 );
        }
    }

}
