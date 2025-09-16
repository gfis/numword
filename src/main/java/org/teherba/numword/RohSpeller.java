/*  Spelling of numbers in Rumantsch (Rumantsch Grischun, Rhaeto-Romance)
    spoken in parts of central Switzerland
    @(#) $Id: RohSpeller.java 521 2010-07-26 07:06:10Z gfis $
    2005-08-23, Georg Fischer: copied from LatSpeller
    based on Wikipedia and http://www.fflch.usp.br/dlcv/lport/MViaro007.pdf
    pure ASCII
*/
/*
 * Copyright 2005 Dr. Georg Fischer <dr dot georg dot fischer at gmail dot ...>
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
 *  Spells numbers in Rhaeto-Romance (Rumantsch Grischun, 
 *  the official, but artificial language combined from 
 *  5 dialects in eastern Switzerland)
 *  @author Dr. Georg Fischer
 */
public class RohSpeller extends BaseSpeller {
    public final static String CVSID = "@(#) $Id: RohSpeller.java 521 2010-07-26 07:06:10Z gfis $";
    
    /**
     *  Constructor
     */
    public RohSpeller() {
        super();
        setIso639("roh,rm");
        setDescription("Rumantsch Grischun");
        setMaxLog(12);
        setSeparator(false);

        wordN = new String[]
        { "nulla"
        , "in" // ina
        , "dus" // duas
        , "trais"
        , "quatter"
        , "tschintg"
        , "sis"
        , "set"
        , "otg"
        , "nov"
        };
        wordN0 = new String[]
        { ""
        , "diesch"
        , "ventg"
        , "trent"
        , "quarant"
        , "tschuncant"
        , "sessant"
        , "settant"
        , "otgant"
        , "novant"
        };
        word1N = new String[]
        { "diesch"
        , "indesch"
        , "dudesch"
        , "tredesch"
        , "quattordesch"
        , "quindesch"
        , "sedesch"
        , "deschset"
        , "deschdotg"
        , "deschnov" 
        };
        wordN00 = new String[]
        { ""
        , "tschient"
        , "duatschient"
        , "traiatschient"
        , "quattertschient"
        , "tschintgtschient"
        , "sistschient"
        , "settschient"
        , "otgtschient"
        , "novtschient"
        };

        // variants for parsing 
        setMorphem("001", "ina"); 
        setMorphem("002", "dua"); 
        setMorphem("003", "traia"); 
        setMorphem("020", "ventg"); 
        setMorphem("m1", "liun");
        setMorphem("m3", "liarda");
        setMorphem("t1", "milli");
        setMorphem("p0", " ");
        setMorphem("p3", "e"); // inserted after "100"
        setMorphem("p4", "a"); // omitted before "in" and "otg"
        setMorphem("p5", "d"); // inserted between "e" and "in"/"otg"
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
                if (digitN0 * 10 + digitN > 0) { // tens and/or ones follow
                    putMorphem("p3"); // insert "e"
                }
                if (digitN == 1 || digitN == 8 || digitN0 == 8) { // starts with "in" or "otg"
                    putMorphem("p5"); // insert "d"
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
                if (digitN > 0 && (digitN != 1 || logTuple != 1)) {
                    // not "unum mille"
                    spellN(digitN);
                }
                break; 
            case 1: // 10..19 with some exceptions
                spell1N(digitN);
                break;
            default:
                switch (digitN) {
                    case 0: // append the "a" except for 20
                        spellN0(digitN0);
                        if (digitN0 != 2) {
                            putMorphem("p4");
                        }
                        break;
                    case 1:
                    case 8: // omit the "a"
                        spellN0(digitN0);
                        spellN (digitN);
                        break;
                    default:
                        spellN0(digitN0);
                        if (digitN >= 1) {
                            putMorphem("p4");
                            spellN (digitN);
                        }
                        break;
                } // switch digitN
                break;
        } // switch digitN0
        
        if (! zeroTuple) { // append thousand, million ... */
            switch (logTuple) {
                case 0: // no thousands
                    break;
                case 1:
                    modifyResult("dus"  , "dua"  );
                    modifyResult("trais", "traia");
                    putMorphem("t1");
                    break;
                default:
                    if (logTuple % 2 == 1) { // milliarda ...
                        modifyResult("in"   , "ina"  );
                        modifyResult("dus"  , "dua"  );
                        modifyResult("trais", "traia");
                    }
                    append(" ");
                    spellN000Morphem(logTuple);
                    append(" ");
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
                    { ""
                    , "schaner"
                    , "favrer"
                    , "mars"
                    , "avrigl"
                    , "matg"
                    , "zercladur"
                    , "fanadur"
                    , "avust"
                    , "Settember"
                    , "october"
                    , "november"
                    , "december"
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
                    { ""
                    , "primavaira"
                    , "stad"
                    , "atun"
                    , "enviern"
                    })[season];
        }
        return result;
    }

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
                    { ""
                    , "glindesdi"
                    , "mardi"
                    , "mesemna"
                    , "gievgia"
                    , "venderdi"
                    , "sonda"
                    , "dumengia"
                    })[weekDay];
        }
        return result;
    }

}
/*
0 nulla
1 in, ina
2 dus, duas
3 trais
4 quatter
5 tschintg
6 sis
7 set
8 otg
9 nov
10 diesch
11 indesch
12 dudesch
13 tredesch
14 quattordesch
15 quindesch
16 sedesch
17 deschset
18 deschdotg
19 deschnov
20 ventg
21 ventgin
22 ventgadus
23 ventgatrais
24 ventgaquatter
25 ventgatschintg
26 ventgasis
27 ventgaset
28 ventgotg
29 ventganov
30 trenta
31 trentin
32 trentadus
40 quaranta
50 tschuncanta
60 sessanta
70 settanta
80 otganta
90 novanta
100 tschient
101 tschientedin
102 tschientedus
200 duatschient
300 traiatschient
400 quattertschient
472 quattertschientsettantadus
1000 milli
1984 millinovtschientotgantaquatter
2000 duamilli
3000 traiamilli
10 000 dieschmilli
100 000 tschientmilli
1 000 000 in milliun
1 000 000 000 ina milliarda

Ordinais
1. emprim
2. segund
3. terz
4. quart
5. tschintgavel
emprima
quarta
segunda
terza
tschintgavla
*/
