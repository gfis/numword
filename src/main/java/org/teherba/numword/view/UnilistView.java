/*  UnilistView.java - show a list of Unicode blocks
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

/** This class shows the list of avaliable blocks
 *  of Unicode characters &lt;= u+ffff.
 *  The code is extracted from the former <em>unilist.jsp</em>.
 *  @author Dr. Georg Fischer
 */
public class UnilistView {
    public final static String CVSID = "@(#) $Id: 058b6a55bb7a7383cb32ef795569872161b7e1bf $";
    public final static long serialVersionUID = 19470629;

    /** log4j logger (category) */
    private Logger log;

    /** No-argument constructor
     */
    public UnilistView() {
        log = Logger.getLogger(UnilistView.class.getName());
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
            out.write("    <title>Unicode List</title>\n");
            out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"stylesheet.css\">\n");
            out.write("</head>\n");
            String CVSID = "@(#) $Id: unilist.jsp 819 2011-11-01 14:06:06Z gfis $";
            out.write("<body>\n");
            out.write("    <span class=\"large\">Unicode Blocks</span>\n");
            out.write("    <table>\n");
            out.write("        <tr><th>Start</th><th>Description</th></tr>\n");
            int code = 0;
            while (code < 0x10000) {
                out.write("        <tr>\n");
                out.write("            <td><strong>&nbsp;\n");
                out.write("<a href=\"servlet?view=uniblock&digits="
                        + Integer.toHexString(code >> 8) + "\">"
                        + Integer.toHexString(0x10000 + code).substring(1, 3)
                        + "xx</a>\n");
                out.write("                &nbsp;</strong>\n");
                out.write("            </td>\n");
                out.write("            <td>\n");
                String name = "(undefined)";
                try {
                    name = Character.UnicodeBlock.of(code).toString();
                } catch (Exception exc) {
                }
                out.write(name);
                out.write("            </td>\n");
                out.write("        </tr>\n");
                code += 0x100; // next block
            } // while code
            out.write("    </table>\n");
            out.write("    Back to the <strong><a href=\"servlet?view=index\">Numword</a></strong> input form;\n");
            out.write("</body>\n");
            out.write("</html>\n");
        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
        } finally {
        }
    } // forward

} // UnilistView
