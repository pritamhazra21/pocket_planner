package com.pritam.pocketplan.dao;

import com.pritam.pocketplan.models.Catagory;

import java.util.List;

public interface CatagoryDao {

    Catagory addCatagory(Catagory catagory);
    List<Catagory> addCatagories(List<Catagory> catagoryList);


    void deleteAllData();
}
