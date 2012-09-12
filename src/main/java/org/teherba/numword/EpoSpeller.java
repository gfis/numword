/*  Spelling of numbers in Esperanto
    artificial language spoken by some people all over the world
    @(#) $Id: EpoSpeller.java 820 2011-11-07 21:59:07Z gfis $
    Copyright (c) 2005 Dr. Georg Fischer <punctum@punctum.com>
    2006-01-04: weekdays and months; UTF-8 "naŭ"
    2005-06-06, Georg Fischer: copied from TurSpeller

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
import  org.teherba.numword.BaseSpeller;
/**
 *  Spells numbers in Esperanto.
 *  @author Dr. Georg Fischer
 */
public class EpoSpeller extends BaseSpeller {
    public final static String CVSID = "@(#) $Id: EpoSpeller.java 820 2011-11-07 21:59:07Z gfis $";
    
    /**
     *  Constructor
     */
    public EpoSpeller() {
        super();
        setIso639("epo,eo");
        setDescription("Esperanto");
        setMaxLog(true);
        setSeparator(true);

        wordN = new String[]
        { "zero"
        , "unu"
        , "du"
        , "tri"
        , "kvar"
        , "kvin"
        , "ses"
        , "sep" 
        , "ok"
        , "naŭ" // hajeck on u
        };

        setMorphem("z1", "dek");
        setMorphem("h1", "cent");
        setMorphem("t1", "mil");
        setMorphem("m1", "iono");
        setMorphem("m3", "iardo");
        setMorphem("p0", " ");
        setMorphem("p1", "-");
        enumerateMorphems();
        // "li" must be set after 'enumerateMorphems'
        removeMorphem("e02");
        setMorphem("l1", "miliono");
        setMorphem("l3", "miliardo");
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
                if (digitN == 1 && logTuple < 1) {
                    spellN(digitN);
                }
                else
                if (digitN > 1) {
                    spellN(digitN);
                }
                break; 
            case 1: 
                putMorphem("z1");
                if (digitN >= 1) {
                    spellN(digitN);
                }
                break;
            default:
                spellN(digitN0);
                append(getMorphem("z1"));
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
                    if (singleTuple) {
                        putMorphem("t1");
                    }
                    else {
                        append(getMorphem("t1"));
                    }
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
                    { "monato"      // 0
                    , "Januaro"     // 1
                    , "Februaro"    // 2
                    , "Marto"       // 3
                    , "Aprilo"      // 4
                    , "Majo"        // 5
                    , "Junio"       // 6
                    , "Julio"       // 7
                    , "Aŭgusto"    // 8
                    , "Septembro"   // 9
                    , "Oktobro"     // 10
                    , "Novembro"    // 11
                    , "Decembro"    // 12
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
                    , "printempo"
                    , "somero"
                    , "aŭtuno"
                    , "vintro"
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
                    , "Lundo"       // 1
                    , "Mardo"       // 2
                    , "Merkredo"    // 3
                    , "Ja\u016ddo"  // 4
                    , "Vendredo"    // 5
                    , "Sabato"      // 6
                    , "Diman\u0109o"// 7
                    })[weekDay];
        }
        return result;
    } // spellWeekDay

	//================================================================
    /** Returns a planet
     *  @param number of the planet (3 = earth, 0 = sun, -1 = moon)
     *  @return planet's name
     */
    public String spellPlanet(int planet) {
		String result = super.spellPlanet(planet);
		switch (planet) {
			case -1:	result = "Luno"; 		break;
			case 0:		result = "Suno"; 		break;
			case 1:		result = "Merkuro";		break;
			case 2:		result = "Venuso";		break;
			case 3:		result = "Tero";		break;
			case 4:		result = "Marso";		break;
			case 5:		result = "Jupitero";	break;
			case 6:		result = "Saturno";		break;
			case 7:		result = "Urano";		break;
			case 8:		result = "Neptuno";		break;
		} // switch
        return result;
    } // spellPlanet(int)

} // EpoSpeller
