package com.grapevine.officecrimes;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;

    public static CrimeLab get(Context context) {
        if(sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }
    private CrimeLab(Context context) {
        // We do not make use of the context object until chapter 14
        mCrimes = new ArrayList<>();
    }

    // Returns a list of all the crimes
    public List<Crime> getCrimes() {
        return mCrimes;
    }

    // Returns the crime with the given id if it exists
    public Crime getCrime(UUID id) {
        for(Crime crime : mCrimes) {
            if(crime.getmId().equals(id)) {
                return crime;
            }
        }
        return null;
    }

    // Creates a new Crime based on the crime passed in
    public void addCrime(Crime c) {
        mCrimes.add(c);
    }
}
