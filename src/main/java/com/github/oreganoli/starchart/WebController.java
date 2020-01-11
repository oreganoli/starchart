package com.github.oreganoli.starchart;


import io.javalin.http.Context;

public class WebController {
    static StarRepository repo;

    static {
        try {
            repo = new StarRepository();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static void get_all_stars(Context ctx) {
        try {
            var stars = repo.read_all();
            ctx.json(stars);
        } catch (Exception e) {
            ctx.status(500).json(new ErrWrapper(e));
        }
    }

    public static void get_star(Context ctx) {
        int id;
        try {
            id = Integer.parseInt(ctx.pathParam("id"));
        } catch (Exception e) {
            ctx.status(422).json(new ErrWrapper("NumberFormat", "The ID of a star must be an integer."));
            return;
        }
        try {
            var star = repo.read(id);
            if (star == null) {
                ctx.status(404).json(new ErrWrapper("NotFound", String.format("The star with the ID %d does not exist.", id)));
                return;
            }
            ctx.json(star);
        } catch (Exception e) {
            ctx.status(500).json(new ErrWrapper(e));
        }
    }

    public static void search(Context ctx) {
        Criteria criteria;
        try {
            criteria = ctx.bodyAsClass(Criteria.class);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(422).json(new ErrWrapper("MalformedRequest", "The request did not supply well-formed search criteria."));
            return;
        }
        try {
            var stars = repo.search(criteria);
            ctx.json(stars);
        } catch (Exception e) {
            ctx.status(500).json(new ErrWrapper(e));
        }
    }
}
