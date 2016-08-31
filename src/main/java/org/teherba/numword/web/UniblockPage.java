/*  UniblockPage.java - show a block of Unicode characters
 *  @(#) $Id: 058b6a55bb7a7383cb32ef795569872161b7e1bf $
 *  2016-02-14: error in table column counting
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
import  javax.servlet.http.HttpSession;
import  org.apache.log4j.Logger;

/** This class shows a blokc of Unicode characters
 *  with their codes and glyphs.
 *  The code is extracted from the former <em>uniblock.jsp</em>.
 *  @author Dr. Georg Fischer
 */
public class UniblockPage {
    public final static String CVSID = "@(#) $Id: 058b6a55bb7a7383cb32ef795569872161b7e1bf $";
    public final static long serialVersionUID = 19470629;

    /** log4j logger (category) */
    private Logger log;

    /** No-argument constructor
     */
    public UniblockPage() {
        log = Logger.getLogger(UniblockPage.class.getName());
    } // constructor()

    /** Processes an http GET request
     *  @param request request with header fields
     *  @param response response with writer
     *  @param basePage refrence to common methods and error messages
     *  @param language 2-letter code en, de etc.
     */
    public void forward(HttpServletRequest request, HttpServletResponse response
            , BasePage basePage
            , String language
             ) {
        try {
            HttpSession session = request.getSession();
            PrintWriter out = basePage.writeHeader(request, response, language);
            out.write("<title>" + basePage.getAppName() + " Uniblock</title>\n");
            out.write("</head>\n<body>\n");

            Object field = session.getAttribute("digits");
            String digits = (field != null) ? (String) field : "01";
            int highByte = 0;
            try {
                highByte = Integer.parseInt(digits, 16);
            } catch (Exception exc) {
            }
            int pageNo = highByte * 256;
            out.write("<form action=\"servlet\" method=\"post\">\n");
            out.write("<span class=\"large\">Unicode Block U+");
            out.print(Integer.toHexString(0x100 + highByte).substring(1).toUpperCase());
            out.write("xx</span>&#xa0; &#xa0;\n");
            out.write("<input type = \"hidden\" name=\"view\" value=\"uniblock\" />\n");
            out.write("Upper byte: <input name=\"digits\" maxsize=\"2\" size=\"2\" value=\"");
            out.print(digits);
            out.write("\" />\n");
            out.write("<input type=\"submit\" value=\"Lookup\" />\n");
            out.write("<a href=\"servlet?view=uniblock&digits=" + Integer.toHexString(highByte - 1) + "\">&lt;</a>&#xa0; &#xa0;");
            out.write("<a href=\"servlet?view=uniblock&digits=" + Integer.toHexString(highByte + 1) + "\">&gt;</a>&#10;");
            out.write("<span class=\"large\">\n");

            String name = "(undefined)";
            try {
                name = Character.UnicodeBlock.of(pageNo).toString();
            } catch (Exception exc) {
            }
            out.write(name);
            out.write("\t\t</span>\n\t</form>\n");
            out.write("<a href=\"servlet?view=unilist\">List</a> of all Unicode blocks &lt; 0x10000\n");

            int maxCol = 16;
            int maxRow = 16;
            out.write("<table>\n");
            out.write("<tr><th>&nbsp;</th>\n"); // upper left cell = empty
            int col = 0; 
            while (col < maxCol) { // headings
                out.write("<th align=\"center\">" + Integer.toHexString(col) + "</th>");
                col ++;
            } // while col - headings
            out.write("</tr>\n");
            
            int code = 0;
            int row = 0;
            while (row < maxRow) {
                out.write("<tr>\n");
                out.write("<td><strong>&nbsp;" 
                        + Integer.toHexString(0x10000 + pageNo + code).substring(1, 4) + "x" 
                        + "&nbsp;</strong></td>\n");
                col = 0; 
                while (col < maxCol) {
                    int unicode = pageNo + code;
                    String unistr = Integer.toHexString(0x10000 + unicode).substring(1);
                    out.write("<td title=\"" + Character.getName(unicode) + "\">\n");
                    out.write("<span class=\"code\">");
                    out.write("<a href=\"servlet?view=unicode&digits=" + unistr + "\">" + unistr + "</a></span>");
                    out.write("<br /><span class=\"glyph\">&#x" + unistr + ";</span></td>\n");
                    code ++;
                    col ++;
                } // while col
                row ++;
            } // while row
            out.write("</table>\n");
            
            basePage.writeTrailer(language, "back,quest");
        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
        } finally {
        }
    } // forward

} // UniblockPage
