package net.diskroom.loancalculator;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.diskroom.loancalculator.wheelview.LoopView;
import net.diskroom.loancalculator.wheelview.OnItemSelectedListener;

import java.util.ArrayList;

public class CarLoanActivity extends AppCompatActivity {
    private EditText carloanTotalInput;         //车贷金额输入框
    private TextView carloanDownPaymentInput;   //首付比例
    private TextView carloanTimeInput;          //车贷期数
    private AlertDialog carloanTimeDialog;      //选择车贷期数对话框
    private LinearLayout loopViewContainer;    // loopView容器
    private LoopView loopView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_loan);
        initial();

    }

    public void initial(){
        carloanTotalInput = (EditText) findViewById(R.id.carloanTotalInput);
        assert carloanTotalInput != null;
        carloanTotalInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carloanTotalInput.setText("");
            }
        });

        carloanDownPaymentInput = (TextView) findViewById(R.id.carLoanDownPaymentInput);


        carloanTimeInput = (TextView) findViewById(R.id.carloanTimeInput);
        carloanTimeDialog = new AlertDialog.Builder(CarLoanActivity.this).setCancelable(false).create();
        final Window carloanTimeWindow = carloanTimeDialog.getWindow();

        carloanTimeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carloanTimeDialog.show();
                carloanTimeWindow.setContentView(R.layout.loan_time_wheel_container);
                loopViewContainer = (LinearLayout)carloanTimeWindow.findViewById(R.id.wheelviewContainer);
                loopViewContainer.addView(loopView);
            }
        });
        //初始化 loopview
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 30; i <= 70; i=i+10) {
            list.add(i + "%");
        }
        initLoopView(list);

    }

    private void initLoopView(ArrayList<String> listData){
        if(loopView == null) {
            loopView = new LoopView(CarLoanActivity.this);
        }
        //设置数据
        loopView.setItems(listData);
        //设置是否循环播放
        //loopView.setNotLoop();
        //滚动监听
        loopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                //Log.d("debug", "Item " + index);
            }
        });

        //设置初始位置
        String carloanTime = carloanTimeInput.getText().toString();
        String loanTimeStr = "";
        int initPos = 5;
        if(carloanTime.length() != 0 ) {
            for (int i = 0; i < carloanTime.length(); i++) {
                if (carloanTime.charAt(i) >= 48 && carloanTime.charAt(i) <= 57) {
                    loanTimeStr += carloanTime.charAt(i);
                }
            }
            initPos = Integer.parseInt(loanTimeStr) - 1;
        }

        loopView.setInitPosition(initPos);
        //设置字体大小
        loopView.setTextSize(20);
    }
}
