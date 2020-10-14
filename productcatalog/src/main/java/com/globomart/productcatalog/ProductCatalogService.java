package com.globomart.productcatalog;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.globomart.Deployer;
import com.globomart.db.DB;
import com.globomart.db.impl.JdbcDB;

public class ProductCatalogService extends AbstractVerticle {


  public static void main(String[] args) {
    Deployer.deployVerticle(ProductCatalogService.class);
  }

  private DB<String, Product> products = new JdbcDB();

  @Override
  public void start() {

    Router router = Router.router(vertx);

    router.route().handler(BodyHandler.create());
    router.get("/products/:productID").handler(this::handleGetProduct);
    router.put("/products").handler(this::handleAddProduct);
    router.get("/products").handler(this::handleListProducts);
    router.delete("/products/:productID").handler(this::handleDeleteProduct);

    vertx.createHttpServer().requestHandler(router::accept).listen(8080);
  }

  private void handleGetProduct(RoutingContext routingContext) {
    String productID = routingContext.request().getParam("productID");
    HttpServerResponse response = routingContext.response();
    if (productID == null) {
      sendError(400, response);
    } else {
    	Product product = products.get(productID);
      if (product == null) {
        sendError(404, response);
      } else {
        response.putHeader("content-type", "application/json").end(new JsonObject(Json.encode(product)).encodePrettily());
      }
    }
  }

  private void handleAddProduct(RoutingContext routingContext) {
    HttpServerResponse response = routingContext.response();
    JsonObject jsonProduct = routingContext.getBodyAsJson();
    Product product = Json.mapper.convertValue(jsonProduct.getMap(), Product.class);
    if (product == null) {
      sendError(400, response);
    } else {
      products.put(product);
      response.end();
    }
  }
  
  private void handleDeleteProduct(RoutingContext routingContext) {
	  String productID = routingContext.request().getParam("productID");
	  HttpServerResponse response = routingContext.response();
	  if (productID == null) {
		  sendError(400, response);
	  } else {
		  Product product = products.remove(productID);
		  if (product == null) {
			  sendError(404, response);
		  } else {
			  response.putHeader("content-type", "application/json").end(new JsonObject(Json.encode(product)).encodePrettily());
		  }
	  }
  }


  private void handleListProducts(RoutingContext routingContext) {
    JsonArray arr = new JsonArray();
    List<Product> allproducts = products.getAll();
    allproducts.forEach(v -> {arr.add(new JsonObject(Json.encode(v)));});
    routingContext.response().putHeader("content-type", "application/json").end(arr.encodePrettily());
  }

  private void sendError(int statusCode, HttpServerResponse response) {
    response.setStatusCode(statusCode).end();
  }
}