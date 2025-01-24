plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlinx-serialization")
}

android {
    namespace = "com.example.dependence"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    //UI预览
    debugApi(libs.androidx.ui.tooling)
    debugApi(libs.androidx.ui.test.manifest)

    //基础库
    api(libs.androidx.core.ktx)
    api(libs.androidx.lifecycle.runtime.ktx)
    api(libs.androidx.activity.compose)
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.ui)
    api(libs.androidx.ui.graphics)
    api(libs.androidx.ui.tooling.preview)
    api(libs.androidx.material3)

    //region 常用依赖

    //必须依赖
    api ("androidx.lifecycle:lifecycle-runtime-compose:2.8.7")
    api ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    api ("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")

    //约束布局
    api ("androidx.constraintlayout:constraintlayout-compose:1.1.0")

    //修改状态栏
    api ("com.google.accompanist:accompanist-systemuicontroller:0.27.0")

    //lottie
    api ("com.airbnb.android:lottie-compose:5.2.0")

    ////kotlin json解析
    api ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")

    //可以activity和fragment中使用 by viewModels() 声明ViewModel
    api ("androidx.activity:activity-ktx:1.10.0")
    api ("androidx.fragment:fragment-ktx:1.8.5")

    // 权限请求框架：https://github.com/getActivity/XXPermissions
    api ("com.github.getActivity:XXPermissions:18.68")
    //endregion

    //region 郑君库
    api(libs.sd.compose.core)
    api(libs.sd.compose.systemui)
    api(libs.sd.compose.button)
    api(libs.sd.compose.input)
    api(libs.sd.compose.refresh)
    api(libs.sd.compose.paging)
    api(libs.sd.compose.text)

    api(libs.sd.coroutine)
    api(libs.sd.loader)
    api(libs.sd.retry)
    api(libs.sd.datastore)
    api(libs.sd.service)
    api(libs.sd.exception)
    api(libs.sd.ctx)
    api(libs.sd.list)
    api(libs.sd.event)
    api(libs.sd.xlog)
    api(libs.sd.encrypt)
    api(libs.sd.utilsKtx)
    //endregion
}