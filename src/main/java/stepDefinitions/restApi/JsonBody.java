package stepDefinitions.restApi;


import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonBody {

    private static String jsonBodyString = null;
    private static LinkedHashMap<String, String> userParameters = new LinkedHashMap<>();


    public static LinkedHashMap<String, String> getUserParameters() {
        return userParameters;
    }

    public static void setUserParameters(LinkedHashMap<String, String> userParameters) {
        JsonBody.userParameters = userParameters;
    }


    public static String getJsonBodyString() {
        return jsonBodyString;
    }

    public static void setJsonBodyString(String jsonBodyString) {
        JsonBody.jsonBodyString = jsonBodyString;
    }


    public static String replaceVariablesValues(String text) throws Throwable {
        String rx = "(\\$\\{[^}]+\\})";

        StringBuffer sb = new StringBuffer();
        Pattern p = Pattern.compile(rx);
        Matcher m = p.matcher(text);

        while (m.find()) {
            String repString = getUserParameters().get(m.group(1));
            if (repString != null)
                m.appendReplacement(sb, repString);
        }
        m.appendTail(sb);
        return sb.toString();
    }


}
