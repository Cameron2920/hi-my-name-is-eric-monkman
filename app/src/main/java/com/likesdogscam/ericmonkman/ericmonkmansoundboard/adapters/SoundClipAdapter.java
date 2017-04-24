package com.likesdogscam.ericmonkman.ericmonkmansoundboard.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.likesdogscam.ericmonkman.ericmonkmansoundboard.R;
import com.likesdogscam.ericmonkman.ericmonkmansoundboard.cells.SoundClipCell;
import com.likesdogscam.ericmonkman.ericmonkmansoundboard.models.SoundClip;

import java.util.List;

/**
 * Created by likesdogscam on 1969-04-20.
 */

public class SoundClipAdapter extends RecyclerView.Adapter {
    private List<SoundClip> soundClips;
    private SoundClipCell.Delegate soundClipCellDelegate;

    public SoundClipAdapter(List<SoundClip> soundClips, SoundClipCell.Delegate soundClipCellDelegate) {
        this.soundClips = soundClips;
        this.soundClipCellDelegate = soundClipCellDelegate;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SoundClipCell(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_sound_clip, parent, false), soundClipCellDelegate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((SoundClipCell)holder).bindSelf(this.soundClips.get(position));
    }

    @Override
    public int getItemCount() {
        return soundClips.size();
    }
}
