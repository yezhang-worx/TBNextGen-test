Feature: Common - Date picker - 4 sanity test

  Scenario: Tilelytics Company overview page - Period range 15 months
    Given User is accessing Tilelytics
    When he logins Tilelyics as default "Management"
    When he selects "English" version
#    Issue : refresh to get new company overview page
    Then he should see Tilelytics default overview page with company name
    Then he should be in company overview page
    And he should see period list
      | Customize your Start and End dates |
      | Yesterday                          |
      | Current Week                       |
      | Last Week                          |
      | Current Month                      |
      | Last Month                         |
      | Last Custom Period                 |
    When he selects "Customize your Start and End dates" from period list
    Then he should see period range of "15" month
    When he logout

  Scenario: Tilelytics Factory Overview
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
    When he logout


  Scenario: Tilelytics Production Data Export  - Period range 15 months
    Given User is accessing Tilelytics
    When he logins Tilelyics as default "Management"
    When he expands sidebar of default factory
    When he navigates to "Production Data Export" of default factory
    Then he should see PU list in Production Data Export page
    And he should see period list
      | Customize your Start and End dates |
      | Yesterday                          |
      | Current Week                       |
      | Last Week                          |
      | Current Month                      |
      | Last Month                         |
#      | Last Custom Period                 |
    When he selects "Customize your Start and End dates" from period list
    Then he should see period range of "15" month
#    TODO
    And he should be able to download data
    When he logout

  Scenario: Tile+ Production Data Export
    Given User is accessing TilePlus
    When he logins as default "Management"
    Then he should see default factory is displayed
    When he navigates to "Production Data Export" of main sidebar
    And he should see period list
      | Customize your Start and End dates |
      | Yesterday                          |
      | Current Week                       |
      | Last Week                          |
#      | Current Month                      |
#      | Last Month                         |
#      | Last Custom Period                 |
    When he logout

