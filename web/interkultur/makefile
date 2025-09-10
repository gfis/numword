#!/usr/bin/make

# makefile for numword/web/interkultur - Blaetter fuer die Interkulturelle Woche 2025-09-21
# @(#) $Id: makefile 974 2013-01-23 11:38:57Z gfis $
# 2025-09-10, Dr. Georg Fischer: *CH
#
APPL=numword
JAVA=java -jar ../../dist/$(APPL).jar

all: list classes index deploy
#-------------------------------------------------------------------
list:
	$(JAVA) \
	| grep -vP "java|ISO|Options" | grep -vP "^\s+\-" \
	| perl -pe 's/^\s+\w+(\,\w+)*\s*//;' \
	| tee $@.tmp
#----
classes:
	make class1 C=c  # cardinals
	make class1 C=g  # greetings
	make class1 C=m  # months
	make class1 C=s  # seasons
	make class1 C=w  # weekdays
class1:
	perl htmlist.pl -$(C) > $(C).html
index:
	perl htmlist.pl -i > index.html
deploy:
	scp index.html ?.html gfis@teherba.org:/var/www/html/teherba.org/interkultur

