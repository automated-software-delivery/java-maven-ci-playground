package it.unimol.anpr_github_metrics.services;


import com.jcabi.github.Github;
import com.jcabi.github.RtGithub;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import it.unimol.anpr_github_metrics.analytics.*;
import it.unimol.anpr_github_metrics.beans.Issue;
import it.unimol.anpr_github_metrics.github.GithubException;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * This class of services handles the end-point api of services
 *
 * @author Stefano Dalla Palma.
 * <p>
 * The current working implemented api are the following:
 * - /average-closing-time
 * - /average-first-response-time
 * - /closing-time-distribution
 * - /commented-closed-issues
 * - /commented-fixed-issues
 * - /commented-open-issues
 * - /first-response-time-distribution
 * - /fixing-time-distribution
 * - /number-of-closed-issues
 * - /number-of-commented-closed-issues
 * - /number-of-commented-fixed-issues
 * - /number-of-commented-labeled-issues
 * - /number-of-commented-open-issues
 * - /number-of-fixed-issues
 * - /number-of-labeled-closed-issues
 * - /number-of-labeled-fixed-issues
 * - /number-of-labeled-open-issues
 * - /number-of-open-issues
 * - /number-of-uncommented-closed-issues
 * - /number-of-uncommented-fixed-issues
 * - /number-of-uncommented-open-issues
 * - /number-of-unlabeled-closed-issues
 * - /number-of-unlabeled-fixed-issues
 * - /number-of-unlabeled-open-issues
 * - /labeled-closed-issues
 * - /labeled-fixed-issues
 * - /labeled-open-issues
 * - /times-from-last-comment
 * - /uncommented-closed-issues
 * - /uncommented-fixed-issues
 * - /uncommented-open-issues
 * - /unlabeled-closed-issues
 * - /unlabeled-fixed-issues
 * - /unlabeled-open-issues
 */
@Path("/analytics")
public class AnalyticsApi {

