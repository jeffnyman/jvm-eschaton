Feature: Text Adventure Parser

  Background:
    Given the parser

  Scenario: Actions with just a verb
    When the action "inventory" is parsed
    Then the verb is recognized as "inventory"

  Scenario: Actions with a verb and direct object
    When the action "take lantern" is parsed
    Then the verb is recognized as "take"
    And  the direct object is recognized as "lantern"

  Scenario: Action with a verb and a direct object that has an article
    When the action "take the lantern" is parsed
    Then the verb is recognized as "take"
    And  the direct object article is recognized as "the"
    And  the direct object is recognized as "lantern"

  Scenario: Action with a verb and a direct object that has a modifier
    When the action "take brass lantern" is parsed
    Then the verb is recognized as "take"
    And  the direct object modifier is recognized as "brass"
    And  the direct object is recognized as "lantern"