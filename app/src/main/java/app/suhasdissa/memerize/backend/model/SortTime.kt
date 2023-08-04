/*******************************************************************************
Created By Suhas Dissanayake on 7/30/23, 6:12 PM
Copyright (c) 2023
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.model

enum class SortTime {
    TODAY,
    WEEK,
    MONTH
}

val SortTime.reddit: String
    get() = when (this) {
        SortTime.TODAY -> "today"
        SortTime.WEEK -> "week"
        SortTime.MONTH -> "month"
    }

val SortTime.lemmy: String
    get() = when (this) {
        SortTime.TODAY -> "TopDay"
        SortTime.WEEK -> "TopWeek"
        SortTime.MONTH -> "TopMonth"
    }
