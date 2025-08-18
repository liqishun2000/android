package com.example.core.utils

import android.app.Activity
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.OnPermissionPageCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.sc.qrbar.ktx.activeResume
import com.sc.qrbar.ktx.activeResumeWithException
import kotlinx.coroutines.suspendCancellableCoroutine

object PermissionUtils {

    suspend fun requestWriteStorage(activity: Activity) {
        val permission = Permission.WRITE_EXTERNAL_STORAGE
        if (XXPermissions.isGranted(activity, permission)) return

        return requestCommon(activity, permission)
    }

    suspend fun requestCamera(activity: Activity) {
        val permission = Permission.CAMERA
        if (XXPermissions.isGranted(activity, permission)) return

        return requestCommon(activity, permission)
    }

    suspend fun requestNotification(activity: Activity) {
        val permission = Permission.POST_NOTIFICATIONS
        if (XXPermissions.isGranted(activity, permission)) return

        return requestCommon(activity, permission)
    }

    private suspend fun requestCommon(activity: Activity, permission: String) {
        return suspendCancellableCoroutine { continuation ->
            XXPermissions.with(activity)
                .permission(permission)
                .request(object : OnPermissionCallback {
                    override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                        continuation.activeResume(Unit)
                    }

                    override fun onDenied(
                        permissions: MutableList<String>,
                        doNotAskAgain: Boolean
                    ) {
                        if (doNotAskAgain) {
                            XXPermissions.startPermissionActivity(
                                activity,
                                permissions,
                                object : OnPermissionPageCallback {
                                    override fun onGranted() {
                                        continuation.activeResume(Unit)
                                    }

                                    override fun onDenied() {
                                        continuation.activeResumeWithException()
                                    }

                                })
                        }else{
                            continuation.activeResumeWithException()
                        }
                    }
                })

        }
    }
}