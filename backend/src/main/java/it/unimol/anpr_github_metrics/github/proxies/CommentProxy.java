package it.unimol.anpr_github_metrics.github.proxies;

import com.jcabi.github.Comment;
import com.jcabi.github.Issue;
import it.unimol.anpr_github_metrics.utils.DateUtils;

import javax.json.JsonObject;
import java.io.IOException;
import java.util.Date;

/**
 * @author Simone Scalabrino.
 */
public class CommentProxy implements Comment, Proxy<Comment> {
    private final IssueProxy issue;
    private final Comment origin;
    private Integer number;

    private Date date;
    private JsonObject storedJson;

    public CommentProxy(IssueProxy issue, Comment origin) {
        this.issue = issue;
        this.origin = origin;

        try {
            this.date = DateUtils.getMandatoryDate(this.json().getString("created_at"));
        } catch (IOException e) {
            this.date = new Date(0);
        }
    }

    @Override
    public Issue issue() {
        return this.issue;
    }

    @Override
    public int number() {
        if (this.number == null)
            this.number = this.origin.number();

        return this.number;
    }

    @Override
    public void remove() throws IOException {
        this.origin.remove();
    }

    @Override
    public void patch(JsonObject json) throws IOException {
        this.origin.patch(json);
    }

    @Override
    public JsonObject json() throws IOException {
        if (this.storedJson == null)
            this.storedJson = this.origin.json();

        return this.storedJson;
    }

    @Override
    public Comment getOrigin() {
        return this.origin;
    }

    @Override
    public int compareTo(Comment comment) {
        return this.origin.compareTo(comment);
    }

    public boolean isAfter(Date date) {
        return this.date.after(date);
    }
}
