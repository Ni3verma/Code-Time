package com.nitin.codetime.ui.contestDetail

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel;
import com.nitin.codetime.R
import com.nitin.codetime.data.repository.ContestRepository
import com.nitin.codetime.internal.lazyDeferred
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class ContestDetailViewModel(
    private val contestId: Int,
    private val contestRepository: ContestRepository
) : ViewModel() {
    val contestDetail by lazyDeferred {
        contestRepository.getContestDetailById(contestId)
    }

    fun getLogo(res: String): Int {
        return when (res) {
            "codechef.com" -> R.drawable.codechef_poster
            "topcoder.com" -> R.drawable.topcoder_poster
            "hackerrank.com" -> R.drawable.hackerrank_poster
            "hackerearth.com" -> R.drawable.hackerearth_poster
            "codeforces.com" -> R.drawable.codeforces_logo
            // TODO: change with app icon
            else -> R.drawable.ic_launcher_background
        }
    }

    fun getDateAndTime(dateTime: String): List<String> {
        return LocalDateTime
            .parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))
            .split(" ")
    }

    fun shareContest(name: String, url: String): Intent {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Time to CODE !!")
        intent.putExtra(
            Intent.EXTRA_TEXT,
            "Check out this contest:\n" +
                    "$name\n" +
                    "Click on this link to register: $url"
        )
        return intent
    }

    fun openInBrower(url: String): Intent {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        return intent
    }
}
