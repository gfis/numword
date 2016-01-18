/*  Abstract class for spelling of numbers in different languages
    @(#) $Id: BaseSpeller.java 852 2012-01-06 08:07:08Z gfis $
    2011-10-14: spellClock
    2011-03-14: private -> protected
    2009-11-24: spellGreeting
    2007-02-08: use SpellerFactory
    2006-07-27: parseString wiht 3rd parameter expanded for months etc.
                enumerateMorphem only if not starting with "0"
    2006-01-04: morphems h3, t3 for slavic languages; putMorphem
    2005-07-21: generalizations for input
    2005-06-01, Georg Fischer

    caution: UTF-8 is essential! ("[^a-zA-ZäöüÄÖÜßáéíóúÉÓÚàèìòùÀÈÌÒÙâêîôûÂÊÎÔÛåøçãõ]+");
    words for "month": http://en.wiktionary.org/wiki/Month
    month names in various languages: http://eo.wikipedia.org/wiki/Monato

    possible extensions:
    - ordinal numbers
    - fractions
    - declination, especially for 1, 2
    - variants for year numbers
    - variants for regional differences (e.g. Switzerland) and dialects
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
import  java.io.BufferedReader;
import  java.io.FileReader;
import  java.io.InputStreamReader;
import  java.util.StringTokenizer;
import  java.util.HashMap;
import  java.util.Iterator;
import  java.util.regex.Matcher;
import  java.util.regex.Pattern;

/** Base class for number spellers defining common properties and methods.
 *  @author Dr. Georg Fischer
 */
public abstract class BaseSpeller {
    public final static String CVSID = "@(#) $Id: BaseSpeller.java 852 2012-01-06 08:07:08Z gfis $";

    /** whether to write debugging output */
    protected final static boolean DEBUG = true;

    /** words for unit digits: 10**(3n+0), n = 0, 1, 2 ...
     *  digits used if no number words are known for a language
     */
    protected String[] wordN   = new String []
            { "0"
            , "1"
            , "2"
            , "3"
            , "4"
            , "5"
            , "6"
            , "7"
            , "8"
            , "9"
            };

    /** words for tens digits: 10**(3n+1), n = 0, 1, 2 ...
     */
    protected String[] wordN0  = new String [] {};

    /** words for hundreds digits: 10**(3n+2), n = 0, 1, 2 ...
     */
    protected String[] wordN00 = new String [] {};

    /** words for teens: 10+n, n = 0, 1, 2 ...
     */
    protected String[] word1N  = new String [] {};

    /** Prefixes of words for (10**6)**n or 1000**n;
     *  US counting: 10**9 = billion; French/German counting: 10**9 = Milliarde.
     *  These words are derived from the latin numbers up to 20.
     *  In most languages they end with "illion" (and "illiard" in some).
     *  This array is used by most language specific classes,
     *  and is rarely overridden.
     */
    protected String[] wordN000 = new String []
    // indexed by powers of 1000 = 10**3
    // [ ]                       (10**6)**N
    /*  0 */ { ""
    /*  1 */ , ""
    /*  2 */ , "mil"             /*  1 */
    /*  3 */ , "bil"             /*  2 */
    /*  4 */ , "tril"            /*  3 */
    /*  5 */ , "quadril"         /*  4 */
    /*  6 */ , "quintil"         /*  5 */
    /*  7 */ , "sextil"          /*  6 */
    /*  8 */ , "septil"          /*  7 */
    /*  9 */ , "octil"           /*  8 */
    /* 10 */ , "nonil"           /*  9 */
    /* 11 */ , "decil"           /* 10 */
    /* 12 */ , "undecil"         /* 11 */
    /* 13 */ , "duodecil"        /* 12 */
    /* 14 */ , "tredecil"        /* 13 */
    /* 15 */ , "quattuordecil"   /* 14 */
    /* 16 */ , "quindecil"       /* 15 */
    /* 17 */ , "sexdecil"        /* 16 */
    /* 18 */ , "septendecil"     /* 17 */
    /* 19 */ , "octodecil"       /* 18 */
    /* 20 */ , "novemdecil"      /* 19 */
    /* 21 */ , "vigintil"        /* 20 */
    };

    /** character propagation direction for HTML ("ltr" or "rtl" (for Arabic, Hebrew)) */
    private String direction;

    /** Sets the character propagation direction
     *  @param direction "ltr" or "rtl"
     */
    protected void setDirection(String direction) {
        this.direction = direction;
    } // setDirection

    /** Gets the character propagation direction
     *  @return "ltr" or "rtl"
     */
    public String getDirection() {
        return this.direction;
    } // getDirection

    /** Sets the maximum number of digits that can be spelled
     *  using all predefined latin 10**6 (or 10**3) prefixes in
     *  <em>wordN000</em>.
     *  @param liard whether the language uses "milliards"
     *  (German stepping by 10**6), or not (US stepping by 10**3)
     */
    protected void setMaxLog(boolean liard) {
        setMaxLog ((wordN000.length - 1) * (liard ? 6 : 3));
    } // setMaxLog(boolean)

    /** Sets the maximum number of digits that can be spelled
     *  @param len length of longest possible digit sequence
     */
    protected void setMaxLog(int len) {
        maxLog = len;
    } // setMaxLog(int)

    /** Sets the base tuple size of the numbering system
     *  @param len 3 for triples (grouping by 1000) or 4 for quadruples (by 10000)
     */
    protected void setLogTuple(int len) {
        lenTuple = len;
    } // setLogTuple

    /** Gets the maximum number of digits that can be spelled
     */
    public int getMaxLog() {
        return maxLog;
    } // getMaxLog

    /** current unit digit */
    protected int digitN;
    /** current tens digit */
    protected int digitN0;
    /** current "teens digit" */
    protected int digit1N;
    /** current hundreds digit */
    protected int digitN00;
    /** current thousands digit (for quadruple numbering system only, e.g. Chinese) */
    protected int digitN000;

    /**
     *  whether the numbering system uses
     *  multiples of 1000  = triples    (3)
     *  multiples of 10000 = quadruples (4)
     */
    protected int lenTuple = 3;
    /** whether current tuple is "(0)001" */
    protected boolean singleTuple;
    /** whether current tuple is "(0)000" */
    protected boolean zeroTuple;
    /** Whether previous tuple was "(0)000" */
    protected boolean previousZeroTuple;
    /** whether whole number is only "(0)000" */
    protected boolean nullOnly;

    /** maximum length of the number;
     *  10**maxLog - 1 is the maximum number representable by the language module
     */
    protected int maxLog = 1; // restricted to units for the moment

    /** effective length of the number (without embedded non-digits) */
    protected int realLog; // restricted to units for the moment

    /** list of applicable ISO-639 codes */
    private String iso639Codes;

