# Keep Room related classes
-keepdeclarationmembers class * extends androidx.room.RoomDatabase {
    <init>(...);
}
-keep class * extends androidx.room.RoomDatabase
-keep class com.ehterami.vellum.data.** { *; }

# Keep Kotlin Serialization
-keepattributes *Annotation*, EnclosingMethod, Signature
-keepclassmembers class ** {
    @kotlinx.serialization.Serializable *;
}
-keepclassmembers class com.ehterami.vellum.ui.Destination** { *; }
