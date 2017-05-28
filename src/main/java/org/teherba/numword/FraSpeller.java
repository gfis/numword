/*  Spelling of numbers in French (Français) 
    spoken in France, Monaco, Mauritius, parts of Belgium,
    Switzerland, Canada, some countries in Africa and in 
    former french colonies
    @(#) $Id: FraSpeller.java 820 2011-11-07 21:59:07Z gfis $
    Copyright (c) 2005 Dr. Georg Fischer <punctum@punctum.com>
    2011-10-26: spellClock
    2009-11-24: spellGreeting
    2006-01-04: UTF-8 précédé
    2005-06-14, Georg Fischer
    
    caution: UTF-8 is essential! compile with "-encoding UTF-8"

    Lots of problems with hyphens, plural, "un" and "et",
    c.f. <http://www.langue-fr.net/index/S/septante.htm>:
    
    Quatre-vingts (mais quatre-vingt-onze) et cent

    Comme cent, vingt prend un s lorsqu'il est précédé d'un chiffre 
    multiplicateur (cinq cents, quatre-vingts) sans être immédiatement 
    suivi d'un autre adjectif numéral (cinq cent un, quatre-vingt-trois). 
    Si millions ou milliards (qui sont des noms) suit, l'accord 
    s'effectue même si le nombre complexe continue au-delà 
    (trois cents millions quatre-vingt mille deux cent onze)

    On aura noté la présence du trait d'union dans quatre-vingts et 
    son absence dans cinq cents. La règle traditionnelle veut en effet que 
    l'on ne mette de trait d'union que pour la fraction des nombres complexes 
    inférieure à cent (deux mille trois cent trente-trois, cinquante-sept, 
    soixante-dix-huit) sauf dans le cas d'une coordination par et (vingt et un). 
    Ce trait d'union est conservé lorsqu'il s'agit d'un multiplicateur 
    (trente-trois mille, quatre-vingts millions trois cent mille quatre-vingt-neuf).

    Les rectifications orthographiques de 1990 autorisent le trait d'union pour 
    tous les numéraux.

    80
    91
    500
    501
    83
    300.080.211
    2.333
    57
    78
    21
    33.000
    84.300.089
    
    http://www.franzoesisch-online.com/grammatik/franzosisch-uhrzeiten/
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
 *  Spells numbers in French (Francais)
 *  @author Dr. Georg Fischer
 */
public class FraSpeller extends BaseSpeller {
    public final static String CVSID = "@(#) $Id: FraSpeller.java 820 2011-11-07 21:59:07Z gfis $";

    /**
     *  separates tens and units 
     */
    private String traitDUnion = "-";   
    
    /**
     *  Constructor
     */
    public FraSpeller() {
        super();
        setIso639("fra,fr");
        setDescription("French (Français)");
        setMaxLog(true);
        setSeparator(true);
        
        wordN = new String[]
        { "zéro" 
        , "un"
        , "deux"
        , "trois"
        , "quatre"
        , "cinq"
        , "six"
        , "sept"
        , "huit"
        , "neuf"
        };
        wordN0 = new String[]
        { ""
        , "dix"
        , "vingt"
        , "trente"
        , "quarante"
        , "cinquante"
        , "soixante"
        , "septante"        // 70: soixante dix, onze ... - patched by algorithm
        , "quatre-vingt"    // Swiss: octante
        , "nonante"         // 90: quatre-vingt dix, onze ... - patched by algorithm
        };
        word1N = new String[]
        { "dix"
        , "onze"
        , "douze"
        , "treize"
        , "quatorze"
        , "quinze"
        , "seize"
        , "dix-sept" // special treatment below
        , "dix-huit"
        , "dix-neuf"
        };

        setMorphem("h1", "cent");
        setMorphem("h2", "cents");
        setMorphem("t1", "mille");
        setMorphem("t2", "milles");
        setMorphem("m1", "lion");
        setMorphem("m2", "lions");
        setMorphem("m3", "liard");
        setMorphem("m4", "liards");
        setMorphem("p0", " ");
        setMorphem("p1", "s");
        setMorphem("p2", "-");
        setMorphem("p3", "et");
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
                putWord("cent");
                break;
            default:
                spellN(digitN00);
                putWord("cent");
                if (digitN0 == 0 && digitN == 0 && logTuple != 1) { // but trois cent mille
                    append("s");
                }
                break;
        } // switch 100

