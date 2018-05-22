package com.newland.location;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    StationInfo stationInfo = new StationInfo();;
    private static final String TAG = "MainActivity";
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.tv_info);
        getCellInfo();
    }


    /**
     * 获取基站信息
     */
    private StationInfo getCellInfo() {

        /** 调用API获取基站信息 */
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        if (!hasSimCard(this)){ //判断有没有sim卡，如果没有安装sim卡下面则会异常
            textView.setText("请插sim卡");
            return null;
        }

        String operator = telephonyManager.getNetworkOperator();

        int mcc = Integer.parseInt(operator.substring(0, 3));
        int mnc = Integer.parseInt(operator.substring(3));

        //参考http://www.cellocation.com/interfac/
        int cid = 0;
        int lac = 0;
        if (mnc == 11 || mnc == 03 || mnc == 05){  //03 05 11 为电信CDMA
            CdmaCellLocation location = (CdmaCellLocation) telephonyManager.getCellLocation();
            //这里的值可根据接口需要的参数获取
            cid = location.getBaseStationId();
            lac = location.getNetworkId();
            mnc = location.getSystemId();
        } else {
            GsmCellLocation location = (GsmCellLocation) telephonyManager.getCellLocation();
            cid = location.getCid();
            lac = location.getLac();
        }

        /** 将获得的数据放到结构体中 */
        stationInfo.setMCC(mcc);
        stationInfo.setMNC(mnc);
        stationInfo.setLAC(lac);
        stationInfo.setCID(cid);

        Log.e(TAG, "getCellInfo: " + stationInfo.toString() );

        textView.setText(stationInfo.toString());
        return stationInfo;

    }

    public static boolean hasSimCard(Context context) {
        TelephonyManager telMgr = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        int simState = telMgr.getSimState();
        boolean result = true;
        switch (simState) {
            case TelephonyManager.SIM_STATE_ABSENT:
                result = false; // 没有SIM卡
                break;
            case TelephonyManager.SIM_STATE_UNKNOWN:
                result = false;
                break;
        }
        Log.d("try", result ? "有SIM卡" : "无SIM卡");
        return result;
    }
}
