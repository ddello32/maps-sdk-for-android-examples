# Proguard rules for Maps SDK
# Lombok annotations are irrelevant at run time anyway
-dontwarn afu.org.checkerframework.**
-dontwarn org.checkerframework.**
-dontwarn android.databinding.**
-keep class android.databinding.** { *; }


# I needed Guava rules to be able to compile and run the APK
-dontwarn java.lang.ClassValue
-dontwarn com.google.common.base.**
-dontwarn com.google.errorprone.annotations.**
-dontwarn com.google.j2objc.annotations.**
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Worakround for proguard not handling some lambdas that well
# see: https://issuetracker.google.com/issues/112297269
# https://sourceforge.net/p/proguard/bugs/734/ (Should be fixed when proguard 6.1.0 comes out)
-keepnames class com.google.common.base.Function { *; }
-keepnames class com.google.common.base.Predicate { *; }

# Rules for TomTom
-dontwarn lombok.NonNull
-dontwarn lombok.Generated
-dontwarn org.joda.convert.FromString
-dontwarn org.joda.convert.ToString
# Map deps
-keep public class com.tomtom.online.sdk.map.model.**
-keep interface com.tomtom.online.sdk.map.*Extension {*;}
-keep class com.tomtom.online.sdk.map.MapStateDatabase { *; }
-keep class com.tomtom.online.sdk.map.MapStateDao { *; }
-keep class * implements com.tomtom.online.sdk.map.MapStateDao { *; }
-keep class * implements com.tomtom.online.sdk.map.MapStateDatabase { *; }
-keep class * implements com.tomtom.online.sdk.map.RoomDatabase { *; }
-keepnames public class com.tomtom.online.sdk.map.** {
    public <methods>;
}
-keepclassmembers public class com.tomtom.online.sdk.map.** {
    public static final *;
}
-keep public class com.tomtom.online.sdk.map.** {
    public static *** builder();
    public <methods>;
}

#Static image deps
-keep public class com.tomtom.online.sdk.staticimage.** {
    public <methods>;
}

-keepclassmembers public class com.tomtom.online.sdk.staticimage.** {
    public static final *;
}

-keep public class com.tomtom.online.sdk.staticimage.** {
    public static *** builder();
    public <methods>;
}