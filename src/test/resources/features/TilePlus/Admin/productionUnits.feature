Feature: Tile+ - Admin - Production Unit


  Scenario Outline: Tile+ - Production unit - management SF4/non-SF4 PU sanity layout
    Given Tester is accessing TilePlus
    When he logins as default "Management"
    When he selects "English" version
    Then he should see user profile
    When he accesses admin "Production Units"
    Then he should see "PU" list of default factory
    When he clicks on the "SF4" PU
    Then he should see page with title "Production Unit"
    Then he should see PU form page with sections
      | Item details                   |
      | Targets                        |
#      | Global Production Unit Quality | DB migration
      | Converted Unit Name            |
      | Cost Per Hour                  |
    Then he should see PU form page with delays
      | Downtime Delay               |
      | Downtime Justification Delay |
    Then he validates converted Unit dropdown list options
      | units   |
      | cases   |
      | packs   |
      | bottles |
      | boxes   |
      | cycles  |
      | bags    |
      |pallets  |
      | kg      |
      | t       |
      | lb      |
      | oz      |
      | m       |
      | ft      |
      | l       |
    Then he should see PU form page with Giveaway Sampling Session
    When he clicks on "Cancel" button
    Then he should see page with title "Production Units"
    When he logout

    Examples:
      | PUName          |
      | Line - Giveaway |
#      | UAT             |






##TODO : 3 delay sections based on user level
#  1: delays - three delays
#    1.1 Downtime delay
#    1.2 end of downtime delay
#    1.3 Justification delay
#  2: delay: giveaway sampling session
#  3: advanced setting : wxadmin
#    1.1 Measure hole delay
#    1.2 Schedule downtime automatic justification delay


##  internal  ---removed
#  Scenario Outline: Tile+ admin->Production unit - SF4 PU with user wxadmin@Test.com
#    Given Tester is accessing TilePlus
#    When he logins as default "wxadmin"
#    Then he should see user profile
#    When he accesses admin "Production Units"
#    Then he should see page with title "Production Units"
#    When he exports on PU "<PUName>"
#    When he exports giveaway measurements of PU "<PUName>"
#    Then in production unit page, he should see advance settings
#      | Measure Hole Detection delay|
#      | Recurring Downtime Trigger Time|
#    When he clicks on "Cancel" button
#    When he logout

#    Examples:
#      | PUName          |
#      | Line - Giveaway |