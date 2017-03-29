package com.ks.ap.artists;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.ks.ap.artists.Utilities.DownloadCallBack;
import com.ks.ap.artists.Utilities.JsonParser;
import com.ks.ap.artists.Utilities.Songs;
import com.ks.ap.artists.fragments.NetworkFragment;
import com.ks.ap.artists.fragments.SongsListFragment;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by kishorsutar on 3/28/17.
 * This Activity will show list of Songs for selected albums
 */

public class SongsListActivity extends FragmentActivity implements DownloadCallBack {


    public static String ALBUM_ID = "albumId";
    public static String SONGS_HREF = "songsHref";
    public static String ALBUMS_SONGS_LINK = "albums.songs";
    public static String ALBUM_ARTISTS_LINK = "albums.artist";
    private NetworkFragment mNetworkFragment;
    private boolean mDownloading = false;

    private ArrayList<Songs> songsArrayList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);
        Bundle bundle = getIntent().getExtras();
        String[] tempArrayForSong = bundle.getString(ALBUMS_SONGS_LINK).split("=");
        String songsLink = tempArrayForSong[0] + "=" + bundle.getString(ALBUM_ID);
        mNetworkFragment = NetworkFragment.getInstance(getSupportFragmentManager(),
                songsLink);
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
//                Toast.makeText(this, result.mResult, Toast.LENGTH_LONG).show();
                try {
                    songsArrayList.clear();
                    songsArrayList = parser.getAlbumSongsDetails(result.mResult);
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
        SongsListFragment albumListFragment = new SongsListFragment();
        manager.beginTransaction().add(R.id.songs_container, albumListFragment, SongsListFragment.TAG).commit();
        albumListFragment.setAdapter(songsArrayList);
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
