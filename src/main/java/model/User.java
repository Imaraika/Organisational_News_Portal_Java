package model;

import org.sql2o.Connection;

import java.util.Objects;

public class User {
    private String name;
    private String position;
    private String role;
    private String iddept;
    private int id;
    public User(String name,String Position,String role,String iddept ){

        this.name=name;
        this.role=role;
        this.position=Position;
        this.iddept=iddept;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String Position) {
        this.position = Position;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getIddept() {
        return iddept;
    }

    public void setIddept(String iddept) {
        this.iddept = iddept;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User users = (User) o;
        return Objects.equals(name, users.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name,position,role, iddept,id);
    }
    public void save() {
        try(Connection con = model.DB.sql2o.open()) {
            String sql = "INSERT INTO users (name, position, role,iddept) VALUES (:name, :position, :role, :iddept)";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("name", this.name)
                    .addParameter("position", this.position)
                    .addParameter("role", this.role)
                    .addParameter("iddept", this.iddept)
                    .executeUpdate()
                    .getKey();
        }
    }
}


