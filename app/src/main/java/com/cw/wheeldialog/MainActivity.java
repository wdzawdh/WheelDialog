package com.cw.wheeldialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.cw.wheeldialog.wheeldialog.WheelDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WheelDialog wheelDialog = new WheelDialog(this);
        wheelDialog.setLabels(list);
        wheelDialog.setOnWheelSelectListener(new WheelDialog.OnWheelSelectListener() {
            @Override
            public void onClickOk(int index, String selectLabel) {
                Toast.makeText(getApplicationContext(), selectLabel, Toast.LENGTH_SHORT).show();
            }
        });
        wheelDialog.show();


    }

    public static List<String> list = new ArrayList<>();

    static {
        list.add("身份证");
        list.add("护照");
        list.add("军人证");
        list.add("少儿证");
        list.add("台胞证");
        list.add("港澳台通行证");
        list.add("户口本");
        list.add("其它");
    }
}
