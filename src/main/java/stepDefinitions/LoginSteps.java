package stepDefinitions;


import cucumber.api.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class LoginSteps {

    @Given("^I have login and password$")
    public void iHaveLoginAndPassword() throws Throwable {
        String url = "https://api-de-tarefas.herokuapp.com";
        RestAssured.baseURI = url;
        RequestSpecification httpRequest = RestAssured.given();
        Response response = given()
                .relaxedHTTPSValidation()
                .auth()
                .preemptive()
                .basic("batata@gmail.com", "123456")
                .body("{\n" +
                        "  \"session\": {\n" +
                        "        \"email\": \"batata@gmail.com\",\n" +
                        "        \"password\": \"123456\"\n" +
                        "  }\n" +
                        "}")
                .accept("application/vnd.api+json")
                .contentType("application/json")
                .when()
                .post("/sessions")
                .then()
                .statusCode(200)
                .and()
                .log().all().extract().response();

        JsonPath jsonPathEvaluator = response.jsonPath();
        String authToken = jsonPathEvaluator.get("data.attributes.auth-token");
        System.out.println(authToken);

    }
}
