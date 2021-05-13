package com.example.test1.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.test1.entity.PainRecord;


import java.util.List;

@Dao
public interface PainRecordDAO {
    @Query("SELECT * FROM painrecord ORDER BY email ASC")
    LiveData<List<PainRecord>> getAll();

    @Query("SELECT * FROM painrecord WHERE email = :email")
    LiveData<List<PainRecord>> getRecordsFromEmail(String email);

    @Query("SELECT * FROM painrecord WHERE uid = :uid LIMIT 1")
    PainRecord findByID(int uid);

    @Insert
    void insert(PainRecord painRecord);

    @Delete
    void delete(PainRecord painRecord);

    @Update
    void update(PainRecord painRecord);

    @Query("DELETE FROM painrecord")
    void deleteAllRecords();
}
