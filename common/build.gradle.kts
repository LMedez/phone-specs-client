plugins {
    id(ModulePlugins.ANDROID_LIBRARY)
    kotlin("android")
    id(ModulePlugins.PARCELIZE)

}

android {
}

dependencies {
    implementation(Deps.AndroidX.Room.COMMON)
    implementation(Deps.Koin.ANDROID)
    implementation(Deps.Google.Gson.GSON)

    api(TestDeps.JUnit.JUNIT)
    api(TestDeps.Truth.TRUTH)
}