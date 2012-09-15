#!/usr/bin/perl

# Get the Wikipedia links (with 2-letter language codes) suitable for number spelling
# from any page for a number word
# @(#) $Id: prep_wiki.pl 302 2009-11-27 06:45:08Z gfis $
# 2009-11-26, Dr. Georg Fischer
#
# Activation:
#   perl prep_wiki.pl Dezoito.html > java.inc

use strict;
use locale;
# use utf8;

    while (<DATA>) {
        if (m{\<li class\=\"interwiki\-\w+\"\>\<a\s+href\=\"http\:\/\/(\w+)\.wikipedia\.org\/wiki\/([^\"]+)\"\>([^\<]+)\<\/a\>\<\/li\>}) {
            my $iso2 = $1;
            my $arte = $2; # article title, URL encoded
            my $lang =  $3;
            if (length($iso2) == 2) {
                print <<"GFis";
        wikiLinks.put(\"$iso2\", \"$arte\"); // $lang
GFis
            }
        } # if matched
    } # while <>

__DATA__
            <ul>
                <li class="interwiki-ar"><a href="http://ar.wikipedia.org/wiki/18_(%D8%B9%D8%AF%D8%AF)">العربية</a></li>
                <li class="interwiki-ca"><a href="http://ca.wikipedia.org/wiki/Divuit">Català</a></li>
                <li class="interwiki-co"><a href="http://co.wikipedia.org/wiki/18_(numeru)">Corsu</a></li>
                <li class="interwiki-cv"><a href="http://cv.wikipedia.org/wiki/18_(%D1%85%D0%B8%D1%81%D0%B5%D0%BF)">Чӑвашла</a></li>
                <li class="interwiki-cy"><a href="http://cy.wikipedia.org/wiki/Un_deg_wyth">Cymraeg</a></li>
                <li class="interwiki-da"><a href="http://da.wikipedia.org/wiki/18_(tal)">Dansk</a></li>
                <li class="interwiki-de"><a href="http://de.wikipedia.org/wiki/Achtzehn">Deutsch</a></li>
                <li class="interwiki-en"><a href="http://en.wikipedia.org/wiki/18_(number)">English</a></li>
                <li class="interwiki-eo"><a href="http://eo.wikipedia.org/wiki/Dek_ok">Esperanto</a></li>
                <li class="interwiki-es"><a href="http://es.wikipedia.org/wiki/Dieciocho">Español</a></li>
                <li class="interwiki-eu"><a href="http://eu.wikipedia.org/wiki/Hemezortzi">Euskara</a></li>
                <li class="interwiki-fa"><a href="http://fa.wikipedia.org/wiki/%DB%B1%DB%B8_(%D8%B9%D8%AF%D8%AF)">فارسی</a></li>
                <li class="interwiki-fi"><a href="http://fi.wikipedia.org/wiki/18_(luku)">Suomi</a></li>
                <li class="interwiki-fr"><a href="http://fr.wikipedia.org/wiki/18_(nombre)">Français</a></li>
                <li class="interwiki-gan"><a href="http://gan.wikipedia.org/wiki/18">贛語</a></li>
                <li class="interwiki-gn"><a href="http://gn.wikipedia.org/wiki/Papoapy">Avañe'ẽ</a></li>
                <li class="interwiki-he"><a href="http://he.wikipedia.org/wiki/18_(%D7%9E%D7%A1%D7%A4%D7%A8)">עברית</a></li>
                <li class="interwiki-ht"><a href="http://ht.wikipedia.org/wiki/18_(nonm)">Kreyòl ayisyen</a></li>
                <li class="interwiki-hu"><a href="http://hu.wikipedia.org/wiki/18_(sz%C3%A1m)">Magyar</a></li>
                <li class="interwiki-ia"><a href="http://ia.wikipedia.org/wiki/18_(numero)">Interlingua</a></li>
                <li class="interwiki-id"><a href="http://id.wikipedia.org/wiki/18_(angka)">Bahasa Indonesia</a></li>
                <li class="interwiki-ig"><a href="http://ig.wikipedia.org/wiki/Iri_na_asat%E1%BB%8D">Igbo</a></li>
                <li class="interwiki-is"><a href="http://is.wikipedia.org/wiki/18_(tala)">Íslenska</a></li>
                <li class="interwiki-it"><a href="http://it.wikipedia.org/wiki/18_(numero)">Italiano</a></li>
                <li class="interwiki-ja"><a href="http://ja.wikipedia.org/wiki/18">日本語</a></li>
                <li class="interwiki-ko"><a href="http://ko.wikipedia.org/wiki/18">한국어</a></li>
                <li class="interwiki-ku"><a href="http://ku.wikipedia.org/wiki/Hejde">Kurdî / كوردی</a></li>
                <li class="interwiki-la"><a href="http://la.wikipedia.org/wiki/Duodeviginti">Latina</a></li>
                <li class="interwiki-lmo"><a href="http://lmo.wikipedia.org/wiki/N%C3%BCmar_18">Lumbaart</a></li>
                <li class="interwiki-lt"><a href="http://lt.wikipedia.org/wiki/18_(skai%C4%8Dius)">Lietuvių</a></li>
                <li class="interwiki-lv"><a href="http://lv.wikipedia.org/wiki/18_(skaitlis)">Latviešu</a></li>
                <li class="interwiki-mk"><a href="http://mk.wikipedia.org/wiki/18_(%D0%B1%D1%80%D0%BE%D1%98)">Македонски</a></li>
                <li class="interwiki-ms"><a href="http://ms.wikipedia.org/wiki/18_(nombor)">Bahasa Melayu</a></li>
                <li class="interwiki-myv"><a href="http://myv.wikipedia.org/wiki/18_(%D0%BB%D0%BE%D0%B2%D0%BE%D0%BC%D0%B0_%D0%B2%D0%B0%D0%BB)">Эрзянь</a></li>
                <li class="interwiki-nah"><a href="http://nah.wikipedia.org/wiki/Caxt%C5%8Dlon%C4%93yi">Nāhuatl</a></li>
                <li class="interwiki-nap"><a href="http://nap.wikipedia.org/wiki/Dici%C3%B2tte">Nnapulitano</a></li>
                <li class="interwiki-nl"><a href="http://nl.wikipedia.org/wiki/18_(getal)">Nederlands</a></li>
                <li class="interwiki-nn"><a href="http://nn.wikipedia.org/wiki/Talet_18">‪Norsk (nynorsk)‬</a></li>
                <li class="interwiki-no"><a href="http://no.wikipedia.org/wiki/18_(tall)">‪Norsk (bokmål)‬</a></li>
                <li class="interwiki-pl"><a href="http://pl.wikipedia.org/wiki/18_(liczba)">Polski</a></li>
                <li class="interwiki-ru"><a href="http://ru.wikipedia.org/wiki/18_(%D1%87%D0%B8%D1%81%D0%BB%D0%BE)">Русский</a></li>
                <li class="interwiki-scn"><a href="http://scn.wikipedia.org/wiki/Dicidottu">Sicilianu</a></li>
                <li class="interwiki-simple"><a href="http://simple.wikipedia.org/wiki/18_(number)">Simple English</a></li>
                <li class="interwiki-sl"><a href="http://sl.wikipedia.org/wiki/18_(%C5%A1tevilo)">Slovenščina</a></li>
                <li class="interwiki-srn"><a href="http://srn.wikipedia.org/wiki/Numro_18">Sranantongo</a></li>
                <li class="interwiki-sv"><a href="http://sv.wikipedia.org/wiki/18_(tal)">Svenska</a></li>
                <li class="interwiki-th"><a href="http://th.wikipedia.org/wiki/18">ไทย</a></li>
                <li class="interwiki-uk"><a href="http://uk.wikipedia.org/wiki/18_(%D1%87%D0%B8%D1%81%D0%BB%D0%BE)">Українська</a></li>
                <li class="interwiki-vi"><a href="http://vi.wikipedia.org/wiki/18_(s%E1%BB%91)">Tiếng Việt</a></li>
                <li class="interwiki-vls"><a href="http://vls.wikipedia.org/wiki/18_(getal)">West-Vlams</a></li>
                <li class="interwiki-xh"><a href="http://xh.wikipedia.org/wiki/Ishumi_elinesibhozo">isiXhosa</a></li>
                <li class="interwiki-yi"><a href="http://yi.wikipedia.org/wiki/18_(%D7%A0%D7%95%D7%9E%D7%A2%D7%A8)">ייִדיש</a></li>
                <li class="interwiki-zh"><a href="http://zh.wikipedia.org/wiki/18">中文</a></li>
                <li class="interwiki-zh-yue"><a href="http://zh-yue.wikipedia.org/wiki/18">粵語</a></li>
            </ul>
