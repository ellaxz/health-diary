package com.example.test1.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


import com.example.test1.dao.PainRecordDAO;


import com.example.test1.entity.PainRecord;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {PainRecord.class}, version = 1, exportSchema = false)
public abstract class PainRecordDatabase extends RoomDatabase {



    public abstract PainRecordDAO painRecordDao();

    private static PainRecordDatabase INSTANCE;

    //we create an ExecutorService with a fixed thread pool so we can later run database operations asynchronously on a background thread.
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    //A synchronized method in a multi threaded environment means that two threads are not allowed to access data at the same time
    public static synchronized PainRecordDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    PainRecordDatabase.class, "UserDatabase")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return INSTANCE; }

    private  static  RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            new PopulateDbAsyncTask(INSTANCE).execute();
        }

    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{

        private PainRecordDAO painRecordDAO;

        private PopulateDbAsyncTask(PainRecordDatabase db){
            painRecordDAO = db.painRecordDao();


        }


        @Override
        protected Void doInBackground(Void... voids){
            painRecordDAO.insert(new PainRecord("ss",1,"xxx","xxxx"));
            painRecordDAO.insert(new PainRecord("emial2",2,"sddsd","dsdsds"));
            painRecordDAO.insert(new PainRecord("emial3",3,"sdsdsds","dsdsds"));

            return null;
        }
    }
}