/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST;

import Entities.Joke;
import Facades.JokeFacade;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import java.util.concurrent.ExecutorService;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import util.EMF_Creator;

/**
 * REST Web Service
 *
 * @author Martin
 */
@Path("joke")
public class JokeResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/CA1",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);
    private static final JokeFacade FACADE = JokeFacade.Get(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Context
    private UriInfo context;

    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String hello(){
        return "{\"msg\":\"hello\"}";
    }
    
    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public String all() {
        List<Joke> j = FACADE.getAll();
        return GSON.toJson(j);
    }

    @GET
    @Path("pop")
    public void populate() {
        FACADE.populate();
    }

    @Path("vote")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String getById(@QueryParam("Joke") Joke J,
            @QueryParam("score") int Score) {
        double s = FACADE.vote(J, Score);
        return "{\"msg\":\"Joken har nu den scoren:" + s + "\"}";
    }

    private ExecutorService executorService = java.util.concurrent.Executors.newCachedThreadPool();

    @GET
    @Path("rand")
    @Produces(value = MediaType.APPLICATION_JSON)
    public void random(@Suspended final AsyncResponse asyncResponse) {
        executorService.submit(new Runnable() {
            public void run() {
                asyncResponse.resume(doRandom());
            }
        });
    }

    private String doRandom() {
        Joke j = FACADE.getRandom();
        return GSON.toJson(j);
    }

}
