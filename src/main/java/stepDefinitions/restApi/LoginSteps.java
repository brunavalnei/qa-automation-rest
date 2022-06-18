package stepDefinitions.restApi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
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

    static JsonBody JsonBody = new JsonBody();

    CustomerPojo customer = new CustomerPojo();
    private static Response response;
    private RequestSpecification request;

    Faker faker = new Faker();

    @Given("^I have baseURI \"([^\"]*)\"$")
    public void iHaveUrl(String url) throws Throwable {
        RestAssured.baseURI = url;
    }

    @Given("^I use the route \"([^\"]*)\"$")
    public void iUseTheRoute(String endpoint) throws Throwable {
        customer.setBaseUrl(endpoint);
    }

    @Given("^I use header$")
    public void iUseHeader() throws Throwable {
        request = given()
                .header("accept", "application/vnd.api+json")
                .header("Content-type", "application/json")
                .log()
                .headers();
    }


    @Given("^I send the POST request$")
    public void iSendThePostRequestTest() throws Throwable {
        response = request
                .when()
                .body(JsonBody.getJsonBodyString())
                .log()
                .body()
                .post(customer.getBaseUrl())
                .then().extract().response();
    }

    @Given("^I send the body$")
    public void setJsonBody(String jsonBodyT) throws Throwable {
        JsonBody.setJsonBodyString(JsonBody.replaceVariablesValues(jsonBodyT));
        try {
            ObjectMapper mapper = new ObjectMapper();
            Object json = mapper.readValue(JsonBody.getJsonBodyString(), Object.class);
            String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
            stepDefinitions.Hooks.scenario.write(prettyJson);
        } catch (Exception e) {
            Hooks.scenario.write(JsonBody.getJsonBodyString());
        }
    }

    @Then("^Http response should be (\\d+)$")
    public void httpResponseShouldBe(Integer statusCode) throws Throwable {
        Integer responseStatusCode = response.getStatusCode();
        Assert.assertEquals(responseStatusCode.toString(), statusCode.toString());
    }

    @Then("^The response JSON must \"([^\"]*)\" have as the string \"([^\"]*)\"$")
    public void theResponseMustHaveAsString(String key, String value) throws Throwable {
        JsonPath jsonPathEvaluator = response.jsonPath();
        String message = jsonPathEvaluator.get(key);
        Assert.assertEquals(message, value);
        Hooks.scenario.write(message);
    }


    @Then("^I save the response value \"([^\"]*)\"$")
    public void iSaveTheResponseValue(String responseValue) throws Throwable {
        JsonPath jsonPathEvaluator = response.jsonPath();
        String method = jsonPathEvaluator.get(responseValue);
        Hooks.scenario.write(method);
    }

    public static String loadVariable(String userKey){
        return JsonBody.getUserParameters().get("{ " + userKey + "}");
    }
    public void saveValue(String userKey) throws Throwable{
        loadVariable(userKey);
        Hooks.scenario.write(JsonBody.getUserParameters().get("{ " + userKey + "}"));
    }
    @Given("^email$")
    public void email() throws Throwable {

//        Hooks.scenario.write(customer.getEmail());
//        String email = null;
//        email = faker.internet().emailAddress();
//        customer.setEmail(email);
//        customer.getEmail();
////        System.out.println(email);
//        Hooks.scenario.write(customer.getEmail());


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
