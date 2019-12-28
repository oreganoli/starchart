package com.github.oreganoli.starchart;

import com.blade.mvc.RouteContext;
import com.blade.mvc.annotation.GetRoute;
import com.blade.mvc.annotation.Path;

@Path
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
}
