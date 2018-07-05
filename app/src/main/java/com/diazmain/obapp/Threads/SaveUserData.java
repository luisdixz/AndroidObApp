package com.diazmain.obapp.Threads;

import android.content.Context;
import android.os.AsyncTask;

import com.diazmain.obapp.helper.SharedPrefManager;
import com.diazmain.obapp.Login.model.User;

public class SaveUserData extends AsyncTask<User, Void, Boolean> {

    private Context context;

    public SaveUserData(Context _context) {
        this.context = _context;
    }

    @Override
    protected Boolean doInBackground(User... users) {
        new SharedPrefManager(context).userLogin(users[0]);

        return null;
    }
}