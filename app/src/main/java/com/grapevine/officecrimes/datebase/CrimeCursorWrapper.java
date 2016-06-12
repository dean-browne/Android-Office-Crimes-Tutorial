/*
*
* A cursor wrapper lets you wrap a cursor you received from another place and place new
* methods on top of it
*
* TODO reread pages 272 of the book on how to improve the database!
*
 */
package com.grapevine.officecrimes.datebase;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.grapevine.officecrimes.Crime;

import com.grapevine.officecrimes.datebase.CrimeDbSchema.CrimeTable;

import java.util.Date;
import java.util.UUID;

public class CrimeCursorWrapper extends CursorWrapper {

    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Crime getCrime() {
        String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED));

        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setmTitle(title);
        crime.setmDate(new Date(date));
        crime.setmSolved(isSolved != 0);

        return crime;
    }
}
