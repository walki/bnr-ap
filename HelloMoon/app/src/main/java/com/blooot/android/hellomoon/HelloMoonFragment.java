package com.blooot.android.hellomoon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by rjw on 11/19/2014.
 */
public class HelloMoonFragment extends Fragment {

    private AudioPlayer mPlayer = new AudioPlayer();

    private Button mPlayButton;
    private Button mStopButton;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_hello_moon, container, false);
        mPlayButton = (Button) v.findViewById(R.id.hellomoon_playButton);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayer.isStopped()){
                    mPlayer.play(getActivity());
                    mPlayButton.setText(R.string.hellomoon_pause);
                }
                else if (mPlayer.isPlaying()){
                    mPlayer.pause();
                    mPlayButton.setText(R.string.hellomoon_play);
                }
                else if (mPlayer.isPaused()){
                    mPlayer.restart();
                    mPlayButton.setText(R.string.hellomoon_pause);
                }


            }
        });

        mStopButton = (Button) v.findViewById(R.id.hellomoon_stopButton);
        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.stop();
            }
        });
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlayer.stop();
    }
}
