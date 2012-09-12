/*  Spelling of numbers in Slovenian (Slovenščina)
    spoken in Slovenia (between Austria, Italy and Croatia)
    @(#) $Id: SlvSpeller.java 521 2010-07-26 07:06:10Z gfis $
    Copyright (c) 2005 Dr. Georg Fischer <punctum@punctum.com>
    2006-04-25: tested against <http://en.wikipedia.org/wiki/Slovenian_numerals>
    2006-01-10, Georg Fischer: copied from SlkSpeller
    
    caution, UTF-8 is essential! compile with -encoding UTF-8
    10-99 similiar to German
    similiar to Slovakian, but not derived from SlavicSpeller
    nič ena dva tri štiri pet šest sedem osem devet
    time ok
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
 *  Spells numbers in Slovenian
 *  @author Dr. Georg Fischer
 */
public class SlvSpeller extends BaseSpeller {
    public final static String CVSID = "@(#) $Id: SlvSpeller.java 521 2010-07-26 07:06:10Z gfis $";

    /**
     *  Constructor
     */
    public SlvSpeller() {
        super();
        setIso639("slv,sl");
        setDescription("Slovenian (Slovenščina)");
        setMaxLog(true);
        setSeparator(true);
        wordN = new String[]
        { "nič"    
        , "ena"     // (m.)??
        , "dva"     // (m.), ??? (f., n.)
        , "tri"     // r^
        , "štiri" 
        , "pet"
        , "šest"
        , "sedem"
        , "osem"
        , "devet"
        };
        wordN0 = new String[]
        { "0"
        , "deset"
        , "dvajset"
        , "trideset"
        , "štirideset"
        , "petdeset"
        , "šestdeset"
        , "sedemdeset"
        , "osemdeset"
        , "devetdeset"
        };
        wordN00 = new String[]
        { "0"
        , "sto"
        , "dvesto"
        , "tristo"
        , "štiristo"
        , "petsto"
        , "šeststo"
        , "sedemsto"
        , "osemsto"
        , "devetsto"
        };
        word1N = new String[]
        { "deset"
        , "enajst"
        , "dvanajst"
        , "trinajst"
        , "štirinajst" 
        , "petnajst"
        , "šestnajst"
        , "sedemnajst"
        , "osemnajst"
        , "devetnajst"
        };
        
        setMorphem("t1", "tisoč");
        setMorphem("m1", "ijon");
        setMorphem("m3", "ijard");
        setMorphem("p0", " ");
        setMorphem("p1", "a");
        setMorphem("p2", "ov");
        setMorphem("p3", "in"); // "and" between units and tens
        setMorphem("p4", "i");
        enumerateMorphems();
    }

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
                spellN00(digitN00);
                break;
        } // switch 100
        
        // tens and ones
        switch (digitN0) {
            case 0:
                if (nullOnly) {
                    spellN(0); // lonely 0
                }
                else 
                if (digitN != 0) {
                    if (digitN == 1 && logTuple < 1) {
                        // not "ena milijon"
                        spellN(digitN);
                    }
                    else
                    if (digitN > 1) {
                        spellN(digitN);
                    }
                }
                break;
            case 1: // 10..19 with some exceptions
                spell1N(digitN);
                break;
            default:
                if (digitN >= 1) {
                    spellN(digitN);
                    append(getMorphem("p3"));
                    append(wordN0[digitN0]);
                }
                else {
                    spellN0(digitN0);
                }
                break;
        } // switch digitN0
        
        if (! zeroTuple) { // append thousand, million ... */
            switch (logTuple) {
                case 0: // no thousands
                    break;
                case 1:
                    if (digitN00 + digitN0 == 0) { 
                        switch (digitN) {
                            case 0:
                                break;
                            default:
                                putWord(getMorphem("t1"));
                                break;
                        }
                            
                    } 
                    else { // >= 10
                                putWord(getMorphem("t1"));
                    }
                    break;
                default:
                    spellN000(logTuple / 2 + 1);
                    if (logTuple % 2 == 0) { // even power of 10**3, million
                        append(getMorphem("m1"));
                        if (digitN00 == 0 && digitN0 == 0) {
                            switch (digitN) {
                                case 1:
                                    break;
                                case 2:
                                    append(getMorphem("p1")); // "a"
                                    break;
                                default:
                                    append(getMorphem("p2")); // "ov"
                                    break;
                            }
                        }
                        else {
                            append(getMorphem("p2"));
                        }
                    } // odd power of 10**3, milliard
                    else {
                        append(getMorphem("m3"));
                        if (singleTuple) {
                            append(getMorphem("p1")); // "a"
                        }
                        else {
                            append(getMorphem("p4")); // "i"
                        }
                    }
                    break;
            } // switch logTuple
        } // thousands ...
    } // spellTuple

    /**
     *  Returns the month's name
     *  @param month month's number, >= 1 and <= 12
     *  @return word denoting the month
     */
    public String spellMonth(int month) {
        String result = Integer.toString(month);
        if (month >= 0 && month <= 12) {
            result = (new String [] 
                    { ""            // 0
                    , "januar"      // 1
                    , "februar"     // 2
                    , "marec"       // 3
                    , "april"       // 4
                    , "maj"         // 5
                    , "junij"       // 6
                    , "julij"       // 7
                    , "avgust"      // 8
                    , "september"   // 9
                    , "oktober"     // 10
                    , "november"    // 11
                    , "december"    // 12
                    })[month];
        }
        return result;
    }

    /**
     *  Returns the season's name
     *  @param season number of the quarter in the year:
     *  1 -> Spring, 2 -> Summer, 3 -> Autumn, 4 = Winter
     *  @return word denoting the season
     */
    public String spellSeason(int season) {
        String result = Integer.toString(season);
        if (season >= 0 && season <= 4) {
            result = (new String [] 
                    { "0"
                    , "pomlad"
                    , "poletje"
                    , "jesen"
                    , "zima"
                    })[season];
        }
        return result;
    }

    /**
     *  Returns the week day's name
     *  @param weekDay number of day in week, >= 0 and <= 7,
     *  1 -> Monday, 7 -> Sunday
     *  @return word denoting the day in the week
     */
    public String spellWeekDay(int weekDay) {
        String result = Integer.toString(weekDay);
        if (weekDay >= 0 && weekDay <= 7) {
            result = (new String [] 
                    { ""            // 0
                    , "ponedeljek"  // 1
                    , "torek"       // 2
                    , "sreda"       // 3
                    , "četrtek"        // 4
                    , "petek"       // 5
                    , "sobota"      // 6
                    , "nedelja"     // 7
                    })[weekDay];
        }
        return result;
    }

}
