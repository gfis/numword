/*  Spelling of numbers in Irish (Gaeilge)
    spoken in Ireland
    @(#) $Id: GleSpeller.java 521 2010-07-26 07:06:10Z gfis $
    Copyright (c) 2005 Dr. Georg Fischer <punctum@punctum.com>
    2006-04-15: month names from <http://eo.wikipedia.org/wiki/Monato>
    2006-01-04: weekdays and months; UTF-8 "cúig"
    2005-06-06, Georg Fischer: copied from NorSpeller
    
    caution: UTF-8 is essential! compile with "-encoding UTF-8"

    - from http://home.unilang.org/main/wiki2/index.php/Translations:_Numbers_-_Irish
    - In another book, the Words for numbers >= 10 
        "surround" the nomen to be counted,
        the tens are noted behind the nomen.
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
 *  Spells numbers in Irish (Gaeilge)
 *  @author Dr. Georg Fischer
 */
public class GleSpeller extends BaseSpeller {
    public final static String CVSID = "@(#) $Id: GleSpeller.java 521 2010-07-26 07:06:10Z gfis $";
    
    /**
     *  Constructor
     */
    public GleSpeller() {
        super();
        setIso639("gle,ga,ir"); // 'ga' also
        setDescription("Irish (Gaeilge)");
        setMaxLog(true);
        setSeparator(true);

        wordN = new String[]
        { "náid"
        , "aon"
        , "dó" //"dhá" ???
        , "trí"
        , "ceathair" // "ceithre" ???
        , "cúig" 
        , "sé"
        , "seacht"
        , "ocht"
        , "naoi"
        };
        wordN0 = new String[]
        { "null"
        , "deich"
        , "fiche"
        , "tríocha"
        , "daichead"
        , "caoga"
        , "seasca"
        , "seachtó"
        , "ochtó"
        , "nócha"
        };

        setMorphem("h1", "céad");
        setMorphem("t1", "míle");
        setMorphem("m1", "líun");
        setMorphem("p0", " ");
        setMorphem("p2", "a");
        setMorphem("p3", "is");
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
                if (digitN00 > 1) {
                    spellN(digitN00);
                }
                putWord("céad");
                if (digitN0 > 0 || digitN > 0) {
                    putWord("is a");
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
            case 1: // 10..19
                if (digitN == 0) {
                    putWord("deich");
                }
                else {
                    spellN(digitN);
                    // putWord("...");
                    putWord("déag");
                }
                break; 
            default:
                spellN0(digitN0);
                if (digitN >= 1) {
                    spellN(digitN);
                    // putWord("..."); // ones before tens ???
                    // putWord("is");
                }
                break;
        } // switch digitN0
        
        switch (logTuple) {
            case 0: // no thousands
                break;
            case 1:
                if (! zeroTuple) {
                    putWord("míle");
                }
                break;
            default:
                if (logTuple % 2 == 0) { // even, 10**(6*N)
                    if (! previousZeroTuple || ! zeroTuple) {
                        spellN000(logTuple / 2 + 1);
                        append("líun");
                    }
                }
                else { // odd, 10**(6*N+3)
                    if (! zeroTuple) {
                        putWord("míle");
                    }
                }
                break;
        } // switch logTuple
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
                    , "Enáir"  // 1                
                    , "Feabhra" // 2
                    , "An Márta"   // 3
                    , "An t-Aibreán"   // 4
                    , "An Bhealtaine"   // 5
                    , "An Meitheamh"    // 6
                    , "Iúil"   // 7
                    , "Lúnasa" // 8
                    , "Meán Fómhair"  // 9
                    , "Deireadh Fómhair"   // 10
                    , "Samhain" // 11
                    , "Nollaig" // 12
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
                    , "earrach"             
                    , "samhradh"
                    , "fómhar"
                    , "geimhreadh"
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
                    , "Luan"        // 1
                    , "Máirt"      // 2
                    , "Céadaoin"   // 3
                    , "Déardaoin"  // 4
                    , "Aoine"       // 5
                    , "Satharn"     // 6
                    , "Domhnach"    // 7
                    })[weekDay];
        }
        return result;
    }


}
