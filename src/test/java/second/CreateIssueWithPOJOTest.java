package second;

import utils.data.JiraPojoHelper;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;
import pojo.issue.Credentials;
import pojo.issue.Issue;
import utils.api.Authorization;

import static io.restassured.RestAssured.given;

public class CreateIssueWithPOJOTest {

  String projectId = "10508";
  String issueType = "10107";

  @Test
  public void createIssueTest() {
    ValidatableResponse response;

    /* test data and parameters */
    String summary = "Test Summary";
    String assignee = "Artur Piluck";

    Credentials credentialsPOJO = JiraPojoHelper.generateJSONForLogin(Authorization.username, Authorization.password);

    String sessionId = given().
        header("Content-Type", ContentType.JSON).
        body(credentialsPOJO).
        when().
        post("/rest/auth/1/session").
        then().
        statusCode(200).contentType(ContentType.JSON).
        extract().
        path("session.value");

    System.out.printf("\nSESSION: " + sessionId);

    // подготавлияваем тестовые данные в формате JSON
    Issue issuePOJO = JiraPojoHelper.generateJSONForIssue(projectId, summary, issueType, assignee);

    response = given().
        header("Content-Type", ContentType.JSON).
        header("Cookie", "JSESSIONID=" + Authorization.JSESSIONID).
        body(issuePOJO).
        when().
        post("/rest/api/2/issue").
        then().
        statusCode(201).contentType(ContentType.JSON);

    String responseBody = response.extract().asString();
    System.out.printf("\nRESPONSE: " + responseBody);
    String issueKey = response.extract().path("key");

    /* get issue to confirm that it exists*/
    given().
        header("Content-Type", ContentType.JSON).
        header("Cookie", "JSESSIONID=" + Authorization.JSESSIONID).
        body(issuePOJO).
        when().
        get("/rest/api/2/issue/" + issueKey).
        then().
        statusCode(200).contentType(ContentType.JSON);

    /* delete issue */

    given().
        header("Content-Type", ContentType.JSON).
        header("Cookie", "JSESSIONID=" + Authorization.JSESSIONID).
        body(issuePOJO).
        when().
        delete("/rest/api/2/issue/" + issueKey).
        then().
        statusCode(204).contentType(ContentType.JSON);

    /* confirm that you will get 404 error when you will try to get the issue */
    given().
        header("Content-Type", ContentType.JSON).
        header("Cookie", "JSESSIONID=" + Authorization.JSESSIONID).
        body(issuePOJO).
        when().
        get("/rest/api/2/issue/" + issueKey).
        then().
        statusCode(404).contentType(ContentType.JSON);
  }
}
