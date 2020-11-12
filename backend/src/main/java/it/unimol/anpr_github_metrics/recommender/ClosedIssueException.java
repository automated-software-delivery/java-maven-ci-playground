package it.unimol.anpr_github_metrics.recommender;

public class ClosedIssueException extends Exception {
    public ClosedIssueException() {
    }

    public ClosedIssueException(String message) {
        super(message);
    }
}
