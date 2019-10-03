package model;

import org.sql2o.Connection;

import java.util.Objects;

public class Department {
    private String nameofdepartment;
    private String descrptofdepartment;
    private String numberofemployees;
    private int id;

    public Department(String nameofdepartment,String descrptofdepartment,String nbrEmployees){
        this.nameofdepartment=nameofdepartment;
        this.descrptofdepartment=descrptofdepartment;
        this.numberofemployees=nbrEmployees;

    }

    public String getDescrptofdepartment() {
        return descrptofdepartment;
    }

    public void setDescrptofdepartment(String descrptofdepartment) {
        this.descrptofdepartment = descrptofdepartment;
    }

    public String getNameofdepartment() {
        return nameofdepartment;
    }

    public void setNameofdepartment(String nameofdepartment) {
        this.nameofdepartment = nameofdepartment;
    }

    public String getNumberofemployees() {
        return numberofemployees;
    }

    public void setNumberofemployees(String numberofemployees) {
        this.numberofemployees = numberofemployees;
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
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return numberofemployees == that.numberofemployees &&
                id == that.id &&
                Objects.equals(nameofdepartment, that.nameofdepartment) &&
                Objects.equals(descrptofdepartment, that.descrptofdepartment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameofdepartment, descrptofdepartment, numberofemployees, id);
    }
    public void save() {
        try(Connection con = model.DB.sql2o.open()) {
            String sql = "INSERT INTO departments (nameofdepartment, descrptofdepartment, numberofemployees) VALUES (:nameofdepartment, :descrptofdepartment, :numberofemployees)";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("nameofdepartment", this.nameofdepartment)
                    .addParameter("descrptofdepartment", this.descrptofdepartment)
                    .addParameter("numberofemployees", this.numberofemployees)
                    .executeUpdate()
                    .getKey();
        }
    }
}
