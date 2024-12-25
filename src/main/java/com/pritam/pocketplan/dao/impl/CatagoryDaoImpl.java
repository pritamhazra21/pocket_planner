package com.pritam.pocketplan.dao.impl;

import com.pritam.pocketplan.dao.CatagoryDao;
import com.pritam.pocketplan.models.Catagory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CatagoryDaoImpl implements CatagoryDao {


    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Catagory addCatagory(Catagory catagory) {
        return null;
    }

    @Override
    public List<Catagory> addCatagories(List<Catagory> catagoryList) {
        return (List<Catagory>) mongoTemplate.insert(catagoryList, Catagory.class);
    }

    @Override
    public void deleteAllData() {
        mongoTemplate.remove(new Query(), Catagory.class);
    }
}
