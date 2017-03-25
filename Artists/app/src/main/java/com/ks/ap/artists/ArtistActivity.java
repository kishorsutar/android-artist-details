package com.ks.ap.artists;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.ks.ap.artists.fragments.NetworkFragment;

public class ArtistActivity extends FragmentActivity implements DownloadCallBack{

    private static final String ARTIST_API = "/api/v1/artists.json";
    /**
     * this reference holds Async task object that will handle network operations.
     */
    private NetworkFragment mNetworkFragment;
    /**
     * create the downloading flag which will prevent overlapping downloads for same event.
     */
    private boolean mDownloading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);
        // get the instance of network manager
        mNetworkFragment = NetworkFragment.getInstance(getSupportFragmentManager(), ARTIST_API, this);
//        startDownload();
    }

    private void startDownload() {
        if (!mDownloading && mNetworkFragment != null) {
            // execute async download.
            mNetworkFragment.startDownload();
            mDownloading = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startDownload();
    }

    @Override
    public void updateFromDownload(String result) {
        // display the result on ui / create new Fragment to display the data.
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }

    @Override
    public NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager manager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo;
    }

    @Override
    public void onProgressUpdate(int progressCode, int parentComplete) {
        switch (progressCode) {
            // UI updates according to progress code.
            case Progress.ERROR:
                Toast.makeText(this, parentComplete, Toast.LENGTH_LONG).show();
                break;
            case Progress.CONNECT_SUCCESS:
                break;
            case Progress.GET_INPUT_STREAM_SUCCESS:
                break;
            case Progress.PROCESS_INPUT_STREAM_PROGRESS:
                break;
            case Progress.PROCESS_INPUT_STREAM_SUCCESS:
                break;
        }

    }

    @Override
    public void finishedDownloading() {
        mDownloading = false;
        if (mNetworkFragment != null) {
            mNetworkFragment.cancelDownload();
        }
    }
}
