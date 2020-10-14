package com.globomart.productprice;


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

public class ProductPriceService extends AbstractVerticle {


  public static void main(String[] args) {
    Deployer.deployVerticle(ProductPriceService.class);
  }

  private DB<String, Product> products = new JdbcDB();

  @Override
  public void start() {

    Router router = Router.router(vertx);

    router.route().handler(BodyHandler.create());
    router.get("/products/price/:productID").handler(this::handleGetPrice);

    vertx.createHttpServer().requestHandler(router::accept).listen(8080);
  }

  private void handleGetPrice(RoutingContext routingContext) {
    String productID = routingContext.request().getParam("productID");
    HttpServerResponse response = routingContext.response();
    if (productID == null) {
      sendError(400, response);
    } else {
    	Product product = products.get(productID);
      if (product == null) {
        sendError(404, response);
      } else {
        response.putHeader("content-type", "application/json").end("{productPrice:"+product.productPrice+"}");
      }
    }
  }


  private void sendError(int statusCode, HttpServerResponse response) {
    response.setStatusCode(statusCode).end();
  }
}