package com.bharanee.android.bakersmanual;
import android.app.Fragment;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import networkcalls.NetworkTasks;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailStepFragment extends Fragment {
    public DetailStepFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        if(Util.SDK_INT>23)
            initializePlayer(videouri);
    }

    @BindView(R.id.playerview) SimpleExoPlayerView mPlayerView;
    SimpleExoPlayer mExoplayer;
    Context context;
    public Uri videouri;
    long videoPos=0;
    Boolean getState=true;
    @BindView(R.id.detailedStep_txt) TextView detailedSteps;
    @BindView(R.id.prev_button)ImageView prevStep;
    @BindView(R.id.next_button) ImageView nextStep;
    @OnClick(R.id.prev_button)
    public void prev_Button(){goToPrevStep();}
    @OnClick(R.id.next_button)
    public void next_Button(){goToNextStep();}
    private int itemId,stepPosition,windowIndex=0;

    public void setData(int itemId,int stepPosition,Context context){
        this.itemId=itemId;
        this.stepPosition=stepPosition;
        this.context=context;
        setUrl();

    }
    public void setUrl(){
        String url= NetworkTasks.items.get(itemId).getVideoUrls().get(stepPosition);
        videouri=Uri.parse(url);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState!=null){
            itemId=savedInstanceState.getInt(getString(R.string.itemIdParam));
            stepPosition=savedInstanceState.getInt(getString(R.string.stepPositionParam));
            setUrl();
            context=getActivity();
            videoPos=savedInstanceState.getLong(getString(R.string.videoPosParam));
            windowIndex=savedInstanceState.getInt("windowIndex");
            getState=savedInstanceState.getBoolean("playState");
        }
        View view=inflater.inflate(R.layout.fragment_detailed_steps,container,false);
        ButterKnife.bind(this,view);
        mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(),R.drawable.default_baking));
        return view;
    }

    private void goToNextStep() {
        int no_of_steps=NetworkTasks.items.get(itemId).getSteps().size();
        if (stepPosition<(no_of_steps - 1)){
            stepPosition++;
            setUrl();
            releasePlayer();
            initializePlayer(videouri);
        }
    }

    private void goToPrevStep() {
        if (stepPosition > 1){
            stepPosition--;
            setUrl();
            releasePlayer();
            initializePlayer(videouri);
        }
    }

    public void initializePlayer(Uri uri) {
        if (uri!=null){
        TrackSelector trackSelector=new DefaultTrackSelector();
        LoadControl loader=new DefaultLoadControl();
        mExoplayer= ExoPlayerFactory.newSimpleInstance(context,trackSelector,loader);
        mPlayerView.setPlayer(mExoplayer);
        String userAgent=Util.getUserAgent(context, context.getString(R.string.app_name));
        MediaSource mediaSource=new ExtractorMediaSource(uri,new DefaultDataSourceFactory(context,userAgent),
                new DefaultExtractorsFactory(),null,null);
        mExoplayer.prepare(mediaSource);
        mExoplayer.setPlayWhenReady(getState);
        mExoplayer.seekTo(windowIndex,videoPos);
        videoPos=0;windowIndex=0;
        }
        detailedSteps.setText(NetworkTasks.items.get(itemId).getSteps().get(stepPosition));
    }
    public void releasePlayer(){
        if (mExoplayer!=null){
            mExoplayer.setPlayWhenReady(false);
        mExoplayer.stop();
        mExoplayer.release();}
        mExoplayer=null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT<=23||mExoplayer==null)
        initializePlayer(videouri);
    }

    @Override
    public void onPause() {
        videoPos=mExoplayer.getCurrentPosition();
        windowIndex=mExoplayer.getCurrentWindowIndex();
        getState=mExoplayer.getPlayWhenReady();
        if (Util.SDK_INT<=23)
        {releasePlayer();}
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT>23)releasePlayer();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(getString(R.string.itemIdParam),itemId);
        outState.putLong(getString(R.string.videoPosParam),videoPos);
        outState.putInt(getString(R.string.stepPositionParam),stepPosition);
        outState.putInt("windowIndex",windowIndex);
        outState.putBoolean("playState",getState);
        DetailsPage.stepPosition=stepPosition;
        super.onSaveInstanceState(outState);
    }
}
