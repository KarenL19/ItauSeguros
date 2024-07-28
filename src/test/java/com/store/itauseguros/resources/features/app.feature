
Feature: implementing registration to calculate the price
  charged for an insurance product

  @Target
  Scenario: Product registration with calculated tariff
    Given be informed of the base price category and name
    When make the post request
    Then must return on screen the registered values in the base plus the tariffed price

  Scenario: Search for registered products
    Given iven I set the search parameters null
    When I call the API with the search parameters
    Then the response status code should be 200 if you do not have recorded records


  Scenario: It is necessary to update the value of the product
    Given there is a product registered with the following category "VIDA" basePrice 200.00 name "SEGURO DE VIDA"
    When update basePrice 300.00
    Then must return on screen the registered values in the base plus the tariffed price to updated