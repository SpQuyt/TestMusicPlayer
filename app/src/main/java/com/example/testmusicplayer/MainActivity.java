package com.example.testmusicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView tvTitle, textTimeCurrent, textTimeTotal;
    private Button btnPrevious, btnNext, btnPlay;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    private ArrayList<Song> arrSong = new ArrayList<Song>();
    private final Handler timer = new Handler();
    private int position = 0;
    private Runnable updatePerSecond = new Runnable() {
        @Override
        public void run() {
            int length = mediaPlayer.getCurrentPosition();
            seekBar.setProgress(length);
            textTimeCurrent.setText(intToTimeString(length));
            timer.postDelayed(this, 1000);
        }
    };
    private ObjectAnimator rotateAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.rotate);

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

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && mediaPlayer != null) {
                    mediaPlayer.seekTo(progress);
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        rotateAnimator.pause();
                        resumeSong();
                    }
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private String intToTimeString(int ms) {
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
        return formatter.format(ms);
    }

    private void findId() {
        tvTitle = findViewById(R.id.tvTitle);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnPlay = findViewById(R.id.btnPlay);
        btnNext = findViewById(R.id.btnNext);
        seekBar = findViewById(R.id.seekBar);
        textTimeCurrent = findViewById(R.id.textTimeCurrent);
        textTimeTotal = findViewById(R.id.textTimeTotal);
    }

    private void addSong(){
        arrSong.add(new Song("ChungTaKhongThuocVeNhau", R.raw.chungtakhongthuocvenhau));
        arrSong.add(new Song("BuongDoiTayNhauRa", R.raw.buongdoitaynhaura));
        arrSong.add(new Song("KhongPhaiDangVuaDau", R.raw.khongphaidangvuadau));
        mediaPlayer = MediaPlayer.create(MainActivity.this, arrSong.get(position).getFile());
    }

    private void resumeSong() {
        int length = mediaPlayer.getCurrentPosition();
        mediaPlayer.seekTo(length);
        mediaPlayer.start();
        rotateAnimator.resume();
        btnPlay.setBackgroundResource(android.R.drawable.ic_media_pause);
        tvTitle.setText(arrSong.get(position).getTitle());
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setProgress(length);
        timer.postDelayed(updatePerSecond, 1000);
    }
}
