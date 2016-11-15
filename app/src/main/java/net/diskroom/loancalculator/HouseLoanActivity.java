package net.diskroom.loancalculator;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.BoringLayout;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TableLayout;
import android.widget.TableRow;
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
    private RadioGroup loanTypeRadio;
    private Button calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_loan);

        ///定义房贷金额输入框焦点事件
        loanTotalInput = (EditText) findViewById(R.id.loanTotalInput);
        loanTotalInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loanTotalInput.setText("");
            }
        });
        /*loanTotalInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    LogUtils.v("1");
                } else {
                    LogUtils.v("2");
                }
            }
        });*/
        ///定义房贷利率输入框焦点事件
        loanRateInput = (EditText) findViewById(R.id.loanRateInput);
        loanRateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loanRateInput.setText("");
            }
        });

        loanTypeRadio = (RadioGroup) findViewById(R.id.loanTypeRadio);

        //////点击呼出wheelview面板
        final TextView loanTimeInput = (TextView) findViewById(R.id.loanTimeInput);
        assert loanTimeInput != null;
        loanTimeInput.setOnClickListener(new View.OnClickListener() {

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
                //取消房贷年限选择
                TextView loanTimeCancel = (TextView) wheelviewContainerDialogWindow.findViewById(R.id.switch_city_dialog_cancel);
                loanTimeCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        wheelviewDialog.dismiss();
                    }
                });
            }
        });

        //////计算
        calculator = (Button) findViewById(R.id.calculator);

        calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //验证房贷金额
                String loanTotal = loanTotalInput.getText().toString();
                if (loanTotal == null || loanTotal.length() == 0 || loanTotal.equals("")) {
                    Toast.makeText(HouseLoanActivity.this, "请输入房贷金额", Toast.LENGTH_LONG).show();
                    return;
                }
                //验证房贷年限
                String loanTime = loanTimeInput.getText().toString();
                String loanTimeStr = "";//从loanTime中提取出整数
                int loanTimeInt = 0;
                if (loanTime != null && !loanTime.equals("") && loanTime.length() != 0) {
                    for (int i = 0; i < loanTime.length(); i++) {
                        if (loanTime.charAt(i) >= 48 && loanTime.charAt(i) <= 57) {
                            loanTimeStr += loanTime.charAt(i);
                        }
                    }
                    loanTimeInt = Integer.parseInt(loanTimeStr);
                }
                if (loanTimeInt == 0) {
                    Toast.makeText(HouseLoanActivity.this, "请选择房贷年限", Toast.LENGTH_LONG).show();
                    return;
                }
                //验证房贷利率
                String loanRate = loanRateInput.getText().toString();
                if (loanTotal == null || loanTotal.length() == 0 || loanTotal.equals("")) {
                    Toast.makeText(HouseLoanActivity.this, "请输入房贷月利率", Toast.LENGTH_LONG).show();
                    return;
                }
                //获取还款方式
                RadioButton currentRadioButton = (RadioButton) findViewById(loanTypeRadio.getCheckedRadioButtonId());
                String loanType = currentRadioButton.getText().toString();
                if (loanType.equals("等额本金")) {
                    //等额本金还款方式(计算公式 每月还款金额 = （贷款本金 / 还款月数）+（本金 — 已归还本金累计额）×每月利率)

                    double[][] months = new double[loanTimeInt * 12][4];
                    int loanTotalInt = Integer.parseInt(loanTotal) * 10000; //贷款本金
                    int perMonthLoan = loanTotalInt / (loanTimeInt * 12);     //每月应还本金;

                    for (int i = 0; i < loanTimeInt * 12; i++) {
                        //LogUtils.v(Float.parseFloat(loanRate));
                        months[i][0] = i + 1;
                        months[i][1] = perMonthLoan + (loanTotalInt - i * perMonthLoan) * Float.parseFloat(loanRate) * 0.01 / 12;
                        months[i][2] = (loanTotalInt - i * perMonthLoan) * Float.parseFloat(loanRate) * 0.01 / 12;
                        months[i][3] = perMonthLoan;
                    }
                    //LogUtils.v(months);

                    //展示计算结果
                    final AlertDialog calculatorDataDialog = new AlertDialog.Builder(HouseLoanActivity.this).setCancelable(true).create();
                    Window calculatorDataDialogWindow = calculatorDataDialog.getWindow();   //获取对话框window对象
                    calculatorDataDialog.show();
                    calculatorDataDialogWindow.setContentView(R.layout.calculator_data);
                    TextView loanTypeTextView = (TextView) calculatorDataDialogWindow.findViewById(R.id.loanType);
                    loanTypeTextView.setText(loanType);
                    //关闭数据对话框
                    TextView close = (TextView) calculatorDataDialogWindow.findViewById(R.id.close);
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            LogUtils.v("click");
                            calculatorDataDialog.dismiss();
                        }

                    });

                    //显示还贷数据 addview 的方式显示 效率极低
                    TableLayout calculateDataTable = (TableLayout)calculatorDataDialogWindow.findViewById(R.id.calculateDataTable);
                    calculateDataTable.setStretchAllColumns(true);
                    ListView lv = (ListView) calculatorDataDialogWindow.findViewById(R.id.calculateDataListView);
                    TableAdapter tableAdapter = new TableAdapter(HouseLoanActivity.this,months);
                    lv.setAdapter(tableAdapter);
                    /*for(int i=0;i<months.length;i++){
                        TableRow tableRow = new TableRow(HouseLoanActivity.this);
                        for(int j=0;j<months[i].length;j++){
                            TextView textView = new TextView(HouseLoanActivity.this);
                            //textView.setLayoutParams(lp); //无效
                            textView.setGravity(Gravity.CENTER);
                            if(j==0) {
                                textView.setText(String.valueOf((int)(months[i][j])));
                            } else {
                                textView.setText(String.format("%.1f",months[i][j]));
                            }
                            tableRow.addView(textView);
                        }

                        calculateDataTable.addView(tableRow);

                    }*/

                    /*fixListViewHeight(lv);
                    lv.setOnScrollListener(new AbsListView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(AbsListView view, int scrollState) {
                            //LogUtils.v(scrollState);
                        }

                        @Override
                        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                            //LogUtils.v("a");
                        }
                    });*/

                    /*ScrollView sv = (ScrollView) calculatorDataDialogWindow.findViewById(R.id.calculateDataScroll);
                    sv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                        @Override
                        public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                            LogUtils.v("ScrollView");
                        }
                    });*/
                }
            }

            //动态计算ListView的高度 修正ListView在ScrollView里只显示一行的问题
            private void fixListViewHeight(ListView lv){
                TableAdapter tableAdapter = (TableAdapter)lv.getAdapter();
                if(tableAdapter == null){
                    return;
                }
                int height = 0;
                for(int i=0;i<tableAdapter.getCount();i++){
                    View tableViewItem = tableAdapter.getView(i,null,lv);
                    tableViewItem.measure(0,0);
                    height += tableViewItem.getMeasuredHeight();
                }
                ViewGroup.LayoutParams params = lv.getLayoutParams();
                params.height = height + lv.getDividerHeight() * (tableAdapter.getCount()-1);        //间隔线高度+listViewItem高度和
                lv.setLayoutParams(params);
            }
        });

    }

}
