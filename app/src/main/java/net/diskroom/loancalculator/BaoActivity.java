package net.diskroom.loancalculator;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class BaoActivity extends BaseActivity {
    private Button calculator;
    private EditText baoTotal;
    private EditText baoRate;

    private float total = 0f;
    private float rate = 0.7f;

    private MyApp myApplication;                         //维护一个application实例
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myApplication = (MyApp) getApplication();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao);
        inital();
        calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valid() == false){
                    return;
                }
                showDialog("预估您的当天收益为 "+String.format("%.2f",total*rate)+" 元");
            }
        });
    }

    //初始化页面
    private void inital(){
        calculator = (Button) findViewById(R.id.calculator);
        baoTotal = (EditText) findViewById(R.id.baoTotal);
        baoRate = (EditText) findViewById(R.id.baoRate);

        //绑定各控件响应事件
        baoTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baoTotal.setText("");
            }
        });

        baoTotal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    baoTotal.setText("");
                }
            }
        });

        baoRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    baoRate.setText("");
                    baoRate.setHint("");
            }
        });

        baoRate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    baoRate.setText("");
                    baoRate.setHint("");
                }
            }
        });
    }

    //验证表单
    public boolean valid(){
        String temp = baoTotal.getText().toString();
        if(temp.equals("") || temp.equals(".")){
            Toast.makeText(getApplicationContext(),"金额不能为空",Toast.LENGTH_LONG).show();
            return false;
        }
        total = Float.parseFloat(temp);
        String temp_ = baoRate.getText().toString();
        if(temp_.equals("") || temp_.equals(".")){
            Toast.makeText(getApplicationContext(),"万份收益不能为空",Toast.LENGTH_LONG).show();
            return false;
        }
        rate = Float.parseFloat(temp_);
        return true;
    }
}
