Feature: Tile+ - Admin - Devices

  Scenario Outline: Tile+ admin->Devices - devices list sanity check
    Given User is accessing TilePlus
    When he logins as default "Management"
    When he selects "English" version
    Then he should see user profile
    When he accesses admin "<subMenu>"
    Then he should see page with title "<subMenu>"
    Then he should see "<subMenu>" list of default factory
#    TODO : validate UI against device list from DB
    When he clicks on the first Device of default factory
    Then he should see text "Factory time zone" displays in the page
#    TODO : validate UI against device detail in DB
    When he navigates back to devices list
    When he logout

    Examples:
      | subMenu |
      | Devices  |