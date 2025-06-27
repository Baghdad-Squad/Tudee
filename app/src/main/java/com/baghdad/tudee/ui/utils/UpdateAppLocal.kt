package com.baghdad.tudee.ui.utils


import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.Locale

fun updateAppLocale(context: Context, locale: Locale): Context {
    Locale.setDefault(locale)
    val config = Configuration(context.resources.configuration)
    config.setLocale(locale)

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        context.createConfigurationContext(config)
    } else {
        @Suppress("DEPRECATION")
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
        context
    }
}
