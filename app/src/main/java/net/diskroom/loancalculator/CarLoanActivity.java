package net.diskroom.loancalculator;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;

import net.diskroom.loancalculator.wheelview.LoopView;
import net.diskroom.loancalculator.wheelview.OnItemSelectedListener;

import java.util.ArrayList;

public class CarLoanActivity extends AppCompatActivity {
    private EditText carloanTotalInput;         //车贷金额输入框
    private TextView carloanDownPaymentInput;   //首付比例
    private TextView carloanTimeInput;          //车贷期数
    private AlertDialog carloanTimeDialog;      //选择车贷期数对话框对象
    private Window    carloanTimeWindow;      //选择车贷期数对话框窗口对象
    private LinearLayout loopViewContainer;    // loopView容器
    private LoopView loopView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_loan);
        initial();

    }

    public void initial(){
        //爱车价格输入框点击事件
        carloanTotalInput = (EditText) findViewById(R.id.carloanTotalInput);
        assert carloanTotalInput != null;
        carloanTotalInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carloanTotalInput.setText("");
            }
        });

        //首付比例输入控件
        carloanDownPaymentInput = (TextView) findViewById(R.id.carLoanDownPaymentInput);

        //车贷期限
        carloanTimeInput = (TextView) findViewById(R.id.carloanTimeInput);
        carloanTimeDialog = new AlertDialog.Builder(CarLoanActivity.this).setCancelable(false).create();
        carloanTimeWindow = carloanTimeDialog.getWindow();
        carloanTimeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carloanTimeDialog.show();
                carloanTimeWindow.setContentView(R.layout.loan_time_wheel_container);
                loopViewContainer = (LinearLayout)carloanTimeWindow.findViewById(R.id.wheelviewContainer);
                //初始化 LoopView
                ArrayList<String> list = new ArrayList<String>();
                for (int i = 1; i <= 5; i++) {
                    list.add(i + "年");
                }
                initLoopView(list);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.CENTER;
                loopViewContainer.addView(loopView,layoutParams);
                //确定房贷年限选择
                TextView loanTimeSure = (TextView) carloanTimeWindow.findViewById(R.id._dialog_sure);
                loanTimeSure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        carloanTimeDialog.dismiss();
                        carloanTimeInput.setText(loopView.getSelectedItem() + 1 + "年");    //直接setText一个整数会失效
                        loopViewContainer.removeAllViews();     //因为Dialog是在外部维护的一个变量 所以关闭之后需要removewAllViews
                    }
                });
                //取消房贷年限选择
                TextView carloanTimeCancel = (TextView) carloanTimeWindow.findViewById(R.id._dialog_cancel);
                carloanTimeCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        carloanTimeDialog.dismiss();
                        loopViewContainer.removeAllViews();
                    }
                });
            }
        });


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
        int initPos = 0;
        if(carloanTime.length() != 0 ) {
            for (int i = 0; i < carloanTime.length(); i++) {
                if (carloanTime.charAt(i) >= 48 && carloanTime.charAt(i) <= 57) {
                    loanTimeStr += carloanTime.charAt(i);
                }
            }
            initPos = Integer.parseInt(loanTimeStr) - 1;

        }
        LogUtils.v(initPos);
        loopView.setInitPosition(initPos);
        //设置字体大小
        loopView.setTextSize(20);
    }
}
