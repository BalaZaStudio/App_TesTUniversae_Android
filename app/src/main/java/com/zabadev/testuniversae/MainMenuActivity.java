package com.zabadev.testuniversae;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.start_button) {
            startQuizActivity();
        }
    }


    private void startQuizActivity() {
        Intent intent = new Intent(this, MainActivity.class); // Reemplaza MainActivity con el nombre de tu actividad del test de preguntas
        startActivity(intent);
    }
}
