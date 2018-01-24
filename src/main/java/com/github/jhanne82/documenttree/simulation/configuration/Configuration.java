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



    /**
     * Get the maximum count how many documents should be created.
     *
     * @return count of documents to create
     */
    public int getMaxCountOfCreatedDocuments() {
        return MAX_COUNT_OF_CREATED_DOCUMENTS;
    }



    /**
     * Get the maximum count how many searches should be performed.
     *
     * @return count of searches which will be performed
     */
    public int getMaxCountOfCreatedSearches() {
        return MAX_COUNT_OF_CREATED_SEARCHES;
    }



    /**
     * Get the count of terms which will define a document and/or search.
     *
     * @return count of terms
     */
    public int getMaxCountOfTermsUsedToDefineVector() {
        return MAX_COUNT_OF_TERMS_USED_TO_DEFINE_VECTOR;
    }



    /**
     * Get the count of terms for the document/search vector which needs to be greater than 0.
     *
     * @return count of terms unequal zero.
     */
    public int getMaxCountOfTermsWithQuantifier() {
        return MAX_COUNT_OF_TERMS_WITH_QUANTIFIER;
    }



    /**
     * Get the count of documents which will be searched for limit search.
     *
     * @return count of terms which will be searched
     */
    public int getLimitForLocalKnowledge() {
        return LIMIT_FOR_LOCAL_KNOWLEDGE;
    }



    /**
     * Get the number of searches which needs to be performed until a document can be repositioned.
     *
     * @return required number of searches to reposition a document
     */
    public int getNumberOfSearchesBeforeRepositioning() {
        return NUMBER_OF_SEARCHES_BEFORE_REPOSITIONING;
    }



    /**
     * Get the threshold to reposition documents which are not searched for a defined number of searches (threshold).
     *
     * @return the threshold
     */
    public int getThreshold() {
        return THRESHOLD;
    }



    /**
     * Get the defined {@link com.github.jhanne82.documenttree.simulation.configuration.enumeration.SearchType}
     * for the simulation to search on the tree.
     *
     * @return the defined {@link com.github.jhanne82.documenttree.simulation.configuration.enumeration.SearchType}
     */
    public SearchType getSearchType() {
        return searchType;
    }


    /**
     * Get the defined {@link com.github.jhanne82.documenttree.simulation.configuration.enumeration.Distribution}
     * when the document terms will be created randomly.
     *
     * @return the defined {@link com.github.jhanne82.documenttree.simulation.configuration.enumeration.Distribution} used to create a document
     */
    public Distribution getDistributionForDocument() {
        return distributionForDocument;
    }


    /**
     * Get the defined {@link com.github.jhanne82.documenttree.simulation.configuration.enumeration.Distribution}
     * when the search terms will be created randomly.
     *
     * @return the defined {@link com.github.jhanne82.documenttree.simulation.configuration.enumeration.Distribution} used to create a search
     */
    public Distribution getDistributionForSearch() {
        return distributionForSearch;
    }



    /**
     * Defines if cluster for document and search terms will be used.
     *
     * @return if cluster will be used
     */
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
