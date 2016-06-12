package com.grapevine.officecrimes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.grapevine.officecrimes.datebase.CrimeBaseHelper;
import com.grapevine.officecrimes.datebase.CrimeCursorWrapper;
import com.grapevine.officecrimes.datebase.CrimeDbSchema.CrimeTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CrimeLab get(Context context) {
        if(sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        // We will make use of the context member variable in chapter 16
        /*
        * We are using application context because this is a singleton and will be alive a lot longer than one activity
         */
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).
                getWritableDatabase();

    }

    private static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getmId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getmTitle());
        values.put(CrimeTable.Cols.DATE, crime.getmDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.ismSolved() ? 1 : 0);

        return values;
    }


    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,
                null, // Columns -> Null selects all
                whereClause,
                whereArgs,
                null,   // groupBy
                null,   // having
                null    // orderBy
        );

        return new CrimeCursorWrapper(cursor);
    }

    // Returns a list of all the crimes
    public List<Crime> getCrimes() {
        List<Crime> crimes = new ArrayList<>();
        CrimeCursorWrapper cursor = queryCrimes(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return crimes;
    }

    // Returns the crime with the given id if it exists
    public Crime getCrime(UUID id) {
        CrimeCursorWrapper cursor = queryCrimes(CrimeTable.Cols.UUID + " = ?",
                new String[] {id.toString() }
        );

        try {
            if(cursor.getCount() == 0){
                return null;
            }

            cursor.moveToFirst();
            return cursor.getCrime();
        }
        finally {
            cursor.close();
        }
    }

    // Inserts the crime into the database
    public void addCrime(Crime c) {
        ContentValues values = getContentValues(c);
        mDatabase.insert(CrimeTable.NAME, null, values);
    }

    // Allows updating of a crime already in the database
    public void updateCrime(Crime crime) {
        String uuidString = crime.getmId().toString();
        ContentValues values = getContentValues(crime);

        // WUUUTTTTT>????
        mDatabase.update(CrimeTable.NAME, values, CrimeTable.Cols.UUID + " = ?",
                new String[]{ uuidString });
    }



    // Deletes a new Crime based on the id passed in
    public void deleteCrime(UUID id) {

    }
}
