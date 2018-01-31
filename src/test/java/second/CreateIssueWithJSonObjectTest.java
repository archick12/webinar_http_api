package second;

import utils.data.JiraJsonObjectHelper;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;
import utils.api.Authorization;

import static io.restassured.RestAssured.given;

public class CreateIssueWithJSonObjectTest {

    String projectId = "10508";
    String issueType = "10107";

    @Test
    public void issueCreateTest() {
        ValidatableResponse response;

    /* test data and parameters */
        String summary = "Test Summary";
        String assignee = "Artur Piluck";


        String loginJson = JiraJsonObjectHelper.generateJSONForLogin();

        String sessionId = given().
                header("Content-Type", "application/json").
                body(loginJson).
                when().
                post("/rest/auth/1/session").
                then().
                statusCode(200).contentType(ContentType.JSON).
                extract().
                path("session.value");

        System.out.printf("SESSION: " + sessionId);

        String issueJSON = JiraJsonObjectHelper.generateJSONForIssue(projectId, summary, issueType, assignee);
        response = given().
                header("Content-Type", "application/json").
                header("Cookie", "JSESSIONID=" + Authorization.JSESSIONID).
                body(issueJSON).
                when().
                post("/rest/api/2/issue").
                then().
                statusCode(201).contentType(ContentType.JSON);

        String responseBody = response.extract().asString();
        System.out.printf("\nRESPONSE: " + responseBody);
        String issueKey = response.extract().path("key");

    /* get issue to confirm that it exists*/
        given().
                header("Content-Type", "application/json").
                header("Cookie", "JSESSIONID=" + Authorization.JSESSIONID).
                body(issueJSON).
                when().
                get("/rest/api/2/issue/" + issueKey).
                then().
                statusCode(200).contentType(ContentType.JSON);

    /* delete issue */

        given().
                header("Content-Type", "application/json").
                header("Cookie", "JSESSIONID=" + Authorization.JSESSIONID).
                body(issueJSON).
                when().
                delete("/rest/api/2/issue/" + issueKey).
                then().
                statusCode(204).contentType(ContentType.JSON);

    /* confirm that you will get 404 error when you will try to get the issue */
        given().
                header("Content-Type", "application/json").
                header("Cookie", "JSESSIONID=" + Authorization.JSESSIONID).
                body(issueJSON).
                when().
                get("/rest/api/2/issue/" + issueKey).
                then().
                statusCode(404).contentType(ContentType.JSON);

    }
}

