package supersports.com.libs.cheker;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.CoderMalfunctionError;
import java.util.ArrayList;

import supersports.com.libs.annotation.Failure;
import supersports.com.libs.annotation.Success;

/**
 * Created by rocky on 2019/3/22.
 */

public class PermissionChecker {


    public static Context mContext;

    public static int requestCode;

    public static void inject(Context context) {
        mContext = context;
    }

    /**
     * 标志位
     */
    enum EntType {
        SUCCESS, FAILURE
    }

    //动态申请权限
    public static void request(String[] permissions, int code) {
        requestCode = code;
        String[] deniPermissions = getDeniedPermissions(mContext, permissions);

        if (deniPermissions != null && deniPermissions.length == 0) {
            invoke(code, EntType.SUCCESS);
        } else {
            requestPermissions(deniPermissions, code);
        }


    }

    private static void invoke(int code, EntType type) {
        Method[] methods = mContext.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (type == EntType.SUCCESS) {
                if (method.isAnnotationPresent(Success.class)) {
                    try {
                        method.invoke(mContext, code);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            } else if (type == EntType.FAILURE) {
                if (method.isAnnotationPresent(Failure.class)) {
                    try {
                        method.invoke(mContext, code);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    /**
     * 动态申请权限
     *
     * @param permissions
     * @param code
     */
    public static void requestPermissions(String[] permissions, int code) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions((Activity) mContext, permissions, code);
        }
    }


    /**
     * 返回缺失的权限
     *
     * @param context
     * @param permissions
     * @return 返回缺少的权限，null 意味着没有缺少权限
     */
    public static String[] getDeniedPermissions(Context context, String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> deniedPermissionList = new ArrayList<>();
            for (String permission : permissions) {
                if (context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    deniedPermissionList.add(permission);
                }
            }
            int size = deniedPermissionList.size();
            if (size > 0) {
                return deniedPermissionList.toArray(new String[deniedPermissionList.size()]);
            }
        }
        return null;
    }


    /**
     * 用于权限申请后的回调
     *
     * @param grantResults
     */
    public static void onRequestPermissionsResult(int code, @NonNull int[] grantResults) {

        if(requestCode!= code){
            return;
        }
        boolean isAllGranted = true;
        for (int grant : grantResults) {
            if (grant == PackageManager.PERMISSION_DENIED) {
                isAllGranted = false;
                break;
            }
        }
        if (isAllGranted) {
            invoke(requestCode, EntType.SUCCESS);
        } else {
            invoke(requestCode, EntType.FAILURE);
        }


    }


}
