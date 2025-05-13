package com.mnhyim.nyius.ui.util

import android.content.Context
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

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

fun getRelativeTime(date: LocalDate): String {
    val now = LocalDate.now()
    val daysAgo = ChronoUnit.DAYS.between(date, now)

    return when {
        daysAgo == 0L -> "Today"
        daysAgo == 1L -> "Yesterday"
        daysAgo < 7 -> "$daysAgo days ago"
        daysAgo < 30 -> "${daysAgo / 7} weeks ago"
        daysAgo < 365 -> "${daysAgo / 30} months ago"
        else -> "${daysAgo / 365} years ago"
    }
}