package com.example.umarkk.torch;

import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;

/**
 * Created by Umar-KK on 11/7/2017.
 */

public class ToastAdListener extends AdListener {

        private Context mContext;
        private String mErrorReason;

        public ToastAdListener(Context context) {
            this.mContext = context;
        }


        @Override
        public void onAdClosed() {
//            Toast.makeText(mContext, "onAdClosed()", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAdFailedToLoad(int errorCode) {
//            mErrorReason = "";
//            switch (errorCode){
//                case AdRequest.ERROR_CODE_INTERNAL_ERROR:
//                    mErrorReason = "Internal Error";
//                    break;
//                case AdRequest.ERROR_CODE_INVALID_REQUEST:
//                    mErrorReason = "Invalid Request";
//                    break;
//                case AdRequest.ERROR_CODE_NETWORK_ERROR:
//                    mErrorReason = "Network Error";
//                    break;
//                case AdRequest.ERROR_CODE_NO_FILL:
//                    mErrorReason = "Not Fill";
//                    break;
//            }
//            Toast.makeText(mContext, String.format("onAdFailedToLoads(%s)", mErrorReason),
//                    Toast.LENGTH_SHORT).show();
        }

//        public String getmErrorReason(){
//            return mErrorReason == null ? "" : mErrorReason;
//        }

        @Override
        public void onAdLeftApplication() {
//            Toast.makeText(mContext, "onAdLeftApplication()", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAdOpened() {
//            Toast.makeText(mContext, "onAdOpened()", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAdLoaded() {
//            Toast.makeText(mContext, "onAddLoaded()", Toast.LENGTH_SHORT).show();
        }
}
