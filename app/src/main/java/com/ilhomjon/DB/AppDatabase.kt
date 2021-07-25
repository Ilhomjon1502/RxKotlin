package DB

import com.ilhomjon.Dao.UserDao
import Entity.User
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase(){

    abstract fun userDao(): UserDao

    companion object{
        private var instance : AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context):AppDatabase{
            if (instance == null){
                instance = Room.databaseBuilder(context, AppDatabase::class.java, "user_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}