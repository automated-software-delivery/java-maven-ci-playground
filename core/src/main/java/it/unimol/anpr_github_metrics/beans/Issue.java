package it.unimol.anpr_github_metrics.beans;

import java.util.Collection;
import java.util.Date;

/**
 * @author Simone Scalabrino.
 */
public class Issue {
    public enum Label {
        BUG,
        ENHANCEMENT,
        HELP,
        QUESTION
    }

    private int number;

    private Repository repository;

    private String url;
    private String title;
    private String body;
    private User author;

    private Date createdTime;
    private Date updatedTime;
    private Date closedTime;

    @Deprecated
    private Date dueTime;

    private boolean locked;

    private boolean invalid;
    private boolean duplicated;

    private boolean closed;
    private boolean fixed;

    private Collection<Label> labels;
    private Collection<IssueComment> comments;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Date getClosedTime() {
        return closedTime;
    }

    public void setClosedTime(Date closedTime) {
        this.closedTime = closedTime;
    }

    public Date getDueTime() {
        return dueTime;
    }

    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isInvalid() {
        return invalid;
    }

    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
    }

    public boolean isDuplicated() {
        return duplicated;
    }

    public void setDuplicated(boolean duplicated) {
        this.duplicated = duplicated;
    }

    public Collection<IssueComment> getComments() {
        return comments;
    }

    public void setComments(Collection<IssueComment> comments) {
        this.comments = comments;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public Collection<Label> getLabels() {
        return labels;
    }

    public void setLabels(Collection<Label> labels) {
        this.labels = labels;
    }
}
