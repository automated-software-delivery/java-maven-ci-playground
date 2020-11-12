package it.unimol.anpr_github_metrics.services;

import com.jcabi.github.Github;
import com.jcabi.github.RtGithub;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import it.unimol.anpr_github_metrics.github.Authenticator;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * @author Simone Scalabrino.
 */
@Path("/dev")
public class DevelopersApi {

    private static final String TEST_TOKEN = Authenticator.TEST;

    @GET
    @Path("/healthcheck")
    @Produces(MediaType.APPLICATION_JSON)
    public Response healthcheck(@Context HttpServletRequest request) {
        return Response.status(Response.Status.OK).entity("it works").build();
    }

    @GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response testLogin(@Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(TEST_TOKEN);

            if (!github.users().self().login().equals("")) {
                return Response.status(Response.Status.OK).entity(this.getUsername(TEST_TOKEN)).build();
            }

        } catch (IOException e) {
        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Login error").build();
        } catch (UnirestException e) {
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Test login not working").build();
    }

    @GET
    @Path("/quota")
    @Produces(MediaType.APPLICATION_JSON)
    public Response quota(@Context HttpServletRequest request) {

        try {
            Github github = new RtGithub(TEST_TOKEN);

            int limit = github.limits().get("core").json().getInt("remaining");
            return Response.status(Response.Status.OK).entity(limit).build();

        } catch (AssertionError e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Login error").build();

        } catch (IOException e) {

        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    public String getUsername(String token) throws UnirestException {
        JSONObject jsonRes = Unirest.get("https://api.github.com/user").queryString("access_token", token).asJson().getBody().getObject();
        return jsonRes.getString("login");
    }
}
