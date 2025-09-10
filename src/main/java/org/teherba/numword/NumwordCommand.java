/*  Spell Numbers in Different Languages (write the number words)
    @(#) $Id: NumwordCommand.java 820 2011-11-07 21:59:07Z gfis $
    2025-09-10: ara in ISO list; *CH
    2020-03-10: -c min max  = range of numbers
    2017-05-28: javadoc 1.8
    2016-01-18: Wikipedia links only for HTML
    2012-09-15: WikipediaHelper extracted from SpellerFactory
    2011-10-14: spellClock, spellCompass
    2009-12-04: append additional words on commandline
    2009-11-24: spellGreeting; mode dependant output, log4j
    2007-02-08: use SpellerFactory
    2006-07-27: -p repaired, expanded for months etc.
    2006-01-04: UTF-8 for all; Jpn, Kor, Gre, Chi, Sino, Slavic, Rus, ...
    2005-06-09, Georg Fischer

    The language specific modules are named according to the
    3-letter language codes of ISO 639-2T (for "terminological" use).

    pure ASCII encoding
*/
/*
 * Copyright 2006 Dr. Georg Fischer <dr dot georg dot fischer at gmail dot ...>
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
import  org.teherba.numword.SpellerFactory;
import  org.teherba.numword.WikipediaHelper;
import  java.io.PrintWriter;
import  java.io.StringWriter;
import  java.io.Writer;
import  java.nio.channels.Channels;
import  java.text.DecimalFormat;
import  java.text.NumberFormat;
import  java.util.Iterator;
import  java.util.regex.Matcher;
import  java.util.regex.Pattern;
import  org.apache.logging.log4j.Logger;
import  org.apache.logging.log4j.LogManager;

/** Spells a number (or some other enumerable word) in some language.
 *  This class is the commandline interface to <em>BaseSpeller</em>.
 *  @author Dr. Georg Fischer
 */
final public class NumwordCommand {
    public final static String CVSID = "@(#) $Id: NumwordCommand.java 820 2011-11-07 21:59:07Z gfis $";

    /** log4j logger (category) */
    public Logger log;
    /** Newline string (CR/LF or LF only) */
    private String nl;

    /** code for output format */
    private int mode;
    /** code for HTML output format */
    public static final int MODE_HTML     = 0;
    /** code for emphasized HTML output format */
    public static final int MODE_HTML_EM  = 1;
    /** code for plain text output format */
    public static final int MODE_PLAIN    = 2;
    /** code for tab separated values, for Excel and other spreadsheet processors */
    public static final int MODE_TSV      = 3;
    /** code for XML output format */
    public static final int MODE_XML      = 4;

    /** Sets the output format
     *  @param format code for the output format, corresponding to the value of commandline option "-m"
     */
    public void setMode(int format) {
        mode = format;
    } // setMode

    /** Gets the output format
     *  @return format code for the output format, corresponding to the value of commandline option "-m"
     */
    public int getMode() {
        return mode;
    } // getMode

    /** No-args Constructor
     */
    public NumwordCommand() {
        log = LogManager.getLogger(NumwordCommand.class.getName());
        nl = System.getProperty("line.separator");
        setMode(MODE_PLAIN);
    } // Constructor()

    /** Puts an error message
     *  @param out writer, where to print the message
     *  @param text text for the error message
     */
    public void error(Writer out, String text) {
        try {
            out.write(text + nl);
        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
        } // try
    } // error

    /** Puts an output table row with a number and a word.
     *  @param out writer, where to print the row
     *  @param number number to be shown in the first  column
     *  @param word   word   to be shown in the second column
     */
    public void printRow(Writer out, String number, String word) {
        StringBuffer row = new StringBuffer(128);
        try {
            switch (mode) {
                default:
                case MODE_PLAIN:
                case MODE_TSV:
                    row.append(number);
                    row.append("\t");
                    row.append(word);
                    break;
                case MODE_XML:
                case MODE_HTML:
                    row.append("<tr><td align=\"right\">");
                    row.append(number);
                    row.append("</td><td>");
                    row.append(word);
                    row.append("</td></tr>");
                    break;
                case MODE_HTML_EM:
                    row.append("<tr>");
                    row.append("<td align=\"right\" class=\"large\">");
                    row.append(number);
                    row.append("</td>");
                    row.append("<td class=\"large\">");
                    row.append(word);
                    row.append("</td></tr>");
                    break;
            } // switch mode
            row.append(nl);
            out.write(row.toString());
        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
        } // try
    } // printRow

