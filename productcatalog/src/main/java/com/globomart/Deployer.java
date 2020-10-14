package com.globomart;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;


public class Deployer {
	
  public static void deployVerticle(Class clazz) {
	  deployVerticle(clazz, new VertxOptions().setClustered(false), null);
  }


  public static void deployVerticle(Class clazz, VertxOptions options, DeploymentOptions
      deploymentOptions) {
	  deployVerticle(clazz.getName(), options, deploymentOptions);
  }


  public static void deployVerticle(String verticleID, VertxOptions options, DeploymentOptions deploymentOptions) {
    if (options == null) {
      // Default parameter
      options = new VertxOptions();
    }

    Consumer<Vertx> runner = vertx -> {
      try {
        if (deploymentOptions != null) {
          vertx.deployVerticle(verticleID, deploymentOptions);
        } else {
          vertx.deployVerticle(verticleID);
        }
      } catch (Throwable t) {
        t.printStackTrace();
      }
    };

      Vertx vertx = Vertx.vertx(options);
      runner.accept(vertx);
  }
}