    /** description for the language */
    protected String description;

    /** remaining exponent of thousands, = 0 for number < 1000 */
    protected int logTuple;

    /** separating space for most languages */
    protected String separator;

    /** buffer for the final number word or digit sequence */
    protected StringBuffer result;

    /** a representative set of test numbers (with dots between thousands for readability) */
    public final static String [] TESTNUMBERS = new String[]
            {  "0",  "1",  "2",  "3",  "4",  "5",  "6",  "7",  "8",  "9"
            , "10", "11", "12", "13", "14", "15", "16", "17", "18", "19"
            , "20", "21", "22", "23", "27", "28", "29"
            , "30", "31", "32", "33", "34"
            , "40", "41", "42", "49"
            , "50", "55"
            , "60", "61", "62"
            , "70", "71", "72", "79"
            , "80", "81", "83", "89"
            , "90", "91", "92", "98", "99"
            , "100", "101", "102", "103"
            , "200", "202"
            , "300", "303"
            , "400", "404", "472"
            , "500", "501", "505"
            , "600", "606"
            , "700", "707"
            , "800", "808"
            , "900", "998", "999"
            , "1.000", "1.001", "1.002", "1.234", "1984"
            , "2.333"
            , "10.000"
            , "33.000"
            , "100.000", "100.001", "123.456"
            , "1.000.000", "1.000.001", "1.234.567", "2.000.000"
            , "84.300.089"
            , "100.000.000", "300.080.211"
            , "1.000.000.000", "1.000.000.001", "1.234.567.890", "2.000.000.000"
            , "1.000.000.000.000", "2.000.000.000.000"
            , "1.000.000.000.000.000"
            , "1.000.000.300.000.000.400.000.500"
            , "4.004.003.003.002.002.001.001.000.000"
            , "9.008.007.006.005.004.003.002.001.000.000"
            , "20.019.018.017.016.015.014.013.012.011.010.009.008.007.006.005.004.003.002.001.000.000"
            , "219.119.018.018.017.017.016.016.015.015.014.014.013.013.002.012.011.011.010.010.009.009.008.008.007.007.006.006.005.005.004.004.003.003.002.002.001.001.000.000"
            , "220.120.019.018.018.017.017.016.016.015.015.014.014.013.013.002.012.011.011.010.010.009.009.008.008.007.007.006.006.005.005.004.004.003.003.002.002.001.001.000.000"
            , "321.220.120.019.018.018.017.017.016.016.015.015.014.014.013.013.002.012.011.011.010.010.009.009.008.008.007.007.006.006.005.005.004.004.003.003.002.002.001.001.000.000"
            , "421.321.220.120.019.018.018.017.017.016.016.015.015.014.014.013.013.002.012.011.011.010.010.009.009.008.008.007.007.006.006.005.005.004.004.003.003.002.002.001.001.000.000"
            };

    /** Constructor
     */
    public BaseSpeller() {
        morphMap = new HashMap/*<1.5*/<String, String>/*1.5>*/(128);
        setSeparator(true); // default: with separator
        setDirection("ltr");
        result = new StringBuffer(2048);
        setMaxLog(false); // no milliards
    } // Constructor()

    /** Appends some constant string to the resulting word,
     *  without a leading separator
     *  @param morphem part of the number word to be appended
     */
    protected void append(String morphem) {
        result.append(morphem);
    } // append

    /** Modifies the end of <em>result</em> by replacing
     *  a trailing string by some other
     *  @param source string to be found at the end of the current result
     *  @param target string to be inserted instead of <em>source</em>
     *  @return whether the replacement took place
     */
    protected boolean modifyResult(String source, String target) {
        boolean result = false;
        if (this.result.toString().endsWith(source)) {
            this.result.replace(this.result.length() - source.length()
                    , this.result.length(), target);
            result = true;
        }
        return result;
    } // modifyResult

    /** Appends a word with a leading <em>separator</em>
     *  @param word part of the number word to be appended
     */
    protected void putWord(String word) {
        result.append(separator);
        result.append(word);
    } // putWord

    /** Appends a morphem with a leading <em>separator</em>
     *  @param key key for morphem hash table
     */
    protected void putMorphem(String key) {
        result.append(separator);
        result.append((String) morphMap.get(key));
    } // putMorphem

    /** Defines whether the language has a (space) separator
     *  between the components of the number word
     *  @param separated = true (false) if the morphems are (not) separated
     */
    protected void setSeparator(boolean separated) {
        separator = separated ? " " : "";
    } // setSeparator(boolean)

    /** Defines the separator string
     *  between the components of the number word
     *  @param separ string which separates components of number words
     */
    protected void setSeparator(String separ) {
        separator = separ;
    } // setSeparator(String)

    /** Appends the separator string
     */
    protected void putSeparator() {
        result.append(separator);
    } // putSeparator

    /** Gets the description for the language.
     *  @return text describing the language of this speller
     */
    public String getDescription() {
        return getFirstIso639() + " - " + description;
    } // getDescription

    /** Sets the description for the language.
     *  @param text text describing the language of this speller
     */
    protected void setDescription(String text) {
        description = text;
    } // setDescription

    /** Gets the list of applicable ISO-639 codes for the language
     *  @return list of 2- or 3-letter codes from ISO-639,
     *  separated by commata
     */
    protected String getIso639() {
        return iso639Codes;
    } // getIso639

    /** Gets the first ISO-639 code for the language
     *  @return a 2- or 3-letter code from ISO-639
     */
    public String getFirstIso639() {
        int pos = iso639Codes.indexOf(",");
        String result = iso639Codes;
        if (pos >= 0) {
            result = iso639Codes.substring(0, pos);
        }
        return result;
    } // getFirstIso639

    /** Sets the list of applicable ISO-639 codes for the language
     *  @param list list of 2- or 3-letter codes from ISO-639,
     *  separated by commata
     */
    protected void setIso639(String list) {
        iso639Codes = list;
    } // setIso639

    /** Gets the result of the conversion: a number word for spellXYZ,
     *  or a digit sequence for parseXYZ.
     */
    public String getResult() {
        return result.toString();
    } // getResult

    /** Morphems of number words which cannot be enumerated;
     *  keys are described under <em>setMorphem</em>,
     *  mainly used for <em>parseString</em>
     */
    protected HashMap/*<1.5*/<String, String>/*1.5>*/ morphMap;

