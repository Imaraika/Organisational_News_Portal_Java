package dao;

import model.Department;
import model.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oDepartmentDao implements DepartmentDao {
    private static Sql2o sql2o;

    public Sql2oDepartmentDao(Sql2o sql2o){
        this.sql2o=sql2o;
        int id;

    }


    @Override
    public void add(Department departments) {
        String data="INSERT INTO departments(nameofdepartment,descrptofdepartment,numberofemployees)VALUES(:nameofdepartment, :descrptofdepartment, :numberofemployees);";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(data, true)
                    .bind(departments)
                    .executeUpdate()
                    .getKey();
            departments.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void addDepartmentToUsers(Department departments, User users) {

    }

    public static List<Department> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM departments")
                    .executeAndFetch(Department.class);
        }
    }


    @Override
    public List<User> getAllUsersOfDepartments(int deptId) {
        return null;
    }

    @Override
    public Department findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM departments WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Department.class);
        }
    }

    @Override
    public void deleteById(int id) {


    }

    @Override
    public List<Department> getAllDepartmentNews(int newsId) {
        String data = "SELECT * FROM news WHERE departmentId =:deptId";
        try(Connection con= sql2o.open()){
            return con.createQuery(data)
                    .addParameter("newsId", newsId)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Department.class);
        }

    }

    @Override
    public void update(int id, String deptName, String deptDescription, int employees) {
        String data="UPDATE departments SET (depName,depDescription,nbrEmployees)=(:depName,:depDescription,:nbrEmployees)";//CHECK!!!
        try (Connection con=sql2o.open()){
            con.createQuery(data)
                    .addParameter("deptName",deptName)
                    .addParameter("deptDescription",deptDescription)
                    .addParameter("Employees",employees)
                    .addParameter("id",id)
                    .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String data = "DELETE from restaurants";
        try (Connection con = sql2o.open()) {
            con.createQuery(data).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}
