package io.github.grooters.libraryseat.api;

/**
 * Create by 李林浪 in 2018/9/24
 * Elegant Code...
 */
public class SitingApi {
//    public static final String REQUEST_SIT_URL="http://"+IpAddress.IP+":8080/scanseat";
//    public static final String REQUEST_LEAVE_SIT_URL="http://"+IpAddress.IP+":8080/leaveseat";
    public static final String REQUEST_LEAVE_SIT_URL=IpAddress.URL+"/leaveseat";
    public static final String REQUEST_SIT_URL=IpAddress.URL+"/scanseat";
    public static final String REQUEST_PAUSE_URL=IpAddress.URL+"/pause";
    public static final String REQUEST_CANCLE_PAUSE_URL=IpAddress.URL+"/back";
}
