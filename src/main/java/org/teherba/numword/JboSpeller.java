/*  Spelling of numbers in Lojban
    an artificial language
    @(#) $Id: DeuSpeller.java 852 2012-01-06 08:07:08Z gfis $
    2020-12-17, Georg Fischer: copied from DeuSpeller
*/
/*
 * Copyright 2020 Dr. Georg Fischer <dr dot georg dot fischer@gmail dot com>
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

/** Spells numbers in Lojban
 *  @author Dr. Georg Fischer
 */
public class JboSpeller extends BaseSpeller {
    public final static String CVSID = "@(#) $Id: DeuSpeller.java 852 2012-01-06 08:07:08Z gfis $";

    /** Constructor
     */
    public JboSpeller() {
        super();
        setIso639("jbo");
        setDescription("Lojban");
        setMaxLog(true);
        setSeparator(false);

        wordN = new String[]
        { "no" // 0
        , "pa" // 1
        , "re" // 2
        , "ci" // 3
        , "vo" // 4
        , "mu" // 5
        , "xa" // 6
        , "ze" // 7
        , "bi" // 8
        , "so" // 9
        };
    } // Constructor

    /** Returns the word for a number in some language.
     *  This method is the heart of the package.
     *  The digits are simply translated into their Lojban words.
     *  @param number a sequence of digit characters, maybe
     *  interspersed with non-digits (spaces, punctuation).
     *  @return number word
     */
    public String spellCardinal(String number) {
        result.setLength(0);
        for (int ic = 0; ic < number.length(); ic ++) {
            char ch = number.charAt(ic);
            if (Character.isDigit(ch)) {
                result.append(' ');
                result.append(wordN[Character.digit(number.charAt(ic), 10)]);
            }
        } // for ic
        return (result.substring(1));
    } // spellCardinal

    /** Parses a string, and returns a digit sequence if
     *  it starts with a number word;
     *  always tries to match a longest prefix;
     *  @param text string to be parsed
     *  @param offset offset in <em>text</em> where to start parsing
     *  @return new offset in <em>text</em> of first particle not recognized,
     *  and sequence of digit characters in global <em>result</em>
     */
    public int parseString(String text, int offset) {
    	boolean busy = true;
        while (busy && offset < text.length()) {
            if (! Character.isLetter(text.charAt(offset))) {
                offset ++; // ignore whitespace
            } else {
                int digit = 0;
                boolean found = false;
                while (digit < wordN.length && ! found && offset <= text.length() - 2) {
                    if (text.substring(offset, offset + 2).equals(wordN[digit])) {
                        found = true;
                        result.append(String.valueOf(digit));
                        offset += 2;
                    }
                    digit ++;
                } // while ! found
                busy = found;
            }
        } // while offset
        return offset;
    } // parseString

    /** Appends the wording for a triple of digits,
     *  plus the remaining power of 1000
     *  @param number the remaining part of the whole number
     */
    public void spellTuple(String number) {
        // not used
    } // spellTuple

    //================================================================
    /** Pairs of Lobjan month numbers, month names and their abbreviations 
     *  http://lojban.org/publications/level0/lessons/less5days.html
     */
    private String months[] = new String[]
            { "00", "nonmast."  // 0
            , "01", "pavmast."  // 1
            , "02", "relmast."  // 2
            , "03", "cibmast."  // 3
            , "04", "vonmast."  // 4
            , "05", "mumymast." // 5
            , "06", "xavmast."  // 6
            , "07", "zelmast."  // 7
            , "08", "bivmast."  // 8
            , "09", "sozmast."  // 9
            , "10", "daumast."  // 10
            , "11", "feimast."  // 11
            , "12", "gaimast."  // 12
            // abbreviations (2 letters)
            , "01", "pa"
            , "02", "re"
            , "03", "ci"
            , "04", "vo"
            , "05", "mu"
            , "06", "xa"
            , "07", "ze"
            , "08", "bi"
            , "09", "so"
            , "10", "da"
            , "11", "fe"
            , "12", "ga"
            // aliases and other abbreviations
            , "00", "nonma'i"  // 0
            , "01", "pavma'i"  // 1
            , "02", "relma'i"  // 2
            , "03", "cibma'i"  // 3
            , "04", "vonma'i"  // 4
            , "05", "mumyma'i."// 5
            , "06", "xavma'i"  // 6
            , "07", "zelma'i"  // 7
            , "08", "bivma'i"  // 8
            , "09", "sozma'i"  // 9
            , "10", "pavnonma'i"  // 10
            , "11", "pavypavma'i" // 11
            , "12", "pavrelma'i"  // 12
            , "10", "pavnonmast."  // 10
            , "11", "pavypavmast." // 11
            , "12", "pavrelmast."  // 12
            };

