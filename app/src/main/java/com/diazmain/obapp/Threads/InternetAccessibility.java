package com.diazmain.obapp.Threads;

import android.content.Context;
import android.os.AsyncTask;

import com.diazmain.obapp.Home.HomeActivity;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.function.Consumer;

public class InternetAccessibility extends AsyncTask<Void, Void, Boolean> {


    private Consumer mConsumer;
    public interface Consumer {
        void accept(Boolean internet);
    }

    public InternetAccessibility(Consumer consumer) {
        this.mConsumer = consumer;
        execute();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            Socket sock = new Socket();
            sock.connect(new InetSocketAddress("8.8.8.8", 53), 4000);
            sock.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
        protected void onPostExecute(Boolean internet) {
            mConsumer.accept(internet);
    }
}
