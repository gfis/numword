/*  Spelling of numbers in Finnish
    spoken in Finland
    @(#) $Id: FinSpeller.java 521 2010-07-26 07:06:10Z gfis $
    Copyright (c) 2005 Dr. Georg Fischer <punctum@punctum.com>
    2006-01-01: weekdays and months; UTF-8 neljä
    2005-06-06, Georg Fischer
    
    caution: UTF-8 is essential! compile with "-encoding UTF-8"

    - quite regular
    - milliards like in German
    
from Lauri Ylönen:
Zahlen (*Lukusanoja*)
1 = yksi
2 = kaksi
3 = kolme
4 = neljä
5 = viisi
6 = kuusi
7 = seitsemän
8 = kahdeksan
9 = yhdeksän
10 = kymmenen
11 = yksitoista
12 = kaksitoista
13 = kolmetoista
14 = neljätoista
15 = viisitoista
16 = kuusitoista
17 = seitsemäntoista
18 = kahdeskantoista
19 = yhdeksäntoista
20 = kaksikymmentä
21 = kaksikymmentäyksi
22 = kaksikymmentäkaksi
23 = kaksikymmentäkolme
24 = kyksikymmentäneljä
25 = kaksikymmentäviisi
26 = kaksikymmentäkuusi
27 = kaksikymmentäseitsemän
28 = kaksikymmentäkahdeksan
29 = kaksikymmentäyhdeksän
30 = kolmekymmentä
31 = kolmekymmentäyksi
40 = neljäkymmentä
50 = viisikymmentä
60 = kuusikymmentä
70 = seitsemänkymmentä
80 = kahdeksankymmentä
90 = yhdeksänkymmentä
100 = sata
200 = kaksisataa
1000 = tuhat
100 000 = satatuhatta
1 000 000 = miljoona
5 000 000 = viisi miljoonaa
1 000 000 000 = miljardi
erster= ensimmäinen
zweiter= toinen
dritter= kolmas

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
 *  Spells numbers in Finnish
 *  @author Dr. Georg Fischer
 */
public class FinSpeller extends BaseSpeller {
    public final static String CVSID = "@(#) $Id: FinSpeller.java 521 2010-07-26 07:06:10Z gfis $";
    
    /**
     *  Constructor
     */
    public FinSpeller() {
        super();
        setIso639("fin,fi");
        setDescription("Finnish");
        setMaxLog(true);
        setSeparator(false);

        wordN = new String[]
        { "nolla"
        , "yksi"
        , "kaksi"
        , "kolme"
        , "neljä"
        , "viisi"
        , "kuusi"
        , "seitsemän"
        , "kahdeksan"
        , "yhdeksän"
        };
        wordN0 = new String[]
        { "null"
        , "kymmenen"
        , "kaksikymmentä"
        , "kolmekymmentä"
        , "neljäkymmentä"
        , "viisikymmentä"
        , "kuusikymmentä"
        , "seitsemänkymmentä"
        , "kahdeksankymmentä"
        , "yhdeksänkymmentä"
        };
        word1N = new String[]
        { "kymmenen"
        , "yksitoista"
        , "kaksitoista"
        , "kolmetoista"
        , "neljätoista"
        , "viisitoista"
        , "kuusitoista"
        , "seitsemäntoista"
        , "kahdeksantoista"
        , "yhdeksäntoista"
        };

        setMorphem("h1", "sata");
        setMorphem("h2", "sataa");
        setMorphem("t1", "tuhat");
        setMorphem("t2", "tuhatta");
        setMorphem("m1", "joonaa");
        setMorphem("m3", "jardi");
        setMorphem("p0", " ");
        setMorphem("p3", "ten"); // e.g. neljäntenätoista = 14th
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
                break;
            default:
                spellN(digitN00);
                putMorphem("h2");
                break;
        } // switch 100
        
        // tens and ones
        switch (digitN0) {
            case 0:
                if (nullOnly) {
                    spellN(0);
                }
                else 
                if (singleTuple && number.length() >= 3) {
                    // not yksituhatta, yksimiljoonaa ...
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
                    putMorphem(singleTuple ? "t1" : "t2");
                    break;
                default:
                    append(getMorphem("p0"));
                    spellN000(logTuple, getMorphem("m1"), getMorphem("m3"));
                    append(getMorphem("p0"));
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
        if (month >= 1 && month <= 12) {
            result = (new String [] 
                    { ""
                    , "tammikuu"
                    , "helmikuu"
                    , "maaliskuu"
                    , "huhtikuu"
                    , "toukokuu"
                    , "kesäkuu"
                    , "heinäkuu"
                    , "elokuu"
                    , "syyskuu"
                    , "lokakuu"
                    , "marraskuu"
                    , "joulukuu"
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
        if (season >= 1 && season <= 4) {
            result = (new String [] 
                    { "?"
                    , "kevät"
                    , "kesä"
                    , "sysky"
                    , "talvi"
                    })[season];
        }
        return result;
    }

    /**
     *  Returns the week day's name
     *  @param weekDay number of day in week, >= 0 and <= 7,
     *  1 -> Monday, 7 (and 0) -> Sunday
     *  @return word denoting the day in the week
     */
    public String spellWeekDay(int weekDay) {
        String result = Integer.toString(weekDay);
        if (weekDay >= 0 && weekDay <= 7) {
            result = (new String [] 
                    { "0"
                    , "maanantai"
                    , "tiistai"
                    , "keskiviikko"
                    , "torstai"
                    , "perjantai"
                    , "launantai"
                    , "sunnuntai"
                    })[weekDay];
        }
        return result;
    }

}
