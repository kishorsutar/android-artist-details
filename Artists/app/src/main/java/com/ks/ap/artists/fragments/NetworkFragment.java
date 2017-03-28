package com.ks.ap.artists.fragments;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.ks.ap.artists.Utilities.DownloadCallBack;
import com.ks.ap.artists.Utilities.UrlBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by kishorsutar on 3/25/17.
 * This class will handle all network operations.
 */
public class NetworkFragment extends Fragment {
    public static final String TAG = "NetWorkFragment";
    private static final String URL_KEY = "UrlKey";


    private String mUrlString;
    private DownloadCallBack mCallBack;
    private DownloadTask mDownloadTask;

/**
 * Static initializer for the fragment with unique URL string.
 */
    public static NetworkFragment getInstance(FragmentManager fragmentManager, String url) {
        NetworkFragment networkFragment = new NetworkFragment();
        Bundle args = new Bundle();
        args.putString(URL_KEY, url);
        networkFragment.setArguments(args);
        fragmentManager.beginTransaction().add(networkFragment, TAG).commit();
        return networkFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // convert total URL for api call.
        mUrlString = UrlBuilder.BASE_API_URL + getArguments().getString(URL_KEY);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallBack = (DownloadCallBack) context;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mCallBack = null;

    }

    public void startDownload() {
        cancelDownload();
        mDownloadTask = new DownloadTask(mCallBack);
        mDownloadTask.execute(mUrlString);
    }

    public void cancelDownload() {
        if(mDownloadTask != null) {
            mDownloadTask.cancel(true);
        }
    }

    public class DownloadTask extends AsyncTask<String, Void, DownloadTask.Result> {

        private DownloadCallBack mCallBack;
        DownloadTask(DownloadCallBack<String> callBack) {
            setCallBack(callBack);
        }
        private void setCallBack(DownloadCallBack mCallBack) {
            this.mCallBack = mCallBack;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            NetworkInfo networkInfo = this.mCallBack.getActiveNetworkInfo();
            if (null == networkInfo || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI &&
                        networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
                this.mCallBack.updateFromDownload(null);
                    cancel(true);
                }
        }


        /**
         * This class will handle the result or exceptions occurred in DoInBackground method.
         */
        public class Result {
            public String mResult;
            public Exception mException;

            private Result(String result) {
                mResult = result;
            }
            private Result(Exception exception) {
                mException = exception;
            }
        }

        @Override
        protected DownloadTask.Result doInBackground(String... urls) {
            Result result = null;
            if(!isCancelled() && null != urls && urls.length > 0) {
                String urlString = urls[0];
                try {
                    URL url = new URL(urlString);
                    String resultString = downloadUrl(url);
                    if (null != resultString) {
                        result = new Result(resultString);
                    }
                    else {
                        throw new IOException("No response received");
                    }
                }
                catch (Exception e) {
                    result = new Result(e);
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(Result result) {
            super.onPostExecute(result);
            if (result != null && this.mCallBack != null) {
                if (result.mException != null) {
                    this.mCallBack.updateFromDownload(result);
                } else if (result.mResult != null) {
                    this.mCallBack.updateFromDownload(result);
                }
                this.mCallBack.finishedDownloading();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        private String downloadUrl(URL url) throws  IOException{
            String result = null;
            InputStream inputStream = null;
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                // set connection timeout to 5000 ms.
                connection.setConnectTimeout(5000);
                // set read timeout to 5000 ms.
                connection.setReadTimeout(5000);
                // set http method
                connection.setRequestMethod("GET");
                // set input to get request.
                connection.setDoInput(true);
                // open connection (network traffic)
                connection.connect();
                // publish the progress.
//                publishProgress(DownloadCallBack.Progress.CONNECT_SUCCESS);
                int responseCode = connection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    throw new IOException("Http error code: "+ responseCode);
                }
                // retrieve the response as inputstream
                inputStream = connection.getInputStream();
                // publish the result.
//                publishProgress(DownloadCallBack.Progress.GET_INPUT_STREAM_SUCCESS, 0);
                if (null != inputStream) {
                    result = readInputStream(inputStream, 2048);
                }
            }
           finally {

            }

            return result;
        }

        /**
         * Read the input stram and convert to string.
         * @param stream - input stream value
         * @param maxLength - length of in put stream to process
         * @return resultString
         * @throws IOException IoException if input string is missing
         */
        private String readInputStream(InputStream stream, int maxLength) throws IOException{
            String resultString = null;
            // Read inputstream using UTF-8
            InputStreamReader streamReader = new InputStreamReader(stream, "UTF-8");
            // create character buffer
            char[] buffer = new char[maxLength];
            // populate temp buffer with temp data.
            int numChar = 0;
            int readSize = 0;
            while (numChar < maxLength && readSize != -1) {
                numChar += readSize;
                int pct = (100 * numChar) / maxLength;
                //publish the progress on UI.
                readSize = streamReader.read(buffer, numChar, buffer.length - numChar);
            }

            if (numChar != -1) {
                // create out string if it is less than max length.
                numChar = Math.min(numChar, maxLength);
                resultString = new String(buffer, 0, numChar);
            }
            return resultString;
        }
    }
}
