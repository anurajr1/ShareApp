package com.anu.developers3k.shareapp.adapter;

/**
 * Created by I321994
 */
public class DeviceInfoManager {
    private String deviceinfoname;
    private String deviceinfovalue;

    public DeviceInfoManager(String deviceinfoname, String deviceinfovalue) {
        super();
        this.setDeviceinfoname(deviceinfoname);
        this.setDeviceinfovalue(deviceinfovalue);
    }

    public String getDeviceinfoname() {
        return deviceinfoname;
    }

    public String getDeviceinfovalue() {
        return deviceinfovalue;
    }

    public void setDeviceinfoname(String deviceinfoname) {
        this.deviceinfoname = deviceinfoname;
    }

    public void setDeviceinfovalue(String deviceinfovalue) {
        this.deviceinfovalue = deviceinfovalue;
    }
}
