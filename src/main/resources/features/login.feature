Feature: POST login

  Background: Caminho
    Given I have baseURI "https://api-de-tarefas.herokuapp.com"
    Given I use header
    * I use the route "/sessions"


  @Positive
  Scenario: status code 200: Login do usuário
    * I send the body
    """
        {
            "session": {
                "email": "batata@gmail.com",
                "password": "123456"
            }
        }
    """
    * I send the POST request
    Then Http response should be 200
    * I save the response value "data.attributes.auth-token"


  @Negative
  Scenario: status code 401: Email incorreto
    * I send the body
    """
        {
            "session": {
                "email": "batata1@gmail.com",
                "password": ""
            }
        }
    """
    * I send the POST request
    * The response JSON must "errors" have as the string "Senha ou e-mail inválidos"



  @Negative
  Scenario Outline: status code 401 <testDescription>
    * I send the body
    """
        {
            "session": {
                "email": "<email>",
                "password": "<password>"
            }
        }
    """
    * I send the POST request
    * The response JSON must "errors" have as the string "Senha ou e-mail inválidos"

    Examples:
      | testDescription    | email             | password |
      | Email incorreto    | batata1@gmail.com | 123456   |
      | Password incorreta | batata@gmail.com  | 1234567  |
      | Email is blank     |                   | 123456   |
      | Password is blank  | batata@gmail.com  |          |
