package com.pos.kasse.messagequeue

import com.pos.kasse.utils.URL_API
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException

object Publisher {

    private val client = OkHttpClient().newBuilder().build()

    /*
    TODO:
    Autentisering med sesjon.
     */
    fun publish(salgsmelding: String) {
        val request = Request.Builder()
            .url("$URL_API/kasse/salg")
            .post(salgsmelding
                .toRequestBody(contentType = "application/json".toMediaTypeOrNull()))
            .addHeader("Content-Type", "application/json")
            .build()
        try {
            val responsebody = client.newCall(request).execute()
            print(responsebody.code)
        } catch (e: IOException) {
            print(e.message)
        }
    }
}