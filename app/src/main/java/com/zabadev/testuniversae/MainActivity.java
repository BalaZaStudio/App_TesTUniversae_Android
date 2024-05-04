package com.zabadev.testuniversae;

import android.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.zabadev.testuniversae.QuestionAnswer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView questionTextView;
    TextView totalQuestionTextView;
    Button ansA, ansB, ansC, ansD;
    Button btn_submit;
    TextView TextEscaled;

    int score = 0;
    int totalQuestion = QuestionAnswer.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";
    boolean buttonsEnabled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalQuestionTextView = findViewById(R.id.total_question);
        questionTextView = findViewById(R.id.question);
        ansA = findViewById(R.id.ans_a);
        ansB = findViewById(R.id.ans_b);
        ansC = findViewById(R.id.ans_c);
        ansD = findViewById(R.id.ans_d);
        btn_submit = findViewById(R.id.btn_submit);
        TextEscaled = findViewById(R.id.titulo);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

        totalQuestionTextView.setText("Numero Preguntas: " + totalQuestion);

        loadNewQuestion();

        // Iniciar animación en buttonAnimated
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.gradient_translation);
        TextEscaled.startAnimation(animation);
    }

    private void loadNewQuestion() {
        resetButtonColors();
        buttonsEnabled = true;

        if (currentQuestionIndex == totalQuestion) {
            finishQuiz();
            return;
        }
        questionTextView.setText(QuestionAnswer.question[currentQuestionIndex]);
        ansA.setText(QuestionAnswer.choices[currentQuestionIndex][0]);
        ansB.setText(QuestionAnswer.choices[currentQuestionIndex][1]);
        ansC.setText(QuestionAnswer.choices[currentQuestionIndex][2]);
        ansD.setText(QuestionAnswer.choices[currentQuestionIndex][3]);

        selectedAnswer = "";
    }

    private void finishQuiz() {
        String passStatus;
        if (score >= totalQuestion * 0.6) {
            passStatus = "Passed";
        } else {
            passStatus = "Failed";
        }

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Numero de Aciertos -> " + score + " <- de  " + totalQuestion)
                .setPositiveButton("Reiniciar", ((dialog, i) -> {
                    restartQuiz();
                }))
                .setNegativeButton("Volver al Menú", ((dialog, i) -> {
                    Intent intent = new Intent(MainActivity.this, MainMenuActivity.class);
                    startActivity(intent);
                    finish();
                }))
                .setCancelable(false)
                .show();
    }


    private void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();
    }

    private void resetButtonColors() {
        ansA.setBackgroundColor(Color.TRANSPARENT);
        ansB.setBackgroundColor(Color.TRANSPARENT);
        ansC.setBackgroundColor(Color.TRANSPARENT);
        ansD.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void onClick(View view) {
        if (!buttonsEnabled) {
            return;
        }

        resetButtonColors();
        Button clickedButton = (Button) view;

        if (clickedButton.getId() == R.id.btn_submit) {
            buttonsEnabled = false;
            if (!selectedAnswer.isEmpty()) {
                if (selectedAnswer.equals(QuestionAnswer.correctAnswers[currentQuestionIndex])) {
                    score++;
                    // Mostrar la respuesta correcta en verde
                    showCorrectAnswer(Color.GREEN);
                } else {
                    // Mostrar la respuesta correcta en verde y la incorrecta en rojo
                    showCorrectAnswer(Color.GREEN);
                    // Cambiar el color del botón seleccionado a rojo si la respuesta es incorrecta
                    if (selectedAnswer.equals(ansA.getText().toString())) {
                        ansA.setBackgroundColor(Color.RED);
                    } else if (selectedAnswer.equals(ansB.getText().toString())) {
                        ansB.setBackgroundColor(Color.RED);
                    } else if (selectedAnswer.equals(ansC.getText().toString())) {
                        ansC.setBackgroundColor(Color.RED);
                    } else if (selectedAnswer.equals(ansD.getText().toString())) {
                        ansD.setBackgroundColor(Color.RED);
                    }
                }

                // Pasar a la siguiente pregunta después de 5 segundos
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        currentQuestionIndex++;
                        loadNewQuestion();
                        buttonsEnabled = true; // Habilitar los botones nuevamente
                    }
                }, 5000);
            } else {
                // Manejar caso en que no se selecciona ninguna respuesta
            }
        } else {
            selectedAnswer = clickedButton.getText().toString();
            // Resaltar opción seleccionada en amarillo
            clickedButton.setBackgroundColor(Color.YELLOW);
        }
    }


    private void showCorrectAnswer(int color) {
        if (QuestionAnswer.correctAnswers[currentQuestionIndex].equals(ansA.getText().toString())) {
            ansA.setBackgroundColor(color);
        } else if (QuestionAnswer.correctAnswers[currentQuestionIndex].equals(ansB.getText().toString())) {
            ansB.setBackgroundColor(color);
        } else if (QuestionAnswer.correctAnswers[currentQuestionIndex].equals(ansC.getText().toString())) {
            ansC.setBackgroundColor(color);
        } else if (QuestionAnswer.correctAnswers[currentQuestionIndex].equals(ansD.getText().toString())) {
            ansD.setBackgroundColor(color);
        }
    }
}
