//package com.sleepapp.SleepTracker.app.music;
//
//import android.os.Bundle;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.sleepapp.SleepTracker.R;
//import com.sleepapp.SleepTracker.base.BaseFragment;
//
//public class MusicFragment extends BaseFragment {
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_music, null);
////        ImageView image = (ImageView) view.findViewById(R.id.dis_sample);             //使用ImageView显示logo
////        image.setImageResource(R.drawable.dis_sample);
//        return view;
//
//    }
//}


package com.sleepapp.SleepTracker.app.music;

        import android.content.res.AssetFileDescriptor;
        import android.content.res.AssetManager;
        import android.media.AudioManager;
        import android.media.MediaPlayer;
        import android.net.Uri;
        import android.os.Bundle;
        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import android.view.LayoutInflater;
        import android.view.MenuInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.content.IntentFilter;
        import android.widget.ImageButton;
        import android.widget.TextView;
        import androidx.fragment.app.Fragment;
        import com.raizlabs.android.dbflow.sql.language.Operator;
        import com.sleepapp.SleepTracker.R;
        import com.sleepapp.SleepTracker.base.BaseFragment;

        import java.io.File;
        import java.io.IOException;
        import java.io.InputStream;
        import java.net.URI;
        import java.util.Vector;

public class MusicFragment extends BaseFragment {

    private View view;
    private MediaPlayer mediaplayer = new MediaPlayer();
    TextView title;
    ImageButton play, last, next;

    // 1-未播放 2-正在播放 3-暂停
    int status = 3;
    int current = 0;
    int musicslen = 0;
    String[] musicpath;
    String titlestr;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_music, container, false);
        return view;
    }

    public void openmusic(int current){
        try {
            if(mediaplayer !=null){
                mediaplayer.reset();
            }
            AssetManager am = getResources().getAssets();
            musicpath = am.list("");
            musicslen = musicpath.length;

            AssetFileDescriptor afd = getResources().getAssets().openFd(musicpath[current]);
            mediaplayer.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            mediaplayer.setOnPreparedListener(preparedListener);
            mediaplayer.prepareAsync();
            mediaplayer.setLooping(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    MediaPlayer.OnPreparedListener preparedListener = new MediaPlayer.OnPreparedListener() {

        @Override
        public void onPrepared(MediaPlayer mp) {
            mediaplayer.start();
        }
    };

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        play = (ImageButton) view.findViewById(R.id.play);
        last = (ImageButton) view.findViewById(R.id.last);
        next = (ImageButton) view.findViewById(R.id.next);
        title = view.findViewById(R.id.title);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //正在播放
                if(status==2) {
                    mediaplayer.pause();
                    play.setImageResource(R.drawable.music_play);
                    status=3;
                }
                //暂停中
                else if(status==3) {
                    openmusic(current);
                    play.setImageResource(R.drawable.music_pause);
                    status=2;
                }
            }
        });
        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(current==0){
                    current=musicslen-3;
                }
                else {
                    current--;
                }
                openmusic(current);
                status=2;
                titlestr = musicpath[current].substring(0,musicpath[current].length()-4);
                title.setText(titlestr);
                //title.setText(musicpath[current]);
                play.setImageResource(R.drawable.music_pause);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(current==(musicslen-3)){
                    current=0;
                }
                else {
                    current++;
                }
                openmusic(current);
                status=2;
                titlestr = musicpath[current].substring(0,musicpath[current].length()-4);
                title.setText(titlestr);
                //title.setText(musicpath[current]);
                play.setImageResource(R.drawable.music_pause);
            }
        });
    }
}