package app.suhasdissa.memerize.backend

import androidx.room.*

@Entity
    data class User(
        @PrimaryKey(autoGenerate = true) val id: Int,
        @ColumnInfo(name = "url") val url: String?
    )

    @Dao
    interface UserDao {
        @Query("SELECT * FROM user")
        fun getAll(): List<User>

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        fun insert(vararg users: User)

        @Delete
        fun delete(user: User)
    }
class MemeCacheDB {
    @Database(entities = [User::class], version = 1)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun userDao(): UserDao
    }
/*
    val db = Room.databaseBuilder(
        context: Context = applicationContext,
        AppDatabase::class.java, "database-name"
    ).build()*/
}
/*
val userDao = db.userDao()
val users: List<User> = userDao.getAll()
*/