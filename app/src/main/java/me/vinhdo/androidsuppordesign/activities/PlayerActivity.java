package me.vinhdo.androidsuppordesign.activities;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.print.PrintAttributes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.loopj.android.http.TextHttpResponseHandler;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.VideoView;
import me.vinhdo.androidsuppordesign.R;
import me.vinhdo.androidsuppordesign.api.loopj.RestClient;
import me.vinhdo.androidsuppordesign.api.loopj.parse.JSONConvert;
import me.vinhdo.androidsuppordesign.models.MoviePlay;
import me.vinhdo.androidsuppordesign.models.Resolution;
import me.vinhdo.androidsuppordesign.models.ResponseModel;
import me.vinhdo.androidsuppordesign.utils.StringUtil;

public class PlayerActivity extends BaseActivty {

    @Bind(R.id.player_surface)
    VideoView videoView;
    @Bind(R.id.player_overlay_seekbar)
    SeekBar seekBar;
    @Bind(R.id.player_overlay_title)
    TextView mTitle;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;
    @Bind(R.id.player_container)
    FrameLayout playerContainer;
    @Bind(R.id.player_overlay_time)
    TextView curTime;
    @Bind(R.id.player_overlay_length)
    TextView tvLength;
    @Bind(R.id.subtitles_surface)
    TextView subtitleSurface;
    @Bind(R.id.control_container)
    RelativeLayout controlContainer;
    @Bind(R.id.player_overlay_play)
    ImageButton mPlayBtn;

    private boolean mControllerShowingStatus;
    private int mProgress;
    private ArrayList<Resolution> resolutions;

    private Handler handler;
    private boolean isLock;
    private Handler mMessageHandler;
    private Handler mMessageSeekbar;
    private String link = "http://plist.vn-hd.com/mp4v3/30afa1999d5782bc28b17c1f03d195b2/d8871a2c95ae4d9399171ebbaba8fe81/00000000000000000000000000000000/10673_e1_720_1080_ivdc.smil/playlist.m3u8";
    private MoviePlay mMoviePlay;
    private int id;
    private int ep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
        if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this))
            return;
        id = getIntent().getIntExtra("id", 0);
        ep = getIntent().getIntExtra("ep", 0);
        RestClient.getPlayVideo(id, ep, new TextHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                showProgressDialog();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                hideProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                ResponseModel responseModel = JSONConvert.getResponse(responseString);
                if (responseModel.isSuccess()) {
                    mMoviePlay = JSONConvert.getMoviePlay(responseModel.getData());
                    setContentView(R.layout.activity_player);
                    initValue();
                    ButterKnife.bind(PlayerActivity.this);
//                    playMovieQuality();
                    mTitle.setText(mMoviePlay.getName());
                    getLink();
                }
            }
        });

    }

    private void initValue() {

        mMessageHandler = new Handler() {
            public void handleMessage(Message message) {
                switch (message.what) {

                    case 1: // '\001'
                        hideController();
                        break;
                    case 2: // '\002'
                        showVideo();
                        break;
                    default:
                        break;
                }
            }

        };
        mMessageSeekbar = new Handler() {

            public void handleMessage(Message message) {
                if (videoView != null && videoView.isPlaying()) {
                    curTime.setText("");
                    curTime.setText(StringUtil.makeTimeString(
                            PlayerActivity.this,
                            videoView.getCurrentPosition() / 1000));
                    tvLength.setText(StringUtil.makeTimeString(
                            PlayerActivity.this,
                            (videoView.getDuration() - videoView
                                    .getCurrentPosition()) / 1000));
                    seekBar.setMax((int) (videoView.getDuration()));
                    seekBar.setProgress((int) videoView.getCurrentPosition());
                    int i = (int) videoView.getDuration();
                    int j = StringUtil.getProgressPercentage(
                            videoView.getBufferPercentage(), i);
                    seekBar.setSecondaryProgress(j);
                }
                mMessageSeekbar.sendEmptyMessageDelayed(0, 800L);
            }
        };


    }

    @OnClick(R.id.player_overlay_play)
    public void playBtnClick(ImageButton btn) {
        if (!videoView.isPlaying()) {
            videoView.start();
            mPlayBtn.setBackgroundResource(R.drawable.btn_pause_active);
        } else {
            videoView.pause();
            mPlayBtn.setBackgroundResource(R.drawable.btn_play_active);
        }
    }

    @OnClick(R.id.back_btn)
    public void backClick(){
        finish();
    }

    private void getLink() {
        new LoadLink().execute();
//        RestClient.getLinkPlay(mMoviePlay.getLinkPlay(), new TextHttpResponseHandler() {
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                final Pattern pattern = Pattern.compile("^#EXT-X-STREAM-INF:.*BANDWIDTH=(\\d+).*RESOLUTION=([\\dx]+).*");
//                Matcher matcher = pattern.matcher(responseString);
//                String bandwidth = "";
//                String resolution = "";
//                if(matcher.find()){
//                    Log.d("GET LINK" ,matcher.group(0));
//                }
//            }
//        });

    }

    private class LoadLink extends AsyncTask<Void, Void, List<Resolution>> {

        @Override
        protected List<Resolution> doInBackground(Void... params) {
            URL url = null;
            try {
                url = new URL(mMoviePlay.getLinkPlay());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                URLConnection ucon = url.openConnection();
                return parseHLSMetadata(ucon.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Resolution> listR) {
            super.onPostExecute(resolutions);
            Log.d("ListSize", listR.size() + "");
            if (listR.size() > 0)
                playMovieQuality(listR.get(listR.size() - 1).url);
        }
    }

    private void playMovieQuality(String urlLink) {
//        InputStream stream = new ByteArrayInputStream(responseBody);
//        resolutions = parseHLSMetadata(stream);
        videoView.setVideoPath(urlLink);
        videoView.requestFocus();
        handler = new Handler();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    if(videoView != null){
                        videoView.seekTo(progress);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mMessageHandler.removeCallbacksAndMessages(null);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mMessageHandler.sendEmptyMessageDelayed(1, 2000L);
            }
        });
        videoView.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {

            }
        });

        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                return false;
            }
        });

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setPlaybackSpeed(1.0f);
                mMessageSeekbar.sendEmptyMessage(0);
                mMessageHandler.sendEmptyMessageDelayed(2, 500L);
