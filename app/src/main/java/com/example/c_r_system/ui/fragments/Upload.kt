package com.example.c_r_system.ui.fragments

class Upload {
    var name: String? = null
    var imageUrl: String? = null
    var city:  String? = null

    constructor(name: String, imageUrl: String?, city:String?) {
        var name = name
        if (name.trim { it <= ' ' } == "") {
            name = "No Name"
        }
        this.name = name
        this.imageUrl = imageUrl
        this.city = city
    }
}