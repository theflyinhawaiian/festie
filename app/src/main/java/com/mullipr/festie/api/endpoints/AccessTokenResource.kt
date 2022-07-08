package com.mullipr.festie.api.endpoints

import com.mullipr.festie.auth.AccessToken
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AccessTokenResource {
    @FormUrlEncoded
    @POST("refresh_token")
    suspend fun refreshToken(@Field("grant_type") grantType: String,
                             @Field("refresh_token") refreshToken: String,
                             @Field("client_id") clientId : String) : Response<AccessToken>
}