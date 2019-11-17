package net.sqlcipher;

import android.support.v4.media.session.PlaybackStateCompat;

public class DefaultCursorWindowAllocation implements CursorWindowAllocation {
    private long WindowAllocationUnbounded = 0;
    private long initialAllocationSize = PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED;

    public long getInitialAllocationSize() {
        return this.initialAllocationSize;
    }

    public long getGrowthPaddingSize() {
        return this.initialAllocationSize;
    }

    public long getMaxAllocationSize() {
        return this.WindowAllocationUnbounded;
    }
}
