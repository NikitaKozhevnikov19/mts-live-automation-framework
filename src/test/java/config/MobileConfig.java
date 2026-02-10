package config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "classpath:${deviceHost}.properties"
})
public interface MobileConfig extends Config {
    @Key("device")
    String deviceName();

    @Key("version")
    String platformVersion();

    @Key("app")
    String appUrl();

    @Key("remote.url")
    String remoteUrl();

    @Key("appium.automationName")
    @DefaultValue("UiAutomator2")
    String automationName();

    @Key("bs.user")
    String bsUser();

    @Key("bs.key")
    String bsKey();
}
