plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    id("com.github.liushuixiaoxia.check16k")
}

android {
    namespace = "cn.mycommons.lynx_android"
    compileSdk = 36

    defaultConfig {
        applicationId = "cn.mycommons.lynx_android"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    sourceSets {

        getByName("main") {
            assets {
                srcDirs("src/main/assets", "../lynx-module/dist/")
            }
        }
    }
}

val lynxVersion = "3.3.0"

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // lynx sdk
    // lynx dependencies
    implementation("org.lynxsdk.lynx:lynx:$lynxVersion")
    implementation("org.lynxsdk.lynx:lynx-jssdk:$lynxVersion")
    implementation("org.lynxsdk.lynx:lynx-trace:$lynxVersion")
    implementation("org.lynxsdk.lynx:primjs:2.13.2")

    // integrating image-service
    implementation("org.lynxsdk.lynx:lynx-service-image:$lynxVersion")

    // image-service dependencies, if not added, images cannot be loaded; if the host APP needs to use other image libraries, you can customize the image-service and remove this dependency
    implementation("com.facebook.fresco:fresco:2.3.0")
    implementation("com.facebook.fresco:animated-gif:2.3.0")
    implementation("com.facebook.fresco:animated-webp:2.3.0")
    implementation("com.facebook.fresco:webpsupport:2.3.0")
    implementation("com.facebook.fresco:animated-base:2.3.0")

    // integrating log-service
    implementation("org.lynxsdk.lynx:lynx-service-log:$lynxVersion")

    // integrating http-service
    implementation("org.lynxsdk.lynx:lynx-service-http:$lynxVersion")

    debugImplementation("org.lynxsdk.lynx:lynx-devtool:$lynxVersion")
    debugImplementation("org.lynxsdk.lynx:lynx-service-devtool:$lynxVersion")

    implementation("com.squareup.okhttp3:okhttp:4.9.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

check16k {
    enable.set(true) // default is true
    // ignoreError.set(false) // default is true
}