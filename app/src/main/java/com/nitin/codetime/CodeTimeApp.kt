package com.nitin.codetime

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.nitin.codetime.data.db.ContestDatabase
import com.nitin.codetime.data.network.*
import com.nitin.codetime.data.repository.ContestRepository
import com.nitin.codetime.data.repository.ContestRepositoryImpl
import com.nitin.codetime.ui.contest.future.FutureContestsViewModelFactory
import com.nitin.codetime.ui.contest.past.PastContestsViewModelFactory
import com.nitin.codetime.ui.contest.present.PresentContestsViewModelFactory
import com.nitin.codetime.ui.contestDetail.ContestDetailViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*

class CodeTimeApp : Application(), KodeinAware {
    override val kodein by Kodein.lazy {
        import(androidXModule(this@CodeTimeApp))

        bind() from singleton { ContestDatabase(instance()) }
        bind() from singleton { instance<ContestDatabase>().contestDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ContestListApiService(instance()) }
        bind<ContestListNetworkDataSource>() with singleton { ContestListNetworkDataSourceImpl(instance()) }
        bind<ContestRepository>() with singleton { ContestRepositoryImpl(instance(), instance()) }
        bind() from provider { PresentContestsViewModelFactory(instance()) }
        bind() from provider { PastContestsViewModelFactory(instance()) }
        bind() from provider { FutureContestsViewModelFactory(instance()) }
        bind() from factory { contestId: Int -> ContestDetailViewModelFactory(contestId, instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}