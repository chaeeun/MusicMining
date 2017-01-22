package music_m.music_mining.model;

import java.util.ArrayList;

import music_m.music_mining.model.MovieListData;

/**
 * Created by ichaeeun on 2017. 1. 5..
 */

public class MovieResult {
    public int err;
    public ArrayList<MovieListData> data;

    public ArrayList<MovieListData> getData() {
        return data;
    }
}
