<?xml version="1.0" encoding="UTF-8"?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="application/xhtml+xml;charset=UTF-8" />
<meta name="robots" content="noindex, nofollow" />
<link rel="stylesheet" title="common" type="text/css" href="stylesheet.css" />
<title>Numword Main Page</title>
</head>
<body>
<!-- function c, lang3 deu, digits="-->
<h2>Number Words</h2>
<form action="servlet" method="post">
    <input type = "hidden" name="view" value="index" />
    <table cellpadding="8">
      <tr><td width="100">Language<br />
          <select name="lang3" size="39">
<option value="ara">ara - Arabic (ديسمبر)</option>
<option value="cze">cze - Czech (čeština)</option>
<option value="chi">chi - Chinese 中文 (zhōngwén)</option>
<option value="dan">dan - Danish (Dansk)</option>
<option value="deu" selected>deu - German (Deutsch)</option>
<option value="eng">eng - English</option>
<option value="epo">epo - Esperanto</option>
<option value="est">est - Estonian</option>
<option value="fin">fin - Finnish</option>
<option value="fra">fra - French (Français)</option>
<option value="gle">gle - Irish (Gaeilge)</option>
<option value="geo">geo - Georgian</option>
<option value="gre">gre - Greek (Ελληνικά)</option>
<option value="hun">hun - Hungarian (Magyar)</option>
<option value="ice">ice - Icelandic (íslenska)</option>
<option value="ita">ita - Italian (Italiano)</option>
<option value="jpn">jpn - Japanese</option>
<option value="kor">kor - Korean</option>
<option value="lat">lat - Latin (Latinum)</option>
<option value="lav">lav - Latvian (Latviešu)</option>
<option value="lit">lit - Lithuanian (Lietuvių)</option>
<option value="nld">nld - Dutch (Nederlands)</option>
<option value="nor">nor - Norwegian (Norsk)</option>
<option value="pol">pol - Polish (Polski)</option>
<option value="por">por - Portuguese (Português)</option>
<option value="roh">roh - Rumantsch Grischun</option>
<option value="ron">ron - Romanian (Română)</option>
<option value="rus">rus - Russian (Русский)</option>
<option value="slo">slo - Slovak</option>
<option value="slv">slv - Slovenian (Slovenščina)</option>
<option value="spa">spa - Spanish (Español)</option>
<option value="swe">swe - Swedish (Svenska)</option>
<option value="tha">tha - Thai (ไทย)</option>
<option value="tlh">tlh - Klingon (tlhIngan-Hol)</option>
<option value="tur">tur - Turkish (Türkçe)</option>
<option value="vie">vie - Vietnamese (tiếng Việt)</option>
<option value="braille">braille - Braille Code</option>
<option value="morse">morse - Morse Code</option>
<option value="roman">roman - Roman Numbers</option>
          </select>
        </td>
        <td width="100">Spell<br />
          <select name="function" size="14">
<option value="c" selected>Digits as Word</option>
<option value="C">Word as Digits</option>
<option value="m">Month</option>
<option value="m3">Month's Abbreviation</option>
<option value="w">Weekday</option>
<option value="w2">Weekday's Abbreviation</option>
<option value="s">Season</option>
<option value="g">Greeting</option>
<option value="h0">Time of Day - offical</option>
<option value="h1">Time - variant 1</option>
<option value="h2">Time - variant 2</option>
<option value="h3">Time - variant 3</option>
<option value="d">Cardinal Direction</option>
<option value="p">Planet</option>
          </select>
          <br />&nbsp;
          <br />
<a title="doc"         href="docs/documentation.html">Documentation</a><br />
<a title="developer"   href="docs/developer.html">Developer Hints</a><br />
<a title="bugs"        href="docs/bugs.html">Bugs</a><br />
<a title="unicode"     href="servlet?view=uniblock&digits=01">Unicodes</a><br />
<a title="wiki"        href="http://www.teherba.org/index.php/Numword" target="_new">Wiki</a><br />
<a title="github"      href="https://github.com/gfis/numword" target="_new">Git Repository</a><br />
<a title="api"         href="docs/api/index.html">Java API</a><br />
<a title="manifest"    href="servlet?view=manifest">Manifest</a><br />
<a title="license"     href="servlet?view=license">License</a><br />
<a title="notice"      href="servlet?view=notice">References</a><br />
          <p  />If the <em>Digits/Word</em> field is left empty, all months, weekdays etc. are shown,
          and for "Digits as Word" a list of representative test numbers is spelled.
        </td>
        <td width="100" valign="top">Digits / Word<br />
          <input name="digits" size="60" value="" /><br />          <input type="submit" value="Submit" />
