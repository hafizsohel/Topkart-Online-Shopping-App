package com.example.topkartonlineshoppingapp.Data.Databases;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.topkartonlineshoppingapp.models.MyCartModel;

@Database(entities = {MyCartModel.class}, version = 2, exportSchema = true)
public abstract class CartDatabase extends RoomDatabase {
    private static CartDatabase instance;

    public abstract MyCartDao myCartDao();

    public static synchronized CartDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    CartDatabase.class, "cart_database").allowMainThreadQueries().build();
        }
        return instance;
    }
}
