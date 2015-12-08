#!/bin/bash
#./flush_params.sh "$1" "$2" "$3"
mysql -u$1 -p$2 $3 < sql/00_ong.sql
mysql -u$1 -p$2 $3 < sql/01_user.sql
mysql -u$1 -p$2 $3 < sql/02_speciality.sql
mysql -u$1 -p$2 $3 < sql/03_administrator.sql
mysql -u$1 -p$2 $3 < sql/04_professional.sql
mysql -u$1 -p$2 $3 < sql/05_patient.sql
mysql -u$1 -p$2 $3 < sql/06_careSession.sql
mysql -u$1 -p$2 $3 < sql/07_schedule.sql
mysql -u$1 -p$2 $3 < sql/08_appointment.sql