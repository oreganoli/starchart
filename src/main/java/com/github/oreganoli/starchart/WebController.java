package com.github.oreganoli.starchart;

import com.blade.mvc.RouteContext;
import com.blade.mvc.annotation.GetRoute;
import com.blade.mvc.annotation.Path;

@Path
public class WebController {
    static int count = 0;

    @GetRoute("/")
    public void hello(RouteContext ctx) {
        count += 1;
        ctx.html(String.format("<h1>HELLO REQUEST NO. %d</h1>", count));
    }
}