    /** Sets morphems of number words
     *  @param key meaning of the morphem (starting with a lowercase letter):
     *  <table>
     *  <tr><td>"h1"</td><td>morphem for    1 hundred  (use individual words for hundreds if missing)</td></tr>
     *  <tr><td>"h2"</td><td>morphem for >= 2 hundreds (same as "h1" if not set)</td></tr>
     *  <tr><td>"h3"</td><td>morphem for >= 3 hundreds (same as "h2" if not set)</td></tr>
     *  <tr><td>"t1"</td><td>morphem for    1 thousand</td></tr>
     *  <tr><td>"t2"</td><td>morphem for >= 2 thousands (same as "t2" if not set)</td></tr>
     *  <tr><td>"t3"</td><td>morphem for >= 3 thousands (same as "t3" if not set)</td></tr>
     *  <tr><td>"z1"</td><td>optional morphem for 10    (Sino)</td></tr>
     *  <tr><td>"tz"</td><td>optional morphem for 10000 (Sino, Klingon)</td></tr>
     *  <tr><td>"mh"</td><td>optional morphem for 100 Mio (Sino)</td></tr>
     *  <tr><td>"l1"</td><td>morphem for    1 million  (pre- and postfix)</td></tr>
     *  <tr><td>"l2"</td><td>morphem for >= 2 millions (pre- and postfix)</td></tr>
     *  <tr><td>"m0"</td><td>morphem for one million (prefix, special treatment if same as "t1")</td></tr>
     *  <tr><td>"m1"</td><td>morphem for one million, billion, trillion ... (only the postfix)</td></tr>
     *  <tr><td>"m2"</td><td>morphem for several millions ... (same as "m1" if not set)</td></tr>
     *  <tr><td>"m3"</td><td>morphem for one milliard (US counting with billions ... if missing)</td></tr>
     *  <tr><td>"m4"</td><td>morphem for several milliards (same as "m3" if not set)</td></tr>
     *  <tr><td>"pi"</td><td>i=0,1,2,... "meaningless" connecting morphems like e, und, et, s, ...</td></tr>
     *  <tr><td>"wm"</td><td>word for "month" (Sino)</td></tr>
     *  </table>
     *  Keys starting with '-' or a digit indicate a direct numerical value
     *  @param value value of the morphem to be stored under <em>key</em>
     */
    protected void setMorphem(String key, String value) {
        morphMap.put(key, value);
        if (! key.startsWith("p") && ! key.startsWith("0")) {
            if (key.endsWith("1")) {
                // set default for corresponding "x2", "x3", "x4"
                morphMap.put(key.substring(0, 1) + "2", value);
                morphMap.put(key.substring(0, 1) + "3", value);
                morphMap.put(key.substring(0, 1) + "4", value);
            }
            if (key.endsWith("2")) {
                // set default for corresponding "x3", "x4"
                morphMap.put(key.substring(0, 1) + "3", value);
                morphMap.put(key.substring(0, 1) + "4", value);
            }
            if (key.endsWith("3")) {
                // set default for corresponding "x4"
                morphMap.put(key.substring(0, 1) + "4", value);
            }
        } // not "p"
    } // setMorphem

    /** Gets a morphem set by <em>setMorphem</em>
     *  @param key meaning of the morphem (starting with a lowercase letter):
     */
    protected String getMorphem(String key) {
        return (String) morphMap.get(key);
    } // getMorphem

    /** Removes a morphem - opposite to <em>setMorphem</em>
     *  @param key meaning of the morphem (starting with a lowercase letter):
     */
    protected void removeMorphem(String key) {
        morphMap.remove(key);
    } // removeMorphem

    /** Enumerates all morphems used for number spelling,
     *  and maps them to their "meaning", that is the
     *  basic number value (as a string).
     */
    protected void enumerateMorphems() {
        int iw;         // index in a word array
        for (iw = 0; iw < wordN   .length; iw ++) {
            morphMap.put(String.valueOf(iw      ), wordN  [iw]);
        }
        for (iw = 1; iw < wordN0  .length; iw ++) {
            morphMap.put(String.valueOf(iw *  10), wordN0 [iw]);
        }
        for (iw = 1; iw < word1N  .length; iw ++) {
            morphMap.put(String.valueOf(iw +  10), word1N [iw]);
        }
        for (iw = 1; iw < wordN00 .length; iw ++) {
            morphMap.put(String.valueOf(iw * 100), wordN00[iw]);
        }
        for (iw = 2; iw < wordN000.length; iw ++) {
            morphMap.put("e" + (String.valueOf(iw + 100)).substring(1,3), wordN000[iw]);
                    // "enn", nn with leading zero
        }
        if (false && DEBUG) {
            Iterator/*<1.5*/<String>/*1.5>*/ iter = morphMap.keySet().iterator();
            while (iter.hasNext()) {
                String key = (String) iter.next();
                System.out.println("enumerateMorphems: " + key + " -> " + (String) morphMap.get(key));
            } // while
        }
    } // enumerateMorphems

