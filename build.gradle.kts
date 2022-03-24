import com.android.build.gradle.LibraryExtension
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryPlugin

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(BuildPlugins.ANDROID)
        classpath(BuildPlugins.GMS)
        classpath(BuildPlugins.SAFE_ARGS)
        classpath(kotlin(module = BuildPlugins.Kotlin.ID, version = BuildPlugins.Kotlin.VERSION))
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    }
}

val projectJvmTarget = JavaVersion.VERSION_1_8

subprojects {
    project.plugins.configureAppAndModules(subProject = project)
}

fun PluginContainer.configureAppAndModules(subProject: Project) = apply {
    whenPluginAdded {
        when (this) {
            is AppPlugin -> {
                subProject.extensions
                    .getByType<AppExtension>()
                    .applyAppCommons()
            }
            is LibraryPlugin -> {
                subProject.extensions
                    .getByType<LibraryExtension>()
                    .applyLibraryCommons()
            }
        }
    }
}

fun Project.disableVariants() {
    val extension = project.extensions
        .getByName("androidComponents") as LibraryAndroidComponentsExtension
    extension.beforeVariants(extension.selector().withBuildType("debug")) {
        it.enabled = false
    }
}

fun AppExtension.applyAppCommons() = apply {

    defaultConfig {
        applicationId = Android.Client.APP_ID
        versionCode = Android.Client.VERSION_CODE
        versionName = Android.Client.VERSION_NAME

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf("room.schemaLocation" to "$projectDir/schemas")
            }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }

        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
        }
    }

    applyBaseCommons()
}

fun LibraryExtension.applyLibraryCommons() = apply {
    applyBaseCommons()
}

fun BaseExtension.applyBaseCommons() = apply {

    // Create a variable called keystorePropertiesFile, and initialize it to your
    // keystore.properties file, in the rootProject folder.
    val keystorePropertiesFile = rootProject.file("keystore.properties")

    // Initialize a new Properties() object called keystoreProperties.
    val keystoreProperties = java.util.Properties()

    // Load your keystore.properties file into the keystoreProperties object.
    keystoreProperties.load(java.io.FileInputStream(keystorePropertiesFile))

    signingConfigs {
        create("release") {
            keyAlias = keystoreProperties.getProperty("keyAlias")
            keyPassword = keystoreProperties.getProperty("keyPassword")
            storeFile = file(keystoreProperties.getProperty("storeFile"))
            storePassword = keystoreProperties.getProperty("password")
        }
    }

    compileSdkVersion(Android.Sdk.COMPILE)
    defaultConfig.apply {
        minSdk = Android.Sdk.MIN
        targetSdk = Android.Sdk.TARGET
    }

    compileOptions.apply {
        sourceCompatibility = projectJvmTarget
        targetCompatibility = projectJvmTarget
    }

    flavorDimensions("type")

    productFlavors {

        create("dev") {
            dimension = "type"
            resourceConfigurations.addAll(listOf("en", "xxhdpi"))
        }

        create("prod") {
            dimension = "type"
            //signingConfig = signingConfigs.getByName("release")
        }
    }
}
