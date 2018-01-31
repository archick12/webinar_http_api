package utils.api;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.apache.log4j.Logger;
import pojo.Pojo;
import utils.data.JiraPojoHelper;

import static io.restassured.RestAssured.given;

/**
 * Wrapper class for all possible actions in JIRA API
 */
public class HTTPMethods {

  static final Logger logger = Logger.getLogger(HTTPMethods.class);


  public static ValidatableResponse get(String urlPath) {
    ValidatableResponse response = given().
        header("Content-Type", ContentType.JSON).
        header("Cookie", "JSESSIONID=" + Authorization.JSESSIONID).
        when().
        get(urlPath).
        then();

    String responseBody = response.extract().asString();
    logger.info("RESPONSE URL: " + Authorization.BASE_URI + urlPath);
    logger.info("RESPONSE BODY: " + responseBody);

    return response;
  }

  public static ValidatableResponse post(String urlPath, Pojo body) {
    ValidatableResponse response = given().
        header("Content-Type", ContentType.JSON).
        header("Cookie", "JSESSIONID=" + Authorization.JSESSIONID).
        body(body).
        when().
        post(urlPath).
        then();

    String responseBody = response.extract().asString();
    logger.info("RESPONSE URL: " + Authorization.BASE_URI + urlPath);
    logger.info("RESPONSE BODY: " + responseBody);
    logger.info("REQUEST BODY: " + JiraPojoHelper.extractPOJO(body));

    return response;
  }

  public static ValidatableResponse delete(String urlPath) {
    ValidatableResponse response = given().
        header("Content-Type", ContentType.JSON).
        header("Cookie", "JSESSIONID=" + Authorization.JSESSIONID).
        when().
        delete(urlPath).
        then();

    String responseBody = response.extract().asString();
    logger.info("RESPONSE URL: " + Authorization.BASE_URI + urlPath);
    logger.info("RESPONSE BODY: " + responseBody);

    return response;
  }

}
