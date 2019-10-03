import dao.Sql2oDepartmentDao;
import dao.Sql2oNewsDao;
import dao.Sql2oUserDao;
import dao.UserDao;
import org.sql2o.Sql2o;
import model.Department;
import model.News;
import model.User;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
//import com.google.gson.Gson;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import exceptions.ApiException;
import static spark.Spark.*;



public class App {

    public static void main(String[] args) {
        staticFileLocation("/public");
        Sql2oNewsDao newsDao;
        Sql2oDepartmentDao departmentDao;
        Sql2oUserDao userDao;

        get("/index", (req, res) -> {
            Map<String, Object> model = new HashMap<>();

            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        Connection conn;
        String connectionString = "jdbc:postgresql://localhost:5432/organisationnewsdb";
        Sql2o sql2o = new Sql2o(connectionString, "wecode", "1234");

        userDao = new Sql2oUserDao(sql2o);
        departmentDao = new Sql2oDepartmentDao(sql2o);
        newsDao = new Sql2oNewsDao(sql2o);

//get the first page
        get("/", (req, resp) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/success", (req, resp) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());
        //get: show a form to create a new department

        get("/departments/new", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return new ModelAndView(model, "depatment-form.hbs");
        }, new HandlebarsTemplateEngine());

        post("/departments/new", (req, res) -> { //new
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("nameofdepartment");
            String description = req.queryParams("descrptofdepartment");
            String numberofemployees = req.queryParams("numberofemployees");
            Department newDepartment = new Department(name, description, numberofemployees);
            model.put("newDepartment", newDepartment);
            newDepartment.save();
            res.redirect("/success");
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        get("/new", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            List<Department> departments = Sql2oDepartmentDao.getAll();
            model.put("departments", departments);
            return new ModelAndView(model, "News.hbs");
        }, new HandlebarsTemplateEngine());
        //get: show a form to create a new user

        get("/users/new", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            List<Department> departments = Sql2oDepartmentDao.getAll();
            model.put("departments", departments);
            return new ModelAndView(model, "employees-form.hbs");
        }, new HandlebarsTemplateEngine());

        post("/users/new", (req, res) -> { //new
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("name");
            String position = req.queryParams("position");
            String role = req.queryParams("role");
            String iddept = req.queryParams("iddept");

            User newUser = new User(name, position, role,iddept);
            model.put("newUser", newUser);
            newUser.save();
            res.redirect("/success");
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        get("/newEmp", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            List<User> users = Sql2oUserDao.getAll();
            model.put("departments", users);
            return new ModelAndView(model, "DataFromDatabase.hbs");
        }, new HandlebarsTemplateEngine());

        get("/News/new", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return new ModelAndView(model, "News-form.hbs");
        }, new HandlebarsTemplateEngine());

        post("/News/new", (req, res) -> { //new
            Map<String, Object> model = new HashMap<>();
            String newstitle = req.queryParams("newstitle");
            String content = req.queryParams("content");
            int iddept = Integer.parseInt(req.queryParams("iddept"));
            News newDepartment = new News(newstitle, content, iddept);
            model.put("newDepartment", newDepartment);
            newDepartment.save();
            res.redirect("/success");
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        get("/new", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            List<News> departments = Sql2oNewsDao.getAll();
            model.put("departments", departments);
            return new ModelAndView(model, "NewsfromDB.hbs");
        }, new HandlebarsTemplateEngine());
    }
}