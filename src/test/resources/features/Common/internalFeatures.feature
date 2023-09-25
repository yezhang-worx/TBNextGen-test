Feature: common - internal features - user wxadmin

#  Admin->Production unit: export Measures is only available for wxadmin
  Scenario Outline: Tile+ - Production unit - SF4 PU should contain 2 exports
    Given Tester is accessing TilePlus
    When he logins as default "wxadmin"
    Then he should see user profile
    When he accesses admin "Production Units"
    When he views PU measures activity of "<SFType>" PU
#    Then he should see page with title "<SF4PUName>"
    Then in activity page, he should see SF4 exports
      | <ExportMeasures>        |
      | <ExportGiveawaySamples> |
    When he clicks on "<ExportMeasures>"
    Then he should see "<ExportMeasures>" popup
#    When he clicks on input "Effective from"
#    Then he should see period range of "<ExportMeasuresRangeInWeek>" month
    When he dismisses the wizard
    When he clicks on "<ExportGiveawaySamples>"
#    ISSUE : popup tile should be fixed
#    Then he should see "<ExportGiveawaySamples>" popup
    When he clicks on input "Effective from"
    Then he should see period range of "<ExportGiveawaySamplesRangeInMonth>" month
    When he dismisses the wizard
    When he logout

    Examples:
      | SFType | ExportMeasures  | ExportGiveawaySamples   | ExportGiveawaySamplesRangeInMonth | ExportMeasuresRangeInWeek |
      | SF4    | Export Measures | Export Giveaway Samples | 12                                | 1                         |
#  ISSUE : ExportGiveawaySamples = Export Giveaway Samples

  Scenario Outline: Tile+ - Production unit - not SF4 PU should not contain export giveaway samples
    Given Tester is accessing TilePlus
    When he logins as default "wxadmin"
    Then he should see user profile
    When he accesses admin "Production Units"
    When he views PU measures activity of "<SFType>" PU
#    Then he should see page with title "<notSF4PUName>"
    Then he should see "<ExportMeasures>" button
    Then he should not see "<ExportGiveawaySamples>" button
    When he logout

    Examples:
      | SFType | ExportMeasures  | ExportGiveawaySamples   | ExportGiveawaySamplesRangeInMonth | ExportMeasuresRangeInWeek |
      | !SF4   | Export Measures | Export Giveaway Samples | 12                                | 1                         |


  Scenario Outline: Tile - Production unit - advance setting
    Given Tester is accessing TilePlus
    When he logins as default "wxadmin"
    Then he should see user profile
    When he accesses admin "Production Units"
    When he clicks on the "SF4" PU
    Then he should see page with title "Production Unit"
    Then in production unit page, he should see advance settings
      | End of Downtime Delay           |
      | Measure Hole Detection delay    |
      | Recurring Downtime Trigger Time |
    When he clicks on "Cancel" button
    When he logout

    Examples:
      | PUName          |
      | Line - Giveaway |