package com.example.c_r_system.ui.fragments

class Upload {
    var incidentdes: String? = null
    var imagestoragedes: String? = null
    var city:  String? = null

    constructor(incidentdesc: String, imagestoragedesc: String?, city:String?) {
        var incidentdes = incidentdesc
        if (incidentdes.trim { it <= ' ' } == "") {
            incidentdes = "No description"
        }
        this.incidentdes = incidentdes
        this.imagestoragedes = imagestoragedesc
        this.city = city
    }
}