    /** Puts the start of an output table.
     *  @param out writer, where to print the tag
     *  @param speller for the description
     */
    public void printStartTable(Writer out, BaseSpeller speller) {
        try {
            switch (mode) {
                default:
                case MODE_PLAIN:
                case MODE_TSV:
                    break;
                case MODE_XML:
                case MODE_HTML:
                case MODE_HTML_EM:
                    out.write("<h2>" + speller.getDescription() + "</h2>\n");
                    out.write("<table>" + nl);
                    break;
            } // switch mode
        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
        } // try
    } // printStartTable

    /** Puts the end   of an output table.
     *  @param out writer, where to print the tag
     */
    public void printEndTable(Writer out) {
        try {
            switch (mode) {
                default:
                case MODE_PLAIN:
                case MODE_TSV:
                    break;
                case MODE_XML:
                case MODE_HTML:
                case MODE_HTML_EM:
                    out.write("</table>" + nl);
                    break;
            } // switch mode
        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
        } // try
    } // printEndTable

    /** Convenience overlay method with a single string argument instead
     *  of an array of strings.
     *  @param commandLine all parameters of the commandline in one string
     *  @return output of the call depending on the function: a digit sequence,
     *  a number word, a month name etc.
     */
    public String process(String commandLine) {
        return process(commandLine.split("\\s+"));
    } // process(String)

    /** Pattern for splitting of day times */
    private static Pattern clockPattern = Pattern.compile("(\\d+)\\D+(\\d+)");

