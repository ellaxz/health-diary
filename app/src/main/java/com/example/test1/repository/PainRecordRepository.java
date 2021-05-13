package com.example.test1.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;


import com.example.test1.dao.PainRecordDAO;


import com.example.test1.database.PainRecordDatabase;


import com.example.test1.entity.PainRecord;


import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class PainRecordRepository {
    private PainRecordDAO painRecordDAO;
    private LiveData<List<PainRecord>> allRecords;

    public PainRecordRepository(Application application) {
        PainRecordDatabase db = PainRecordDatabase.getInstance(application);
        painRecordDAO = db.painRecordDao();
        allRecords = painRecordDAO.getAll();
    }

    // Room executes this query on a separate thread
    public LiveData<List<PainRecord>> getAllRecords() {
        return allRecords;
    }

    public void insert(final PainRecord painRecord) {
        PainRecordDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                painRecordDAO.insert(painRecord);
            }
        });
    }

    public void deleteAll() {
        PainRecordDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                painRecordDAO.deleteAllRecords();
            }
        });
    }

    public void delete(final PainRecord painRecord) {
        PainRecordDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                painRecordDAO.delete(painRecord);
            }
        });
    }

    public void updateRecord(final PainRecord painRecord) {
        PainRecordDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                painRecordDAO.update(painRecord);
            }
        });
    }

    public LiveData<List<PainRecord>> getRecordFromEmail (String email) {
        return painRecordDAO.getRecordsFromEmail(email);
    }





    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<PainRecord> findByIDFuture(final  int uid){
        return CompletableFuture.supplyAsync(new Supplier<PainRecord>() {
            @Override
            public PainRecord get() {
                return painRecordDAO.findByID(uid);
            }
        }, PainRecordDatabase.databaseWriteExecutor);
    }
}