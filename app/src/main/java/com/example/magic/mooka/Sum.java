package com.example.magic.mooka;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
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

public class Sum extends AppCompatActivity {


    MediaPlayer Buttonsound;
    MediaPlayer correctsound;
    MediaPlayer WrongAnswer;
    MediaPlayer Cry;

    static int count;
    static int id=1;
    int finish=0;
    int destroy=0;
    String correctResult="";
    counter ct;
    NotificationChannel mychannel;
    Toast toastR;
    Toast toastwrong;
    Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum);
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
        Buttonsound=MediaPlayer.create(Sum.this,R.raw.button);
        correctsound=MediaPlayer.create(Sum.this,R.raw.malktrue);
        WrongAnswer=MediaPlayer.create(Sum.this,R.raw.wrong2);
        Cry=MediaPlayer.create(Sum.this,R.raw.cry);



        //create counter
        ct =new counter(10000,100);
        ct.start();

        //view Equation
        getEquation();



    }
    public void getEquation(){
        //initilize number images
        int[] NumberImage={R.drawable.arone,R.drawable.artwo,R.drawable.arthree,R.drawable.arfour,
                R.drawable.arfive,R.drawable.arsix,R.drawable.arseven,R.drawable.areight,R.drawable.arnine,
                R.drawable.arten};
        String[] myarray=getResources().getStringArray(R.array.numbers);

        //get random numbers for equation
        Random rand= new Random();
        int num1= rand.nextInt(10)+1;
        ImageView N1=findViewById(R.id.n1);
        N1.setImageResource(NumberImage[num1-1]);

        int num2= rand.nextInt(10)+1;
        ImageView N2=findViewById(R.id.n2);
        N2.setImageResource(NumberImage[num2-1]);


        //get random numbers for result
        int RandomResult1= rand.nextInt(20)+1;
        int RandomResult2= rand.nextInt(20)+1;

        //find correct result
        int result=num1+num2;

        //check if result equal any random number
        if(result==RandomResult1)
            RandomResult1= rand.nextInt(20)+1;
        else if (result==RandomResult2)
            RandomResult2= rand.nextInt(20)+1;

        //convert all to arabic
        String randnumResult1= getArabic(RandomResult1,myarray);
        String randnumResult2=getArabic(RandomResult2,myarray);
        String finalResult=getArabic(result,myarray);
        correctResult=finalResult;

        //view random results
        ArrayList<String> asd = new ArrayList<String>();

        asd.add( randnumResult1);
        asd.add( randnumResult2);
        asd.add( finalResult);
        Collections.shuffle(asd);
        TextView R1=findViewById(R.id.R1);
        TextView R2=findViewById(R.id.R2);
        TextView R3=findViewById(R.id.R3);
                R1.setText(asd.get(0));
                R2.setText(asd.get(1));
                R3.setText(asd.get(2));

    }
    @Override
    protected void onDestroy() {

        destroy=1;
        stopAllSounds();
        super.onDestroy();

    }


    public String getArabic(int num,String[] myarray){
        String number="";
        for(int i=0;i<21;i++)
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

    public void PlayAgain(View view) {

        Buttonsound.start();
        stopAllSounds();

        if(finish==1)
            count++;

        //create notifcation after 4 true results
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


        Intent i = new Intent(this,Sum.class);
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

    public void close(View view) {
        finish();
    }

    public class counter extends CountDownTimer{

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
