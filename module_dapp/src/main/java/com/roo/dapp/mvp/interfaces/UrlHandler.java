package com.roo.dapp.mvp.interfaces;

import android.net.Uri;

public interface UrlHandler {

    String getScheme();

    String handle(Uri uri);
}