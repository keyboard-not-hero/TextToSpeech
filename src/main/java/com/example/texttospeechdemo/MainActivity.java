package com.example.texttospeechdemo;

import android.graphics.Point;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{
    private TextToSpeech mTextToSpeech;
    private EditText mEditText;
    private Button mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Point point = new Point();
        //获得display的宽度
        getWindowManager().getDefaultDisplay().getSize(point);
        int width = point.x;
        mTextToSpeech = new TextToSpeech(this, this);
        mEditText = (EditText) findViewById(R.id.text);
        mEditText.setWidth((int) (width*0.8));
        mButton = (Button) findViewById(R.id.button);
        mButton.setWidth((int) (width*0.8));
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakOut();
            }
        });
    }

    private void speakOut() {
        String text = mEditText.getText().toString();
        mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onDestroy() {
        if(mTextToSpeech != null){
            mTextToSpeech.stop();
            mTextToSpeech.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){
            //设置语言
            int result = mTextToSpeech.setLanguage(Locale.ENGLISH);
            //设置语速
            mTextToSpeech.setPitch(1.2f);
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.d("MainActivity","language is not support");
            }else{
                //mButton.setEnabled(true);
                speakOut();
            }
        }else{
            Log.d("MainActivity","initialized failure");

        }
    }
}
