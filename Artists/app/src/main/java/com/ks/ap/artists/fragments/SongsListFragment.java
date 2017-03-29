package com.ks.ap.artists.fragments;

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
import com.ks.ap.artists.Utilities.Songs;

import java.util.ArrayList;

/**
 * Created by kishorsutar on 3/28/17.
 * List od Songs for selected album
 */

public class SongsListFragment extends ListFragment {

    public static String TAG = "SongsListFragment";

    private ArrayList<Songs> songsArrayList = new ArrayList<>();

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

    public void setAdapter(ArrayList<Songs> listitmes) {
        setListAdapter(new ArtistAdapter(listitmes));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
//        getArtistDetails(artistsArrayList.get(position));
        Log.d("Selected album value", songsArrayList.get(position).getSongTitle());
    }

//    private void getArtistDetails(Artists artistDetails) {
//        String artistId = artistDetails.getId();
//        String hrefStr = artistDetails.getHref();
//        Bundle bundle = new Bundle();
//        bundle.putString(AlbumsListActivity.ARTIST_ID, artistId);
//        bundle.putString(AlbumsListActivity.ARTIST_HREF, hrefStr);
//
//        Intent intent = new Intent(getActivity(), AlbumsListActivity.class);
//        intent.putExtras(bundle);
//        getActivity().startActivity(intent);
//    }


    class ArtistAdapter extends BaseAdapter {

        public ArtistAdapter(ArrayList<Songs> items) {
            songsArrayList.clear();
            songsArrayList = items;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Songs getItem(int position) {
            return songsArrayList.get(position);
        }

        @Override
        public int getCount() {
            return songsArrayList.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = (LayoutInflater.from(getActivity()).inflate(R.layout.artist_list_item, null));
            TextView nameText = (TextView) view.findViewById(R.id.artist_name);
            nameText.setText(songsArrayList.get(position).getSongTitle());
            return view;
        }
    }

}
