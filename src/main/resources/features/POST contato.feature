Feature: POST contato

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
  Scenario: status code 201: Criar contato
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
  Scenario Outline: status code 422: <testDescription>
    Given I have baseURI "herokuapp"
    Given I have basePath "/contacts"
    * I send the body
    """
        {
            "name": "<name>",
            "last_name": "valnei",
            "email": "<email>",
            "age": "<age>",
            "phone": "11999999999",
            "address": "Rua dos testes",
            "state": "São Paulo",
            "city": "São Paulo"
        }
    """
    * I send the POST request
    Then I print the response
    Then Http response should be 422
    * The response JSON must "errors.<errors>" have as the string "<message>"

    Examples:
      | testDescription             | name  | email                 | age | errors | message                                  |
      | name em branco              |       | bruna.teste@gmail.com | 28  | name   | [não pode ficar em branco]               |
      | email em branco             | Bruna |                       | 28  | email  | [não pode ficar em branco, não é válido] |
      | email sem o nome do usuário | Bruna | @gmail.com            | 28  | email  | [não é válido]                           |
      | email sem o servidor        | Bruna | bruna.teste@          | 28  | email  | [não é válido]                           |
      | age em branco               | Bruna | bruna.teste@gmail.com |     | age    | [não é um número]                        |



