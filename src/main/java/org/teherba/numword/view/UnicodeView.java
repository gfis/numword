/*  UnicodeView.java - show a single Unicode character
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

/** This class shows a single
 *  Unicode character &lt;= u+ffff.
 *  The code is extracted from the former <em>unicode.jsp</em>.
 *  @author Dr. Georg Fischer
 */
public class UnicodeView {
    public final static String CVSID = "@(#) $Id: 058b6a55bb7a7383cb32ef795569872161b7e1bf $";
    public final static long serialVersionUID = 19470629;

    /** log4j logger (category) */
    private Logger log;

    /** No-argument constructor
     */
    public UnicodeView() {
        log = Logger.getLogger(UnicodeView.class.getName());
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
            out.write("    <title>Unicode</title>\n");
            out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"stylesheet.css\">\n");
            out.write("</head>\n");

            String CVSID = "@(#) $Id: unicode.jsp 819 2011-11-01 14:06:06Z gfis $";
            Object
            field = session.getAttribute("text");
            String[] text = new String[] {""};
            if (field != null) {
                text = (String[]) field;
            }
            out.write("<body>\n");
            int nline = text.length;
            int iline = 0;
            if (iline < nline && text[iline] != null) {
                out.write("<h2>");
                out.print(    text[iline] );
                out.write("</h2>\n");
            }
            iline ++;
            if (iline < nline && text[iline] != null) {
                out.write("<br /><span>");
                out.print(  text[iline].replaceAll("&", "&amp") );
                out.write("</span>\n");
            }
            iline ++;
            if (iline < nline && text[iline] != null) {
                out.write("<br /><span style=\"font-size:240pt\">&#x");
                out.print( Integer.toHexString(0x10000 + text[iline].charAt(0)).substring(1) );
                out.write(";</span>\n");
            }
            iline ++;
            if (iline < nline && text[iline] != null) {
                out.write("<br /><span>");
                out.print(  text[iline] );
                out.write("</span>\n");
            }
            iline ++;
            if (iline < nline && text[iline] != null) {
                out.write("<br /><span>");
                out.print(  text[iline] );
                out.write("</span>\n");
            }
            out.write("</body>\n");
            out.write("</html>\n");
        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
        } finally {
        }
    } // forward

} // UnicodeView
