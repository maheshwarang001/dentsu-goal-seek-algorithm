# Campaign Budget Optimization Tool

## Overview

The Campaign Budget Optimization Tool helps in calculating and optimizing ad budgets within a campaign. The tool ensures that the total campaign budget—including ad spend, agency fees, third-party tool fees, and fixed costs—stays within the approved amount.

The tool uses the Goal Seek algorithm to maximize the budget for a specific ad among other ads using binary search. The overall time complexity is \( O(N \cdot \log(\text{total budget})) \), where \( N \) is the number of ads.

## Features

- **Calculate Total Budget**: Computes the total budget required for a campaign including agency fees, tool fees, and fixed costs.
- **Maximize Specific Ad Budget**: Finds the maximum budget that can be allocated to a specific ad while keeping the total budget within the approved amount.
- **Calculate Goal**: Determines the maximum budget for a specific ad based on the campaign model and existing ads.

## Usage Instructions Commands CLI

### 1. View the Table

To view the table listing the current configuration and parameters:

![View Table](https://github.com/user-attachments/assets/067596c6-913b-429e-be88-3160d4b78f48)


### 2. Initialize Total Campaign Budget

Set the total campaign budget and ensure all parameters are correctly initialized:

![Initialize Budget](https://github.com/user-attachments/assets/1d22206d-4e22-44c4-bbbd-54a6efb7aa22)

### 3. Update Parameters

- Choose the campaign budget or the specific ad.
- Add your query details:

![Update Parameters](https://github.com/user-attachments/assets/0e12fc12-91bd-45b0-aa2e-42bc3cefcf63)

### 4. Insert Ads

- Specify the number of ads you want to input.
- Enter the ads into a TreeMap with `String` keys and `Double` values:

![Insert Ads](https://github.com/user-attachments/assets/a57be3b0-f9e6-40be-8d59-42ff201aed48)

### 5. Maximize the Value Using the Goal Seek Algorithm

- Input the ad name you want to maximize:

![Maximize Ad Value](https://github.com/user-attachments/assets/de045bd1-7877-4456-8368-ac759f48d7c2)


