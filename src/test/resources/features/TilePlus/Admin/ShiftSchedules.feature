Feature: Tile+ - Admin - shift schedules


  Scenario: Tile+ admin-> shift schedules sanity check
    Given Tester is accessing TilePlus
    When he logins as default "Management"
    When he selects "English" version
    Then he should see user profile
    When he accesses admin "Shift Schedules"
    Then he should see Shift Schedules filters in the page
      | All production units |
      | All days             |
      | All shifts           |
    Then he should see "PU" dropdown list of "All production units"
#      | Line - Giveaway |
#      | UAT             |
#      | Blackbox PoC    |
#      | Packaging       |
    Then he should see dropdown list of "All days"
      | Monday    |
      | Tuesday   |
      | Wednesday |
      | Thursday  |
      | Friday    |
      | Saturday  |
      | Sunday    |
#    And he should see dropdown list of "All shifts" of factory "Factory WX"
#    TODO : validate UI against DB
    And he should see PUs shifts display with default factory
    When he creates a new shift

    When he logout






#  Requested by SEO. Refactor the default root downtime categories. Instead of the 7 we used to have, we will have 4
#
#  100 - Production Management
#  200 - Equipment
#  300 - Quality
#  400 - Health and Safety - Environment
