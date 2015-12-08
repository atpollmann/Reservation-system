#!/bin/bash
mysql -u"$1" -p"$2" "$3" < .flush_tables.sql