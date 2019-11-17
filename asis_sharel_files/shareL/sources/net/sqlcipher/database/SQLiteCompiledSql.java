package net.sqlcipher.database;

import android.util.Log;

class SQLiteCompiledSql {
    private static final String TAG = "SQLiteCompiledSql";
    SQLiteDatabase mDatabase;
    private boolean mInUse = false;
    private String mSqlStmt = null;
    private Throwable mStackTrace = null;
    long nHandle = 0;
    long nStatement = 0;

    private final native void native_compile(String str);

    private final native void native_finalize();

    SQLiteCompiledSql(SQLiteDatabase sQLiteDatabase, String str) {
        if (sQLiteDatabase.isOpen()) {
            this.mDatabase = sQLiteDatabase;
            this.mSqlStmt = str;
            this.mStackTrace = new DatabaseObjectNotClosedException().fillInStackTrace();
            this.nHandle = sQLiteDatabase.mNativeHandle;
            compile(str, true);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("database ");
        sb.append(sQLiteDatabase.getPath());
        sb.append(" already closed");
        throw new IllegalStateException(sb.toString());
    }

    private void compile(String str, boolean z) {
        if (!this.mDatabase.isOpen()) {
            StringBuilder sb = new StringBuilder();
            sb.append("database ");
            sb.append(this.mDatabase.getPath());
            sb.append(" already closed");
            throw new IllegalStateException(sb.toString());
        } else if (z) {
            this.mDatabase.lock();
            try {
                native_compile(str);
            } finally {
                this.mDatabase.unlock();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void releaseSqlStatement() {
        if (this.nStatement != 0) {
            if (SQLiteDebug.DEBUG_ACTIVE_CURSOR_FINALIZATION) {
                StringBuilder sb = new StringBuilder();
                sb.append("closed and deallocated DbObj (id#");
                sb.append(this.nStatement);
                sb.append(")");
                Log.v(TAG, sb.toString());
            }
            try {
                this.mDatabase.lock();
                native_finalize();
                this.nStatement = 0;
            } finally {
                this.mDatabase.unlock();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002d, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean acquire() {
        /*
            r5 = this;
            monitor-enter(r5)
            boolean r0 = r5.mInUse     // Catch:{ all -> 0x002e }
            if (r0 == 0) goto L_0x0008
            r0 = 0
            monitor-exit(r5)
            return r0
        L_0x0008:
            r0 = 1
            r5.mInUse = r0     // Catch:{ all -> 0x002e }
            boolean r1 = net.sqlcipher.database.SQLiteDebug.DEBUG_ACTIVE_CURSOR_FINALIZATION     // Catch:{ all -> 0x002e }
            if (r1 == 0) goto L_0x002c
            java.lang.String r1 = "SQLiteCompiledSql"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x002e }
            r2.<init>()     // Catch:{ all -> 0x002e }
            java.lang.String r3 = "Acquired DbObj (id#"
            r2.append(r3)     // Catch:{ all -> 0x002e }
            long r3 = r5.nStatement     // Catch:{ all -> 0x002e }
            r2.append(r3)     // Catch:{ all -> 0x002e }
            java.lang.String r3 = ") from DB cache"
            r2.append(r3)     // Catch:{ all -> 0x002e }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x002e }
            android.util.Log.v(r1, r2)     // Catch:{ all -> 0x002e }
        L_0x002c:
            monitor-exit(r5)
            return r0
        L_0x002e:
            r0 = move-exception
            monitor-exit(r5)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: net.sqlcipher.database.SQLiteCompiledSql.acquire():boolean");
    }

    /* access modifiers changed from: 0000 */
    public synchronized void release() {
        if (SQLiteDebug.DEBUG_ACTIVE_CURSOR_FINALIZATION) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Released DbObj (id#");
            sb.append(this.nStatement);
            sb.append(") back to DB cache");
            Log.v(str, sb.toString());
        }
        this.mInUse = false;
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        try {
            if (this.nStatement != 0) {
                boolean z = SQLiteDebug.DEBUG_ACTIVE_CURSOR_FINALIZATION;
                String str = TAG;
                if (z) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("** warning ** Finalized DbObj (id#");
                    sb.append(this.nStatement);
                    sb.append(")");
                    Log.v(str, sb.toString());
                }
                int length = this.mSqlStmt.length();
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Releasing statement in a finalizer. Please ensure that you explicitly call close() on your cursor: ");
                String str2 = this.mSqlStmt;
                if (length > 100) {
                    length = 100;
                }
                sb2.append(str2.substring(0, length));
                Log.w(str, sb2.toString(), this.mStackTrace);
                releaseSqlStatement();
                super.finalize();
            }
        } finally {
            super.finalize();
        }
    }
}