    /** Parses a string, and returns a digit sequence if
     *  it starts with a number word.
     *  Always tries to match a longest prefix.
     *  @param text string to be parsed
     *  @param offset offset in <em>text</em> where to start parsing
     *  @return new offset in <em>text</em> of first particle not recognized,
     *  and sequence of digit characters in global <em>result</em>
     */
    public int parseString(String text, int offset) {
        int triple = 0; // current value of triple
        result.delete(0, result.length()); // clear buffer
        StringBuffer particle = new StringBuffer(32);
        boolean found = false;
        boolean liard = morphMap.get("m3") != null; // whether there is a special postfix "liard"yy
        int prefixLen = 1;
        while (prefixLen > 0) { // any number morphem found
            prefixLen = 0;
            String prefixKey = "";
            Iterator/*<1.5*/<String>/*1.5>*/ iter = morphMap.keySet().iterator();
            while (iter.hasNext()) { // search over all defined morphems
                String key   = (String) iter.next();
                String value = (String) morphMap.get(key);
                if (false && DEBUG) {
                    System.out.println("parseString: " + key + " -> \"" + value + "\"");
                }
                if (value.length() > prefixLen && text.startsWith(value, offset)) { // remember this
                    prefixKey = key;
                    prefixLen = value.length();
                }
            } // while
            if (prefixLen > 0) { // any number morphem found
                found = true;
                char ch0 = prefixKey.charAt(0);
                if (ch0 == 'p') { // meaningless particle - but only if behind other morphem
                    if (result.length() > 0 || triple != 0) {
                        particle.append(text.substring(offset, offset + prefixLen));
                    } else { // particle at start - no number word found
                        found = false;
                        prefixLen = 0;
                    }
                } else if (ch0 != '-' && ! Character.isDigit(ch0)) { // key with encoded meaning
                    particle.delete(0, particle.length()); // number follows - forget the particles
                    switch (ch0) {
                        case 'e': // million, (milliard), billion, ...
                            // look whether "lion"xx or "liard"yy follows
                            if  (   text.substring(offset + prefixLen).startsWith((String) morphMap.get("m1"))
                                ||  text.substring(offset + prefixLen).startsWith((String) morphMap.get("m2"))
                                ||  liard &&
                                (   text.substring(offset + prefixLen).startsWith((String) morphMap.get("m3"))
                                ||  text.substring(offset + prefixLen).startsWith((String) morphMap.get("m4"))
                                )
                                )
                            { // "mil" + "lion"xx
                                if (triple == 0) {
                                    // "million" instead of "one million" - should not occur
                                    triple = 1;
                                }
                                int exponent = Integer.parseInt(prefixKey.substring(1)) - 1;
                                        // exactly 2 digits behind 'e'
                                StringBuffer part = new StringBuffer(32);
                                part.append(String.valueOf(triple));
                                while (exponent > 0) {
                                    if (liard) { // German billion
                                        part.append("000000");
                                    } else { // US billion
                                        part.append("000");
                                    }
                                    exponent --;
                                } // while exponent
                                if  (! liard || liard &&
                                    (   text.substring(offset + prefixLen).startsWith((String) morphMap.get("m3"))
                                    ||  text.substring(offset + prefixLen).startsWith((String) morphMap.get("m4"))
                                    )
                                    )
                                { // liard postfix
                                    part.append("000"); // same as "lion" * 1000
                                }
                                if (result.length() > part.length()) {
                                    // replace trailing zeroes
                                    result.replace(result.length() - part.length()
                                            , result.length(), part.toString());
                                } else {
                                    result.append(part);
                                }
                                triple = 0;

                            } else { // prefix found, but not "lion/liard"
                                prefixLen = 0;
                            }
                            break;
                        case 'h': // hundred
                            if (triple > 0) {
                                triple *= 100;
                            } else { // missing "one" hundred
                                triple = 100;
                            }
                            break;
                        case 'k': // special Klingon exponents - not yet implemented ???
                            break;
                        case 'l': // million(s) (only in case 1000 same as prefix of 10**6: sp, pt, eo)
                            {
                                if (triple == 0) {
                                    // "million" instead of "one million" - should not occur
                                    triple = 1;
                                }
                                StringBuffer part = new StringBuffer(32);
                                part.append(String.valueOf(triple));
                                part.append("000000");
                                if (prefixKey.compareTo("l3") >= 0) { // milliard(s)
                                    part.append("000");
                                }
                                if (result.length() > part.length()) {
                                    // replace trailing zeroes
                                    result.replace(result.length() - part.length()
                                            , result.length(), part.toString());
                                } else {
                                    result.append(part);
                                }
                                triple = 0;
                            }
                            break;
                        // case 'p': handled separately above
                        //  break;
                        case 't': // thousand
                            if (triple == 0) {
                                triple = 1;
                            }
                            String part = String.valueOf(triple) + "000";
                            if (result.length() > part.length()) {
                                // replace trailing zeroes
                                result.replace(result.length() - part.length(), result.length(), part);
                            } else {
                                result.append(part);
                            }
                            triple = 0;
                            break;
                        default: // unknown key
                            break;
                    } // switch ch0
                }
                else { // key with direct numeric meaning:
                    particle.delete(0, particle.length()); // number follows - forget the particles
                    // units, *10, +10, *100
                    triple += Integer.parseInt(prefixKey); // exceptions should not occur
                }
                offset += prefixLen;
            } // number morphem found
        } // while match
        if (found) {
            if (triple == 0) {
                if (result.length() == 0) { // a single zero
                    result.append("0");
                }
            } else {
                if (result.length() == 0) { // < 1000
                    result.append(String.valueOf(triple));
                } else {
                    String part = String.valueOf(triple);
                    result.replace(result.length() - part.length(), result.length(), part);
                }
            }
        }
        return offset;
    } // parseString(String, int)

    /** Tries to find a word in a table of String pairs of the form
     *  (index, word). The index of the first entry found is returned.
     *  @param word string to be looked up in the table
     *  @return index value from the table as String,
     *  or null if the word was not found
     */
    public String lookupWord(String word, String[] pairs) {
        String result = null; // not found
        int index = 0;
        while (index < pairs.length) { // search
            if (pairs[index + 1].compareToIgnoreCase(word) == 0) { // found
                result = pairs[index];
                index = pairs.length; // break loop
            } // found
            index += 2;
        } // while searching
        return result;
    } // lookupWord

    /** Tries to find a word in a table of String pairs of the form
     *  (index, word).
     *  @param word string to be looked up in the table
     *  @return index value from the table converted to int,
     *  or -1 if the word was not found
     */
    public int intLookup(String word, String[] pairs) {
        int result = -1; // not found
        try {
            result = Integer.parseInt(lookupWord(word, pairs));
        } catch (Exception exc) {
            // cannot occur - table always contains proper number strings or null
        }
        return result;
    } // intLookup

    /** Parses a string, and returns the cardinal number of
     *  a month, weekday, or season.
     *  @param text string to be parsed
     *  @param offset offset in <em>text</em> where to start parsing
     *  @param category option "m", "m3", "w", "w2", "s"
     *  @return new offset in <em>text</em> of first particle not recognized,
     *  and sequence of digit characters in global <em>result</em>
     */
    public int parseString(String text, int offset, String category) {
        if (false && DEBUG) {
            System.out.println("parseString: test=\"" + text
                    + "\" offset=\"" + offset
                    + "\" category=\"" + category
                    + "\"");
        }
        result.delete(0, result.length()); // clear buffer
        StringBuffer particle = new StringBuffer(32);
        int found = 0;
        int prefixLen = 1;
        int abbrevLen = 0;
        if (category.length() > 1) {
            try {
                abbrevLen = Integer.parseInt(category.substring(1));
            } catch (Exception exc) { // doesn't matter
            }
        }
        int index = 1;
        String prefix = "A";
        while (found == 0 && prefix.compareTo("A") >= 0) {
            if (false) {
            } else if (category.startsWith("m")) {
                prefix = spellMonth(index);
            } else if (category.startsWith("s")) {
                prefix = spellSeason(index);
            } else if (category.startsWith("w")) {
                prefix = spellWeekDay(index);
            }

            if (abbrevLen > 0 && abbrevLen <= prefix.length()) {
                prefix = prefix.substring(0, abbrevLen);
            }
            if (text.startsWith(prefix)){
                found = index;
                offset = prefix.length();
            }
            index ++;
        } // while found == 0

        if (found > 0) {
            result.append(Integer.toString(found));
        }
        return offset;
    } // parseString(String, int, String)

