plugins {
    id(ModulePlugins.ANDROID_LIBRARY)
    kotlin("android")
}

android {

}

dependencies {
    implementation(project(":domain"))
    implementation(project(":common"))
    implementation(Deps.Koin.ANDROID)
    implementation(Deps.Kotlinx.Coroutines.CORE)
    implementation(Deps.AndroidX.Lifecycle.VIEWMODEL_KTX)
    implementation(Deps.AndroidX.Lifecycle.LIVEDATA_KTX)
    implementation(Deps.Google.PlayCore.PLAY_SERVICES)
    implementation(Deps.AndroidX.Navigation.FRAGMENT_KTX)


    testImplementation(TestDeps.JUnit.JUNIT)
    testImplementation(TestDeps.AndroidX.Arch.CORE_TESTING)
    testImplementation(TestDeps.Mockito.CORE)

}