package net.minecraft.client.particle.chroma.utils;

import java.util.function.*;

public enum LengthUtils
{
    main("main.jar", 0L), 
    authlib("authlib-1.5.21.jar", 64412L), 
    codecjorbis("codecjorbis-20101023.jar", 103871L), 
    codecwav("codecwav-20101023.jar", 5618L), 
    commons_codec("commons-codec-1.9.jar", 263965L), 
    commons_compress("commons-compress-1.8.1.jar", 365552L), 
    commons_io("commons-io-2.4.jar", 185140L), 
    commons_lang("commons-lang3-3.3.2.jar", 412739L), 
    commons_logging("commons-logging-1.1.3.jar", 62050L), 
    gson("gson-2.2.4.jar", 190432L), 
    guava("guava-17.0.jar", 2243036L), 
    httpclient("httpclient-4.3.3.jar", 589512L), 
    httpcore("httpcore-4.3.2.jar", 282269L), 
    icu4j_core_mojang("icu4j-core-mojang-51.2.jar", 1634692L), 
    jinput("jinput-2.0.5.jar", 208338L), 
    jopt_simple("jopt-simple-4.6.jar", 62477L), 
    jutils("jutils-1.0.0.jar", 7508L), 
    librarylwjglopenal("librarylwjglopenal-20100824.jar", 18981L), 
    log4j_api("log4j-api-2.0-beta9.jar", 108161L), 
    log4j_core("log4j-core-2.0-beta9.jar", 681134L), 
    lwjgl_2("lwjgl-2.9.1.jar", 1014790L), 
    lwjgl_util("lwjgl_util-2.9.1.jar", 173909L), 
    netty("netty-all-4.0.15.Final.jar", 1501009L), 
    pircbot("pircbot.jar", 76700L), 
    realms("realms-1.6.1.jar", 298229L), 
    soundsystem("soundsystem-20120107.jar", 65020L), 
    trove4j("trove4j-3.0.3.jar", 2523218L), 
    twitch("twitch-6.5.jar", 55977L), 
    vecmath("vecmath-1.5.2.jar", 318956L), 
    avutil("avutil-ttv-51.dll", 653832L), 
    twitchsdk("twitchsdk.dll", 1384960L), 
    jinput_dx8("jinput-dx8.dll", 61952L), 
    jinput_dx8_6("jinput-dx8_64.dll", 65024L), 
    jinput_raw("jinput-raw.dll", 59392L), 
    jinput_raw_64("jinput-raw_64.dll", 62464L), 
    jinput_wintab("jinput-wintab.dll", 56832L), 
    libmfxsw64("libmfxsw64.dll", 20533656L), 
    libmp3lame("libmp3lame-ttv.dll", 688161L), 
    lwjgl("lwjgl.dll", 298496L), 
    lwjgl64("lwjgl64.dll", 310272L), 
    OpenAL32("OpenAL32.dll", 390144L), 
    OpenAL64("OpenAL64.dll", 382464L), 
    swresample("swresample-ttv-0.dll", 361103L);
    
    public final String string;
    private final long length;
    public static Function<String, LengthUtils> getFile;
    
    private LengthUtils(final String string, final long lenght) {
        this.string = string;
        this.length = lenght;
    }
    
    public long getLength() {
        if (this == LengthUtils.main) {
            try {
                return NetworkUtils.getFileSize("http://nethersoul.altervista.org/launcher/st_18/main.jar");
            }
            catch (Exception ex) {}
        }
        return this.length;
    }
}