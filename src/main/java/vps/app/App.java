package vps.app;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;


public class App {

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

    static Connection getDatabaseConnection(String defualtJdbcUrl) throws URISyntaxException, SQLException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        String database_url = processBuilder.environment().get("DATABASE_URL");
        if (database_url != null) {

            URI uri = new URI(database_url);
            String[] hostParts = uri.getUserInfo().split(":");
            String username = hostParts[0];
            String password = hostParts[1];
            String host = uri.getHost();

            int port = uri.getPort();

            String path = uri.getPath();
            String url = String.format("jdbc:postgresql://%s:%s%s", host, port, path);

            return DriverManager.getConnection(url, username, password);

        }

        return DriverManager.getConnection(defualtJdbcUrl);

    }



    public static void main(String[] args) {
        Map<String, Integer> userCounterMap = new HashMap<>();

        try {

            staticFiles.location("/public"); // Static files

            port(getHerokuAssignedPort());

            get("/", (req, res) -> {

                Map<String, Object> dataMap = new HashMap<>();

                String username = req.cookie("username");

                if (username == null) {
                    res.redirect("/login");
                    return null;
                }

                Integer counter = userCounterMap.containsKey(username) ?  userCounterMap.get(username) : 0;

                dataMap.put("username", username);
                dataMap.put("counter", counter);

                return new ModelAndView(dataMap, "counter.hbs");

            }, new HandlebarsTemplateEngine());

            post("/increment", (req, res) -> {

                String username = req.cookie("username");

                if (username == null) {
                    res.redirect("/login");
                }

                if (!userCounterMap.containsKey(username)) {
                    userCounterMap.put(username, 0);
                }

                userCounterMap.put(username, userCounterMap.get(username) + 1);

                res.redirect("/");
                return null;

            });

            post("/decrement", (req, res) -> {

                String username = req.cookie("username");

                if (username == null) {
                    res.redirect("/login");
                }

                if (!userCounterMap.containsKey(username)) {
                    userCounterMap.put(username, 0);
                }

                userCounterMap.put(username, userCounterMap.get(username) - 1);

                if (userCounterMap.get(username) < 0) {
                    userCounterMap.put(username, 0);
                }



                res.redirect("/");
                return null;
            });


            post("/login", (req, res) -> {

                String username = req.queryParams("username");

                if (username == null || "".equals(username)) {
                    res.redirect("/login");
                } else {
                    res.cookie("username", username);
                    res.redirect("/");
                }

                return null;

            }, new HandlebarsTemplateEngine());

            get("/login", (req, res) -> {
                Map<String, Object> dataMap = new HashMap<>();
                return new ModelAndView(dataMap, "login.hbs");
            }, new HandlebarsTemplateEngine());

            get("/logout", (req, res) -> {

                res.removeCookie("username");

                res.redirect("/login");
                return null;
            }, new HandlebarsTemplateEngine());



        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
