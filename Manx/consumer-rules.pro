-keepattributes !SourceFile
-dontshrink

-keep class e2.C5{static<methods>;}
-keep class a3.W{static<methods>;}
-keep class q7.B5{static<methods>;}
-keep class a3.a0
-keep class b3.U5
-keep class j3.B8
-keep class c8.I5{static<methods>;}
-keep class x2.P1{static<methods>;} #H5 so
-keep class j3.A0
-keep class k5.H0


-keep public class com.tradplus.** { *; }
-keep class com.tradplus.ads.** { *; }

-keep class com.bytedance.sdk.** { *; }

#appsflyer start
# keep init adpost
-keep class com.appsflyer.** { *; }
-keep class kotlin.jvm.internal.** { *; }
-keep public class com.android.installreferrer.** { *; }
#appsflyer end
