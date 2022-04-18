package com.example.c_r_system.models

data class AppUser(
    val uid: String?,
    val name: String?,
    val email: String,
    val profileComplete: Boolean,
    val address: String?,
    val mobileNo: String?,
    val guardian: Guardian?,
    val settings: Settings?
)