    /** Parses a text file, and tries to recognize
     *  number words in the lines.
     *  Print
     *  @param fileName name of the file to be parsed,
     *  or read from STDIN if null or empty
     */
    public void parseFile(String fileName) {
        String line = null; // current line from text file
        Pattern gapPattern = Pattern.compile
                ("[^a-zA-ZäöüÄÖÜßáéíóúÉÓÚàèìòùÀÈÌÒÙâêîôûÂÊÎÔÛåøçãõ]+");
                // a gap consists of characters not in this list
        int posGap  = 0;  // start of current match
        int endGap  = 0;  // end   behind current match
        int posWord = 0;  // start of current (number?)word
        int endNum  = 0;  // end   behind found number word
        boolean found = false; // whether a gap was found

        try {
            BufferedReader lineReader = new BufferedReader
                    (   (fileName.length() <= 0)
                    ? new InputStreamReader(System.in)
                    : new FileReader (fileName) );
            line = lineReader.readLine();
            StringBuffer replaced = new StringBuffer(256);
            while (line != null) { // read and process lines
                replaced.delete(0, replaced.length());
                if (DEBUG) {
                    System.out.println(line + ";");
                }
                Matcher matcher = gapPattern.matcher(line + " ");
                posWord = 0;
                found = matcher.find(posWord); // behind last number word
                while (found) { // there is another gap
                    posGap = matcher.start();
                    endGap = matcher.end  ();
                    if (endGap <= line.length()) { // plausible
                        if (posWord < posGap) { // any word left
                            if (DEBUG) {
                                System.out.println("parseString: " + line.substring(posWord)
                                        + "; posWord = " + posWord
                                        + ", endNum = " + endNum
                                        + ", posGap = " + posGap
                                        + ", endGap = " + endGap);
                            }
                            endNum = parseString(line, posWord);
                            if (endNum > posWord) { // non-empty number word matched
                                replaced.append("<cardinal value=\"");
                                replaced.append(getResult());
                                replaced.append("\" text=\"");
                                replaced.append(line.substring(posWord, endNum));
                                replaced.append("\"/>");
                                endGap = endNum;
                            } else {
                                replaced.append(line.substring(posWord, endGap));
                            }
                        } else {
                                replaced.append(line.substring(posWord, endGap));
                        }
                    } else {
                            replaced.append(line.substring(posWord));
                    }
                    posWord = endGap;
                    found = matcher.find(posWord);
                } // while found
                if (posWord < line.length()) {
                    replaced.append(line.substring(posWord));
                }
                System.out.println(replaced.toString());
                line = lineReader.readLine();
            } // while ! eof
        } catch (Exception exc) {
            System.err.println(exc.getMessage());
            exc.printStackTrace();
            System.out.println("posWord = " + posWord
                    + ", endNum = " + endNum
                    + ", posGap = " + posGap
                    + ", endGap = " + endGap
                    );
        } // try
    } // parseFile

    /** Reads a text file consisting of tuples
     *  number, tab, number words, tries to recognize
     *  the number words, and prints the result.
     *  @param fileName name of the file to be parsed,
     *  or read from STDIN if null or empty
     */
    public void testFile(String fileName) {
        try {
            BufferedReader lineReader = new BufferedReader
                    (   (fileName.length() <= 0)
                    ? new InputStreamReader(System.in)
                    : new FileReader (fileName) );
            String line; // current line from text file

            line = lineReader.readLine();
            while (line != null) { // read and process lines
                int tabPos = line.indexOf("\t");
                if (tabPos < 0) {
                    tabPos = 0;
                }
                String digits = line.substring(0, tabPos);
                int errors = 0;
                if (! digits.equals(result.toString())) {
                    System.out.println(line);
                    System.out.println("\t? " + result.toString());
                    errors ++;
                }
                String word = spellCardinal(digits);
                if (! line.substring(tabPos + 1).equals(word)) {
                    if (errors == 0) {
                        System.out.println(line);
                    }
                    System.out.println("\t?\t" + word);
                    errors ++;
                }
                if (errors == 0) {
                    System.out.println(digits + " !");
                }
                line = lineReader.readLine();
            } // while ! eof
        } catch (Exception exc) {
            System.err.println(exc.getMessage());
            exc.printStackTrace();
        } // try
    } // testFile

    /** Appends the word for <em>digit+10</em>
     *  @param digit digit at position 10**(3n+1), n=0,1,...
     */
    protected void spell1N(int digit) {
        putWord(word1N[digit]);
    } // spell1N

    /** Appends the word for <em>digit</em>
     *  @param digit digit at position 10**(3n+0), n=0,1,...
     */
    protected void spellN(int digit) {
        putWord(wordN[digit]);
    } // spellN

    /** Appends the word for <em>digit*10</em>
     *  @param digit digit at position 10**(3n+1), n=0,1,...
     */
    protected void spellN0(int digit) {
        putWord(wordN0[digit]);
    } // spellN0(int)

    /** Appends the word for <em>digit*100</em>
     *  @param digit digit at position 10**(3n+2), n=0,1,...
     */
    protected void spellN00(int digit) {
        putWord(wordN00[digit]);
    } // spellN00(int)

    /** Appends the base word for 10**3
     *  @param log1000 logarithm of 1000, = 2, 3, 4 ...
     */
    protected void spellN000(int log1000) {
        putWord(wordN000[log1000]);
    } // spellN000(int)

    /** Appends the word for 10**3,
     *  followed by the suffixes for "lion" (US counting)
     *  @param log1000 logarithm of 1000, = 2, 3, 4 ...
     *  @param lion  suffix for 10**6, 10**9 ...
     */
    protected void spellN000(int log1000, String lion) {
        spellN000(log1000);
        append(lion);
    } // spellN000(int, String)

    /** Appends the word for 10**3,
     *  followed by the suffixes for "lion" or "liard" (French counting)
     *  @param log   logarithm of 1000 / 2 + 1
     *  @param lion  suffix for 10**6, 10**12 ...
     *  @param liard suffix for 10**9, 10**15 ...
     */
    protected void spellN000(int log, String lion, String liard) {
        spellN000(log / 2 + 1);
        if (log % 2 == 0) { // even
            append(lion);
        } else {
            append(liard);
        }
    } // spellN000(int, String, String)

    /** Appends the word for 10**3,
     *  followed by the suffixes for "lion" or "liard" (French counting),
     *  possibly with plural endings, as stored in morphems m1,m2,m3,m4
     *  @param log   logarithm of 1000 / 2 + 1
     */
    protected void spellN000Morphem(int log) {
        spellN000(log / 2 + 1);
        if (log % 2 == 0) { // even
            if (singleTuple) {
                append(getMorphem("m1"));
            } else {
                append(getMorphem("m2"));
            }
        } else {
            if (singleTuple) {
                append(getMorphem("m3"));
            } else {
                append(getMorphem("m4"));
            }
        }
    } // spellN000Morphem

    /** Appends the wording for a triple of digits,
     *  plus the word for the applicable power of 1000.
     *  @param number the remaining part of the whole number
     */
    public abstract void spellTuple(String number);

