-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

-keepattributes RuntimeVisibleAnnotations,AnnotationDefault

-dontwarn okhttp3.internal.platform.**
-dontwarn org.conscrypt.**
-dontwarn org.bouncycastle.**
-dontwarn org.openjsse.**
-dontwarn org.xmlpull.v1.**
-dontwarn org.kxml2.io.**
-dontwarn android.content.res.**

-keep class org.xmlpull.** { *; }
-keepclassmembers class org.xmlpull.** { *; }
