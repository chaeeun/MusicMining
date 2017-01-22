package music_m.music_mining.Song_Detail;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kki53 on 2017-01-04.
 */

public class Song_Info_Response {
    @SerializedName("err") int err;
    @SerializedName("data")
    Song_Info_Response_Object infoData;

    public Song_Info_Response_Object getData() {
        return infoData;
    }
}
