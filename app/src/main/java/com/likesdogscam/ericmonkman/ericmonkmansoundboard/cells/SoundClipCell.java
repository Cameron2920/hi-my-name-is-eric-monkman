package com.likesdogscam.ericmonkman.ericmonkmansoundboard.cells;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.likesdogscam.ericmonkman.ericmonkmansoundboard.R;
import com.likesdogscam.ericmonkman.ericmonkmansoundboard.models.SoundClip;

/**
 * Created by likesdogscam on 1969-04-20.
 */

public class SoundClipCell extends RecyclerView.ViewHolder {
    private Button playSoundClipButton;

    private SoundClip soundClip;
    private Delegate delegate;

    public interface Delegate {
        void onPlaySoundClipButton(SoundClip soundClip);
    }

    public SoundClipCell(View itemView, Delegate delegate) {
        super(itemView);
        this.delegate = delegate;
        this.playSoundClipButton = (Button) itemView.findViewById(R.id.playSoundClipButton);
    }

    public void bindSelf(final SoundClip soundClip){
        this.soundClip = soundClip;
        this.playSoundClipButton.setText(this.soundClip.getDescription());
        this.playSoundClipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(delegate != null){
                    delegate.onPlaySoundClipButton(soundClip);
                }
            }
        });
    }
}
