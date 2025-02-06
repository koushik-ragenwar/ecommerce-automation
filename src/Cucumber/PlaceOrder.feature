@tag
Feature: search the product and purchase the order

  Background:
    Given I landed on flipkart home page

  @tag2
  Scenario Outline: positive test case for search add to cart checkout functionality
    Given Search the <productname>
    When clicked on searched <productname>
    And checkout the <productname> and add to cart
    Then sucsessfully order placed!
    Examples:
      | productname |
