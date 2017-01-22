package music_m.music_mining.Song_Detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import music_m.music_mining.R;
import music_m.music_mining.application.ApplicationController;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Song_Infomation extends AppCompatActivity {

    //Retrofit retrofitSongInfo;
    private ImageButton xButton;
    private ImageButton likeButton;
    private ImageButton shareButton;
    private TextView contentTextview;
    private TextView infoTextview;
    private TextView songname;
    private TextView singername;
    private ImageView profileimage;
    private TextView singername2;
    private TextView song_singer;
    private TextView textLyrics;
    private TextView writers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song__infomation);

        Intent intent = getIntent();
        int musicid = intent.getExtras().getInt("musicid");

        int err = 0;
        String title; //= "24시 코인 노래방";
        String lyrics;// = "술을 많이 마신 걸까  고향이 그리운 걸까  늦은 밤 까닭 없는  외로움에 슬퍼진 걸까  오랫동안 만나왔던  여자와 헤어진 걸까  하지 말았어야 했던  말을 후회하는 걸까  등록금을 걱정하다  휴학을 해 버린 걸까  호기롭게 시작한 사업이  실패를 한 걸까  꿈을 포기해야 하는  상황을 마주친 걸까  그냥 돌아서기엔  아쉬움이 큰가  차가운 유리창  너머에는 그가 울고  유리창 표면에  그를 닮은 내가 있고  우리에겐 눈물의  이유가 너무 많고  세탁기는 무심하게  윙윙 돌고  모두 잠든 새벽 세 시  코인 빨래방에 앉아  세탁기 소리에 숨어  흐느끼고 있는 남자  그 모습을 바라보다  술에 취해 걸어가다  문득 나도 그를 따라  울고 싶어지네  차가운 유리창  너머에는 그가 울고  유리창 표면에  그를 닮은 내가 있고  우리에겐 눈물의  이유가 너무 많고  세탁기는 무심하게  윙윙 돌고  차가운 유리창  너머에는 그가 울고  유리창 표면에  그를 닮은 내가 있고  우리에겐 눈물의  이유가 너무 많고  세탁기는 무심하게  윙윙 돌고  세탁기는 무심하게  윙윙 돌고  세탁기는 무심하게  윙윙 돌고  세탁기는 무심하게  윙윙 돌고  세탁기는 무심하게  윙윙 돌고  세탁기는 무심하게  윙윙 돌고  세탁기는 무심하게  윙윙 돌고  세탁기는 무심하게  윙윙 돌고  세탁기는 무심하게  윙윙 돌고  세탁기는 무심하게 윙윙 돌고 세탁기는 무심하게 윙윙 돌고";
        String album_image_url;// = "https://s3.ap-northeast-2.amazonaws.com/sopt-yuuuunmi/MusicMinning/album_kang.png";
        String musician_name;// = "강백수";

        Call<Song_Info_Response> songinfo = ApplicationController.getInstance().getNetworkService().getSonginfo(musicid);
        songinfo.enqueue(new Callback<Song_Info_Response>() {
            @Override
            public void onResponse(Call<Song_Info_Response> call, Response<Song_Info_Response> response) {
                if (response.isSuccessful()) {
                    //err[0] = response.body().err;
                    if(response.body().err == 0){
                        //Toast.makeText(Song_Infomation.this, "서버연동성공", Toast.LENGTH_SHORT).show();
                        String title;
                        String lyrics;
                        String album_image_url;
                        String musician_name;
                        Song_Info_Response sir = response.body();
                        Song_Info_Response_Object sir_object = sir.getData();
                        title = sir_object.getTitle();
                        lyrics = sir_object.getLyrics();
                        album_image_url = sir_object.getAlbum_image_url();
                        musician_name = sir_object.getInfoComposers().get(0).getMusician_name();


                        songname = (TextView)findViewById(R.id.songname);
                        songname.setText(title);
                        singername = (TextView)findViewById(R.id.singername);
                        singername.setText(musician_name);
                        profileimage = (ImageView)findViewById(R.id.profileimage);
                        //Glide.with(this).load(album_image_url).into(profileimage);
                        Glide.with(getApplicationContext()).load(album_image_url).into(profileimage);
                        singername2 = (TextView)findViewById(R.id.singername2);
                        singername2.setText(musician_name);

                        song_singer = (TextView)findViewById(R.id.song_singer);
                        song_singer.setText(title + " - " + musician_name);

                        textLyrics = (TextView)findViewById(R.id.textLyrics);
                        textLyrics.setText(lyrics);

                        writers = (TextView)findViewById(R.id.writers);
                        writers.setText("작사: "+musician_name+"  |  작곡: "+musician_name);
                    }
                    else{
                        Toast.makeText(Song_Infomation.this, "서버연동실패", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
            @Override
            public void onFailure (Call<Song_Info_Response> call, Throwable t){
                Toast.makeText(Song_Infomation.this, "서버연동안됨", Toast.LENGTH_SHORT).show();
                return;
            }
        });

        //디비를 가져왔다고 가정.

/*
        if(err[0] == 0){


            songname = (TextView)findViewById(R.id.songname);
            songname.setText(title);
            singername = (TextView)findViewById(R.id.singername);
            singername.setText(musician_name);
            //profileimage = (ImageView)findViewById(R.id.profileimage);
            //profileimage();
            singername2 = (TextView)findViewById(R.id.singername2);
            singername2.setText(musician_name);

            song_singer = (TextView)findViewById(R.id.song_singer);
            song_singer.setText(title + " - " + musician_name);

            textLyrics = (TextView)findViewById(R.id.textLyrics);
            textLyrics.setText(lyrics);
        }*/

        //엑스박스 토스트 메시지
        xButton = (ImageButton)findViewById(R.id.xButton);
        xButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Song_Infomation.this, "현재 서비스 준비중입니다", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        //좋아요 버튼 토스트 메시지
        likeButton = (ImageButton) findViewById(R.id.likeButton);
        likeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(Song_Infomation.this, "♥좋아요♥가 전달 되었습니다!", Toast.LENGTH_SHORT).show();
            }
        });

        //공유 버튼 토스트 메시지
        shareButton = (ImageButton) findViewById(R.id.shareButton);
        shareButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(Song_Infomation.this, "현재 서비스 준비중입니다", Toast.LENGTH_SHORT).show();
            }
        });

        //컨텐츠 텍스트뷰 토스트 메시지
        contentTextview = (TextView) findViewById(R.id.contentTextview);
        contentTextview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(Song_Infomation.this, "현재 서비스 준비중입니다", Toast.LENGTH_SHORT).show();
            }
        });

        //인포 텍스트뷰 토스트 메시지
        infoTextview = (TextView) findViewById(R.id.infoTextview);
        infoTextview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(Song_Infomation.this, "현재 서비스 준비중입니다", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
