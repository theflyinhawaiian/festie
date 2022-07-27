package com.mullipr.festie.di

import android.content.Context
import com.mullipr.festie.auth.IOAuthService
import com.mullipr.festie.auth.OAuthService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.openid.appauth.AuthorizationService

@Module
@InstallIn(SingletonComponent::class)
abstract class OAuthModule {
    companion object{
        @Provides fun bindAuthService(@ApplicationContext ctx : Context) : AuthorizationService = AuthorizationService(ctx)
    }
    @Binds abstract fun bindOAuthService(oAuthServiceImpl: OAuthService): IOAuthService
}