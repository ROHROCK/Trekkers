package com.example.rohit.gmapslolipop;

public class Trek {
    private String tname;
    private String tduration;
    private String tcity;
    private String trecommendation;

    Trek(String tname, String tduration, String tcity, String trecommendation) {
        this.tname = tname;
        this.tduration = tduration;
        this.tcity = tcity;
        this.trecommendation = trecommendation;
    }

    String getTname() {
        return tname;
    }

    String getTduration() {
        return tduration;
    }

    String getTrecommendation() {
        return trecommendation;
    }

    String getTcity() {
        return tcity;
    }
}
