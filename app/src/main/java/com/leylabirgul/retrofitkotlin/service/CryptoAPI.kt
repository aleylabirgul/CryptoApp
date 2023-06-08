package com.leylabirgul.retrofitkotlin.service

import com.leylabirgul.retrofitkotlin.model.CryptoModel
import retrofit2.Call
import retrofit2.http.GET

interface CryptoAPI {
    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    fun getData() : Call<List<CryptoModel>>
}