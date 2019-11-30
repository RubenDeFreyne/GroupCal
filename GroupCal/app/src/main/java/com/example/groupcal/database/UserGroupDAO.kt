package com.example.groupcal.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

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
    fun getGroupsFromUser(userId: Long): Array<Group>

    @Query("""
           SELECT * FROM user_table
           INNER JOIN user_group_table
           ON user_table.id=user_group_table.userID
           WHERE user_group_table.groupID=:groupId
           """)
    fun getSongsForPlaylist(groupId: Long): Array<User>
}
