/*  Spelling of numbers in Iceland (íslenska)
    spoken in Iceland, parts of Canada (Manitoba) 
    @(#) $Id: IceSpeller.java 521 2010-07-26 07:06:10Z gfis $
    2006-01-07: copied from NorSpeller
    
    caution: UTF-8 is essential! compile with "-encoding UTF-8"

    c.f. <http://hem.passagen.se/peter9/gram/g_num.html>
    http://de.wikipedia.org/wiki/Zahlen_in_unterschiedlichen_Sprachen
    http://is.wiktionary.org/wiki/Wikior%C3%B0ab%C3%B3k:%C3%8Dslenska/N%C3%BAmer

    no proper declination for <= 4
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
 *  Spells numbers in Iceland (íslenska)
 *  @author Dr. Georg Fischer
 */
public class IceSpeller extends BaseSpeller {
    public final static String CVSID = "@(#) $Id: IceSpeller.java 521 2010-07-26 07:06:10Z gfis $";
    
    /**
     *  Constructor
     */
    public IceSpeller() {
        super();
        setIso639("ice,isl,is");
        setDescription("Icelandic (íslenska)");
        setMaxLog(true);
        setSeparator(true);

        wordN = new String[]
        { "núll"
        , "einn"
        , "tveir"
        , "þrír"
        , "fjórir"
        , "fimm"
        , "sex"
        , "sjö"
        , "átta"
        , "níu"
        };
        wordN0 = new String[]
        { "null"
        , "tíu"
        , "tuttugu"
        , "þrjátíu"
        , "fjórutíu"
        , "fimmtíu"
        , "sextíu"
        , "sjautíu"
        , "áttatíu"
        , "níutíu"
        };
        word1N = new String[]
        { "tíu"
        , "ellefu"
        , "tólf"
        , "þréttán"
        , "fjórtán"
        , "fimmtán"
        , "sextán"
        , "sjautján"
        , "átján"
        , "nítján"
        };
        
        setMorphem("01", "ein");
        setMorphem("h1", "hundrað");
        setMorphem("t1", "þúsund");
        setMorphem("m1", "jón");
        setMorphem("m3", "jarður");
        setMorphem("p0", " ");
        setMorphem("p3", "og");
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
                    putMorphem("p3");
                }
                break;
            default:
                spellN(digitN00);
                putWord("hundre");
                if (digitN0 != 0 || digitN != 0) {
                    putMorphem("p3");
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
                    spellN000(logTuple, getMorphem("m1"), getMorphem("m3"));
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
                    , "janúar" // 1
                    , "febrúar"    // 2
                    , "mars"    // 3
                    , "apríl"  // 4
                    , "maí"    // 5
                    , "júní"  // 6
                    , "júlí"  // 7
                    , "ágúst" // 8
                    , "september"   // 9
                    , "október"    // 10
                    , "nóvember"   // 11
                    , "desember"    // 12
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
                    , "vor"
                    , "sumar"
                    , "haust"
                    , "vetur"
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
                    , "Mánudagur"  // 1
                    , "Þriðjudagur"   // 2
                    , "Miðvikudagur"   // 3
                    , "Fimmtudagur" // 4
                    , "Föstudagur" // 5
                    , "Laugardagur" // 6
                    , "Sunnudagur"  // 7
                    })[weekDay];
        }
        return result;
    }

}
