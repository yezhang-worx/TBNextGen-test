Feature: Tile+ - Admin - Downtime Reasons

  Scenario: Tile+ admin-> Downtime reasons sanity check
    Given Tester is accessing TilePlus
    When he logins as default "Management"
    When he selects "English" version
    Then he should see user profile
    When he accesses admin "Downtime Reasons"
    Then he should see root downtime category list of default factory
      | 100 - Maintenance       |
      | 200 - Mechanical        |
      | 300 - Human Resources 3 |
      | L3 - L3                 |
      | L4b - L4                |
    When he logout






#  Requested by SEO. Refactor the default root downtime categories. Instead of the 7 we used to have, we will have 4
#
#  100 - Production Management
#  200 - Equipment
#  300 - Quality
#  400 - Health and Safety - Environment
