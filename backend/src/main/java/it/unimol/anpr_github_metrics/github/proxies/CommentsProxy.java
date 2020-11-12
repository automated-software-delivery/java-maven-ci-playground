package it.unimol.anpr_github_metrics.github.proxies;

import com.jcabi.github.Comment;
import com.jcabi.github.Comments;
import com.jcabi.github.Issue;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Simone Scalabrino.
 */
public class CommentsProxy implements Comments, Proxy<Comments> {
    private final IssueProxy issue;
    private final Comments origin;
    private final Map<Integer, CommentProxy> cachedComments;
    private Date lastCommentsUpdate;

    public CommentsProxy(IssueProxy issue, Comments origin) {
        this.issue = issue;
        this.origin = origin;
        this.lastCommentsUpdate = new Date(0);

        this.cachedComments = new HashMap<>();
    }

    @Override
    public Issue issue() {
        return this.issue;
    }

    @Override
    public Comment get(int number) {
        if (!this.cachedComments.containsKey(number))
            this.cachedComments.put(number, new CommentProxy(this.issue, this.origin.get(number)));

        return this.cachedComments.get(number);
    }

    @Override
    public Iterable<Comment> iterate(Date since) {
        for (Comment comment : this.origin.iterate(this.lastCommentsUpdate)) {
            this.cachedComments.put(comment.number(), new CommentProxy(this.issue, comment));
        }

        return this.cachedComments.values().stream().filter(commentProxy -> commentProxy.isAfter(since)).collect(Collectors.toList());
    }

    @Override
    public Comment post(String text) throws IOException {
        return this.origin.post(text);
    }

    @Override
    public Comments getOrigin() {
        return this.origin;
    }
}
