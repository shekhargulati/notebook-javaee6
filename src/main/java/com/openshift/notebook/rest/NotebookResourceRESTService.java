package com.openshift.notebook.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.openshift.notebook.domain.Notebook;

@Path("/notebooks")
@Stateless
public class NotebookResourceRESTService {
	
   @Inject
   private EntityManager em;
   
   @POST
   @Path("/create")
   @Consumes("text/xml")
   @Produces("text/xml")
   public Notebook createNewNotebook(Notebook notebook){
	   em.persist(notebook);
	   return notebook;
   }

   @GET
   @Path("/list")
   @Produces("text/xml")
   public List<Notebook> listAllNotebooks() {
      @SuppressWarnings("unchecked")
      final List<Notebook> results = em.createQuery("select m from Notebook m order by m.name").getResultList();
      return results;
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces("text/xml")
   public Notebook lookupNotebookById(@PathParam("id") long id) {
      return em.find(Notebook.class, id);
   }
   
   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteNotebook(@PathParam("id") Long id){
	   Notebook notebook = em.find(Notebook.class, id);
	   if(notebook == null){
		   return Response.status(Response.Status.NOT_FOUND).build();
	   }
	   
	   em.remove(notebook);
	   return Response.ok().build();
   }
}
