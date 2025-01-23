package com.example.core.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.example.core.ktx.logActivityStack
import java.util.Stack

object ActivityStackUtils {

    var count = 0
    var isBackground: Boolean = false

    /** activity栈 */
    val activityStack: Stack<Activity> = Stack()

    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                logActivityStack ("${activity.javaClass.name} onCreate")

                activityStack.add(activity)
            }

            override fun onActivityStarted(activity: Activity) {
                logActivityStack ("${activity.javaClass.name} onStart")

                if (isBackground) {
                    foreground()
                }
                count++
            }

            override fun onActivityResumed(activity: Activity) {
                logActivityStack ("${activity.javaClass.name} OnResume")
            }

            override fun onActivityPaused(activity: Activity) {
                logActivityStack ("${activity.javaClass.name} onPause")
            }

            override fun onActivityStopped(activity: Activity) {
                logActivityStack ("${activity.javaClass.name} onStop")

                count--
                if (count == 0) {
                    background()
                }
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
                logActivityStack ("${activity.javaClass.name} onDestroy")
                activityStack.remove(activity)
            }

        })
    }

    fun background() {
        logActivityStack ("ActivityStackUtils>>>background")

        isBackground = true

    }

    fun foreground() {
        logActivityStack ("ActivityStackUtils>>>foreground")

        isBackground = false

    }
}