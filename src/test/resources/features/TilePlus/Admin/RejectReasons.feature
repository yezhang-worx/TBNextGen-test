Feature: Tile+ - Admin - Reject Reasons

  Scenario: Tile+ admin-> Reject reasons sanity check
    Given Tester is accessing TilePlus
    When he logins as default "Management"
    When he selects "English" version
    Then he should see user profile
    When he accesses admin "Reject Reasons"
    Then he should see root downtime category list of default factory
      | 100 - Process      |
      | 200 - Raw Material |
    When he logout
