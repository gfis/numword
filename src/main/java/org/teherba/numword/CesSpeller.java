/*  Spelling of numbers in Czech (čeština)
    spoken in Czechia
    @(#) $Id: CesSpeller.java 521 2010-07-26 07:06:10Z gfis $
    Copyright (c) 2005 Dr. Georg Fischer <punctum@punctum.com>
    2006-01-01: weekdays and months; UTF-8 "čtyři"
    2005-06-08, Georg Fischer
    
    caution, UTF-8 is essential! compile with -encoding UTF-8
    - incomplete if >= 10**6
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
import  org.teherba.numword.SlavicSpeller;
/**
 *  Spells numbers in Czech (čeština)
 *  @author Dr. Georg Fischer
 */
public class CesSpeller extends SlavicSpeller {
    public final static String CVSID = "@(#) $Id: CesSpeller.java 521 2010-07-26 07:06:10Z gfis $";

    /**
     *  Constructor
     */
    public CesSpeller() {
        super();
        setIso639("cze,ces,cs");
        setDescription("Czech (čeština)");
        // setMaxLog(false);
        // setSeparator(true);
        wordN = new String[]
        { "nula" 
        , "jeden"   // (m.), jedna (f.), jedno (n.)
        , "dva"     // (m.), dvě (f., n.)
        , "tři"    // r^
        , "čtyři" 
        , "pět"
        , "šest"
        , "sedm"
        , "osm"
        , "devět"
        };
        wordN0 = new String[]
        { "0"
        , "deset"
        , "dvacet"
        , "třicet" 
        , "čtyřicet" 
        , "padesát"
        , "šedesát"
        , "sedmdesát"
        , "osmdesát"
        , "devědesát"
        };
        wordN00 = new String[]
        { "0"
        , "sto"
        , "dvěstě"
        , "třista" 
        , "čtyřista" 
        , "pětset"
        , "šestset"
        , "sedmset"
        , "osmset"
        , "devětset"
        };
        word1N = new String[]
        { "deset"
        , "jedenáct"
        , "dvanáct"
        , "třináct"
        , "čtrnáct"
        , "patnáct"
        , "šestnáct"
        , "sedmnáct"
        , "osmnáct"
        , "devatenáct"
        };
        
        /*
            1000 = tisíc
            2000 = dva tisíce
            5000 = pět tisíc
            10 000 = deset tisíc
            100 000 = sto tisíc

            1 000 000 = milión
            1 000 000 000 = miliarda 
        */
        setMorphem("t1", "tisíc");
        setMorphem("t2", "tisíce");
        setMorphem("t3", "tisíc");
        setMorphem("m1", "ión");
        setMorphem("m3", "iarda"); // ???
        setMorphem("p0", " ");
        enumerateMorphems();
    }
    
    /**
     *  Returns the month's name
     *  @param month month's number, >= 1 and <= 12
     *  @return word denoting the month
     */
    public String spellMonth(int month) {
        String result = Integer.toString(month);
        if (month >= 0 && month <= 12) {
            result = (new String [] 
                    { "M\u011bs\u00edc" // 0
                    , "leden"   // 1
                    , "\u00fanor"   // 2
                    , "b\u0159ezen" // 3
                    , "duben"   // 4
                    , "kv\u011bten" // 5
                    , "\u010derven" // 6
                    , "\u010dervenec"   // 7
                    , "srpen"   // 8
                    , "z\u00e1\u0159\u00ed" // 9
                    , "\u0159\u00edjen" // 10
                    , "listopad"    // 11
                    , "prosinec"    // 12
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
                    , "jaro"
                    , "léto"
                    , "podzim"
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
                    { ""    // 0
                    , "pond\u011bl\u00ed"   // 1
                    , "\u00fater\u00fd" // 2
                    , "st\u0159eda" // 3
                    , "\u010dtvrtek"    // 4
                    , "p\u00e1tek"  // 5
                    , "sobota"  // 6
                    , "ned\u011ble" // 7
                    })[weekDay];
        }
        return result;
    }

}
