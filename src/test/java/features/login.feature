Feature: POST login

#  Background: Caminho
#    Given I have baseURI "https://api-de-tarefas.herokuapp.com"
#    Given I use header

#  @Positive
#  Scenario: status code 200: Login do usuário
#    * I use the body with email "batata@gmail.com" and password "123456"
#    * I send the POST request with endpoint "/sessions"
#    Then Http response should be 200
#    * I save the auth token

  @Negative
  Scenario Outline: status code 401 <testDescription>
    Given I have baseURI "https://api-de-tarefas.herokuapp.com"
    Given I use header
    * I use the body with email "<email>" and password "<password>"
    * I send the POST request with endpoint "/sessions"
    Then Http response should be 401
    * The response JSON must "errors" have as the string "Senha ou e-mail inválidos"

    Examples:
      | testDescription    | email             | password |
      | Email incorreto    | batata1@gmail.com | 123456   |
#      | Password incorreta | batata@gmail.com  | 1234567  |
#      | Email is blank     |                   | 123456   |
#      | Password is blank  | batata@gmail.com  |          |