package com.example.c_r_system.services

import com.example.c_r_system.models.HistoryItem

interface HistoryCallback {
    fun onPostExecute(histories: List<HistoryItem>?)
}


