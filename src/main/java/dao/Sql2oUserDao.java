package dao;

import model.Department;
import model.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oUserDao implements UserDao { //don't forget to shake hands with your interface!
    private final Sql2o sql2o;
    public Sql2oUserDao(Sql2o sql2o){ this.sql2o = sql2o; }

    @Override
    public void add(User users) {
        String sql = "INSERT INTO users (name, position, role) VALUES (:name, :position, :role)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(users)
                    .executeUpdate()
                    .getKey();
            users.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }


    public List<User> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM users")
                    .executeAndFetch(User.class);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from users WHERE id=:id";
        String delete = "DELETE from users WHERE uid = :UserId";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();

//            con.createQuery(deleteJoin)
//                    .addParameter("userId", id)
//                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from users";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void addUsersToDepartments(User users, Department departments){
        String sql = "INSERT INTO users_in_departments (departmentId, userId) VALUES (:deparId, :userId)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("deparId", departments.getId())
                    .addParameter("userId", users.getId())
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

//    @Override
//    public List<User> getall() {
//        return null;
//    }

//    @Override
//    public List<Department> getallDepartmentsForUsers(int id) {
//        return null;
//    }

    @Override
    public List<Department> getAllDepartmentsForUsers(int userId) {
        List<Department> departments = new ArrayList();
        String joinQuery = "SELECT departmentid FROM users_in_department WHERE userId = :userId";

        try (Connection con = sql2o.open()) {
            List<Integer> getallDepartmentsIds = con.createQuery(joinQuery)
                    .addParameter("userId", userId)
                    .executeAndFetch(Integer.class); //what is happening in the lines above?
            for (Integer deparId : getallDepartmentsIds){
                String departmentQuery = "SELECT * FROM departments WHERE id = :deparId";
                departments.add(
                        con.createQuery(departmentQuery)
                                .addParameter("depId", deparId)
                                .executeAndFetchFirst(Department.class));
            } //why are we doing a second sql query - set?
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
        return departments;
    }

    @Override
    public User findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM users WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(User.class);
        }
    }


}
