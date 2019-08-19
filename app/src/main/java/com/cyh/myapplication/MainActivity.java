package com.cyh.myapplication;

import android.app.Activity;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import static android.content.ContentValues.TAG;

/**
* 基于TCP的Socket网络通信例程 Android端
*@author dahaiChang
*created at 2019/8/16 10:45
*/
public class MainActivity extends Activity {
    private TextView show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show = findViewById(R.id.show);
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    //建立连接到远程服务器的Socket
                    Socket socket = new Socket("192.168.1.128", 30050);
                    //加入10s超时及处理
                    socket.setSoTimeout(1000);
                    //将Sockt对应的输入流包装成BufferReader
                    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    //进行普通IO操作
                    String line = br.readLine();
                    show.setText("来自服务器的数据：" + line);
                } catch (SocketException e) {
                    e.printStackTrace();
                    Log.e(TAG,"请求超时");
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }.start();
    }
}
