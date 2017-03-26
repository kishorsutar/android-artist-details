package com.ks.ap.artists.Utilities;

/**
 * Created by kishorsutar on 3/26/17.
 * Model class for artists.
 */

public class Artists {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    private String id;
    private String name;
    private String website;
    private String href;


}
