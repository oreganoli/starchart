package com.github.oreganoli.starchart;

import com.blade.Blade;

public class Main {

    public static void main(String[] args) throws Exception {
        var repo = new StarRepository();
        Blade.of().start(WebController.class);
    }
}
