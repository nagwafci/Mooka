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

public class CountAR extends AppCompatActivity {

    static int n=0;
    MediaPlayer sound;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_ar);



        int[] NumberSounds={R.raw.n1,R.raw.n2,R.raw.n3,R.raw.n4,R.raw.n5,R.raw.n6
                ,R.raw.n7,R.raw.n8,R.raw.n9,R.raw.n10};
        sound=MediaPlayer.create(CountAR.this,NumberSounds[n]);

        int[] NumberImage={R.drawable.arone,R.drawable.artwo,R.drawable.arthree,R.drawable.arfour,
                R.drawable.arfive,R.drawable.arsix,R.drawable.arseven,R.drawable.areight,R.drawable.arnine,
                R.drawable.arten};
        ImageView Number=findViewById(R.id.Number);
        Number.setImageResource(NumberImage[n]);
        sound.start();

        //ducks inflater
        GridView list=findViewById(R.id.grid);
        myAdapter duckAdapter=new myAdapter(n+1);
        list.setAdapter(duckAdapter);

    }
    public void Home(View view) {
        sound.stop();
        Intent SumIntent=new Intent(this,MainActivity.class);
        startActivity(SumIntent);
    }

    public void BeforeNumber(View view) {
        sound.stop();
        if(n<9&&n>0)
            n--;
        else
            n=0;
        Intent i = new Intent(this,CountAR.class);
        //the following 2 tags are for clearing the backStack and start fresh
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(i);

    }

    public void NextNumber(View view) {
        sound.stop();
        if(n<9)
            n++;
        else if(n==9)
            n=0;
        Intent i = new Intent(this,CountAR.class);
        //the following 2 tags are for clearing the backStack and start fresh
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(i);
    }

    public void ARspeaker(View view) {
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
