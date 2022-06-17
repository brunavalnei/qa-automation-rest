package stepDefinitions;


import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.CustomerPojo;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class LoginSteps {

    CustomerPojo customer = new CustomerPojo();
    private static Response response;
    private RequestSpecification request;

    @Given("^I have baseURI \"([^\"]*)\"$")
    public void iHaveUrl(String url) throws Throwable {
        RestAssured.baseURI = url;
    }

    @Given("^I use header$")
    public void iUseHeader() throws Throwable {
        request = given()
                .header("accept", "application/vnd.api+json")
                .header("Content-type", "application/json")
                .log()
                .headers();
    }

    @Given("^I use the body with email \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void iUseTheBody(String email, String password) throws Throwable {
        customer.setEmailUser(email);
        customer.setPassword(password);
    }

    @Given("^I send the POST request with endpoint \"([^\"]*)\"$")
    public void iSendThePostRequest(String endpoint) throws Throwable {
        response = request
                .when()
                .body("{\n" +
                        "  \"session\": {\n" +
                        "        \"email\":\"" + customer.getEmailUser() + "\", " + "\n"+
                        "        \"password\": \"" + customer.getPassword()  + "\" " + "\n"+
                        "  }\n" +
                        "}")
                .log()
                .body()
                .post(endpoint)
                .then().extract().response();
    }

    @Then("^Http response should be (\\d+)$")
    public void httpResponseShouldBe(int statusCode) throws Throwable {
        Assert.assertEquals(statusCode, response.getStatusCode());
    }

    @Then("^I save the auth token$")
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
