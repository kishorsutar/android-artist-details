package com.ks.ap.artists.Utilities;

import android.net.NetworkInfo;

import com.ks.ap.artists.fragments.NetworkFragment;

/**
 * Created by kishorsutar on 3/25/17.
 * This interface will handle callbacks from Network which will be implemented by activity/fragment to update UI changes
 */
public interface DownloadCallBack<T> {

    interface Progress {
        int ERROR = -1;
        int CONNECT_SUCCESS = 0;
        int GET_INPUT_STREAM_SUCCESS = 1;
        int PROCESS_INPUT_STREAM_PROGRESS = 2;
        int PROCESS_INPUT_STREAM_SUCCESS = 3;
    }
    /**
     * This method should call from Main thread.
     * @param result
     */
    void updateFromDownload(NetworkFragment.DownloadTask.Result result);

    NetworkInfo getActiveNetworkInfo();

    /**
     * Indicate progress changes ranges between 0-100.
     * @param progressCode
     * @param parentComplete
     */
    void onProgressUpdate(int progressCode, int parentComplete);

    void finishedDownloading();
}