    // ============================ AVERAGE CLOSING TIME ============================//
    @GET
    @Path("/average-closing-time/{login-name}/{repository-name}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAverageClosingTime(@PathParam("repository-name") String repoPath, @PathParam("login-name") String loginName, @PathParam("token") String token, @Context HttpServletRequest request) {

        try {
            //TODO: implementare cache system
            Github github = new RtGithub(token); //THOWS ASSERTION ERROR WHEN THE LOGIN ATTEMPT RESPONSE != 200

            String repoName = loginName + "/" + repoPath;
            ClosedIssuesAnalytics analytics = new ClosedIssuesAnalytics(github);

            Long averageClosingTime = analytics.getAverageClosingTime(repoName);
            return Response.ok(averageClosingTime).build();

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (GithubException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    // ============================ AVERAGE FIXING TIME ============================//
    @GET
    @Path("/average-fixing-time/{login-name}/{repository-name}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAverageFixingTime(@PathParam("repository-name") String repoPath, @PathParam("login-name") String loginName, @PathParam("token") String token, @Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(token); //THOWS ASSERTION ERROR WHEN THE LOGIN ATTEMPT RESPONSE != 200

            String repoName = loginName + "/" + repoPath;
            FixedIssuesAnalytics analytics = new FixedIssuesAnalytics(github);

            Long averageFixingTime = analytics.getAverageFixingTime(repoName);
            return Response.ok(averageFixingTime).build();

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (GithubException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    // ============================ AVERAGE FIRST RESPONSE TIME ============================//
    @GET
    @Path("/average-first-response-time/{login-name}/{repository-name}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAverageResponseTime(@PathParam("repository-name") String repoPath, @PathParam("login-name") String loginName, @PathParam("token") String token, @Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(token); //THOWS ASSERTION ERROR WHEN THE LOGIN ATTEMPT RESPONSE != 200

            String repoName = loginName + "/" + repoPath;
            IssuesAnalytics analytics = new IssuesAnalytics(github);

            Long averageFirstResponseTime = analytics.getAverageFirstResponseTime(repoName);
            return Response.ok(averageFirstResponseTime).build();

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (GithubException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    // ============================ AVERAGE TIME FROM LAST COMMENT ============================//
    @GET
    @Path("/average-time-from-last-comment/{login-name}/{repository-name}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAverageTimeFromLastComment(@PathParam("repository-name") String repoPath, @PathParam("login-name") String loginName, @PathParam("token") String token, @Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(token); //THOWS ASSERTION ERROR WHEN THE LOGIN ATTEMPT RESPONSE != 200

            String repoName = loginName + "/" + repoPath;
            IssuesAnalytics analytics = new IssuesAnalytics(github);

            Long averageTimeFromLastComment = analytics.getAverageTimeFromLastComment(repoName);
            return Response.ok(averageTimeFromLastComment).build();

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (GithubException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    // ============================ COMMENTED CLOSED ISSUES ============================//
    @GET
    @Path("/commented-closed-issues/{login-name}/{repository-name}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCommentedClosedIssues(@PathParam("repository-name") String repoPath, @PathParam("login-name") String loginName, @PathParam("token") String token, @Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(token); //THOWS ASSERTION ERROR WHEN THE LOGIN ATTEMPT RESPONSE != 200

            String repoName = loginName + "/" + repoPath;
            ClosedIssuesAnalytics analytics = new ClosedIssuesAnalytics(github);

            final ArrayList<Issue> commentedIssues = analytics.getCommentedClosedIssues(repoName);
            JSONArray json = JSONConverter.issuesToJSONArray(commentedIssues);
            return Response.ok(json.toString()).build();

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (GithubException | IllegalArgumentException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    // ============================ COMMENTED FIXED ISSUES ============================//
    @GET
    @Path("/commented-fixed-issues/{login-name}/{repository-name}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCommentedFixedIssues(@PathParam("repository-name") String repoPath, @PathParam("login-name") String loginName, @PathParam("token") String token, @Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(token); //THOWS ASSERTION ERROR WHEN THE LOGIN ATTEMPT RESPONSE != 200

            String repoName = loginName + "/" + repoPath;
            FixedIssuesAnalytics analytics = new FixedIssuesAnalytics(github);

            ArrayList<Issue> commentedIssues = analytics.getCommentedFixedIssues(repoName);
            JSONArray json = JSONConverter.issuesToJSONArray(commentedIssues);
            return Response.ok(json.toString()).build();

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (GithubException | IllegalArgumentException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    // ============================ COMMENTED OPEN ISSUES ============================//
    @GET
    @Path("/commented-open-issues/{login-name}/{repository-name}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCommentedOpenIssues(@PathParam("repository-name") String repoPath, @PathParam("login-name") String loginName, @PathParam("token") String token, @Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(token); //THOWS ASSERTION ERROR WHEN THE LOGIN ATTEMPT RESPONSE != 200

            String repoName = loginName + "/" + repoPath;
            OpenIssuesAnalytics analytics = new OpenIssuesAnalytics(github);

            ArrayList<Issue> commentedIssues = analytics.getCommentedOpenIssues(repoName);
            JSONArray json = JSONConverter.issuesToJSONArray(commentedIssues);
            return Response.ok(json.toString()).build();

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (GithubException | IllegalArgumentException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    // ============================ CLOSING TIME DISTRIBUTION ============================//
    @GET
    @Path("/closing-time-distribution/{login-name}/{repository-name}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTicketClosingTimeDistribution(@PathParam("repository-name") String repoPath, @PathParam("login-name") String loginName, @PathParam("token") String token, @Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(token); //THOWS ASSERTION ERROR WHEN THE LOGIN ATTEMPT RESPONSE != 200

            String repoName = loginName + "/" + repoPath;
            ClosedIssuesAnalytics analytics = new ClosedIssuesAnalytics(github);

            HashMap<Issue, Long> ticketClosingTimeDistribution = analytics.getClosingTimeDistribution(repoName);

            JSONArray json = new JSONArray();

            ticketClosingTimeDistribution.forEach((issue, time) -> {
                JSONObject obj = new JSONObject();
                obj.put("issue", JSONConverter.issueToJSONObject(issue));
                obj.put("closing_time", time);
                json.put(obj);
            });

            return Response.ok(json.toString()).build();

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (GithubException | IllegalArgumentException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    // ============================ FIRST RESPONSE TIME DISTRIBUTION ============================//
    @GET
    @Path("/first-response-time-distribution/{login-name}/{repository-name}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFirstResponseTimeDistribution(@PathParam("repository-name") String repoPath, @PathParam("login-name") String loginName, @PathParam("token") String token, @Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(token); //THOWS ASSERTION ERROR WHEN THE LOGIN ATTEMPT RESPONSE != 200

            String repoName = loginName + "/" + repoPath;
            IssuesAnalytics analytics = new IssuesAnalytics(github);

            HashMap<Issue, Long> firstResponseTimeDistribution = analytics.getFirstResponseTimeDistribution(repoName);

            JSONArray json = new JSONArray();

            firstResponseTimeDistribution.forEach((issue, responseTime) -> {
                JSONObject obj = new JSONObject();
                obj.put("issue", JSONConverter.issueToJSONObject(issue));
                obj.put("response_time", responseTime);
                json.put(obj);
            });

            return Response.ok(json.toString()).build();

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (GithubException | IllegalArgumentException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    // ============================ FIXING TIME DISTRIBUTION ============================//
    @GET
    @Path("/fixing-time-distribution/{login-name}/{repository-name}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFixingTimeDistribution(@PathParam("repository-name") String repoPath, @PathParam("login-name") String loginName, @PathParam("token") String token, @Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(token); //THOWS ASSERTION ERROR WHEN THE LOGIN ATTEMPT RESPONSE != 200

            String repoName = loginName + "/" + repoPath;
            FixedIssuesAnalytics analytics = new FixedIssuesAnalytics(github);

            HashMap<Issue, Long> fixingTimeDistribution = analytics.getFixingTimeDistribution(repoName);

            JSONArray json = new JSONArray();

            fixingTimeDistribution.forEach((issue, fixingTime) -> {
                JSONObject obj = new JSONObject();
                obj.put("issue", JSONConverter.issueToJSONObject(issue));
                obj.put("fixing_time", fixingTime);
                json.put(obj);
            });

            return Response.ok(json.toString()).build();

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (GithubException | IllegalArgumentException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    // ============================ LABELED CLOSED ISSUES ============================//
    @GET
    @Path("/labeled-closed-issues/{login-name}/{repository-name}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLabeledClosedIssues(@PathParam("repository-name") String repoPath, @PathParam("login-name") String loginName, @PathParam("token") String token, @Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(token); //THOWS ASSERTION ERROR WHEN THE LOGIN ATTEMPT RESPONSE != 200

            String repoName = loginName + "/" + repoPath;
            ClosedIssuesAnalytics analytics = new ClosedIssuesAnalytics(github);

            ArrayList<Issue> labeledIssues = analytics.getLabeledClosedIssues(repoName);
            JSONArray json = JSONConverter.issuesToJSONArray(labeledIssues);
            return Response.ok(json.toString()).build();

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (GithubException | IllegalArgumentException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    // ============================ LABELED FIXED ISSUES ============================//
    @GET
    @Path("/labeled-fixed-issues/{login-name}/{repository-name}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLabeledFixedIssues(@PathParam("repository-name") String repoPath, @PathParam("login-name") String loginName, @PathParam("token") String token, @Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(token); //THOWS ASSERTION ERROR WHEN THE LOGIN ATTEMPT RESPONSE != 200

            String repoName = loginName + "/" + repoPath;
            FixedIssuesAnalytics analytics = new FixedIssuesAnalytics(github);

            ArrayList<Issue> labeledIssues = analytics.getLabeledFixedIssues(repoName);
            JSONArray json = JSONConverter.issuesToJSONArray(labeledIssues);
            return Response.ok(json.toString()).build();

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (GithubException | IllegalArgumentException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ============================ LABELED OPEN ISSUES ============================//
    @GET
    @Path("/labeled-open-issues/{login-name}/{repository-name}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLabeledOpenIssues(@PathParam("repository-name") String repoPath, @PathParam("login-name") String loginName, @PathParam("token") String token, @Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(token); //THOWS ASSERTION ERROR WHEN THE LOGIN ATTEMPT RESPONSE != 200

            String repoName = loginName + "/" + repoPath;
            OpenIssuesAnalytics analytics = new OpenIssuesAnalytics(github);

            ArrayList<Issue> labeledIssues = analytics.getLabeledOpenIssues(repoName);
            JSONArray json = JSONConverter.issuesToJSONArray(labeledIssues);
            return Response.ok(json.toString()).build();

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (GithubException | IllegalArgumentException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    // ============================ NUMBER OF COMMENTED CLOSED ISSUES ============================//
    @GET
    @Path("/number-of-commented-closed-issues/{login-name}/{repository-name}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNumberOfCommentedClosedIssues(@PathParam("repository-name") String repoPath, @PathParam("login-name") String loginName, @PathParam("token") String token, @Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(token); //THOWS ASSERTION ERROR WHEN THE LOGIN ATTEMPT RESPONSE != 200

            String repoName = loginName + "/" + repoPath;
            ClosedIssuesAnalytics analytics = new ClosedIssuesAnalytics(github);

            int numberOfCommentedClosedIssues = analytics.getNumberOfCommentedClosedIssues(repoName);
            return Response.status(Response.Status.OK).entity(numberOfCommentedClosedIssues).build();

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (GithubException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    // ============================ NUMBER OF COMMENTED FIXED ISSUES ============================//
    @GET
    @Path("/number-of-commented-fixed-issues/{login-name}/{repository-name}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNumberOfCommentedFixedIssues(@PathParam("repository-name") String repoPath, @PathParam("login-name") String loginName, @PathParam("token") String token, @Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(token); //THOWS ASSERTION ERROR WHEN THE LOGIN ATTEMPT RESPONSE != 200

            String repoName = loginName + "/" + repoPath;
            FixedIssuesAnalytics analytics = new FixedIssuesAnalytics(github);

            int numberOfCommentedFixedIssues = analytics.getNumberOfCommentedFixedIssues(repoName);
            return Response.status(Response.Status.OK).entity(numberOfCommentedFixedIssues).build();

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (GithubException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    // ============================ NUMBER OF COMMENTED OPEN ISSUES ============================//
    @GET
    @Path("/number-of-commented-open-issues/{login-name}/{repository-name}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNumberOfCommentedOpenIssues(@PathParam("repository-name") String repoPath, @PathParam("login-name") String loginName, @PathParam("token") String token, @Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(token); //THOWS ASSERTION ERROR WHEN THE LOGIN ATTEMPT RESPONSE != 200

            String repoName = loginName + "/" + repoPath;
            OpenIssuesAnalytics analytics = new OpenIssuesAnalytics(github);

            int numberOfCommentedOpenIssues = analytics.getNumberOfCommentedOpenIssues(repoName);
            return Response.status(Response.Status.OK).entity(numberOfCommentedOpenIssues).build();

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (GithubException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    // ============================ NUMBER OF CLOSED ISSUES ============================//
    @GET
    @Path("/number-of-closed-issues/{login-name}/{repository-name}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNumberOfClosedIssues(@PathParam("repository-name") String repoPath, @PathParam("login-name") String loginName, @PathParam("token") String token, @Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(token); //THOWS ASSERTION ERROR WHEN THE LOGIN ATTEMPT RESPONSE != 200

            String repoName = loginName + "/" + repoPath;
            ClosedIssuesAnalytics analytics = new ClosedIssuesAnalytics(github);

            int numberOfClosedIssues = analytics.getNumberOfClosedIssues(repoName);
            return Response.status(Response.Status.OK).entity(numberOfClosedIssues).build();

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (GithubException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    // ============================ NUMBER OF FIXED ISSUES ============================//
    @GET
    @Path("/number-of-fixed-issues/{login-name}/{repository-name}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNumberOfFixedIssues(@PathParam("repository-name") String repoPath, @PathParam("login-name") String loginName, @PathParam("token") String token, @Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(token); //THOWS ASSERTION ERROR WHEN THE LOGIN ATTEMPT RESPONSE != 200

            String repoName = loginName + "/" + repoPath;
            FixedIssuesAnalytics analytics = new FixedIssuesAnalytics(github);

            int numberOfFixedIssues = analytics.getNumberOfFixedIssues(repoName);
            return Response.status(Response.Status.OK).entity(numberOfFixedIssues).build();

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (GithubException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    // ============================ NUMBER OF LABELED CLOSED ISSUES ============================//
    @GET
    @Path("/number-of-labeled-closed-issues/{login-name}/{repository-name}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNumberOfLabeledClosedIssues(@PathParam("repository-name") String repoPath, @PathParam("login-name") String loginName, @PathParam("token") String token, @Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(token); //THOWS ASSERTION ERROR WHEN THE LOGIN ATTEMPT RESPONSE != 200

            String repoName = loginName + "/" + repoPath;
            ClosedIssuesAnalytics analytics = new ClosedIssuesAnalytics(github);

            int numberOfLabeledClosedIssues = analytics.getNumberOfLabeledClosedIssues(repoName);
            return Response.status(Response.Status.OK).entity(numberOfLabeledClosedIssues).build();

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (GithubException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    // ============================ NUMBER OF LABELED FIXED ISSUES ============================//
    @GET
    @Path("/number-of-labeled-fixed-issues/{login-name}/{repository-name}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNumberOfLabeledFixedIssues(@PathParam("repository-name") String repoPath, @PathParam("login-name") String loginName, @PathParam("token") String token, @Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(token); //THOWS ASSERTION ERROR WHEN THE LOGIN ATTEMPT RESPONSE != 200

            String repoName = loginName + "/" + repoPath;
            FixedIssuesAnalytics analytics = new FixedIssuesAnalytics(github);

            int numberOfLabeledFixedIssues = analytics.getNumberOfLabeledFixedIssues(repoName);
            return Response.status(Response.Status.OK).entity(numberOfLabeledFixedIssues).build();

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (GithubException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    // ============================ NUMBER OF LABELED OPEN ISSUES ============================//
    @GET
    @Path("/number-of-labeled-open-issues/{login-name}/{repository-name}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNumberOfLabeledOpenIssues(@PathParam("repository-name") String repoPath, @PathParam("login-name") String loginName, @PathParam("token") String token, @Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(token); //THOWS ASSERTION ERROR WHEN THE LOGIN ATTEMPT RESPONSE != 200

            String repoName = loginName + "/" + repoPath;
            OpenIssuesAnalytics analytics = new OpenIssuesAnalytics(github);

            int numberOfLabeledOpenIssues = analytics.getNumberOfLabeledOpenIssues(repoName);
            return Response.status(Response.Status.OK).entity(numberOfLabeledOpenIssues).build();

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (GithubException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    // ============================ NUMBER OF OPEN ISSUES ============================//
    @GET
    @Path("/number-of-open-issues/{login-name}/{repository-name}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNumberOfOpenIssues(@PathParam("repository-name") String repoPath, @PathParam("login-name") String loginName, @PathParam("token") String token, @Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(token); //THOWS ASSERTION ERROR WHEN THE LOGIN ATTEMPT RESPONSE != 200

            String repoName = loginName + "/" + repoPath;
            OpenIssuesAnalytics analytics = new OpenIssuesAnalytics(github);

            int numberOfOpenIssues = analytics.getNumberOfOpenIssues(repoName);
            return Response.status(Response.Status.OK).entity(numberOfOpenIssues).build();

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (GithubException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    // ============================ NUMBER OF UNCOMMENTED CLOSED ISSUES ============================//
    @GET
    @Path("/number-of-uncommented-closed-issues/{login-name}/{repository-name}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNumberOfUncommentedClosedIssues(@PathParam("repository-name") String repoPath, @PathParam("login-name") String loginName, @PathParam("token") String token, @Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(token); //THOWS ASSERTION ERROR WHEN THE LOGIN ATTEMPT RESPONSE != 200

            String repoName = loginName + "/" + repoPath;
            ClosedIssuesAnalytics analytics = new ClosedIssuesAnalytics(github);

            int numberOfUncommentedClosedIssues = analytics.getNumberOfUncommentedClosedIssues(repoName);
            return Response.status(Response.Status.OK).entity(numberOfUncommentedClosedIssues).build();

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (GithubException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    // ============================ NUMBER OF UNCOMMENTED FIXED ISSUES ============================//
    @GET
    @Path("/number-of-uncommented-fixed-issues/{login-name}/{repository-name}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNumberOfUncommentedFixedIssues(@PathParam("repository-name") String repoPath, @PathParam("login-name") String loginName, @PathParam("token") String token, @Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(token); //THOWS ASSERTION ERROR WHEN THE LOGIN ATTEMPT RESPONSE != 200

            String repoName = loginName + "/" + repoPath;
            FixedIssuesAnalytics analytics = new FixedIssuesAnalytics(github);

            int numberOfUncommentedFixedIssues = analytics.getNumberOfUncommentedFixedIssues(repoName);
            return Response.status(Response.Status.OK).entity(numberOfUncommentedFixedIssues).build();

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (GithubException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    // ============================ NUMBER OF UNCOMMENTED OPEN ISSUES ============================//
    @GET
    @Path("/number-of-uncommented-open-issues/{login-name}/{repository-name}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNumberOfUncommentedOpenIssues(@PathParam("repository-name") String repoPath, @PathParam("login-name") String loginName, @PathParam("token") String token, @Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(token); //THOWS ASSERTION ERROR WHEN THE LOGIN ATTEMPT RESPONSE != 200

            String repoName = loginName + "/" + repoPath;
            OpenIssuesAnalytics analytics = new OpenIssuesAnalytics(github);

            int numberOfUncommentedOpenIssues = analytics.getNumberOfUncommentedOpenIssues(repoName);
            return Response.status(Response.Status.OK).entity(numberOfUncommentedOpenIssues).build();

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (GithubException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    // ============================ NUMBER OF UNLABELED CLOSED ISSUES ============================//
    @GET
    @Path("/number-of-unlabeled-closed-issues/{login-name}/{repository-name}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNumberOfUnlabeledClosedIssues(@PathParam("repository-name") String repoPath, @PathParam("login-name") String loginName, @PathParam("token") String token, @Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(token); //THOWS ASSERTION ERROR WHEN THE LOGIN ATTEMPT RESPONSE != 200

            String repoName = loginName + "/" + repoPath;
            ClosedIssuesAnalytics analytics = new ClosedIssuesAnalytics(github);

            int numberOfUnlabeledClosedIssues = analytics.getNumberOfUnlabeledClosedIssues(repoName);
            return Response.status(Response.Status.OK).entity(numberOfUnlabeledClosedIssues).build();

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (GithubException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    // ============================ NUMBER OF UNLABELED FIXED ISSUES ============================//
    @GET
    @Path("/number-of-unlabeled-fixed-issues/{login-name}/{repository-name}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNumberOfUnlabeledFixedIssues(@PathParam("repository-name") String repoPath, @PathParam("login-name") String loginName, @PathParam("token") String token, @Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(token); //THOWS ASSERTION ERROR WHEN THE LOGIN ATTEMPT RESPONSE != 200

            String repoName = loginName + "/" + repoPath;
            FixedIssuesAnalytics analytics = new FixedIssuesAnalytics(github);

            int numberOfUnlabeledFixedIssues = analytics.getNumberOfUnlabeledFixedIssues(repoName);
            return Response.status(Response.Status.OK).entity(numberOfUnlabeledFixedIssues).build();

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (GithubException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    // ============================ NUMBER OF UNLABELED OPEN ISSUES ============================//
    @GET
    @Path("/number-of-unlabeled-open-issues/{login-name}/{repository-name}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNumberOfUnlabeledOpenIssues(@PathParam("repository-name") String repoPath, @PathParam("login-name") String loginName, @PathParam("token") String token, @Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(token); //THOWS ASSERTION ERROR WHEN THE LOGIN ATTEMPT RESPONSE != 200

            String repoName = loginName + "/" + repoPath;
            OpenIssuesAnalytics analytics = new OpenIssuesAnalytics(github);

            int numberOfUnlabeledOpenIssues = analytics.getNumberOfUnlabeledOpenIssues(repoName);
            return Response.status(Response.Status.OK).entity(numberOfUnlabeledOpenIssues).build();

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (GithubException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    // ============================ TIMES FROM LAST COMMENT ============================//
    @GET
    @Path("/times-from-last-comment/{login-name}/{repository-name}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTimesFromLastComment(@PathParam("repository-name") String repoPath, @PathParam("login-name") String loginName, @PathParam("token") String token, @Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(token); //THOWS ASSERTION ERROR WHEN THE LOGIN ATTEMPT RESPONSE != 200

            String repoName = loginName + "/" + repoPath;
            IssuesAnalytics analytics = new IssuesAnalytics(github);

            HashMap<Issue, Long> issueTimes = analytics.getTimesFromLastComment(repoName);
            JSONArray json = new JSONArray();

            issueTimes.forEach((issue, timeFromLastComment) -> {
                JSONObject obj = new JSONObject();
                obj.put("issue", JSONConverter.issueToJSONObject(issue));
                obj.put("time_from_last_comment", timeFromLastComment);
                json.put(obj);
            });

            return Response.ok(json.toString()).build();

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (GithubException | IllegalArgumentException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    // ============================ UNCOMMENTED CLOSED ISSUES ============================//
    @GET
    @Path("/uncommented-closed-issues/{login-name}/{repository-name}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUncommentedClosedIssues(@PathParam("repository-name") String repoPath, @PathParam("login-name") String loginName, @PathParam("token") String token, @Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(token); //THOWS ASSERTION ERROR WHEN THE LOGIN ATTEMPT RESPONSE != 200

            String repoName = loginName + "/" + repoPath;
            ClosedIssuesAnalytics analytics = new ClosedIssuesAnalytics(github);

            ArrayList<Issue> issues = analytics.getUncommentedClosedIssues(repoName);
            JSONArray json = JSONConverter.issuesToJSONArray(issues);
            return Response.ok(json.toString()).build();

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (GithubException | IllegalArgumentException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    // ============================ UNCOMMENTED FIXED ISSUES ============================//
    @GET
    @Path("/uncommented-fixed-issues/{login-name}/{repository-name}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUncommentedFixedIssues(@PathParam("repository-name") String repoPath, @PathParam("login-name") String loginName, @PathParam("token") String token, @Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(token); //THOWS ASSERTION ERROR WHEN THE LOGIN ATTEMPT RESPONSE != 200

            String repoName = loginName + "/" + repoPath;
            FixedIssuesAnalytics analytics = new FixedIssuesAnalytics(github);

            ArrayList<Issue> issues = analytics.getUncommentedFixedIssues(repoName);
            JSONArray json = JSONConverter.issuesToJSONArray(issues);
            return Response.ok(json.toString()).build();

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (GithubException | IllegalArgumentException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    // ============================ UNCOMMENTED OPEN ISSUES ============================//
    @GET
    @Path("/uncommented-open-issues/{login-name}/{repository-name}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUncommentedOpenIssues(@PathParam("repository-name") String repoPath, @PathParam("login-name") String loginName, @PathParam("token") String token, @Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(token); //THOWS ASSERTION ERROR WHEN THE LOGIN ATTEMPT RESPONSE != 200

            String repoName = loginName + "/" + repoPath;
            OpenIssuesAnalytics analytics = new OpenIssuesAnalytics(github);

            ArrayList<Issue> issues = analytics.getUncommentedOpenIssue(repoName);
            JSONArray json = JSONConverter.issuesToJSONArray(issues);
            return Response.ok(json.toString()).build();

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (GithubException | IllegalArgumentException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    // ============================ UNLABELED CLOSED ISSUES ============================//
    @GET
    @Path("/unlabeled-closed-issues/{login-name}/{repository-name}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUnlabeledClosedIssues(@PathParam("repository-name") String repoPath, @PathParam("login-name") String loginName, @PathParam("token") String token, @Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(token); //THOWS ASSERTION ERROR WHEN THE LOGIN ATTEMPT RESPONSE != 200

            String repoName = loginName + "/" + repoPath;
            ClosedIssuesAnalytics analytics = new ClosedIssuesAnalytics(github);

            ArrayList<Issue> issues = analytics.getUnlabeledClosedIssues(repoName);
            JSONArray json = JSONConverter.issuesToJSONArray(issues);
            return Response.ok(json.toString()).build();

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (GithubException | IllegalArgumentException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    // ============================ UNLABELED FIXED ISSUES ============================//
    @GET
    @Path("/unlabeled-fixed-issues/{login-name}/{repository-name}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUnlabeledFixedIssues(@PathParam("repository-name") String repoPath, @PathParam("login-name") String loginName, @PathParam("token") String token, @Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(token); //THOWS ASSERTION ERROR WHEN THE LOGIN ATTEMPT RESPONSE != 200

            String repoName = loginName + "/" + repoPath;
            FixedIssuesAnalytics analytics = new FixedIssuesAnalytics(github);

            ArrayList<Issue> issues = analytics.getUnlabeledFixedIssues(repoName);
            JSONArray json = JSONConverter.issuesToJSONArray(issues);
            return Response.ok(json.toString()).build();

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (GithubException | IllegalArgumentException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    // ============================ UNLABELED OPEN ISSUES ============================//
    @GET
    @Path("/unlabeled-open-issues/{login-name}/{repository-name}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUnlabeledOpenIssues(@PathParam("repository-name") String repoPath, @PathParam("login-name") String loginName, @PathParam("token") String token, @Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(token); //THOWS ASSERTION ERROR WHEN THE LOGIN ATTEMPT RESPONSE != 200

            String repoName = loginName + "/" + repoPath;
            OpenIssuesAnalytics analytics = new OpenIssuesAnalytics(github);

            ArrayList<Issue> issues = analytics.getUnlabeledOpenIssues(repoName);
            JSONArray json = JSONConverter.issuesToJSONArray(issues);
            return Response.ok(json.toString()).build();

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (GithubException | IllegalArgumentException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    public String getLoginName(String token) throws UnirestException {
        JSONObject jsonRes = Unirest.get("https://api.github.com/user").queryString("access_token", token).asJson().getBody().getObject();
        return jsonRes.getString("login");
    }
}
