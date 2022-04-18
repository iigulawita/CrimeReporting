package com.example.c_r_system.services

import com.example.c_r_system.models.AppUser

interface UserCallback {
    fun onPostExecute(dRef: String)
    fun onPostExecute(user: AppUser)
}


