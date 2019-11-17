package com.asisctf.ShareL.models;

public class NewDevice {
    public int code;
    public mData data;

    public class mData {
        public String auth_hash;
        public int user_id;

        public mData() {
        }
    }
}
