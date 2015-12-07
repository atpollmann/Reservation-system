#!/bin/bash
./flush.sh
mysql -u root agendator < sql/00_ong.sql
mysql -u root agendator < sql/01_user.sql
mysql -u root agendator < sql/02_speciality.sql
mysql -u root agendator < sql/03_administrator.sql
mysql -u root agendator < sql/04_professional.sql
mysql -u root agendator < sql/05_patient.sql
mysql -u root agendator < sql/06_careSession.sql
mysql -u root agendator < sql/07_schedule.sql
mysql -u root agendator < sql/08_appointment.sql