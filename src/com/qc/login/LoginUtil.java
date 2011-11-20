package com.qc.login;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.conn.HttpHostConnectException;

import android.os.Handler;
import android.util.Log;

import com.qc.xmlrpc.XMLRPCClient;
import com.qc.xmlrpc.XMLRPCException;
import com.qc.xmlrpc.XMLRPCFault;

public class LoginUtil {

    public static final String HOST = "http://172.23.145.251:10088/semi-god-semi-devil/rpcs/user_rpc";
    public static final String KEY = "semi-god-semi-devil-v0.1-acdjiac5tq-android";
    public static final String UDID = "qc-1234";

    private XMLRPCClient client = new XMLRPCClient(HOST);

    public void sendLoginRequest(String email, String password, String nickname) {
        // add 2 to 4
        XMLRPCMethod method = new XMLRPCMethod("create_user", new XMLRPCMethodCallback() {
            public void callFinished(Object result) {
                Log.i("login", "XML RPC response:" + result.toString());
            }
        });
        method.call(getLoginParam(email, password, nickname));

        // check whether x is inside range 4..10
        // boolean isInside = (Boolean) client.call("isInside", x, 4, 10);

        /*
         * try { Object response = client.call("create_user", 1); // Object
         * response = client.call("create_user", getLoginParam(email, //
         * password, nickname)); Log.i("login", response.toString()); } catch
         * (XMLRPCException e) { // TODO Auto-generated catch block
         * Log.i("login", e.getMessage()); e.printStackTrace(); }
         */
    }

    private static Object[] getLoginParam(String email, String password, String nickname) {
        Map<String, String> payload = new HashMap<String, String>();
        payload.put("email_addr", email);
        payload.put("password", password);
        payload.put("nickname", nickname);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("UDID", UDID);
        map.put("KEY", KEY);
        map.put("CONTENT", payload);

        Object[] param = { map };
        return param;
    }

    interface XMLRPCMethodCallback {
        void callFinished(Object result);
    }

    class XMLRPCMethod extends Thread {
        private String method;
        private Object[] params;
        private Handler handler;
        private XMLRPCMethodCallback callBack;

        public XMLRPCMethod(String method, XMLRPCMethodCallback callBack) {
            this.method = method;
            this.callBack = callBack;
            handler = new Handler();
        }

        public void call() {
            call(null);
        }

        public void call(Object[] params) {
            Log.i("login", "Calling host " + HOST);
            this.params = params;
            start();
        }

        @Override
        public void run() {
            try {
                final long t0 = System.currentTimeMillis();
                final Object result = client.callEx(method, params);
                final long t1 = System.currentTimeMillis();
                handler.post(new Runnable() {
                    public void run() {
                        Log.i("login", "XML-RPC call took " + (t1 - t0) + "ms");
                        callBack.callFinished(result);
                    }
                });
            } catch (final XMLRPCFault e) {
                handler.post(new Runnable() {
                    public void run() {
                        Log.i("login", "Fault message: " + e.getFaultString() + "\nFault code: "
                                + e.getFaultCode());
                        Log.d("Test", "error", e);
                    }
                });
            } catch (final XMLRPCException e) {
                handler.post(new Runnable() {
                    public void run() {
                        Throwable couse = e.getCause();
                        if (couse instanceof HttpHostConnectException) {
                            Log.i("login",
                                    "Cannot connect to "
                                            + HOST
                                            + "\nMake sure server.py on your development host is running !!!");
                        } else {
                            Log.i("login", "Error " + e.getMessage());
                        }
                        Log.d("Test", "error", e);
                    }
                });
            }
        }
    }
}
