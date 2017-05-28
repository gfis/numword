/*  Spelling of numbers in modern English,
    spoken in USA, Great Britain, Australia,
    parts of Canada, former English colonies
    and throughout the world as "lingua franca"
    @(#) $Id: EngSpeller.java 820 2011-11-07 21:59:07Z gfis $
    2016-01-18: set m3="NO_LIARDS" for US counting: million, billion ...
    2011-10-26: spellClock
    2009-11-24: spellGreeting
    2006-01-06: super()
    2005-07-23: with word parsing
    2005-06-01, Georg Fischer
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
/**
 *  Spells numbers in English
 *  @author Dr. Georg Fischer
 */
public class EngSpeller extends BaseSpeller {
    public final static String CVSID = "@(#) $Id: EngSpeller.java 820 2011-11-07 21:59:07Z gfis $";

    /**
     *  Constructor
     */
    public EngSpeller() {
        super();
        setIso639("eng,en");
        setDescription("English");
        setMaxLog(false);
        setSeparator(true);

        wordN = new String[]
        { "zero"
        , "one"
        , "two"
        , "three"
        , "four"
        , "five"
        , "six"
        , "seven"
        , "eight"
        , "nine"
        };
        wordN0 = new String[]
        { ""
        , "ten"
        , "twenty"
        , "thirty"
        , "forty"
        , "fifty"
        , "sixty"
        , "seventy"
        , "eighty"
        , "ninety"
        };

        word1N = new String[]
        { "ten"
        , "eleven"
        , "twelve"
        , "thirteen"
        , "fourteen"
        , "fifteen"
        , "sixteen"
        , "seventeen"
        , "eighteen"
        , "nineteen"
        };

        setMorphem("h1", "hundred");
        setMorphem("t1", "thousand");
        setMorphem("m1", "lion");
        setMorphem("m2", "lions");
        setMorphem("m3", "NO_LIARDS"); // US counting: millions, billions ... (thanks to Nino Svonja <nino@lumanetix.com>)
        setMorphem("p0", " ");
        setMorphem("p1", "-");
        setMorphem("p2", "s");
        setMorphem("p3", "and");
        enumerateMorphems();
    } // Constructor

    /**
     *  Appends the wording for a triple of digits,
     *  plus the remaining power of 1000
     *  @param number the remaining part of the whole number
     */
    public void spellTuple(String number) {
        // hundreds
        switch (digitN00) {
            case 0:
                break;
            default:
                spellN(digitN00);
                putMorphem("h1");
                if (digitN0 != 0 || digitN != 0) {
                    putMorphem("p3");
                }
                break;
        } // switch 100

        // tens and ones
        switch (digitN0) {
            case 0:
                if (nullOnly) {
                    spellN(0); // lonely 0
                }
                else
                if (digitN > 0) {
                    spellN(digitN);
                }
                break;
            case 1:
                spell1N(digitN);
                break;
            default:
                spellN0(digitN0);
                if (digitN >= 1) {
                    append(getMorphem("p1")); // "-"
                    append(wordN[digitN]);
                }
                break;
        } // switch digitN0

        if (! zeroTuple) { // append thousand, million ... */
            switch (logTuple) {
                case 0: // no thousands
                    break;
                case 1:
                    putMorphem("t1");
                    break;
                default:
                    spellN000(logTuple);
                    append(getMorphem("m1")); // lion
                    if (! singleTuple) {
                        append(getMorphem("p2")); // two million"s"
                    }
                    break;
            } // switch logTuple
        } // thousands ...
    } // spellTuple

    /**
     *  Returns the month's name
     *  @param month month's number, &gt;= 1 and &lt;= 12
     *  @return word denoting the month
     */
    public String spellMonth(int month) {
        String result = Integer.toString(month);
        if (month >= 0 && month <= 12) {
            result = (new String []
                    { "Month"
                    , "January"
                    , "February"
                    , "March"
                    , "April"
                    , "May"
                    , "June"
                    , "July"
                    , "August"
                    , "September"
                    , "October"
                    , "November"
                    , "December"
                    })[month];
        }
        return result;
    } // SpellMonth

