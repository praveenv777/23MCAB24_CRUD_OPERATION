package org.example.inventory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class MainVerticle extends AbstractVerticle {

    private MongoClient mongoClient;

    @Override
    public void start(Promise<Void> startPromise) {
        JsonObject config = new JsonObject()
                .put("connection_string", "mongodb://localhost:27017")
                .put("db_name", "stationery");

        mongoClient = MongoClient.createShared(vertx, config);

        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());
        router.post("/api/items").handler(this::addItem);
        router.get("/api/items/:itemId").handler(this::getItem);
        router.put("/api/items/:itemId").handler(this::updateItem);
        router.delete("/api/items/:itemId").handler(this::deleteItem);

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8888, http -> {
                    if (http.succeeded()) {
                        startPromise.complete();
                        System.out.println("HTTP server started on port 8888");
                    } else {
                        startPromise.fail(http.cause());
                    }
                });
    }

    private void addItem(io.vertx.ext.web.RoutingContext ctx) {
        JsonObject item = ctx.getBodyAsJson();
        mongoClient.save("items", item, res -> {
            if (res.succeeded()) {
                ctx.response()
                        .setStatusCode(201)
                        .end("Item added successfully with id " + res.result());
            } else {
                ctx.response()
                        .setStatusCode(500)
                        .end("Failed to add item");
            }
        });
    }

    private void getItem(io.vertx.ext.web.RoutingContext ctx) {
        String itemId = ctx.pathParam("itemId");
        mongoClient.findOne("items", new JsonObject().put("itemId", itemId), null, res -> {
            if (res.succeeded() && res.result() != null) {
                ctx.response()
                        .putHeader("content-type", "application/json")
                        .end(res.result().encodePrettily());
            } else {
                ctx.response()
                        .setStatusCode(404)
                        .end("Item not found");
            }
        });
    }

    private void updateItem(io.vertx.ext.web.RoutingContext ctx) {
        String itemId = ctx.pathParam("itemId");
        JsonObject update = ctx.getBodyAsJson();
        mongoClient.updateCollection("items", new JsonObject().put("itemId", itemId), new JsonObject().put("$set", update), res -> {
            if (res.succeeded()) {
                ctx.response()
                        .end("Item updated successfully");
            } else {
                ctx.response()
                        .setStatusCode(500)
                        .end("Failed to update item");
            }
        });
    }

    private void deleteItem(io.vertx.ext.web.RoutingContext ctx) {
        String itemId = ctx.pathParam("itemId");
        mongoClient.removeDocument("items", new JsonObject().put("itemId", itemId), res -> {
            if (res.succeeded()) {
                ctx.response()
                        .end("Item deleted successfully");
            } else {
                ctx.response()
                        .setStatusCode(500)
                        .end("Failed to delete item");
            }
        });
    }
}

