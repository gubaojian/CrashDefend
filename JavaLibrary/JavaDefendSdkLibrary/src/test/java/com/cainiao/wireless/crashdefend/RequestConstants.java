package com.cainiao.wireless.crashdefend;

public class RequestConstants {

    public  final String INTERCEPT_CHOOSE = "CHOOSE";
    public  final String INTERCEPT_INTERCEPT = "INTERCEPT";
    public  final String INTERCEPT_PASS = "PASS";

    public  final String DO_CONSIGNMENT = "INLAND_OPT_DISPATCH";
    public  final String DO_CONSIGNMENT_AND_LOADCAR = "INLAND_OPT_DISPATCH_HANDOVER";
    public  final String DO_LOADCAR = "INLAND_OPT_HANDOVER";
    public  final String DO_CANCEL_LOADCAR = "INLAND_OPT_CANCEL_HANDOVER";
    public  final String DO_CANCEL_CONSIGNMENT = "INLAND_OPT_CANCEL_DISPATCH";

    public  final String NOT_MERGED = "B2B_PACKAGE_NOT_MERGED";
    public  final String WITHOUT_DISPATCH_ORDER = "B2B_PACKAGE_WITHOUT_DISPATCH_ORDER";
    public  final String INTERCEPT = "INTERCEPT_WAYBILL";
    public  final String FLOW_CONFIRM = "HANDOVER_VEHICLE_VERIFY";
    public  final String CANCEL_CONFIRM = "CANCEL_HANDOVER";

    public  final int REQUEST_INTERCEPT = 10002;
    public  final int REQUEST_NOT_MERGED = 10012;
    public  final int REQUEST_FLOW_CONFIRM = 10022;
    public  final int REQUEST_WITHOUT_DISPATCH_ORDER = 10032;
    public  final int REQUEST_CANCEL = 10042;
    public  final int NO_CONFIRM = 0;
    public  final int REQUEST_DIR_CHECK = 10052;
}
