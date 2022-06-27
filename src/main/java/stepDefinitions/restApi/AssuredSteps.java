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

    @Given("^I use header$")
    public void iUseHeader() throws Throwable {
        request = given()
                .header("accept", "application/vnd.api+json")
                .header("Content-type", "application/json")
                .log()
                .headers();
    }


    @Given("^I send the POST request$")
    public void ISendThePOSTRequestT() {
        response = given()
                .contentType("application/json")
                .body(JsonBody.getJsonBodyString())
                .post(HttpClient.getCompletePath())
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

    @Given("^I print the response$")
    public void iPrintTheResponse() throws Throwable {
        ResponseBody body = response.getBody();
        Hooks.scenario.write(body.asPrettyString());
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

    @Test
    @Given("^Sicred$")
    public void sicredi() throws Throwable {
        String url = "http://localhost:8090/api/v1";
        RestAssured.baseURI = url;
        RequestSpecification httpRequest = RestAssured.given();
        String email = "batata@gmail.com";
        String password = "123456";
        Response response = given()
                .relaxedHTTPSValidation()
                .accept("application/vnd.api+json")
                .contentType("application/json")
                .body("  {\n" +
                        "        \"nome\": \"" + faker.name().fullName() + "\", " + "\n" +
                        "        \"cpf\": \"" + generatorCpf + "\", " + "\n" +
                        "        \"email\": \"" + faker.internet().emailAddress() + "\", " + "\n" +
                        "        \"valor\": 1300,\n" +
                        "        \"parcelas\": 3,\n" +
                        "        \"seguro\": true\n" +
                        "    }")
                .when()
                .post("/simulacoes")
                .then()
                .statusCode(201)
                .and()
                .log().all().extract().response();

    }
}