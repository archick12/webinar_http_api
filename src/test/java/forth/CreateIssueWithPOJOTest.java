package forth;

import utils.api.JiraApiActions;
import utils.data.JiraPojoHelper;
import org.testng.annotations.Test;
import pojo.issue.Issue;

public class CreateIssueWithPOJOTest {

  String projectId = "10508";
  String issueType = "10107";

  @Test
  public void createIssueTest() {
    /* test data and parameters */
    String summary = "Test Summary";
    String assignee = "Artur Piluck";

    Issue issuePOJO = JiraPojoHelper.generateJSONForIssue(projectId, summary, issueType, assignee);

    /* create issue */
    String issueKey = JiraApiActions.createIssue(issuePOJO);

    /* get issue to confirm that it exists*/
    JiraApiActions.getIssue(issueKey);

    /* delete issue */
    JiraApiActions.deleteIssue(issueKey);

    /* confirm that you will get 404 error when you will try to get the issue */
    JiraApiActions.getNonExistingIssue(issueKey);

  }
}
