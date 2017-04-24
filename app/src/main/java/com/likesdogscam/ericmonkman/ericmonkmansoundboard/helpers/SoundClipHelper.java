package com.likesdogscam.ericmonkman.ericmonkmansoundboard.helpers;

import android.util.Log;

import com.likesdogscam.ericmonkman.ericmonkmansoundboard.models.SoundClip;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

/**
 * Created by likesdogscam on 1969-04-20.
 */

public class SoundClipHelper {
    private static String TAG = "SoundClipHelper";

    public static List<SoundClip> LoadSoundClips(InputStream soundClipDescriptionInputStream, String soundClipDirectory){
        ArrayList<SoundClip> soundClips = new ArrayList<>();

        try {
            InputStreamReader csvStreamReader = new InputStreamReader(soundClipDescriptionInputStream);
            CSVReader csvReader = new CSVReader(csvStreamReader);
            String[] line;

            // throw away the header
            csvReader.readNext();

            while ((line = csvReader.readNext()) != null) {
                soundClips.add(new SoundClip(soundClipDirectory + "/" + line[0], line[1]));
            }
            return soundClips;
        }
        catch (IOException e){
            Log.e(TAG, "error parsing description csv", e);
        }
        return null;
    }
}
