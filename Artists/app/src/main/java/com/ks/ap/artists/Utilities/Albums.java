package com.ks.ap.artists.Utilities;

/**
 * Created by kishorsutar on 3/28/17.
 */

public class Albums {

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getAlbumYear() {
        return albumYear;
    }

    public void setAlbumYear(String albumYear) {
        this.albumYear = albumYear;
    }

    public String getAlbumHref() {
        return albumHref;
    }

    public void setAlbumHref(String albumHref) {
        this.albumHref = albumHref;
    }

    public String albumId;
    public String albumTitle;
    public String albumYear;
    public String albumHref;

    public String getAlbSongHref() {
        return albSongHref;
    }

    public void setAlbSongHref(String albSongHref) {
        this.albSongHref = albSongHref;
    }

    public String getAlbArtHref() {
        return albArtHref;
    }

    public void setAlbArtHref(String albArtHref) {
        this.albArtHref = albArtHref;
    }

    public String albSongHref;
    public String albArtHref;
}
