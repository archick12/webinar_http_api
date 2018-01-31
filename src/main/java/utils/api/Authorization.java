package utils.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.log4j.Logger;
import utils.data.JiraJsonObjectHelper;

import static io.restassured.RestAssured.given;

public class Authorization {

  public static String JSESSIONID;
  public static String BASE_URI = "http://jira.hillel.it:8080";
  public static String username = "webinar1";
  public static String password = "";
  static final Logger logger = Logger.getLogger(Authorization.class);

  public static void loginToJIRA() {
    RestAssured.baseURI = BASE_URI;

    String loginJson = JiraJsonObjectHelper.generateJSONForLogin();
    JSESSIONID =
        given().
            header("Content-Type", ContentType.JSON).
            body(loginJson).
            when().
            post(APIPathes.login).
            then().
            statusCode(200).contentType(ContentType.JSON).
            extract().
            path("session.value");

    logger.info("\nAUTHORIZATION TOKEN: " + Authorization.JSESSIONID);
  }
}
