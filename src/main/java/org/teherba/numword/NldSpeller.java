/*  Spelling of numbers in Dutch (Nederlands)
    spoken in the Nederlands and northern Belgium (Flemish)
    @(#) $Id: NldSpeller.java 818 2011-10-31 17:35:23Z gfis $
    2006-01-04: weekdays and months; UTF-8 "één"
    2005-06-01, Georg Fischer: copied from DeuSpeller.java

    caution: UTF-8 is essential! compile with "-encoding UTF-8"
    
    retrieved from "http://home.unilang.org/main/wiki2/index.php/Translations:_Numbers_-_Dutch"
    and checked against that list: éénhonderd and éénduizend??
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
 *  Spells numbers in Dutch (Nederlands)
 *  @author Dr. Georg Fischer
 */
public class NldSpeller extends BaseSpeller {
    public final static String CVSID = "@(#) $Id: NldSpeller.java 818 2011-10-31 17:35:23Z gfis $";
    
    /**
     *  Constructor
     */
    public NldSpeller() {
        super();
        setIso639("nld,nl");
        setDescription("Dutch (Nederlands)");
        setMaxLog(true);
        setSeparator(true);

        wordN = new String[]
        { "nul"
        , "één"
        , "twee"
        , "drie"
        , "vier"
        , "vijf"
        , "zes"
        , "zeven"
        , "acht"
        , "negen"
        };
        wordN0 = new String[]
        { ""
        , "tien"
        , "twintig"
        , "dertig"
        , "veertig"
        , "vijftig"
        , "zestig"
        , "zeventig"
        , "tachtig"
        , "negentig"
        };
        word1N = new String[]
        { "tien"
        , "elf"
        , "twaalf"
        , "dertien"
        , "veertien"
        , "vijttien"
        , "zestien"
        , "zeventien"
        , "achttien"
        , "negentien"
        };

        setMorphem("h1", "honderd");
        setMorphem("t1", "duizend");
        setMorphem("m1", "joen");
        setMorphem("m3", "jard");
        setMorphem("p0", " ");
        setMorphem("p3", "en");
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
                spellN(digitN00);
                append(getMorphem("h1"));
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
                if (digitN >= 1) {
                    spellN(digitN);
                    append(getMorphem("p3"));
                    append(wordN0[digitN0]);
                }
                else {
                    spellN0(digitN0);
                }
                break;
        } // switch digitN0
        
        if (! zeroTuple) { // append thousand, million ... */
            switch (logTuple) {
                case 0: // no thousands
                    break;
                case 1:
                    append(getMorphem("t1"));
                    break;
                default:
                    spellN000(logTuple, getMorphem("m1"), getMorphem("m3"));
                    break;
            } // switch logTuple
        } // thousands ...
    } // spellTuple

    /** Returns the month's name
     *  @param month month's number, &gt;= 1 and &lt;= 12
     *  @return word denoting the month
     */
    public String spellMonth(int month) {
        String result = Integer.toString(month);
        if (month >= 0 && month <= 12) {
            result = (new String [] 
                    { "maand"   // 0
                    , "januari" // 1
                    , "februari"    // 2
                    , "maart"   // 3
                    , "april"   // 4
                    , "mei" // 5
                    , "juni"    // 6
                    , "juli"    // 7
                    , "augustus"    // 8
                    , "september"   // 9
                    , "oktober" // 10
                    , "november"    // 11
                    , "december"    // 12
                    })[month];
        }
        return result;
    } // spellMonth

    /** Returns the season's name
     *  @param season number of the quarter in the year:
     *  1 -&gt; Spring, 2 -&gt; Summer, 3 -&gt; Autumn, 4 = Winter // spellWeekDay
     *  @return word denoting the season
     */
    public String spellSeason(int season) {
        String result = Integer.toString(season);
        if (season >= 0 && season <= 4) {
            result = (new String [] 
                    { "0"
                    , "lente"
                    , "zomer"
                    , "herfst"
                    , "winter"
                    })[season];
        }
        return result;
    } // spellSeason

    /** Returns the week day's name
     *  @param weekDay number of day in week, &gt;= 0 and &lt;= 7,
     *  1 -&gt; Monday, 7 -&gt; Sunday
     *  @return word denoting the day in the week
     */
    public String spellWeekDay(int weekDay) {
        String result = Integer.toString(weekDay);
        if (weekDay >= 0 && weekDay <= 7) {
            result = (new String [] 
                    { "0"
                    , "Maandag" // 1
                    , "Dinsdag" // 2
                    , "Woensdag"    // 3
                    , "Donderdag"   // 4
                    , "Vrijdag" // 5
                    , "Zaterdag"    // 6
                    , "Zondag"  // 7
                    })[weekDay];
        }
        return result;
    } // spellWeekDay

	/** Get a word for one the 4 cardinal directions, 
	 * 	and for the particle for 32th fractions
	 *	@param cardDir a cardinal direction, 0 = North, 1 = East, 2 = South, 3 = West
	 */
	protected String getCompassWord(int cardDir) {
		String result = "";
		switch (cardDir) {
			case 0:	result = "noord"	; break;
			case 1:	result = "oost"		; break;
			case 2:	result = "zuid"		; break;
			case 3:	result = "west"		; break;
			case 4:	result = "ten"		; break;
		} // switch
		return result;
	} // getCompassWord
	
/*
00000b  0 		N 		Noord
00001b  11¼ 	NNNO 	Noord ten oosten
00010b  22½ 	NNO 	Noordnoordoost
00011b  33¾ 	ONNO 	Noordoost ten noorden
00100b  45 		NO 		Noordoost
00101b  56¼ 	NONO 	Noordoost ten oosten
00110b  67½ 	ONO 	Oostnoordoost
00111b  78¾ 	OONO 	Oost ten noorden
01000b  90 		O 		Oost
01001b  101¼ 	OOZO 	Oost ten zuiden
01010b  112½ 	OZO 	Oostzuidoost
01011b  123¾ 	ZOZO 	Zuidoost ten oosten
01100b  135 	ZO 		Zuidoost
01101b  146¼ 	OZZO 	Zuidoost ten zuiden
01110b  157½ 	ZZO 	Zuidzuidoost
01111b  168¾ 	ZZZO 	Zuid ten oosten
10000b  180 	Z 		Zuid
10001b  191¼ 	ZZZW 	Zuid ten westen
10010b  202½ 	ZZW 	Zuidzuidwest
10011b  213¾ 	WZZW 	Zuidwest ten zuiden
10100b  225 	ZW 		Zuidwest
10101b  236¼ 	ZWZW 	Zuidwest ten westen
10110b  247½ 	WZW 	Westzuidwest
10111b  258¾ 	WWZW 	West ten zuiden
11000b  270 	W 		West
11001b  281¼ 	WWNW 	West ten noorden
11010b  292½ 	WNW 	Westnoordwest
11011b  303¾ 	NWNW 	Noordwest ten westen
11100b  315 	NW 		Noordwest
11101b  326¼ 	WNNW 	Noordwest ten noorden
11110b  337½ 	NNW 	Noordnoordwest
11111b  348¾ 	NNNW 	Noord ten westen
0b 		360  	N 		Noord
*/

    /** Returns the language specific words for a cardinal direction 
     *	@param code abbreviation, a sequence of the letters N,E,S,W.
     */
    protected String spellCompassCode(String code) {
		String result = super.spellCompassCode(code);
		if (code.length() == 4) {
			result += "en";
		}
		return result;
	} // spellCompassCode
	
} // NldSpeller
