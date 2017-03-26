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
    private static String LINKES = "links";

    private static String ARTIST_ID = "id";
    private static String ARTIST_NAME = "name";
    private static String ARTIST_WEBSITE = "website";
    private static String ARTIST_HREF = "href";

    private static String ARTIST_ALBUM = "artists.albums";
    private static String ARTIST_SONGS = "artists.songs";
    private static String ARTIST_TYPE = "type";


    public ArrayList<Artists> getArtistDetails(String jsonString) throws JSONException {

        JSONObject artistsJson = createJsonFromString(jsonString);
        ArrayList<Artists> artists = new ArrayList<>();
        JSONArray artistArrayObj = artistsJson.getJSONArray(ARTIST_ARRAY);
        for (int i = 0; i < artistArrayObj.length(); i++) {
            Artists artist = new Artists();
            JSONObject artistJsonObj = artistArrayObj.getJSONObject(i);
            artist.setId(artistJsonObj.getString(ARTIST_ID));
            artist.setName(artistJsonObj.getString(ARTIST_NAME));
            artist.setWebsite(artistJsonObj.getString(ARTIST_WEBSITE));
            artist.setHref(artistJsonObj.getString(ARTIST_HREF));
            artists.add(artist);
        }
        return artists;
    }

    private JSONObject createJsonFromString(String string) throws JSONException {
        return new JSONObject(string);
    }

}
