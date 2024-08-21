package org.example.model;

/**
 * model for a campaign's budget
 * This class encapsulates all the data related to a campaign's budget including
 * the total budget, agency fees, fees for ads, agency hours and total ad spend
 */
public class CampaignModel {

    double campaignBudget;
    double agencyFees;
    double feesForAds ;
    double agencyHours;
    double totalAdSpend;

    /**
     * Default constructor for the CampaignModel class
     * */
    public CampaignModel() {
    }

    /**
     * Constructs a new CampaignModel with the specified parameters.
     *
     * @param campaignBudget  the total budget allocated for the campaign
     * @param agencyFees      the percentage of the budget allocated for agency fees
     * @param feesForAds      the percentage of the budget allocated for ad-related tools and fees
     * @param agencyHours     the fixed cost for agency hours
     * @param totalAdSpend    the total amount spent on ads so far
     */
    public CampaignModel(double campaignBudget, double agencyFees, double feesForAds, double agencyHours, double totalAdSpend) {
        this.campaignBudget = campaignBudget;
        this.agencyFees = agencyFees;
        this.feesForAds = feesForAds;
        this.agencyHours = agencyHours;
        this.totalAdSpend = totalAdSpend;
    }

    public double getCampaignBudget() {
        return campaignBudget;
    }

    public void setCampaignBudget(double campaignBudget) {
        this.campaignBudget = campaignBudget;
    }

    public double getAgencyFees() {
        return agencyFees;
    }

    public void setAgencyFees(double agencyFees) {
        this.agencyFees = agencyFees;
    }

    public double getFeesForAds() {
        return feesForAds;
    }

    public void setFeesForAds(double feesForAds) {
        this.feesForAds = feesForAds;
    }

    public double getAgencyHours() {
        return agencyHours;
    }

    public void setAgencyHours(double agencyHours) {
        this.agencyHours = agencyHours;
    }

    public double getTotalAdSpend() {
        return totalAdSpend;
    }

    public void setTotalAdSpend(double totalAdSpend) {
        this.totalAdSpend = totalAdSpend;
    }

    @Override
    public String toString() {
        return "CampaignModel{" +
                "campaignBudget=" + campaignBudget +
                ", agencyFees=" + agencyFees +
                ", feesForAds=" + feesForAds +
                ", agencyHours=" + agencyHours +
                ", totalAdSpend=" + totalAdSpend +
                '}';
    }
}
