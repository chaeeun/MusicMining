package music_m.music_mining.model;

/**
 * Created by ichaeeun on 2017. 1. 5..
 */

public class MovieListData {
    int music_id;
    int album_id;
    String title;
    String highlight_video_url;
    String thumbnail_url;
    String album_name;
    String album_image_url;
    int musicain_id;
    String musician_name;

    public MovieListData(int music_id, int album_id, String title, String highlight_video_url, String thumbnail_url, String album_name, String album_image_url, int musicain_id, String musician_name) {
        this.music_id = music_id;
        this.album_id = album_id;
        this.title = title;
        this.highlight_video_url = highlight_video_url;
        this.thumbnail_url = thumbnail_url;
        this.album_name = album_name;
        this.album_image_url = album_image_url;
        this.musicain_id = musicain_id;
        this.musician_name = musician_name;
    }

    public int getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
    }

    public String getAlbum_image_url() {
        return album_image_url;
    }

    public void setAlbum_image_url(String album_image_url) {
        this.album_image_url = album_image_url;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public String getHighlight_video_url() {
        return highlight_video_url;
    }

    public void setHighlight_video_url(String highlight_video_url) {
        this.highlight_video_url = highlight_video_url;
    }

    public int getMusic_id() {
        return music_id;
    }

    public void setMusic_id(int music_id) {
        this.music_id = music_id;
    }

    public int getMusicain_id() {
        return musicain_id;
    }

    public void setMusicain_id(int musicain_id) {
        this.musicain_id = musicain_id;
    }

    public String getMusician_name() {
        return musician_name;
    }

    public void setMusician_name(String musician_name) {
        this.musician_name = musician_name;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
