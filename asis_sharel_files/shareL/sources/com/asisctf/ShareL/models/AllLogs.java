package com.asisctf.ShareL.models;

public class AllLogs {
    public int code;
    public mData data;

    public class mData {
        public Logs logs;

        public class Logs {
            public String log_details;
            public int log_id;
            public String log_name;
            public int user_id;

            public Logs() {
            }
        }

        public mData() {
        }
    }
}
