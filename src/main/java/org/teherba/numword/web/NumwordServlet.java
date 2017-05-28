/*  Spell Numbers in Different Languages
    (write the number words, weekday, month and season names)
    @(#) $Id: NumwordServlet.java 820 2011-11-07 21:59:07Z gfis $
    2017-05-28: javadoc 1.8; was 1 dir level higher
    2016-08-31: subdir web, BasePage
    2016-02-03: JSPs replaced by teherba.numword.view.*View.java; UTF-8 response
    2011-10-17: spellClock
    2009-04-06: renamed from NumberServlet
    2007-02-08: use SpellerFactory
    2005-07-27, Dr. Georg Fischer

    The language specific modules are named according to the
    common 2-letter language/country codes and the
    3-letter language codes of ISO 639-2T (for "terminological" use).
*/
/*
 * Copyright 2006 Dr. Georg Fischer <punctum at punctum dot kom>
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
import  org.teherba.numword.web.IndexPage;
import  org.teherba.numword.web.Messages;
import  org.teherba.numword.web.UniblockPage;
import  org.teherba.numword.web.UnicodePage;
import  org.teherba.numword.web.UnilistPage;
import  org.teherba.common.web.BasePage;
import  org.teherba.common.web.MetaInfPage;
import  java.io.IOException;
import  javax.servlet.RequestDispatcher;
import  javax.servlet.ServletConfig;
import  javax.servlet.ServletContext;
import  javax.servlet.ServletException;
import  javax.servlet.http.HttpServlet;
import  javax.servlet.http.HttpServletRequest;
import  javax.servlet.http.HttpServletResponse;
import  org.apache.log4j.Logger;

/** Spell numbers in some language.
 *  This class is the servlet interface to {@link BaseSpeller},
 *  and ressembles the functionality of the commandline interface in
 *  {@link NumwordCommand}.
 *  @author Dr. Georg Fischer
 */
public class NumwordServlet extends HttpServlet {
    public final static String CVSID = "@(#) $Id: NumwordServlet.java 820 2011-11-07 21:59:07Z gfis $";
    // public final static long serialVersionUID = 19470629004L;

    /** log4j logger (category) */
    private Logger log;

    /** Delivers a subclass of {@link BaseSpeller} */
    private SpellerFactory factory;

    /** common code and messages for auxiliary web pages */
    private BasePage basePage;
    /** name of this application */
    private static final String APP_NAME = "Numword";

    /** Called by the servlet container to indicate to a servlet
     *  that the servlet is being placed into service.
     *  @param config object containing the servlet's configuration and initialization parameters
     *  @throws ServletException for servlet errors
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config); // ???
        log = Logger.getLogger(NumwordServlet.class.getName());
        basePage = new BasePage(APP_NAME);
        Messages.addMessageTexts(basePage);
        factory = new SpellerFactory();
    } // init

    /** Creates the response for a HTTP GET request.
     *  @param request fields from the client input form
     *  @param response data to be sent back the user's browser
     *  @throws IOException for IO errors
     */
    public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException {
        generateResponse(request, response);
    } // doGet

    /** Creates the response for a HTTP POST request.
     *  @param request fields from the client input form
     *  @param response data to be sent back the user's browser
     *  @throws IOException for IO errors
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        generateResponse(request, response);
    } // doPost

    /** Creates the response for a HTTP GET or POST request.
     *  @param request fields from the client input form
     *  @param response data to be sent back the user's browser
     *  @throws IOException for IO errors
     */
    public void generateResponse(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {
            String language = "en";
            String view     = BasePage.getInputField(request, "view"    , "index");
            String function = BasePage.getInputField(request, "function", "c");
            String lang3    = BasePage.getInputField(request, "lang3"   , "deu");
            String digits   = BasePage.getInputField(request, "digits"  , "1981");

            if (false) {
            } else if (view.equals("index")) { // main input form
                //                  =====
                if (false) {
                } else if (function.equals      ("c")
                       ||  function.equals      ("C")    // check for alphabetic "digits" input field
                       ||  function.equals      ("d")
                       ||  function.equals      ("g")
                       ||  function.startsWith  ("h")
                       ||  function.equals      ("m")
                       ||  function.equals      ("m3")
                       ||  function.equals      ("p")
                       ||  function.equals      ("s")
                       ||  function.equals      ("w")
                       ||  function.equals      ("w2")
                       ) { // known function code
                    BaseSpeller speller = factory.getSpeller(lang3);
                    if (speller == null) { // invalid target lang3
                        basePage.writeMessage(request, response, language, new String[] { "401", "lang3 code", lang3 });
                    } else {
                       (new IndexPage()).forward(request, response, basePage, language, function, lang3, digits);
                    }
                } else { // invalid function 
                    basePage.writeMessage(request, response, language, new String[] { "401", "function", function });
                }

            } else if (view.equals("uniblock")) {
                //                  ========
                if (digits.length() > 2) {
                    basePage.writeMessage(request, response, language, new String[] { "401", "length", String.valueOf(digits.length()) });
                } else {
                    (new UniblockPage()).forward(request, response, basePage, language);
                }

            } else if (view.equals("unicode")) {
                //                  =======
                if (digits.length() > 4) {
                    basePage.writeMessage(request, response, language, new String[] { "401", "length", String.valueOf(digits.length()) });
                } else {
                    (new UnicodePage()).forward(request, response, basePage, language, digits);
                }

            } else if (view.equals("unilist")) {
                //                  =======
                (new UnilistPage()).forward(request, response, basePage, language);

            } else if (view.equals("manifest")
                    || view.equals("license" )
                    || view.equals("notice"  )
                    ) { //          ========
                (new MetaInfPage    ()).showMetaInf (request, response, basePage, language, view, this);
                
            } // switch view
        } catch (Exception exc) {
            response.getWriter().write(exc.getMessage());
            log.error(exc.getMessage(), exc);
        }
    } // generateResponse

} // NumwordServlet