    /** Returns the word for a number in some language.
     *  This method is the heart of the package.
     *  It assumes the "normal" european numbering system
     *  derived from latin. The entire number is splitted
     *  into triples of digits: hundreds, tens, and ones.
     *  These are spelled in order, joined by some morphemes
     *  like "and", and "s" for plural.
     *  The words for ones, tens, for 10..19 and sometimes
     *  for the hundreds are stored in language specific arrays.
     *  @param number a sequence of digit characters, maybe
     *  interspersed with non-digits (spaces, punctuation).
     *  @return number word
     */
    public String spellCardinal(String number) {
        result.setLength(0);
        digitN000 = 0; // invariant for lenTuple == 3
        // remove anything but digits
        StringBuffer buffer = new StringBuffer(1024);
        // ensure length is a multiple of 'lenTuple'
        String nullTuple = "000000".substring(0, lenTuple);
        buffer.append(nullTuple);
        int position = 0;
        while (position < number.length()) { // remove non-digits
            char ch = number.charAt(position);
            if (Character.isDigit(ch)) {
                buffer.append(ch);
            }
            position ++;
        } // while non-digits
        realLog = buffer.length() - lenTuple; // -3 because of "000" above
        // trim size to multiples of 'lenTuple'
        number = buffer.toString().substring(buffer.length() % lenTuple);
        previousZeroTuple = true;
        if (realLog <= maxLog) { // number can be spelled in this language
            position = 0;
            nullOnly = number.equals(nullTuple);
            while (position < number.length()) { // process all triples
                // extract some global properties for the triple, and spell it
                if (lenTuple == 4) {
                    digitN000 = number.charAt(position ++) - '0';
                }
                {
                    digitN00  = number.charAt(position ++) - '0';
                    digitN0   = number.charAt(position ++) - '0';
                    digitN    = number.charAt(position ++) - '0';
                }
                singleTuple = (digitN000 + digitN00 + digitN0 == 0) && digitN == 1;
                zeroTuple   = (digitN000 + digitN00 + digitN0 == 0) && digitN == 0;
                logTuple    = (number.length() - position) / lenTuple; // 1 for 10**3, 2 for 10**6 ...
                spellTuple(number.substring(position));
            /*
                if (! zeroTuple) {
                    append("\n"); // break line after triple
                }
            */
                previousZeroTuple = zeroTuple;
            } // while all triples
            result.delete(0, separator.length()); // remove any initial separator
        } else { // realLog too big
            if (DEBUG) {
                System.err.println ("realLog " + realLog + " > maxLog " + maxLog);
            }
            result.append(number + " >= 1");
            for (int pos = 0; pos < maxLog; pos ++) {
                result.append('0');
            } // for int
        }
        return (result.substring(0, 1).equals(separator)
                ?   result.substring(1).toString()
                :   result.toString());
    } // spellCardinal

    //================================================================
    /** Returns a denotation of the day's time, possibly in several variants
     *  @param hour hour 0..24
     *  @param minute minute 0..59
     *  @param variant the code behind the commandline option "-h":
     *  empty or 0 (official), 1,2,3 for  a language specific variant.
     *  @return phrase corresponding to the denotation of the time, for example
     *  <ul>
     *  <li>English, default: 18:15 =&gt; "quarter past six"</li>
     *  <li>German, variant "1" = southern: 18:45 =&gt; "dreiviertel sieben"</li>
     *  </ul>
     */
    public String spellClock(int hour, int minute, String variant) {
        return String.valueOf(hour   + 100).substring(1) + ':'
            +  String.valueOf(minute + 100).substring(1);
    } // spellClock(3)

    /** Returns a denotation of the day's time, official variant
     *  @param hour hour 0..24
     *  @param minute minute 0..59
     *  @return phrase corresponding to the official denotation of the time, for example
     *  <ul>
     *  <li>English,: 18:15 =&gt; "quarter past six"</li>
     *  <li>German: 18:44 =&gt; "achtzehn Uhr vierundvierzig"</li>
     *  </ul>
     */
    public String spellClock(int hour, int minute) {
        return spellClock(hour, minute, "0");
    } // spellClock(2)

    /** a representative set of test day times */
    public final static String [] TESTTIMES = new String[]
            {  "0:00", "0:15", "0:20", "1:00",  "2:10",  "3:15",  "4:29",  "5:30",  "6:31",  "7:45",  "8:50",  "9:00"
            ,  "12:00",  "12:15", "12:30", "12:45", "13:10",  "13:15", "13:30", "13:45"
            ,  "21:20"
            ,  "23:30", "23:45", "23:55", "24:00"
            };

    //================================================================
    private static String[] CARDDIRS = new String[]
            { "N"       //  00000b  0       Noord
            , "NNNE"    //  00001b  11¼     Noord ten oosten
            , "NNE"     //  00010b  22½     Noordnoordoost
            , "ENNE"    //  00011b  33¾     Noordoost ten noorden
            , "NE"      //  00100b  45      Noordoost
            , "NENE"    //  00101b  56¼     Noordoost ten oosten
            , "ENE"     //  00110b  67½     Oostnoordoost
            , "EENE"    //  00111b  78¾     Oost ten noorden
            , "E"       //  01000b  90      Oost
            , "EESE"    //  01001b  101¼    Oost ten zuiden
            , "ESE"     //  01010b  112½    Oostzuidoost
            , "SESE"    //  01011b  123¾    Zuidoost ten oosten
            , "SE"      //  01100b  135     Zuidoost
            , "ESSE"    //  01101b  146¼    Zuidoost ten zuiden
            , "SSE"     //  01110b  157½    Zuidzuidoost
            , "SSSE"    //  01111b  168¾    Zuid ten oosten
            , "S"       //  10000b  180     Zuid
            , "SSSW"    //  10001b  191¼    Zuid ten westen
            , "SSW"     //  10010b  202½    Zuidzuidwest
            , "WSSW"    //  10011b  213¾    Zuidwest ten zuiden
            , "SW"      //  10100b  225     Zuidwest
            , "SWSW"    //  10101b  236¼    Zuidwest ten westen
            , "WSW"     //  10110b  247½    Westzuidwest
            , "WWSW"    //  10111b  258¾    West ten zuiden
            , "W"       //  11000b  270     West
            , "WWNW"    //  11001b  281¼    West ten noorden
            , "WNW"     //  11010b  292½    Westnoordwest
            , "NWNW"    //  11011b  303¾    Noordwest ten westen
            , "NW"      //  11100b  315     Noordwest
            , "WNNW"    //  11101b  326¼    Noordwest ten noorden
            , "NNW"     //  11110b  337½    Noordnoordwest
            , "NNNW"    //  11111b  348¾    Noord ten westen
            , "N"       // 100000b  360     Noord
            };

