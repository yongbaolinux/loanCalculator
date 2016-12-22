package net.diskroom.loancalculator;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

/**
 * Created by jsb-hdp-0 on 2016/12/21.
 */

public class MyApp extends Application {

    /**
     * 弹出计算结果页面
     *
     * @param context  上下文环境
     * @param a        贷款本金
     * @param i        贷款月利率
     * @param n        还款期数
     * @param loanType 还款方式 1等额本息 2等额本金
     */
    public void calculator(Context context, float a, double i, int n, int loanType){
        double total = 0;               //总还款金额
        double interest = 0;            //总支付利息
        double[][] months = new double[n][4];;              //还款详情
        if(loanType == 1) {
            double perTermMoney = a * i * Math.pow((1 + i), n) / (Math.pow((1 + i), n) - 1);    //月均还款
            for (int t = 0; t < n; t++) {
                months[t][0] = t + 1;
                months[t][1] = perTermMoney;
                months[t][2] = a * Math.pow(i, 2) * Math.pow((1 + i), t) / (Math.pow((1 + i), (t + 1)) - 1);   //每月所还利息
                months[t][3] = perTermMoney - months[t][2];        //每月所还本金
            }
            //总还款=每月还款 x 总还款期数  总支付利息=总还款-贷款本金总额
            total = perTermMoney * n;
            interest = total - a;
        } else if(loanType == 2){
            float perMonthLoan = a / n;     //每月支付本金;
            for (int t = 0; t < n; t++) {
                //LogUtils.v(Float.parseFloat(loanRate));
                months[t][0] = t + 1;
                months[t][1] = perMonthLoan + (a - t * perMonthLoan) * i;   //每月还款
                months[t][2] = (a - t * perMonthLoan) * i;                  //每月利息
                months[t][3] = perMonthLoan;                                                                //每月本金
                interest += months[t][2];
            }
            //LogUtils.v(months);
            total = a + interest;             //总还款金额
        }
        //展示计算结果
        final AlertDialog calculatorDataDialog = new AlertDialog.Builder(context, R.style.Dialog_FS).setCancelable(true).create();
        Window calculatorDataDialogWindow = calculatorDataDialog.getWindow();   //获取对话框window对象
        calculatorDataDialog.show();
        calculatorDataDialogWindow.setContentView(R.layout.calculator_data);
        //设置贷款类型 还款总额和总支付利息
        TextView loanTypeTextView = (TextView) calculatorDataDialogWindow.findViewById(R.id.loanType);
        loanTypeTextView.setText("等额本息");
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
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
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
        TableAdapter tableAdapter = new TableAdapter(context,months);
        lv.setAdapter(tableAdapter);
    }
}
