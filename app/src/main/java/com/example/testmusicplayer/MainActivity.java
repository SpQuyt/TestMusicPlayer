package com.example.testmusicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView tvTitle;
    private Button btnPrevious;
    private Button btnPlay;
    private Button btnNext;
    private MediaPlayer mediaPlayer;
    private ArrayList<Song> arrSong = new ArrayList<Song>();
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findId();
        addSong();
        tvTitle.setText(arrSong.get(position).getTitle());

        btnPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    btnPlay.setBackgroundResource(R.drawable.play);
                    mediaPlayer.pause();
                } else {
                    btnPlay.setBackgroundResource(R.drawable.pause);
                    mediaPlayer.start();
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (position > arrSong.size()) {
                    position = 0;
                }
                mediaPlayer.stop();
                position++;
                tvTitle.setText(arrSong.get(position).getTitle());
                mediaPlayer = MediaPlayer.create(MainActivity.this, arrSong.get(position).getFile());
            }
        });
    }

    private void findId() {
        tvTitle = findViewById(R.id.tvTitle);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnPlay = findViewById(R.id.btnPlay);
        btnNext = findViewById(R.id.btnNext);
    }

    private void addSong(){
        arrSong.add(new Song("ChungTaKhongThuocVeNhau", R.raw.chungtakhongthuocvenhau));
        arrSong.add(new Song("BuongDoiTayNhauRa", R.raw.buongdoitaynhaura));
        arrSong.add(new Song("KhongPhaiDangVuaDau", R.raw.khongphaidangvuadau));
        mediaPlayer = MediaPlayer.create(MainActivity.this, arrSong.get(position).getFile());
    }
}
