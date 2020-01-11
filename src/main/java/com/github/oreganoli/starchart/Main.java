package com.github.oreganoli.starchart;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.json.JavalinJson;

public class Main {
    private static StarRepository repo;

    static {
        try {
            repo = new StarRepository();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static void main(String[] args) throws Exception {
        Gson gson = new GsonBuilder().create();
        JavalinJson.setFromJsonMapper(gson::fromJson);
        JavalinJson.setToJsonMapper(gson::toJson);
        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("static", Location.EXTERNAL);
        }).start(9000);
        app.get("/stars", ctx -> {
            var stars = repo.read_all();
            ctx.json(stars);
        });
        app.get("/stars/:id", ctx -> {
            int id;
            try {
                id = Integer.parseInt(ctx.pathParam("id"));
            } catch (Exception e) {
                ctx.status(422).json(new ErrWrapper("NumberFormat", "The ID of a star must be an integer."));
                return;
            }
            var star = repo.read(id);
            if (star == null) {
                ctx.status(404).json(new ErrWrapper("NotFound", String.format("The star with the ID %d does not exist.", id)));
                return;
            }
            ctx.json(star);
        });
    }
}
