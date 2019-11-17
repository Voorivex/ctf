package com.asisctf.ShareL.models;

public class UserProfile {
    public int code;
    public mData data;

    public class mData {
        public String auth_hash;
        public int links;
        public int user_id;
        public String user_type;

        public mData() {
        }
    }
}