        // put tens and ones
        switch (digitN0) {
            case 0: // 0-9
                if (nullOnly) {
                    spellN(0); // lonely 0
                }
                else 
                if (digitN != 0) {
                    if (digitN == 1 && logTuple != 1) {
                        // not "un mille"
                        spellN(digitN);
                    }
                    else
                    if (digitN > 1) {
                        spellN(digitN);
                    }
                }
                break; 
            case 1: // 11-19
                spell1N(digitN);
                break;
            case 7: // 70
                spellN0(digitN0 - 1); // patch: soixante
                if (digitN == 1) {
                    putWord("et");
                    spell1N(digitN);
                }
                else {
                    append(traitDUnion);
                    append(word1N[digitN]);
                }
                break;
            case 9: // 90
                spellN0(digitN0 - 1); // patch: quatre-vingt
                append(traitDUnion);
                append(word1N[digitN]);
                break;
            case 8: // 80 
                spellN0(digitN0);
                switch (digitN) {
                    case 0:
                        append("s");
                        break;
                    default:
                        append(traitDUnion);
                        append(wordN[digitN]);
                        break;
                } // switch digitN
                break;
            default: // 20, 30, 40, 50, 60, 80 
                spellN0(digitN0);
                switch (digitN) {
                    case 0:
                        break;
                    case 1:
                        putWord("et un");
                        break;
                    default:
                        append(traitDUnion);
                        append(wordN[digitN]);
                        break;
                } // switch digitN
                break;
        } // switch digitN0
        
