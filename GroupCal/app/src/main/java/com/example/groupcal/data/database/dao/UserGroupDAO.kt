package com.example.groupcal.database.dao

import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.groupcal.database.databaseModels.Group
import com.example.groupcal.database.databaseModels.User
import com.example.groupcal.database.databaseModels.UserGroup
import io.reactivex.Single

@Dao
interface UserGroupDAO {
    @Insert
    fun insert(userGroup: UserGroup)

    @Query("""
           SELECT * FROM group_table
           INNER JOIN user_group_table
           ON group_table.id=user_group_table.groupID
           WHERE user_group_table.userId=:userId
           """)
    fun getGroupsFromUser(userId: Long): Single<List<Group>>

    @Query("""
           SELECT * FROM user_table
           INNER JOIN user_group_table
           ON user_table.id=user_group_table.userID
           WHERE user_group_table.groupID=:groupId
           """)
    fun getSongsForPlaylist(groupId: Long): Single<List<User>>
}
