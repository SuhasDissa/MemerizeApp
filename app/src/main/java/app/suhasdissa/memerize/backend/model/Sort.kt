/*******************************************************************************
Created By Suhas Dissanayake on 7/30/23, 6:12 PM
Copyright (c) 2023
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.model

import app.suhasdissa.memerize.R

sealed class Sort(open val name: Int, val redditSort: String, open val lemmySort: String) {
    object Hot : Sort(R.string.hot, "hot", "Hot")
    object New : Sort(R.string.sort_new, "new", "New")
    object Rising : Sort(R.string.rising, "rising", "Active")
    sealed class Top(val redditT: String) : Sort(R.string.top, "top", "") {
        object Today : Top("today") {
            override val name = R.string.reddit_today_btn
            override val lemmySort = "TopDay"
        }

        object Week : Top("week") {
            override val name = R.string.reddit_week_btn
            override val lemmySort = "TopWeek"
        }

        object Month : Top("month") {
            override val name = R.string.reddit_month_btn
            override val lemmySort = "TopMonth"
        }
    }
}
