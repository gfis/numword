/*  Spelling of numbers in Swedish (Svenska)
    spoken in Sweden, parts of Finland (?)
    @(#) $Id: SweSpeller.java 521 2010-07-26 07:06:10Z gfis $
    Copyright (c) 2005 Dr. Georg Fischer <punctum@punctum.com>
    2006-01-04: weekdays and months; UTF-8 två
    2005-06-06, Georg Fischer: copied from FinSpeller

    caution: UTF-8 is essential! compile with "-encoding UTF-8"
    
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
 *  Spells numbers in Swedish (Svenska)
 *  @author Dr. Georg Fischer
 */
public class SweSpeller extends BaseSpeller {
    public final static String CVSID = "@(#) $Id: SweSpeller.java 521 2010-07-26 07:06:10Z gfis $";
    
    /**
     *  Constructor
     */
    public SweSpeller() {
        super();
        setIso639("swe,sv");
        setDescription("Swedish (Svenska)");
        setMaxLog(true);
        setSeparator(false);

        wordN = new String[]
        { "null"
        , "ett"
        , "två"
        , "tre"
        , "fyra"
        , "fem"
        , "sex"
        , "sju"
        , "åtta"
        , "nio"
        };
        wordN0 = new String[]
        { "null"
        , "tio"
        , "tjugu"
        , "trettio"
        , "fyrtio"
        , "femtio"
        , "sextio"
        , "sjuttio"
        , "ttio"
        , "nittio"
        };
        word1N = new String[]
        { "tio"
        , "elleve"
        , "tolv"
        , "tretton"
        , "fjorton"
        , "femton"
        , "sexton"
        , "sjutton"
        , "aderton"
        , "nitton"
        };

        setMorphem("01", "en");
        setMorphem("h1", "hundra");
        setMorphem("t1", "tusen");
        setMorphem("m1", "jon");
        setMorphem("m3", "jard");
        setMorphem("p0", " ");
        setMorphem("p3", "o");
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
                putMorphem("h1");
                if (digitN0 != 0 || digitN != 0) {
                    putMorphem("p3"); // o
                }
                break;
            default:
                spellN(digitN00);
                putMorphem("h1");
                if (digitN0 != 0 || digitN != 0) {
                    putMorphem("p3"); // o
                }
                break;
        } // switch 100
        
        // tens and ones
        switch (digitN0) {
            case 0:
                if (nullOnly) {
                    spellN(0);
                }
                else
                if (digitN >= 1) {
                    spellN(digitN);
                }
                break; 
            case 1: // 10..19 with some exceptions
                spell1N(digitN);
                break;
            default:
                spellN0(digitN0);
                if (digitN >= 1 && (! singleTuple || logTuple <= 0)) {
                    spellN(digitN);
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
                    spellN000(logTuple, getMorphem("m1"),  getMorphem("m3"));
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
                    { "M\u00e5nad"  // 0
                    , "Januari" // 1
                    , "Februari"    // 2
                    , "Mars"    // 3
                    , "April"   // 4
                    , "Maj" // 5
                    , "Juni"    // 6
                    , "Juli"    // 7
                    , "Augusti" // 8
                    , "September"   // 9
                    , "Oktober" // 10
                    , "November"    // 11
                    , "December"    // 12
                    })[month];
        }
        return result;
    }

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
                    { "0"
                    , "vår"
                    , "sommar"
                    , "höst"
                    , "vinter"
                    })[season];
        }
        return result;
    }

    /**
     *  Returns the week day's name
     *  @param weekDay number of day in week, &gt;= 0 and &lt;= 7,
     *  1 -&gt; Monday, 7 -&gt; Sunday
     *  @return word denoting the day in the week
     */
    public String spellWeekDay(int weekDay) {
        String result = Integer.toString(weekDay);
        if (weekDay >= 0 && weekDay <= 7) {
            result = (new String [] 
                    { "0"
                    , "M\u00e5ndag" // 1
                    , "Tisdag"  // 2
                    , "Onsdag"  // 3
                    , "Torsdag" // 4
                    , "Fredag"  // 5
                    , "L\u00f6rdag" // 6
                    , "S\u00f6ndag" // 7
                    })[weekDay];
        }
        return result;
    }

}
