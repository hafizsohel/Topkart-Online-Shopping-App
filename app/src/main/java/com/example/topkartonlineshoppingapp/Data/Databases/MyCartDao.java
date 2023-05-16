package com.example.topkartonlineshoppingapp.Data.Databases;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.topkartonlineshoppingapp.models.MyCartModel;

import java.util.List;
@Dao
public interface MyCartDao {


    @Query("SELECT * FROM cart_items")
    List<MyCartModel> getAll();

    @Insert
    void insert(MyCartModel cartModel);

    @Query("DELETE FROM cart_items")
    void deleteAllCartItems();

    @Query("SELECT * FROM cart_items")
    List<MyCartModel> getAllCartItems();
}
