package common.utils;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertiesUtil {

    private PropertiesUtil() {

    }

    private static final String CUCUMBER_PROPERTIES = "cucumber";
    public static final String SANDBOX = "sandbox";
    public static final String VARIABLE_PROFILE = "cucumber.profiles.active";

    public static String getProperty(final String key) {
        String value = null;

        try {
            ResourceBundle bundle = null;
            String profile = System.getProperty(VARIABLE_PROFILE);
            System.out.println(VARIABLE_PROFILE);
            if (profile != null) {
                bundle = ResourceBundle.getBundle(CUCUMBER_PROPERTIES + "-" + profile);
                System.out.println(CUCUMBER_PROPERTIES);
            } else {
                bundle = ResourceBundle.getBundle(CUCUMBER_PROPERTIES);
                System.out.println(CUCUMBER_PROPERTIES);
            }
            value = resolveValueWithEnvVars(bundle.getString(key));
        } catch (Exception e) {
        }
        return value;
    }

    private static String resolveValueWithEnvVars(String value) {
        if (null == value) {
            return null;
        }

        Pattern p = Pattern.compile("\\$\\{(\\w+)\\}|\\$(\\w+)");
        Matcher m = p.matcher(value);
        String envVarValue = value;
        while (m.find()){
            String envVarName = null == m.group(1) ? m.group(2) : m.group(1);
            envVarValue = System.getenv(envVarName);
            if (envVarValue == null || envVarValue.isEmpty()){
                envVarValue = System.getProperty(envVarName);
            }
        }
        return envVarValue;
    }

}
