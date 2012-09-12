/*  Spell Numbers in Different Languages (write the number words)
    @(#) $Id: NumwordService.java 521 2010-07-26 07:06:10Z gfis $
    Copyright (c) 2005 Dr. Georg Fischer <punctum@punctum.com>
    2009-11-24: spellGreeting
	2009-04-06: renamed from NumberService
    2007-02-08: use SpellerFactory
    2005-07-27, Georg Fischer

    Service to be called via SOAP, offering the functions of NumwordCommand
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

package org.teherba.numword;
import  org.teherba.numword.BaseSpeller;
import  org.teherba.numword.NumwordCommand;
import  org.teherba.numword.SpellerFactory;
import  org.apache.log4j.Logger;

/** Spell numbers in some language
 *  This class is the SOAP service interface to {@link BaseSpeller},
 *  and ressembles the functionality of the commandline interface
 *  in {@link NumwordCommand}.
 *  @author Dr. Georg Fischer
 */
public class NumwordService {
    public final static String CVSID = "@(#) $Id: NumwordService.java 521 2010-07-26 07:06:10Z gfis $";
    /** log4j logger (category) */
    private Logger log;
    /** Delivers <em>Transformer</em>s */
    private SpellerFactory factory;

    /** Returns the results of an activation of {@link NumwordCommand}
     *  to a SOAP client.
     *  @param language ISO 639 language code, e.g. "de"
     *  @param function a code for the desired function
     *  @return number word or digit sequence
     */
    public String convert(String language, String function, String digits)  {
        String response = "";
        log = Logger.getLogger(NumwordService.class.getName());
        try {
            NumwordCommand NumwordCommand = new NumwordCommand();
            BaseSpeller speller = (new SpellerFactory()).getSpeller(language);
            String newPage = "index";
            if (speller == null               ) {
                response = "003 - invalid language code"; // invalid language code
                newPage = "message";
            } else if (function.equals("spell")         ) {
                // check for proper digit sequence
                function = "c";
            } else if ( function.equals("month")         ) {
                function = "m";
            } else if ( function.equals("month,3")       ) {
                function = "m3";
            } else if ( function.equals("parse")         ) {
                // check for alphabetic "digits" input field
                function = "p";
            } else if ( function.equals("season")        ) {
                function = "s";
            } else if ( function.equals("greeting")      ) {
                function = "g";
            } else if ( function.equals("weekday")       ) {
                function = "w";
            } else if ( function.equals("weekday,2")     ) {
                function = "w2";
            } else { // invalid function
                response = "001 - invalid function";
                newPage = "message";
            }
            if (newPage.equals("index")) {
                response = NumwordCommand.process("-l " + language + " -" + function + " " + digits);
            } else {
                response = "error " + response;
            }
        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
        }
        return response;
    } // convert
    
} // NumwordService
