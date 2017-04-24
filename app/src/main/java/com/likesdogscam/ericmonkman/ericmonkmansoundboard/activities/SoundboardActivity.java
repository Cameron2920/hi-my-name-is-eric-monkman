package com.likesdogscam.ericmonkman.ericmonkmansoundboard.activities;

import android.annotation.TargetApi;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.likesdogscam.ericmonkman.ericmonkmansoundboard.R;
import com.likesdogscam.ericmonkman.ericmonkmansoundboard.adapters.SoundClipAdapter;
import com.likesdogscam.ericmonkman.ericmonkmansoundboard.cells.SoundClipCell;
import com.likesdogscam.ericmonkman.ericmonkmansoundboard.helpers.SoundClipHelper;
import com.likesdogscam.ericmonkman.ericmonkmansoundboard.models.SoundClip;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SoundboardActivity extends AppCompatActivity implements SoundClipCell.Delegate{
    private static String TAG = "SoundboardActivity";
    private static String SOUND_CLIP_DESCRIPTION_PATH = "eric_monker_clip_descriptions.csv";
    private static String SOUND_CLIP_DIRECTORY = "sound_clips";
    private static int MAX_AUDIO_STREAMS = 20;

    private RecyclerView soundClipRecyclerView;
    private SoundClipAdapter soundClipRecyclerViewAdapter;
    private RecyclerView.LayoutManager soundClipRecyclerViewLayoutMananger;

    private List<SoundClip> soundClips;
    private Map<Integer, SoundClip> sampleIdSoundClipMap;
    private SoundPool soundPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soundboard);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        sampleIdSoundClipMap = new HashMap<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createSoundPool();
        } else {
            createDeprecatedSoundPool();
        }
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                SoundClip soundClip = sampleIdSoundClipMap.get(sampleId);

                if(soundClip != null){
                    soundClip.setSampleLoaded(true);
                    soundPool.play(soundClip.getSampleId(), 1, 1, 0, 0, 1);
                }
                else {
                    Log.e(TAG, "sound clip not found");
                }
            }
        });
        loadSoundClips();
        soundClipRecyclerView = (RecyclerView) findViewById(R.id.soundClipRecyclerView);
        soundClipRecyclerView.setHasFixedSize(true);
        soundClipRecyclerView.setNestedScrollingEnabled(false);
        soundClipRecyclerViewLayoutMananger = new LinearLayoutManager(this);
        soundClipRecyclerView.setLayoutManager(soundClipRecyclerViewLayoutMananger);

        if(soundClips != null) {
            soundClipRecyclerViewAdapter = new SoundClipAdapter(soundClips, this);
            soundClipRecyclerView.setAdapter(soundClipRecyclerViewAdapter);
        }
        else {
            Log.e(TAG, "sound clips is null");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(soundPool != null){
            soundPool.release();
            soundPool = null;
        }
    }

    @Override
    public void onPlaySoundClipButton(SoundClip soundClip) {
        if(soundClip.getSampleId() != -1) {
            if (soundClip.isSampleLoaded()) {
                soundPool.play(soundClip.getSampleId(), 1, 1, 0, 0, 1);
                Log.d(TAG, "playing sound clip " + soundClip.getDescription());
            }
        }
        else{
            try {
                soundClip.setSampleId(soundPool.load(getAssets().openFd(soundClip.getFilename()), 0));
                sampleIdSoundClipMap.put(soundClip.getSampleId(), soundClip);
            }
            catch (IOException e){
                Log.e(TAG, "unable to load sound clip", e);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void createSoundPool(){
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .setMaxStreams(MAX_AUDIO_STREAMS)
                .build();
    }

    @SuppressWarnings("deprecation")
    protected void createDeprecatedSoundPool(){
        soundPool = new SoundPool(MAX_AUDIO_STREAMS, AudioManager.STREAM_MUSIC, 0);
    }

    private void loadSoundClips(){
        try {
            soundClips = SoundClipHelper.LoadSoundClips(getAssets().open(SOUND_CLIP_DESCRIPTION_PATH), SOUND_CLIP_DIRECTORY);
        }
        catch (IOException e){
            Log.e(TAG, "unable to load soundclips", e);
        }
    }
}
