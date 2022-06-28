Feature: POST login

#
#  @Positive
#  Scenario: status code 200: Login do usuário
#    Given I have baseURI "herokuapp"
#    Given I have basePath "/sessions"
#    * I send the body
#    """
#        {
#            "session": {
#                "email": "batata@gmail.com",
#                "password": "123456"
#            }
#        }
#    """
#    * I send the POST request
#    Then I print the response
#    Then Http response should be 200
#    * I save the response value "data.attributes.auth-token"


  @Negative
  Scenario Outline: status code 401 <testDescription>
    Given I have baseURI "herokuapp"
    Given I have basePath "/sessions"
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
    Then I print the response
    Then Http response should be 401
    * The response JSON must "errors" have as the string "Senha ou e-mail inválidos"

    Examples:
      | testDescription    | email             | password |
      | Email incorreto    | batata1@gmail.com | 123456   |
      | Password incorreta | batata@gmail.com  | 1234567  |
      | Email is blank     |                   | 123456   |
      | Password is blank  | batata@gmail.com  |          |
