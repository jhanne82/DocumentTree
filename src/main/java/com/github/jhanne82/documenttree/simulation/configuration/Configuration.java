package com.github.jhanne82.documenttree.simulation.configuration;

import com.github.jhanne82.documenttree.simulation.configuration.enumeration.Distribution;
import com.github.jhanne82.documenttree.simulation.configuration.enumeration.SearchType;



/**
 * Implementation to store all parameters used for the simulation.
 * Irreversible parameters which will be the same in each simulation are:
 * <li>the count of created documents which will be filled into the tree</li>
 * <li>the count of searches which will be performed in a simulation run</li>
 * <li>the count of terms which will describe a document and search</li>
 * <li>count of terms of the document and search vector which are greater than 0</li>
 * <li>limitation for the tree which should not be searched completely</li>
 * <li>number of searches which needs to be performed on a document to change the position within the tree</li>
 * <li>a threshold value to switch documents which did not match {@link #NUMBER_OF_SEARCHES_BEFORE_REPOSITIONING}</li>
 */
public class Configuration {



    private static final int MAX_COUNT_OF_CREATED_DOCUMENTS             = 1000;
    private static final int MAX_COUNT_OF_CREATED_SEARCHES              = 1000000;
    private static final int MAX_COUNT_OF_TERMS_USED_TO_DEFINE_VECTOR   = 1000;
    private static final int MAX_COUNT_OF_TERMS_WITH_QUANTIFIER         = 3;
    private static final int LIMIT_FOR_LOCAL_KNOWLEDGE                  = 300;
    private static final int NUMBER_OF_SEARCHES_BEFORE_REPOSITIONING    = 20;
    private static final int THRESHOLD                                  = 100;



    private final SearchType   searchType;
    private final Distribution distributionForDocument;
    private final Distribution distributionForSearch;
    private final boolean      cluster;



    /**
     * Creates a object of type {@link Configuration}.
     *
     * @param searchType defines the {@link com.github.jhanne82.documenttree.simulation.configuration.enumeration.SearchType}
     * @param distributionForDocument defines the {@link com.github.jhanne82.documenttree.simulation.configuration.enumeration.Distribution} for the document terms
     * @param distributionForSearch defines the {@link com.github.jhanne82.documenttree.simulation.configuration.enumeration.Distribution} for the search terms
     * @param cluster defines of term cluster will be used
     */
    public Configuration( SearchType   searchType,
                          Distribution distributionForDocument,
                          Distribution distributionForSearch,
                          boolean      cluster  ) {

        this.searchType = searchType;
        this.distributionForDocument = distributionForDocument;
        this.distributionForSearch = distributionForSearch;
        this.cluster = cluster;
    }



    public int getMaxCountOfCreatedDocuments() {
        return MAX_COUNT_OF_CREATED_DOCUMENTS;
    }



    public int getMaxCountOfCreatedSearches() {
        return MAX_COUNT_OF_CREATED_SEARCHES;
    }



    public int getMaxCountOfTermsUsedToDefineVector() {
        return MAX_COUNT_OF_TERMS_USED_TO_DEFINE_VECTOR;
    }



    public int getMaxCountOfTermsWithQuantifier() {
        return MAX_COUNT_OF_TERMS_WITH_QUANTIFIER;
    }



    public int getLimitForLocalKnowledge() {
        return LIMIT_FOR_LOCAL_KNOWLEDGE;
    }



    public int getNumberOfSearchesBeforeRepositioning() {
        return NUMBER_OF_SEARCHES_BEFORE_REPOSITIONING;
    }



    public int getTHRESHOLD() {
        return THRESHOLD;
    }



    public SearchType getSearchType() {
        return searchType;
    }



    public Distribution getDistributionForDocument() {
        return distributionForDocument;
    }



    public Distribution getDistributionForSearch() {
        return distributionForSearch;
    }



    public boolean isCluster() {
        return cluster;
    }



    @Override
    public String toString() {

        return String.format("Search Type: %20s%n", searchType) +
               String.format("Search Distr.: %14s%n", distributionForSearch) +
               String.format("Document Distr.: %12s%n", distributionForDocument) +
               String.format("Cluster: %18s%n", cluster);
    }
}
