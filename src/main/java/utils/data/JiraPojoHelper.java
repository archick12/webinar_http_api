package utils.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.tools.javac.util.List;
import pojo.Pojo;
import pojo.issue.Credentials;
import pojo.issue.Fields;
import pojo.issue.Issue;
import utils.api.Authorization;

public class JiraPojoHelper {


  public static Credentials generateJSONForLogin(String userName, String password) {
    return new Credentials(Authorization.username, Authorization.password);
  }

  public static Issue generateJSONForIssue(String projectId, String summary, String issueType, String assignee) {

    Issue issuePOJO = new Issue(new Fields()
        .setProject(projectId)
        .setSummary(summary)
        .setAssignee(assignee)
        .setIssueType(issueType)
    );
    return issuePOJO;
  }

  public static String extractPOJO(Pojo pojo) {
    ObjectMapper mapper = new ObjectMapper();
    String result = null;
    try {
      result = mapper.writeValueAsString(pojo);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return result;
  }

}