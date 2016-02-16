/*  IndexView.java - show meta data
 *  @(#) $Id: 8d5e24e355fe592d9ce9ed6d7ba4f4a174079723 $
 *  2012-07-01: subpackage view
 *  2012-02-11, Georg Fischer: copied from metaInf.jsp
 */
/*
 * Copyright 2012 Dr. Georg Fischer <punctum at punctum dot kom>
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
import  org.teherba.numword.BaseSpeller;
import  org.teherba.numword.NumwordCommand;
import  org.teherba.numword.SpellerFactory;
import  java.io.BufferedReader;
import  java.io.InputStreamReader;
import  java.io.PrintWriter;
import  java.util.Iterator;
import  javax.servlet.http.HttpServletRequest;
import  javax.servlet.http.HttpServletResponse;
import  javax.servlet.http.HttpSession;
import  org.apache.log4j.Logger;

/** This class prints the metadata for the application:
 *  <ul>
 *  <li>License,</li>
 *  <li>JAR Manifest, and</li>
 *  <li>Notices for included software packages</li>
 *  </ul>
 *  @author Dr. Georg Fischer
 */
public class IndexView {
    public final static String CVSID = "@(#) $Id: 8d5e24e355fe592d9ce9ed6d7ba4f4a174079723 $";
    public final static long serialVersionUID = 19470629;

    /** log4j logger (category) */
    private Logger log;

    /** No-argument constructor
     */
    public IndexView() {
        log = Logger.getLogger(IndexView.class.getName());
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
            out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n");
            out.write("    \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n");
            out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\n");
            out.write("<head>\n");
            out.write("    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n");
            out.write("    <title>Number Words</title>\n");
            out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"stylesheet.css\">\n");
            out.write("</head>\n");

            String CVSID = "@(#) $Id: index.jsp 820 2011-11-07 21:59:07Z gfis $";
            String[] optFunction = new String []
                    /*  0 */ { "c"
                    /*  1 */ , "C"
                    /*  2 */ , "m"
                    /*  3 */ , "m3"
                    /*  4 */ , "w"
                    /*  5 */ , "w2"
                    /*  6 */ , "s"
                    /*  7 */ , "g"
                    /*  8 */ , "h0"
                    /*  9 */ , "h1"
                    /* 10 */ , "h2"
                    /* 11 */ , "h3"
                    /* 12 */ , "d"
                    /* 13 */ , "p"
                    } ;
            String[] enFunction = new String []
                    /*  0 */ { "Digits as Word"
                    /*  1 */ , "Word as Digits"
                    /*  2 */ , "Month"
                    /*  3 */ , "Month's Abbreviation"
                    /*  4 */ , "Weekday"
                    /*  5 */ , "Weekday's Abbreviation"
                    /*  6 */ , "Season"
                    /*  7 */ , "Greeting"
                    /*  8 */ , "Time of Day - offical"
                    /*  9 */ , "Time - variant 1"
                    /* 10 */ , "Time - variant 2"
                    /* 11 */ , "Time - variant 3"
                    /* 12 */ , "Cardinal Direction"
                    /* 13 */ , "Planet"
                    } ;
            SpellerFactory factory;
            Object obj = session.getAttribute("factory");
            if (obj == null) {
                factory = new SpellerFactory();
                session.setAttribute("factory"  , factory );
            } else {
                factory = (SpellerFactory) obj;
            }
            // factory = new SpellerFactory();
            Object
            field = session.getAttribute("function");
            String function = (field != null) ? (String) field : "c";
            field = session.getAttribute("language");
            String language = (field != null && field != "") ? (String) field : "de";
            field = session.getAttribute("digits");
            String digits   = (field != null) ? (String) field : "1947";
            out.write("<body>\n");
            out.write("<!-- function " + function + ", language "+ language + ", digits=\"" + digits + "-->\n");
            out.write("<h2>Number Words</h2>\n");
            out.write("<form action=\"servlet\" method=\"post\">\n");
            out.write("    <input type = \"hidden\" name=\"view\" value=\"index\" />\n");
            out.write("    <table cellpadding=\"8\">\n");
            out.write("      <tr><td width=\"100\">Language<br />\n");
            out.write("          <select name=\"language\" size=\"" + factory.getCount() + "\">\n");
            Iterator/*<1.5*/<BaseSpeller>/*1.5>*/ iter = factory.getIterator();
            while (iter.hasNext()) {
                BaseSpeller speller = (BaseSpeller) iter.next();
                String iso = speller.getFirstIso639();
                out.write("<option value=\"" + iso + "\""
                        + (iso.equals(language) ? " selected" : "")
                        + ">" + speller.getDescription() + "</option>\n");
            } // while iter
            out.write("          </select>\n");
            out.write("        </td>\n");
            out.write("        <td width=\"100\">Spell<br />\n");
            out.write("          <select name=\"function\" size=\"" + optFunction.length + "\">\n");
            int ind = 0;
            while (ind < optFunction.length) {
                out.write("<option value=\"" + optFunction[ind] + "\""
                        + (optFunction[ind].equals(function) ? " selected" : "")
                        + ">" + enFunction[ind] + "</option>\n");
                ind ++;
            } // while ind
            out.write("          </select>\n");
            out.write("          <br />&nbsp;\n");
            out.write("          <br /><a href=\"docs/documentation.html\">Documentation</a>,\n");
            out.write("                <a href=\"docs/api/index.html\">API</a>\n");
            out.write("          <br /><a href=\"docs/developer.html\">Developer Hints, </a>\n");
            out.write("                <a href=\"docs/bugs.html\">Bugs</a>\n");
            out.write("          <br /><a href=\"servlet?view=uniblock&digits=01\">Unicode Tables</a>\n");
            out.write("          <br /><a href=\"servlet?view=manifest\">Manifest</a>,\n");
            out.write("                <a href=\"servlet?view=license\" >License</a>\n");
            out.write("          <br /><a href=\"servlet?view=notice\"  >References</a>\n");
            out.write("          <p  />If the <em>Digits/Word</em> field is left empty, all months, weekdays etc. are shown,\n");
            out.write("          and for \"Digits as Word\" a list of representative test numbers is spelled.\n");
            out.write("        </td>\n");
            out.write("        <td width=\"100\" valign=\"top\">Digits / Word<br />\n");
            out.write("          <input name=\"digits\" size=\"60\" value=\"" +  digits + "\" /><br />");
            out.write("          <input type=\"submit\" value=\"Submit\" />\n");
            // this is the result output code in the 3rd (rightmost) cell
            NumwordCommand command = new NumwordCommand();
            String commandLine = "-l " + language + " -" + function + " " + digits;
            command.setMode(command.MODE_HTML_EM);
            if (digits.equals("") && "ch".indexOf(function.substring(0,1)) >= 0) {
                command.setMode(command.MODE_HTML);
            }
            out.write(command.process(commandLine));
            out.write("\n");
            out.write("        </td>\n");
        //  out.write("        <td width=\"*\">&nbsp;</td>\n");
            out.write("      </tr>\n");
            out.write("    </table>\n");
            out.write("</form>\n");
            out.write("<p><font size=\"-1\">\n");
            out.write("Questions, remarks to: <a href=\"mailto:punctum@punctum.com\">Dr. Georg Fischer</a>\n");
            out.write("   </font>\n");
            out.write("</p>\n");
            out.write("</body></html>\n");
        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
        } finally {
        }
    } // forward

} // IndexView
