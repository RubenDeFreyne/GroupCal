package com.example.groupcal.data.database.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy

/**
 * Source: https://medium.com/androiddevelopers/7-pro-tips-for-room-fbadea4bfbd1
 *
 * [BaseDAO] defines commonly used queries
 *
 * @param T the type of the item stored in the [Database]
 */
interface BaseDAO<T> {

    /**
     * Insert list of items in database
     * @param items The list that should be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMany(items: List<T>)

    /**
     * Create a new record
     * @param item The item that should be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: T)
}