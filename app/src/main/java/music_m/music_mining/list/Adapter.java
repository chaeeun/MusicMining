package music_m.music_mining.list;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import music_m.music_mining.R;
import music_m.music_mining.application.ApplicationController;
import music_m.music_mining.model.PlayListResult;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by jongjookim on 2017. 1. 1..
 */

public class Adapter extends RecyclerView.Adapter<ViewHolder> {


    ArrayList<PlayListResult> PlayList;
    ApplicationController myApp;
    View.OnClickListener mOnclickListener; //희원
    //View.OnClickListener playOnclickListener; //희원
    //MyPlayList myList;
    Activity activity;

    public Adapter(ArrayList<PlayListResult> playList, View.OnClickListener mOnclickListener, Activity activity) {
        PlayList = playList;
        this.activity = activity;
        Log.i("tag", playList.size()+"aaa");

        //희원
        myApp = (ApplicationController)getApplicationContext();
        //myList = (MyPlayList)getApplicationContext();

        myApp.setMp_List(playList);
        myApp.setActivtiy(this.activity);
        //state = myApp.getState();
        this.mOnclickListener = mOnclickListener;

        //this.playOnclickListener = playOnclickListener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        final ViewHolder holder = new ViewHolder(v);

        /*희원*/
        v.setOnClickListener(mOnclickListener);
        ///////////////
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.title.setText(PlayList.get(position).getTitle());
        holder.musician_name.setText(PlayList.get(position).getMusicianName());
        Glide.with(getApplicationContext()).load(PlayList.get(position).getAlbumImageUrl()).into(holder.album_image);

        holder.position = position;
        //holder.play_btn.setOnClickListener(playOnclickListener);
        holder.play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myApp.getMp_state().pause();
                myApp.setMp_playChecker();
                myApp.setMp_state(holder.position);
                myApp.setMp_Next();
                myApp.playMp_state();
                ((MyPlayList)activity).barTitle.setText(myApp.getMp_List().get(holder.position).getTitle());
                ((MyPlayList)activity).barMusician.setText(myApp.getMp_List().get(holder.position).getMusicianName());
                Glide.with(getApplicationContext()).load(PlayList.get(position).getAlbumImageUrl()).into(((MyPlayList)activity).album_image);
                //myList.setControlBar();
            }
        });
/*
        holder.targetList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Song_Infomation.class);
                intent.putExtra("musicid", myApp.getMp_List().get(holder.position).getMusicId());
                getApplicationContext().startActivity(intent);
            }
        });*/


    }

    public void removeItem(int position) {
        PlayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, PlayList.size());
    }

    @Override
    public int getItemCount() {
        return PlayList.size();
    }


    /*
    View.OnClickListener mOnClickListener;

    public Adapter(ArrayList<PlayListResult> playList) {
        PlayList = playList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        itemView.setOnClickListener(mOnClickListener);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(PlayList.get(position).getTitle());
        holder.musician_name.setText(PlayList.get(position).getMusicianName());
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    */

}