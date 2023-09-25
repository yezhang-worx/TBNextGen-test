Feature: Tilelytics  - Factory Edit


  Scenario: Tilelytics admin - factory update
    Given User is accessing Tilelytics
    When he logins Tilelyics as default "Management"
    Then he should see links in side bars
      | Admin |
    When he expands Admin in sidebar
    Then he should see Admin links in side bars
      | Factories      |
      | Custom Periods |
    And he navigates to Factories page
    Then he should see page with title "Factories"
    Then he should see factory list in Factories page
    When he edits default factory

#    TODO : edit factory
          #    When he updates default factory name as "TestFactory"
          #    Then he should see default factory updated as as "TestFactory"
          #    When he resets default factory name

    Then he should see fields in Factory page
      | Name         |
      | Currency     |
      | Availability |
      | Performance  |
      | Quality      |
      | OEE          |
    When he logout
