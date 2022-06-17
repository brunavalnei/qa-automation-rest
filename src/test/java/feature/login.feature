Feature: POST login

  @Positive
  Scenario: Login
    Given I have baseURI "https://api-de-tarefas.herokuapp.com"
    * I use header
    * I use the body with email "batata@gmail.com" and password "123456"
    * I send the POST request with endpoint "/sessions"
    Then Http response should be 200
    * I save the auth token