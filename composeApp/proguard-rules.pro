-keepclasseswithmembers public class MainKt {
    public static void main(java.lang.String[]);
}

-dontwarn kotlinx.coroutines.debug.*

-keepclassmembers class * extends java.lang.Enum {
    <fields>;
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class kotlin.** { *; }
-keep class kotlinx.coroutines.** { *; }
-keep class org.jetbrains.skia.** { *; }
-keep class org.jetbrains.skiko.** { *; }
-keep class com.darkrockstudios.** { *; }
-keep class kotlinx.coroutines.** { *; }
-keep class org.apache.poi.** { *; }
-keep class org.openxmlformats.** { *; }

-dontwarn org.jetbrains.kotlinx.**
-dontwarn waffle.**
-dontwarn org.openxmlformats.schemas.**
-dontwarn org.joda.time.**
-dontwarn org.apache.**

-dontobfuscate
-ignorewarnings