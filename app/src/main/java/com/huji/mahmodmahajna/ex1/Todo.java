package com.huji.mahmodmahajna.ex1;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by mahmodmahajna on 21/03/2017.
 */

public class Todo implements Parcelable{
    private String todo;
    private Date date;
    public Todo() {}
    public Todo(String todo, Date date) {
        this.todo = todo;
        this.date = date;
    }
    public String getTodo() {
        return todo;
    }
    public Todo(Parcel in) {
        todo = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(todo);
    }

    public static final Parcelable.Creator<Todo> CREATOR = new Parcelable.Creator<Todo>() {
        public Todo createFromParcel(Parcel in) {
            return new Todo(in);
        }

        public Todo[] newArray(int size) {
            return new Todo[size];
        }
    };
}