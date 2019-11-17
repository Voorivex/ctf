package net.sqlcipher;

public class CursorIndexOutOfBoundsException extends IndexOutOfBoundsException {
    public CursorIndexOutOfBoundsException(int i, int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append("Index ");
        sb.append(i);
        sb.append(" requested, with a size of ");
        sb.append(i2);
        super(sb.toString());
    }

    public CursorIndexOutOfBoundsException(String str) {
        super(str);
    }
}
