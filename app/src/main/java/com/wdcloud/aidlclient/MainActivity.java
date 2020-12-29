package com.wdcloud.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.wdcloud.aidlserver.Book;
import com.wdcloud.aidlserver.BookController;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    private final String TAG = "Client";

    private BookController bookController;

    private boolean connected;

    private List<Book> bookList;
    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            bookController = BookController.Stub.asInterface(iBinder);
            connected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            connected = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindService();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connected) {
            unbindService(serviceConnection);
        }
    }
    public void getBooklist(View view)
    {
        try {
            bookList= bookController.getBookList();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        log();
    }
    public void addBook(View view)
    {
        if(connected)
        {
            try {
                Book book = new Book("新书");
                bookController.addBookInOut(book);
                Log.e(TAG, "向服务器以InOut方式添加了一本新书");
                Log.e(TAG, "新书名：" + book.getName());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
    private void bindService() {
        Intent intent = new Intent();
        intent.setPackage("com.wdcloud.aidlserver");
        intent.setAction("com.wdcloud.service.action");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }
    private void log() {
        for (Book book : bookList) {
            Log.e(TAG, book.toString());
        }
    }
}