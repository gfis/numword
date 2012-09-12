/*  Spelling of numbers in Slovak
    spoken in Slovakia, southern part of former Czech-Slovakia
    @(#) $Id: SlkSpeller.java 521 2010-07-26 07:06:10Z gfis $
    Copyright (c) 2005 Dr. Georg Fischer <punctum@punctum.com>
    2006-01-10, Georg Fischer: copied from CesSpeller
    
    caution, UTF-8 is essential! compile with -encoding UTF-8
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
 *  Spells numbers in Slovak
 *  @author Dr. Georg Fischer
 */
public class SlkSpeller extends SlavicSpeller {
    public final static String CVSID = "@(#) $Id: SlkSpeller.java 521 2010-07-26 07:06:10Z gfis $";

    /**
     *  Constructor
     */
    public SlkSpeller() {
        super();
        setIso639("slo,slk,sk");
        setDescription("Slovak");
        // setMaxLog(false);
        // setSeparator(true);
        wordN = new String[]
        { "nula" 
        , "jeden"   // (m.), jedna (f.), jedno (n.)
        , "dva"     // (m.), dvě (f., n.)
        , "tri"     // r^
        , "štyri" 
        , "päť"
        , "šesť"
        , "sedem"
        , "osem"
        , "deväť"
        };
        wordN0 = new String[]
        { "0"
        , "desať"
        , "dvadsať"
        , "tridsať"
        , "štyridsať"
        , "päťdesiat"
        , "šesťdesiat"
        , "sedemdesiat"
        , "osemdesiat"
        , "deväťdesiat"
        };
        wordN00 = new String[]
        { "0"
        , "sto"
        , "dvesto"
        , "tristo"
        , "štyristo"
        , "päťsto"
        , "šesťsto"
        , "sedemsto"
        , "osemsto"
        , "deväťsto"
        };
        word1N = new String[]
        { "desať"
        , "jedenásť"
        , "dvanásť"
        , "trinásť"
        , "pätnásť"
        , "šestnásť"
        , "sedemnásť"
        , "osemnásť"
        , "devätnásť"
        };
        
        /*
            1000 = tisíc
            2000 = dvetisíc
            10 000 = desať tisíc
            100 000 = sto tisíc

            1 000 000 = milión
            1 000 000 000 = miliarda 
        */
        setMorphem("t1", "tisíc");
        setMorphem("t2", "tisíc");
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
                    { ""            // 0
                    , "Január"     // 1
                    , "Február"        // 2
                    , "Marec"       // 3
                    , "Apríl"      // 4
                    , "Máj"            // 5
                    , "Jún"            // 6
                    , "Júl"            // 7
                    , "August"      // 8
                    , "September"   // 9
                    , "Október"        // 10
                    , "November"    // 11
                    , "December"    // 12
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
                    , "jar"
                    , "leto"
                    , "jeseň"
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
                    { ""        // 0
                    , "pondelok"// 1
                    , "utorok"  // 2
                    , "streda"  // 3
                    , "štvrtok"    // 4
                    , "piatok"  // 5
                    , "sobota"  // 6
                    , "nedeľa" // 7
                    })[weekDay];
        }
        return result;
    }

}
