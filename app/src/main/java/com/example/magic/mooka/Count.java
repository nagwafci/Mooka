package com.example.magic.mooka;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class Count extends AppCompatActivity {
    static int number=0;
    MediaPlayer sound;
    int[] NumberSounds={R.raw.zero,R.raw.one,R.raw.two,R.raw.three,R.raw.four,R.raw.five
            ,R.raw.six,R.raw.seven,R.raw.eight,R.raw.nine,R.raw.ten};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        int[] NumberImage={R.drawable.zero,R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.four,
                R.drawable.five,R.drawable.six,R.drawable.seven,R.drawable.eight,R.drawable.nine,R.drawable.ten};
        ImageView txt=findViewById(R.id.Number);
        txt.setImageResource(NumberImage[number]);
        sound=MediaPlayer.create(Count.this,NumberSounds[number]);
        sound.start();
        //ducks inflater
        GridView list=findViewById(R.id.grid);
        myAdapter duckAdapter=new myAdapter(number);
        list.setAdapter(duckAdapter);

    }

    public void Home(View view) {
        sound.stop();
        Intent SumIntent=new Intent(this,MainActivity.class);
        startActivity(SumIntent);
    }
    public void Next(View view) {

        sound.stop();
       if(number<10)
           number++;
       else
           number=0;
        Intent i = new Intent(this,Count.class);
        //the following 2 tags are for clearing the backStack and start fresh
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(i);
    }
    public void Before(View view) {

        sound.stop();
        if(number<=10&&number>0)
            number--;
        else
            number=0;
        Intent i = new Intent(this,Count.class);
        //the following 2 tags are for clearing the backStack and start fresh
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(i);
    }

    public void speaker(View view) {
        sound.start();
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
}
