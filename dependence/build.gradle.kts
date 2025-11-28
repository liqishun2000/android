plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    id("kotlinx-serialization")

    id("com.google.devtools.ksp")
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

    // Compose Icons
    api("androidx.compose.material:material-icons-extended:1.7.8")

    //lottie
    api ("com.airbnb.android:lottie-compose:5.2.0")

    //kotlin json解析
    api ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
    //kotlin反射
    api ("org.jetbrains.kotlin:kotlin-reflect:1.9.23")

    //可以activity和fragment中使用 by viewModels() 声明ViewModel
    api ("androidx.activity:activity-ktx:1.10.0")
    api ("androidx.fragment:fragment-ktx:1.8.5")

    // 权限请求框架：https://github.com/getActivity/XXPermissions
    api ("com.github.getActivity:XXPermissions:18.68")

    //xml 流布局 app:flexWrap="wrap" 可以设置自动换行或者不换行
    //          app:justifyContent="space_between" 设置排列方式
    api ("com.google.android.flexbox:flexbox:3.0.0")
    api ("androidx.coordinatorlayout:coordinatorlayout:1.3.0")
    api ("com.google.android.material:material:1.1.0")

    //blankj
    api ("com.blankj:utilcodex:1.31.1")

    //room
    api("androidx.room:room-runtime:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    api("androidx.room:room-ktx:2.6.1")
    //endregion

    //coil
    api("io.coil-kt.coil3:coil-compose:3.1.0") //compose
    api("io.coil-kt.coil3:coil-network-okhttp:3.1.0") //加载网络图片
    api("io.coil-kt.coil3:coil-svg:3.1.0") //加载svg

    //retrofit
    api("com.squareup.retrofit2:retrofit:3.0.0")
    api("com.squareup.retrofit2:converter-gson:3.0.0")
    api("com.squareup.okhttp3:logging-interceptor:4.9.1")

    //datastore
    api("androidx.datastore:datastore-preferences:1.1.1")

    //exif
    implementation(libs.androidx.exifinterface)

    //协程
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")
    //kotlin 版本
//    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.1.10")

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