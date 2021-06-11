package com.bnkit.bnkit.data.models.remoteapimodels

import com.google.gson.annotations.SerializedName

data class CheckClientBody(
    @SerializedName("email")
    var email: String="",
    @SerializedName("phone")
    var phone: String="",
    @SerializedName("sysToken")
    var SysToken: String=""
)