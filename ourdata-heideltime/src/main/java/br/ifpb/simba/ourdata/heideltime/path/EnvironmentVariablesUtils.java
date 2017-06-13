package br.ifpb.simba.ourdata.heideltime.path;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 13/06/2017, 02:34:08
 */
public class EnvironmentVariablesUtils {
    
    /**
     * Process a path with environment variables and return the this path.
     * 
     * @param input
     * @return A Path processed without environment variables.
     */
    public static String source(String input) {

        if (null == input) {
            return null;
        }

        // match ${ENV_VAR_NAME} or $ENV_VAR_NAME
        Pattern p = Pattern.compile("\\$\\{(\\w+)\\}|\\$(\\w+)");
        Matcher m = p.matcher(input); // get a matcher object
        StringBuffer sb = new StringBuffer();

        while (m.find()) {
            String envVarName = null == m.group(1) ? m.group(2) : m.group(1);
            String envVarValue = System.getenv(envVarName);

            if (envVarValue != null
                    && envVarValue != ""
                    && p.matcher(envVarValue).find()) {
                envVarValue = source(envVarValue);
            }

            m.appendReplacement(sb, null == envVarValue ? "" : envVarValue);
        }

        m.appendTail(sb);
        return sb.toString();
    }
}
