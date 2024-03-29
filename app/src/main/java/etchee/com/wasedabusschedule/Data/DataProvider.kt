package etchee.com.wasedabusschedule.Data

import android.content.*
import android.database.Cursor
import android.net.Uri

/**
 * Content Provider Function
 * Created by rikutoechigoya on 2017/05/23.
 */
class DataProvider : ContentProvider() {

    private val CODE_TO_WASEDA = 100
    private val CODE_TO_NISHI = 101
    private val CODE_SAT_TO_WASEDA = 200
    private val CODE_SAT_TO_NISHI = 201
    private val DATABASE_VERSION:Int = 1
    private val data:DataContract.GlobalConstants = DataContract.GlobalConstants()
    private val matcher : UriMatcher = initUriMatcher()
    lateinit var dbHelper:DataDbHelper

    private fun initUriMatcher():UriMatcher {
        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        uriMatcher.addURI(data.CONTENT_AUTHORITY, data.PATH_TO_WASEDA, CODE_TO_WASEDA)
        uriMatcher.addURI(data.CONTENT_AUTHORITY, data.PATH_TO_NISHI, CODE_TO_NISHI)
        uriMatcher.addURI(data.CONTENT_AUTHORITY, data.PATH_SAT_TO_WASEDA, CODE_SAT_TO_WASEDA)
        uriMatcher.addURI(data.CONTENT_AUTHORITY, data.PATH_SAT_TO_NISHI, CODE_SAT_TO_NISHI)

        return uriMatcher
    }

    override fun onCreate(): Boolean {
        dbHelper = DataDbHelper(context.applicationContext, data.DATABASE_NAME, null, DATABASE_VERSION)
        return true
    }

    override fun query(uri: Uri?, projection: Array<out String>?, selection: String?,
                       selectionArgs: Array<out String>?, sortOrder: String?): Cursor {


        val database = dbHelper.writableDatabase
        val match = matcher.match(uri)
        val cursor: Cursor

        when (match) {

            CODE_TO_WASEDA -> {
                cursor = database.query(
                        DataContract.DB_TO_WASEDA().TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs, null, null, null) as Cursor
            }

            CODE_TO_NISHI -> {
                cursor = database.query(
                        DataContract.DB_TO_NISHI().TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs, null, null, null) as Cursor
            }

            CODE_SAT_TO_WASEDA -> {
                cursor = database.query(
                        DataContract.SATURDAY_DB_TO_WASEDA().TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, null, null) as Cursor
            }

            CODE_SAT_TO_NISHI -> {
                cursor = database.query(
                        DataContract.SATURDAY_DB_TO_NISHI().TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, null, null) as Cursor
            }

            else -> {
                throw IllegalArgumentException("Query method cannot handle " +
                        "unsupported URI: " + uri)
            }
        }
        cursor.setNotificationUri(context!!.contentResolver, uri)
        return cursor
    }

    override fun insert(uri: Uri, values: ContentValues): Uri {
        val match = matcher.match(uri)
        val uri_new:Uri?

        when (match) {

            CODE_TO_WASEDA -> {
                uri_new = insertInWasedaTable(uri, values )
            }

            CODE_TO_NISHI -> {
                uri_new = insertInNishiTable(uri, values )
            }

            CODE_SAT_TO_WASEDA -> {
                uri_new = insertInSATWasedaTable(uri, values )
            }

            CODE_SAT_TO_NISHI -> {
                uri_new = insertInSATNishiTable(uri, values )
            }

            else -> throw IllegalArgumentException("Insert method cannot handle " +
                    "unsupported URI: " + uri)
        }
        return uri_new
    }

    fun insertInWasedaTable(uri: Uri, values: ContentValues):Uri {
        val database = dbHelper.writableDatabase

        //id is the ID of the newly inserted row. Returns -1 in case of an error with insertion.
        val id = database.insert(DataContract.DB_TO_WASEDA().TABLE_NAME, null, values)

            if (id < 0) {
                throw IllegalArgumentException("Content provider's insertion has failed.")
        }

        return ContentUris.withAppendedId(uri, id)
    }

    fun insertInNishiTable(uri: Uri, values: ContentValues):Uri {
        val database = dbHelper.writableDatabase

        //error prevention measure #1
        val id = database.insert(DataContract.DB_TO_NISHI().TABLE_NAME, null, values)
        if (id != null) {
            if (id < 0) {
                throw IllegalArgumentException("Content provider's insertion has failed.")
            }
        }
        return ContentUris.withAppendedId(uri, id as Long)
    }

    fun insertInSATWasedaTable(uri: Uri, values: ContentValues):Uri {
        val database = dbHelper.writableDatabase

        //error prevention measure #1
        val id = database.insert(DataContract.SATURDAY_DB_TO_WASEDA().TABLE_NAME, null, values)

            if (id < 0) {
                throw IllegalArgumentException("Content provider's insertion has failed.")
            }


        return ContentUris.withAppendedId(uri, id as Long)
    }

    fun insertInSATNishiTable(uri: Uri, values: ContentValues):Uri {
        val database = dbHelper.writableDatabase

        //error prevention measure #1
        val id = database.insert(DataContract.SATURDAY_DB_TO_NISHI().TABLE_NAME, null, values)
        if (id != null) {
            if (id < 0) {
                throw IllegalArgumentException("Content provider's insertion has failed.")
            }
        }


        return ContentUris.withAppendedId(uri, id)
    }

    override fun update(p0: Uri?, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        //not needed right now

        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val numOfRowsDeleted: Int
        val database = dbHelper.writableDatabase
        val match = matcher.match(uri)

        when (match) {
            CODE_TO_WASEDA -> {
                numOfRowsDeleted = database.delete(DataContract.DB_TO_WASEDA().TABLE_NAME,
                        selection, selectionArgs)
            }

            CODE_TO_NISHI -> {
                numOfRowsDeleted = database.delete(DataContract.DB_TO_NISHI().TABLE_NAME,
                        selection, selectionArgs)
            }
            CODE_SAT_TO_WASEDA -> {
                numOfRowsDeleted = database.delete(DataContract.DB_TO_WASEDA().TABLE_NAME,
                        selection, selectionArgs)
            }

            CODE_SAT_TO_NISHI -> {
                numOfRowsDeleted = database.delete(DataContract.SATURDAY_DB_TO_NISHI().TABLE_NAME,
                        selection, selectionArgs)
            }

            else -> throw IllegalArgumentException("Delete method cannot handle " +
                    "unsupported URI: " + uri)
        }

        if (numOfRowsDeleted < 0)
            throw IllegalArgumentException("Content Provider (delete method) gave an error. " +
                    "Number of deleted row was 0 or less.")

        context!!.contentResolver.notifyChange(uri, null)
        return numOfRowsDeleted
    }



    override fun getType(p0: Uri?): String {
        //not needed as of now
        return "Not Implemented in the provider"
    }
}