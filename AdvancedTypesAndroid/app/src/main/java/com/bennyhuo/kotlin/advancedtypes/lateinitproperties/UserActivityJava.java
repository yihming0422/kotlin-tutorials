package com.bennyhuo.kotlin.advancedtypes.lateinitproperties;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bennyhuo.kotlin.advancedtypes.R;

public class UserActivityJava extends AppCompatActivity {

    private TextView nameView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        nameView = findViewById(R.id.nameView);
    }
}
