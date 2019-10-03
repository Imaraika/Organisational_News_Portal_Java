package dao;

import model.Department;
import model.User;

import java.util.List;

public interface UserDao {
    //create
    void add(User users);
    void addUsersToDepartments(User users, Department department);

    //read
  /*  List<User> getAll();*/
    List<Department>getAllDepartmentsForUsers(int id);

    //update
    //omit for now

    //delete
    void deleteById(int id);
    void clearAll();

    User findById(int userid);
}
