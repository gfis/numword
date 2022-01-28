/*  Call Unix command "unicode -x" to get a description
    @(#) $Id: Unicode.java 852 2012-01-06 08:07:08Z gfis $
    2017-05-28: javadoc 1.8
    2016-01-18: comment the sources
    2012-08-05: reader.close()
    2011-11-01: Georg Fischer
*/
/*
 * Copyright 2011 Dr. Georg Fischer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.teherba.numword;
import  java.io.BufferedReader;
import  java.io.InputStreamReader;
import  java.lang.Process;
import  java.lang.Runtime;
import  org.apache.logging.log4j.Logger;
import  org.apache.logging.log4j.LogManager;

/** Gets the description of a Unicode character from the Unix command <em>unicode -x</em>.
 *  @author Dr. Georg Fischer
 */

public class Unicode {
    public final static String CVSID = "@(#) $Id: Unicode.java 852 2012-01-06 08:07:08Z gfis $";

    /** log4j logger (category) */
    public Logger log;

    /** Empty constructor
     */
    public Unicode () {
        log = LogManager.getLogger(Unicode.class.getName());
    } // constructor

    /** Gets 5 lines of output of the Unix command <em>unicode -x C off code</em>,
     *  whose author is Radovan Garabik, garabic@kassiopeia.juls.savba.sk.
     *  This the deprecated version using an external command line utility.
     *  @param code hexadecimal character code
     *  @return for example
     *  <pre>
U+25B6 BLACK RIGHT-POINTING TRIANGLE
UTF-8: e2 96 b6  UTF-16BE: 25b6  Decimal: &amp;#9654;

Category: So (Symbol, Other)
Bidi: ON (Other Neutrals)
     *  </pre>
     *  The data can also be found in <a href="http://www.unicode.org/Public/UNIDATA/UnicodeData.txt">
http://www.unicode.org/Public/UNIDATA/UnicodeData.txt</a> in the form
     *  <pre>
0020;SPACE;Zs;0;WS;;;;;N;;;;;
0021;EXCLAMATION MARK;Po;0;ON;;;;;N;;;;;
0022;QUOTATION MARK;Po;0;ON;;;;;N;;;;;
0023;NUMBER SIGN;Po;0;ET;;;;;N;;;;;
0024;DOLLAR SIGN;Sc;0;ET;;;;;N;;;;;
0025;PERCENT SIGN;Po;0;ET;;;;;N;;;;;
0026;AMPERSAND;Po;0;ON;;;;;N;;;;;
     *  </pre>
     */
    public String[] describe_99(String code) {
        Runtime runtime = Runtime.getRuntime();
        Process process = null;
        String[] result = new String[5];
        try {
            process = runtime.exec("unicode --hexadecimal --colour=off --io=UTF-8 " + code);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
            String line = null;
            int iline = 0;
            while (iline < 5 && (line = reader.readLine()) != null) {
                result[iline ++] = line;
            } // while iline
            reader.close();
        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
        } // try
        return result;
    } // describe_99

    /** Gets up to 5 lines of descriptive information about the character,
     *  as a replacement for the deprecated method {@link #describe_99}.
     *  @param code hexadecimal character code
     *  @return sample output (only the first line is currently implemented):
     *  <pre>
U+25B6 BLACK RIGHT-POINTING TRIANGLE
UTF-8: e2 96 b6  UTF-16BE: 25b6  Decimal: &amp;#9654;

Category: So (Symbol, Other)
Bidi: ON (Other Neutrals)
     *  </pre>
     */
    public String[] describe(String code) {
        String[] result = new String[1];
        int iline = 0;
        int unicode = 0;
        try {
            unicode = Integer.parseInt(code, 16);
        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
        } // try
        result[iline ++] = "U+" + code + " " + Character.getName(unicode);
        return result;
    } // describe

    /** Test program: like <em>unicode -x</em>
     *  @param args (integer) ASCII code number of the character to be described
     */
    public static void main(String args[]) {
        String code = "41";
        if (args.length > 0) {
            code = args[0];
        }
        String text[] = (new Unicode()).describe(code);
        int itext = 0;
        while (itext < text.length) {
            System.out.println(text[itext]);
            itext ++;
        } // while itext
    } // main

} // Unicode
