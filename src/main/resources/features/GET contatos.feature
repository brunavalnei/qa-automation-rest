Feature: GET contatos

  @Pre_req
  Scenario: status code 200: Login do usuário
    Given I have baseURI "herokuapp"
    Given I have basePath "/sessions"
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
    Then I print the response
    Then Http response should be 200
    * I save the response value "data.attributes.auth-token"


  @Positive
  Scenario: status code 200: GET contatos
    Given I have baseURI "herokuapp"
    Given I have basePath "/contacts"
    * I send the GET request
    Then I print the response
    Then Http response should be 200





