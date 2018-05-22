package com.newland.location;

/**
 * @author lin
 * @version 2018/5/18 0018
 *
 * /**
 *  基站信息
 *  MCC: 国家代码：中国代码 460
 *  MNC，移动设备网络代码（Mobile Network Code，MNC），中国移动 = 00，中国联通 = 01, 中国电信 = 03 05 11
 *  LAC，Location Area Code，位置区域码；
 *  CID，Cell Identity，基站编号，是个16位的数据（范围是0到65535）。
 */

public class StationInfo {

    private int MCC;
    private int MNC;
    private int LAC;
    private int CID;

    @Override
    public String toString() {
        return "基站信息\n" +
                " 国家代码 MCC=" + MCC +
                "\n 设备网络 MNC=" + MNC +
                "\n 位置区域码 LAC=" + LAC +
                "\n 基站编号 CID=" + CID ;
    }

    public int getMCC() {
        return MCC;
    }

    public void setMCC(int MCC) {
        this.MCC = MCC;
    }

    public int getMNC() {
        return MNC;
    }

    public void setMNC(int MNC) {
        this.MNC = MNC;
    }

    public int getLAC() {
        return LAC;
    }

    public void setLAC(int LAC) {
        this.LAC = LAC;
    }

    public int getCID() {
        return CID;
    }

    public void setCID(int CID) {
        this.CID = CID;
    }


}
