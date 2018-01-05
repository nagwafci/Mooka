package com.example.magic.mooka;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Subtraction extends AppCompatActivity {
    MediaPlayer Buttonsound;
    MediaPlayer correctsound;
    MediaPlayer WrongAnswer;
    MediaPlayer Cry;

    static int count;
    static int id=1;
    int finish=0;
    int destroy=0;
    String correctResult="";
    counterSub ct;
    NotificationChannel mychannel;
    Toast toast;
    Toast toastR;
    Toast toastwrong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtraction);
        LayoutInflater myInflater=getLayoutInflater();

        //Gift View
        View myView=myInflater.inflate(R.layout.gift,(ViewGroup) findViewById(R.id.GiftLayout));
        toast=new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(myView);

        //right answer View
        View rightView=myInflater.inflate(R.layout.right,(ViewGroup) findViewById(R.id.right));
        toastR=new Toast(getApplicationContext());
        toastR.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
        toastR.setDuration(Toast.LENGTH_LONG);
        toastR.setView(rightView);

        //wrong answer View
        View wrongView=myInflater.inflate(R.layout.wrong,(ViewGroup) findViewById(R.id.wrong));
        toastwrong=new Toast(getApplicationContext());
        toastwrong.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
        toastwrong.setDuration(Toast.LENGTH_LONG);
        toastwrong.setView(wrongView);

        //initialize sound
        Buttonsound=MediaPlayer.create(Subtraction.this,R.raw.button);
        correctsound=MediaPlayer.create(Subtraction.this,R.raw.malktrue);
        WrongAnswer=MediaPlayer.create(Subtraction.this,R.raw.wrong2);
        Cry=MediaPlayer.create(Subtraction.this,R.raw.cry);


        //create counter
        ct =new counterSub(10000,100);
        ct.start();

        //View equation
         getEquation();




    }
    public  void  StopAllSound(){
        Buttonsound.stop();
        correctsound.stop();
        WrongAnswer.stop();
        Cry.stop();
    }
    public  void  getEquation(){
        //initilize number images
        int[] NumberImage={R.drawable.arone,R.drawable.artwo,R.drawable.arthree,R.drawable.arfour,
                R.drawable.arfive,R.drawable.arsix,R.drawable.arseven,R.drawable.areight,R.drawable.arnine,
                R.drawable.arten};
        String[] myarray=getResources().getStringArray(R.array.numbers);

        //get random numbers for equation
        Random rand= new Random();
        int num1= rand.nextInt(10)+1;
        int num2= rand.nextInt(10)+1;
        //check num1>num2
        if( num1<num2) {
            int x = num1;
            num1 = num2;
            num2 = x;
        }

        ImageView N1=findViewById(R.id.n1);
        N1.setImageResource(NumberImage[num1-1]);


        ImageView N2=findViewById(R.id.n2);
        N2.setImageResource(NumberImage[num2-1]);


        //get random results
        int ran1=  rand.nextInt(20)+1;
        String randnumResult1= getArabic(ran1);
        int ran2= rand.nextInt(20)+1;
        String randnumResult2=getArabic(ran2);

        //find result
        int result=num1-num2;
        correctResult=getArabic(result);

        //view random results
        ArrayList<String> asd = new ArrayList<String>();

        asd.add( randnumResult1);
        asd.add( randnumResult2);
        asd.add(correctResult);
        Collections.shuffle(asd);
        TextView R1=findViewById(R.id.R1);
        TextView R2=findViewById(R.id.R2);
        TextView R3=findViewById(R.id.R3);
        R1.setText(asd.get(0));
        R2.setText(asd.get(1));
        R3.setText(asd.get(2));
    }
    public String getArabic(int num){
        String number="";
        String[] myarray=getResources().getStringArray(R.array.numbers);
        for(int i=0;i<20;i++)
        {
            if(num==i)
                number=myarray[i];
        }
        return number;
    }



    public void R1click(View view) {

        TextView R1=findViewById(R.id.R1);
        String t=R1.getText().toString();

        if(correctResult==t)
        {
            toastR.show();
            correctsound.start();

            finish=1;}
        else
        {
            toastwrong.show();
            WrongAnswer.start();}

    }

    public void R2click(View view) {

        TextView R2=findViewById(R.id.R2);
        String t=R2.getText().toString();

        if(correctResult==t)
        {
            toastR.show();
            correctsound.start();
            finish=1;}
        else
        {
            toastwrong.show();
            WrongAnswer.start();}
    }
    public void R3click(View view) {
        TextView R3=findViewById(R.id.R3);
        String t=R3.getText().toString();

        if(correctResult==t)
        {
            toastR.show();
            correctsound.start();
            finish=1;}
        else
        {
            toastwrong.show();
            WrongAnswer.start();}
    }
    @Override
    protected void onDestroy() {
        StopAllSound();
        destroy=1;
        super.onDestroy();


    }

    public void PlayAgain(View view)
    {
        Buttonsound.start();
        StopAllSound();
        if(finish==1)
            count++;
        //create notification after 4 true results
        String message=getResources().getString(R.string.good);
        NotificationCompat.Builder notif=new NotificationCompat.Builder(this,"1")
                .setSmallIcon(R.drawable.redbox)
                .setContentText(message)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        NotificationManager manager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(count==4)
        {
            toast.show();
            manager.notify(id,notif.build());
            count=0;
            id++;
        }

        Intent i = new Intent(this,Subtraction.class);
        //the following 2 tags are for clearing the backStack and start fresh
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(i);


    }

    public void Home(View view) {
        Buttonsound.start();
        StopAllSound();
        Intent SumIntent=new Intent(this,MainActivity.class);
        startActivity(SumIntent);
    }

    public void close(View view) {
        finish();
    }

    public class counterSub extends CountDownTimer {

        public counterSub(long millisInFuture, long countDownInterval) {
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

}
