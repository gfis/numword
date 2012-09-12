/*  Spelling of numbers in Turkish (Türkçe)
    spoken in Turkey
    @(#) $Id: TurSpeller.java 521 2010-07-26 07:06:10Z gfis $
    Copyright (c) 2005 Dr. Georg Fischer <punctum@punctum.com>
    2006-01-04: weekdays and months; UTF-8 üç
    2005-06-06, Georg Fischer: copied from NorSpeller
    1990-01-01, Herbert Schmid
    ??? 10..19

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
 *  Spells numbers in Turkish (Türkce).
 *  @author Dr. Georg Fischer
 */
public class TurSpeller extends BaseSpeller {
    public final static String CVSID = "@(#) $Id: TurSpeller.java 521 2010-07-26 07:06:10Z gfis $";
    
    /**
     *  Constructor
     */
    public TurSpeller() {
        super();
        setIso639("tur,tr");
        setDescription("Turkish (Türkçe)");
        setMaxLog(true);
        setSeparator(true);

        wordN = new String[]
        { "sıfır"
        , "bir"
        , "iki"
        , "üç"
        , "dört"
        , "be\u015f"  // s,
        , "alt\u0131" // dotless i
        , "yedi"
        , "sekiz"
        , "dokuz"
        };
        wordN0 = new String[]
        { "null"
        , "on"
        , "yirmi"
        , "otuz"
        , "k\u0131rk" // dotless i
        , "elli"
        , "altmi\u015f" // s,
        , "yetmi\u015f" // s,
        , "sekzen"
        , "doksan"
        };

        setMorphem("h1", "yüs");
        setMorphem("t1", "bin");
        setMorphem("m1", "yon");
        setMorphem("m3", "yar");
        setMorphem("p0", " ");
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
                spellN(digitN00);
                append("yüs");
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
                putWord("on");
                spellN0(digitN);
                break;
            default:
                spellN0(digitN0);
                if (digitN >= 1) {
                    spellN(digitN);
                }
                break;
        } // switch digitN0
        
        if (! zeroTuple) { // append thousand, million ... */
            switch (logTuple) {
                case 0: // no thousands
                    break;
                case 1:
                    putWord("bin");
                    break;
                default:
                    spellN000(logTuple / 2 + 1);
                    if (logTuple % 2 == 0) { // even
                        append("yon");
                    }
                    else {
                        append("yar");
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
                    { "0"   // 0
                    , "Ocak"    // 1
                    , "\u015eubat"  // 2
                    , "Mart"    // 3
                    , "Nisan"   // 4
                    , "May\u0131s"  // 5
                    , "Haziran" // 6
                    , "Temmuz"  // 7
                    , "A\u011fustos"    // 8
                    , "Eyl\u00fcl"  // 9
                    , "Ekim"    // 10
                    , "Kas\u0131m"  // 11
                    , "Aral\u0131k" // 12
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
                    , "ilkbahar"
                    , "yaz"
                    , "sonbahar"
                    , "kış"
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
                    { ""    // 0
                    , "Pazartesi"   // 1
                    , "Sal\u0131"   // 2
                    , "\u00c7ar\u015famba"  // 3
                    , "Per\u015fembe"   // 4
                    , "Cuma"    // 5
                    , "Cumartesi"   // 6
                    , "Pazar"   // 7
                    })[weekDay];
        }
        return result;
    }

}
