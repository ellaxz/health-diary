package com.example.test1.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PainRecord {


    @PrimaryKey(autoGenerate = true)
    public int uid;

//    @ColumnInfo(name = "full_name")
//    @NonNull
//    public String fullName;

    @ColumnInfo (name = "email")
    @NonNull
    public String email;

    @ColumnInfo (name = "pain_level")
    @NonNull
    public double painLevel;

    @ColumnInfo (name = "goal")
    @NonNull
    public String message;

    @ColumnInfo (name = "mood")
    @NonNull
    public String mood;



    public PainRecord (@NonNull String email, double painLevel, String mood, String message){
        this.email = email;
        this.painLevel = painLevel;
        this.message = message;
        this.mood = mood;
    }

}