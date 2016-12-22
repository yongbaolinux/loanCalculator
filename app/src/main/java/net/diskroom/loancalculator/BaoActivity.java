package net.diskroom.loancalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;

public class BaoActivity extends AppCompatActivity {
    private Button calculator;
    private EditText baoTotal;
    private EditText baoRate;

    private float total = 0f;
    private float rate = 0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao);
        inital();
        calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valid() == false){
                    return;
                }

            }
        });
    }

    //初始化页面
    private void inital(){
        calculator = (Button) findViewById(R.id.calculator);
        baoTotal = (EditText) findViewById(R.id.baoTotal);
        baoRate = (EditText) findViewById(R.id.baoRate);
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
