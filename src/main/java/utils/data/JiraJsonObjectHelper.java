package utils.data;

import org.json.simple.JSONObject;
import utils.api.Authorization;

public class JiraJsonObjectHelper {


    public static String generateJSONForLogin() {
        JSONObject credentials = new JSONObject();
        credentials.put("username", Authorization.username);
        credentials.put("password", Authorization.password);

        return credentials.toJSONString();
    }

    public static String generateJSONForIssue(String projectId, String summary, String issueType, String assignee) {

        JSONObject issueData = new JSONObject();
        JSONObject fieldsJSONObject = new JSONObject();
        JSONObject projectJSONObject = new JSONObject();
        JSONObject issuetypeJSONObject = new JSONObject();
        JSONObject assigneeJSONObject = new JSONObject();

        projectJSONObject.put("id", projectId);
        issuetypeJSONObject.put("id", issueType);
        assigneeJSONObject.put("name", assignee);

        fieldsJSONObject.put("project", projectJSONObject);
        fieldsJSONObject.put("summary", summary);
        fieldsJSONObject.put("issuetype", issuetypeJSONObject);
        fieldsJSONObject.put("assignee", assigneeJSONObject);

        issueData.put("fields", fieldsJSONObject);

        return issueData.toString();
    }
}