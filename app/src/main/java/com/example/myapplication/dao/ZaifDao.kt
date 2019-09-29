package com.example.myapplication.dao

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.CompletableDeferred
import okhttp3.*
import java.io.IOException
import java.lang.Exception
import java.math.BigDecimal

object CurrencyPairListTypeReference: TypeReference<List<CurrencyPair>>()

class DeferredCallback<T>(val deferred: CompletableDeferred<T>, val parseBody: DeferredCallback<T>.(ResponseBody)->T): Callback {
    val mapper = ObjectMapper()
    override fun onResponse(call: Call, response: Response) {
        response.use {
            if (it.isSuccessful) {
                response.body?.also {
                    deferred.complete(this.parseBody(it))
                }
                return@use
            }

            deferred.completeExceptionally(Exception("Request failed: response code: ${it.code}"))
        }
    }

    override fun onFailure(call: Call, e: IOException) {
        deferred.completeExceptionally(Exception(e))
    }
}

class ZaifDao {
    companion object {
        private const val urlCurrencyPairs = "https://api.zaif.jp/api/1/currency_pairs/all"
    }
    private val client = OkHttpClient.Builder().build()

    fun requestCurrencies(): CompletableDeferred<List<CurrencyPair>> {
        val request = Request.Builder()
            .url(urlCurrencyPairs)
            .build()
        val deferred = CompletableDeferred<List<CurrencyPair>>()
        val callback = DeferredCallback(deferred) {
            mapper.readValue(it.string(), CurrencyPairListTypeReference)
        }
        client.newCall(request).enqueue(callback)
        return deferred
    }
}

data class CurrencyPair (
    @JsonProperty("item_japanese") val item_japanese : String,
    @JsonProperty("currency_pair") val currency_pair : String,
    @JsonProperty("id") val id : Int,
    @JsonProperty("event_number") val eventNumber : Int,
    @JsonProperty("item_unit_step") val itemUnitStep : Int,
    @JsonProperty("name") val name : String,
    @JsonProperty("aux_unit_min") val auxUnitMin : BigDecimal,
    @JsonProperty("item_unit_min") val itemUnitMin : Int,
    @JsonProperty("aux_japanese") val auxJapanese : String,
    @JsonProperty("description") val description : String,
    @JsonProperty("aux_unit_step") val auxUnitStep : BigDecimal,
    @JsonProperty("aux_unit_point") val auxUnitPoint : Int,
    @JsonProperty("is_token") val isToken : Boolean,
    @JsonProperty("title") val title : String,
    @JsonProperty("seq") val seq : Int
)