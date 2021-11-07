package com.coba.sqlite;

public class StudentModel {
    public String id, name, kelas;

    public StudentModel(String id,String name,String kelas){
        this.id = id;
        this.name = name;
        this.kelas = kelas;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getKelas() {
        return kelas;
    }
}
