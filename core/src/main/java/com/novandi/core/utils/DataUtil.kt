package com.novandi.core.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import java.util.Locale

fun String?.toImageUrl(): String = "https://image.tmdb.org/t/p/w500$this"

fun String?.toTmdbImageUrl(): String = "https://image.tmdb.org/t/p/w500$this"

fun String?.toGravatarImageUrl(): String = "https://www.gravatar.com/avatar/$this?s=240"

fun getMovieGenre(genreIds: List<Int>): String {
    return if (genreIds.isNotEmpty()) {
        val genre = MovieGenres.genres.filter { it.id == genreIds[0] }
        genre[0].name
    } else "Unknown"
}

fun String?.toImageUrlOriginal() : String = "https://image.tmdb.org/t/p/original$this"

fun String.formatDate(): String {
    return if (this != "") {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val date = inputFormat.parse(this)
        outputFormat.format(date!!)
    } else "Unknown"
}

fun Double.doubleToCeil(): String = String.format("%.1f", this)

fun String.splitAtIndex(index: Int) = when {
    index < 0 -> 0
    index > length -> length
    else -> index
}.let {
    take(it) to substring(it)
}

@SuppressLint("NewApi")
fun formatDateTime(dateStr: String): String {
    val dateTime = ZonedDateTime.parse(dateStr)
    val now = ZonedDateTime.now(ZoneOffset.UTC)
    val diff = Duration.between(dateTime, now)
    val diffYears = ChronoUnit.YEARS.between(dateTime, now)

    val minutes = diff.toMinutes()
    val hours = diff.toHours()
    val days = diff.toDays()
    val years = if (diffYears > 0) diffYears else 0
    val months = ChronoUnit.MONTHS.between(dateTime.plusYears(years), now)

    return when {
        years > 0 -> "$diffYears years ago"
        months > 0 -> "$months months ago"
        days > 0 -> "$days days ago"
        hours > 0 -> "$hours hours ago"
        minutes > 0 -> "$minutes minutes ago"
        else -> "Just now"
    }
}

fun isInternetAvailable(context: Context): Boolean {
    var result = false
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        connectivityManager.run {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
    }
    return result
}
