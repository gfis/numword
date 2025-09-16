/*  IndexPage.java - show meta data
 *  @(#) $Id: 8d5e24e355fe592d9ce9ed6d7ba4f4a174079723 $
 *  2016-08-31: with BasePage
 *  2012-07-01: subpackage view
 *  2012-02-11, Georg Fischer: copied from metaInf.jsp
 */
/*
 * Copyright 2012 Dr. Georg Fischer <dr dot georg dot fischer at gmail dot ...>
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
import  org.teherba.numword.BaseSpeller;
import  org.teherba.numword.NumwordCommand;
import  org.teherba.numword.SpellerFactory;
import  org.teherba.common.web.BasePage;
import  java.io.BufferedReader;
import  java.io.InputStreamReader;
import  java.io.PrintWriter;
import  java.util.Iterator;
import  javax.servlet.http.HttpServletRequest;
import  javax.servlet.http.HttpServletResponse;
import  org.apache.logging.log4j.Logger;
import  org.apache.logging.log4j.LogManager;

/** This class prints the main page with the input form of the application.
 *  @author Dr. Georg Fischer
 */
public class IndexPage {
    public final static String CVSID = "@(#) $Id: 8d5e24e355fe592d9ce9ed6d7ba4f4a174079723 $";
    public final static long serialVersionUID = 19470629;

    /** log4j logger (category) */
    private Logger log;

    /** No-argument constructor
     */
    public IndexPage() {
        log = LogManager.getLogger(IndexPage.class.getName());
    } // constructor()

    /** Output the main dialog page for RaMath
     *  @param request request with header fields
     *  @param response response with writer
     *  @param basePage refrence to common methods and error messages
     *  @param language 2-letter code en, de etc.
     *  @param function category to be spelled
     *  @param lang3 3-letter code for natural target language
     *  @param digits digits or number word to be parsed
     */
    public void forward(HttpServletRequest request, HttpServletResponse response
            , BasePage basePage
            , String language
            , String function
            , String lang3
            , String digits
            ) {
        try {
            PrintWriter out = basePage.writeHeader(request, response, language);
            out.write("<title>" + basePage.getAppName() + " Main Page</title>\n");
            out.write("</head>\n<body>\n");

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
            SpellerFactory factory = new SpellerFactory();
            out.write("<!-- function " + function + ", lang3 "+ lang3 + ", digits=\"" + digits + "-->\n");
            out.write("<h2>Number Words</h2>\n");
            out.write("<form action=\"servlet\" method=\"post\">\n");
            out.write("    <input type = \"hidden\" name=\"view\" value=\"index\" />\n");
            out.write("    <table cellpadding=\"8\">\n");
            out.write("      <tr><td width=\"100\">Language<br />\n");
            out.write("          <select name=\"lang3\" size=\"" + factory.getCount() + "\">\n");
            Iterator<BaseSpeller> iter = factory.getIterator();
            while (iter.hasNext()) {
                BaseSpeller speller = (BaseSpeller) iter.next();
                String iso = speller.getFirstIso639();
                out.write("<option value=\"" + iso + "\""
                        + (iso.equals(lang3) ? " selected" : "")
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
            out.write("          <br />\n");
            basePage.writeAuxiliaryLinks(language, "main");
            out.write("          <p  />If the <em>Digits/Word</em> field is left empty, all months, weekdays etc. are shown,\n");
            out.write("          and for \"Digits as Word\" a list of representative test numbers is spelled.\n");
            out.write("        </td>\n");
            out.write("        <td width=\"100\" valign=\"top\">Digits / Word<br />\n");
            out.write("          <input name=\"digits\" size=\"60\" value=\"" +  digits + "\" /><br />");
            out.write("          <input type=\"submit\" value=\"Submit\" />\n");

            // this is the result output code in the 3rd (rightmost) cell
            NumwordCommand command = new NumwordCommand();
            String commandLine = "-l " + lang3 + " -" + function + " " + digits;
            command.setMode(command.MODE_HTML_EM);
            if (digits.equals("") && "ch".indexOf(function.substring(0,1)) >= 0) {
                command.setMode(command.MODE_HTML);
            }
            out.write(command.process(commandLine));
            out.write("        </td>\n");
            out.write("      </tr>\n");
            out.write("    </table>\n");
            out.write("</form>\n");
            basePage.writeTrailer(language, "quest");
        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
        }
    } // forward

} // IndexPage
