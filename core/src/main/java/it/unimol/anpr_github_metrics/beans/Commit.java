package it.unimol.anpr_github_metrics.beans;

import java.util.Collection;

/**
 * @author Simone Scalabrino.
 */
public class Commit {
    private String hash;
    private User author;
    private String message;
    private Collection<FileChange> changes;

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Collection<FileChange> getChanges() {
        return changes;
    }

    public void setChanges(Collection<FileChange> changes) {
        this.changes = changes;
    }

    public int getTotalAddedLines() {
        int totalAdded = 0;
        for (FileChange change : this.changes) {
            totalAdded += change.addedLines;
        }

        return totalAdded;
    }

    public int getTotalRemovedLines() {
        int totalRemoved = 0;
        for (FileChange change : this.changes) {
            totalRemoved += change.removedLines;
        }

        return totalRemoved;
    }

    public int getTotalChangedLines() {
        int totalChanged = 0;
        for (FileChange change : this.changes) {
            totalChanged += change.changedLines;
        }

        return totalChanged;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public static class FileChange {
        private String fileName;
        private int addedLines;
        private int removedLines;
        private int changedLines;

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public int getAddedLines() {
            return addedLines;
        }

        public void setAddedLines(int addedLines) {
            this.addedLines = addedLines;
        }

        public int getRemovedLines() {
            return removedLines;
        }

        public void setRemovedLines(int removedLines) {
            this.removedLines = removedLines;
        }

        public int getChangedLines() {
            return changedLines;
        }

        public void setChangedLines(int changedLines) {
            this.changedLines = changedLines;
        }
    }
}