<h2>deu - German (Deutsch)</h2>
<table>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Null" target="new">0</a></td><td>null</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Eins" target="new">1</a></td><td>eins</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Zwei" target="new">2</a></td><td>zwei</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Drei" target="new">3</a></td><td>drei</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Vier" target="new">4</a></td><td>vier</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Fünf" target="new">5</a></td><td>fünf</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Sechs" target="new">6</a></td><td>sechs</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Sieben" target="new">7</a></td><td>sieben</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Acht" target="new">8</a></td><td>acht</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Neun" target="new">9</a></td><td>neun</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Zehn" target="new">10</a></td><td>zehn</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Elf" target="new">11</a></td><td>elf</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Zwölf" target="new">12</a></td><td>zwölf</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Dreizehn" target="new">13</a></td><td>dreizehn</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Vierzehn" target="new">14</a></td><td>vierzehn</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Fünfzehn" target="new">15</a></td><td>fünfzehn</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Sechzehn" target="new">16</a></td><td>sechzehn</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Siebzehn" target="new">17</a></td><td>siebzehn</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Achtzehn" target="new">18</a></td><td>achtzehn</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Neunzehn" target="new">19</a></td><td>neunzehn</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Zwanzig" target="new">20</a></td><td>zwanzig</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Einundzwanzig" target="new">21</a></td><td>einundzwanzig</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Zweiundzwanzig" target="new">22</a></td><td>zweiundzwanzig</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Dreiundzwanzig" target="new">23</a></td><td>dreiundzwanzig</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Siebenundzwanzig" target="new">27</a></td><td>siebenundzwanzig</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Achtundzwanzig" target="new">28</a></td><td>achtundzwanzig</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Neunundzwanzig" target="new">29</a></td><td>neunundzwanzig</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Dreißig" target="new">30</a></td><td>dreißig</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Einunddreißig" target="new">31</a></td><td>einunddreißig</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Zweiunddreißig" target="new">32</a></td><td>zweiunddreißig</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Dreiunddreißig" target="new">33</a></td><td>dreiunddreißig</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Vierunddreißig" target="new">34</a></td><td>vierunddreißig</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Vierzig" target="new">40</a></td><td>vierzig</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Einundvierzig" target="new">41</a></td><td>einundvierzig</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Zweiundvierzig" target="new">42</a></td><td>zweiundvierzig</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Neunundvierzig" target="new">49</a></td><td>neunundvierzig</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Fünfzig" target="new">50</a></td><td>fünfzig</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Fünfundfünfzig" target="new">55</a></td><td>fünfundfünfzig</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Sechzig" target="new">60</a></td><td>sechzig</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Einundsechzig" target="new">61</a></td><td>einundsechzig</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Zweiundsechzig" target="new">62</a></td><td>zweiundsechzig</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Siebzig" target="new">70</a></td><td>siebzig</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Einundsiebzig" target="new">71</a></td><td>einundsiebzig</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Zweiundsiebzig" target="new">72</a></td><td>zweiundsiebzig</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Neunundsiebzig" target="new">79</a></td><td>neunundsiebzig</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Achtzig" target="new">80</a></td><td>achtzig</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Einundachtzig" target="new">81</a></td><td>einundachtzig</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Dreiundachtzig" target="new">83</a></td><td>dreiundachtzig</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Neunundachtzig" target="new">89</a></td><td>neunundachtzig</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Neunzig" target="new">90</a></td><td>neunzig</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Einundneunzig" target="new">91</a></td><td>einundneunzig</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Zweiundneunzig" target="new">92</a></td><td>zweiundneunzig</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Achtundneunzig" target="new">98</a></td><td>achtundneunzig</td></tr>
<tr><td align="right"><a href="http://de.wikipedia.org/wiki/Neunundneunzig" target="new">99</a></td><td>neunundneunzig</td></tr>
<tr><td align="right">100</td><td>einhundert</td></tr>
<tr><td align="right">101</td><td>einhunderteins</td></tr>
<tr><td align="right">102</td><td>einhundertzwei</td></tr>
<tr><td align="right">103</td><td>einhundertdrei</td></tr>
<tr><td align="right">200</td><td>zweihundert</td></tr>
<tr><td align="right">202</td><td>zweihundertzwei</td></tr>
<tr><td align="right">300</td><td>dreihundert</td></tr>
<tr><td align="right">303</td><td>dreihundertdrei</td></tr>
<tr><td align="right">400</td><td>vierhundert</td></tr>
<tr><td align="right">404</td><td>vierhundertvier</td></tr>
<tr><td align="right">472</td><td>vierhundertzweiundsiebzig</td></tr>
<tr><td align="right">500</td><td>fünfhundert</td></tr>
<tr><td align="right">501</td><td>fünfhunderteins</td></tr>
<tr><td align="right">505</td><td>fünfhundertfünf</td></tr>
<tr><td align="right">600</td><td>sechshundert</td></tr>
<tr><td align="right">606</td><td>sechshundertsechs</td></tr>
<tr><td align="right">700</td><td>siebenhundert</td></tr>
<tr><td align="right">707</td><td>siebenhundertsieben</td></tr>
<tr><td align="right">800</td><td>achthundert</td></tr>
<tr><td align="right">808</td><td>achthundertacht</td></tr>
<tr><td align="right">900</td><td>neunhundert</td></tr>
<tr><td align="right">998</td><td>neunhundertachtundneunzig</td></tr>
<tr><td align="right">999</td><td>neunhundertneunundneunzig</td></tr>
<tr><td align="right">1000</td><td>eintausend</td></tr>
<tr><td align="right">1001</td><td>eintausendeins</td></tr>
<tr><td align="right">1002</td><td>eintausendzwei</td></tr>
<tr><td align="right">1234</td><td>eintausendzweihundertvierunddreißig</td></tr>
<tr><td align="right">1984</td><td>eintausendneunhundertvierundachtzig</td></tr>
<tr><td align="right">2333</td><td>zweitausenddreihundertdreiunddreißig</td></tr>
<tr><td align="right">10000</td><td>zehntausend</td></tr>
<tr><td align="right">33000</td><td>dreiunddreißigtausend</td></tr>
<tr><td align="right">100000</td><td>einhunderttausend</td></tr>
<tr><td align="right">100001</td><td>einhunderttausendeins</td></tr>
<tr><td align="right">123456</td><td>einhundertdreiundzwanzigtausendvierhundertsechsundfünfzig</td></tr>
<tr><td align="right">1000000</td><td>eine Million </td></tr>
<tr><td align="right">1000001</td><td>eine Million eins</td></tr>
<tr><td align="right">1234567</td><td>eine Million zweihundertvierunddreißigtausendfünfhundertsiebenundsechzig</td></tr>
<tr><td align="right">2000000</td><td>zwei Millionen </td></tr>
<tr><td align="right">84300089</td><td>vierundachtzig Millionen dreihunderttausendneunundachtzig</td></tr>
<tr><td align="right">100000000</td><td>einhundert Millionen </td></tr>
<tr><td align="right">300080211</td><td>dreihundert Millionen achtzigtausendzweihundertelf</td></tr>
<tr><td align="right">1 000 000 000</td><td>eine Milliarde </td></tr>
<tr><td align="right">1 000 000 001</td><td>eine Milliarde eins</td></tr>
<tr><td align="right">1 234 567 890</td><td>eine Milliarde zweihundertvierunddreißig Millionen fünfhundertsiebenundsechzigtausendachthundertneunzig</td></tr>
<tr><td align="right">2 000 000 000</td><td>zwei Milliarden </td></tr>
<tr><td align="right">1 000 000 000 000</td><td>eine Billion </td></tr>
<tr><td align="right">2 000 000 000 000</td><td>zwei Billionen </td></tr>
<tr><td align="right">1 000 000 000 000 000</td><td>eine Billiarde </td></tr>
<tr><td align="right">1 000 000 300 000 000 400 000 500</td><td>eine Quadrillion dreihundert Billiarden vierhundert Millionen fünfhundert</td></tr>
<tr><td align="right">4 004 003 003 002 002 001 001 000 000</td><td>vier Quadrilliarden vier Quadrillionen drei Trilliarden drei Trillionen zwei Billiarden zwei Billionen eine Milliarde eine Million </td></tr>
<tr><td align="right">9 008 007 006 005 004 003 002 001 000 000</td><td>neun Quintillionen acht Quadrilliarden sieben Quadrillionen sechs Trilliarden fünf Trillionen vier Billiarden drei Billionen zwei Milliarden eine Million </td></tr>
<tr><td align="right">20 019 018 017 016 015 014 013 012 011 010 009 008 007 006 005 004 003 002 001 000 000</td><td>zwanzig Dezilliarden neunzehn Dezillionen achtzehn Nonilliarden siebzehn Nonillionen sechzehn Oktilliarden fünfzehn Oktillionen vierzehn Septilliarden dreizehn Septillionen zwölf Sextilliarden elf Sextillionen zehn Quintilliarden neun Quintillionen acht Quadrilliarden sieben Quadrillionen sechs Trilliarden fünf Trillionen vier Billiarden drei Billionen zwei Milliarden eine Million </td></tr>
</table>
        </td>
      </tr>
    </table>
</form>
<!-- language="en", features="quest" -->
<p><span style="font-size:small">
Questions, remarks: email to  <a href="mailto:punctum@punctum.com?&subject=Numword">Dr. Georg Fischer</a></span></p>
</body></html>