    /** Evaluates the arguments of the command line, and processes them.
     *  @param args Arguments; if missing, print the following:
     *  <pre>
     *  usage: java org.teherba.numword.NumwordCommand [-l iso [-c|-d|-g|-h|-m[3]|-s|-w[2]] [number]]
     *         java org.teherba.numword.NumwordCommand -l iso (-f|-t) filename
     *         java org.teherba.numword.NumwordCommand -l iso -p number-word
     *
     *    -f   find and replace number words in text file
     *    -c   print cardianal number word (default)
     *    -d   print compass direction (parameter is degrees, 270 = West)
     *    -g   print greeting corresponding to day's time (6, 12, 18, 24, 0)
     *    -hi  print hour of clock (h0 = official, h1,h2,h3 variants)
     *    -m   print 12 month names
     *    -m3  print 12 months' abbreviations (3 letters)
     *    -p   print 8 planets of the sun
     *    -s   print 4 seasons of the year
     *    -w   print 7 weekdays
     *    -w2  print 7 weekdays' abbreviations (2 letters)
     *
     *    -C   parse number word, return digits
     *    -t   test against file consisting of lines with: digits tab number-word
     *    -l   language code: de, fr, en, tlh ...
     *  </pre>
     *  The digit sequences may contain non-digit characters, which are ignored.
     *  If the number starts with '-', all numbers up to and including
     *  the specified number are spelled.
     *  @return output of the call depending on the function: a digit sequence,
     *  a number word, a month name etc.
     */
    public String process(String args[]) {
        /** internal buffer for the string to be output */
        StringWriter out = new StringWriter(16384);
        SpellerFactory factory = new SpellerFactory();
        WikipediaHelper helper = new WikipediaHelper();
        String word = "";
        try {
            int iarg = 0; // index for command line arguments
            if (iarg >= args.length) { // usage, with known ISO codes and languages
                out.write("Usage:\tjava org.teherba.numword.NumwordCommand [-l iso [-c|-m[3]|-s|-w[2]|-g] [number]]" + nl);
                out.write("      \tjava org.teherba.numword.NumwordCommand -l iso (-f|-t) filename" + nl);
                out.write("      \tjava org.teherba.numword.NumwordCommand -l iso -p number-word" + nl);
                out.write("      \tjava org.teherba.numword.NumwordCommand -l iso [-m[3]|-s|-w[2]] -p [month|weekday|season]" + nl);
                out.write("Options:" + nl);
                out.write("  -f  \tfind and replace number words in text file" + nl);
                out.write("  -c  \tprint cardianal number word (default)" + nl);
                out.write("  -d  \tprint compass direction (parameter is degrees, 270 = West)" + nl);
                out.write("  -g  \tprint greeting corresponding to day's time (6, 12, 18, 24, 0)" + nl);
                out.write("  -hi \tprint hour of clock (h0 = official, h1,h2,h3 variants)" + nl);
                out.write("  -m  \tprint 12 month names" + nl);
                out.write("  -m3 \tprint 12 months' abbreviations (3 letters)" + nl);
                out.write("  -s  \tprint 4 seasons of the year" + nl);
                out.write("  -w  \tprint 7 weekdays" + nl);
                out.write("  -w2 \tprint 7 weekdays' abbreviations (2 letters)" + nl);
                out.write("  -p  \tparse word, return digits or cardinal number" + nl);
                out.write("  -t  \ttest against file consisting of lines with: digits tab number-word" + nl);
                out.write("ISO language codes:" + nl);
                Iterator/*<1.5*/<BaseSpeller>/*1.5>*/ iter = factory.getIterator();
                while (iter.hasNext()) {
                    BaseSpeller speller = (BaseSpeller) iter.next();
                    out.write(      "\t" + speller.getIso639()
                                +   "\t" + speller.getDescription() + nl);
                } // while iter
            } else { // >= 1 argument
                StringBuffer  fileName = new StringBuffer(128);
                String  arg;
                String  language = "en";
                boolean cardinal= false;
                boolean file    = false;
                boolean compass = false;
                boolean clock   = false;
                boolean month   = false;
                boolean parse   = false;
                boolean planet  = false;
                boolean season  = false;
                boolean greeting= false;
                boolean test    = false;
                boolean weekDay = false;
                int abbrevLen   = 0; // no abbreviation
                String variant  = ""; // rest behind "-h"

                // get all options
                while (iarg < args.length && args[iarg].startsWith("-")) {
                    String option = args[iarg ++];
                    if (option.indexOf("-c") >= 0) {
                        cardinal = cardinal || true;
                    }
                    if (option.startsWith("-l")) {
                        if (iarg < args.length) {
                            language = args[iarg ++];
                        }
                    }
                    if (option.startsWith("-f")) {
                        file = true;
                        if (iarg < args.length) {
                            arg = args[iarg ++];
                            if (arg.equals("-")) {
                                arg = "";
                            }
                            fileName.append(arg);
                        }
                    }
                    if (option.startsWith("-g")) {
                        greeting = true;
                    }
                    if (option.startsWith("-d")) {
                        compass  = true;
                    }
                    if (option.startsWith("-h")) {
                        clock    = true;
                        variant  = option.substring(2);
                    }
                    if (option.startsWith("-m")) {
                        if (option.equals("-mode")) {
                            if (iarg < args.length) {
                                arg = args[iarg ++];
                                if (arg.equals("-")) {
                                    arg = "plain";
                                    iarg --;
                                }
                                if (false) {
                                } else if (arg.equals("html")) {
                                    setMode(MODE_HTML);
                                } else if (arg.equals("plain")) {
                                    setMode(MODE_PLAIN);
                                } else if (arg.equals("tsv")) {
                                    setMode(MODE_TSV);
                                } else if (arg.equals("xml")) {
                                    setMode(MODE_XML);
                                } else {
                                    out.write("invalid mode \"" + arg + "\"");
                                    setMode(MODE_PLAIN);
                                }
                            } else {
                                setMode(MODE_PLAIN);
                            }
                        } else {
                            month = true;
                            if  (option.indexOf("3") >= 0) {
                                abbrevLen = 3;
                            }
                        } // -m
                    }
                    if (option.startsWith("-C")) {
                        parse = true;
                        while (iarg < args.length) {
                            fileName.append(args[iarg ++]);
                            fileName.append(" ");
                        }
                    }
                    if (option.startsWith("-p")) {
                        planet = true;
                    }
                    if (option.startsWith("-s")) {
                        season = true;
                    }
                    if (option.startsWith("-t")) {
                        test = true;
                        if (iarg < args.length) {
                            arg = args[iarg ++];
                            if (arg.equals("-")) {
                                arg = "";
                            }
                            fileName.append(arg);
                        }
                    }
                    if (option.startsWith("-w")) {
                        weekDay = true;
                        if  (option.indexOf("2") >= 0) {
                            abbrevLen = 2;
                        }
                    }
                } // while options

                BaseSpeller speller = factory.getSpeller(language);
                if (speller != null) { // language code was found
                    printStartTable(out, speller);
                    if (iarg < args.length) { // with argument
                        String number = args[iarg ++];
                        while (iarg < args.length) { // append additional words on commandline
                            number += " " + args[iarg ++];
                        }
                        int inum;
                        try {
                            inum = Integer.parseInt(number);
                        } catch (Exception exc) {
                            inum = -103; // default - undefined
                        }
                        if (number.startsWith("-") && number.length() <= 4) {
                            // spell all from 0 up to and including 'number'
                            int highNumber = - inum;
                            for (inum = 0; inum <= highNumber; inum ++) {
                                number = Integer.toString(inum);
                                word = speller.spellCardinal(number);
                                printRow(out, helper.getWikipediaLink(speller, number, word), word);
                            } // for inum
                        } else { // a single, positive number
                            if (false) {
                            } else if (clock   ) {
                                Matcher mat = clockPattern.matcher(number);
                                int hour = 0;
                                int minute = 0;
                                if (mat.matches()) {
                                    try {
                                        hour    = Integer.parseInt(mat.group(1));
                                        minute  = Integer.parseInt(mat.group(2));
                                    } catch (Exception exc) {
                                    }
                                } // if matches
                                printRow(out, number, speller.spellClock(hour, minute, variant));
                            } else if (compass) {
                                float angle = 0;
                                try {
                                    angle = Float.parseFloat(number + "f");
                                } catch (Exception exc) {
                                }
                                printRow(out, number, speller.spellCompass (angle));
                            } else if (greeting) {
                                printRow(out, number, speller.spellGreeting(inum));
                            } else if (greeting) {
                                printRow(out, number, speller.spellGreeting(inum));
                            } else if (month) {
                                printRow(out, number, speller.spellMonth(inum, abbrevLen));
                            } else if (planet) {
                                printRow(out, number, speller.spellPlanet(inum));
                            } else if (season) {
                                printRow(out, number, speller.spellSeason(inum));
                            } else if (weekDay) {
                                printRow(out, number, speller.spellWeekDay(inum, abbrevLen));
                            } else { // cardinal
                                int spacePos = number.indexOf(' ');
                                if (spacePos < 0) { // no space
                                    word = speller.spellCardinal(number);
                                    printRow(out, helper.getWikipediaLink(speller, number, word), word);
                                    word = speller.spellIdeographic(number);
                                    if (word != null) {
                                        printRow(out, word, "");
                                    }
                                } else { // range of min..max
                                    int min = Integer.parseInt(number.substring(0, spacePos));
                                    int max = Integer.parseInt(number.substring(spacePos+ 1));
                                    for (int num = min; num <= max; num ++) {
                                        number = String.valueOf(num);
                                        out.write(number);
                                        out.write(' ');
                                        out.write(speller.spellCardinal(number));
                                        out.write(nl);
                                    } // for num
                                }
                            }
                        }
                        // with argument
                    } else { // no argument - take predefined test cases
                            String fileString = fileName.toString();
                            if (false) {
                            } else if (parse && (month || season || weekDay)) {
                                String category = month ? "m" : (season ? "s" : "w");
                                if (abbrevLen > 0) {
                                    category += abbrevLen;
                                }
                                int pos = 0;
                                pos = speller.parseString(fileString, pos, category);
                                if (pos > 0) {
                                    out.write("{");
                                    out.write(fileString.substring(0, pos));
                                    out.write("=NuTime" + category.toUpperCase() + ".");
                                    out.write(speller.getResult());
                                    out.write("}");
                                }
                                out.write(fileString.substring(pos));
                                out.write(nl);
                            } else if (parse && file) {
                                speller.parseFile(fileString);
                            } else if (parse) {
                                int pos = 0;
                                pos = speller.parseString(fileString, pos);
                                if (pos > 0) {
                                    out.write("{");
                                    out.write(fileString.substring(0, pos));
                                    out.write("=NuCard.");
                                    out.write(speller.getResult());
                                    out.write("}");
                                }
                                out.write(fileString.substring(pos));
                                out.write(nl);
                            } else if (test) {
                                speller.testFile(fileString);
                            } else if (greeting || month || season || planet || weekDay) {
                                if (false) {
                                } else if (greeting) {
                                    for (int inum = 0; inum <= 24; inum += 6) {
                                        printRow(out, String.valueOf(inum), speller.spellGreeting(inum));
                                    } // for inum
                                } else if (month) {
                                    for (int inum = 1; inum <= 12; inum ++) {
                                        printRow(out, String.valueOf(inum), speller.spellMonth(inum, abbrevLen));
                                    } // for inum
                                } else if (planet) {
                                    for (int inum = -1; inum <= 8; inum ++) {
                                        printRow(out, String.valueOf(inum), speller.spellPlanet(inum));
                                    } // for inum
                                } else if (season) {
                                    for (int inum = 1; inum <= 4; inum ++) {
                                        printRow(out, String.valueOf(inum), speller.spellSeason(inum));
                                    } // for inum
                                } else if (weekDay) {
                                    for (int inum = 1; inum <= 7; inum ++) {
                                        printRow(out, String.valueOf(inum), speller.spellWeekDay(inum, abbrevLen));
                                    } // for inum
                                }
                            } else if (clock   ) {
                                int inum = 0;
                                while (inum       < BaseSpeller.TESTTIMES.length) {
                                    String number = BaseSpeller.TESTTIMES[inum];
                                    Matcher mat = clockPattern.matcher(number);
                                    int hour = 0;
                                    int minute = 0;
                                    if (mat.matches()) {
                                        try {
                                            hour    = Integer.parseInt(mat.group(1));
                                            minute  = Integer.parseInt(mat.group(2));
                                        } catch (Exception exc) {
                                        }
                                    } // if matches
                                    printRow(out, number, speller.spellClock(hour, minute, variant));
                                    inum ++;
                                } // while inum
                                // clock
                            } else if (compass) {
                                int inum = 0;
                                DecimalFormat form = (DecimalFormat) DecimalFormat.getInstance();
                                form.applyPattern("##0.00");
                                while (inum <= 32) {
                                    float angle = 11.250001f * inum;
                                    String number = Float.toString(angle);
                                    printRow(out, form.format(angle), speller.spellCompass(angle));
                                    inum += 2; // print 1/16 only
                                } // while inum
                                // compass
                            } else { // cardinal
                                int inum = 0;
                                while (inum       < BaseSpeller.TESTNUMBERS.length) {
                                    String number = BaseSpeller.TESTNUMBERS[inum];
                                    String temp = (number.length() >= 12)
                                            ? number.replaceAll("\\.",  " ")
                                            : number.replaceAll("\\.",  "");
                                    if (temp.length() <= speller.getMaxLog()) {
                                        word = speller.spellCardinal(number);
                                        if (number.length() <= 2 && (mode == MODE_HTML || mode == MODE_HTML_EM)) {
                                            printRow(out, helper.getWikipediaLink(speller, number, word), word);
                                        } else {
                                            printRow(out, temp, word);
                                        }
                                    } else {
                                        inum = BaseSpeller.TESTNUMBERS.length; // leave loop
                                    }
                                    inum ++;
                                } // while inum
                                // cardinal
                            }
                    } // predefined test cases
                    printEndTable(out);
                } // language was found
                else {
                    error(out, "unknown language code \"" + language + "\"");
                }
            } // args.length >= 1
        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
        } // try
        return out.toString();
    } // process

    /** Commandline interface for number spelling.
     *  @param args elements of the commandline separated by whitespace
     */
    public static void main(String args[]) {
        NumwordCommand speller = new NumwordCommand();
        String resultEncoding = "UTF-8";
        PrintWriter charWriter = new PrintWriter(Channels.newWriter(Channels.newChannel(System.out), resultEncoding));
        charWriter.print(speller.process(args));
        charWriter.flush();
        charWriter.close();
    } // main

} // NumwordCommand
