package com.pos.yza.yzapos.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.pos.yza.yzapos.data.representations.Staff;

/**
 * The Room Database that contains the Staff table.
 */
@Database(entities = {Staff.class}, version = 1)
public abstract class PoSDatabase extends RoomDatabase {

    private static PoSDatabase INSTANCE;

    public abstract StaffDao staffDao();

    private static final Object sLock = new Object();

    public static PoSDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        PoSDatabase.class, "Staff.db")
                        .build();
            }
            return INSTANCE;
        }
    }

}
