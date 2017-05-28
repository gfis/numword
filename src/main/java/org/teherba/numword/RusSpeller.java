/*  Russian (Русский)
    spoken in Russia, former member countries of the USSR, and in Eastern Europe
    @(#) $Id: RusSpeller.java 521 2010-07-26 07:06:10Z gfis $
    Copyright (c) 2006 Dr. Georg Fischer <punctum@punctum.com>
    2006-01-01, Georg Fischer: from iCalendar

    caution: UTF-8 is essential! compile with "-encoding UTF-8"
    writing of billion, trillion ... unsure
    
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
 *  Spells numbers in Russian (Русский)
 *  @author Dr. Georg Fischer
 */
public class RusSpeller extends SlavicSpeller {
    public final static String CVSID = "@(#) $Id: RusSpeller.java 521 2010-07-26 07:06:10Z gfis $";

    /**
     *  Constructor
     */
    public RusSpeller() {
        super();
        setIso639("rus,ru");
        setDescription("Russian (Русский)");
        setMaxLog(6 * 5); // keep in sync with array in redefined 'spellN000' below
        setSeparator(true);
        
        wordN = new String[]
        { "ноль" 
        , "один"    // (m.), одна (f.), одно (n.)
        , "два"      // (m., n.), две (f.)
        , "три" 
        , "четыре" 
        , "пять"
        , "шесть"  // 6
        , "семь"
        , "восемь"
        , "девять"
        };
        wordN0 = new String[]
        { "0"
        , "десять"
        , "двадцать"
        , "тридцать"
        , "сорок"
        , "пятьдесят" // 50
        , "шестьдесят"
        , "семьдесят"
        , "восемьдесят"
        , "девяносто"
        };
        wordN00 = new String[]
        { "0"
        , "сто"          // 100
        , "двести"        // 200
        , "триста"        // 300
        , "четыреста"
        , "пятьсот"      // 500
        , "шестьсот"
        , "семьсот"
        , "восемьсот"
        , "девятьсот"
        };
        word1N = new String[]
        { "десять"
        , "одиннадцать"
        , "двенадцать"
        , "тринадцать"
        , "четырнадцать"
        , "пятнадцать"    // 15
        , "шестнадцать"
        , "семнадцать"
        , "восемнадцать"
        , "девятнадцать"
        };
    
        // alternate spellings
        setMorphem("02"     , "две");
        
        /*
         * 1000 = тысяча
         * 2000 = две тысячи
         * 5000 = пять тысяч
         * 10 000 = десять тысяч
         * 100 000 = сто тысяч
         * 1 000 000 = миллион
        */
        setMorphem("t1", "тысяча");
        setMorphem("t2", "тысячи");
        setMorphem("t3", "тысяч");
        setMorphem("m1", "лион");
        setMorphem("m3", "лиярд"); // ???
        enumerateMorphems();
    }
    
    /**
     *  Appends the base word for 10**3
     *  @param log1000 logarithm of 1000, = 2, 3, 4 ...
     */
    protected void spellN000(int log1000) {
        putWord(new String []
        { ""
        , ""
        , "мил"
        , "вил"
        , "трил"
        , "квадмрил"
        }[log1000]);
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
                    { "0"   // 0
                    , "\u044f\u043d\u0432\u0430\u0440\u044c"    // 1
                    , "\u0444\u0435\u0432\u0440\u0430\u043b\u044c"  // 2
                    , "\u043c\u0430\u0440\u0442"    // 3
                    , "\u0430\u043f\u0440\u0435\u043b\u044c"    // 4
                    , "\u043c\u0430\u0439"  // 5
                    , "\u0438\u044e\u043d\u044c"    // 6
                    , "\u0438\u044e\u043b\u044c"    // 7
                    , "\u0430\u0432\u0433\u0443\u0441\u0442"    // 8
                    , "\u0441\u0435\u043d\u0442\u044f\u0431\u0440\u044c"    // 9
                    , "\u043e\u043a\u0442\u044f\u0431\u0440\u044c"  // 10
                    , "\u043d\u043e\u044f\u0431\u0440\u044c"    // 11
                    , "\u0434\u0435\u043a\u0430\u0431\u0440\u044c"  // 12
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
                    , "весна"
                    , "лето"
                    , "осень"
                    , "зима"
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
                    , "\u043f\u043e\u043d\u0435\u0434\u0435\u043b\u044c\u043d\u0438\u043a"  // 1
                    , "\u0432\u0442\u043e\u0440\u043d\u0438\u043a"  // 2
                    , "\u0441\u0440\u0435\u0434\u0430"  // 3
                    , "\u0447\u0435\u0442\u0432\u0435\u0440\u0433"  // 4
                    , "\u043f\u044f\u0442\u043d\u0438\u0446\u0430"  // 5
                    , "\u0441\u0443\u0431\u0431\u043e\u0442\u0430"  // 6
                    , "\u0432\u043e\u0441\u043a\u0440\u0435\u0441\u0435\u043d\u044c\u0435"  // 7
                    })[weekDay];
        }
        return result;
    }

}
