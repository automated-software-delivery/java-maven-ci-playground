package it.unimol.anpr_github_metrics.services;

import com.jcabi.github.Github;
import com.jcabi.github.RtGithub;
import it.unimol.anpr_github_metrics.beans.Issue;
import it.unimol.anpr_github_metrics.beans.Repository;
import it.unimol.anpr_github_metrics.github.GithubException;
import it.unimol.anpr_github_metrics.github.IssueExtractor;
import it.unimol.anpr_github_metrics.github.IssueExtractorFactory;
import it.unimol.anpr_github_metrics.recommender.AssigneesRecommender;
import it.unimol.anpr_github_metrics.recommender.ClosedIssueException;
import it.unimol.anpr_github_metrics.recommender.RecommendedUser;
import org.json.JSONArray;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * This class provide Api for recommended issues assignments.
 *
 * @author Stefano Dalla Palma.
 */
@Path("/recommendations")
public class RecommenderApi {

    @GET
    @Path("/get-assignees-for-issues/{login-name}/{repository-name}/{issue-number}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecommendedUsers(@PathParam("repository-name") String repoPath, @PathParam("login-name") String loginName, @PathParam("issue-number") int issueNumber, @PathParam("token") String token, @Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(token);

            String repoName = loginName + "/" + repoPath;

            IssueExtractor issueFactory = IssueExtractorFactory.getInstance(github);
            Repository repository = new Repository();
            repository.setName(repoName);

            ArrayList<Issue> issues = new ArrayList<>(issueFactory.getOpenIssues(repository));
            issues.removeIf(issue -> issue.getNumber() != issueNumber);

            if (issues.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Not an open issue.").build();
            } else {
                AssigneesRecommender recommender = new AssigneesRecommender(github);
                ArrayList<RecommendedUser> recommendedUsers = recommender.getRecommendation(issues.get(0));
                JSONArray array = JSONConverter.recommendedUserToJSONArray(recommendedUsers);
                return Response.ok(array).build();
            }

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (GithubException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (ClosedIssueException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Not an open issue.").build();
        }
    }
}
