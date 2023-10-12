package com.esliph.todolist.modules.auth.decorator;

import java.util.ArrayList;

public class PathsWithAuthorization {
    static private ArrayList<String> paths = new ArrayList<String>();

    public static void addPath(String path) {
        PathsWithAuthorization.paths.add(path);
    }

    public static void addPaths(ArrayList<String> paths) {
        paths.forEach(path -> {
            PathsWithAuthorization.addPath(path);
        });
    }

    public static ArrayList<String> getPaths() {
        return PathsWithAuthorization.paths;
    }

    public static boolean hasPath(String path) {
        var paths = getPaths();

        for (int i = 0; i < paths.size(); i++) {
            if (paths.get(i).equals(path)) {
                return true;
            }
        }

        return false;
    }
}
