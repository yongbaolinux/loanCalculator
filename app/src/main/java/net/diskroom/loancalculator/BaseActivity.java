package net.diskroom.loancalculator;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BaseActivity extends AppCompatActivity {

    private AlertDialog commonDialog;       //维护一个全局的风格统一的dialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 应用统一风格对话框
     */
    public void showDialog(String string){
        //维护一个公共对话框
        commonDialog = new AlertDialog.Builder(BaseActivity.this).setCancelable(false).create();
        Window commonDialogWindow = commonDialog.getWindow();
        //commonDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        commonDialog.show();
        commonDialogWindow.setContentView(R.layout.common_dialog);
        TextView commonDialogContent = (TextView)commonDialogWindow.findViewById(R.id.commonDialogContent);
        commonDialogContent.setText(string);
        TextView sure = (TextView) commonDialogWindow.findViewById(R.id._dialog_sure);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonDialog.dismiss();
            }
        });
    }

    /**
     * activity退出
     */
    public void stop(){

    }
}
