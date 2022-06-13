package com.example.videocall

import android.webkit.JavascriptInterface

class JavascriptInterface(val callActivity_sd: CallActivity_sd) {

    @JavascriptInterface
    public fun onPeerConnected(){
        callActivity_sd.onPeerConnected()
    }
}