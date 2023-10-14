package com.esliph.todolist.modules.auth;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestMethod;

public class Path {
    RequestMethod method;
    String[] basePaths;
    String[] paths;

    public Path(RequestMethod method, String[] basePaths, String[] paths) {
        this.method = method;
        this.basePaths = basePaths;
        this.paths = paths;
    }

    public Path(RequestMethod method, String[] paths) {
        this.method = method;
        this.basePaths = new String[0];
        this.paths = paths;
    }

    public ArrayList<String> getFullPaths() {
        ArrayList<String> fullPaths = new ArrayList<>();

        for (String basePath : this.basePaths) {
            for (String path : this.paths) {
                fullPaths.add(basePath + path);
            }
        }

        return fullPaths;
    }

    public String getFullPath() {
        String fullPath = "";

        for (String basePath : this.basePaths) {
            for (String path : this.paths) {
                fullPath += basePath + path;
            }
        }

        return fullPath;
    }
}