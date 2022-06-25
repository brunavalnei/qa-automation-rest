Feature: POST Criar contrato

  Background: Caminho
    Given I have baseURI "https://api-de-tarefas.herokuapp.com"
    Given I use header
    * I use the route "/sessions"

#  @Pre_Req
#  @Positive
#  Scenario: status code 200: Login do usuário
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
#    Then Http response should be 200
#    * I save the response value "data.attributes.auth-token"


  @Positive
  Scenario: status code 201: Criar contato
    Given email
#    Given I have baseURI "https://api-de-tarefas.herokuapp.com"
#    Given I use header
#    * I use the route "/contacts"
#    * I send the body
#    """
#        {
#            "name": "bruno",
#            "last_name": "batista",
#            "email": "teste52@gmail.com",
#            "age": "28",
#            "phone": "21984759575",
#            "address": "Rua dois",
#            "state": "Minas Gerais",
#            "city": "Belo Horizonte"
#        }
#    """
#    Then Http response should be 200