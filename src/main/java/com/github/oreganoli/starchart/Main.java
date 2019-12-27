package com.github.oreganoli.starchart;

public class Main {

    public static void main(String[] args) throws Exception {
        var repo = new StarRepository();
        repo.initialize();
        repo.conn.close();
    }
}
