package net.sqlcipher;

import java.util.ArrayList;

public class MatrixCursor extends AbstractCursor {
    private final int columnCount;
    private final String[] columnNames;
    /* access modifiers changed from: private */
    public Object[] data;
    private int rowCount;

    public class RowBuilder {
        private final int endIndex;
        private int index;

        RowBuilder(int i, int i2) {
            this.index = i;
            this.endIndex = i2;
        }

        public RowBuilder add(Object obj) {
            if (this.index != this.endIndex) {
                Object[] access$000 = MatrixCursor.this.data;
                int i = this.index;
                this.index = i + 1;
                access$000[i] = obj;
                return this;
            }
            throw new CursorIndexOutOfBoundsException("No more columns left.");
        }
    }

    public MatrixCursor(String[] strArr, int i) {
        this.rowCount = 0;
        this.columnNames = strArr;
        this.columnCount = strArr.length;
        int i2 = 1;
        if (i >= 1) {
            i2 = i;
        }
        this.data = new Object[(this.columnCount * i2)];
    }

    public MatrixCursor(String[] strArr) {
        this(strArr, 16);
    }

    private Object get(int i) {
        if (i < 0 || i >= this.columnCount) {
            StringBuilder sb = new StringBuilder();
            sb.append("Requested column: ");
            sb.append(i);
            sb.append(", # of columns: ");
            sb.append(this.columnCount);
            throw new CursorIndexOutOfBoundsException(sb.toString());
        } else if (this.mPos < 0) {
            throw new CursorIndexOutOfBoundsException("Before first row.");
        } else if (this.mPos < this.rowCount) {
            return this.data[(this.mPos * this.columnCount) + i];
        } else {
            throw new CursorIndexOutOfBoundsException("After last row.");
        }
    }

    public RowBuilder newRow() {
        this.rowCount++;
        int i = this.rowCount * this.columnCount;
        ensureCapacity(i);
        return new RowBuilder(i - this.columnCount, i);
    }

    public void addRow(Object[] objArr) {
        int length = objArr.length;
        int i = this.columnCount;
        if (length == i) {
            int i2 = this.rowCount;
            this.rowCount = i2 + 1;
            int i3 = i2 * i;
            ensureCapacity(i + i3);
            System.arraycopy(objArr, 0, this.data, i3, this.columnCount);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("columnNames.length = ");
        sb.append(this.columnCount);
        sb.append(", columnValues.length = ");
        sb.append(objArr.length);
        throw new IllegalArgumentException(sb.toString());
    }

    public void addRow(Iterable<?> iterable) {
        int i = this.rowCount;
        int i2 = this.columnCount;
        int i3 = i * i2;
        int i4 = i2 + i3;
        ensureCapacity(i4);
        if (iterable instanceof ArrayList) {
            addRow((ArrayList) iterable, i3);
            return;
        }
        Object[] objArr = this.data;
        for (Object next : iterable) {
            if (i3 != i4) {
                int i5 = i3 + 1;
                objArr[i3] = next;
                i3 = i5;
            } else {
                throw new IllegalArgumentException("columnValues.size() > columnNames.length");
            }
        }
        if (i3 == i4) {
            this.rowCount++;
            return;
        }
        throw new IllegalArgumentException("columnValues.size() < columnNames.length");
    }

    private void addRow(ArrayList<?> arrayList, int i) {
        int size = arrayList.size();
        if (size == this.columnCount) {
            this.rowCount++;
            Object[] objArr = this.data;
            for (int i2 = 0; i2 < size; i2++) {
                objArr[i + i2] = arrayList.get(i2);
            }
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("columnNames.length = ");
        sb.append(this.columnCount);
        sb.append(", columnValues.size() = ");
        sb.append(size);
        throw new IllegalArgumentException(sb.toString());
    }

    private void ensureCapacity(int i) {
        Object[] objArr = this.data;
        if (i > objArr.length) {
            int length = objArr.length * 2;
            if (length >= i) {
                i = length;
            }
            this.data = new Object[i];
            System.arraycopy(objArr, 0, this.data, 0, objArr.length);
        }
    }

    public int getCount() {
        return this.rowCount;
    }

    public String[] getColumnNames() {
        return this.columnNames;
    }

    public String getString(int i) {
        Object obj = get(i);
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    public short getShort(int i) {
        Object obj = get(i);
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Number) {
            return ((Number) obj).shortValue();
        }
        return Short.parseShort(obj.toString());
    }

    public int getInt(int i) {
        Object obj = get(i);
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        }
        return Integer.parseInt(obj.toString());
    }

    public long getLong(int i) {
        Object obj = get(i);
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        }
        return Long.parseLong(obj.toString());
    }

    public float getFloat(int i) {
        Object obj = get(i);
        if (obj == null) {
            return 0.0f;
        }
        if (obj instanceof Number) {
            return ((Number) obj).floatValue();
        }
        return Float.parseFloat(obj.toString());
    }

    public double getDouble(int i) {
        Object obj = get(i);
        if (obj == null) {
            return 0.0d;
        }
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }
        return Double.parseDouble(obj.toString());
    }

    public int getType(int i) {
        return DatabaseUtils.getTypeOfObject(get(i));
    }

    public boolean isNull(int i) {
        return get(i) == null;
    }
}