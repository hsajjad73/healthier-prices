package com.healthierprices.poc;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/healthy")
public class RestfulServiceResource {

    @GET
    @Path("/products")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<?> products(@QueryParam("category") String catId) {
    	if(catId != null) {
    		return MongoDAO.getProductsByCategory(catId);
    	}else {
    		return MongoDAO.getAllProducts();
    	}
    }
    
    @GET
    @Path("/products/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<?> productById(@PathParam("id") String productId) {
        return MongoDAO.getProductById(productId);
    }
    
    @GET
    @Path("/products/count")
    @Produces(MediaType.APPLICATION_JSON)
    public long productsCount() {
        return MongoDAO.getProductsCount();
    }
}