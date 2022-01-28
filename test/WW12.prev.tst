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
<!-- function h2, lang3 deu, digits="-->
<h2>Number Words</h2>
<form action="servlet" method="post">
    <input type = "hidden" name="view" value="index" />
    <table cellpadding="8">
      <tr><td width="100">Language<br />
          <select name="lang3" size="40">
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
<option value="jbo">jbo - Lojban</option>
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
<option value="c">Digits as Word</option>
<option value="C">Word as Digits</option>
<option value="m">Month</option>
<option value="m3">Month's Abbreviation</option>
<option value="w">Weekday</option>
<option value="w2">Weekday's Abbreviation</option>
<option value="s">Season</option>
<option value="g">Greeting</option>
<option value="h0">Time of Day - offical</option>
<option value="h1">Time - variant 1</option>
<option value="h2" selected>Time - variant 2</option>
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
<tr><td align="right">0:00</td><td>zwölf</td></tr>
<tr><td align="right">0:15</td><td>viertel eins</td></tr>
<tr><td align="right">0:20</td><td>zwanzig nach zwölf</td></tr>
<tr><td align="right">0:45</td><td>dreiviertel eins</td></tr>
<tr><td align="right">1:00</td><td>eins</td></tr>
<tr><td align="right">1:15</td><td>viertel zwei</td></tr>
<tr><td align="right">1:45</td><td>dreiviertel zwei</td></tr>
<tr><td align="right">2:15</td><td>viertel drei</td></tr>
<tr><td align="right">3:15</td><td>viertel vier</td></tr>
<tr><td align="right">4:29</td><td>neunundzwanzig nach vier</td></tr>
<tr><td align="right">5:30</td><td>halb sechs</td></tr>
<tr><td align="right">6:31</td><td>neunundzwanzig vor sieben</td></tr>
<tr><td align="right">7:45</td><td>dreiviertel acht</td></tr>
<tr><td align="right">8:50</td><td>zehn vor neun</td></tr>
<tr><td align="right">9:00</td><td>neun</td></tr>
<tr><td align="right">12:00</td><td>zwölf</td></tr>
<tr><td align="right">12:15</td><td>viertel eins</td></tr>
<tr><td align="right">12:30</td><td>halb eins</td></tr>
<tr><td align="right">12:45</td><td>dreiviertel eins</td></tr>
<tr><td align="right">13:10</td><td>zehn nach eins</td></tr>
<tr><td align="right">13:15</td><td>viertel zwei</td></tr>
<tr><td align="right">13:30</td><td>halb zwei</td></tr>
<tr><td align="right">13:45</td><td>dreiviertel zwei</td></tr>
<tr><td align="right">21:20</td><td>zwanzig nach neun</td></tr>
<tr><td align="right">23:30</td><td>halb zwölf</td></tr>
<tr><td align="right">23:45</td><td>dreiviertel zwölf</td></tr>
<tr><td align="right">23:55</td><td>fünf vor zwölf</td></tr>
<tr><td align="right">24:00</td><td>zwölf</td></tr>
</table>
        </td>
      </tr>
    </table>
</form>
<!-- language="en", features="quest" -->
<p><span style="font-size:small">
Questions, remarks: email to  <a href="mailto:punctum@punctum.com?&subject=Numword">Dr. Georg Fischer</a></span></p>
</body></html>
