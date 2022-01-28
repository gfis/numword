/*  UnilistPage.java - show a list of Unicode blocks
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
package org.teherba.numword.web;
import  org.teherba.common.web.BasePage;
import  java.io.PrintWriter;
import  java.util.HashMap;
import  java.util.regex.Pattern;
import  javax.servlet.http.HttpServletRequest;
import  javax.servlet.http.HttpServletResponse;
import  org.apache.logging.log4j.Logger;
import  org.apache.logging.log4j.LogManager;

/** This class shows the list of avaliable blocks
 *  of Unicode characters &lt;= u+ffff.
 *  The code is extracted from the former <em>unilist.jsp</em>.
 *  @author Dr. Georg Fischer
 */
public class UnilistPage {
    public final static String CVSID = "@(#) $Id: 058b6a55bb7a7383cb32ef795569872161b7e1bf $";
    public final static long serialVersionUID = 19470629;

    /** log4j logger (category) */
    private Logger log;

    /** No-argument constructor
     */
    public UnilistPage() {
        log = LogManager.getLogger(UnilistPage.class.getName());
    } // constructor()

    /** Processes an http GET request
     *  @param request request with header fields
     *  @param response response with writer
     *  @param basePage reference to common methods and error messages
     *  @param language 2-letter code en, de etc.
     */
    public void forward(HttpServletRequest request, HttpServletResponse response
            , BasePage basePage
            , String language
             ) {
        try {
            PrintWriter out = basePage.writeHeader(request, response, language);
            out.write("<title>" + basePage.getAppName() + " Unilist</title>\n");
            out.write("</head>\n<body>\n");

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
            basePage.writeTrailer(language, "back,quest");
        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
        } finally {
        }
    } // forward

} // UnilistPage