        if (! zeroTuple) { // append thousand, million ... */
            switch (logTuple) {
                case 0: // no thousands
                    break;
                case 1:
                    putWord("mille");
                    break;
                default:
                    spellN000Morphem(logTuple);
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
                    { "mois"
                    , "Janvier"
                    , "Février"
                    , "Mars"
                    , "Avril"
                    , "Mai"
                    , "Juin"
                    , "Juillet"
                    , "Août"
                    , "Septembre"
                    , "Octobre"
                    , "Novembre"
                    , "Décembre"
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
                    { "année"
                    , "Printemps"
                    , "Été"
                    , "Automne"
                    , "Hiver"
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
                    { "jour du semaine"
                    , "Lundi"
                    , "Mardi"
                    , "Mercredi"
                    , "Jeudi"
                    , "Vendredi"
                    , "Samedi"
                    , "Dimanche"
                    })[weekDay];
        }
        return result;
    } // spellWeekDay
    
    //================================================================
    /** Returns a denotation of the day's time, possibly in several variants
     *  @param hour hour 0..24
     *  @param minute minute 0..59
     *  @param variant the code behind the commandline option "-h":
     *  empty or 0 (official), 1,2,3 for  a language specific variant.
     *  @return phrase corresponding to the denotation of the time, for example
     *  <ul>
     *  <li>Français, variant "1": 18:15 =&gt; "six et quart"</li>
     *  </ul> 
     */
    public String spellClock(int hour, int minute, String variant) {
        String result = String.valueOf(hour   + 100).substring(1) + ':' 
                      + String.valueOf(minute + 100).substring(1);
        int hour12 = hour + (minute > 30 ? 1 : 0);
        hour12 = (hour12 == 0 ? 12 : (hour12 >= 13 ? hour12 - 12 : hour12)); 
        String spellHour = (spellCardinal(String.valueOf(hour12))
                + " heure"
                + (hour12 > 1 ? "s" : "") 
                ).replaceAll("un heures?", "une heure");
        if (false) {
        } else if (variant.length() == 0 || variant.equals("0")) {
            result  = (spellCardinal(String.valueOf(hour)).replace("zero", "zéro")
                    + " heure"
                    + (hour > 1 ? "s" : "")
                    ).replaceAll("un heures?", "une heure");
            if (minute > 0) {
                result += " " + spellCardinal(String.valueOf(minute)); 
            }
        } else if (variant.equals("1")) { 
            if (minute % 15 == 0) {
                switch (minute / 15) {
                    default:
                    case 0:
                        result  = spellHour;
                        break;
                    case 1:
                        result  = spellHour + " et quart";
                        break;
                    case 2:
                        result  = spellHour + " et demi";
                        break;
                    case 3:
                        result  = spellHour + " moins quart";
                        break;
                } // switch 0..3
            } else {
                result  = spellHour 
                        + (minute < 30  ? " et "    + spellCardinal(String.valueOf(     minute))
                                        : " moins " + spellCardinal(String.valueOf(60 - minute))) 
                        ;
            }
        } // switch variant
        return result;
    } // spellClock(3)
    
    //================================================================
    /** Get a word for one the 4 cardinal directions, 
     *  and for the particle for 32th fractions
     *  @param cardDir a cardinal direction, 0 = North, 1 = East, 2 = South, 3 = West
     */
    protected String getCompassWord(int cardDir) {
        String result = "";
        switch (cardDir) {
            case 0: result = "nord"     ; break;
            case 1: result = "est"      ; break;
            case 2: result = "sud"      ; break;
            case 3: result = "ouest"    ; break;
            case 4: result = "à"        ; break;
        } // switch
        return result;
    } // getCompassWord
    
    /** Standard separator for the cardinal direction words */
    protected String getCompassSeparator() {
        return "-"; // none for abbreviations   
    } // getCompassSeparator

    /** Returns the language specific words for a cardinal direction 
     *  @param code abbreviation, a sequence of the letters N,E,S,W.
<pre>
from http://fr.wikipedia.org/wiki/Point_cardinal:

Abréviation     Point   Azimut  Radians
N   nord    0°  0
NNE     nord-nord-est   22,5°   π/8
NE  nord-est    45°     π/4
ENE     est-nord-est    67,5°   3π/8
E   est     90°     π/2
ESE     est-sud-est     112,5°  5π/8
SE  sud-est     135°    3π/4
SSE     sud-sud-est     157,5°  7π/8
S   sud     180°    π
SSO     sud-sud-ouest   202,5°  9π/8
SO  sud-ouest   225°    5π/4
OSO     ouest-sud-ouest     247,5°  11π/8
O   ouest   270°    3π/2
ONO     ouest-nord-ouest    292,5°  13π/8
NO  nord-ouest  315°    7π/4
NNO     nord-nord-ouest     337,5°  15π/8
N   nord    360°    2π    
</pre>
     */
    protected String spellCompassCode(String code) {
        String result = super.spellCompassCode(code);
        result = result.substring(0, 1).toLowerCase() + result.substring(1);
        if (code.length() == 4) {
            // result += "en";
        }
        return result;
    } // spellCompassCode
   
    //================================================================
    /** Returns a greeting corresponding to the parameter time:
     *  @return greeting corresponding to the time of the day
     */
    public String spellGreeting(int timeOfDay) {
        String result = "Bonjour";
        timeOfDay /= 6; 
        if (timeOfDay >= 0 && timeOfDay <= 4) {
            result = (new String[]
                    { "Au revoir"
                    , "Bon matin"
                    , "Bonjour"
                    , "Bonsoir"
                    , "Bonne nuit"
                    }
                    )[timeOfDay];
        } // in range
        return result;
    } // spellGreeting(int)

    //================================================================
    /** Returns a planet
     *  @param planet number of the planet (3 = earth, 0 = sun, -1 = moon)
     *  @return planet's name
     */
    public String spellPlanet(int planet) {
        String result = super.spellPlanet(planet);
        switch (planet) {
            case -1:    result = "Lune";        break;
            case 0:     result = "Soleil";      break;
            case 1:     result = "Mercure";     break;
            case 2:     result = "Vénus";       break;
            case 3:     result = "Terre";       break;
            case 6:     result = "Saturne";     break;
            case 8:     result = "Neptune";     break;
        } // switch
        return result;
    } // spellPlanet(int)

} // FraSpeller
