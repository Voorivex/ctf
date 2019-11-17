package net.sqlcipher.database;

import android.content.Context;
import android.util.Log;
import java.io.File;
import net.sqlcipher.DatabaseErrorHandler;
import net.sqlcipher.DefaultDatabaseErrorHandler;
import net.sqlcipher.database.SQLiteDatabase.CursorFactory;

public abstract class SQLiteOpenHelper {
    private static final String TAG = "SQLiteOpenHelper";
    private final Context mContext;
    private SQLiteDatabase mDatabase;
    private boolean mDeferSetWriteAheadLoggingEnabled;
    private boolean mEnableWriteAheadLogging;
    private final DatabaseErrorHandler mErrorHandler;
    private final CursorFactory mFactory;
    private final SQLiteDatabaseHook mHook;
    private boolean mIsInitializing;
    private final String mName;
    private final int mNewVersion;

    public void onConfigure(SQLiteDatabase sQLiteDatabase) {
    }

    public abstract void onCreate(SQLiteDatabase sQLiteDatabase);

    public void onOpen(SQLiteDatabase sQLiteDatabase) {
    }

    public abstract void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2);

    public SQLiteOpenHelper(Context context, String str, CursorFactory cursorFactory, int i) {
        this(context, str, cursorFactory, i, null, new DefaultDatabaseErrorHandler());
    }

    public SQLiteOpenHelper(Context context, String str, CursorFactory cursorFactory, int i, SQLiteDatabaseHook sQLiteDatabaseHook) {
        this(context, str, cursorFactory, i, sQLiteDatabaseHook, new DefaultDatabaseErrorHandler());
    }

    public SQLiteOpenHelper(Context context, String str, CursorFactory cursorFactory, int i, SQLiteDatabaseHook sQLiteDatabaseHook, DatabaseErrorHandler databaseErrorHandler) {
        this.mDatabase = null;
        this.mIsInitializing = false;
        if (i < 1) {
            StringBuilder sb = new StringBuilder();
            sb.append("Version must be >= 1, was ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        } else if (databaseErrorHandler != null) {
            this.mContext = context;
            this.mName = str;
            this.mFactory = cursorFactory;
            this.mNewVersion = i;
            this.mHook = sQLiteDatabaseHook;
            this.mErrorHandler = databaseErrorHandler;
        } else {
            throw new IllegalArgumentException("DatabaseErrorHandler param value can't be null.");
        }
    }

    public synchronized SQLiteDatabase getWritableDatabase(String str) {
        return getWritableDatabase(str == null ? null : str.toCharArray());
    }

    public synchronized SQLiteDatabase getWritableDatabase(char[] cArr) {
        return getWritableDatabase(cArr == null ? null : SQLiteDatabase.getBytes(cArr));
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:52:0x00ac */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized net.sqlcipher.database.SQLiteDatabase getWritableDatabase(byte[] r7) {
        /*
            r6 = this;
            monitor-enter(r6)
            net.sqlcipher.database.SQLiteDatabase r0 = r6.mDatabase     // Catch:{ all -> 0x00cf }
            if (r0 == 0) goto L_0x0019
            net.sqlcipher.database.SQLiteDatabase r0 = r6.mDatabase     // Catch:{ all -> 0x00cf }
            boolean r0 = r0.isOpen()     // Catch:{ all -> 0x00cf }
            if (r0 == 0) goto L_0x0019
            net.sqlcipher.database.SQLiteDatabase r0 = r6.mDatabase     // Catch:{ all -> 0x00cf }
            boolean r0 = r0.isReadOnly()     // Catch:{ all -> 0x00cf }
            if (r0 != 0) goto L_0x0019
            net.sqlcipher.database.SQLiteDatabase r7 = r6.mDatabase     // Catch:{ all -> 0x00cf }
            monitor-exit(r6)
            return r7
        L_0x0019:
            boolean r0 = r6.mIsInitializing     // Catch:{ all -> 0x00cf }
            if (r0 != 0) goto L_0x00c7
            net.sqlcipher.database.SQLiteDatabase r0 = r6.mDatabase     // Catch:{ all -> 0x00cf }
            if (r0 == 0) goto L_0x0026
            net.sqlcipher.database.SQLiteDatabase r0 = r6.mDatabase     // Catch:{ all -> 0x00cf }
            r0.lock()     // Catch:{ all -> 0x00cf }
        L_0x0026:
            r0 = 1
            r1 = 0
            r2 = 0
            r6.mIsInitializing = r0     // Catch:{ all -> 0x00b5 }
            java.lang.String r0 = r6.mName     // Catch:{ all -> 0x00b5 }
            if (r0 != 0) goto L_0x0037
            java.lang.String r7 = ""
            net.sqlcipher.database.SQLiteDatabase r7 = net.sqlcipher.database.SQLiteDatabase.create(r2, r7)     // Catch:{ all -> 0x00b5 }
        L_0x0035:
            r2 = r7
            goto L_0x0060
        L_0x0037:
            android.content.Context r0 = r6.mContext     // Catch:{ all -> 0x00b5 }
            java.lang.String r3 = r6.mName     // Catch:{ all -> 0x00b5 }
            java.io.File r0 = r0.getDatabasePath(r3)     // Catch:{ all -> 0x00b5 }
            java.lang.String r0 = r0.getPath()     // Catch:{ all -> 0x00b5 }
            java.io.File r3 = new java.io.File     // Catch:{ all -> 0x00b5 }
            r3.<init>(r0)     // Catch:{ all -> 0x00b5 }
            boolean r4 = r3.exists()     // Catch:{ all -> 0x00b5 }
            if (r4 != 0) goto L_0x0055
            java.io.File r3 = r3.getParentFile()     // Catch:{ all -> 0x00b5 }
            r3.mkdirs()     // Catch:{ all -> 0x00b5 }
        L_0x0055:
            net.sqlcipher.database.SQLiteDatabase$CursorFactory r3 = r6.mFactory     // Catch:{ all -> 0x00b5 }
            net.sqlcipher.database.SQLiteDatabaseHook r4 = r6.mHook     // Catch:{ all -> 0x00b5 }
            net.sqlcipher.DatabaseErrorHandler r5 = r6.mErrorHandler     // Catch:{ all -> 0x00b5 }
            net.sqlcipher.database.SQLiteDatabase r7 = net.sqlcipher.database.SQLiteDatabase.openOrCreateDatabase(r0, r7, r3, r4, r5)     // Catch:{ all -> 0x00b5 }
            goto L_0x0035
        L_0x0060:
            boolean r7 = r6.mDeferSetWriteAheadLoggingEnabled     // Catch:{ all -> 0x00b5 }
            if (r7 == 0) goto L_0x006a
            boolean r7 = r2.enableWriteAheadLogging()     // Catch:{ all -> 0x00b5 }
            r6.mEnableWriteAheadLogging = r7     // Catch:{ all -> 0x00b5 }
        L_0x006a:
            r6.onConfigure(r2)     // Catch:{ all -> 0x00b5 }
            int r7 = r2.getVersion()     // Catch:{ all -> 0x00b5 }
            int r0 = r6.mNewVersion     // Catch:{ all -> 0x00b5 }
            if (r7 == r0) goto L_0x009e
            r2.beginTransaction()     // Catch:{ all -> 0x00b5 }
            if (r7 != 0) goto L_0x007e
            r6.onCreate(r2)     // Catch:{ all -> 0x0099 }
            goto L_0x008d
        L_0x007e:
            int r0 = r6.mNewVersion     // Catch:{ all -> 0x0099 }
            if (r7 <= r0) goto L_0x0088
            int r0 = r6.mNewVersion     // Catch:{ all -> 0x0099 }
            r6.onDowngrade(r2, r7, r0)     // Catch:{ all -> 0x0099 }
            goto L_0x008d
        L_0x0088:
            int r0 = r6.mNewVersion     // Catch:{ all -> 0x0099 }
            r6.onUpgrade(r2, r7, r0)     // Catch:{ all -> 0x0099 }
        L_0x008d:
            int r7 = r6.mNewVersion     // Catch:{ all -> 0x0099 }
            r2.setVersion(r7)     // Catch:{ all -> 0x0099 }
            r2.setTransactionSuccessful()     // Catch:{ all -> 0x0099 }
            r2.endTransaction()     // Catch:{ all -> 0x00b5 }
            goto L_0x009e
        L_0x0099:
            r7 = move-exception
            r2.endTransaction()     // Catch:{ all -> 0x00b5 }
            throw r7     // Catch:{ all -> 0x00b5 }
        L_0x009e:
            r6.onOpen(r2)     // Catch:{ all -> 0x00b5 }
            r6.mIsInitializing = r1     // Catch:{ all -> 0x00cf }
            net.sqlcipher.database.SQLiteDatabase r7 = r6.mDatabase     // Catch:{ all -> 0x00cf }
            if (r7 == 0) goto L_0x00b1
            net.sqlcipher.database.SQLiteDatabase r7 = r6.mDatabase     // Catch:{ Exception -> 0x00ac }
            r7.close()     // Catch:{ Exception -> 0x00ac }
        L_0x00ac:
            net.sqlcipher.database.SQLiteDatabase r7 = r6.mDatabase     // Catch:{ all -> 0x00cf }
            r7.unlock()     // Catch:{ all -> 0x00cf }
        L_0x00b1:
            r6.mDatabase = r2     // Catch:{ all -> 0x00cf }
            monitor-exit(r6)
            return r2
        L_0x00b5:
            r7 = move-exception
            r6.mIsInitializing = r1     // Catch:{ all -> 0x00cf }
            net.sqlcipher.database.SQLiteDatabase r0 = r6.mDatabase     // Catch:{ all -> 0x00cf }
            if (r0 == 0) goto L_0x00c1
            net.sqlcipher.database.SQLiteDatabase r0 = r6.mDatabase     // Catch:{ all -> 0x00cf }
            r0.unlock()     // Catch:{ all -> 0x00cf }
        L_0x00c1:
            if (r2 == 0) goto L_0x00c6
            r2.close()     // Catch:{ all -> 0x00cf }
        L_0x00c6:
            throw r7     // Catch:{ all -> 0x00cf }
        L_0x00c7:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException     // Catch:{ all -> 0x00cf }
            java.lang.String r0 = "getWritableDatabase called recursively"
            r7.<init>(r0)     // Catch:{ all -> 0x00cf }
            throw r7     // Catch:{ all -> 0x00cf }
        L_0x00cf:
            r7 = move-exception
            monitor-exit(r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: net.sqlcipher.database.SQLiteOpenHelper.getWritableDatabase(byte[]):net.sqlcipher.database.SQLiteDatabase");
    }

    public synchronized SQLiteDatabase getReadableDatabase(String str) {
        return getReadableDatabase(str == null ? null : str.toCharArray());
    }

    public synchronized SQLiteDatabase getReadableDatabase(char[] cArr) {
        return getReadableDatabase(cArr == null ? null : SQLiteDatabase.getBytes(cArr));
    }

    /* JADX INFO: finally extract failed */
    public synchronized SQLiteDatabase getReadableDatabase(byte[] bArr) {
        SQLiteDatabase sQLiteDatabase;
        if (this.mDatabase != null && this.mDatabase.isOpen()) {
            return this.mDatabase;
        } else if (!this.mIsInitializing) {
            try {
                return getWritableDatabase(bArr);
            } catch (SQLiteException e) {
                if (this.mName != null) {
                    String str = TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Couldn't open ");
                    sb.append(this.mName);
                    sb.append(" for writing (will try read-only):");
                    Log.e(str, sb.toString(), e);
                    sQLiteDatabase = null;
                    this.mIsInitializing = true;
                    String path = this.mContext.getDatabasePath(this.mName).getPath();
                    File file = new File(path);
                    File file2 = new File(this.mContext.getDatabasePath(this.mName).getParent());
                    if (!file2.exists()) {
                        file2.mkdirs();
                    }
                    if (!file.exists()) {
                        this.mIsInitializing = false;
                        SQLiteDatabase writableDatabase = getWritableDatabase(bArr);
                        this.mIsInitializing = true;
                        writableDatabase.close();
                    }
                    sQLiteDatabase = SQLiteDatabase.openDatabase(path, bArr, this.mFactory, 1, this.mHook, this.mErrorHandler);
                    if (sQLiteDatabase.getVersion() == this.mNewVersion) {
                        onOpen(sQLiteDatabase);
                        String str2 = TAG;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("Opened ");
                        sb2.append(this.mName);
                        sb2.append(" in read-only mode");
                        Log.w(str2, sb2.toString());
                        this.mDatabase = sQLiteDatabase;
                        SQLiteDatabase sQLiteDatabase2 = this.mDatabase;
                        this.mIsInitializing = false;
                        if (!(sQLiteDatabase == null || sQLiteDatabase == this.mDatabase)) {
                            sQLiteDatabase.close();
                        }
                        return sQLiteDatabase2;
                    }
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Can't upgrade read-only database from version ");
                    sb3.append(sQLiteDatabase.getVersion());
                    sb3.append(" to ");
                    sb3.append(this.mNewVersion);
                    sb3.append(": ");
                    sb3.append(path);
                    throw new SQLiteException(sb3.toString());
                }
                throw e;
            } catch (Throwable th) {
                this.mIsInitializing = false;
                if (!(sQLiteDatabase == null || sQLiteDatabase == this.mDatabase)) {
                    sQLiteDatabase.close();
                }
                throw th;
            }
        } else {
            throw new IllegalStateException("getReadableDatabase called recursively");
        }
    }

    public synchronized void close() {
        if (this.mIsInitializing) {
            throw new IllegalStateException("Closed during initialization");
        } else if (this.mDatabase != null && this.mDatabase.isOpen()) {
            this.mDatabase.close();
            this.mDatabase = null;
        }
    }

    public String getDatabaseName() {
        return this.mName;
    }

    public void setWriteAheadLoggingEnabled(boolean z) {
        synchronized (this) {
            if (this.mEnableWriteAheadLogging != z) {
                if (this.mDatabase == null || !this.mDatabase.isOpen() || this.mDatabase.isReadOnly()) {
                    this.mDeferSetWriteAheadLoggingEnabled = z;
                } else {
                    if (z) {
                        this.mDatabase.enableWriteAheadLogging();
                    } else {
                        this.mDatabase.disableWriteAheadLogging();
                    }
                    this.mEnableWriteAheadLogging = z;
                }
            }
        }
    }

    public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append("Can't downgrade database from version ");
        sb.append(i);
        sb.append(" to ");
        sb.append(i2);
        throw new SQLiteException(sb.toString());
    }
}
