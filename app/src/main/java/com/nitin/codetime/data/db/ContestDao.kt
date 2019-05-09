package com.nitin.codetime.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nitin.codetime.data.db.entity.ContestEntry

@Dao
interface ContestDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertContests(contestList: List<ContestEntry>)

    @Query("select * from contests where startDate <= :currDataTime and endDate > :currDataTime")
    fun getLiveContests(currDataTime: String): LiveData<List<ContestShortInfoModel>>

    @Query("select * from contests where endDate <= :currDataTime")
    fun getPastContests(currDataTime: String): LiveData<List<ContestShortInfoModel>>

    @Query("select * from contests where startDate > :currDataTime")
    fun getFutureContests(currDataTime: String): LiveData<List<ContestShortInfoModel>>

    @Query("select * from contests where cid=:id")
    fun getContestById(id: Int): LiveData<ContestEntry>

    @Query("DELETE from contests where startDate <= :currDataTime and endDate > :currDataTime")
    fun deleteLiveContests(currDataTime: String)

}