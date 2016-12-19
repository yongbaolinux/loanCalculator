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
    private TextView carloanRateInput;          //车贷年利率

    private AlertDialog carloanDialog;      //选择车贷期数对话框对象
    private Window    carloanWindow;      //选择车贷期数对话框窗口对象
    private LinearLayout loopViewContainer;    // loopView容器
    private LoopView loopView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_loan);
        initial();

    }

    public void initial(){
        //维护一个公共的 loopview 对话框
        carloanDialog = new AlertDialog.Builder(CarLoanActivity.this).setCancelable(false).create();
        carloanWindow = carloanDialog.getWindow();

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
        carloanDownPaymentInput.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                carloanDialog.show();
                carloanWindow.setContentView(R.layout.loan_time_wheel_container);
                loopViewContainer = (LinearLayout)carloanWindow.findViewById(R.id.wheelviewContainer);
                //初始化 LoopView
                ArrayList<String> list = new ArrayList<String>();
                for (int i = 30; i <= 70; i=i+10) {
                    list.add(i + "%");
                }
                initLoopView(list);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.CENTER;
                loopViewContainer.addView(loopView,layoutParams);
                //确定车贷首付比例选择
                TextView loanDownPaymentSure = (TextView) carloanWindow.findViewById(R.id._dialog_sure);
                loanDownPaymentSure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        carloanDialog.dismiss();
                        carloanDownPaymentInput.setText(String.valueOf(intval(loopView.getSelectedItemValue())));    //直接setText一个整数会失效
                        loopViewContainer.removeAllViews();     //因为Dialog是在外部维护的一个变量 所以关闭之后需要removewAllViews
                    }
                });
                //取消车贷期限选择
                TextView carloanDownPaymentCancel = (TextView) carloanWindow.findViewById(R.id._dialog_cancel);
                carloanDownPaymentCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        carloanDialog.dismiss();
                        loopViewContainer.removeAllViews();
                    }
                });
            }
        });
        //车贷利率控件
        carloanRateInput = (TextView) findViewById(R.id.carloanRateInput);

        //车贷期限输入控件
        carloanTimeInput = (TextView) findViewById(R.id.carloanTimeInput);
        carloanTimeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carloanDialog.show();
                carloanWindow.setContentView(R.layout.loan_time_wheel_container);
                loopViewContainer = (LinearLayout)carloanWindow.findViewById(R.id.wheelviewContainer);
                //初始化 LoopView
                ArrayList<String> list = new ArrayList<String>();
                for (int i = 1; i <= 5; i++) {
                    list.add(i + "年");
                }
                initLoopView(list);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.CENTER;
                loopViewContainer.addView(loopView,layoutParams);
                //确定车贷期限选择
                TextView loanTimeSure = (TextView) carloanWindow.findViewById(R.id._dialog_sure);
                loanTimeSure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        carloanDialog.dismiss();
                        int carloanTime = loopView.getSelectedItem() + 1;//车贷期限
                        carloanTimeInput.setText(carloanTime + "年");    //直接setText一个整数会失效
                        loopViewContainer.removeAllViews();     //因为Dialog是在外部维护的一个变量 所以关闭之后需要removewAllViews
                        //根据车贷期限修改 基准年利率
                        if(carloanTime <= 1){
                            carloanRateInput.setText("4.35");
                        } else if(carloanTime <= 5){
                            carloanRateInput.setText("4.75");
                        } else {
                            carloanRateInput.setText("4.90");
                        }
                    }
                });
                //取消车贷期限选择
                TextView carloanTimeCancel = (TextView) carloanWindow.findViewById(R.id._dialog_cancel);
                carloanTimeCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        carloanDialog.dismiss();
                        loopViewContainer.removeAllViews();
                    }
                });
            }
        });


    }

    //初始化LoopView
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
        //LogUtils.v(initPos);
        //loopView.setInitPosition(3);  //// TODO: 2016/12/19 注释掉这一行 初始化居然正常了
        //设置字体大小
        loopView.setTextSize(20);
    }

    //提取字符串中的整数
    private int intval(String str){
        String tempString = "";
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
                tempString += str.charAt(i);
            }
        }
        return Integer.parseInt(tempString);
    }
}
