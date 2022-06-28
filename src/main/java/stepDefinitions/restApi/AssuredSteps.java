package stepDefinitions.restApi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import common.utils.generator.DocumentsBrazil;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import httpClient.HttpClient;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import model.Customer;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.util.UriUtils;

import java.util.Locale;

import static io.restassured.RestAssured.given;
import static java.nio.charset.StandardCharsets.UTF_8;

public class AssuredSteps {

    Customer customer = new Customer();
    static JsonBody JsonBody = new JsonBody();

    public Response response;
    public RequestSpecification request;

    Faker faker = new Faker(new Locale("pt-BR"));
    String generatorCpf = new DocumentsBrazil().cpf();

    @Given("^I have baseURI \"([^\"]*)\"$")
    public void iHaveBaseURI(String baseUri) {
        Api.setDomain(baseUri);
    }

    @Given("^I have basePath \"([^\"]*)\"$")
    public void iHaveBasePath(String basePath) throws Throwable {
        Api.setPath(UriUtils.encodeQuery(JsonBody.replaceVariablesValues(basePath), UTF_8));
        Hooks.scenario.write(HttpClient.getCompletePath());
    }

    @Given("^I have basePath with id \"([^\"]*)\"$")
    public void iHaveBasePathId(String basePath) throws Throwable {
        Integer id = response.getBody().jsonPath().getInt("data.id");
        Api.setPath(UriUtils.encodeQuery(JsonBody.replaceVariablesValues(basePath + id), UTF_8));
        Hooks.scenario.write(HttpClient.getCompletePath());
    }

    @Given("^I save the variable \"([^\"]*)\" with value \"([^\"]*)\"$")
    public void saveValue(String userKey, String value) throws Throwable {
        Api.saveVariable(userKey, value);
        Hooks.scenario.write(Api.getUserParameters().get("${" + userKey + "}"));
    }

    @Given("^user informs email random$")
    public void emailRandom() throws Throwable {
        saveValue("email", faker.internet().emailAddress());
    }

    @Given("^I send the GET request$")
    public void iSendTheGet() {
        response = given()
                .contentType("application/json")
                .accept("application/vnd.tasksmanager.v2")
                .when()
                .get(HttpClient.getCompletePath())
                .then()
                .and()
                .log().all().extract().response();
    }

    @Given("^I send the POST request$")
    public void ISendThePOSTRequestT() {
        response = given()
                .contentType("application/json")
                .accept("application/vnd.tasksmanager.v2")
                .body(JsonBody.getJsonBodyString())
                .post(HttpClient.getCompletePath())
                .then()
                .and()
                .log().all().extract().response();
    }

    @Given("^I send the PUT request$")
    public void iSendThePutRequestTest() throws Throwable {
        response = given()
                .contentType("application/json")
                .accept("application/vnd.tasksmanager.v2")
                .when()
                .body(JsonBody.getJsonBodyString())
                .put(HttpClient.getCompletePath())
                .then().extract().response();
    }

    @Given("^I send the DELETE request$")
    public void ISendTheDELETERequest() {
        response = given()
                .contentType("application/json")
                .accept("application/vnd.tasksmanager.v2")
                .delete(HttpClient.getCompletePath())
                .then()
                .and()
                .log().all().extract().response();
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
        String message = jsonPathEvaluator.getString(key);
        Assert.assertEquals(message, value);
        Hooks.scenario.write(message);
    }

    @Then("^I save the response value \"([^\"]*)\"$")
    public void iSaveTheResponseValue(String responseValue) throws Throwable {
        JsonPath jsonPathEvaluator = response.jsonPath();
        String method = jsonPathEvaluator.get(responseValue);
        Hooks.scenario.write(method);
    }

    @Given("^I print the response$")
    public void iPrintTheResponse() throws Throwable {
        ResponseBody body = response.getBody();
        Hooks.scenario.write(body.asPrettyString());
    }

    @Test
    public void iHaveLoginAndPassword() throws Throwable {
        String url = "https://api-de-tarefas.herokuapp.com";
        RestAssured.baseURI = url;
        RequestSpecification httpRequest = RestAssured.given();
        Response response = given()
                .relaxedHTTPSValidation()
                .accept("application/vnd.api+json")
                .contentType("application/json")
                .body("{\n" +
                        "    \"name\": \"\",\n" +
                        "    \"last_name\": \"batista\",\n" +
                        "    \"email\": \"batatatatat@gmail.com\",\n" +
                        "    \"age\": \"28\",\n" +
                        "    \"phone\": \"21984759575\",\n" +
                        "    \"address\": \"Rua dois\",\n" +
                        "    \"state\": \"Minas Gerais\",\n" +
                        "    \"city\": \"Belo Horizonte\"\n" +
                        "}")
                .when()
                .post("/contacts")
                .then()
                .statusCode(422)
                .and()
                .log().all().extract().response();

        JsonPath jsonPathEvaluator = response.jsonPath();
        String message = jsonPathEvaluator.getString("errors.name");
//Assert.assertTrue("não pode ficar em branco", message);
        //        JsonPath jsonPathEvaluator = response.jsonPath();
//        String authToken = jsonPathEvaluator.get("errors/0/name");
        System.out.println(message);

    }

}