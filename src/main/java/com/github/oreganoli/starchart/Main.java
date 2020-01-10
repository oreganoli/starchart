package com.github.oreganoli.starchart;

import com.blade.Blade;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws Exception {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current relative path is: " + s);
        var repo = new StarRepository();
        Blade.of().addStatics("/static").start(WebController.class);
    }
}
