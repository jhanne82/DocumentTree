package com.github.jhanne82.documenttree.simulation.utils;

import com.github.jhanne82.documenttree.simulation.enumeration.Distribution;
import com.github.jhanne82.documenttree.simulation.enumeration.SearchType;

public class SimulationSetup {


    public final SearchType   searchType;
    public final Distribution distributionForSearchVector;
    public final Distribution distributionForDocumentVector;
    public final boolean      cluster;
    public final int          countOfTermsUsedToDefineVector;
    public final int          countOfTermsWithQuantifier;
    public final int          countOfCreatedDocuments;
    public final int          countOfPerformedSearches;
    public final int          limitForLocalKnowledge;
    public final int          requiredSearchesOnDocumentToRespositioning;
    public final int          treshold;



    public SimulationSetup( SearchType searchType,
                            Distribution distributionForDocumentVector,
                            Distribution distributionForSearchVector,
                            int countOfTermsUsedToDefineVector,
                            int countOfTermsWithQuantifier,
                            int countOfCreatedDocuments,
                            int countOfPerformedSearches,
                            int limitForLocalKnowledge,
                            int requiredSearchesOnDocumentToRespositioning,
                            int treshold,
                            boolean cluster ) {

        this.searchType = searchType;
        this.distributionForDocumentVector = distributionForDocumentVector;
        this.distributionForSearchVector   = distributionForSearchVector;
        this.countOfTermsUsedToDefineVector = countOfTermsUsedToDefineVector;
        this.countOfTermsWithQuantifier = countOfTermsWithQuantifier;
        this.countOfCreatedDocuments = countOfCreatedDocuments;
        this.countOfPerformedSearches = countOfPerformedSearches;
        this.limitForLocalKnowledge = limitForLocalKnowledge;
        this.requiredSearchesOnDocumentToRespositioning = requiredSearchesOnDocumentToRespositioning;
        this.treshold = treshold;
        this.cluster = cluster;
    }



    @Override
    public String toString() {

        StringBuffer buffer = new StringBuffer();

        buffer.append( String.format( "Search Type: %20s%n", searchType ) );  
        buffer.append( String.format( "Search Distr.: %14s%n", distributionForSearchVector ) );
        buffer.append( String.format( "Document Distr.: %12s%n", distributionForDocumentVector ) );
        buffer.append( String.format( "Cluster: %18s%n", cluster ) );

        return buffer.toString();
    }
}
