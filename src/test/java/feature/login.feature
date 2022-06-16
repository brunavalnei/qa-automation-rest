Feature: POST login

  @Positive
  Scenario: Login
    Given I have url "https://api-de-tarefas.herokuapp.com"
#    * I have route "/sessions"
    * I use the body with email "batata@gmail.com" and password "123456"
#    * I send the POST request
    Then Http response should be 200
    * Save auth token