    /** Returns the month's number as String (between "01" and "12")
     *  @param name name of the month, or an alias or abbreviation
     *  @return number between "01" and "12", or null if not found
     */
    public String parseMonth(String name) {
       return lookupWord(name, months);
    } // parseMonth

    /** Returns the month's name
     *  @param month month's number, &gt;= 1 and &lt;= 12
     *  @return word denoting the month
     */
    public String spellMonth(int month) {
        return (month >= 0 && month <= 12)
                ? months[month * 2 + 1]
                : Integer.toString(month);
    } // spellMonth

    //================================================================
    /** Returns the season's name
     *  @param season number of the quarter in the year:
     *  1 -&gt; Spring, 2 -&gt; Summer, 3 -&gt; Autumn, 4 = Winter
     *  @return word denoting the season
     */
    public String spellSeason(int season) {
        String result = Integer.toString(season);
        if (season >= 0 && season <= 4) {
            result = (new String []
                    { ""
                    , "vensa"
                    , "crisa"
                    , "critu"
                    , "dunra"
                    })[season];
        }
        return result;
    } // spellSeason

    //================================================================
    /** Pairs of weekday numbers, weekday names and their abbreviations */
    private String weekdays[] = new String[]
            { "00", "nondjed."
            , "01", "pavdjed."
            , "02", "reldjed."
            , "03", "cibdjed."
            , "04", "vondjed."
            , "05", "mumdjed."
            , "06", "xavdjed."
            , "07", "zeldjed."
            , "01", "pa"
            , "02", "re"
            , "03", "ci"
            , "04", "vo"
            , "05", "mu"
            , "06", "xa"
            , "07", "ze"
            };

    /** Returns the week day's name
     *  @param weekDay number of day in week, &gt;= 0 and &lt;= 7,
     *  1 -&gt; Monday, 7 -&gt; Sunday
     *  @return word denoting the day in the week
     */
    public String spellWeekDay(int weekDay) {
        String result = Integer.toString(weekDay);
        if (weekDay >= 0 && weekDay <= 7) {
            result = (new String []
                    { "nondjed."
                    , "pavdjed."
                    , "reldjed."
                    , "cibdjed."
                    , "vondjed."
                    , "mumdjed."
                    , "xavdjed."
                    , "zeldjed."
                    })[weekDay];
        }
        return result;
    } // spellWeekDay

