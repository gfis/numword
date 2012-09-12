/*  Spelling of numbers in Chinese 中文 (zhōngwén) simplified
    spoken in China 六
    and other parts of Asia
    @(#) $Id: ChiSpeller.java 521 2010-07-26 07:06:10Z gfis $
    Copyright (c) 2006 Dr. Georg Fischer <punctum@punctum.com>
    2006-01-06: Georg Fischer, copied from GreSpeller 
    
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
 *  Spells numbers in Chinese
 *  @author Dr. Georg Fischer
 */
public class ChiSpeller extends SinoSpeller {
    public final static String CVSID = "@(#) $Id: ChiSpeller.java 521 2010-07-26 07:06:10Z gfis $";
    
    /**
     *  Constructor
     */
    public ChiSpeller() {
        super();
        setIso639("chi,zho,zh"); 
        setDescription("Chinese 中文 (zhōngwén)");
        setMorphem("tz", "万");    // (yī-wàn)
        setMorphem("mh", "亿");    // (yī-yì)
        setMorphem("wm", "月");    // yuè
        enumerateMorphems();
    }

    /**
     *  Returns the month's name
     *  @param month month's number, >= 1 and <= 12
     *  @return word denoting the month
     */
    public String spellMonth(int month) {
        String result = "";
        switch (month) {
            case 10: 
                result = getMorphem("z1") + getMorphem("wm");
                break;
            case 11:
            case 12:
                result = getMorphem("z1");
                month %= 10; // keep ones digit only
                result = wordN[month] + getMorphem("wm"); // n month
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                result = wordN[month] + getMorphem("wm"); // n month
                break;
            default:
                result = Integer.toString(month);
                break;
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
                    , "春天"
                    , "夏天"
                    , "秋天"
                    , "冬天"
                    })[season];
        }
        return result;
    }


}
