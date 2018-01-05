package com.example.magic.mooka;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CountDucks extends AppCompatActivity {

    MediaPlayer Buttonsound;
    MediaPlayer correctsound;
    MediaPlayer WrongAnswer;
    MediaPlayer Cry;
    static int count;
    static int id=1;
    int finish=0;
    int destroy=0;
    int correctResult;
    counter ct;
    NotificationChannel mychannel;
    Toast toastR;
    Toast toastwrong;
    Bitmap Correct;
    Boolean trueAnswer=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_ducks);


        //Gift inflater
        LayoutInflater myInflater=getLayoutInflater();
        View myView=myInflater.inflate(R.layout.gift,(ViewGroup) findViewById(R.id.GiftLayout));
        Toast toast=new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(myView);

        //right answer inflater
        View rightView=myInflater.inflate(R.layout.right,(ViewGroup) findViewById(R.id.right));
        toastR=new Toast(getApplicationContext());
        toastR.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
        toastR.setDuration(Toast.LENGTH_SHORT);
        toastR.setView(rightView);

        //wrong answer inflater
        View wrongView=myInflater.inflate(R.layout.wrong,(ViewGroup) findViewById(R.id.wrong));
        toastwrong=new Toast(getApplicationContext());
        toastwrong.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
        toastwrong.setDuration(Toast.LENGTH_SHORT);
        toastwrong.setView(wrongView);


        //initialize sound
        Buttonsound=MediaPlayer.create(CountDucks.this,R.raw.button);
        correctsound=MediaPlayer.create(CountDucks.this,R.raw.e);
        WrongAnswer=MediaPlayer.create(CountDucks.this,R.raw.wrong2);
        Cry=MediaPlayer.create(CountDucks.this,R.raw.cry);

        //initilize number images
        int[] NumberImage={R.drawable.arone,R.drawable.artwo,R.drawable.arthree,R.drawable.arfour,
                R.drawable.arfive,R.drawable.arsix,R.drawable.arseven,R.drawable.areight,R.drawable.arnine,
                R.drawable.arten};

        //get randum number
        Random rand= new Random();
        int randomNum= rand.nextInt(10)+1;
        correctResult=NumberImage[ (randomNum-1)];
        Drawable myDrawable = getResources().getDrawable(correctResult);
        Correct= ((BitmapDrawable) myDrawable).getBitmap();

        ArrayList<Integer> number = new ArrayList<Integer>();
        for (int i = 1; i <= 10; ++i)
            number.add(i);
        Collections.shuffle(number);


        //get random numbers for result
        int RandomResult1=number.get(1);
        int RandomResult2=number.get(5);
        //check if result equal any random number
        if(randomNum==RandomResult1)
            RandomResult1= number.get(2);
        if(randomNum==RandomResult2)
            RandomResult2= number.get(9);




        //view random results
        ArrayList<Integer> asd = new ArrayList<Integer>();

            asd.add( RandomResult1);
            asd.add( RandomResult2);
            asd.add( randomNum);
        Collections.shuffle(asd);

        ImageView Number1=findViewById(R.id.R1);
        Number1.setImageResource( NumberImage[asd.get(0)-1]);
        ImageView Number2=findViewById(R.id.R2);
        Number2.setImageResource(NumberImage[asd.get(1)-1]);
        ImageView Number3=findViewById(R.id.R3);
        Number3.setImageResource(NumberImage[asd.get(2)-1]);

        //ducks inflater
        GridView list=findViewById(R.id.grid);
        myAdapter duckAdapter=new myAdapter(randomNum);
        list.setAdapter(duckAdapter);

        //create counter
        ct =new counter(10000,100);
        ct.start();

        //create notifcation after 4 true results        String message=getResources().getString(R.string.good);
        NotificationCompat.Builder notif=new NotificationCompat.Builder(this,"1")
                .setSmallIcon(R.drawable.redbox)
                .setContentText("good")
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        NotificationManager manager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(count==4)
        {
            toast.show();
            manager.notify(id,notif.build());
            count=0;
            id++;
        }

      if(trueAnswer)
          PlayAgain();

    }
    class  myAdapter   extends BaseAdapter {
        int ducksNumber;
        public myAdapter(int ducksNumber) {
            this.ducksNumber=ducksNumber;
        }

        @Override
        public int getCount() {
            return ducksNumber;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater myInflate=getLayoutInflater();
            View myView=myInflate.inflate(R.layout.ducklayout,parent,false);
            ImageView duckView=myView.findViewById(R.id.duckimage);
            duckView.setImageResource(R.drawable.miniduck);
            return  myView;
        }
    }
    @Override
    protected void onDestroy() {

        destroy=1;
        stopAllSounds();
        super.onDestroy();

    }
    public void R1click(View view) {
        ImageView R1=findViewById(R.id.R1);
        Bitmap bmap = ((BitmapDrawable)R1.getDrawable()).getBitmap();
        if(bmap.sameAs(Correct))
        {
            correctsound.start();
            toastR.show();
            trueAnswer=true;}
        else
        {
            toastwrong.show();
            WrongAnswer.start();
            trueAnswer=false;
        }

    }
    public void R2click(View view) {

        ImageView R2=findViewById(R.id.R2);
        Bitmap bmap = ((BitmapDrawable)R2.getDrawable()).getBitmap();
        if(bmap.sameAs(Correct))
         {
            correctsound.start();
            toastR.show();
             trueAnswer=true;}
        else
        {
            toastwrong.show();
            WrongAnswer.start();
            trueAnswer=false;
        }

    }
    public void R3click(View view) {
        ImageView R3=findViewById(R.id.R3);
        Bitmap bmap = ((BitmapDrawable)R3.getDrawable()).getBitmap();
        if(bmap.sameAs(Correct))
          {
            correctsound.start();
            toastR.show();
              trueAnswer=true;}
        else
        {
            toastwrong.show();
            WrongAnswer.start();
            trueAnswer=false;}

    }
    public void PlayAgain() {

        stopAllSounds();
            count++;

        Intent i = new Intent(this,CountDucks.class);
        //the following 2 tags are for clearing the backStack and start fresh
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(i);

    }
    public void Home(View view) {
        Buttonsound.start();
        stopAllSounds();
        Intent SumIntent=new Intent(this,MainActivity.class);
        startActivity(SumIntent);
    }
    public class counter extends CountDownTimer {

        public counter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // if(finish==0&&destroy==0)countersound.start();
        }

        @Override
        public void onFinish() {
            if(finish==0)
                Cry.start();
        }
    }
    public void stopAllSounds(){
        Buttonsound.stop();
        correctsound.stop();
        WrongAnswer.stop();
        Cry.stop();

    }
}
