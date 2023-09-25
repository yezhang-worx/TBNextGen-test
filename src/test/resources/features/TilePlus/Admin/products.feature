Feature: Tile+ - Admin - Products

#  Not run this test case in regression test, since it will create a lot of soft-deleted product in Prod
  @Skip
  Scenario Outline: Tile+ admin->Products - create update and delete a product
    Given Tester is accessing TilePlus
    When he logins as default "Management"
    Then he should see user profile
    When he accesses admin "<subMenu>"
    Then he should see "<subMenu>" list of default factory
    And he should see buttons in the page
      | Export  |
      | Import  |
      | Product |
    When he adds a new product
    Then he validates Unit dropdown list of options
      | None |
      | kg   |
      | lb   |
      | g    |
      | L    |
      | mL   |
      | m    |
      | ft   |
    Then he should see PU list of default factory
    When he fills new product
      | sku          | description | category | target | associatedPU |
      | <deviceName> | testProduct | testCat  | 2000   | <SF4>        |
    When he searches product "<deviceName>"
    Then he should find product "<deviceName>"
    When he edits product "<deviceName>"
    When he update product name to "<updatedDeviceName>"
    Then he should find product by name "<updatedDeviceName>"
    When he deletes product "<deviceName>"
    Then he should not find product "<deviceName>"
    When he logout

    Examples:
      | subMenu  | deviceName           | updatedDeviceName           |
      | Products | automationTestDevice | updatedAutomationTestDevice |

  Scenario Outline: Tile+ admin->Products - product wizard
    Given Tester is accessing TilePlus
    When he logins as default "Management"
    When he selects "English" version
    Then he should see user profile
    When he accesses admin "<subMenu>"
    Then he should see "<subMenu>" list of default factory
    And he should see buttons in the page
      | Export  |
      | Import  |
      | Product |
    When he edits the first product
    Then he validates Unit dropdown list of options
      | None |
      | kg   |
      | lb   |
      | g    |
      | oz   |
      | L    |
      | mL   |
      | m    |
      | ft   |
    Then he should see PU list of default factory
    When he saves the form
    When he logout

    Examples:
      | subMenu  |
      | Products |



##TODO list
#    1: target rate/h = 9999999999
#Done    2: SF4 : mandatory field : Target Giveaway Percentage
#    3: