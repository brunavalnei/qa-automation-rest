Feature: DELETE contrato

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
  Scenario: status code 204: Deletar contato
    Given I have baseURI "herokuapp"
    Given I have basePath "/contacts"
    * I send the body
    """
        {
            "name": "bruna",
            "last_name": "valnei",
            "email": "bruna.teste@gmail.com",
            "age": "28",
            "phone": "11999999999",
            "address": "Rua dos testes",
            "state": "São Paulo",
            "city": "São Paulo"
        }
    """
    * I send the POST request
    Then I print the response
    Then Http response should be 201


  #delete
    Given I have baseURI "herokuapp"
    * I have basePath with id "/contacts/"
    Given I send the DELETE request
    Then Http response should be 204


  @Negative
  Scenario: status code 404 - Contato não encontrado
    Given I have baseURI "herokuapp"
    Given I have basePath "/contacts/batata"
    Given I send the DELETE request
    Then Http response should be 404


