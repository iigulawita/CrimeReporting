package com.example.c_r_system.models

import com.google.firebase.Timestamp

data class HistoryItem(
    val timestamp: Timestamp? = null,
    val questionnaireType: String = "",
    val score: Int? = -1,
)

