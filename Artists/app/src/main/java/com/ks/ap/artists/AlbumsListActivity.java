package com.ks.ap.artists;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.ks.ap.artists.Utilities.Albums;
import com.ks.ap.artists.Utilities.DownloadCallBack;
import com.ks.ap.artists.Utilities.JsonParser;
import com.ks.ap.artists.fragments.AlbumListFragment;
import com.ks.ap.artists.fragments.NetworkFragment;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by kishorsutar on 3/27/17.
 * This activity will show the albums list
 */

public class AlbumsListActivity extends FragmentActivity implements DownloadCallBack {


    public static String ARTIST_ID = "artistId";
    public static String ALBUMS_HREF = "albumHref";
    public static String ARTIS_ALBUM_LINK = "artists.albums";
    public static String ARTIST_SONGS_LINK = "artists.songs";
    private NetworkFragment mNetworkFragment;
    private boolean mDownloading = false;

    private ArrayList<Albums> albumsArrayList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        Bundle bundle = getIntent().getExtras();
        String[] tempArrayForAlbum = bundle.getString(ARTIS_ALBUM_LINK).split("=");
        String albumLink = tempArrayForAlbum[0] + "=" + bundle.getString(ARTIST_ID);
        mNetworkFragment = NetworkFragment.getInstance(getSupportFragmentManager(),
                albumLink);
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
                    albumsArrayList.clear();
                    albumsArrayList = parser.getAlbumDetails(result.mResult);
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
        AlbumListFragment albumListFragment = new AlbumListFragment();
        manager.beginTransaction().add(R.id.album_container, albumListFragment, AlbumListFragment.TAG).commit();
        albumListFragment.setAdapter(albumsArrayList);
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
