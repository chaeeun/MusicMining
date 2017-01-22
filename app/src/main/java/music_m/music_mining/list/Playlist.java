package music_m.music_mining.list;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import music_m.music_mining.model.PlayListResult;

/**
 * Created by jongjookim on 2017. 1. 3..
 */

public class Playlist {
    @SerializedName("err") int err;
    @SerializedName("data") ArrayList<PlayListResult> musics;


    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public ArrayList<PlayListResult> getMusics() {
        return musics;
    }
    public void setMusics(ArrayList<PlayListResult> musics) {
        this.musics = musics;
    }
}
