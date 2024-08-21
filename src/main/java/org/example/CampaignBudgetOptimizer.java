package org.example;

import org.example.model.Ads;
import org.example.model.CampaignModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;
import java.util.StringTokenizer;

public class CampaignBudgetOptimizer {

    private CampaignModel campaignModel;
    private Ads adsMap;

    private static BufferedReader br;
    private static PrintWriter pw;

    public static void main(String[] args) {
        CampaignBudgetOptimizer optimizer = new CampaignBudgetOptimizer();

        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            pw = new PrintWriter(System.out, true);

            boolean running = true;

            while (running) {
                displayMenu();
                int choice = getUserChoice();

                switch (choice) {
                    case 1:
                        optimizer.viewCurrentValues();
                        break;
                    case 2:
                        optimizer.initializeCampaign();
                        break;
                    case 3:
                        optimizer.update();
                        break;
                    case 4:
                        optimizer.insertAds();
                        break;
                    case 5:
                        optimizer.goalSeek();
                        break;
                    case 6:
                        pw.println("Exiting...");
                        running = false;
                        break;
                    default:
                        pw.println("Invalid choice. Please select between 1-6.");
                        pw.flush();
                        break;
                }
            }
        } catch (IOException e) {
            pw.println("An error occurred while reading input.");
            pw.flush();
        } finally {
            closeResources();
        }
    }

    private static void displayMenu() {
        pw.println("\nCampaign Budget CLI");
        pw.println("1. View Current Values");
        pw.println("2. Initialize Campaign Parameters");
        pw.println("3. Update Parameters");
        pw.println("4. Insert Ad Budgets");
        pw.println("5. Ad Goal Seek");
        pw.println("6. Exit");
        pw.print("Pick from (1-6): ");
        pw.flush();
    }

    private static int getUserChoice() {
        try {
            return Integer.parseInt(br.readLine().trim());
        } catch (NumberFormatException | IOException e) {
            pw.println("Invalid input. Please enter a number between 1-6.");
            pw.flush();
            return -1; // Invalid choice
        }
    }

    private static void closeResources() {
        try {
            if (br != null) br.close();
            if (pw != null) pw.close();
        } catch (IOException e) {
            pw.println("An error occurred while closing resources.");
            pw.flush();
        }
    }

    private void update() throws IOException {
        pw.println("\nUpdate");
        pw.println("1. Update Campaign Budget");
        pw.println("2. Update Ad");
        pw.flush();

        int choice = getUserChoice();

        switch (choice) {
            case 1:
                updateCampaign();
                break;
            case 2:
                updateAd();
                break;
            default:
                pw.println("Invalid option. Please select 1 or 2.");
                pw.flush();
                break;
        }
    }

    private void updateCampaign() throws IOException {
        if (campaignModel == null) {
            pw.println("Campaign is not initialized. Please initialize first.");
            pw.flush();
            return;
        }

        pw.println("\nUpdate Campaign Budget");
        pw.println("Guide -> Update Any");
        pw.println("Z: Total Budget");
        pw.println("Y1: Agency Fee Percentage");
        pw.println("Y2: Fees For Ads tools");
        pw.println("H: Fixed Cost for Agency Hours");
        pw.print("Enter new values: ");
        pw.flush();

        StringTokenizer st = new StringTokenizer(br.readLine().trim());

        if (st.countTokens() != 2) {
            pw.println("Invalid input. Please provide a valid input in the format: key value.");
            pw.flush();
            return;
        }

        String key = st.nextToken();
        double value;
        try {
            value = Double.parseDouble(st.nextToken());
        } catch (NumberFormatException e) {
            pw.println("Invalid value. Please enter a valid number.");
            pw.flush();
            return;
        }

        switch (key) {
            case "Z":
                campaignModel.setCampaignBudget(value);
                break;
            case "Y1":
                campaignModel.setAgencyFees(value);
                break;
            case "Y2":
                campaignModel.setFeesForAds(value);
                break;
            case "H":
                campaignModel.setAgencyHours(value);
                break;
            default:
                pw.println("Invalid option. Please choose one of the valid keys: Z, Y1, Y2, H");
                pw.flush();
        }
    }

    private void updateAd() throws IOException {
        if (adsMap == null) adsMap = new Ads();

        pw.println("\nUpdate Ads");
        pw.println("Guide: BudgetName budgetCost");
        pw.flush();

        StringTokenizer st = new StringTokenizer(br.readLine().trim());

        if (st.countTokens() < 2) {
            pw.println("Insufficient input. Please provide both name and budget.");
            pw.flush();
            return;
        }

        String name = st.nextToken();

        if (!adsMap.getAds().containsKey(name)) {
            pw.println("Ad does not exist.");
            return;
        }

        double adBudget;
        try {
            adBudget = Double.parseDouble(st.nextToken());

            if (campaignModel.getTotalAdSpend() - adsMap.getAds().get(name) + adBudget > campaignModel.getCampaignBudget()) {
                pw.println("Updated budget cannot exceed total budget.");
                return;
            }
            if (adBudget < 0) throw new ArithmeticException();
        } catch (Exception e) {
            pw.println("Invalid budget amount. Please enter a valid number.");
            pw.flush();
            return;
        }

        campaignModel.setTotalAdSpend(campaignModel.getTotalAdSpend() - adsMap.getAds().get(name) + adBudget);
        adsMap.setAdsMap(name, adBudget);
    }

    private void goalSeek() throws IOException {
        if (campaignModel == null || adsMap == null) {
            pw.println("Please initialize campaign budget and ads first.");
            pw.flush();
            return;
        }

        pw.println("\nGoal Seeker:");
        pw.println("Insert Ad Name:");

        String adName = br.readLine().trim();

        if (!adsMap.getAds().containsKey(adName)) {
            pw.println("Ad name not found. Please try again.");
            pw.flush();
            return;
        }

        CampaignCalculate campaignCalculate = new CampaignCalculate();
        double maxBudget = campaignCalculate.calculateGoal(campaignModel, adsMap, adName);
        pw.println("Max Budget for " + adName + ": " + maxBudget);
        pw.flush();

        campaignModel.setTotalAdSpend(campaignModel.getTotalAdSpend() + maxBudget - adsMap.getAds().get(adName));
        adsMap.setAdsMap(adName, maxBudget);
    }

    private void viewCurrentValues() {
        pw.println("\nCurrent Values:");

        if (campaignModel != null) {
            pw.println("║                      Campaign Summary");
            pw.println("╠══════════════════════════════════════════════════════════════════════════");
            pw.printf("║ %-25s: %10.2f\n", "Total Budget", campaignModel.getCampaignBudget());
            pw.printf("║ %-25s: %9.2f%%\n", "Agency Fee Percentage", campaignModel.getAgencyFees() * 100);
            pw.printf("║ %-25s: %9.2f%%\n", "Fees For Ads", campaignModel.getFeesForAds() * 100);
            pw.printf("║ %-25s: %10.2f\n", "Fixed Cost for Agency Hours", campaignModel.getAgencyHours());
            pw.printf("║ %-25s: %10.2f\n", "Total Spend on ads", campaignModel.getTotalAdSpend());
        } else {
            pw.println("Campaign budget not initialized.");
        }

        if (adsMap != null) {
            pw.println("\nAd Budget Table:");
            pw.println("+----------------+-----------------+");
            pw.println("|    Ad Name     |    Ad Budget    |");
            pw.println("+----------------+-----------------+");

            for (Map.Entry<String, Double> entry : adsMap.getAds().entrySet()) {
                pw.printf("| %-14s | %-15.2f |\n", entry.getKey(), entry.getValue());
                pw.println("+----------------+-----------------+");
            }
        } else {
            pw.println("No ads available.");
        }

        pw.flush();
    }

    private void insertAds() {
        try {
            if (adsMap == null) adsMap = new Ads();

            pw.println("\nInsert Ads");
            pw.println("Number of ads to be inserted");
            pw.flush();

            int numberOfAds = Integer.parseInt(br.readLine().trim());
            pw.println("Guide: BudgetName budgetCost");
            pw.flush();

            for (int i = 0; i < numberOfAds; i++) {
                pw.print("Enter details for ad " + (i + 1) + ": ");
                pw.flush();

                StringTokenizer st = new StringTokenizer(br.readLine().trim());

                if (st.countTokens() < 2) {
                    pw.println("Insufficient input. Please provide both name and budget.");
                    pw.flush();
                    i--;
                    continue;
                }

                String name = st.nextToken();

                if (adsMap.getAds().containsKey(name)) {
                    pw.println("Ad already exists.");
                    i--;
                    continue;
                }

                double adBudget;
                try {
                    adBudget = Double.parseDouble(st.nextToken());

                    if (campaignModel.getTotalAdSpend() + adBudget > campaignModel.getCampaignBudget()) {
                        pw.println("Ad budget exceeds the total budget.");
                        throw new ArithmeticException();
                    }
                    if (adBudget < 0) throw new ArithmeticException();
                } catch (Exception e) {
                    pw.println("Invalid budget amount. Please enter a valid number.");
                    pw.flush();
                    i--;
                    continue;
                }

                campaignModel.setTotalAdSpend(campaignModel.getTotalAdSpend() + adBudget);
                adsMap.setAdsMap(name, adBudget);
            }
        } catch (IOException e) {
            pw.println("An error occurred while reading input.");
            pw.flush();
        }
    }

    private void initializeCampaign() {
        if (campaignModel == null) {
            try {
                pw.println("\nInitialize Campaign Budget");
                pw.println("Guide -> Z Y1 Y2 H");
                pw.println("Z: Total Budget");
                pw.println("Y1: Agency Fee Percentage");
                pw.println("Y2: Fees For Ads tools");
                pw.println("H: Fixed Cost for Agency Hours");

                pw.print("Enter values: ");
                pw.flush();

                StringTokenizer st = new StringTokenizer(br.readLine().trim());

                if (st.countTokens() != 4) {
                    pw.println("Invalid input. Please provide exactly four parameters.");
                    pw.flush();
                    return;
                }

                double totalBudget = Double.parseDouble(st.nextToken());
                double agencyFee = Double.parseDouble(st.nextToken());
                double adsFee = Double.parseDouble(st.nextToken());
                double agencyHours = Double.parseDouble(st.nextToken());

                if (totalBudget < 0 || agencyFee < 0 || adsFee < 0 || agencyHours < 0) {
                    throw new ArithmeticException();
                }

                campaignModel = new CampaignModel(totalBudget, agencyFee, adsFee, agencyHours, 0);

                pw.println("Campaign budget initialized successfully.");
                pw.flush();
            } catch (IOException | NumberFormatException e) {
                pw.println("Invalid input. Please provide valid numbers for all parameters.");
                pw.flush();
            }
        } else {
            pw.println("Campaign budget already initialized.");
            pw.flush();
        }
    }
}
