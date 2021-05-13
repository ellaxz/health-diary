package com.example.test1.viewmodel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.test1.entity.PainRecord;

import com.example.test1.repository.PainRecordRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PainRecordViewModel extends AndroidViewModel {
    private static PainRecordRepository uRepository;
    private LiveData<List<PainRecord>> allRecords;

    public PainRecordViewModel(@NonNull Application application) {
        super(application);
        uRepository = new PainRecordRepository(application);
        allRecords= uRepository.getAllRecords();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<PainRecord> findByIDFuture(final int uid){
        return uRepository.findByIDFuture(uid);
    }

    public LiveData<List<PainRecord>> getAllRecords() {
        return allRecords;
    }

    public LiveData<List<PainRecord>> getRecordFromEmail(String email) {
        return uRepository.getRecordFromEmail(email);
    }


    public static void insert(PainRecord painRecord) {
        uRepository.insert(painRecord);
    }

    public void deleteAll() {
        uRepository.deleteAll();
    }

    public void delete(PainRecord painRecord){
        uRepository.delete(painRecord);
    }

    public void update(PainRecord painRecord) {

        uRepository.updateRecord(painRecord);
    }
}