//                if (moviesModel.getSubtitleExt() != null) {
                videoView.addTimedTextSource(mMoviePlay.getSubs().get("VIE").getSource());
//                }
                videoView.setTimedTextShown(true);
                videoView.start();
                seekBar.setMax((int)mediaPlayer.getDuration());
                mPlayBtn.setBackgroundResource(R.drawable.btn_pause_active);

            }
        });
        videoView.setOnTimedTextListener(new MediaPlayer.OnTimedTextListener() {

            @Override
            public void onTimedText(final String text) {

                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Log.d("text", text);
                        subtitleSurface.setText(text);
                    }
                }, 0);

            }

            @Override
            public void onTimedTextUpdate(byte[] pixels, int width, int height) {

            }
        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {

                return false;
            }
        });

        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == 0) {
                    if (!mControllerShowingStatus) {
                        showControllerAutoTurnoff();
                    }
                    return false;
                } else {
                    return false;
                }
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoView != null)
            videoView.stopPlayback();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (videoView != null)
            videoView.start();
    }

    private void hideController() {
        mControllerShowingStatus = false;
        controlContainer.setVisibility(View.GONE);
    }

    private void showVideo() {
        progressBar.setVisibility(View.GONE);
        playerContainer.setVisibility(View.VISIBLE);
        hideController();
    }

    private void showProgress() {
        hideController();
        playerContainer.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }


    private void showControllerAutoTurnoff() {
        mControllerShowingStatus = true;
        controlContainer.setVisibility(View.VISIBLE);
        mMessageHandler.sendEmptyMessageDelayed(1, 2000L);
    }
//
//    @OnClick(R.id.player_overlay_subtitle) void chooseSubtitle(){
//        MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
//        builder.title("Choose subtitle").items(R.array.sub_type).itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
//
//            @Override
//            public boolean onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
//                switch (i) {
//                    case 0:
//                        subtitleSurface.setVisibility(View.VISIBLE);
//                        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//
//                            @Override
//                            public void onPrepared(MediaPlayer mediaPlayer) {
//
////                                if (moviesModel.getSubtitleExt() != null) {
//                                videoView.addTimedTextSource("http://s.vn-hd.com:8080/mp4_03/store_10_2015/07102015/The_Flash_S02_2015_720p_HDTV_S/E001/The_Flash_S02_2015_720p_HDTV_S_E001_VIE.srt");
////                                }
//                                videoView.setTimedTextShown(true);
//
//                            }
//                        });
//                        break;
//                    case 1:
//                        subtitleSurface.setVisibility(View.VISIBLE);
//
//                        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//
//                            @Override
//                            public void onPrepared(MediaPlayer mediaPlayer) {
//                                mediaPlayer.setPlaybackSpeed(1.0f);
//                                mMessageSeekbar.sendEmptyMessage(0);
//                                mMessageHandler.sendEmptyMessageDelayed(2, 500L);
////                                if (moviesModel.getSubtitleExt() != null) {
//                                videoView.addTimedTextSource("http://s.vn-hd.com:8080/mp4_03/store_10_2015/07102015/The_Flash_S02_2015_720p_HDTV_S/E001/The_Flash_S02_2015_720p_HDTV_S_E001_ENG.srt");
////                                }
//                                videoView.setTimedTextShown(true);
//
//                            }
//                        });
//                        break;
//                    case 2:
//                        subtitleSurface.setVisibility(View.INVISIBLE);
//                        break;
//                }
//                return true;
//            }
//        }).positiveText(android.R.string.ok).show();
//    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private ArrayList<Resolution> parseHLSMetadata(InputStream i) {

        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(i, "UTF-8"));
            ArrayList<Resolution> resolutionArrayList;
            resolutionArrayList = new ArrayList<>();
            String line;
            Resolution resolutionModel = null;
            while ((line = r.readLine()) != null) {
                if (line.startsWith("#EXT-X-STREAM")) { //start of m3u8

                    resolutionModel = new Resolution();
                    final Pattern pattern = Pattern.compile("^#EXT-X-STREAM-INF:.*BANDWIDTH=(\\d+).*RESOLUTION=([\\dx]+).*");

                    Matcher matcher = pattern.matcher(line);
                    String bandwidth = "";
                    String resolution = "";

                    if (matcher.find()) {
                        bandwidth = matcher.group(1);
                        resolution = matcher.group(2);
                    }
                    resolutionModel.bandwidth = bandwidth;
                    resolutionModel.name = resolution;
                } else if (line.startsWith("http")) { //once found EXTINFO use runner to get the next line which contains the media file, parse duration of the segment
                    resolutionModel.url = line;
                    resolutionArrayList.add(resolutionModel);
                }
            }
            return resolutionArrayList;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
