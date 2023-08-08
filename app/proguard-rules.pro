-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

-keepattributes RuntimeVisibleAnnotations,AnnotationDefault

-keep class org.xmlpull.** { *; }
-keepclassmembers class org.xmlpull.** { *; }

-dontwarn javax.xml.stream.**

-keep public class org.simpleframework.** { *; }
-keep class org.simpleframework.xml.** { *; }

-keepattributes Element, ElementList, Root

-keepclassmembers class * {
    @org.simpleframework.xml.* *;
}

-keepclassmembers class * {
    @org.simpleframework.xml.* <fields>;
    @org.simpleframework.xml.* <init>(...);
}

-keep class app.suhasdissa.memerize.backend.model.redditvideo.**{ *; }
