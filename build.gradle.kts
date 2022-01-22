buildscript {
    dependencies {
        classpath("com.google.android.gms:oss-licenses-plugin:0.10.4")
    }
}
plugins {
    id("com.android.application") version "7.1.0-rc01" apply false
    id("com.android.library") version "7.1.0-rc01" apply false
    id("org.jetbrains.kotlin.android") version "1.6.10" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}