private final List<Data> mPackages = new ArrayList<>();
private final long END_TAG = 0xFFFFFFFFL;//End Tag

public class HistoryOfRecord {
    /**
     * Device orignal query time
     */
    public long stamp;
    /**
     * Display time in local zone
     */
    public long record;
}
private List<HistoryOfRecord> mHistoryOfRecords;//HR records list


private byte[] subSlice(final int start, final byte[] value) {
    return subByte(value, start, value.length - 1);
}

private byte[] subByte(final byte[] source, final int start, final int end) {
    final byte[] dest = new byte[end - start];                                     
    System.arraycopy(source, start, dest, 0, dest.length);                         
    return dest;                                                                   
}                                                                                  

private long getLongParse(byte[] bytes, int pos, int len) {
    long val = 0;
    len += pos;
    for (int i = pos; i < len; i++) {
        val <<= 8;
        val |= (long) bytes[i] & 0xFF;
    }
    return val;
}

private long restoreZoneUTC(final long stamp) {             
    Calendar calendar = Calendar.getInstance();                   
    calendar.setTimeInMillis(stamp * 1000L);                      
    int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);          
    int dstOffset = calendar.get(Calendar.DST_OFFSET);            
    calendar.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
    return calendar.getTimeInMillis();                            
}                                                                 

if (mode == 0x21) {// HR history record 0x21
    if (mHistoryOfRecords == null) {
        mHistoryOfRecords = new ArrayList<>();
    }
    final long utcTag = getLongParse(value, 3, 4);
    if (utcTag != END_TAG) {
        mPackages.add(data);
    } else {
        int offset;
        byte[] slice;
        for (int index = 0; index < mPackages.size(); index++) {
            slice = subSlice(3, mPackages.get(index).getValue());
            for (int i = 0; i < slice.length / 4; i++) {
                offset = i * 4;
                long stamp = getLongParse(slice, offset, 4);
                long record = restoreZoneUTC(stamp);
                mHistoryOfRecords.add(new HistoryOfRecord(stamp, record));
            }
        }
        onHistoryOfHRRecordReceived(device, mHistoryOfRecords);
        mHistoryOfRecords.clear();
        mPackages.clear();
    }
} 