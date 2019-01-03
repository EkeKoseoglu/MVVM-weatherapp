import com.android.tools.r8.kotlin.KotlinClass
import org.jetbrains.kotlin.config.KotlinCompilerVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {

    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin ("kapt")
    id("androidx.navigation.safeargs")
}

android {
    compileSdkVersion(28)
    defaultConfig {
        applicationId = "tr.com.homesorft.wetherapp"
        minSdkVersion (21)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    buildToolsVersion("28.0.3")
    dataBinding.isEnabled = true
}

dependencies {
    val kotlinVersion = KotlinCompilerVersion.VERSION
    val navigationVersion = "1.0.0-alpha09"
    val roomVersion = "2.1.0-alpha03"
    val retrofitVersion = "2.3.0"
    val coroutineVersion = "1.0.1"
    val koinVersion = "1.0.2"
    val glideVersion = "4.8.0"
    val lifecycleVersion = "2.0.0"

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("androidx.appcompat:appcompat:1.1.0-alpha01")
    implementation("androidx.constraintlayout:constraintlayout:2.0.0-alpha3")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-extensions:$lifecycleVersion")
    kapt("androidx.lifecycle:lifecycle-compiler:$lifecycleVersion")

    //Test helpers for LiveData
    testImplementation("androidx.arch.core:core-testing:$lifecycleVersion")

    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test:runner:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.1.1")

    //Material Design
    implementation("com.google.android.material:material:1.1.0-alpha02")

    // Navigation
    implementation("android.arch.navigation:navigation-fragment-ktx:$navigationVersion")
    implementation("android.arch.navigation:navigation-ui-ktx:$navigationVersion")

    // Room components
    implementation("androidx.room:room-runtime:$roomVersion")
    androidTestImplementation("androidx.room:room-testing:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")

    //Gson
    implementation("com.google.code.gson:gson:2.8.5")

    //Threetenabp
    implementation("com.jakewharton.threetenabp:threetenabp:1.1.1")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")

    // Kotlin & Coroutines
    implementation(kotlin("stdlib-jdk8", kotlinVersion))
    implementation(kotlin("reflect", kotlinVersion))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")

    // Koin for Android
    implementation( "org.koin:koin-android:$koinVersion")
    implementation("org.koin:koin-androidx-scope:$koinVersion")
    implementation("org.koin:koin-androidx-viewmodel:$koinVersion")
    testImplementation("org.koin:koin-test:$koinVersion")

    //Annotations
    implementation("javax.annotation:javax.annotation-api:1.3.2")

    //Glide components
    implementation( "com.github.bumptech.glide:glide:$glideVersion")
    implementation("com.github.bumptech.glide:okhttp3-integration:$glideVersion")
    kapt("com.github.bumptech.glide:compiler:$glideVersion")

    //Location
    implementation("com.google.android.gms:play-services-location:16.0.0")

    // Preference
    implementation("androidx.preference:preference:1.1.0-alpha02")


    //Timber
    implementation("com.jakewharton.timber:timber:4.7.1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.2")
}

tasks.withType<KotlinCompile>{
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

