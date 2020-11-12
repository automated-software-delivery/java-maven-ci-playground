package it.unimol.anpr_github_metrics.beans;

import java.util.Date;

/**
 * @author Simone Scalabrino.
 */
public class IssueComment implements Comparable {
    private Issue issue;
    private String url;
    private String body;

    private User author;
    private Date createdTime;
    private Date updatedTime;

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public int compareTo(Object o) {
        return this.createdTime.compareTo(((IssueComment)o).createdTime);
    }
}
