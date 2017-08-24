package com.scorpio.framework.net;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * To change this template use File | Settings | File Templates.
 */
public interface IConnectResultListener
{
    public void onResultData(HashMap<String, Object> mapData);
}
