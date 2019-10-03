package dao;

import model.News;

import java.util.List;

public interface NewsDao {
    //create
    void add(News news);

    //read
//    static List<News> getAll();
    List<News> getAllNewsPostedByDepartment(int deparIdId);

    //update
    //omit for now

    //delete
    void deleteById(int id);
    void clearAll();
}

