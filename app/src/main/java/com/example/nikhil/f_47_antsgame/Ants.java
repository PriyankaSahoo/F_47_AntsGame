package com.example.nikhil.f_47_antsgame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.MediaPlayer;

public class Ants
{
    public int xPos, yPos, ySpeed, animationFrame = 0, screenX, screenY, deadForFrames = 255;
    public boolean  isAlive;
    public Bitmap[] frames = new Bitmap[5];
    public String fileNamePrefix;
    public Context c;
    public MediaPlayer hit;

    public Ants(int screenX, int screenY, String fileNamePrefix, Context c)
    {
         hit = MediaPlayer.create(c, R.raw.ant_5_0);

        this.screenX = screenX;
        this.screenY = screenY;
        this.fileNamePrefix = fileNamePrefix;
        this.c = c;

        reCreate();

        //Load Images
        for(int i=0; i< frames.length; i++)
        {
            int id = c.getResources().getIdentifier(fileNamePrefix+i, "drawable", c.getPackageName());
            frames[i] = BitmapFactory.decodeResource(c.getResources(), id);

            //ReSize
            float dpi = c.getResources().getDisplayMetrics().density;
            frames[i] = Bitmap.createScaledBitmap(frames[i], (int)(50*dpi) ,(int)(70*dpi) ,false);

            //Rotate
            Matrix matrix = new Matrix();
            matrix.postRotate(180);
            frames[i] = Bitmap.createBitmap(frames[i] , 0, 0, frames[i] .getWidth(), frames[i] .getHeight(), matrix, true);
        }
    }

    public void Move()
    {
        yPos += ySpeed;
        if(yPos > 2*screenY)
            reCreate();

        animationFrame++;
        if(animationFrame > 3)
            animationFrame = 0;

        if(isAlive==false)
        {
            deadForFrames--;
            if(deadForFrames<0)
                reCreate();;
        }
    }

    public void reCreate()
    {
        xPos = (int)(Math.random()*screenX);
        yPos = (int)(Math.random()*screenY) * -1;
        isAlive = true;
        deadForFrames = 255;
        ySpeed = 5 +(int)( Math.random()*30);
    }

    public void Draw(Canvas c)
    {
        if(isAlive)
             c.drawBitmap(frames[animationFrame], xPos, yPos, null);
        else {
            Paint p = new Paint();
            p.setColor(Color.argb(deadForFrames,0,0,0));
            c.drawBitmap(frames[4], xPos, yPos, p);
        }
    }

    public boolean checkKill(int pressX, int pressY)
    {
        if(     pressX > xPos && pressX < xPos + frames[0].getWidth() &&
                pressY > yPos && pressY < yPos + frames[0].getHeight())
        {
            isAlive = false;
            ySpeed = 0;

            if(!hit.isPlaying())
                hit.start();
           // hit.stop();
           // hit = null;

            return true;
        }
        return false;
    }
}