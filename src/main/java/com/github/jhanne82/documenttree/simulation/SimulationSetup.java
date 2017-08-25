package com.github.jhanne82.documenttree.simulation;

public class SimulationSetup {


    public final SearchType searchType;
    public final Distribution distributionForSearchVector;
    public final Distribution distributionForDocumentVector;
    public final boolean cluster;
    public final int countOfTermsUsedToDefineVector;
    public final int countOfTermsWithQuantifier;
    public final int countOfCreatedDocuments;
    public final int countOfPerformedSearches;
    public final int limitForLocalKnowledge;
    public final int requiredSearchesOnDocumentToRespositioning;



    public SimulationSetup( SearchType searchType,
                            Distribution distributionForDocumentVector,
                            Distribution distributionForSearchVector,
                            int countOfTermsUsedToDefineVector,
                            int countOfTermsWithQuantifier,
                            int countOfCreatedDocuments,
                            int countOfPerformedSearches,
                            int limitForLocalKnowledge,
                            int requiredSearchesOnDocumentToRespositioning,
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
        this.cluster = cluster;
    }
}
