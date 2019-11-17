package net.sqlcipher;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import net.sqlcipher.IContentObserver.Stub;

public abstract class BulkCursorNative extends Binder implements IBulkCursor {
    public IBinder asBinder() {
        return this;
    }

    public BulkCursorNative() {
        attachInterface(this, IBulkCursor.descriptor);
    }

    public static IBulkCursor asInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IBulkCursor iBulkCursor = (IBulkCursor) iBinder.queryLocalInterface(IBulkCursor.descriptor);
        if (iBulkCursor != null) {
            return iBulkCursor;
        }
        return new BulkCursorProxy(iBinder);
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        int i3 = 0;
        String str = IBulkCursor.descriptor;
        switch (i) {
            case 1:
                parcel.enforceInterface(str);
                CursorWindow window = getWindow(parcel.readInt());
                if (window == null) {
                    parcel2.writeInt(0);
                    return true;
                }
                parcel2.writeNoException();
                parcel2.writeInt(1);
                window.writeToParcel(parcel2, 0);
                return true;
            case 2:
                parcel.enforceInterface(str);
                int count = count();
                parcel2.writeNoException();
                parcel2.writeInt(count);
                return true;
            case 3:
                parcel.enforceInterface(str);
                String[] columnNames = getColumnNames();
                parcel2.writeNoException();
                parcel2.writeInt(columnNames.length);
                int length = columnNames.length;
                while (i3 < length) {
                    parcel2.writeString(columnNames[i3]);
                    i3++;
                }
                return true;
            case 4:
                parcel.enforceInterface(str);
                boolean updateRows = updateRows(parcel.readHashMap(null));
                parcel2.writeNoException();
                if (updateRows) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 5:
                parcel.enforceInterface(str);
                boolean deleteRow = deleteRow(parcel.readInt());
                parcel2.writeNoException();
                if (deleteRow) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 6:
                parcel.enforceInterface(str);
                deactivate();
                parcel2.writeNoException();
                return true;
            case 7:
                parcel.enforceInterface(str);
                int requery = requery(Stub.asInterface(parcel.readStrongBinder()), (CursorWindow) CursorWindow.CREATOR.createFromParcel(parcel));
                parcel2.writeNoException();
                parcel2.writeInt(requery);
                parcel2.writeBundle(getExtras());
                return true;
            case 8:
                parcel.enforceInterface(str);
                onMove(parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 9:
                parcel.enforceInterface(str);
                boolean wantsAllOnMoveCalls = getWantsAllOnMoveCalls();
                parcel2.writeNoException();
                if (wantsAllOnMoveCalls) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 10:
                parcel.enforceInterface(str);
                Bundle extras = getExtras();
                parcel2.writeNoException();
                parcel2.writeBundle(extras);
                return true;
            case 11:
                parcel.enforceInterface(str);
                Bundle respond = respond(parcel.readBundle(getClass().getClassLoader()));
                parcel2.writeNoException();
                parcel2.writeBundle(respond);
                return true;
            case 12:
                try {
                    parcel.enforceInterface(str);
                    close();
                    parcel2.writeNoException();
                    return true;
                } catch (Exception e) {
                    DatabaseUtils.writeExceptionToParcel(parcel2, e);
                    return true;
                }
            default:
                return super.onTransact(i, parcel, parcel2, i2);
        }
    }
}
