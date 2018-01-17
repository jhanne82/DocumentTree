package com.github.jhanne82.documenttree.simulation.utils;

import com.github.jhanne82.documenttree.simulation.configuration.Parameter;
import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Utility {



    public static void writeSimulationResults( String path, Parameter parameter, SimulationResult simulationResult )
            throws IOException {

        path += parameter.isCluster() ? "clustered" : "not_clustered";

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
//        File file = new File( path );
        PrintWriter writer = new PrintWriter( new BufferedWriter( new FileWriter( path, true ) ) );
        writer.println( content );
        writer.close();
    }


}
