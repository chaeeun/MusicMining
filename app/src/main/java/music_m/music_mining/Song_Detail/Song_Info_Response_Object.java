package music_m.music_mining.Song_Detail;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by kki53 on 2017-01-04.
 */

public class Song_Info_Response_Object {
    int music_id;
    String title;
    String lyrics;
    String genre;
    int album_id;
    String album_name;
    String album_info;
    String album_image_url;
    String sale_date;
    String music_url;
    int likes;
    @SerializedName("composers") ArrayList<Song_Info_Response_Composers> infoComposers;

    public ArrayList<Song_Info_Response_Composers> getInfoComposers() {
        return infoComposers;
    }

    public String getTitle(){
        return title;
    }
    public String getLyrics(){
        return lyrics;
    }
    public String getAlbum_image_url(){
        return album_image_url;
    }
}
