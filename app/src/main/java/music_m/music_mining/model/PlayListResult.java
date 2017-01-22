package music_m.music_mining.model;

import com.google.gson.annotations.SerializedName;
/**
 * Created by jongjookim on 2017. 1. 2..
 */

public class PlayListResult {
    @SerializedName("music_id") int musicId;
    @SerializedName("musician_name") String musicianName;
    String title;
    @SerializedName("music_url") String musicUrl;
    @SerializedName("album_image_url") String albumImageUrl;
    @SerializedName("featuring_musician_name") String featuringMusicianName;

    public int getMusicId() {
        return musicId;
    }

    public void setMusicId(int musicId) {
        this.musicId = musicId;
    }

    public String getMusicianName() {
        return musicianName;
    }

    public void setMusicianName(String musicianName) {
        this.musicianName = musicianName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMusicUrl() {
        return musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }

    public String getAlbumImageUrl() {
        return albumImageUrl;
    }

    public void setAlbumImageUrl(String albumImageUrl) {
        this.albumImageUrl = albumImageUrl;
    }

    public String getFeaturingMusicianName() {
        return featuringMusicianName;
    }

    public void setFeaturingMusicianName(String featuringMusicianName) {
        this.featuringMusicianName = featuringMusicianName;
    }
}
