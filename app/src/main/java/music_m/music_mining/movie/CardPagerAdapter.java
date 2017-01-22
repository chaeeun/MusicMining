package music_m.music_mining.movie;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tedcoder.wkvideoplayer.dlna.engine.DLNAContainer;
import com.android.tedcoder.wkvideoplayer.dlna.service.DLNAService;
import com.android.tedcoder.wkvideoplayer.model.Video;
import com.android.tedcoder.wkvideoplayer.model.VideoUrl;
import com.android.tedcoder.wkvideoplayer.view.SuperVideoPlayer;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import music_m.music_mining.R;
import music_m.music_mining.application.ApplicationController;
import music_m.music_mining.model.MainResult;
import music_m.music_mining.model.MovieListData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<MovieListData> mData;
    private float mBaseElevation;
    private ShadowTransformer mFragmentCardShadowTransformer;
    private View mPlayBtnView;
    private SuperVideoPlayer mSuperVideoPlayer;
    private String user_id;
    private List<Drawable> thumnailImages;
    FrameLayout thumnail;
    Bitmap thumnailBitmap;

    public CardPagerAdapter() {

    }

    public CardPagerAdapter(String user_id) {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
        thumnailImages = new ArrayList<>();
        thumnailImages.add(getApplicationContext().getResources().getDrawable(R.drawable.thumnail_1));
        thumnailImages.add(getApplicationContext().getResources().getDrawable(R.drawable.thumnail_2));
        thumnailImages.add(getApplicationContext().getResources().getDrawable(R.drawable.thumnail_3));
        thumnailImages.add(getApplicationContext().getResources().getDrawable(R.drawable.thumnail_4));
        thumnailImages.add(getApplicationContext().getResources().getDrawable(R.drawable.thumnail_5));
        thumnailImages.add(getApplicationContext().getResources().getDrawable(R.drawable.thumnail_6));
        thumnailImages.add(getApplicationContext().getResources().getDrawable(R.drawable.thumnail_7));
        thumnailImages.add(getApplicationContext().getResources().getDrawable(R.drawable.thumnail_8));
        thumnailImages.add(getApplicationContext().getResources().getDrawable(R.drawable.thumnail_9));
        thumnailImages.add(getApplicationContext().getResources().getDrawable(R.drawable.thumnail_10));


        this.user_id = user_id;
    }

    public void addCardItem(ArrayList<MovieListData> items) {


        for(MovieListData item : items) {
            mViews.add(null);
            mData.add(item);
        }
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }


    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.adapter, container, false);
        container.addView(view);
        try {
            bind(mData.get(position), view,position);

        } catch (IOException e) {
            e.printStackTrace();
        }


        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);

        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);
        mViews.set(position, null);
    }



    private void bind(final MovieListData item, View view, final int position) throws IOException {
        final SuperVideoPlayer mSuperVideoPlayer = (SuperVideoPlayer) view.findViewById(R.id.video);
        mPlayBtnView = view.findViewById(R.id.play_btn);
        ImageView albumImage = (ImageView) view.findViewById(R.id.album_image);
        FrameLayout thumnail = (FrameLayout) view.findViewById(R.id.thumnail);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView albumName = (TextView) view.findViewById(R.id.album_name);
        TextView musicianName = (TextView) view.findViewById(R.id.musician_name);
        ImageButton addMusicToPlayList = (ImageButton) view.findViewById(R.id.addMusicToPlayList);


        Glide.with(getApplicationContext())
                .load(item.getAlbum_image_url())
                .into(albumImage);

        thumnail.setBackground(thumnailImages.get(position));


        title.setText(item.getTitle());
        albumName.setText(item.getAlbum_name());
        musicianName.setText(item.getMusician_name());
        mPlayBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayBtnView.setVisibility(View.GONE);
                mSuperVideoPlayer.setVisibility(View.VISIBLE);
                mSuperVideoPlayer.setAutoHideController(true);

                Video video = new Video();
                VideoUrl videoUrl1 = new VideoUrl();
                videoUrl1.setFormatUrl(item.getHighlight_video_url());
                ArrayList<VideoUrl> arrayList1 = new ArrayList<>();
                arrayList1.add(videoUrl1);
                video.setVideoName("백승환");
                video.setVideoUrl(arrayList1);


                ArrayList<Video> videoArrayList = new ArrayList<>();
                videoArrayList.add(video);
                mSuperVideoPlayer.loadMultipleVideo(videoArrayList,0,0,0);
            }
        });
        SuperVideoPlayer.VideoPlayCallbackImpl mVideoPlayCallback = new SuperVideoPlayer.VideoPlayCallbackImpl() {
            @Override
            public void onCloseVideo() {
                mSuperVideoPlayer.close();
                mPlayBtnView.setVisibility(View.VISIBLE);
                mSuperVideoPlayer.setVisibility(View.GONE);

            }

            @Override
            public void onSwitchPageType() {
                stopDLNAService();
            }

            @Override
            public void onPlayFinish() {
                mSuperVideoPlayer.close();
                stopDLNAService();
            }
        };


        addMusicToPlayList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<MainResult> mService = ApplicationController.getInstance().getNetworkService().requestAddMusic(user_id, item.getMusic_id());
                mService.enqueue(new Callback<MainResult>() {
                    @Override
                    public void onResponse(Call<MainResult> call, Response<MainResult> response) {
                        if(response.isSuccessful()) {
                            if(response.body().err == 0) {
                                Toast.makeText(getApplicationContext(), "음악을 플레이리스트에 추가했습니다" , Toast.LENGTH_SHORT).show();
                            }else if(response.body().err == 1) {
                                Toast.makeText(getApplicationContext(), "음악 추가 실패" , Toast.LENGTH_SHORT).show();
                            }else if (response.body().err == 2) {
                                Toast.makeText(getApplicationContext(), "이미 추가하셨습니다" , Toast.LENGTH_SHORT).show();
                            }


                        }
                    }

                    @Override
                    public void onFailure(Call<MainResult> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "음악 추가 실패" , Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



        mSuperVideoPlayer.setVideoPlayCallback(mVideoPlayCallback);
        startDLNAService();
    };



    private void startDLNAService() {
        // Clear the device container.
        DLNAContainer.getInstance().clear();
        Intent intent = new Intent(getApplicationContext(), DLNAService.class);
        getApplicationContext().startService(intent);
    }

    private void stopDLNAService() {
        Intent intent = new Intent(getApplicationContext(), DLNAService.class);
        getApplicationContext().stopService(intent);
    }




}