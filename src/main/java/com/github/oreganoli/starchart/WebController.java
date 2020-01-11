package com.github.oreganoli.starchart;

import com.blade.mvc.RouteContext;
import com.blade.mvc.annotation.*;
import com.blade.mvc.http.Request;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Path
public class WebController {
    static StarRepository repo;
    static Gson gson = new Gson();
    static String indexPage;
    static {
        try {
            indexPage = Files.readString(Paths.get("static/index.html"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            repo = new StarRepository();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @GetRoute("/stars/:id")
    public void get_star_by_id(RouteContext ctx) {
        int id;
        try {
            id = ctx.pathInt("id");
        } catch (Exception e) {
            ctx.status(400);
            ctx.json(new ErrWrapper(e));
            return;
        }
        try {
            var star = repo.read(id);
            if (star == null) {
                ctx.status(404);
            }
            ctx.json(star);
        } catch (Exception e) {
            ctx.status(500);
            ctx.json(new ErrWrapper(e));
        }
    }

    @GetRoute("/stars")
    public void get_stars(RouteContext ctx) {
        try {
            var stars = repo.read_all();
            ctx.json(stars);
        } catch (Exception e) {
            ctx.status(500);
            ctx.json(new ErrWrapper(e));
        }
    }

    @PostRoute("/search")
    public void search_stars(Request req, RouteContext ctx) {
        try {
            var criteria = gson.fromJson(req.bodyToString(), Criteria.class);
            ctx.json(repo.search(criteria));
        } catch (Exception e) {
            ctx.status(500);
            ctx.json(new ErrWrapper(e));
        }
    }

    @PutRoute("/upsert")
    public void upsert(Request req, RouteContext ctx) {
        try {
            var star = gson.fromJson(req.bodyToString(), Star.class);
            var id = repo.upsert(star);
            ctx.json(repo.read(id));
        } catch (AlreadyExistsException e) {
            ctx.status(409);
            ctx.json(new ErrWrapper(e));
        } catch (IllegalArgumentException e) {
            ctx.status(422);
            ctx.json(new ErrWrapper(e));
        } catch (Exception e) {
            ctx.status(500);
            ctx.json(new ErrWrapper(e));
        }
    }

    @DeleteRoute("/delete/:id")
    public void delete(RouteContext ctx) {
        int id;
        try {
            id = ctx.pathInt("id");
        } catch (Exception e) {
            ctx.status(422);
            ctx.json(new ErrWrapper(e));
            return;
        }
        try {
            repo.delete(id);
            ctx.json(id);
        } catch (Exception e) {
            ctx.status(500);
            ctx.json(new ErrWrapper(e));
        }
    }

    @GetRoute("/static/:path")
    public void static_serve(RouteContext ctx) {
        String path = "";
        try {
            path = ctx.pathString("path");
        } catch (Exception e) {
            ctx.status(404);
            return;
        }
        String content = "";
        try {
            content = Files.readString(Paths.get(String.format("static/%s", path)));
        } catch (Exception e) {
            ctx.status(404);
            return;
        }
        ctx.text(content);
    }

    @GetRoute("/js/:path")
    public void static_js(RouteContext ctx) {
        String path = "";
        try {
            path = ctx.pathString("path");
        } catch (Exception e) {
            ctx.status(404);
            return;
        }
        String content = "";
        try {
            content = Files.readString(Paths.get(String.format("static/js/%s", path)));
        } catch (Exception e) {
            ctx.status(404);
            return;
        }
        ctx.text(content).contentType("text/javascript").header("Content-Type", "text/javascript");
    }

    @GetRoute("/js/web_modules/:path")
    public void static_js_mod(RouteContext ctx) {
        String path = "";
        try {
            path = ctx.pathString("path");
        } catch (Exception e) {
            ctx.status(404);
            return;
        }
        String content = "";
        try {
            content = Files.readString(Paths.get(String.format("static/js/web_modules/%s", path)));
        } catch (Exception e) {
            ctx.status(404);
            return;
        }
        ctx.text(content).contentType("text/javascript").header("Content-Type", "text/javascript");
    }


    @GetRoute("/")
    public void index(RouteContext ctx) {
        ctx.html(indexPage);
    }

}
