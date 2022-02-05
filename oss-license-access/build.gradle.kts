plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    compileSdk = 31
    buildToolsVersion = "31.0.0"

    defaultConfig {
        minSdk = 14
        targetSdk = 31

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
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
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}

project.afterEvaluate {
    this.publishing.publications {
        create<MavenPublication>("release") {
            groupId = "cz.lastaapps.osslicenseaccess"
            artifactId = "oss-license-access"
            version = "1.0"

            from(components["release"])
            //artifact(tasks.getByName("androidReleaseSourcesJar"))

            pom {
                name.set("OSS License Access")
                description.set("API to access OSS generated licenses")
                url.set("http://github.com/Lastaapps/OssLicenseAccess")
                /*properties.set(
                    mapOf(
                        "myProp" to "value",
                        "prop.with.dots" to "anotherValue"
                    )
                )*/
                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://github.com/Lastaapps/OssLicenseAccess/blob/main/LICENSE")
                    }
                }
                developers {
                    developer {
                        id.set("Lasta Apps")
                        name.set("Petr Laštovička")
                        email.set("lastaappsdev@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/Lastaapps/OssLicenseAccess.git")
                    developerConnection.set("scm:git:ssh://github.com/Lastaapps/OssLicenseAccess.git")
                    url.set("http://github.com/Lastaapps/OssLicenseAccess/")
                }
            }
        }
    }
}

//tasks.register("androidReleaseSourcesJar", Jar::class) {
//    archiveClassifier.set("sources")
//    from(kotlin.sourceSets["main"].kotlin.srcDirs)
//}