package it.unimol.anpr_github_metrics.services;

import it.unimol.anpr_github_metrics.beans.Issue;
import it.unimol.anpr_github_metrics.beans.IssueComment;
import it.unimol.anpr_github_metrics.beans.Repository;
import it.unimol.anpr_github_metrics.beans.User;
import it.unimol.anpr_github_metrics.recommender.RecommendedUser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;

/**
 * @author Stefano Dalla Palma
 */
public class JSONConverter {

    public static JSONArray issueCommentsToJSONArray(final Collection<IssueComment> comments) throws IllegalArgumentException {

        if(null == comments) throw new IllegalArgumentException("Null parameter");

        JSONArray array = new JSONArray();
        comments.forEach(comment -> array.put(issueCommentToJSONObject(comment)));
        return array;
    }

    public static JSONObject issueCommentToJSONObject(final IssueComment comment) throws IllegalArgumentException {

        if(null == comment) throw new IllegalArgumentException("Null parameter");

        JSONObject json = new JSONObject();

        json.put("author", userToJSONObject(comment.getAuthor()));
        json.put("body", comment.getBody());
        json.put("created_at", comment.getCreatedTime().getTime());
        json.put("updated_at", comment.getUpdatedTime().getTime());
        json.put("issue_number", comment.getIssue().getNumber());
        json.put("url", comment.getUrl());

        return json;
    }

    public static JSONArray issuesToJSONArray(final Collection<Issue> issues) throws IllegalArgumentException {

        if(null == issues) throw new IllegalArgumentException("Null parameter");

        JSONArray array = new JSONArray();
        issues.forEach(issue -> array.put(issueToJSONObject(issue)));

        return array;
    }

    public static JSONObject issueToJSONObject(final Issue issue) throws IllegalArgumentException {

        if(null == issue) throw new IllegalArgumentException("Null parameter");

        JSONObject json = new JSONObject();

        json.put("number", issue.getNumber());
        json.put("title", issue.getTitle());
        json.put("body", issue.getBody());
        json.put("created_at", issue.getCreatedTime().getTime());
        if (issue.isClosed()) json.put("closed_at", issue.getClosedTime().getTime());
        if (null != issue.getUpdatedTime()) json.put("updated_at", issue.getUpdatedTime().getTime());
        json.put("closed", issue.isClosed());
        json.put("fixed", issue.isFixed());
        json.put("duplicated", issue.isDuplicated());
        json.put("invalid", issue.isInvalid());
        json.put("locked", issue.isLocked());

        json.put("author", userToJSONObject(issue.getAuthor()));

        // A Json object representing only the name and url of the repository
        JSONObject lightRepo = new JSONObject();
        lightRepo.put("name", issue.getRepository().getName());
        lightRepo.put("url", issue.getRepository().getApiUrl());

        json.put("repository", lightRepo);

        json.put("comments", issueCommentsToJSONArray(issue.getComments()));
        json.put("labels", labelsToJSONArray(issue.getLabels()));

        return json;
    }


    public static JSONArray labelsToJSONArray(final Collection<Issue.Label> labels) throws IllegalArgumentException {

        if(null == labels) throw new IllegalArgumentException("Null parameter");

        JSONArray array = new JSONArray();
        labels.forEach(label -> array.put(label.toString()));
        return array;
    }

    public static JSONObject repositoryToJSONObject(final Repository repo) throws IllegalArgumentException {

        if(null == repo) throw new IllegalArgumentException("Null parameter");

        JSONObject json = new JSONObject();

        json.put("name", repo.getName());
        json.put("url", repo.getApiUrl());

        JSONArray issues = new JSONArray();
        repo.getIssues().forEach(issue -> issues.put(issueToJSONObject(issue)));
        json.put("issues", issues);

        JSONArray contributors = new JSONArray();
        repo.getContributors().forEach(contributor -> contributors.put(userToJSONObject(contributor)));
        json.put("contributors", contributors);

        return json;
    }

    public static JSONObject userToJSONObject(final User user) throws IllegalArgumentException {

        if(null == user) throw new IllegalArgumentException("Null parameter");

        JSONObject json = new JSONObject();
        json.put("login", user.getLogin());
        json.put("url", user.getUrl());
        return json;
    }


    public static JSONArray recommendedUserToJSONArray(final Collection<RecommendedUser> users) throws IllegalArgumentException {

        if(null == users) throw new IllegalArgumentException("Null parameter");

        JSONArray array = new JSONArray();
        users.forEach(user -> array.put(recommendedUserToJSONObject(user)));
        return array;
    }

    public static JSONObject recommendedUserToJSONObject(final RecommendedUser user) throws IllegalArgumentException {
        if(null == user) throw new IllegalArgumentException("Null parameter");

        JSONObject json = new JSONObject();
        json.put("user", userToJSONObject(user.getUser()));
        json.put("rank", user.getWeightedCoverage());
        return json;
    }
}