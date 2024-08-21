import org.example.CampaignCalculate;
import org.example.model.CampaignModel;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestCalculate {

    private final CampaignCalculate campaignCalculate = new CampaignCalculate();


    /**
     * Test case for maximizing specific ad budget with a total campaign budget of 0
     *
     * In this case, no ad budget should be allocated, and the result should be 0
     */

    @Test
    public void testZeroTotalBudget(){

        CampaignModel campaignModel = new CampaignModel(
                0.0,
                0.1,
                0.2,
                300,
                0
        );


        double expectedResult = campaignCalculate.maximizeSpecificAdBudget(
                        campaignModel.getCampaignBudget(),
                        campaignModel.getAgencyFees(),
                        campaignModel.getFeesForAds(),
                        campaignModel.getAgencyHours(),
                        List.of(500.00, 2343.01 , 800.3)
        );


        assertEquals(expectedResult, 0.0,1e-9);



    }


    @Test
    public void testValues() {

        double largeTotalBudget = 100000;
        double largeAgencyFee = 0.1;
        double largeToolFee = 0.05;
        double largeHours = 1000;
        List<Double> otherAdBudgets = List.of(
                10000.12,
                20000.0,
                30000.21
        );

        CampaignModel campaignModel = new CampaignModel(
                largeTotalBudget,
                largeAgencyFee,
                largeToolFee,
                largeHours,
                otherAdBudgets.stream().mapToDouble(Double::doubleValue).sum()
        );

        double actualResult = campaignCalculate.maximizeSpecificAdBudget(
                campaignModel.getCampaignBudget(),
                campaignModel.getAgencyFees(),
                campaignModel.getFeesForAds(),
                campaignModel.getAgencyHours(),
                otherAdBudgets
        );

        double expectedResult = 26086.63;
        assertEquals(expectedResult, (Math.round(actualResult * 100.0) / 100.0) , 1e-9);

    }

    @Test
    public void testValues2() {

        double largeTotalBudget = 5000000;
        double largeAgencyFee = 0.3;
        double largeToolFee = 0.01;
        double largeHours = 140;
        List<Double> otherAdBudgets = List.of(
                5000.12, 10000.50, 15000.75, 20000.10, 25000.20,
                30000.30, 35000.40, 40000.50, 45000.60, 50000.70,
                55000.80, 60000.90, 65000.00, 70000.10, 75000.20,
                80000.30, 85000.40, 90000.50, 95000.60, 100000.70
        );

        CampaignModel campaignModel = new CampaignModel(
                largeTotalBudget,
                largeAgencyFee,
                largeToolFee,
                largeHours,
                otherAdBudgets.stream().mapToDouble(Double::doubleValue).sum()
        );

        double actualResult = campaignCalculate.maximizeSpecificAdBudget(
                campaignModel.getCampaignBudget(),
                campaignModel.getAgencyFees(),
                campaignModel.getFeesForAds(),
                campaignModel.getAgencyHours(),
                otherAdBudgets
        );

        double expectedResult = 2766678.35;

        assertEquals( expectedResult, Math.round(actualResult * 100.0) / 100.0, 1e-9);

    }
}