    /**
     *  Returns the season's name
     *  @param season number of the quarter in the year:
     *  1 -&gt; Spring, 2 -&gt; Summer, 3 -&gt; Autumn, 4 = Winter
     *  @return word denoting the season
     */
    public String spellSeason(int season) {
        String result = Integer.toString(season);
        if (season >= 0 && season <= 4) {
            result = (new String []
                    { "Season"
                    , "Spring"
                    , "Summer"
                    , "Autumn"
                    , "Winter"
                    })[season];
        }
        return result;
    } // spellSeason

    /**
     *  Returns the week day's name
     *  @param weekDay number of day in week, &gt;= 0 and &lt;= 7,
     *  1 -&gt; Monday, 7 (and 0) -&gt; Sunday
     *  @return word denoting the day in the week
     */
    public String spellWeekDay(int weekDay) {
        String result = Integer.toString(weekDay);
        if (weekDay >= 0 && weekDay <= 7) {
            result = (new String []
                    { "Weekday"
                    , "Monday"
                    , "Tuesday"
                    , "Wednesday"
                    , "Thursday"
                    , "Friday"
                    , "Saturday"
                    , "Sunday"
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
     *  <li>English, variant "1": 18:15 =&gt; "quarter past six"</li>
     *  </ul>
     */
    public String spellClock(int hour, int minute, String variant) {
        String result = String.valueOf(hour   + 100).substring(1) + ':'
                      + String.valueOf(minute + 100).substring(1);
        int hour12 = hour + (minute > 30 ? 1 : 0);
        hour12 = (hour12 == 0 ? 12 : (hour12 >= 13 ? hour12 - 12 : hour12));
        String spellHour = spellCardinal(String.valueOf(hour12));
        if (false) {
        } else if (variant.length() == 0 || variant.equals("0")) {
            result  = spellCardinal(String.valueOf(hour)).replace("zero", "twelve")
                    + " o'clock ";
            if (minute > 0) {
                result += " " +spellCardinal(String.valueOf(minute));
            }
        } else if (variant.equals("1")) {
            if (minute % 15 == 0) {
                switch (minute / 15) {
                    default:
                    case 0:
                        if (hour == 24) {
                            result = "midnight";
                        } else {
                            result  = spellHour + " o'clock";
                        }
                        break;
                    case 1:
                        result  = "quarter past "   + spellHour;
                        break;
                    case 2:
                        result  = "half past "      + spellHour;
                        break;
                    case 3:
                        result  = "quarter to "     + spellHour;
                        break;
                } // switch 0..3
            } else {
                result  = (minute < 30  ? spellCardinal(String.valueOf(     minute)) + " past "
                                        : spellCardinal(String.valueOf(60 - minute)) + " to ")
                        + spellHour;
            }
        } // switch variant
        return result;
    } // spellClock(3)

    //================================================================
    /** Get a word for one the 4 cardinal directions,
     *  and for the particle for 32th fractions
     *  @param cardDir a cardinal direction, 0 = North, 1 = East, 2 = South, 3 = West
     */
    protected String getCompassWord(int cardDir) {
        String result = "";
        switch (cardDir) {
            case 0: result = "north"    ; break;
            case 1: result = "east"     ; break;
            case 2: result = "south"    ; break;
            case 3: result = "west"     ; break;
            case 4: result = "to"       ; break;
        } // switch
        return result;
    } // getCompassWord

    //================================================================
    /** Returns a greeting corresponding to the parameter time:
     *  @return greeting corresponding to the time of the day
     */
    public String spellGreeting(int timeOfDay) {
        String result = "Hello";
        timeOfDay /= 6;
        if (timeOfDay >= 0 && timeOfDay <= 4) {
            result = (new String[]
                    { "Good bye"
                    , "Good morning"
                    , "Hello"
                    , "Good evening"
                    , "Good night"
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
            case -1:    result = "Moon";        break;
            case 0:     result = "Sun";         break;
            case 1:     result = "Mercury";     break;
            case 3:     result = "Earth";       break;
        } // switch
        return result;
    } // spellPlanet(int)

    //================================================================
} // EngSpeller
