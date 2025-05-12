package com.mnhyim.nyius.ui.util

import android.content.Context
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri

fun Context.launchCustomTabs(url: String, useIncognito: Boolean?) {
    CustomTabsIntent.Builder().build().apply {
        if (useIncognito == true) {
            intent.putExtra(
                "com.google.android.apps.chrome.EXTRA_OPEN_NEW_INCOGNITO_TAB",
                true
            )
        }
    }
        .launchUrl(this, url.toUri())
}