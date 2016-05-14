package com.example.nikhil.f_47_antsgame;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity  {

    public long mLastMove;
    public int pressX,pressY, screenX, screenY;
    public Ants[] ant = new Ants[10];
    public RefreshHandler mRedrawHandler = new RefreshHandler();
    Bitmap bg, close ;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ballView(this));

        //Get SCREEN size
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
         screenX = metrics.widthPixels;
         screenY = metrics.heightPixels;

        ant[0] = new Ants( screenX, screenY, "ants0_", this);
        ant[1] = new Ants( screenX, screenY, "ants1_", this);
        ant[2] = new Ants( screenX, screenY, "ants2_", this);
        ant[3] = new Ants( screenX, screenY, "ants3_", this);
        ant[4] = new Ants( screenX, screenY, "ants4_", this);
        ant[5] = new Ants( screenX, screenY, "ants5_", this);
        ant[6] = new Ants( screenX, screenY, "ants6_", this);
        ant[7] = new Ants( screenX, screenY, "ants7_", this);
        ant[8] = new Ants( screenX, screenY, "ants8_", this);
        ant[9] = new Ants( screenX, screenY, "bee", this);

        bg = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        bg = Bitmap.createScaledBitmap(bg, screenX, screenY, false);

        close = BitmapFactory.decodeResource(getResources(), R.drawable.close);
        close = Bitmap.createScaledBitmap(close, 50, 50, false);

    }

    public void update()
    {
        long now = System.currentTimeMillis();
        if (now - mLastMove > 33) {
            setContentView(new ballView(this));
            mLastMove = now;
        }
        mRedrawHandler.sleep(33);
    }

    class ballView extends View {
        Canvas tempCanvas;
        public ballView(Context context)
        {
            super(context);
        }
        @Override
        public void onDraw(Canvas canvas) {

            //canvas.drawColor(Color.WHITE);

            canvas.drawBitmap(bg,0,0,null);
            for (int i = 0; i < ant.length; i++) {
                ant[i].Move();
                ant[i].Draw(canvas);
            }

            canvas.drawBitmap(close, screenX-close.getWidth(), 0 ,null);
            update();
        }
    }

    class RefreshHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            MainActivity.this.update();
        }
        public void sleep(long delayMillis) {
            this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {

        pressX = (int) event.getX();
        pressY = (int) event.getY();

        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {

            Toast.makeText(this,
                    "PressX ="+pressX+", PressY = "+pressY

                    , Toast.LENGTH_LONG).show();

            //Check App Close
            if(pressY > (screenX-close.getWidth()) && pressY < close.getHeight())
                finish();

            for (int i = 0; i < ant.length; i++) {
                if( ant[i].checkKill(pressX, pressY) )
                {
                   //Do Something
                }
            }
        }

    return  false;
    }

}



