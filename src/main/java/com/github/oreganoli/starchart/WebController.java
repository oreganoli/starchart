//package com.github.oreganoli.starchart;
//
//import com.google.gson.Gson;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//
//@Path
//public class WebController {
//    static StarRepository repo;
//    static Gson gson = new Gson();
//    static {
//        try {
//            repo = new StarRepository();
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.exit(-1);
//        }
//    }
//    public static void get_star_by_id() {
//        int id;
//        try {
//            id = ctx.pathInt("id");
//        } catch (Exception e) {
//            ctx.status(400);
//            ctx.json(new ErrWrapper(e));
//            return;
//        }
//        try {
//            var star = repo.read(id);
//            if (star == null) {
//                ctx.status(404);
//            }
//            ctx.json(star);
//        } catch (Exception e) {
//            ctx.status(500);
//            ctx.json(new ErrWrapper(e));
//        }
//    }
//    public void get_stars(RouteContext ctx) {
//        try {
//            var stars = repo.read_all();
//            ctx.json(stars);
//        } catch (Exception e) {
//            ctx.status(500);
//            ctx.json(new ErrWrapper(e));
//        }
//    }
//
//    @PostRoute("/search")
//    public void search_stars(Request req, RouteContext ctx) {
//        try {
//            var criteria = gson.fromJson(req.bodyToString(), Criteria.class);
//            ctx.json(repo.search(criteria));
//        } catch (Exception e) {
//            ctx.status(500);
//            ctx.json(new ErrWrapper(e));
//        }
//    }
//
//    @PutRoute("/upsert")
//    public void upsert(Request req, RouteContext ctx) {
//        try {
//            var star = gson.fromJson(req.bodyToString(), Star.class);
//            var id = repo.upsert(star);
//            ctx.json(repo.read(id));
//        } catch (AlreadyExistsException e) {
//            ctx.status(409);
//            ctx.json(new ErrWrapper(e));
//        } catch (IllegalArgumentException e) {
//            ctx.status(422);
//            ctx.json(new ErrWrapper(e));
//        } catch (Exception e) {
//            ctx.status(500);
//            ctx.json(new ErrWrapper(e));
//        }
//    }
//
//    @DeleteRoute("/delete/:id")
//    public void delete(RouteContext ctx) {
//        int id;
//        try {
//            id = ctx.pathInt("id");
//        } catch (Exception e) {
//            ctx.status(422);
//            ctx.json(new ErrWrapper(e));
//            return;
//        }
//        try {
//            repo.delete(id);
//            ctx.json(id);
//        } catch (Exception e) {
//            ctx.status(500);
//            ctx.json(new ErrWrapper(e));
//        }
//    }
//
//}
