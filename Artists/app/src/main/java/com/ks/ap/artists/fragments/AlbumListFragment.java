package com.ks.ap.artists.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ks.ap.artists.R;
import com.ks.ap.artists.SongsListActivity;
import com.ks.ap.artists.Utilities.Albums;

import java.util.ArrayList;

/**
 * Created by kishorsutar on 3/28/17.
 */

public class AlbumListFragment extends ListFragment {

    public static String TAG = "AlbumListFragment";

    private ArrayList<Albums> albumsArrayList = new ArrayList<>();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
////        super.onCreateView(inflater, container, savedInstanceState);
//        View view = inflater.inflate(R.layout.arist_fragment, container, false);
//        return view;
//    }

    public void setAdapter(ArrayList<Albums> listitmes) {
        setListAdapter(new ArtistAdapter(listitmes));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        getArtistDetails(albumsArrayList.get(position));
        Log.d("Selected album value", albumsArrayList.get(position).getAlbumTitle());
    }

    private void getArtistDetails(Albums albums) {
        String artistId = albums.getAlbumId();
        String hrefStr = albums.getAlbSongHref();
        Bundle bundle = new Bundle();
        bundle.putString(SongsListActivity.ALBUM_ID, artistId);
        bundle.putString(SongsListActivity.SONGS_HREF, hrefStr);
        bundle.putString(SongsListActivity.ALBUMS_SONGS_LINK, albums.getAlbSongHref());
        bundle.putString(SongsListActivity.ALBUM_ARTISTS_LINK, albums.getAlbArtHref());
        Intent intent = new Intent(getActivity(), SongsListActivity.class);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }

    class ArtistAdapter extends BaseAdapter {

        public ArtistAdapter(ArrayList<Albums> items) {
            albumsArrayList.clear();
            albumsArrayList = items;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Albums getItem(int position) {
            return albumsArrayList.get(position);
        }

        @Override
        public int getCount() {
            return albumsArrayList.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = (LayoutInflater.from(getActivity()).inflate(R.layout.artist_list_item, null));
            TextView nameText = (TextView) view.findViewById(R.id.artist_name);
            nameText.setText(albumsArrayList.get(position).getAlbumTitle());
            return view;
        }
    }

}
