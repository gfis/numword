/*  Spelling of numbers in  한국어 (Hangukeo) / Korean
    spoken in Korea
    @(#) $Id: KorSpeller.java 521 2010-07-26 07:06:10Z gfis $
    Copyright (c) 2006 Dr. Georg Fischer <punctum@punctum.com>
    2006-01-06: Georg Fischer, copied from JpnSpeller 
    
    caution: UTF-8 is essential! compile with "-encoding UTF-8"
    Retrieved from "http://home.unilang.org/main/wiki2/index.php/Translations:_Numbers_-_Greek"
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
import  org.teherba.numword.SinoSpeller;
/**
 *  Spells numbers in  한국어 (Hangukeo) / Korean
 *  @author Dr. Georg Fischer
 */
public class KorSpeller extends SinoSpeller {
    public final static String CVSID = "@(#) $Id: KorSpeller.java 521 2010-07-26 07:06:10Z gfis $";
    
    /**
     *  Constructor
     */
    public KorSpeller() {
        super();
        setIso639("kor,ko"); 
        setDescription("Korean");
        setMorphem("tz", "万");    // 
        setMorphem("mh", "億");    // (ichi-oku)
        setMorphem("wm", "\uc6d4"); 
        enumerateMorphems();
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
                    , "봄"
                    , "여름"
                    , "가을"
                    , "겨울"
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
                    , "월요일"   // [weol yo il] 
                    , "화요일"   // [hwa yo il] ("fire day")
                    , "수요일"   // [su yo il] ("water day")
                    , "목요일"   // [mok yo il] ("wood day")
                    , "금요일"   // [keum yo il] ("gold day")
                    , "토요일"   // [t'o yo il] ("earth day")
                    , "일요일"   // [il yo il]
                    })[weekDay];
        }
        return result;
    }

}
