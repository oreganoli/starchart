package com.github.oreganoli.starchart;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.json.JavalinJson;

public class Main {
    public static void main(String[] args) throws Exception {
        Gson gson = new GsonBuilder().create();
        JavalinJson.setFromJsonMapper(gson::fromJson);
        JavalinJson.setToJsonMapper(gson::toJson);
        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("static", Location.EXTERNAL);
        }).start(9000);
        app.get("/stars", WebController::get_all_stars);
        app.get("/stars/:id", WebController::get_star);
        app.post("/stars", WebController::upsert);
        app.post("/search", WebController::search);
    }
}
