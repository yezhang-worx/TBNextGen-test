Feature: Tile+ - Admin - user


#  Scenario: Tile+ admin->user - create new user role "Management"
#    Given Tester is accessing TilePlus
#    When he logins as default "Management"
#    Then he should see user profile
#    When he accesses admin users
#    Then he should see users list associated to selected company
#    When Tester wants to create a new user:
#      | displayName             | email                        | password | role       | adminRight | TilelyticsAccess | PUAssociations | activeUser | factory |
#      | 1 ManagementDefaultName | ManagementTest@worximity.com | Test1234 | Management | -          | -                | -              | True       | -       |
#    Then he should see new created user displayed in user list with correct information
#

#  Scenario: Tile+ admin->user - create new user role "Management"
#    Given Tester is accessing TilePlus
#    When he logins as default "Management"
#    Then he should see user profile
#    When he accesses admin users
#    Then he should see users list associated to selected company
#    When Tester wants to create a new user:
#      | displayName             | email                        | password | role       | adminRight | TilelyticsAccess | PUAssociations | activeUser | factory |
#      | 1 ManagementDefaultName | ManagementTest@worximity.com | Test1234 | Management | -          | -                | -              | True       | -       |
#    Then he should see new created user displayed in user list with correct information
#

#  Scenario: Tile+ admin->user - create new user role "Operation" with admin (factory dropdown list)
#    Given Tester is accessing TilePlus
#    When he logins as default "Management"
#    Then he should see user profile
#    When he accesses admin users
#    Then he should see users list associated to selected company
#    When Tester wants to create a new user:
#      | displayName          | email                            | password | role      | adminRight | TilelyticsAccess | PUAssociations | activeUser | factory |
#      | 2 OperationWithAdmin | OperationWithAdmin@worximity.com | Test1234 | Operation | true       | true             | All            | true       | #1      |
#    Then he should see new created user displayed in user list with correct information
#

#  Scenario: Tile+ admin->user - create new user role "Operation" without admin (no factory dropdown list, PU association list)
#    Given Tester is accessing TilePlus
#    When he logins as default "Management"
#    Then he should see user profile
#    When he accesses admin users
#    Then he should see users list associated to selected company
#    When Tester wants to create a new user:
#      | displayName             | email                               | password | role      | adminRight | TilelyticsAccess | PUAssociations | activeUser | factory |
#      | 3 OperationWithoutAdmin | OperationWithoutAdmin@worximity.com | Test1234 | Operation | false      | true             | All            | true       | -       |
#    Then he should see new created user displayed in user list with correct information
#
#

#  Scenario: Tile+ admin->user - create new user role "Display"
#    Given Tester is accessing TilePlus
#    When he logins as default "Management"
#    Then he should see user profile
#    When he accesses admin users
#    Then he should see users list associated to selected company
#    When Tester wants to create a new user:
#      | displayName          | email                     | password | role    | adminRight | TilelyticsAccess | PUAssociations | activeUser | factory |
#      | 4 DisplayDefaultName | DisplayTest@worximity.com | Test1234 | Display | -          | -                | All            | true       | -       |
#    Then he should see new created user displayed in user list with correct information
#
#

#  Scenario: Tile+ admin->user - create user, then update, then delete
#    Given Tester is accessing TilePlus
#    When he logins as default "Management"
#    When he accesses admin users
#    Then he should see users list associated to selected company
#    When Tester wants to create a new user:
#      | displayName                | email                                   | password | role      | adminRight | TilelyticsAccess | PUAssociations | activeUser | factory |
#      | Temp OperationWithoutAdmin | TempOperationWithoutAdmin@worximity.com | Test1234 | Operation | false      | true             | All            | true       | -       |
#    When he searches user "TempOperationWithoutAdmin@worximity.com"
#    When he updates identified by email user with data:
#      | displayName                       | email                                   | password | role       | adminRight | TilelyticsAccess | PUAssociations | activeUser | factory |
#      | 12 TempName OperationWithoutAdmin | TempOperationWithoutAdmin@worximity.com | -        | Management | -          | -                | -              | false      | -       |
#    Then he should see the user updated
#    When he deletes user by email "TempOperationWithoutAdmin@worximity.com"
#    Then he should see that user with email "TempOperationWithoutAdmin@worximity.com" is not displayed in users list


  @Skip
  Scenario: Tile+ admin->user - validate user role = display
    Given Tester is accessing TilePlus
    When he logins with "DisplayTest@worximity.com" and "Test1234"
    Then he should not see Admin in the sidebar
    When he logout

  @Skip
  Scenario: Tile+ admin->user - validate user role = operation without admin right
    Given Tester is accessing TilePlus
    When he logins with "OperationWithoutAdmin@worximity.com" and "Test1234"
    Then he should not see Admin in the sidebar
    When he logout

  @Skip
  Scenario: Tile+ admin->user - validate user role = operation with admin right
    Given Tester is accessing TilePlus
    When he logins with "OperationWithAdmin@worximity.com" and "Test1234"
    Then he should see Admin in the sidebar
    When he accesses admin users
    Then he should see that user is not editable
    When he logout

#    TODO :
#    1: Prevent any access to Tilelytics if the account does not enable it
#    PR : https://dev.azure.com/Worximity/TileBoard-NextGen/_git/TilePlus-UI/pullrequest/2152

