package ru.mavist.urava;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class What extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what);
    }

    public void gbavko(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}