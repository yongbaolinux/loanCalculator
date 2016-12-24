package net.diskroom.loancalculator;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
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

public class HouseLoanActivity extends BaseActivity {
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
                ArrayList<String> list = new ArrayList<String>();
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
                String loanTime = loanTimeInput.getText().toString();
                String loanTimeStr = "";
                int initPos = 5;
                if(loanTime.length() != 0 ) {
                    for (int i = 0; i < loanTime.length(); i++) {
                        if (loanTime.charAt(i) >= 48 && loanTime.charAt(i) <= 57) {
                            loanTimeStr += loanTime.charAt(i);
                        }
                    }
                    initPos = Integer.parseInt(loanTimeStr) - 1;
                }

                loopView.setInitPosition(initPos);
                //设置字体大小
                loopView.setTextSize(20);
                wheelviewContainer = (LinearLayout) wheelviewContainerDialogWindow.findViewById(R.id.wheelviewContainer);
                layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.CENTER;
                wheelviewContainer.addView(loopView, layoutParams);
                //确定房贷年限选择
                TextView loanTimeSure = (TextView) wheelviewContainerDialogWindow.findViewById(R.id._dialog_sure);
                loanTimeSure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        wheelviewDialog.dismiss();
                        loanTimeInput.setText(loopView.getSelectedItem() + 1 + "年");    //直接setText一个整数会失效

                    }
                });
                //取消房贷年限选择
                TextView loanTimeCancel = (TextView) wheelviewContainerDialogWindow.findViewById(R.id._dialog_cancel);
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
                //TODO精简上段代码
                if (loanTimeInt == 0) {
                    Toast.makeText(HouseLoanActivity.this, "请选择房贷年限", Toast.LENGTH_LONG).show();
                    return;
                }
                //验证房贷利率
                String loanRate = loanRateInput.getText().toString();
                if (loanRate == null || loanRate.length() == 0 || loanRate.equals("")) {
                    Toast.makeText(HouseLoanActivity.this, "请输入房贷年利率", Toast.LENGTH_LONG).show();
                    return;
                }

                //获取还款方式
                RadioButton currentRadioButton = (RadioButton) findViewById(loanTypeRadio.getCheckedRadioButtonId());
                String loanType = currentRadioButton.getText().toString();
                if (loanType.equals("等额本金")) {
                    //等额本金还款方式(计算公式 每月还款金额 = （贷款本金 / 还款月数）+（本金 — 已归还本金累计额）×每月利率)

                    double[][] months = new double[loanTimeInt * 12][4];
                    int loanTotalInt = (int)(Float.parseFloat(loanTotal) * 10000); //贷款本金
                    float perMonthLoan = loanTotalInt / (loanTimeInt * 12);     //每月支付本金;
                    double interest = 0;                                       //总支付利息
                    for (int i = 0; i < loanTimeInt * 12; i++) {
                        //LogUtils.v(Float.parseFloat(loanRate));
                        months[i][0] = i + 1;
                        months[i][1] = perMonthLoan + (loanTotalInt - i * perMonthLoan) * Float.parseFloat(loanRate) * 0.01 / 12;
                        months[i][2] = (loanTotalInt - i * perMonthLoan) * Float.parseFloat(loanRate) * 0.01 / 12;
                        months[i][3] = perMonthLoan;
                        interest += months[i][2];
                    }
                    //LogUtils.v(months);
                    double total = loanTotalInt + interest;             //总还款金额

                    //展示计算结果
                    final AlertDialog calculatorDataDialog = new AlertDialog.Builder(HouseLoanActivity.this,R.style.Dialog_FS).setCancelable(true).create();
                    Window calculatorDataDialogWindow = calculatorDataDialog.getWindow();   //获取对话框window对象
                    calculatorDataDialog.show();
                    calculatorDataDialogWindow.setContentView(R.layout.calculator_data);
                    //设置贷款类型 还款总额和总支付利息
                    TextView loanTypeTextView = (TextView) calculatorDataDialogWindow.findViewById(R.id.loanType);
                    loanTypeTextView.setText(loanType);
                    TextView totalTextView = (TextView) calculatorDataDialogWindow.findViewById(R.id.total);
                    totalTextView.setText(String.format("%.1f",total)+" ( 元 )");
                    TextView interestTextView = (TextView) calculatorDataDialogWindow.findViewById(R.id.interest);
                    interestTextView.setText(String.format("%.1f",interest) + " ( 元 )");
                    //关闭数据对话框
                    TextView close = (TextView) calculatorDataDialogWindow.findViewById(R.id.close);
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            calculatorDataDialog.dismiss();
                        }

                    });

                    //显示还贷数据 addview 的方式显示 效率极低
                    //TableLayout calculateDataTable = (TableLayout)calculatorDataDialogWindow.findViewById(R.id.calculateDataTable);
                    //calculateDataTable.setStretchAllColumns(true);

                    DisplayMetrics metrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(metrics);
                    TextView loanTips = (TextView)calculatorDataDialogWindow.findViewById(R.id.loanTips); //小提示
                    LinearLayout loanTitle = (LinearLayout)calculatorDataDialogWindow.findViewById(R.id.loanTitle); //标题
                    TableLayout loanSubTitle = (TableLayout)calculatorDataDialogWindow.findViewById(R.id.loanSubTitle); //副标题
                    ListView lv = (ListView) calculatorDataDialogWindow.findViewById(R.id.calculateDataListView);       //listview
                    LinearLayout loanFooter = (LinearLayout)calculatorDataDialogWindow.findViewById(R.id.loanFooter);   //底部

                    int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
                    int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
                    loanTitle.measure(w, h);
                    loanSubTitle.measure(w, h);
                    loanFooter.measure(w, h);
                    loanTips.measure(w, h);

                    double lvHeight = metrics.heightPixels - loanTips.getMeasuredHeight() - loanTitle.getMeasuredHeight() - loanSubTitle.getMeasuredHeight() - loanFooter.getMeasuredHeight() ;
                    ViewGroup.LayoutParams lvLayoutParams = lv.getLayoutParams();
                    lvLayoutParams.height = (int)lvHeight;
                    lv.setLayoutParams(lvLayoutParams);

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
                } else if(loanType.equals("等额本息")){
                    //等额本息计算公式
                    //月均还款 a×i×(1＋i)^n÷((1＋i)^n－1)   a-贷款本金总额 i-月利率 n-还款期数
                    //每月利息 (a×i^2×(1+i)^(n-1)) ÷ ((1＋i)^n-1)
                    int a = (int)(Float.parseFloat(loanTotal) * 10000); //贷款本金
                    double i = Float.parseFloat(loanRate) * 0.01 / 12;      //月利率
                    int n = loanTimeInt*12;                                 //总还款期数
                    double perTermMoney = a * i * Math.pow((1+i),n)/(Math.pow((1+i),n) - 1);    //月均还款
                    double[][] months = new double[n][4];
                    for (int t = 0; t < n; t++) {
                        months[t][0] = t + 1;
                        months[t][1] = perTermMoney;
                        months[t][2] = a * Math.pow(i, 2) * Math.pow((1 + i), t) / (Math.pow((1 + i), (t+1)) - 1);   //每月所还利息
                        months[t][3] = perTermMoney - months[t][2];        //每月所还本金
                    }
                    //总还款 每月还款 x 总还款期数 总支付利息 总还款-贷款本金总额
                    double total = perTermMoney * n;
                    double interest = total - a;
                    //展示计算结果
                    final AlertDialog calculatorDataDialog = new AlertDialog.Builder(HouseLoanActivity.this,R.style.Dialog_FS).setCancelable(true).create();
                    Window calculatorDataDialogWindow = calculatorDataDialog.getWindow();   //获取对话框window对象
                    calculatorDataDialog.show();
                    calculatorDataDialogWindow.setContentView(R.layout.calculator_data);
                    //设置贷款类型 还款总额和总支付利息
                    TextView loanTypeTextView = (TextView) calculatorDataDialogWindow.findViewById(R.id.loanType);
                    loanTypeTextView.setText(loanType);
                    TextView totalTextView = (TextView) calculatorDataDialogWindow.findViewById(R.id.total);
                    totalTextView.setText(String.format("%.1f",total) +" ( 元 )");
                    TextView interestTextView = (TextView) calculatorDataDialogWindow.findViewById(R.id.interest);
                    interestTextView.setText(String.format("%.1f",interest) +" ( 元 )");
                    /////
                    //关闭数据对话框
                    TextView close = (TextView) calculatorDataDialogWindow.findViewById(R.id.close);
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            calculatorDataDialog.dismiss();
                        }

                    });
                    //////

                    //动态计算并设置listview的高度
                    DisplayMetrics metrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(metrics);
                    TextView loanTips = (TextView)calculatorDataDialogWindow.findViewById(R.id.loanTips); //小提示
                    LinearLayout loanTitle = (LinearLayout)calculatorDataDialogWindow.findViewById(R.id.loanTitle); //标题
                    TableLayout loanSubTitle = (TableLayout)calculatorDataDialogWindow.findViewById(R.id.loanSubTitle); //副标题
                    ListView lv = (ListView) calculatorDataDialogWindow.findViewById(R.id.calculateDataListView);       //listview
                    LinearLayout loanFooter = (LinearLayout)calculatorDataDialogWindow.findViewById(R.id.loanFooter);   //底部

                    int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
                    int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
                    loanTitle.measure(w, h);
                    loanSubTitle.measure(w, h);
                    loanFooter.measure(w, h);
                    loanTips.measure(w, h);

                    double lvHeight = metrics.heightPixels - loanTips.getMeasuredHeight() - loanTitle.getMeasuredHeight() - loanSubTitle.getMeasuredHeight() - loanFooter.getMeasuredHeight() ;
                    ViewGroup.LayoutParams lvLayoutParams = lv.getLayoutParams();
                    lvLayoutParams.height = (int)lvHeight;
                    lv.setLayoutParams(lvLayoutParams);
                    //////

                    TableAdapter tableAdapter = new TableAdapter(HouseLoanActivity.this,months);
                    lv.setAdapter(tableAdapter);
                }
            }

            //动态计算ListView的高度 修正ListView在ScrollView里只显示一行的问题(废弃)
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
