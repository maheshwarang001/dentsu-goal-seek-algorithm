package org.example.model;

import java.util.Map;
import java.util.TreeMap;

/**
 * model for the Ad's budget
 * This class encapsulates all the data related to the Ad's including
 * name and budget reserved for the add
 */

public class Ads {

    //A Tree map to store name and budget in the order

    private TreeMap<String, Double> adsMap;

    /**
     * Default constructor for the Ads class
     * */
    public Ads() {
        this.adsMap = new TreeMap<>();
    }


    public Map<String, Double> getAds() {
        return adsMap;
    }

    public void setAdsMap(TreeMap<String, Double> adsMap){
        this.adsMap = adsMap;
    }

    public void setAdsMap(String adName, Double adBudget) {
        adsMap.put(adName, adBudget);
    }

}
