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

-dontwarn javax.xml.stream.**

-keep public class org.simpleframework.** { *; }
-keep class org.simpleframework.xml.** { *; }
-keep class org.simpleframework.xml.core.** { *; }
-keep class org.simpleframework.xml.util.** { *; }

-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response

-keepattributes Element, ElementList, Root

-keepclassmembers class * {
    @org.simpleframework.xml.* *;
}
