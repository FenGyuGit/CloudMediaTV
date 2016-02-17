/**
 * @author TangHao
 *  2015-10-22
 *  use : judging the inputType for provide is legal
 */
package com.geeya.wifitv.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LegalityJudgeUtil {

	public static boolean isLegalUrl(String matcherString){
		String compileString = "^(http|www|ftp|)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$";
		return isMatch(compileString, matcherString);
	} 
	
	private static boolean isMatch(String compileString, String matcherString){
		Pattern pattern = Pattern.compile(compileString);
        Matcher  matcher = pattern.matcher(matcherString);
        return matcher.matches();
	}
}

