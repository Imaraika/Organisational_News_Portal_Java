package dao;

import model.Department;
import model.User;

import java.util.List;

public interface DepartmentDao {

    //read
   /* static List<Department> getAll();*/
    Department findById(int id);
    List<User> getAllUsersOfDepartments(int deparId);
    List<Department> getAllDepartmentNews(int newsId);
    //update
    void update(int id, String deptName, String deptDescription, int employees);

    //delete
    void deleteById(int id);
    void clearAll();

//create

    void add (Department department);
    void addDepartmentToUsers(Department department, User user);
}

