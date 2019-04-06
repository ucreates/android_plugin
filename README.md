# Plugin(Android)
The project is plugin for [Android](https://www.android.com).

## Description
The project is develoed by Android Studio(3.3.2).

FirebaseCore(16.0.0),FireabaseMessaging(17.1.0).

Android plugin supported Android6.0 Later.

## Usage
***Notes on use for Android***

1.Add to your build.gradle(appproject).

```
buildscript {
    repositories {
        ・
        ・
        ・
        maven { url "https://github.com/layerhq/releases-gradle/raw/master/releases" }
        ・
        ・
        ・
    }
    dependencies {
        ・
        ・
        ・
        classpath group: 'com.layer', name: 'git-repo-plugin', version: '1.0.0'
        ・
        ・
        ・
    }
}
```

2.Add to your build.gradle(app).

- Java

```
apply plugin: 'git-repo'
・
・
・
repositories {
    github("ucreates", "android_plugin", "master", "repository/java")
}
・
・
・
dependencies {
    compile 'com.ucreates:plugin:1.0.0' 
}    
```

- Kotlin

```
apply plugin: 'git-repo'
・
・
・
repositories {
    github("ucreates", "android_plugin", "master", "repository/kotlin")
}
・
・
・
dependencies {
    compile 'com.ucreates:plugin:1.0.0' 
}    
```

3.Sync gradle in your Android project.

4.Build and Running Android Plugin on your Android Client App.
