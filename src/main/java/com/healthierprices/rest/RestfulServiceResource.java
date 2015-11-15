package com.healthierprices.rest;

import java.util.Collection;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.healthierprices.data.ProductsDAO;

@Path("/healthy")
public class RestfulServiceResource {
	
	private ProductsDAO dao;
	
    @GET
    @Path("/products")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<?> products() {
    	return dao.getAllProducts();
    }

    @POST
    @Path("/products")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProduct(Map o) {
    	Object result = dao.insertProduct(o);
    	return Response.status(201).entity(result).build();
    }
    
    @GET
    @Path("/products/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<?> productById(@PathParam("id") String productId) {
        return dao.getProductById(productId);
    }
    
    @GET
    @Path("/products/count")
    @Produces(MediaType.APPLICATION_JSON)
    public long productsCount() {
        return dao.getProductsCount();
    }

	public ProductsDAO getDao() {
		return dao;
	}

	public void setDao(ProductsDAO dao) {
		this.dao = dao;
	}
    
    
}