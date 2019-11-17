package com.asisctf.ShareL.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class UsrPLink {
    public int code;
    public List<Links> data;

    public class Links {
        public String link;
        public int link_id;
        public String link_name;
        @SerializedName("private")
        public int privat;
        public int user_id;

        public Links() {
        }
    }
}
