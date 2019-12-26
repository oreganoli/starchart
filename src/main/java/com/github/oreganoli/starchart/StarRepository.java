package com.github.oreganoli.starchart;

public class StarRepository {
    public StarRepository() throws Exception {
        var dbUrl = System.getenv("STARCHART_DB_URL");
        if (dbUrl == null) {
            throw new Exception("The STARCHART_DB_URL environment variable must be set.");
        }
    }
}
