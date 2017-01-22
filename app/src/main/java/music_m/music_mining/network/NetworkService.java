package music_m.music_mining.network;

import music_m.music_mining.Song_Detail.Song_Info_Response;
import music_m.music_mining.list.Playlist;
import music_m.music_mining.make_id.FindPasswordRequestObject;
import music_m.music_mining.make_id.FindPasswordResponseObject;
import music_m.music_mining.make_id.LoginRequestObject;
import music_m.music_mining.make_id.LoginResponseObject;
import music_m.music_mining.model.MainResult;
import music_m.music_mining.model.MovieResult;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by jongjookim on 2016. 12. 29..
 */

public interface NetworkService {
    @FormUrlEncoded
    @POST("/login") //메써드 (요청경로)
    Call<MainResult> getMainData(@Field("user_id") String user_id,
                                 @Field("passwd") String passwd,
                                 @Field("case") int caseNum); // 요청
    @POST("/login/regist")
    Call<LoginResponseObject> requestLogin(@Body LoginRequestObject object);

    @POST("login/findpw")
    Call<FindPasswordResponseObject> requestFindPassword(@Body FindPasswordRequestObject object);


    @GET("/playlists")
    Call<Playlist> getAllList();

    /** 플레이리스트 불러오기 */
    @GET("/playlists/{user_id}")
    Call<Playlist> getList(@Path("user_id") String userId);

    /* 곡상세보기*/
    @GET("/musics/{music_id}")
    Call<Song_Info_Response> getSonginfo(@Path("music_id") int music_id); //요청
    /** 홈 영상 불러오기*/
    @GET("/movies")
    Call<MovieResult> getMovieData();

    /** 플레이리스트에 음악추가*/
    @FormUrlEncoded
    @POST("/playlists") //메써드 (요청경로)
    Call<MainResult> requestAddMusic(@Field("user_id") String user_id,
                                     @Field("music_id") int music_id);

    /** 플레이리스트 음악 삭제 */
    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "/playlists", hasBody = true)
    Call<MainResult> deleteMusic(@Field("user_id") String user_id,
                                 @Field("music_id") int music_id);


/*
@GET("api/{email}/{password}")
     Call<Login> authenticate(@Path("email") String email,
                              @Path("password") String password);
                              @POST("api/{email}/{password}")
     Call<Login> registration(@Path("email") String email, @Path("password") String password);

 */


    //
/*
    @Multipart
    @GET("/playlists")
    Call<PlayListResult> registerImgNotice(@Part MultipartBody.Part file,
                                           @Part("title") RequestBody title,
                                           @Part("musician_name") RequestBody musician_name
    );
*/
}