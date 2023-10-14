package com.esliph.todolist.modules.auth;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestMethod;

public class PathsWithAuthorization {
    static private ArrayList<Path> paths = new ArrayList<Path>();

    public static void addPath(Path path) {
        PathsWithAuthorization.paths.add(path);
    }

    public static void addPaths(ArrayList<Path> paths) {
        paths.forEach(path -> {
            PathsWithAuthorization.addPath(path);
        });
    }

    public static ArrayList<Path> getPaths() {
        return PathsWithAuthorization.paths;
    }

    public static boolean hasPath(String method, String path) {
        var _method = getMethodByString(method);

        for (var _path : paths) {
            if (!_path.method.equals(_method)) {
                continue;
            }

            var fullPath = _path.getFullPath();

            var hasEqual = verifyPathsEquals(fullPath, path);

            if (hasEqual) {
                return true;
            }
        }

        return false;
    }

    public static boolean verifyPathsEquals(String... paths) {
        for (int i = 0; i < paths.length - 1; i++) {
            var pathOne = paths[i];

            for (int j = i + 1; j < paths.length; j++) {
                var pathTwo = paths[j];

                var isEqual = verifyPathEqualsPath(pathOne, pathTwo);

                if (!isEqual) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean verifyPathEqualsPath(String pathOne, String pathTwo) {
        var pathOneInArr = pathOne.substring(1).split("/");
        var pathTwoInArr = pathTwo.substring(1).split("/");

        if (pathOneInArr.length != pathTwoInArr.length) {
            return false;
        }

        for (int i = 0; i < pathOneInArr.length; i++) {
            var argOne = pathOneInArr[i];
            var argTwo = pathTwoInArr[i];

            if (argOne.equals(argTwo)) {
                continue;
            }

            if ((argOne.substring(0, 1).equals("{")
                    && argOne.substring(argOne.length() - 1, argOne.length()).equals("}"))
                    || (argTwo.substring(0, 1).equals("{")
                            && argTwo.substring(argTwo.length() - 1, argTwo.length()).equals("}"))) {
                continue;
            }

            return false;
        }

        return true;
    }

    public static RequestMethod getMethodByString(String method) {
        try {
            return RequestMethod.resolve(method);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
