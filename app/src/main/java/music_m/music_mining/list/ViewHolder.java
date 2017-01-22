package music_m.music_mining.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import music_m.music_mining.R;

/**
 * Created by jongjookim on 2017. 1. 1..
 */

public class ViewHolder extends RecyclerView.ViewHolder {

    //MediaPlayer player;//희원추가
    //int playCheck = 0;
    int position;

    public TextView title;
    public TextView musician_name;
    public ImageView album_image;
    public ImageView play_btn;
    public LinearLayout targetList;
    public ViewHolder(View itemView) {
        super(itemView);

        title = (TextView)itemView.findViewById(R.id.title);
        musician_name = (TextView)itemView.findViewById(R.id.musician_name);
        album_image =(ImageView) itemView.findViewById(R.id.album_image);
        play_btn = (ImageView) itemView.findViewById(R.id.play_Btn);
        targetList = (LinearLayout) itemView.findViewById(R.id.targetList);
/*
        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });*/
    }
}
