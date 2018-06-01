/*  UnicodePage.java - show a single Unicode character
 *  @(#) $Id: 058b6a55bb7a7383cb32ef795569872161b7e1bf $
 *  2017-05-28: javadoc 1.8
 *  2016-01-18, Georg Fischer: copied from MessageView.java
 */
/*
 * Copyright 2016 Dr. Georg Fischer <punctum at punctum dot kom>
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
package org.teherba.numword.web;
import  org.teherba.common.web.BasePage;
import  java.io.PrintWriter;
import  java.util.HashMap;
import  java.util.regex.Pattern;
import  javax.servlet.http.HttpServletRequest;
import  javax.servlet.http.HttpServletResponse;
import  org.apache.log4j.Logger;

/** This class shows a single
 *  Unicode character &lt;= u+ffff.
 *  The code is extracted from the former <em>unicode.jsp</em>.
 *  @author Dr. Georg Fischer
 */
public class UnicodePage {
    public final static String CVSID = "@(#) $Id: 058b6a55bb7a7383cb32ef795569872161b7e1bf $";
    public final static long serialVersionUID = 19470629;

    /** log4j logger (category) */
    private Logger log;

    /** No-argument constructor
     */
    public UnicodePage() {
        log = Logger.getLogger(UnicodePage.class.getName());
    } // constructor()

    /** Gets up to 5 lines of descriptive information about the character.
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
        int unicode = 0;
        try {
            unicode = Integer.parseInt(code, 16);
        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
        } // try
        String[] result = new String[3];
        int iline = 0;
        result[iline ++] = "U+" + code + " " + Character.getName(unicode);
        result[iline ++] = "Decimal &#" + String.format("%d", unicode) + ";";
        result[iline ++] = code;
        return result;
    } // describe

    /** Processes an http GET request
     *  @param request request with header fields
     *  @param response response with writer
     *  @param basePage reference to common methods and error messages
     *  @param language 2-letter code en, de etc.
     *  @param code hexadecimal representation of the Unicode character
     */
    public void forward(HttpServletRequest request, HttpServletResponse response
            , BasePage basePage
            , String language
            , String code
            ) {
        try {
	        int unicode = 0;
	        try {
    	        unicode = Integer.parseInt(code, 16);
        	} catch (Exception exc) {
        	} // try
            PrintWriter out = basePage.writeHeader(request, response, language);
            out.write("<title>" + basePage.getAppName() + " Unicode</title>\n");
            out.write("</head>\n<body>\n");

            String[] text = describe(code);
            int nline = text.length;
            int iline = 0;
            if (iline < nline && text[iline] != null) {
                out.write("<h2>");
                out.print(    text[iline] );
                out.write("</h2>\n");
            }
            iline ++;
            if (iline < nline && text[iline] != null) {
                out.write("<p><span>");
                out.print(  text[iline].replaceAll("&", "&amp") );
                out.write("</p>\n");
            }
            iline ++;
            if (iline < nline && text[iline] != null) {
                out.write("<p>");
                out.write("<a href=\"servlet?view=unicode&digits=" + String.format("%04x", unicode - 1) + "\">&lt;-</a>\n");
                out.write("<table><tr><td><span style=\"font-size:240pt\">&#x");
                out.write(text[iline]);
                out.write(";</span></td></tr></table>\n");
                out.write("<a href=\"servlet?view=unicode&digits=" + String.format("%04x", unicode + 1) + "\">-&gt;</a>\n		");
                out.write("</p>\n");
            }
            iline ++;
            if (iline < nline && text[iline] != null) {
                out.write("<p><span>");
                out.print(  text[iline] );
                out.write("</p>\n");
            }
            iline ++;
            if (iline < nline && text[iline] != null) {
                out.write("<p><span>");
                out.print(  text[iline] );
                out.write("</p>\n");
            }
            basePage.writeTrailer(language, "back,quest");
        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
        } finally {
        }
    } // forward

} // UnicodePage
