package com.example.cuieney.savedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.ToggleButton;

import com.feetsdk.android.FeetSdk;
import com.feetsdk.android.feetsdk.ui.FwController;

public class MainActivity extends AppCompatActivity {

    private FwController feetUiController;
    private Button add;
    private ToggleButton button;
    private boolean checked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add = ((Button) findViewById(R.id.add));
        button = ((ToggleButton) findViewById(R.id.toggle));
        feetUiController = FeetSdk.getFeetUiController();
        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checked = isChecked;
                feetUiController.setAutoBpm(isChecked,MainActivity.this);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int bpm = Integer.parseInt(add.getText().toString());
                if (bpm > 200) {
                    bpm = 120;
                } else {
                    bpm += 5;
                }
                add.setText(bpm + "");
                if (checked) {
                    feetUiController.setBpm(bpm);
                }
            }
        });


        feetUiController.setLocation(500);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                feetUiController.show(MainActivity.this);
            }
        });
    }
}
