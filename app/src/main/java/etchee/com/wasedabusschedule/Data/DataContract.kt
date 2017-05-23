package etchee.com.wasedabusschedule.Data

import android.content.ContentResolver
import android.net.Uri
import android.provider.BaseColumns

/**
 * Class to contain all the constants
 * Created by rikutoechigoya on 2017/05/19.
 */

class DataContract private constructor() {


    data class GlobalConstants(
            val PASS_STRING:String = "PASS_STRING",

            //universal declarations
            val CONTENT_AUTHORITY: String = "etchee.com.wasedabusschedule",
            val BASE_CONTENT_URI: Uri = Uri.parse("content://" + CONTENT_AUTHORITY),
            val DATABASE_NAME: String = "DATABASE",

            //Specific URI addresses
            val PATH_TO_WASEDA: String = "TO-WASEDA",
            val PATH_TO_NISHI: String = "TO-NISHI",
            val PATH_SAT_TO_WASEDA: String = "SAT-TO-WASEDA",
            val PATH_SAT_TO_NISHI: String = "SAT-TO-NISHI"
    )

    data class DB_TO_WASEDA(

            val CONTENT_URI: Uri? = GlobalConstants().BASE_CONTENT_URI.buildUpon()
                    .appendPath(GlobalConstants().PATH_TO_WASEDA).build(),

            val CONTENT_ITEM_TYPE: String = ContentResolver.CURSOR_ITEM_BASE_TYPE +
                    "/" + GlobalConstants().CONTENT_AUTHORITY +
                    "/" + GlobalConstants().PATH_TO_WASEDA,

            val TABLE_NAME: String = "TABLE_TO_WASEDA",
            val _ID: String = BaseColumns._ID,
            val COLUMN_HOUR: String = "COLUMN_HOUR",
            val COLUMN_MIN:String = "COLUMN_MIN",
            val COLUMN_FLAG:String = "COLUMN_FLAG"
    )

    data class DB_TO_NISHI(

            val CONTENT_URI: Uri? = GlobalConstants().BASE_CONTENT_URI.buildUpon()
                    .appendPath(GlobalConstants().PATH_TO_NISHI).build(),

            val CONTENT_ITEM_TYPE: String = ContentResolver.CURSOR_ITEM_BASE_TYPE +
                    "/" + GlobalConstants().CONTENT_AUTHORITY +
                    "/" + GlobalConstants().PATH_TO_NISHI,

            val TABLE_NAME: String = "TABLE_TO_NISHI",
            val _ID: String = BaseColumns._ID,
            val COLUMN_HOUR: String = "COLUMN_HOUR",
            val COLUMN_MIN:String = "COLUMN_MIN",
            val COLUMN_FLAG:String = "COLUMN_FLAG"
    )

    data class SATURDAY_DB_TO_WASEDA(
            val CONTENT_URI: Uri? = GlobalConstants().BASE_CONTENT_URI.buildUpon()
                    .appendPath(GlobalConstants().PATH_SAT_TO_WASEDA).build(),

            val CONTENT_ITEM_TYPE: String = ContentResolver.CURSOR_ITEM_BASE_TYPE +
                    "/" + GlobalConstants().CONTENT_AUTHORITY +
                    "/" + GlobalConstants().PATH_SAT_TO_WASEDA,

            val TABLE_NAME: String = "TABLE_TO_NISHI",
            val _ID: String = BaseColumns._ID,
            val COLUMN_HOUR: String = "COLUMN_HOUR",
            val COLUMN_MIN:String = "COLUMN_MIN",
            val COLUMN_FLAG:String = "COLUMN_FLAG"
    )

    data class SATURDAY_DB_TO_NISHI(
            val CONTENT_URI: Uri? = GlobalConstants().BASE_CONTENT_URI.buildUpon()
                    .appendPath(GlobalConstants().PATH_SAT_TO_NISHI).build(),

            val CONTENT_ITEM_TYPE: String = ContentResolver.CURSOR_ITEM_BASE_TYPE +
                    "/" + GlobalConstants().CONTENT_AUTHORITY +
                    "/" + GlobalConstants().PATH_SAT_TO_NISHI,

            val TABLE_NAME: String = "TABLE_TO_NISHI",
            val _ID: String = BaseColumns._ID,
            val COLUMN_HOUR: String = "COLUMN_HOUR",
            val COLUMN_MIN:String = "COLUMN_MIN",
            val COLUMN_FLAG:String = "COLUMN_FLAG"
    )
}
