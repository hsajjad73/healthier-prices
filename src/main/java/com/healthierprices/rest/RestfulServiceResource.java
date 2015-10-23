package com.healthierprices.rest;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.healthierprices.data.ProductsDAO;

@Path("/healthy")
public class RestfulServiceResource {
	
	private ProductsDAO dao;
	
    @GET
    @Path("/products")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<?> products(@QueryParam("category") String catId) {
    	if(catId != null) {
    		return dao.getProductsByCategory(catId);
    	}else {
    		return dao.getAllProducts();
    	}
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