    /** Returns a cardinal direction corresponding to the parameter angle:
     *    0 = north,
     *   90 = east,
     *  180 = south,
     *  270 = west,
     *  and also intermediate directions like 45 = north-east, 22.5 = north-north-east, or even 11.25
     *  @return words corresponding to the cardinal direction (of the compass)
     */
    public String spellCompass(float angle) {
        String result = Float.toString(angle); // an abbreviation consisting of the letters N, E, S, W
        int d32 = Float.valueOf(angle * 4.0f + 0.0001f).intValue() / 45; // we divide down to 360 / 32 = 45 / 4 = 11.25 degree segments
        if (d32 < 0 || d32 > 32) {
            // error - return the number unchanged
        } else { // in range
            result = CARDDIRS[d32 % 32];
        }
        return spellCompassCode(result);
    } // spellCompass(float)

    /** Returns a cardinal direction corresponding to the parameter angle:
     *    0 = north,
     *   90 = east,
     *  180 = south,
     *  270 = west,
     *  and also intermediate directions like 45 = north-east, 22.5 = north-north-east, or even 11.25
     *  @return words corresponding to the cardinal direction (of the compass)
     */
    private String spellCompass2(float angle) {
        String result = Float.toString(angle); // an abbreviation consisting of the letters N, E, S, W
        String compassSep = getCompassSeparator();
        int d32 = Float.valueOf(angle * 4.0f + 0.0001f).intValue() / 45; // we divide down to 360 / 32 = 45 / 4 = 11.25 degree segments
        if (d32 < 0 || d32 > 32) {
            // error - return the number unchanged
        } else { // in range
            int d16 = d32 >> 1;
            int d8  = d16 >> 1;
            int d4  = d8  >> 1;
            int d2  = d4  >> 1;
            if (d32 % 2 != 0) { // for /32 - is there a cute formula?
            /*  ddd13
                24862
                00000b  0       N       Noord
                00001b  11¼     NNNO    Noord ten oosten
                00010b  22½     NNO     Noordnoordoost
                00011b  33¾     ONNO    Noordoost ten noorden
                00100b  45      NO      Noordoost
                00101b  56¼     NONO    Noordoost ten oosten
                00110b  67½     ONO     Oostnoordoost
                00111b  78¾     OONO    Oost ten noorden
                01000b  90      O       Oost
                01001b  101¼    OOZO    Oost ten zuiden
                01010b  112½    OZO     Oostzuidoost
                01011b  123¾    ZOZO    Zuidoost ten oosten
                01100b  135     ZO      Zuidoost
                01101b  146¼    OZZO    Zuidoost ten zuiden
                01110b  157½    ZZO     Zuidzuidoost
                01111b  168¾    ZZZO    Zuid ten oosten
                10000b  180     Z       Zuid
                10001b  191¼    ZZZW    Zuid ten westen
                10010b  202½    ZZW     Zuidzuidwest
                10011b  213¾    WZZW    Zuidwest ten zuiden
                10100b  225     ZW      Zuidwest
                10101b  236¼    ZWZW    Zuidwest ten westen
                10110b  247½    WZW     Westzuidwest
                10111b  258¾    WWZW    West ten zuiden
                11000b  270     W       West
                11001b  281¼    WWNW    West ten noorden
                11010b  292½    WNW     Westnoordwest
                11011b  303¾    NWNW    Noordwest ten westen
                11100b  315     NW      Noordwest
                11101b  326¼    WNNW    Noordwest ten noorden
                11110b  337½    NNW     Noordnoordwest
                11111b  348¾    NNNW    Noord ten westen
               100000b  360     N       Noord
                24862
            */
                result  =               compassLetters[((d8 % 4 + d16 % 2) >> 1) % 4]
                        + compassSep  + compassLetters[(d4 % 4 +  d8 % 2) % 4]
                        + compassSep  + compassLetters[(d4 + 1) & 2]
                        + compassSep  + compassLetters[((d2 % 2) << 1) + 1];
            } else { // d32 even
                if (d16 % 2 != 0) { // d16 odd
                    // d16 = NNE = 0001b, ENE = 0011b, ESE = 0101b, SSE = 0111b, SSW = 1001b, WSW = 1011b, WNW = 1101b, NNW = 1111b
                    //       NE           NE           SE           SE           SW           SW           NW           NW
                    result  =               compassLetters[(d4 % 4 + d8 % 2) % 4]
                            + compassSep  + compassLetters[(d4 + 1) & 2]
                            + compassSep  + compassLetters[((d2 % 2) << 1) + 1];
                } else { // d16 even
                    if (d8 % 2 != 0) { // d8 odd
                        // d8 = NE = 001b, SE = 011b, SW = 101b, NW = 111b
                        result  =               compassLetters[(d4 + 1) & 2]
                                + compassSep  + compassLetters[((d2 % 2) << 1) + 1];
                    } else { // d8 even, d4
                        // d4 = N = 00b, E = 01b, S = 10b, W = 11b
                        result = compassLetters[d4 % 4];
                    } // d8 % 2 == 0
                } // d16 % 2 == 0
            } // d32 % 2 =0 0
        } // in range
        return spellCompassCode(result);
    } // spellCompass2(float)