    //================================================================
    /** Returns a denotation of the day's time, possibly in several variants
     *  @param hour hour 0..24
     *  @param minute minute 0..59
     *  @param variant the code behind the commandline option "-h":
     *  empty or 0 (official), 1,2,3 for  a language specific variant.
     *  @return phrase corresponding to the denotation of the time, for example
     *  <ul>
     *  <li>German, variant "1" = western:  18:45 =&gt; "viertel vor sieben"</li>
     *  <li>German, variant "2" = southern: 18:45 =&gt; "dreiviertel sieben"</li>
     *  </ul>
     */
    public String spellClock(int hour, int minute, String variant) {
        String result = String.valueOf(hour   + 100).substring(1) + ':'
                      + String.valueOf(minute + 100).substring(1);
        int hour12 = hour + (minute >= 30 ? 1 : 0);
        hour12 = (hour12 == 0 ? 12 : (hour12 >= 13 ? hour12 - 12 : hour12));
        String spellHour = spellCardinal(String.valueOf(hour12));
        if (false) {
        } else if (variant.length() == 0 || variant.equals("0")) {
            result  = spellCardinal(String.valueOf(hour)).replace("eins", "ein")
                    + " Uhr";
            if (minute > 0) {
                result += " " +spellCardinal(String.valueOf(minute));
            }
        } else if (variant.equals("1")) { // Western Germany, north/west of a line Luebeck/Saaarbruecken (c.f. de.wikipedia: Uhrzeit)
            if (minute % 15 == 0) {
                switch (minute / 15) {
                    default:
                    case 0:
                        result  = spellHour;
                        break;
                    case 1:
                        result  = "viertel nach "   + spellHour;
                        break;
                    case 2:
                        result  = "halb "           + spellHour;
                        break;
                    case 3:
                        result  = "viertel vor "    + spellHour;
                        break;
                } // switch 0..3
            } else {
                result  = (minute < 30  ? spellCardinal(String.valueOf(     minute)) + " nach "
                                        : spellCardinal(String.valueOf(60 - minute)) + " vor ")
                        + spellHour;
            }
        } else if (variant.equals("2")) { // Southern Germany, south/east of a line Luebeck/Saaarbruecken (c.f. de.wikipedia: Uhrzeit)
            if (minute % 15 == 0) {
                switch (minute / 15) {
                    default:
                    case 0:
                        result  = spellHour;
                        break;
                    case 1:
                        hour12 ++;
                        hour12 = hour12 > 12 ? hour12 - 12 : hour12;
                        spellHour = spellCardinal(String.valueOf(hour12));
                        result  = "viertel "        + spellHour;
                        break;
                    case 2:
                        result  = "halb "           + spellHour;
                        break;
                    case 3:
                        result  = "dreiviertel "    + spellHour;
                        break;
                } // switch 0..3
            } else {
                result  = (minute < 30  ? spellCardinal(String.valueOf(     minute)) + " nach "
                                        : spellCardinal(String.valueOf(60 - minute)) + " vor ")
                        + spellHour;
            }
        } // switch variant
        return result;
    } // spellClock(3)

    //================================================================
    /** Get a word for one the 4 cardinal directions,
     *  and for the particle for 32th fractions.
     *  Cf. https://en.wikibooks.org/wiki/Lojban/Directions
     *  @param cardDir a cardinal direction, 0 = North, 1 = East, 2 = South, 3 = West
     */
    protected String getCompassWord(int cardDir) {
        String result = "";
        switch (cardDir) {
            case 0: result = ""          ; break;
            case 1: result = "berti"     ; break;
            case 2: result = "stuna"     ; break;
            case 3: result = "snanu"     ; break;
            case 4: result = "stici"     ; break;
        } // switch
        return result;
    } // getCompassWord

    /** Returns the language specific words for a cardinal direction
     *  @param code abbreviation, a sequence of the letters N,E,S,W.
     *  @param words (not used)
     *  @return word for direction
     */
    protected String spellCompassCode(String code, String words[]) {
        String result = super.spellCompassCode(code);
        if (code.length() == 4) {
            // result += "en";
        }
        return result;
    } // spellCompassCode

    /** Returns a greeting corresponding to the parameter time:
     *  @return greeting corresponding to the time of the day
     */
    public String spellGreeting(int timeOfDay) {
        String result = "coi";
        timeOfDay /= 6;
        if (timeOfDay >= 0 && timeOfDay <= 4) {
            result = (new String[]
                    { "co'o"
                    , "coi"
                    , "coi"
                    , "coi"
                    , "coi"
                    }
                    )[timeOfDay];
        } // in range
        return result;
    } // spellGreeting(int)

    //================================================================
    /** Returns a planet
     *  @param planet number of the planet (3 = earth, 0 = sun, -1 = moon)
     *  @return planet's name
     */
    public String spellPlanet(int planet) {
        String result = super.spellPlanet(planet);
        switch (planet) {
            case -1:    result = "mluni";       break;
            case 0:     result = "solri";       break;
            case 1:     result = "pavyplini";   break;
            case 2:     result = "relplini";    break;
            case 3:     result = "cibyplini";   break;
            case 4:     result = "vonplini";    break;
            case 5:     result = "mumplini";    break;
            case 6:     result = "xavyplini";   break;
            case 7:     result = "zelplini";    break;
            case 8:     result = "bivplini";    break;
        } // switch
        return result;
    } // spellPlanet(int)

    //================================================================

    /** Letters which are used as vowels */
    protected String vowels     = "aeiou";
    /** Letters which are used as consonants */
    protected String consonants = "bcdfgjklmnprstvxz";

} // DeuSpeller
