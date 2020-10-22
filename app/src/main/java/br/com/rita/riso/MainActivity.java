package br.com.rita.riso;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageView catImv;
    private ImageView btnPlayPause;
    private TextView messageTv;
    private MediaPlayer mediaPlayer;
    private Boolean isPause = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initControl();
    }

    private void initUI(){
        catImv = findViewById(R.id.imv_cat);
        btnPlayPause = findViewById(R.id.btn_play_pause);
        messageTv = findViewById(R.id.tv_message);
    }

    private void initControl(){
        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer == null || isPause) {
                    play();
                } else {
                    pause();
                }
            }
        });
    }

    private void play() {
        catImv.setBackground(getResources().getDrawable(R.drawable.cat_cry));
        btnPlayPause.setBackground(getResources().getDrawable(R.mipmap.ic_pause));
        messageTv.setVisibility(View.INVISIBLE);
        catImv.setVisibility(View.VISIBLE);
        if(!isPause) {
            mediaPlayer = MediaPlayer.create(this, R.raw.rita_song);
        }
        isPause = false;
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stop();
            }
        });
        mediaPlayer.start();
    }

    private void pause() {
        if(mediaPlayer != null) {
            catImv.setBackground(getResources().getDrawable(R.drawable.buzz_meme));
            btnPlayPause.setBackground(getResources().getDrawable(R.mipmap.ic_play));
            mediaPlayer.pause();
            isPause = true;
        }
    }

    private void stop() {
        if(mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            isPause = false;
            catImv.setBackground(getResources().getDrawable(R.drawable.cat_raivoso));
            messageTv.setVisibility(View.VISIBLE);
            btnPlayPause.setBackground(getResources().getDrawable(R.mipmap.ic_play));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_direitos:
                Uri uri = Uri.parse(getResources().getString(R.string.url_direitos_autorais));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
