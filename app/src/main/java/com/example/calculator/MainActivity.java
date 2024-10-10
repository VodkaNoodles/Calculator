// SAUL RODRIGUEZ-TAPIA



package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView resultTV, solutionTV;
    MaterialButton buttonC, buttonBracketOpen, buttonBracketClose;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonMult, buttonAdd, buttonSub, buttonDiv, buttonEqual;
    MaterialButton buttonAC, buttonDot;
    /**
     * Called on start. This is where most initialization should go.
     * Enables edge-to-edge display, handles system bars
     * (e.g., status bar and navigation bar) using window insets.
     *
     * @param savedInstanceState If the activity is being restarted after previously being shut down,
     *                           this Bundle contains the data it most recently supplied.
     *                           Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        resultTV = findViewById(R.id.result_tv);

        solutionTV = findViewById(R.id.solution_tv);

        assignID(buttonC,R.id.button_c);
        assignID(buttonBracketOpen,R.id.button_open_bracket);
        assignID(buttonBracketClose,R.id.button_closed_bracket);
        assignID(button0,R.id.button_zero);
        assignID(button1,R.id.button_1);
        assignID(button2,R.id.button_2);
        assignID(button3,R.id.button_3);
        assignID(button4,R.id.button_4);
        assignID(button5,R.id.button_5);
        assignID(button6,R.id.button_6);
        assignID(button7,R.id.button_7);
        assignID(button8,R.id.button_8);
        assignID(button9,R.id.button_9);

        assignID(buttonMult,R.id.button_multiplication);
        assignID(buttonAdd,R.id.button_addition);
        assignID(buttonSub,R.id.button_subtraction);
        assignID(buttonDiv,R.id.button_divison);
        assignID(buttonEqual,R.id.button_equals);

        assignID(buttonAC,R.id.button_AC);
        assignID(buttonDot,R.id.button_dot);



    }


    /**
     * Assigns an ID to a MaterialButton and sets the current activity as its click listener.
     *
     * <p>This method finds a button by its resource ID and sets up the click listener to
     * be handled by the current activity\</p>
     *
     * @param btn The MaterialButton to be assigned.
     * @param id The resource ID used to find the button in the layout.
     */
    void assignID(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);

    }
    /**
     * Handles click events for the buttons in the calculator UI. Based on the button clicked,
     * this method updates the solution and result TextViews.
     *
     * <p>This method processes different button actions like clearing the input ("AC"),
     * deleting the last character ("C"), and evaluating the expression ("=").
     * It concatenates the clicked button's text to the current input for other buttons.</p>
     *
     * @param view The view that was clicked, which should be a MaterialButton representing
     *             a calculator button.
     */
    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalucate = solutionTV.getText().toString();

        if (buttonText.equals("AC")){
            solutionTV.setText("");
            resultTV.setText("0");
            return;

        }
        if(buttonText.equals("=")){
            solutionTV.setText(resultTV.getText());
            return;

        }
        if(buttonText.equals("C")){
            dataToCalucate = dataToCalucate.substring(0,dataToCalucate.length()-1);
        }else{
            dataToCalucate = dataToCalucate+buttonText;
        }
        solutionTV.setText(dataToCalucate);

        String finalResult = getResults(dataToCalucate);

        if(!finalResult.equals("Err")){
            resultTV.setText(finalResult);
        }


    }

    /**
     * Evaluates the given string expression as a JavaScript expression and returns the result.
     *
     * <p>This method uses the Rhino engine to execute JavaScript code. If the expression
     * is valid, it returns the result as a string. In case of an error during evaluation,
     * it returns "Err".</p>
     *
     * @param data The string containing the mathematical expression to be evaluated.
     * @return The result of the evaluated expression as a string, or "Err" if an error occurs.
     */
    String getResults(String data){
        try{
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            return context.evaluateString(scriptable,data, "Javascript", 1, null).toString();
        }catch(Exception e){
            return "Err";
        }
    }
}