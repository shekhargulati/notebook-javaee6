package com.openshift.notebook.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.openshift.notebook.domain.Notebook;

/**
 * JAX-RS Example
 * 
 * This class produces a RESTful service to read the contents of the members table.
 */
@Path("/notebooks")
@RequestScoped
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
      // Use @SupressWarnings to force IDE to ignore warnings about "genericizing" the results of
      // this query
      @SuppressWarnings("unchecked")
      // We recommend centralizing inline queries such as this one into @NamedQuery annotations on
      // the @Entity class
      // as described in the named query blueprint:
      // https://blueprints.dev.java.net/bpcatalog/ee5/persistence/namedquery.html
      final List<Notebook> results = em.createQuery("select m from Notebook m order by m.name").getResultList();
      return results;
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces("text/xml")
   public Notebook lookupNotebookById(@PathParam("id") long id) {
      return em.find(Notebook.class, id);
   }
}
