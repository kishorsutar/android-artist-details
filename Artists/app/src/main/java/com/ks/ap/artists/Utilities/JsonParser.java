package com.ks.ap.artists.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kishorsutar on 3/26/17.
 * This class parse the network response and returns the respective object for UI updates
 */

public class JsonParser {

    private static String ARTIST_ARRAY = "artists";
    private static String ALBUMS_ARRAY = "albums";
    private static String SONGS_ARRAY = "songs";

    private static String LINKES = "links";

    private static String ID = "id";
    private static String ARTIST_NAME = "name";
    private static String ARTIST_WEBSITE = "website";
    private static String HREF = "href";

    private static String ARTIST_ALBUM = "artists.albums";
    private static String ARTIST_SONGS = "artists.songs";
    private static String ALBUM_SONGS = "albums.songs";
    private static String ALBUMS_ARTISTS = "albums.artist";
    private static String ARTIST_TYPE = "type";

    private static String TITLE = "title";
    private static String YEAR = "year";



    public ArrayList<Artists> getArtistDetails(String jsonString) throws JSONException {

        JSONObject artistsJson = createJsonFromString(jsonString);
        ArrayList<Artists> artists = new ArrayList<>();
        JSONArray artistArrayObj = artistsJson.getJSONArray(ARTIST_ARRAY);
        JSONObject linkObj = artistsJson.getJSONObject(LINKES);
        JSONObject albumObj = linkObj.getJSONObject(ARTIST_ALBUM);
        JSONObject songObj = linkObj.getJSONObject(ARTIST_SONGS);

        for (int i = 0; i < artistArrayObj.length(); i++) {
            Artists artist = new Artists();
            JSONObject artistJsonObj = artistArrayObj.getJSONObject(i);
            artist.setId(artistJsonObj.getString(ID));
            artist.setName(artistJsonObj.getString(ARTIST_NAME));
            artist.setWebsite(artistJsonObj.getString(ARTIST_WEBSITE));
            artist.setHref(artistJsonObj.getString(HREF));
            // get Artist links
            artist.setLinkAlbum(albumObj.getString(HREF));
            artist.setLinkSongs(songObj.getString(HREF));
            artists.add(artist);
        }
        return artists;
    }

    public ArrayList<Albums> getAlbumDetails(String jsonString) throws JSONException {

        JSONObject albumObj = createJsonFromString(jsonString);
        ArrayList<Albums> albumsList = new ArrayList<>();
        JSONArray albmsJsonArray = albumObj.getJSONArray(ALBUMS_ARRAY);
        JSONObject linkObj = albumObj.getJSONObject(LINKES);
        JSONObject albumSongObj = linkObj.getJSONObject(ALBUM_SONGS);
        JSONObject albArtistObj = linkObj.getJSONObject(ALBUMS_ARTISTS);
        for (int i = 0; i < albmsJsonArray.length(); i++) {
            Albums albums = new Albums();
            JSONObject albumJsonObj = albmsJsonArray.getJSONObject(i);
            albums.setAlbumId(albumJsonObj.getString(ID));
            albums.setAlbumTitle(albumJsonObj.getString(TITLE));
            albums.setAlbumYear(albumJsonObj.getString(YEAR));
            albums.setAlbumHref(albumJsonObj.getString(HREF));

            albums.setAlbSongHref(albumSongObj.getString(HREF));
            albums.setAlbArtHref(albArtistObj.getString(HREF));
            albumsList.add(albums);
        }
        return albumsList;
    }

    public ArrayList<Songs> getAlbumSongsDetails(String jsonString) throws JSONException {

        JSONObject songsObj = createJsonFromString(jsonString);
        ArrayList<Songs> songsArrayList = new ArrayList<>();
        JSONArray songsObjJSONArray = songsObj.getJSONArray(SONGS_ARRAY);

        for (int i = 0; i < songsObjJSONArray.length(); i++) {
            Songs song = new Songs();
            JSONObject songJsonObject = songsObjJSONArray.getJSONObject(i);
            song.setSongID(songJsonObject.getString(ID));
            song.setSongTitle(songJsonObject.getString(TITLE));
            song.setSongHref(songJsonObject.getString(HREF));
            songsArrayList.add(song);
        }
        return songsArrayList;
    }

    private JSONObject createJsonFromString(String string) throws JSONException {
        return new JSONObject(string);
    }

}
