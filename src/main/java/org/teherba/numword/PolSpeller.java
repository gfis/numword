/*  Spelling of numbers in Polish (Polski)
    spoken in Poland, eastern border of Germany
    @(#) $Id: PolSpeller.java 521 2010-07-26 07:06:10Z gfis $
    Copyright (c) 2006 Dr. Georg Fischer <punctum@punctum.com>
    2006-01-04, Georg Fischer: from iCalendar; UTF-8 "pięć"
    
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
import  org.teherba.numword.SlavicSpeller;
/**
 *  Spells numbers in Polish (Polski)
 *  @author Dr. Georg Fischer
 */
public class PolSpeller extends SlavicSpeller {
    public final static String CVSID = "@(#) $Id: PolSpeller.java 521 2010-07-26 07:06:10Z gfis $";

    /**
     *  Constructor
     */
    public PolSpeller() {
        super();
        setIso639("pol,pl");
        setDescription("Polish (Polski)");
        // setMaxLog(7);
        // setSeparator(true);
        wordN = new String[]
        { "zero" 
        , "jeden"
        , "dwa"
        , "trzy" 
        , "cztery" 
        , "pięć"
        , "sześć"
        , "siedem"
        , "osiem"
        , "dziewięć"
        };
        wordN0 = new String[]
        { "zero"
        , "dziesięć"
        , "dwadzieścia"
        , "trzydzieści" 
        , "czterdzieści" 
        , "pięćdziesiąt"
        , "sześćdziesiąt"
        , "siedemdziesiąt"
        , "osiemdziesiąt"
        , "dziewięćdziesiat"
        };
        wordN00 = new String[]
        { "zero"
        , "sto"
        , "dwieście"
        , "trzysta" 
        , "czterysta" 
        , "pięćset"
        , "sześćset"
        , "siedemset"
        , "osiemset"
        , "dziewięćset"
        };
        word1N = new String[]
        { "dziesięć"
        , "jedenaście"
        , "dwanaście"
        , "trzynaście" 
        , "czternaście" 
        , "piętnaście"
        , "szesnaście"
        , "siedemnaście"
        , "osiemnaście"
        , "dziewiętnaście"
        };
        /*
            * 1000 = tysiąc
            * 2000 = dwa tysiące
            * 10 000 = dziesięć tysięcy
            * 100 000 = sto tysięcy
            * 1 000 000 = milion
        */
        setMorphem("t1", "tysiąc");
        setMorphem("t2", "tysiące");
        setMorphem("t3", "tysięcy");
        setMorphem("m1", "ion");
        setMorphem("m3", "iarda"); // ???
        setMorphem("p0", " ");
        enumerateMorphems();
        // "li" must be set after 'enumerateMorphems'
        removeMorphem("e02");
        setMorphem("l1", "milion");
        setMorphem("l3", "miliarda");
    }
    
    /**
     *  Returns the month's name
     *  @param month month's number, &gt;= 1 and &lt;= 12
     *  @return word denoting the month
     */
    public String spellMonth(int month) {
        String result = Integer.toString(month);
        if (month >= 0 && month <= 12) {
            result = (new String [] 
                    { "miesi\u0105c"    // 0
                    , "stycze\u0144"    // 1
                    , "luty"            // 2
                    , "marzec"          // 3
                    , "kwiecie\u0144"   // 4
                    , "maj"             // 5
                    , "czerwiec"        // 6
                    , "lipiec"          // 7
                    , "sierpie\u0144"   // 8
                    , "wrzesie\u0144"   // 9
                    , "pa\u017adziernik"// 10
                    , "listopad"        // 11
                    , "grudzie\u0144"   // 12
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
                    , "wiosna"
                    , "lato"
                    , "jesień"
                    , "zima"
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
                    { ""    // 0
                    , "poniedzia\u0142ek"   // 1
                    , "wtorek"      // 2
                    , "\u015aroda"  // 3
                    , "czwartek"    // 4
                    , "pi\u0105tek" // 5
                    , "sobota"      // 6
                    , "niedziela"   // 7
                    })[weekDay];
        }
        return result;
    }

}
