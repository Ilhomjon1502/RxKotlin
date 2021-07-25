package com.ilhomjon.Dao

import Entity.User
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface UserDao {

    @Insert
    fun addUser(user: User):Single<Long>

    @Query("select * from users")
    fun getAllUsers(): Flowable<List<User>>

    @Query("select * from users where id=:id")
    fun getUserById(id:Int):Maybe<User>
}