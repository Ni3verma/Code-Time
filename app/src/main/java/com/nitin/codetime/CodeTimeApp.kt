package com.nitin.codetime

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.nitin.codetime.data.network.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class CodeTimeApp : Application(), KodeinAware {
    override val kodein by Kodein.lazy {
        import(androidXModule(this@CodeTimeApp))

        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ContestListApiService(instance()) }
        bind<ContestListNetworkDataSource>() with singleton { ContestListNetworkDataSourceImpl(instance()) }

    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}