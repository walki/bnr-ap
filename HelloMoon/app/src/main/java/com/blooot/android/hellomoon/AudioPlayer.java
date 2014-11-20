package com.blooot.android.hellomoon;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by roger_000 on 11/19/2014.
 */
public class AudioPlayer {

    private enum State{
        NONE,
        PLAYING,
        PAUSED,
    }


    private MediaPlayer mPlayer;

    public State getState() {
        return mState;
    }

    public boolean isPlaying()
    {
        return mState == State.PLAYING;
    }

    public boolean isStopped()
    {
        return mState == State.NONE;
    }

    public boolean isPaused()
    {
        return mState == State.PAUSED;
    }

    private State mState;

    public void stop(){
        mState = State.NONE;
        if (mPlayer != null){
            mPlayer.release();
            mPlayer = null;
        }

    }

    public void pause(){
        mState = State.PAUSED;
        mPlayer.pause();
    }

    public void restart(){
        mState = State.PLAYING;
        mPlayer.start();
    }

    public void play(Context c){
        mState = State.PLAYING;

        mPlayer = MediaPlayer.create(c, R.raw.one_small_step);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stop();
            }
        });

        mPlayer.start();
        mState = State.PLAYING;
    }


}
