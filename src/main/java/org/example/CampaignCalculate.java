package org.example;

import org.example.model.Ads;
import org.example.model.CampaignModel;

import java.util.List;
import java.util.Map;


/**
 * The {@code CampaignCalculate} class provides methods for calculating and optimizing campaign budgets.
 * It includes functionalities to calculate the total budget, maximize the budget for a specific ad,
 * and determine the budget goal for a given ad within a campaign.
 */
public class CampaignCalculate {


    /**
     * Calculates the total budget required given the current ad budget, agency fee, tool fee,
     * agency hours, and other ad budgets.
     *
     * Time Complexity: O(N)
     * Space Complexity: O(1)
     *
     * @param currX            the current budget allocated to a specific ad
     * @param agencyFee        the percentage of the budget allocated for agency fees
     * @param toolFee          the percentage of the budget allocated for tool fees
     * @param hours            the fixed cost for agency hours
     * @param otherAdBudgets   a list of budgets for other ads
     * @return                 the total budget required including all fees and costs
     */
    public double calculateTotalBudget(double currX, double agencyFee, double toolFee, double hours, List<Double> otherAdBudgets) {
        double totalAdSpend = currX;

        // Add the budgets of other ads to the total ad spend
        for (double budget : otherAdBudgets) {
            totalAdSpend += budget;
        }

        // Calculate agency fees as a percentage of the total ad spend
        double agencyFees = agencyFee * totalAdSpend;

        // Calculate tool fees as a percentage of the total ad spend
        double thirdPartyFees = toolFee * (totalAdSpend);


        //return the total budget required
        return totalAdSpend + agencyFees + thirdPartyFees + hours;
    }



    /**
     * Finds the maximum budget that can be allocated to a specific ad without exceeding
     * the total budget allocated for the campaign. The method uses a binary search algorithm to find
     * the maximum possible budget for the ad that fits within the given constraints.
     *
     * Time Complexity: O(N * Log(totalBudgetAllocated)
     * Space Complexity: O(1)
     *
     * @param totalBudgetAllocated   the total budget allocated for the campaign
     * @param agencyFee              the percentage of the budget allocated for agency fees
     * @param toolFee                the percentage of the budget allocated for tool fees
     * @param hours                  the fixed cost for agency hours
     * @param otherAdBudgets         a list of budgets for other ads
     * @return                       the maximum budget that can be allocated to the specific ad
     */

    public double maximizeSpecificAdBudget(double totalBudgetAllocated, double agencyFee, double toolFee, double hours, List<Double> otherAdBudgets) {
        double xLow = 0;
        double xHigh = totalBudgetAllocated;
        double currentX = xHigh;


        //tolerance for the binary search
        double TOLERANCE = 1e-9;



        // binary search to find the maximum budget for the ad

        while (xHigh - xLow > TOLERANCE) {
            double totalBudget = calculateTotalBudget(currentX, agencyFee, toolFee, hours, otherAdBudgets);
            if (totalBudget > totalBudgetAllocated) {
                xHigh = currentX;
            } else {
                xLow = currentX;
            }
            currentX = (xHigh + xLow) / 2;
        }

        // lower bound of the range as the maximum budget
        return xLow;
    }


    /**
     * Calculates the maximum budget that can be allocated to a specific ad based on the campaign model and existing ads.
     *
     * Time Complexity: O(N)
     * Space Complexity: O(N)
     *
     * @param campaignModel  the campaign model containing budget and fee information
     * @param ads            the ads model containing ad names and budgets
     * @param ad             the name of the ad for which to calculate the budget goal
     * @return               the maximum budget that can be allocated to the specified ad, or -1 if not possible
     */
    public double calculateGoal(CampaignModel campaignModel , Ads ads, String ad ){

        Map<String, Double> adBudgetsMap = ads.getAds();
        List<Double> otherAdBudgets = adBudgetsMap.entrySet().stream()
                .filter(entry -> !entry.getKey().equals(ad))
                .map(Map.Entry::getValue)
                .toList();

        if(campaignModel.getTotalAdSpend() - ads.getAds().get(ad) > campaignModel.getTotalAdSpend()){
            return -1;
        }

        return maximizeSpecificAdBudget(
                campaignModel.getCampaignBudget(),
                campaignModel.getAgencyFees(),
                campaignModel.getFeesForAds(),
                campaignModel.getAgencyHours(),
                otherAdBudgets
        );



    }
}
