package net.diskroom.loancalculator;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
///实现从屏幕底部向上滑出一个view
//import android.view.animation.Animation;
////

////wheelview控件
import com.apkfuns.logutils.LogUtils;

import net.diskroom.loancalculator.wheelview.LoopView;
import net.diskroom.loancalculator.wheelview.OnItemSelectedListener;

public class HouseLoanActivity extends AppCompatActivity {
    private LinearLayout.LayoutParams layoutParams;
    private LinearLayout wheelviewContainer;

    private EditText loanTotalInput;
    private TextView loanTimeInput;
    private EditText loanRateInput;
    private Button calculator;
    //private Animation myAnimation_Translate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_loan);

        ///定义房贷金额输入框焦点事件
        final EditText loanTotalInput = (EditText)findViewById(R.id.loanTotalInput);
        loanTotalInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loanTotalInput.setText("");
            }
        });
        ///定义房贷利率输入框焦点事件
        final EditText loanRateInput = (EditText)findViewById(R.id.loanRateInput);
        loanRateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loanRateInput.setText("");
            }
        });

        loanTotalInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /*if (hasFocus) {
                    LogUtils.v("1");
                } else {
                    LogUtils.v("2");
                }*/
            }
        });

        //////点击呼出wheelview面板
        final TextView loanTimeInput = (TextView) findViewById(R.id.loanTimeInput);
        assert loanTimeInput != null;
        loanTimeInput.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                final AlertDialog wheelviewDialog = new AlertDialog.Builder(HouseLoanActivity.this).setCancelable(false).create();
                Window wheelviewContainerDialogWindow = wheelviewDialog.getWindow();   //获取对话框window对象
                wheelviewDialog.show();
                wheelviewContainerDialogWindow.setContentView(R.layout.loan_time_wheel_container);

                //初始化wheelview
                final LoopView loopView = new LoopView(HouseLoanActivity.this);
                ArrayList<String> list = new ArrayList<>();
                for (int i = 1; i <= 30; i++) {
                    list.add(i + "年");
                }
                //设置是否循环播放
                //loopView.setNotLoop();
                //滚动监听
                loopView.setListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int index) {
                        //Log.d("debug", "Item " + index);
                    }
                });
                //设置原始数据
                loopView.setItems(list);
                //设置初始位置
                loopView.setInitPosition(5);
                //设置字体大小
                loopView.setTextSize(20);
                wheelviewContainer = (LinearLayout) wheelviewContainerDialogWindow.findViewById(R.id.wheelviewContainer);
                layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.CENTER;
                wheelviewContainer.addView(loopView, layoutParams);
                //确定房贷年限选择
                TextView loanTimeSure = (TextView) wheelviewContainerDialogWindow.findViewById(R.id.switch_city_dialog_sure);
                loanTimeSure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        wheelviewDialog.dismiss();
                        loanTimeInput.setText(loopView.getSelectedItem() + 1 + "年");    //直接setText一个整数会失效

                    }
                });
            }
        });

        //////计算
        calculator = (Button) findViewById(R.id.calculator);
        calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String loanTotal = loanTotalInput.getText().toString();
                if(loanTotal == null || loanTotal.length()==0 || loanTotal.equals("")){
                    Toast.makeText(HouseLoanActivity.this,"请输入房贷金额",Toast.LENGTH_LONG).show();
                }
                String loanTime = loanTimeInput.getText().toString();
                //从loanTime中提取出整数
                String loanTimeStr = "";
                int loanTimeInt = 0;
                if(loanTime != null && !loanTime.equals("") && loanTime.length()!=0){
                    for(int i = 0;i<loanTime.length();i++){
                        if(loanTime.charAt(i)>=48 && loanTime.charAt(i)<=57) {
                            loanTimeStr += loanTime.charAt(i);
                        }
                    }
                    loanTimeInt = Integer.parseInt(loanTimeStr);
                }
                
            }
        });
    }

}
