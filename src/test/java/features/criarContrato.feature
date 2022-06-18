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


#  @Positive
#  Scenario: status code 200: Login do usuário
#    Given I have baseURI "https://api-de-tarefas.herokuapp.com"
#    Given I use header with accept "application/vnd.tasksmanager.v2"
#    * I use the body with email "batata@gmail.com" and password "123456"
#    * I send the POST request with endpoint "/contacts"
#    Then Http response should be 200
#    * I save the auth token