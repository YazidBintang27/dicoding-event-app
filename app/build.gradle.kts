plugins {
   alias(libs.plugins.android.application)
   alias(libs.plugins.kotlin.android)
   id("com.google.devtools.ksp")
   id("com.google.dagger.hilt.android")
   id ("androidx.navigation.safeargs")
}

android {
   namespace = "com.latihan.dicodingevent"
   compileSdk = 34

   buildFeatures {
      viewBinding = true
   }

   defaultConfig {
      applicationId = "com.latihan.dicodingevent"
      minSdk = 24
      targetSdk = 34
      versionCode = 1
      versionName = "1.0"

      testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
   buildFeatures {
      viewBinding = true
   }
}

dependencies {

   implementation(libs.androidx.core.ktx)
   implementation(libs.androidx.appcompat)
   implementation(libs.material)
   implementation(libs.androidx.constraintlayout)
   implementation(libs.androidx.lifecycle.livedata.ktx)
   implementation(libs.androidx.lifecycle.viewmodel.ktx)
   implementation(libs.androidx.navigation.fragment.ktx)
   implementation(libs.androidx.navigation.ui.ktx)
   testImplementation(libs.junit)
   androidTestImplementation(libs.androidx.junit)
   androidTestImplementation(libs.androidx.espresso.core)

   //Dagger Hilt
   //noinspection GradleDependency
   implementation(libs.hilt.android)
   ksp(libs.dagger.compiler) // Dagger compiler
   ksp(libs.hilt.compiler)   // Hilt compiler

   // Retrofit
   implementation(libs.retrofit)
   implementation(libs.converter.gson)

   // Coroutines
   //noinspection GradleDependency
   implementation (libs.kotlinx.coroutines.android)

   // Glide
   //noinspection GradleDependency,GradleDependency
   implementation(libs.glide)
   annotationProcessor(libs.compiler)

   // Lifecycle
   implementation(libs.androidx.lifecycle.viewmodel.ktx.v286)
   implementation(libs.androidx.lifecycle.livedata.ktx.v286)
   implementation(libs.androidx.lifecycle.runtime.ktx)

   // DataStore
   implementation(libs.androidx.datastore.preferences)

   // Coroutines
   implementation (libs.kotlinx.coroutines.android)

   //Room Database
   implementation(libs.androidx.room.runtime)
   ksp(libs.androidx.room.compiler)
   implementation(libs.androidx.room.ktx)

}