package it.unimol.anpr_github_metrics.recommender;

import com.jcabi.github.Github;
import it.unimol.anpr_github_metrics.beans.Commit;
import it.unimol.anpr_github_metrics.beans.Issue;
import it.unimol.anpr_github_metrics.beans.User;
import it.unimol.anpr_github_metrics.github.GithubException;
import it.unimol.anpr_github_metrics.github.IssueExtractorFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class provides an engine to provide user recommendation for a given issue
 * @author Code Warrior Team
 */
public class AssigneesRecommender {
    private Github github;
    private ArrayList<User> users;
    private ArrayList<Issue> fixedIssues;

    public AssigneesRecommender(Github github) {
        this.github = github;
    }

    public AssigneesRecommender(ArrayList<User> users, ArrayList<Issue> fixedIssues) {
        this.users = users;
        fixedIssues.removeIf(issue -> !issue.isClosed());
        this.fixedIssues = fixedIssues;
    }


    public AssigneesRecommender(Github github, ArrayList<User> users, ArrayList<Issue> fixedIssues) {
        this.github = github;
        this.users = users;
        fixedIssues.removeIf(issue -> !issue.isClosed());
        this.fixedIssues = fixedIssues;
    }

    /**
     * This method returns a list of recommended users ordered in descend order of ranking
     * @param issue the issue to which user recommendation is needed
     * @return an ArrayList of recommended users
     * @throws ClosedIssueException
     * @throws GithubException
     */
    public ArrayList<RecommendedUser> getRecommendation(Issue issue) throws ClosedIssueException, GithubException {

        ArrayList<RecommendedUser> recommendedUsers = new ArrayList<>();

        if (issue.isClosed()) {
            throw new ClosedIssueException("The issue is closed. Pass an opened issue");
        }

        HashMap<Issue, Double> similarityMap = new HashMap<>();
        HashMap<Issue, ArrayList<Commit>> issueCommitMap = new HashMap<>();
        double distance = 0;

        for (Issue fixed : fixedIssues) {
            double titleSimilarity = VSM.computeTextualSimilarity(issue.getTitle(), fixed.getTitle());
            double bodySimilarity = VSM.computeTextualSimilarity(issue.getBody(), fixed.getBody());
            double similarity = (titleSimilarity + bodySimilarity) / 2;   // 0 <= similarity <= 1
            distance += 1 - similarity;

            similarityMap.put(fixed, similarity);
            issueCommitMap.put(fixed, (ArrayList<Commit>) IssueExtractorFactory.getInstance(github).getCommitsInvolvedInIssue(fixed));
        }

        final double meanDistance = fixedIssues.isEmpty() ? 0 : distance / fixedIssues.size();

        //Filter out all the issues with distance greater then meanDistance
        similarityMap = similarityMap
                .entrySet()
                .stream()
                .filter(p -> (1 - p.getValue()) <= meanDistance)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));


        // Find user issues's coverage
        for (Map.Entry<Issue, Double> entry : similarityMap.entrySet()) {
            Issue key = entry.getKey();
            double similarity = entry.getValue();

            ArrayList<Commit> commits = issueCommitMap.get(key);
            HashMap<User, Double> userChanges = new HashMap<>();

            int totalChanges = 0;
            for (Commit commit : commits) {

                double value = commit.getTotalAddedLines() + commit.getTotalRemovedLines();

                if (userChanges.containsKey(commit.getAuthor())) {
                    value += userChanges.get(commit.getAuthor());
                }
                userChanges.put(commit.getAuthor(), value);
                totalChanges += value;
            }

            // Update user's coverage
            for (Map.Entry<User, Double> users : userChanges.entrySet()) {
                User user = users.getKey();

                double coverage = users.getValue() / totalChanges * similarity;

                RecommendedUser recommendedUser = new RecommendedUser(user);

                if (recommendedUsers.contains(recommendedUser)) {
                    recommendedUser = recommendedUsers.get(recommendedUsers.indexOf(recommendedUser));
                    recommendedUser.updateCoverage(coverage);
                    recommendedUser.updateWeight(similarity);
                } else {
                    recommendedUser.updateCoverage(coverage);
                    recommendedUser.updateWeight(similarity);
                    recommendedUsers.add(recommendedUser);
                }
            }
        }

        // Sort recommended list from desc order of ranking
        Collections.reverse(recommendedUsers);
        return recommendedUsers;
    }

}
