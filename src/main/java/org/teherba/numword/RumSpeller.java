/*  Spelling of numbers in Romanian (Română)
    spoken in Romania in eastern Europe
    @(#) $Id: RumSpeller.java 521 2010-07-26 07:06:10Z gfis $
    Copyright (c) 2006 Dr. Georg Fischer <punctum@punctum.com>
    2006-09-03: 11 ... 10**9-1
    2006-01-13, Georg Fischer: copied from ItaSpeller

    caution: UTF-8 is essential! compile with "-encoding UTF-8"
    - No separator, except before and after words for 10**6
    very incomplete, >= 11 wrong, same as italian
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
 *  Spells numbers in Romanian (Română)
 *  @author Dr. Georg Fischer
 */
public class RumSpeller extends BaseSpeller {
    public final static String CVSID = "@(#) $Id: RumSpeller.java 521 2010-07-26 07:06:10Z gfis $";

    /**
     *  Constructor
     */
    public RumSpeller() {
        super();
        setIso639("ron,ro,rum");
        setDescription("Romanian (Română)");
        setMaxLog(9);
        setSeparator(true);

        wordN = new String[]
        { "zero"
        , "unu"
        , "doi"
        , "trei"
        , "patru"
        , "cinci"
        , "şase"
        , "şapte"
        , "opt"
        , "nouã"
        };
        wordN0 = new String[]
        { ""
        , "zece"
        , "douăzeci"
        , "treizeci"
        , "patruzeci"
        , "cincizeci"
        , "şaizeci"
        , "şaptezeci"
        , "optzeci"
        , "nouăzeci"
        };
        word1N = new String[]
        { "zece"
        , "unsprezece"
        , "doisprezece"
        , "treisprezece"
        , "paisprezece"
        , "cinsprezece"
        , "şaisprezece"
        , "şaptesprezece"
        , "optsprezece"
        , "nouăsprezece"
        };
/*

        * douăzeci (douăzeci şi unu, douăzeci şi una)
         
        zero                 
unu     unsprezece      douăzeci şi unu   o sută
doi     doisprezece     douăzeci   douăzeci şi doi   două  sute
trei    treisprezece    treizeci    douăzeci şi trei  trei sute
patru   paisprezece     patruzeci       patru sute
cinci   cin(ci)sprezece     cin(ci)zeci         cinci sute
şase   şaisprezece    şaizeci        şase sute
şapte  şaptesprezece  şaptezeci      şapte sute
opt     optsprezece     optzeci         opt sute
nouă   nouăsprezece   nouăzeci       nouă sute
zece        o sută     o mie, două  mii   un milion,două  milioane

*/

        // tricky: leading zero makes alternate key, but yields same numerical value
        setMorphem("01",    "o");
        setMorphem("001",   "un");
        setMorphem("0001",  "una"); // for 'parse' function
        setMorphem("02",    "două");
        setMorphem("h1",    "sută");
        setMorphem("h2",    "sute");
        setMorphem("t1",    "mie");
        setMorphem("t2",    "mii");
        setMorphem("m1",    "ion");
        setMorphem("m2",    "ioane");
        setMorphem("p0",    " ");
        setMorphem("p3",    "şi");
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
            case 1:
                putMorphem("01");
                putMorphem("h1");
                break;
            case 2:
                putMorphem("02");
                putMorphem("h2");
                break;
            default:
                putWord(wordN[digitN00]);
                putMorphem("h2");
                break;
        } // switch 100
        if (digitN00 > 0 && (digitN0 > 0 || digitN > 0)) {
            putMorphem("p3");
        }
        
        // put tens and ones
        switch (digitN0) {
            case 0: // 0-9
                if (nullOnly) {
                    putWord(wordN[0]); // lonely 0
                }
                else {
                    switch (digitN) {
                        case 0:
                            break;
                        case 1: // triple of "x01"
                            switch (logTuple) {
                                case 0:
                                    putWord(wordN[digitN]);
                                    break;
                                case 1:
                                    putMorphem("01"); // o mie
                                    break;
                                default:
                                    putMorphem("001"); // un milion
                                    break;
                            }
                            break;
                        case 2: // triple of "x02"
                            switch (logTuple) {
                                case 0:
                                    putWord(wordN[digitN]);
                                    break;
                                default:
                                    putWord(getMorphem("02")); // două mii, milioane
                                    break;
                            }
                            break;
                        default:
                            putWord(wordN[digitN]);
                            break;
                    } // switch digitN
                } // not nullOnly
                break; 
            case 1: // 10-19
                putWord(word1N[digitN]);
                break;
            default:
                putWord(wordN0[digitN0]);
                switch (digitN) {
                    case 0:
                        break;
                    default:
                        putMorphem("p3");
                        putWord(wordN[digitN]);
                        break;
                }
                break;
        } // switch digitN0
        
        if (! zeroTuple) { // append thousand, million ... */
            switch (logTuple) {
                case 0: // no thousands
                    break;
                case 1:
                    putWord((singleTuple) ? getMorphem("t1") : getMorphem("t2"));
                    break;
                default:
                    spellN000(logTuple);
                    append( (singleTuple) ? getMorphem("m1") : getMorphem("m2"));
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
                    { "0"           // 0
                    , "ianuarie"    // 1
                    , "februarie"   // 2
                    , "martie"      // 3
                    , "aprilie"     // 4
                    , "mai"         // 5
                    , "iunie"       // 6
                    , "julie"       // 7
                    , "august"      // 8
                    , "septembrie"  // 9
                    , "octombrie"   // 10
                    , "noiembrie"   // 11
                    , "decembrie"   // 12
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
                    , "primăvară"
                    , "vară"
                    , "toamnă"
                    , "iarnă"
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
                    { "0"
                    , "luni"        // 1
                    , "marţi"      // 2
                    , "miercuri"    // 3
                    , "joi"         // 4
                    , "vineri'"     // 5
                    , "sâmbătă"      // 6
                    , "duminică"   // 7
                    })[weekDay];
        }
        return result;
    }

}
