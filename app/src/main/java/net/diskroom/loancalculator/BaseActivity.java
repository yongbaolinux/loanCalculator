package net.diskroom.loancalculator;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

public class BaseActivity extends AppCompatActivity {

    private AlertDialog commonDialog;       //维护一个全局的风格统一的dialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 应用统一风格对话框
     */
    public void showDialog(View view){
        //维护一个公共的 loopview 对话框
        commonDialog = new AlertDialog.Builder(BaseActivity.this).setCancelable(false).create();
        Window commonDialogWindow = commonDialog.getWindow();
        //commonDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        commonDialog.show();
        commonDialogWindow.setContentView(R.layout.loan_time_wheel_container);
        LinearLayout commonDialogLinearLayout = (LinearLayout)commonDialogWindow.findViewById(R.id.wheelviewContainer);
        commonDialogLinearLayout.addView(view);
    }

    /**
     * activity退出
     */
    public void stop(){

    }
}
