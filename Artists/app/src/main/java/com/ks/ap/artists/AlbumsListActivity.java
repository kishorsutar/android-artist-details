package com.ks.ap.artists;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.ks.ap.artists.Utilities.DownloadCallBack;
import com.ks.ap.artists.Utilities.JsonParser;
import com.ks.ap.artists.fragments.NetworkFragment;

/**
 * Created by kishorsutar on 3/27/17.
 */

public class AlbumsListActivity extends FragmentActivity implements DownloadCallBack {
    public static String TAG = "AlbumsListActivity";

    public static String ARTIST_ID = "artistId";
    public static String ARTIST_HREF = "albumHref";

    private NetworkFragment mNetworkFragment;
    private boolean mDownloading = false;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       Bundle bundle = getIntent().getExtras();
        mNetworkFragment = NetworkFragment.getInstance(getSupportFragmentManager(),
                bundle.getString(ARTIST_HREF));
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
        if (null != result) {
            if (null != result.mResult) {
                JsonParser parser = new JsonParser();
                Toast.makeText(this, result.mResult, Toast.LENGTH_LONG).show();
//                try {
//                    artistsArrayList.clear();
//                    artistsArrayList = parser.getArtistDetails(result.mResult);
//                    loadList();
//                } catch (JSONException ex) {
//                    Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
//                }
            } else {
                Toast.makeText(this, result.mException.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
        }
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

    }

    @Override
    public void finishedDownloading() {
        mDownloading = false;
        if (mNetworkFragment != null) {
            mNetworkFragment.cancelDownload();
        }
    }
}
