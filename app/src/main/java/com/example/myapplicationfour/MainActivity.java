package com.example.myapplicationfour;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
    Button bind, unbind, getServiceStatus;
    // 保持所启动的Service的IBinder对象
    BindService.MyBinder binder;
    // 定义一个ServiceConnection对象
    private ServiceConnection conn = new ServiceConnection(){
        // 当该Activity与Service连接成功时回调该方法
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("GsonUtils","onServiceConnected");
            binder=(BindService.MyBinder) service;
        }
        // 当该Activity与Service断开连接时回调该方法
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("GsonUtils","onServiceDisconnected");
        }

    };
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 获取程序界面中的start、stop、getServiceStatus按钮
        bind = (Button) findViewById(R.id.bind);
        unbind = (Button) findViewById(R.id.unbind);
        getServiceStatus = (Button) findViewById(R.id.getServiceStatus);
        //创建启动Service的Intent
        final Intent intent = new Intent(this,BindService.class);
        //为Intent设置Action属性
        intent.setAction(".BindService");


        bind.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View source){
                //绑定指定Serivce
                bindService(intent,conn,BIND_AUTO_CREATE);
                Toast.makeText(MainActivity.this,"绑定完成！",Toast.LENGTH_SHORT).show();
            }
        });
        unbind.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View source){
                //解除绑定Serivce
                unbindService(conn);
                Toast.makeText(MainActivity.this,"解除绑定！",Toast.LENGTH_SHORT).show();
            }
        });
        getServiceStatus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View source){
                // 获取、并显示Service的count值
                Toast.makeText(MainActivity.this
                        , "Serivce的count值为：" + binder.getCount()
                        , Toast.LENGTH_SHORT).show();
            }
        });
    }
}

