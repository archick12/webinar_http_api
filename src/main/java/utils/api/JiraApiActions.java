package utils.api;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.testng.Assert;
import pojo.issue.Issue;

/**
 * Wrapper class for all possible actions in JIRA API
 */
public class JiraApiActions {

  public static String createIssue(Issue issuePOJO) {
    ValidatableResponse response = HTTPMethods.post(APIPathes.issue, issuePOJO);
    Assert.assertEquals(response.extract().statusCode(), 201);
    Assert.assertTrue(response.extract().contentType().contains(ContentType.JSON.toString()));
    return response.extract().path("key");
  }

  public static String getIssue(String issueKey) {
    ValidatableResponse response = HTTPMethods.get(APIPathes.issue + issueKey);
    Assert.assertEquals(response.extract().statusCode(), 200);
    Assert.assertTrue(response.extract().contentType().contains(ContentType.JSON.toString()));
    return response.extract().asString();
  }

  public static String getNonExistingIssue(String issueKey) {
    ValidatableResponse response = HTTPMethods.get(APIPathes.issue + issueKey);
    Assert.assertEquals(response.extract().statusCode(), 404);
    Assert.assertTrue(response.extract().contentType().contains(ContentType.JSON.toString()));
    return response.extract().asString();
  }

  public static void deleteIssue(String issueKey) {
    ValidatableResponse response = HTTPMethods.delete(APIPathes.issue + issueKey);
    Assert.assertEquals(response.extract().statusCode(), 204);
    Assert.assertTrue(response.extract().contentType().contains(ContentType.JSON.toString()));
  }

}
