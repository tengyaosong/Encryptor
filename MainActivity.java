package edu.gatech.seclass.sdpencryptor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Arrays;
import java.util.stream.IntStream;

public class MainActivity extends AppCompatActivity {

    private EditText plainText;
    private EditText alphaKey;
    private EditText betaKey;
    private Button buttonEncipher;
    private EditText resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        plainText = (EditText) findViewById(R.id.plaintextID);
        alphaKey = (EditText) findViewById(R.id.alphaKeyID);
        alphaKey.setText("0");
        betaKey = (EditText) findViewById(R.id.betaKeyID);
        betaKey.setText("0");
        buttonEncipher = (Button) findViewById(R.id.encipherButtonID);
        resultText = (EditText) findViewById(R.id.ciphertextID);
    }

    public void handleClick (View view) {
        String output = "";
        String input = plainText.getText().toString();
        String alphaText = alphaKey.getText().toString();
        String betaText = betaKey.getText().toString();


        int alpha = Integer.parseInt(alphaKey.getText().toString());
        int beta = Integer.parseInt(betaKey.getText().toString());

        int[] alphaValue = {1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25};
        int[] betaValue = IntStream.range(1, 26).toArray();

        if (input.length() == 0 || input.matches("[0-9]+")) {
            plainText.setError("Invalid Plaintext");
        }
        if (Arrays.stream(alphaValue).noneMatch(x -> x == alpha) ||
                TextUtils.isEmpty(alphaText)) {
            alphaKey.setError("Invalid Alpha Key");
        }
        if (Arrays.stream(betaValue).noneMatch(x -> x == beta) ||
                TextUtils.isEmpty(betaText)) {
            betaKey.setError("Invalid Beta Key");
        }
        if (buttonEncipher.isPressed()) {
            output = encryptor(input, alpha, beta);
        }resultText.setText(output);
    }

    private String encryptor(String input, int alpha, int beta) {
        StringBuilder sb = new StringBuilder();
        char[] chars = input.toCharArray();
        for (char aChar : chars) {
            if (Character.isLetter(aChar)) {
                if (Character.isLowerCase(aChar)) {
                    int position = aChar - 'a';
                    int encryptedNum = (alpha * position + beta) % 26;
                    char encryptedLetter = (char) (encryptedNum + 'a');
                    sb.append(encryptedLetter);
                } else {
                    int position = aChar - 'A';
                    int encryptedNum = (alpha * position + beta) % 26;
                    char encryptedLetter = (char) (encryptedNum + 'A');
                    sb.append(encryptedLetter);
                }
            } else {
                sb.append(aChar);
            }
        }
        return sb.toString();
    }
}
