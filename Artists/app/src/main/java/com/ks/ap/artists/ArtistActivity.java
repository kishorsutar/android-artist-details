package com.ks.ap.artists;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.ks.ap.artists.Utilities.Artists;
import com.ks.ap.artists.Utilities.DownloadCallBack;
import com.ks.ap.artists.Utilities.JsonParser;
import com.ks.ap.artists.fragments.ArtistListFragment;
import com.ks.ap.artists.fragments.NetworkFragment;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Artist activity
 */
public class ArtistActivity extends FragmentActivity implements DownloadCallBack {

    private static final String ARTIST_API = "/api/v1/artists.json";
    /**
     * this reference holds Async task object that will handle network operations.
     */
    private NetworkFragment mNetworkFragment;
    /**
     * create the downloading flag which will prevent overlapping downloads for same event.
     */
    private boolean mDownloading = false;
    public ArrayList<Artists> artistsArrayList = new ArrayList<>();
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
    public void updateFromDownload(NetworkFragment.DownloadTask.Result result) {
        // display the result on ui / create new Fragment to display the data.
        if (null != result) {
            if (null != result.mResult) {
                JsonParser parser = new JsonParser();
                try {
                    artistsArrayList.clear();
                    artistsArrayList = parser.getArtistDetails(result.mResult);
                    loadList();
                } catch (JSONException ex) {
                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, result.mException.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
        }
    }

    private void loadList() {
        FragmentManager manager = getSupportFragmentManager();
        ArtistListFragment artistListFragment = new ArtistListFragment();
        manager.beginTransaction().add(R.id.artist_fragment, artistListFragment, ArtistListFragment.TAG).commit();
        artistListFragment.setAdapter(artistsArrayList);
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
