package com.example.cuieney.savedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.feetsdk.android.FeetSdk;
import com.feetsdk.android.common.utils.SystemInfoUtil;
import com.feetsdk.android.feetsdk.ui.FwController;

public class MainActivity extends AppCompatActivity {

    private FwController feetUiController;
    private Button add;
    private ToggleButton button;
    private boolean checked;
    private TextView textView;
    private View close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add = ((Button) findViewById(R.id.add));
        button = ((ToggleButton) findViewById(R.id.toggle));
        textView = (TextView) findViewById(R.id.btn);
        close = findViewById(R.id.close);

        feetUiController = FeetSdk.getFeetUiController();
        feetUiController.setLocation(600);

        button.setOnCheckedChangeListener((buttonView, isChecked) -> {
            checked = isChecked;
            textView.setText(isChecked + "");
            feetUiController.setAutoBpm(isChecked, MainActivity.this);
        });
        button.setChecked(true);
        add.setOnClickListener(v -> {
            int bpm = Integer.parseInt(add.getText().toString());
            if (bpm > 200) {
                bpm = 120;
            } else {
                bpm += 5;
            }
            add.setText(bpm + "");
            if (!checked) {
                feetUiController.setBpm(bpm);
            }
        });

        add.setOnClickListener(v -> {
            int bpm = Integer.parseInt(add.getText().toString());
            if (bpm > 200) {
                bpm = 120;
            } else {
                bpm += 5;
            }
            add.setText(bpm + "");
            if (!checked) {
                feetUiController.setBpm(bpm);
            }
        });

        textView.setOnClickListener(v -> feetUiController.show(MainActivity.this));

        close.setOnClickListener(v -> feetUiController.remove());
        close.setVisibility(View.GONE);

    }
}
