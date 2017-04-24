package com.likesdogscam.ericmonkman.ericmonkmansoundboard.models;

/**
 * Created by likesdogscam on 1969-04-20.
 */

public class SoundClip {
    private String filename;

    private String description;

    private int sampleId;

    private boolean isSampleLoaded;

    public SoundClip(String filename, String description) {
        this.filename = filename;
        this.description = description;
        this.isSampleLoaded = false;
        this.sampleId = -1;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSampleId() {
        return sampleId;
    }

    public void setSampleId(int sampleId) {
        this.sampleId = sampleId;
    }

    public boolean isSampleLoaded() {
        return isSampleLoaded;
    }

    public void setSampleLoaded(boolean sampleLoaded) {
        isSampleLoaded = sampleLoaded;
    }
}
