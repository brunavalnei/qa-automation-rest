package stepDefinitions;


import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class LoginSteps {

    private static Response response;


    @Given("^I have url \"([^\"]*)\"$")
    public void iHaveUrl(String url) throws Throwable {
        RestAssured.baseURI = url;
    }

//    @Given("^I have route \"([^\"]*)\"$")
//    public void iHaveRoute(String basePath) throws Throwable {
//        response = request.basePath(basePath);
//        get
//        //        RestAssured.basePath = basePath;
//    }

    @Given("^I use the body with email \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void iUseTheBody(String email, String password) throws Throwable {
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("email", email);
        body.put("password", password);

        response = given()
                .body(body)
                .accept("application/vnd.api+json")
                .contentType("application/json")
                .when()
                .post("/sessions")
                .then().extract().response();
    }

//    @Given("^I send the POST request$")
//    public void iSendThePostRequest() throws Throwable {
//        response = (RequestSpecification) request.post("/sessions");
//    }

    @Then("^Http response should be (\\d+)$")
    public void httpResponseShouldBe(int statusCode) throws Throwable {
        Assert.assertEquals(statusCode, response.getStatusCode());
    }

    @Then("^Save auth token$")
    public void save_auth_token() throws Throwable {
        JsonPath jsonPathEvaluator = response.jsonPath();
        String authToken = jsonPathEvaluator.get("data.attributes.auth-token");
        System.out.println(authToken);
    }

    @Test
    @Given("^I have login and password$")
    public void iHaveLoginAndPassword() throws Throwable {
        String url = "https://api-de-tarefas.herokuapp.com";
        RestAssured.baseURI = url;
        RequestSpecification httpRequest = RestAssured.given();
        String email = "batata@gmail.com";
        String password = "123456";
        Response response = given()
                .relaxedHTTPSValidation()
                .accept("application/vnd.api+json")
                .contentType("application/json")
                .body("{\n" +
                        "  \"session\": {\n" +
                        "        \"email\": \"${email}\",\n" +
                        "        \"password\": \"${password}\"\n" +
                        "  }\n" +
                        "}")
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
