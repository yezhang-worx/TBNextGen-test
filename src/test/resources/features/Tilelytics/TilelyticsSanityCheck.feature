Feature: Tilelytics - E2E layout - paga navigation test

  Scenario: Tilelytics - SC1 side bar of management role
    Given User is accessing Tilelytics
    When he logins Tilelyics as default "Management"
    Then he should see links in side bars
      | Overview |
      | Admin    |
    Then he should see factories links in side bars
    And he should see Admin links in side bars
      | Factories      |
      | Custom Periods |
    When he logout


  Scenario: Tilelytics - SC2 side bar of wxadmin role
    Given User is accessing Tilelytics
    When he logins Tilelyics as default "wxadmin"
    Then he should see links in side bars
      | Overview            |
      | Admin               |
      | Performance Manager |
    Then he should see factories links in side bars
    And he should see Admin links in side bars
      | Factories      |
      | Custom Periods |
    When he logout

  #  Issue : refresh to get new company overview page
  #  Issue : company overview should links to new overview page

  Scenario: Tilelytics E2E layout - SC3 company overview page
    Given User is accessing Tilelytics
    When he logins Tilelyics as default "wxadmin"
#    Issue : refresh to get new company overview page
    Then he should see Tilelytics default overview page with company name
    When he selects "Dark" mode and "English" version
    Then he should see profile display in "Dark" mode
    Then he should be in company overview page
    #    TODO : get currency from DB
    And he should see KPI tiles
      | OEE          |
      | Availability |
      | Efficiency   |
      | Quality      |
    And he should see period list
      | Customize your Start and End dates |
      | Yesterday                          |
      | Current Week                       |
      | Last Week                          |
      | Current Month                      |
      | Last Month                         |
      | Last Custom Period                 |
    And he should see opportunity unit menu
      | CAD |
      | hr  |
    And he should see potential gain
#    mouse moveover tooltip
#    And he should see tooltips
#      | Amount of gains, using a benchmark of 100%, listed by opportunity and production line. It could an annualized value or a value that covers the selected period. Open the menu next to the period picker to change it. |
#      | Amount of gains using a benchmark of 100%. It could an annualized value or a value that covers the selected period. Open the menu next to the period picker to change it.                                             |
    And he should see Opportunities section
    And he should see chart options
      | OEE          |
      | Availability |
      | Performance  |
    And he should see charts
      | View as a line chart |
      | View as tabular data |
      | View as a bar chart  |
    When he should be able to switch to table view
    Then he should be able to see factories in chart
    Then he should be able to see saved filter
    #    TODO : get saved filters from DB
    When he clicks on Filter
    When he logout


  Scenario: Tilelytics E2E layout - SC4 DPM page layout for wxadmin user
    Given User is accessing Tilelytics
    When he logins Tilelyics as default "wxadmin"
    Then he should see Tilelytics default overview page with company name
    When he navigates to DPM
    Then he should see DPM options
      | Shift Changes |
      | Line Starts   |
    And he should see accessible factory list in DPM page
    When he selects default factory
    Then he should see accessible PU lines list in DPM page
    When he selects SF4 PU line
    Then he should see shift transactions list
    And he should see tooltips in DPM page
      | Total amount of time saved if there is no downtime during a shift change. Directors can also view the time converted into currency using each production unit's cost per hour factor. |
    And he should see Potential Yearly Savings options
      | View savings in currency |
      | View savings in time     |
    And he clicks on Apply Filters button
    Then he should see chart section with reports
      | Today’s Average    |
      | Cumulative Average |
      | Best Mark          |
    When he clicks on table view
    Then he should see table view
    When he logout


  Scenario: Tilelytics E2E layout - SC5 factory sub menu
    Given User is accessing Tilelytics
    When he logins Tilelyics as default "Management"
    When he expands sidebar of default factory
    When he navigates to "Availability & Downtime" of default factory
    Then he should see Availability and Downtime outline page
    When he navigates to "My Reports" of default factory
    Then he should see report template
      | General - Downtime         |
      | Production line - Downtime |
      | Shift - Downtime           |
      | Overview Report            |
    When he navigates to "Production Data Export" of default factory
     #    issue : default factory-> Data export - production units -- none element is selected
    Then he should see PU list in Production Data Export page
    And he should see period list
      | Customize your Start and End dates |
      | Yesterday                          |
      | Current Week                       |
      | Last Week                          |
      | Current Month                      |
      | Last Month                         |
#      | Last Custom Period                 |
    And he should be able to download data
    When he logout

  Scenario: Tilelytics E2E layout - SC6 factory overview
    Given User is accessing Tilelytics
    When he logins Tilelyics as default "Management"
    When he expands sidebar of default factory
    When he navigates to "Overview" of default factory
    Then he validates Period dropdown list of options
#      | Customize your Start and End dates |
      | Yesterday          |
      | Current Week       |
      | Last Week          |
      | Current Month      |
      | Last Month         |
      | Last Custom Period |
    Then he should see PU dropdown list of default factory
    Then he should see dropdown list of "Period"
      | Yesterday          |
      | Current Week       |
      | Last Week          |
      | Current Month      |
      | Last Month         |
      | Last Custom Period |
    Then he should see "Statistics" section
      | OEE          |
      | Availability |
      | Performance  |
      | Quantity     |
    Then he should see "Production time" section
      | Planned downtime     |
      | Unplanned downtime   |
      | Unjustified downtime |
    Then he should see dropdown list of "Key Performance Indicators (KPI)"
      | OEE          |
      | Availability |
      | Performance  |
      | Quantity     |
      | Weight       |
#    "Weight" from dropdown list of "Key Performance Indicators (KPI)"
    Then he should see dropdown list of "Unit"
      | Kilogram (kg)        |
      | Metric Tonne (t)     |
      | Pound (lb)           |
      | Ounce (oz)           |
      | US ton (US t)        |
      | Imperial ton (imp t) |
    When he logout

  Scenario: Tilelytics E2E layout - SC7 factory availability and downtime
    Given User is accessing Tilelytics
    When he logins Tilelyics as default "Management"
    When he expands sidebar of default factory
    When he navigates to "Availability & Downtime" of default factory
    Then he should see Availability and Downtime outline page

    When he logout