    /** Returns the language specific words for a cardinal direction
     *  @param code abbreviation, a sequence of the following letters:
     *  <ul>
     *  <li>N = north</li>
     *  <li>E = east</li>
     *  <li>S = south</li>
     *  <li>W = west</li>
     *  </ul>
     *  They may be combined to NE, NNE or even NNNE.
    <pre>
from: http://de.wikipedia.org/wiki/Himmelsrichtung

=== Systematik der Benennung {{Anker|Systematik}} ===
[[Datei:Compass Card.png|miniatur|Die Himmelsrichtungen (32er-Teilung, mit Gradskala genordet)]]
* Viertel haben ihre eigene Bezeichnung (N,&nbsp;O oder E,&nbsp;S,&nbsp;W)
* Achtel werden aus den Namen der Vierteln zusammengesetzt, wobei Nord und Süd vor West und Ost (O oder&nbsp;E) stehen:
  [[Nordwest]], [[Nordost]], [[Südost]], [[Südwest]] – abgekürzt NW,&nbsp;NO oder NE,&nbsp;SO oder&nbsp;SE,&nbsp;SW.
* Sechzehntel setzen sich aus den Namen der Viertel und des jeweils benachbarten Achtels zusammen (in dieser Reihenfolge).
  Beispiele: WSW&nbsp;= Westsüdwest, SSW&nbsp;= Südsüdwest.
* Zweiunddreißigstel werden gebildet, indem man dem jeweiligen angrenzenden Viertel oder Achtel mit einem „zu“
  dem in die Richtung liegenden nächsten Viertel-Namen anhängt. Beispiele: WzS&nbsp;= West zu Süd, SWzW&nbsp;= Südwest zu West.
* halbe Striche werden aus dem Namen des ganzen Strichs (gerade Striche) und einem „ein halb“ mit dem jeweiligen Viertel-Namen gebildet.
  Zum Beispiel: Nord ein halb Ost (N&nbsp;1/2&nbsp;E = 5,625&nbsp;Grad) oder SüdOst ein halb Ost (SE&nbsp;1/2&nbsp;E) = 129,375&nbsp;Grad).
Seit längerer Zeit zieht man aber die Angabe von [[Grad (Winkel)|Gradzahlen]] vor,
die beim Steuern von Kursen meistens auf 5° oder 10° gerundet werden. 5° entspricht etwa der Genauigkeit, mit der ein kleines Schiff gesteuert werden kann.
    </pre>
     */
    protected String spellCompassCode(String code) {
        String result = code;
        String compassSep = getCompassSeparator();
        switch (code.length()) {
            case 44: // not yet
                result = code.substring(0, 3) + " " + getCompassWord(4) + " " + code.substring(3,4);
                // fall thru
            case 1:
            case 2:
            case 3:
                result = result
                        .replaceAll("N", getCompassWord(0) + compassSep)
                        .replaceAll("E", getCompassWord(1) + compassSep)
                        .replaceAll("S", getCompassWord(2) + compassSep)
                        .replaceAll("W", getCompassWord(3) + compassSep)
                        ;
                break;
            default: // return the abbreviation unchanged
                break;
        } // switch length
        result = result.substring(0, 1).toUpperCase() + result.substring(1);
        if (compassSep.length() > 0 && result.endsWith(compassSep)) {
            result = result.substring(0, result.length() - 1);
        }
        if (false && code.length() == 4) {
            int pos = result.indexOf("zu ");
            result  = result.substring(0, pos + 3)
                    + result.substring(pos + 3, pos + 4).toUpperCase()
                    + result.substring(pos + 4);
        }
        return result;
    } // spellCompassCode

    /** Standard letters for the 4 cardinal directions */
    protected String[] compassLetters = new String[]
            { "N"   //   0 degrees = 360
            , "E"   //  90
            , "S"   // 180
            , "W"   // 270
            };
    /** Standard words (here: abbreviations) for the 4 cardinal directions,
     *  and for the particle for 32th fractions
     */
    protected String[] compassWords = new String[]
            { "N"   //   0 degrees = 360
            , "E"   //  90
            , "S"   // 180
            , "W"   // 270
            , " "
            };

    /** Get a standard word (here: abbreviation) for one the 4 cardinal directions,
     *  and for the particle for 32th fractions
     *  @param cardDir a cardinal direction, 0 = North, 1 = East, 2 = South, 3 = West
     */
    protected String getCompassWord(int cardDir) {
        String result = "";
        switch (cardDir) {
            case 0: result = "N"; break;
            case 1: result = "E"; break;
            case 2: result = "S"; break;
            case 3: result = "W"; break;
        } // switch
        return result;
    } // getCompassWord

    /** Standard separator for the cardinal direction words */
    protected String getCompassSeparator() {
        return ""; // none for abbreviations
    } // getCompassSeparator

    //================================================================
    /** Returns a greeting corresponding to the parameter time (hour):
     *  <pre>
     *   0 = Good bye
     *   6 = Good morning
     *  12 = Hello
     *  18 = Good evening
     *  24 = Good night
     *  </pre>
     *  @return greeting corresponding to the time of the day
     */
    public String spellGreeting(int timeOfDay) {
        return (timeOfDay == 0)
                ? "Good bye"
                : "Hello"; // String.valueOf(timeOfDay);
    } // spellGreeting(int)

    //================================================================
    /** Returns the month's name
     *  @param month month's number, >= 1 and <= 12
     *  @return word denoting the month
     */
    public String spellMonth(int month) {
        return String.valueOf(month);
    } // spellMonth(int)

    /** Returns an abbreviation of the month's name
     *  @param month month number, >= 1 and <= 12
     *  @param abbreviation length of the abbreviation (usually 3)
     *  @return the month's abbreviation, or the full month name
     *  if len = 0
     */
    public String spellMonth(int month, int abbreviation) {
        String result = spellMonth(month);
        if (abbreviation > 0) {
            if (abbreviation < 3) {
                abbreviation = 3;
            }
            if (result.length() >= abbreviation) {
                result = result.substring(0,abbreviation);
            }
        } // else = 0: take it unabbreviated
        return result;
    } // spellMonth(int, int)

    //================================================================
    /** Returns a planet
     *  @param planet number of the planet (3 = earth, 0 = sun, -1 = moon)
     *  @return planet's name
     */
    public String spellPlanet(int planet) {
        int result = 0x263e;
        switch (planet) {
            case 0:     result -= 2;        break; // sun
            case -1:    result -= 0;        break; // moon
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                        result += planet;   break;
        } // switch
    /*
        String.valueOf(planet);
        switch (planet) {
            case 2:     result = "Venus";       break;
            case 4:     result = "Mars";        break;
            case 5:     result = "Jupiter";     break;
            case 6:     result = "Saturn";      break;
            case 7:     result = "Uranus";      break;
            case 8:     result = "Neptun";      break;
        } // switch
    */
        return Character.toString((char) result);
    } // spellPlanet(int)

    //================================================================
    /** Returns the season's name
     *  @param season number of the quarter in the year:
     *  1 -> Spring, 2 -> Summer, 3 -> Autumn, 4 = Winter
     *  @return word denoting the season
     */
    public String spellSeason(int season) {
        return String.valueOf(season);
    } // spellSeason(int)

    //================================================================
    /** Returns the week day's name
     *  @param weekDay number of day in week, >= 0 and <= 7,
     *  1 -> Monday, 7 (and 0) -> Sunday
     *  @return word denoting the day in the week
     */
    public String spellWeekDay(int weekDay) {
        return String.valueOf(weekDay);
    } // spellWeekDay(int)

    /** Returns an abbreviation of the week day's name
     *  @param weekDay number of day in week, >= 0 and <= 7,
     *  1 -> Monday, 7 (and 0) -> Sunday
     *  @return word denoting the day in the week, or the full week day
     *  if len = 0
     */
    public String spellWeekDay(int weekDay, int abbreviation) {
        String result = spellWeekDay(weekDay);
        if (abbreviation > 0) {
            if (abbreviation < 2) {
                abbreviation = 2;
            }
            if (result.length() >= abbreviation) {
                result = result.substring(0,abbreviation);
            }
        } // else = 0: take it unabbreviated
        return result;
    } // spellWeekDay(int, int)

} // BaseSpeller
