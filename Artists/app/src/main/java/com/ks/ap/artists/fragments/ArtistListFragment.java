package com.ks.ap.artists.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ks.ap.artists.R;
import com.ks.ap.artists.Utilities.Artists;

import java.util.ArrayList;

/**
 * Created by kishorsutar on 3/26/17.
 */

public class ArtistListFragment extends ListFragment {
public static String TAG = "ArtistListFragment";

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

    public void setAdapter(ArrayList<Artists> listitmes) {
    setListAdapter(new ArtistAdapter(listitmes));
}
    class ArtistAdapter extends BaseAdapter {

        private ArrayList<Artists> artistsArrayList = new ArrayList<>();
      public ArtistAdapter(ArrayList<Artists> items) {
          artistsArrayList = items;
      }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Artists getItem(int position) {
            return artistsArrayList.get(position);
        }

        @Override
        public int getCount() {
            return artistsArrayList.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = (LayoutInflater.from(getActivity()).inflate( R.layout.artist_list_item, null));
            TextView nameText = (TextView) view.findViewById(R.id.artist_name);
            nameText.setText(artistsArrayList.get(position).getName());
            Log.d("Artist Array value", artistsArrayList.get(position).getName());
            return view;
        }
  }
}
