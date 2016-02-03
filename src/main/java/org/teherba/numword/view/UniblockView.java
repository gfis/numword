/*  UniblockView.java - show a block of Unicode characters
 *  @(#) $Id: 058b6a55bb7a7383cb32ef795569872161b7e1bf $
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
package org.teherba.numword.view;
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
public class UniblockView {
    public final static String CVSID = "@(#) $Id: 058b6a55bb7a7383cb32ef795569872161b7e1bf $";
    public final static long serialVersionUID = 19470629;

    /** log4j logger (category) */
    private Logger log;

    /** No-argument constructor
     */
    public UniblockView() {
        log = Logger.getLogger(UniblockView.class.getName());
    } // constructor()

    /** Processes an http GET request
     *  @param request request with header fields
     *  @param response response with writer
     *  @throws IOException
     */
    public void forward(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            PrintWriter out = response.getWriter();
            out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n");
            out.write("    \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n");
            out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\n");
            out.write("<head>\n");
            out.write("    <title>Unicode Block</title>\n");
            out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"stylesheet.css\">\n");
            out.write("</head>\n");

            String CVSID = "@(#) $Id: uniblock.jsp 819 2011-11-01 14:06:06Z gfis $";
            Object field = session.getAttribute("digits");
            String digits = (field != null) ? (String) field : "01";
            int highByte = 0;
            try {
                highByte = Integer.parseInt(digits, 16);
            } catch (Exception exc) {
            }
            int pageNo = highByte * 256;
            out.write("<body>\n");
            out.write("    <form action=\"servlet\" method=\"post\">\n");
            out.write("\t    <span class=\"large\">Unicode Block U+");
            out.print( Integer.toHexString(0x100 + highByte).substring(1).toUpperCase() );
            out.write("xx</span>\n");
            out.write("        &#xa0; &#xa0;\n");
            out.write("        <input type = \"hidden\" name=\"view\" value=\"uniblock\" />\n");
            out.write("        Upper byte: <input name=\"digits\" maxsize=\"2\" size=\"2\" value=\"");
            out.print( digits );
            out.write("\" />\n");
            out.write("        <input type=\"submit\" value=\"Lookup\" />\n");
            out.write("<a href=\"servlet?view=uniblock&digits=" + Integer.toHexString(highByte - 1) + "\">&lt;</a>&#xa0; &#xa0;");
            out.write("<a href=\"servlet?view=uniblock&digits=" + Integer.toHexString(highByte + 1) + "\">&gt;</a>&#10;");
            out.write("\t\t<span class=\"large\">\n");

            String name = "(undefined)";
            try {
                name = Character.UnicodeBlock.of(pageNo).toString();
            } catch (Exception exc) {
            }
            out.write(name);
            out.write("\t\t</span>\n");
            out.write("\t</form>\n");
            out.write("    <table>\n");
            out.write("        <tr>\n");
            out.write("            <th>\n");
            out.write("                &nbsp;\n");
            out.write("            </th>\n");

            int maxCol = 32;
            int maxRow =  8;
            for (int column = 0; column < maxCol; column ++) {
                out.write("            <th>\n");
                out.write (Integer.toHexString(0x100 + column).substring(1));
                out.write("            </th>\n");
            } // for column
            out.write("        </tr>\n");
            int code = 0;
            for (int row = 0; row < maxRow; row ++) {
                out.write("        <tr>\n");
                out.write("            <td><strong>&nbsp;\n");
                out.print( Integer.toHexString(0x100 + code).substring(1) );
                out.write("                &nbsp;</strong>\n");
                out.write("            </td>\n");
                for (int column = 0; column < maxCol; column ++) {
                    code ++;
                    int unicode = pageNo + code;
                    String unistr = Integer.toHexString(0x10000 + unicode).substring(1);
                    out.write("        <td title=\"" + Character.getName(unicode) + "\">\n");
                    out.write("<span class=\"code\">");
                    out.write("<a href=\"servlet?view=unicode&digits=" + unistr + "\">" + unistr + "</a></span>");
                    out.write("<br /><span class=\"glyph\">&#x" + unistr + ";</span>");
                    out.write("        </td>\n");
                } // for column
            } // for row
            out.write("    </table>\n");
            out.write("    Back to the <strong><a href=\"servlet?view=index\">Numword</a></strong> input form;\n");
            out.write("    &#xa0; &#xa0;\n");
            out.write("    <a href=\"servlet?view=unilist\">List</a> of all Unicode blocks &lt; 0x10000\n");
            out.write("</body>\n");
            out.write("</html>\n");
        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
        } finally {
        }
    } // forward

} // UniblockView
