BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Appointments" (
	"appid"	INTEGER,
	"pid"	TEXT NOT NULL DEFAULT ' ',
	"docid"	TEXT DEFAULT '  ',
	"posid"	TEXT DEFAULT 0,
	"depid"	INTEGER NOT NULL DEFAULT 0,
	"app_date"	INTEGER NOT NULL DEFAULT ' ',
	"app_time"	INTEGER NOT NULL DEFAULT ' ',
	"purpose"	TEXT DEFAULT ' ',
	"status"	TEXT DEFAULT ' ',
	"created_at"	txt DEFAULT ' ',
	PRIMARY KEY("appid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "Billing" (
	"billid"	INTEGER,
	"pid"	TEXT DEFAULT ' ',
	"appid"	INTEGER NOT NULL DEFAULT 0,
	"total_amount"	NUMERIC NOT NULL DEFAULT 0,
	"payment_status"	TEXT DEFAULT 'Pending',
	"payment_date"	TEXT DEFAULT ' ',
	"insurance_provider"	TEXT DEFAULT ' ',
	PRIMARY KEY("billid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "Medical_Records" (
	"mrid"	INTEGER,
	"date"	TEXT DEFAULT ' ',
	"pid"	TEXT NOT NULL DEFAULT ' ',
	"appid"	INTEGER NOT NULL DEFAULT 0,
	"docid"	TEXT DEFAULT ' ',
	"posid"	INTEGER DEFAULT 0,
	"diagnosis"	TEXT DEFAULT ' ',
	"treatment"	TEXT DEFAULT ' ',
	"prescription"	TEXT DEFAULT ' ',
	"created_at"	TEXT DEFAULT ' ',
	"depid"	INTEGER DEFAULT 0,
	PRIMARY KEY("mrid")
);
CREATE TABLE IF NOT EXISTS Medical_Records_Medicine (
    mrmid INTEGER PRIMARY key,
    medid INTEGER NOT NULL,
    dosage TEXT    
);
CREATE TABLE IF NOT EXISTS "Medicine" (
	"medid"	INTEGER,
	"name"	TEXT NOT NULL DEFAULT ' ',
	"brand"	TEXT DEFAULT ' ',
	"type"	TEXT NOT NULL DEFAULT ' ',
	"dosage"	TEXT DEFAULT ' ',
	"expiry_date"	TEXT DEFAULT ' ',
	PRIMARY KEY("medid")
);
CREATE TABLE IF NOT EXISTS "Patients_in_rooms" (
	"assid"	INTEGER,
	"pid"	TEXT DEFAULT ' ',
	"roomid"	INTEGER DEFAULT 0,
	"start_date"	TEXT DEFAULT ' ',
	"end_date"	TEXT DEFAULT ' ',
	"adid"	INTEGER DEFAULT 0,
	"createdat"	TEXT,
	PRIMARY KEY("assid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "Pharmacy" (
	"phid"	INTEGER,
	"medicine_id"	INTEGER DEFAULT 0,
	"pid"	TEXT DEFAULT ' ',
	"qty"	NUMERIC,
	"prescription_date"	TEXT DEFAULT ' ',
	PRIMARY KEY("phid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "Prescription" (
	"pres_id"	INTEGER,
	"pid"	TEXT DEFAULT ' ',
	"docid"	TEXT DEFAULT ' ',
	"posid"	INTEGER DEFAULT 0,
	"presdate"	TEXT DEFAULT ' ',
	"medid"	INTEGER DEFAULT 0,
	"dosage"	TEXT DEFAULT ' ',
	"frequency"	TEXT DEFAULT ' ',
	"duration"	TEXT DEFAULT ' ',
	"notes"	TEXT DEFAULT ' ',
	"depid"	INTEGER DEFAULT 0,
	PRIMARY KEY("pres_id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "admission" (
	"adid"	INTEGER DEFAULT 0,
	"add_date"	TEXT DEFAULT ' ',
	"discharge_date"	NUMERIC DEFAULT ' ',
	"reason"	TEXT DEFAULT ' ',
	"pid"	TEXT DEFAULT ' ',
	"docid"	TEXT DEFAULT ' ',
	"posid"	INTEGER DEFAULT 0,
	"depid"	INTEGER DEFAULT 0,
	"status"	TEXT DEFAULT ' ',
	"nurseid"	INTEGER DEFAULT 0,
	"nposid"	INTEGER DEFAULT 0,
	PRIMARY KEY("adid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "cities" (
	"cid"	INTEGER,
	"name"	TEXT DEFAULT ' ',
	PRIMARY KEY("cid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "countries" (
	"coid"	TEXT NOT NULL DEFAULT ' ',
	"countryname"	TEXT NOT NULL DEFAULT ' ',
	"code"	TEXT DEFAULT ' ',
	PRIMARY KEY("coid")
);
CREATE TABLE IF NOT EXISTS "department_employee" (
	"demid"	INTEGER,
	"depid"	INTEGER NOT NULL DEFAULT 0,
	"empid"	TEXT NOT NULL DEFAULT ' ',
	"posid"	INTEGER DEFAULT 0,
	PRIMARY KEY("demid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "departments" (
	"depid"	INTEGER,
	"depname"	TEXT DEFAULT ' ',
	"location"	TEXT DEFAULT ' ',
	PRIMARY KEY("depid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "diagnosis" (
	"diid"	INTEGER,
	"pid"	TEXT DEFAULT ' ',
	"docid"	TEXT DEFAULT ' ',
	"posid"	INTEGER DEFAULT 0,
	"ddate"	TEXT DEFAULT ' ',
	"description"	TEXT DEFAULT ' ',
	"diagnosis"	TEXT DEFAULT ' ',
	"depid"	INTEGER DEFAULT 0,
	PRIMARY KEY("diid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "docappticancel" (
	"cid"	INTEGER,
	"empid"	TEXT DEFAULT ' ',
	"posid"	INTEGER DEFAULT 0,
	"startdate"	TEXT DEFAULT ' ',
	"enddate"	TEXT DEFAULT ' ',
	"starttime"	TEXT DEFAULT ' ',
	"endtime"	TEXT DEFAULT ' ',
	"depid"	INTEGER DEFAULT 0,
	PRIMARY KEY("cid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "doctorapptimes" (
	"tid"	INTEGER DEFAULT 0,
	"empid"	TEXT DEFAULT ' ',
	"posid"	INTEGER DEFAULT 0,
	"dayname"	INTEGER DEFAULT 0,
	"starttime"	TEXT DEFAULT ' ',
	"endtime"	TEXT DEFAULT ' ',
	"depid"	INTEGER DEFAULT 0,
	PRIMARY KEY("tid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "employee_positions" (
	"eposid"	INTEGER,
	"posid"	INTEGER DEFAULT 0,
	"empid"	TEXT DEFAULT ' ',
	PRIMARY KEY("eposid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "employee_specialization" (
	"emsid"	INTEGER,
	"sid"	INTEGER DEFAULT 0,
	"empid"	TEXT DEFAULT ' ',
	PRIMARY KEY("emsid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "employees" (
	"empid"	TEXT DEFAULT ' ' UNIQUE,
	"name"	TEXT DEFAULT ' ',
	"phone"	TEXT DEFAULT ' ',
	"email"	TEXT DEFAULT ' ',
	"gender"	TEXT DEFAULT ' ',
	"country"	TEXT DEFAULT ' ',
	"cid"	INTEGER,
	"street"	TEXT DEFAULT ' ',
	"dob"	TEXT DEFAULT ' ',
	"dateofjoin"	TEXT DEFAULT ' ',
	"remark"	TEXT DEFAULT ' ',
	"emptype"	TEXT DEFAULT ' ',
	"status"	TEXT DEFAULT ' '
);
CREATE TABLE IF NOT EXISTS "patient" (
	"pid"	TEXT DEFAULT ' ' UNIQUE,
	"pname"	TEXT DEFAULT ' ',
	"dob"	TEXT DEFAULT ' ',
	"gender"	TEXT DEFAULT ' ',
	"blood"	TEXT DEFAULT ' ',
	"country"	TEXT DEFAULT ' ',
	"cid"	INTEGER DEFAULT ' ',
	"street"	TEXT DEFAULT ' ',
	"phone"	TEXT DEFAULT ' ',
	"email"	TEXT DEFAULT ' ',
	"remark"	TEXT DEFAULT ' ',
	"status"	TEXT DEFAULT ' ',
	"createat"	TEXT DEFAULT ' '
);
CREATE TABLE IF NOT EXISTS "patienttests" (
	"peid"	INTEGER,
	"tid"	INTEGER DEFAULT 0,
	"result"	TEXT DEFAULT ' ',
	"testdate"	TEXT DEFAULT ' ',
	"pid"	TEXT DEFAULT ' ',
	"docid"	INTEGER DEFAULT 0,
	"posid"	INTEGER DEFAULT 0,
	"comment"	TEXT DEFAULT ' ',
	"depid"	INTEGER DEFAULT 0,
	PRIMARY KEY("peid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "positions" (
	"posid"	INTEGER,
	"name"	TEXT DEFAULT ' ',
	"type"	TEXT DEFAULT ' ',
	PRIMARY KEY("posid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "relative" (
	"rid"	INTEGER,
	"rname"	TEXT DEFAULT ' ',
	"rtype"	TEXT DEFAULT ' ',
	"rphone"	TEXT DEFAULT ' ',
	"rpid"	INTEGER NOT NULL DEFAULT 0,
	PRIMARY KEY("rid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "rooms" (
	"roomid"	INTEGER DEFAULT 0,
	"roomname"	TEXT DEFAULT ' ',
	"capacity"	INTEGER DEFAULT 0,
	"occupied"	INTEGER DEFAULT 0,
	"roomtype"	TEXT DEFAULT ' ',
	"depid"	INTEGER DEFAULT 0,
	"status"	TEXT DEFAULT ' ',
	PRIMARY KEY("roomid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "specializations" (
	"sid"	INTEGER,
	"name"	TEXT DEFAULT ' ',
	"type"	TEXT DEFAULT ' ',
	PRIMARY KEY("sid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS sqlite_stat4(tbl,idx,neq,nlt,ndlt,sample);
CREATE TABLE IF NOT EXISTS "test" (
	"tid"	INTEGER,
	"tname"	TEXT NOT NULL,
	"price"	NUMERIC,
	PRIMARY KEY("tid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "treatments" (
	"tid"	INTEGER,
	"tdate"	INTEGER,
	"tname"	TEXT,
	"adid"	INTEGER,
	"description"	INTEGER,
	PRIMARY KEY("tid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "visit_records" (
	"vid"	INTEGER,
	"vdate"	TEXT,
	"pid"	TEXT,
	"docid"	INTEGER,
	"posid"	INTEGER,
	"symptoms"	TEXT,
	"disease"	TEXT,
	"diagnosis"	TEXT,
	"next_vdate"	TEXT,
	"adid"	TEXT,
	"depid"	INTEGER,
	PRIMARY KEY("vid" AUTOINCREMENT)
);
INSERT INTO "Appointments" ("appid","pid","docid","posid","depid","app_date","app_time","purpose","status","created_at") VALUES (7,'P000001','D001','18',1,'2026-03-02','09:00','he is sick','Pending','2026-03-09');
INSERT INTO "Appointments" ("appid","pid","docid","posid","depid","app_date","app_time","purpose","status","created_at") VALUES (8,'P000002','D001','18',1,'2026-03-04','12:30','She is sick','Pending','2026-03-09');
INSERT INTO "Medical_Records" ("mrid","date","pid","appid","docid","posid","diagnosis","treatment","prescription","created_at","depid") VALUES (1,'2026-03-10','P000002',8,'D001',18,'Routine checkup','None','None','2026-03-10',1);
INSERT INTO "Medical_Records" ("mrid","date","pid","appid","docid","posid","diagnosis","treatment","prescription","created_at","depid") VALUES (2,'2026-03-05','P000002',8,'D001',18,'Hypertension','Lifestyle changes, medication','Lisinopril','2026-03-10',1);
INSERT INTO "Medicine" ("medid","name","brand","type","dosage","expiry_date") VALUES (1,'Paracetamol','BrandA','Tablet','500mg','2025-12-31');
INSERT INTO "Medicine" ("medid","name","brand","type","dosage","expiry_date") VALUES (2,'Ibuprofen','BrandB','Capsule','250mg','2025-06-30');
INSERT INTO "Medicine" ("medid","name","brand","type","dosage","expiry_date") VALUES (3,'Cough Syrup','BrandC','Liquid','10ml','2025-03-31');
INSERT INTO "Medicine" ("medid","name","brand","type","dosage","expiry_date") VALUES (4,'Amoxicillin','BrandD','Capsule','200mg','2026-01-31');
INSERT INTO "Medicine" ("medid","name","brand","type","dosage","expiry_date") VALUES (5,'Aspirin','BrandE','Tablet','500mg','2025-11-30');
INSERT INTO "Medicine" ("medid","name","brand","type","dosage","expiry_date") VALUES (6,'Ciprofloxacin','BrandF','Tablet','500mg','2025-05-31');
INSERT INTO "Medicine" ("medid","name","brand","type","dosage","expiry_date") VALUES (7,'Vitamin C','BrandG','Capsule','20mg','2024-12-31');
INSERT INTO "Medicine" ("medid","name","brand","type","dosage","expiry_date") VALUES (8,'Doxycycline','BrandH','Capsule','100mg','2026-07-15');
INSERT INTO "Medicine" ("medid","name","brand","type","dosage","expiry_date") VALUES (9,'Metformin','BrandI','Capsule','500mg','2026-02-28');
INSERT INTO "Medicine" ("medid","name","brand","type","dosage","expiry_date") VALUES (10,'Prednisone','BrandJ','Tablet','0.5mg','2024-11-30');
INSERT INTO "Prescription" ("pres_id","pid","docid","posid","presdate","medid","dosage","frequency","duration","notes","depid") VALUES (7,'P000002','D001',18,'2026-03-11',4,'450 mg','Every 8 hours','7 days','For bacterial infection
',1);
INSERT INTO "Prescription" ("pres_id","pid","docid","posid","presdate","medid","dosage","frequency","duration","notes","depid") VALUES (8,'P000002','D001',18,'2026-03-15',5,'200 mg','Every 4 hours','3 days','For fever and pain
',1);
INSERT INTO "Prescription" ("pres_id","pid","docid","posid","presdate","medid","dosage","frequency","duration","notes","depid") VALUES (9,'P000002','D001',18,'2026-03-20',1,'500 mg','Every 6 hours','5 days','For pain relief
',1);
INSERT INTO "admission" ("adid","add_date","discharge_date","reason","pid","docid","posid","depid","status","nurseid","nposid") VALUES (8,'2026-03-10','','she is sick','P000009','D001',18,1,'In progress','N004','');
INSERT INTO "cities" ("cid","name") VALUES (4,'Budapest');
INSERT INTO "cities" ("cid","name") VALUES (5,'London');
INSERT INTO "cities" ("cid","name") VALUES (9,'Paris');
INSERT INTO "cities" ("cid","name") VALUES (13,'San Jose CA');
INSERT INTO "cities" ("cid","name") VALUES (14,'Vienna');
INSERT INTO "cities" ("cid","name") VALUES (15,'Kecskemét');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('AFG','Afghanistan','AF');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ALA','Åland','AX');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ALB','Albania','AL');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('DZA','Algeria','DZ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ASM','American Samoa','AS');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('AND','Andorra','AD');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('AGO','Angola','AO');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('AIA','Anguilla','AI');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ATA','Antarctica','AQ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ATG','Antigua and Barbuda','AG');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ARG','Argentina','AR');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ARM','Armenia','AM');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ABW','Aruba','AW');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('AUS','Australia','AU');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('AUT','Austria','AT');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('AZE','Azerbaijan','AZ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BHS','Bahamas','BS');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BHR','Bahrain','BH');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BGD','Bangladesh','BD');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BRB','Barbados','BB');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BLR','Belarus','BY');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BEL','Belgium','BE');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BLZ','Belize','BZ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BEN','Benin','BJ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BMU','Bermuda','BM');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BTN','Bhutan','BT');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BOL','Bolivia','BO');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BES','Bonaire','BQ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BIH','Bosnia and Herzegovina','BA');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BWA','Botswana','BW');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BVT','Bouvet Island','BV');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BRA','Brazil','BR');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('IOT','British Indian Ocean Territory','IO');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('VGB','British Virgin Islands','VG');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BRN','Brunei','BN');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BGR','Bulgaria','BG');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BFA','Burkina Faso','BF');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BDI','Burundi','BI');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('KHM','Cambodia','KH');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('CMR','Cameroon','CM');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('CAN','Canada','CA');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('CPV','Cape Verde','CV');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('CYM','Cayman Islands','KY');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('CAF','Central African Republic','CF');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('TCD','Chad','TD');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('CHL','Chile','CL');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('CHN','China','CN');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('CXR','Christmas Island','CX');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('CCK','Cocos [Keeling] Islands','CC');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('COL','Colombia','CO');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('COM','Comoros','KM');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('COK','Cook Islands','CK');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('CRI','Costa Rica','CR');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('HRV','Croatia','HR');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('CUB','Cuba','CU');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('CUW','Curacao','CW');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('CYP','Cyprus','CY');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('CZE','Czech Republic','CZ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('COD','Democratic Republic of the Congo','CD');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('DNK','Denmark','DK');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('DJI','Djibouti','DJ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('DMA','Dominica','DM');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('DOM','Dominican Republic','DO');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('TLS','East Timor','TL');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ECU','Ecuador','EC');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('EGY','Egypt','EG');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SLV','El Salvador','SV');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('GNQ','Equatorial Guinea','GQ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ERI','Eritrea','ER');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('EST','Estonia','EE');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ETH','Ethiopia','ET');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('FLK','Falkland Islands','FK');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('FRO','Faroe Islands','FO');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('FJI','Fiji','FJ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('FIN','Finland','FI');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('FRA','France','FR');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('GUF','French Guiana','GF');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('PYF','French Polynesia','PF');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ATF','French Southern Territories','TF');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('GAB','Gabon','GA');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('GMB','Gambia','GM');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('GEO','Georgia','GE');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('DEU','Germany','DE');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('GHA','Ghana','GH');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('GIB','Gibraltar','GI');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('GRC','Greece','GR');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('GRL','Greenland','GL');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('GRD','Grenada','GD');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('GLP','Guadeloupe','GP');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('GUM','Guam','GU');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('GTM','Guatemala','GT');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('GGY','Guernsey','GG');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('GIN','Guinea','GN');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('GNB','Guinea-Bissau','GW');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('GUY','Guyana','GY');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('HTI','Haiti','HT');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('HMD','Heard Island and McDonald Islands','HM');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('HND','Honduras','HN');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('HKG','Hong Kong','HK');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('HUN','Hungary','HU');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ISL','Iceland','IS');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('IND','India','IN');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('IDN','Indonesia','ID');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('IRN','Iran','IR');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('IRQ','Iraq','IQ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('IRL','Ireland','IE');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('IMN','Isle of Man','IM');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ISR','Israel','IL');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ITA','Italy','IT');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('CIV','Ivory Coast','CI');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('JAM','Jamaica','JM');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('JPN','Japan','JP');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('JEY','Jersey','JE');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('JOR','Jordan','JO');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('KAZ','Kazakhstan','KZ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('KEN','Kenya','KE');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('KIR','Kiribati','KI');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('XKX','Kosovo','XK');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('KWT','Kuwait','KW');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('KGZ','Kyrgyzstan','KG');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('LAO','Laos','LA');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('LVA','Latvia','LV');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('LBN','Lebanon','LB');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('LSO','Lesotho','LS');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('LBR','Liberia','LR');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('LBY','Libya','LY');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('LIE','Liechtenstein','LI');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('LTU','Lithuania','LT');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('LUX','Luxembourg','LU');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MAC','Macao','MO');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MKD','Macedonia','MK');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MDG','Madagascar','MG');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MWI','Malawi','MW');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MYS','Malaysia','MY');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MDV','Maldives','MV');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MLI','Mali','ML');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MLT','Malta','MT');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MHL','Marshall Islands','MH');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MTQ','Martinique','MQ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MRT','Mauritania','MR');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MUS','Mauritius','MU');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MYT','Mayotte','YT');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MEX','Mexico','MX');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('FSM','Micronesia','FM');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MDA','Moldova','MD');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MCO','Monaco','MC');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MNG','Mongolia','MN');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MNE','Montenegro','ME');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MSR','Montserrat','MS');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MAR','Morocco','MA');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MOZ','Mozambique','MZ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MMR','Myanmar [Burma]','MM');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('NAM','Namibia','NA');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('NRU','Nauru','NR');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('NPL','Nepal','NP');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('NLD','Netherlands','NL');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('NCL','New Caledonia','NC');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('NZL','New Zealand','NZ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('NIC','Nicaragua','NI');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('NER','Niger','NE');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('NGA','Nigeria','NG');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('NIU','Niue','NU');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('NFK','Norfolk Island','NF');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('PRK','North Korea','KP');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MNP','Northern Mariana Islands','MP');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('NOR','Norway','NO');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('OMN','Oman','OM');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('PAK','Pakistan','PK');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('PLW','Palau','PW');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('PSE','Palestine','PS');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('PAN','Panama','PA');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('PNG','Papua New Guinea','PG');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('PRY','Paraguay','PY');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('PER','Peru','PE');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('PHL','Philippines','PH');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('PCN','Pitcairn Islands','PN');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('POL','Poland','PL');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('PRT','Portugal','PT');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('PRI','Puerto Rico','PR');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('QAT','Qatar','QA');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('COG','Republic of the Congo','CG');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('REU','Réunion','RE');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ROU','Romania','RO');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('RUS','Russia','RU');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('RWA','Rwanda','RW');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BLM','Saint Barthélemy','BL');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SHN','Saint Helena','SH');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('KNA','Saint Kitts and Nevis','KN');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('LCA','Saint Lucia','LC');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MAF','Saint Martin','MF');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SPM','Saint Pierre and Miquelon','PM');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('VCT','Saint Vincent and the Grenadines','VC');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('WSM','Samoa','WS');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SMR','San Marino','SM');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('STP','São Tomé and Príncipe','ST');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SAU','Saudi Arabia','SA');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SEN','Senegal','SN');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SRB','Serbia','RS');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SYC','Seychelles','SC');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SLE','Sierra Leone','SL');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SGP','Singapore','SG');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SXM','Sint Maarten','SX');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SVK','Slovakia','SK');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SVN','Slovenia','SI');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SLB','Solomon Islands','SB');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SOM','Somalia','SO');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ZAF','South Africa','ZA');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SGS','South Georgia and the South Sandwich Islands','GS');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('KOR','South Korea','KR');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SSD','South Sudan','SS');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ESP','Spain','ES');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('LKA','Sri Lanka','LK');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SDN','Sudan','SD');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SUR','Suriname','SR');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SJM','Svalbard and Jan Mayen','SJ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SWZ','Swaziland','SZ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SWE','Sweden','SE');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('CHE','Switzerland','CH');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SYR','Syria','SY');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('TWN','Taiwan','TW');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('TJK','Tajikistan','TJ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('TZA','Tanzania','TZ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('THA','Thailand','TH');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('TGO','Togo','TG');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('TKL','Tokelau','TK');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('TON','Tonga','TO');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('TTO','Trinidad and Tobago','TT');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('TUN','Tunisia','TN');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('TUR','Turkey','TR');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('TKM','Turkmenistan','TM');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('TCA','Turks and Caicos Islands','TC');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('TUV','Tuvalu','TV');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('UMI','U.S. Minor Outlying Islands','UM');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('VIR','U.S. Virgin Islands','VI');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('UGA','Uganda','UG');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('UKR','Ukraine','UA');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ARE','United Arab Emirates','AE');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('GBR','United Kingdom','GB');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('USA','United States','US');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('URY','Uruguay','UY');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('UZB','Uzbekistan','UZ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('VUT','Vanuatu','VU');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('VAT','Vatican City','VA');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('VEN','Venezuela','VE');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('VNM','Vietnam','VN');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('WLF','Wallis and Futuna','WF');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ESH','Western Sahara','EH');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('YEM','Yemen','YE');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ZMB','Zambia','ZM');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ZWE','Zimbabwe','ZW');
INSERT INTO "department_employee" ("demid","depid","empid","posid") VALUES (10,3,'D004',78);
INSERT INTO "department_employee" ("demid","depid","empid","posid") VALUES (11,24,'D007',80);
INSERT INTO "department_employee" ("demid","depid","empid","posid") VALUES (12,1,'D001',18);
INSERT INTO "departments" ("depid","depname","location") VALUES (1,'Cardiology','');
INSERT INTO "departments" ("depid","depname","location") VALUES (2,'Neurology','');
INSERT INTO "departments" ("depid","depname","location") VALUES (3,'Oncology','');
INSERT INTO "departments" ("depid","depname","location") VALUES (4,'Pediatrics','');
INSERT INTO "departments" ("depid","depname","location") VALUES (5,'Radiology','');
INSERT INTO "departments" ("depid","depname","location") VALUES (6,'Orthopedics','');
INSERT INTO "departments" ("depid","depname","location") VALUES (7,'Dermatology','');
INSERT INTO "departments" ("depid","depname","location") VALUES (8,'Gynecology','');
INSERT INTO "departments" ("depid","depname","location") VALUES (9,'Urology','');
INSERT INTO "departments" ("depid","depname","location") VALUES (10,'Ophthalmology','');
INSERT INTO "departments" ("depid","depname","location") VALUES (11,'Gastroenterology','');
INSERT INTO "departments" ("depid","depname","location") VALUES (12,'Hematology','');
INSERT INTO "departments" ("depid","depname","location") VALUES (13,'Endocrinology','');
INSERT INTO "departments" ("depid","depname","location") VALUES (14,'Pulmonology','');
INSERT INTO "departments" ("depid","depname","location") VALUES (15,'Nephrology','');
INSERT INTO "departments" ("depid","depname","location") VALUES (16,'Rheumatology','');
INSERT INTO "departments" ("depid","depname","location") VALUES (17,'Infectious Diseases','');
INSERT INTO "departments" ("depid","depname","location") VALUES (18,'Allergy and Immunology','');
INSERT INTO "departments" ("depid","depname","location") VALUES (19,'Anesthesiology','');
INSERT INTO "departments" ("depid","depname","location") VALUES (20,'Pathology','');
INSERT INTO "departments" ("depid","depname","location") VALUES (21,'Psychiatry','');
INSERT INTO "departments" ("depid","depname","location") VALUES (22,'Surgery','');
INSERT INTO "departments" ("depid","depname","location") VALUES (23,'Emergency Medicine','');
INSERT INTO "departments" ("depid","depname","location") VALUES (24,'General surgery','');
INSERT INTO "diagnosis" ("diid","pid","docid","posid","ddate","description","diagnosis","depid") VALUES (1,'1.0','1',NULL,'2024-03-02','Routine checkup','Healthy',NULL);
INSERT INTO "diagnosis" ("diid","pid","docid","posid","ddate","description","diagnosis","depid") VALUES (2,'2.0','2',NULL,'2024-02-25','Fever and cough','Respiratory infection',NULL);
INSERT INTO "diagnosis" ("diid","pid","docid","posid","ddate","description","diagnosis","depid") VALUES (3,'3.0','3',NULL,'2024-03-08','High blood pressure','Hypertension',NULL);
INSERT INTO "diagnosis" ("diid","pid","docid","posid","ddate","description","diagnosis","depid") VALUES (4,'4.0','4',NULL,'2024-03-12','Diabetes management','Type 2 Diabetes',NULL);
INSERT INTO "diagnosis" ("diid","pid","docid","posid","ddate","description","diagnosis","depid") VALUES (5,'5.0','5',NULL,'2024-03-14','Cholesterol screening','High cholesterol',NULL);
INSERT INTO "diagnosis" ("diid","pid","docid","posid","ddate","description","diagnosis","depid") VALUES (6,'6.0','6',NULL,'2024-03-05','Thyroid disorder','Hypothyroidism',NULL);
INSERT INTO "diagnosis" ("diid","pid","docid","posid","ddate","description","diagnosis","depid") VALUES (7,'7.0','7',NULL,'2024-03-09','Heart condition','Arrhythmia',NULL);
INSERT INTO "diagnosis" ("diid","pid","docid","posid","ddate","description","diagnosis","depid") VALUES (8,'8.0','8',NULL,'2024-03-07','High blood pressure','Hypertension',NULL);
INSERT INTO "diagnosis" ("diid","pid","docid","posid","ddate","description","diagnosis","depid") VALUES (9,'9.0','9',NULL,'2024-03-11','Cholesterol screening','High cholesterol',NULL);
INSERT INTO "diagnosis" ("diid","pid","docid","posid","ddate","description","diagnosis","depid") VALUES (10,'10.0','10',NULL,'2024-03-15','High blood pressure','Hypertension',NULL);
INSERT INTO "docappticancel" ("cid","empid","posid","startdate","enddate","starttime","endtime","depid") VALUES (2,'D001',18,'2026-03-23','2026-03-23','08:00','10:00',1);
INSERT INTO "doctorapptimes" ("tid","empid","posid","dayname","starttime","endtime","depid") VALUES (5,'D001',18,'Wednesday','12:00','16:00',1);
INSERT INTO "doctorapptimes" ("tid","empid","posid","dayname","starttime","endtime","depid") VALUES (6,'D001',18,'Wednesday','13:00','17:00',1);
INSERT INTO "doctorapptimes" ("tid","empid","posid","dayname","starttime","endtime","depid") VALUES (7,'D001',18,'Monday','08:00','12:00',1);
INSERT INTO "employee_positions" ("eposid","posid","empid") VALUES (8,18,'D001');
INSERT INTO "employee_positions" ("eposid","posid","empid") VALUES (9,76,'D002');
INSERT INTO "employee_positions" ("eposid","posid","empid") VALUES (10,77,'D003');
INSERT INTO "employee_positions" ("eposid","posid","empid") VALUES (11,78,'D004');
INSERT INTO "employee_positions" ("eposid","posid","empid") VALUES (12,79,'D005');
INSERT INTO "employee_positions" ("eposid","posid","empid") VALUES (13,69,'D006');
INSERT INTO "employee_positions" ("eposid","posid","empid") VALUES (14,80,'D007');
INSERT INTO "employee_positions" ("eposid","posid","empid") VALUES (15,45,'N001');
INSERT INTO "employee_positions" ("eposid","posid","empid") VALUES (16,43,'N002');
INSERT INTO "employee_positions" ("eposid","posid","empid") VALUES (17,32,'N003');
INSERT INTO "employee_positions" ("eposid","posid","empid") VALUES (18,36,'N004');
INSERT INTO "employee_positions" ("eposid","posid","empid") VALUES (19,28,'N005');
INSERT INTO "employee_positions" ("eposid","posid","empid") VALUES (20,46,'N006');
INSERT INTO "employee_positions" ("eposid","posid","empid") VALUES (21,49,'S001');
INSERT INTO "employee_positions" ("eposid","posid","empid") VALUES (22,68,'S002');
INSERT INTO "employee_positions" ("eposid","posid","empid") VALUES (23,81,'S003');
INSERT INTO "employee_positions" ("eposid","posid","empid") VALUES (24,62,'S004');
INSERT INTO "employee_positions" ("eposid","posid","empid") VALUES (25,48,'S005');
INSERT INTO "employee_positions" ("eposid","posid","empid") VALUES (26,60,'S006');
INSERT INTO "employee_specialization" ("emsid","sid","empid") VALUES (8,2,'D001');
INSERT INTO "employee_specialization" ("emsid","sid","empid") VALUES (9,9,'D002');
INSERT INTO "employee_specialization" ("emsid","sid","empid") VALUES (10,53,'D003');
INSERT INTO "employee_specialization" ("emsid","sid","empid") VALUES (11,5,'D004');
INSERT INTO "employee_specialization" ("emsid","sid","empid") VALUES (12,63,'D005');
INSERT INTO "employee_specialization" ("emsid","sid","empid") VALUES (13,65,'D006');
INSERT INTO "employee_specialization" ("emsid","sid","empid") VALUES (14,7,'D007');
INSERT INTO "employee_specialization" ("emsid","sid","empid") VALUES (15,79,'N001');
INSERT INTO "employee_specialization" ("emsid","sid","empid") VALUES (16,80,'N002');
INSERT INTO "employee_specialization" ("emsid","sid","empid") VALUES (17,76,'N003');
INSERT INTO "employee_specialization" ("emsid","sid","empid") VALUES (18,95,'N004');
INSERT INTO "employee_specialization" ("emsid","sid","empid") VALUES (19,76,'N005');
INSERT INTO "employee_specialization" ("emsid","sid","empid") VALUES (20,84,'N006');
INSERT INTO "employee_specialization" ("emsid","sid","empid") VALUES (21,146,'S001');
INSERT INTO "employee_specialization" ("emsid","sid","empid") VALUES (22,148,'S002');
INSERT INTO "employee_specialization" ("emsid","sid","empid") VALUES (23,12,'S003');
INSERT INTO "employee_specialization" ("emsid","sid","empid") VALUES (24,147,'S004');
INSERT INTO "employee_specialization" ("emsid","sid","empid") VALUES (25,18,'S005');
INSERT INTO "employee_specialization" ("emsid","sid","empid") VALUES (26,147,'S006');
INSERT INTO "employees" ("empid","name","phone","email","gender","country","cid","street","dob","dateofjoin","remark","emptype","status") VALUES ('D001','Dr. John Smith','123-456-7890','john.smith@gmail.com','Male','United Kingdom',5,'Downing street 10.','1964-01-30','2025-03-03','','d','Active');
INSERT INTO "employees" ("empid","name","phone","email","gender","country","cid","street","dob","dateofjoin","remark","emptype","status") VALUES ('D002','Dr. Sarah Johnson','123-456-7891','sarah.johnson@gmail.com','Female','United Kingdom',5,'Oxford street 10.','1980-01-31','2026-01-03','','d','Active');
INSERT INTO "employees" ("empid","name","phone","email","gender","country","cid","street","dob","dateofjoin","remark","emptype","status") VALUES ('D003','Dr. Michael Brown','123-456-7892','michael.brown@gmail.com','Male','United Kingdom',5,'Piccadilly Circus 11.','1990-12-27','2025-03-03','','d','Active');
INSERT INTO "employees" ("empid","name","phone","email","gender","country","cid","street","dob","dateofjoin","remark","emptype","status") VALUES ('D004','Dr. James Wilson','123-456-7894','james.wilson@gmail.com','Male','United Kingdom',5,' Regent Street 23.','1998-04-30','2024-03-03','','d','Active');
INSERT INTO "employees" ("empid","name","phone","email","gender","country","cid","street","dob","dateofjoin","remark","emptype","status") VALUES ('D005','Dr. Noha Saber','123-456-7897','noha.saber@gmail.com','Female','United Kingdom',5,'Abbey Road 15.','2001-01-01','2024-03-03','','d','Active');
INSERT INTO "employees" ("empid","name","phone","email","gender","country","cid","street","dob","dateofjoin","remark","emptype","status") VALUES ('D006','Dr. Tamer Hosny','123-456-7898','tamer.hosny@gmail.com','Male','United Kingdom',5,'Portobello Road 25.','1997-10-10','2020-03-03','','d','Active');
INSERT INTO "employees" ("empid","name","phone","email","gender","country","cid","street","dob","dateofjoin","remark","emptype","status") VALUES ('D007','Dr. Frank Martinez','555-5566 -11','frank.martinez@gmail.com','Male','United Kingdom',5,'Abchurch Lane  12.','1999-12-01','2023-03-03','','d','Active');
INSERT INTO "employees" ("empid","name","phone","email","gender","country","cid","street","dob","dateofjoin","remark","emptype","status") VALUES ('N001','Helen Rosenthal','1156-234-22','helen.rosenthal@gmail.com','Female','United Arab Emirates',5,'Oxford street 34.','1998-12-31','2024-03-04','','n','Active');
INSERT INTO "employees" ("empid","name","phone","email","gender","country","cid","street","dob","dateofjoin","remark","emptype","status") VALUES ('N002','Margaret Houlihan','234 222 345','margaret.houlihan@gmail.com','Female','United Kingdom',5,'Trafalgar square 48.','2001-01-23','2025-03-04','','n','Active');
INSERT INTO "employees" ("empid","name","phone","email","gender","country","cid","street","dob","dateofjoin","remark","emptype","status") VALUES ('N003','Bonnie Hunt','567 349 25','bonnie.hunt@gmail.com','Female','United Kingdom',5,'Baker street 11.','2003-06-01','2024-03-04','','n','Active');
INSERT INTO "employees" ("empid","name","phone","email","gender","country","cid","street","dob","dateofjoin","remark","emptype","status") VALUES ('N004','Naomi Judd','234 789 44','naomi.judd@gmail.com','Female','United Kingdom',5,'Bond street 45.','1995-03-03','2020-03-04','','n','Active');
INSERT INTO "employees" ("empid","name","phone","email","gender","country","cid","street","dob","dateofjoin","remark","emptype","status") VALUES ('N005','Mary Benjamin','234 666 899','mary.benjamin@gmail.com','Female','United Kingdom',5,'Piccadilly 23.','1995-03-03','2022-03-04','','n','Active');
INSERT INTO "employees" ("empid","name","phone","email","gender","country","cid","street","dob","dateofjoin","remark","emptype","status") VALUES ('N006','Ann Perkins','111 222 333','ann.perkins@gmail.com','Female','United Kingdom',5,'Fleet street 67.','1997-11-26','2018-03-04','','n','Active');
INSERT INTO "employees" ("empid","name","phone","email","gender","country","cid","street","dob","dateofjoin","remark","emptype","status") VALUES ('S001','Grace Moore','234 77 888','grace.moore@gmail.com','Female','United Kingdom',5,'Denmark street 55.','1996-12-31','2020-03-04','','s','Active');
INSERT INTO "employees" ("empid","name","phone","email","gender","country","cid","street","dob","dateofjoin","remark","emptype","status") VALUES ('S002',' Helen Taylor','2244 6677','helen.taylor@gmail.com','Female','United Kingdom',5,'Gerrard street 88.','1999-01-01','2021-03-04','','s','Active');
INSERT INTO "employees" ("empid","name","phone","email","gender","country","cid","street","dob","dateofjoin","remark","emptype","status") VALUES ('S003','Daniel Mukinu','2435 5566','daniel.mukini@gmail.com','Male','United Kingdom',5,'Bywater street 34.','1998-02-03','2021-03-04','','s','Active');
INSERT INTO "employees" ("empid","name","phone","email","gender","country","cid","street","dob","dateofjoin","remark","emptype","status") VALUES ('S004','Fred Wanjala','234 77 677','fred.wanjala@gmail.com','Male','United Kingdom',5,'Jermyn street 45.','1996-12-21','2020-03-04','','s','Active');
INSERT INTO "employees" ("empid","name","phone","email","gender","country","cid","street","dob","dateofjoin","remark","emptype","status") VALUES ('S005','Albert Kamau','4567 445','albert.kamau@gmail.com','Male','United Kingdom',5,'Old Compton street 66.','1999-09-09','2020-03-04','','s','Active');
INSERT INTO "employees" ("empid","name","phone","email","gender","country","cid","street","dob","dateofjoin","remark","emptype","status") VALUES ('S006','Jones Jim','222 666 999','jones.jim@gmail.com','Male','United Arab Emirates',5,'Baker street 45.','1994-10-10','2024-03-04','','s','Active');
INSERT INTO "patient" ("pid","pname","dob","gender","blood","country","cid","street","phone","email","remark","status","createat") VALUES ('P000001','Cristian Fischer','1978-09-08','Male','A+','United Kingdom',5,'Oxford street 666.','222 345 55','cristian.fischer@patientmail.com','','Active','2024-03-04');
INSERT INTO "patient" ("pid","pname","dob","gender","blood","country","cid","street","phone","email","remark","status","createat") VALUES ('P000002','Rosalie Bradley','1982-11-17','Female','A+','United Kingdom',15,'Kossuth street 67.','20 34443 45','rosalie.bradley@patientmail.com','','Active','2026-03-04');
INSERT INTO "patient" ("pid","pname","dob","gender","blood","country","cid","street","phone","email","remark","status","createat") VALUES ('P000003','Richard Cameron','1970-05-22','Male','B+','United Kingdom',5,'Baker street 355.','2255 55656','richard.cameron@patientmail.com','','Active','2026-03-04');
INSERT INTO "patient" ("pid","pname","dob","gender","blood","country","cid","street","phone","email","remark","status","createat") VALUES ('P000004','Maci Randall','1993-07-12','Female','AB+','United Kingdom',5,'Baker street 455.','222 67778','maci.randall@patientmail.com','','Active','2026-03-04');
INSERT INTO "patient" ("pid","pname","dob","gender","blood","country","cid","street","phone","email","remark","status","createat") VALUES ('P000005','Trenton Kelley','1985-01-30','Male','A+','United Kingdom',5,'Piccadilly circus 12','123 445 777','trenton.kelley@patientmail.com','','Active','2026-03-04');
INSERT INTO "patient" ("pid","pname","dob","gender","blood","country","cid","street","phone","email","remark","status","createat") VALUES ('P000006','Harmony Sherman','1973-12-05','Female','A+','United Kingdom',5,'Trafalgar  square 23.','223 455 887','harmony.sherman@patientmail.com','more illness','Active','2026-03-04');
INSERT INTO "patient" ("pid","pname","dob","gender","blood","country","cid","street","phone","email","remark","status","createat") VALUES ('P000007','Zane Robertson','1980-04-10','Male','A+','United Kingdom',5,'Harley street 111.','222 765 766','zane.robertson@patientmail.com','oke','Active','2026-03-04');
INSERT INTO "patient" ("pid","pname","dob","gender","blood","country","cid","street","phone","email","remark","status","createat") VALUES ('P000008','Rosalie Bradley','1982-11-17','Female','O+','United Kingdom',5,'Carnaby street 67.','1234 8976','','hello','Active','2026-03-04');
INSERT INTO "patient" ("pid","pname","dob","gender","blood","country","cid","street","phone","email","remark","status","createat") VALUES ('P000009','Layla Byrd','1998-03-25','Female','AB-','United Kingdom',5,'Regen street 555.','2233 5544','layla.byrd@patientmail.com','','Active','2026-03-04');
INSERT INTO "patient" ("pid","pname","dob","gender","blood","country","cid","street","phone","email","remark","status","createat") VALUES ('P000010','Adan White','2000-06-15','Male','B-','United Kingdom',5,'Fleet street 66.','2345  6654','adan.white@patientmail.com','','Active','2026-03-04');
INSERT INTO "patienttests" ("peid","tid","result","testdate","pid","docid","posid","comment","depid") VALUES (2,2,'excellent	','2026-03-11','P000002','D001',18,'none',1);
INSERT INTO "patienttests" ("peid","tid","result","testdate","pid","docid","posid","comment","depid") VALUES (3,2,'xxxx','2026-01-01','P000009','D001',18,'xxx',1);
INSERT INTO "patienttests" ("peid","tid","result","testdate","pid","docid","posid","comment","depid") VALUES (6,2,'oke','2026-04-10','P000002','D001',18,'oke super',1);
INSERT INTO "positions" ("posid","name","type") VALUES (1,'Rezident','d');
INSERT INTO "positions" ("posid","name","type") VALUES (3,'Intern','d');
INSERT INTO "positions" ("posid","name","type") VALUES (4,'Registrar','d');
INSERT INTO "positions" ("posid","name","type") VALUES (5,'Senior consultant','d');
INSERT INTO "positions" ("posid","name","type") VALUES (6,'Medical student','d');
INSERT INTO "positions" ("posid","name","type") VALUES (7,'Medical director','d');
INSERT INTO "positions" ("posid","name","type") VALUES (8,'Attending physician','d');
INSERT INTO "positions" ("posid","name","type") VALUES (9,'Hospitalist','d');
INSERT INTO "positions" ("posid","name","type") VALUES (10,'Primary care doctor','d');
INSERT INTO "positions" ("posid","name","type") VALUES (11,'Allergist','d');
INSERT INTO "positions" ("posid","name","type") VALUES (12,'Paediatrician','d');
INSERT INTO "positions" ("posid","name","type") VALUES (13,'Geriatric medicine doctor','d');
INSERT INTO "positions" ("posid","name","type") VALUES (14,'Dermatologist','d');
INSERT INTO "positions" ("posid","name","type") VALUES (15,'Infectious disease doctor','d');
INSERT INTO "positions" ("posid","name","type") VALUES (16,'Ophthalmologist','d');
INSERT INTO "positions" ("posid","name","type") VALUES (17,'Obstetricians and gynaecologist','d');
INSERT INTO "positions" ("posid","name","type") VALUES (18,'Cardiologist','d');
INSERT INTO "positions" ("posid","name","type") VALUES (19,'Endocrinologist','d');
INSERT INTO "positions" ("posid","name","type") VALUES (20,'Gastroenterologist','d');
INSERT INTO "positions" ("posid","name","type") VALUES (21,'Nephrologist','d');
INSERT INTO "positions" ("posid","name","type") VALUES (22,'Nephrologist','d');
INSERT INTO "positions" ("posid","name","type") VALUES (23,'Pulmonologist','d');
INSERT INTO "positions" ("posid","name","type") VALUES (24,'Licensed Practical Nurse','n');
INSERT INTO "positions" ("posid","name","type") VALUES (25,'Registered Nurse','n');
INSERT INTO "positions" ("posid","name","type") VALUES (26,'Bedside Nurse','n');
INSERT INTO "positions" ("posid","name","type") VALUES (27,'Staff nurse','n');
INSERT INTO "positions" ("posid","name","type") VALUES (28,'Charge Nurse','n');
INSERT INTO "positions" ("posid","name","type") VALUES (29,'Shift Manager','n');
INSERT INTO "positions" ("posid","name","type") VALUES (30,'Nurse Manager','n');
INSERT INTO "positions" ("posid","name","type") VALUES (31,'Director of Nursing','n');
INSERT INTO "positions" ("posid","name","type") VALUES (32,'Chief Nursing Officer','n');
INSERT INTO "positions" ("posid","name","type") VALUES (33,'Advanced Practice Registered Nurses','n');
INSERT INTO "positions" ("posid","name","type") VALUES (34,'Nurse Anesthetist','n');
INSERT INTO "positions" ("posid","name","type") VALUES (35,'Nurse Practitioner','n');
INSERT INTO "positions" ("posid","name","type") VALUES (36,'Clinical Nurse Specialist','n');
INSERT INTO "positions" ("posid","name","type") VALUES (37,'Nurse Midwife','n');
INSERT INTO "positions" ("posid","name","type") VALUES (38,'Family Nurse Practitioner','n');
INSERT INTO "positions" ("posid","name","type") VALUES (39,'Travel Nurse','n');
INSERT INTO "positions" ("posid","name","type") VALUES (40,'Nurse Care Manager','n');
INSERT INTO "positions" ("posid","name","type") VALUES (41,'Intensive Care Unit Registered Nurse','n');
INSERT INTO "positions" ("posid","name","type") VALUES (42,'Neonatal Intensive Care Registered Nurse','n');
INSERT INTO "positions" ("posid","name","type") VALUES (43,'Operating Room Nurse','n');
INSERT INTO "positions" ("posid","name","type") VALUES (44,'Medical-Surgical Registered Nurse','n');
INSERT INTO "positions" ("posid","name","type") VALUES (45,'Oncology Registered Nurse','n');
INSERT INTO "positions" ("posid","name","type") VALUES (46,'Dialysis Registered Nurse','n');
INSERT INTO "positions" ("posid","name","type") VALUES (47,'Home Care Registered Nurse','n');
INSERT INTO "positions" ("posid","name","type") VALUES (48,'Driver','s');
INSERT INTO "positions" ("posid","name","type") VALUES (49,'Database administrator','s');
INSERT INTO "positions" ("posid","name","type") VALUES (50,'Clinical systems analyst','s');
INSERT INTO "positions" ("posid","name","type") VALUES (51,'Surgical techniciant','s');
INSERT INTO "positions" ("posid","name","type") VALUES (52,'Occupational therapist','s');
INSERT INTO "positions" ("posid","name","type") VALUES (53,'Xray-techniciant','s');
INSERT INTO "positions" ("posid","name","type") VALUES (54,'Patient services representative','s');
INSERT INTO "positions" ("posid","name","type") VALUES (55,'Maintenance person','s');
INSERT INTO "positions" ("posid","name","type") VALUES (56,'Medical equipment techniciant','s');
INSERT INTO "positions" ("posid","name","type") VALUES (57,'Security guard','s');
INSERT INTO "positions" ("posid","name","type") VALUES (58,'Social worker','s');
INSERT INTO "positions" ("posid","name","type") VALUES (59,'Laboratory techniciant','s');
INSERT INTO "positions" ("posid","name","type") VALUES (60,'Hospital administrator','s');
INSERT INTO "positions" ("posid","name","type") VALUES (61,'IT specialist','s');
INSERT INTO "positions" ("posid","name","type") VALUES (62,'Healt service manager','s');
INSERT INTO "positions" ("posid","name","type") VALUES (63,'Pharmacist','s');
INSERT INTO "positions" ("posid","name","type") VALUES (64,'Medical receptionist','s');
INSERT INTO "positions" ("posid","name","type") VALUES (65,'Medical techniciant','s');
INSERT INTO "positions" ("posid","name","type") VALUES (66,'Houskeeping worker','s');
INSERT INTO "positions" ("posid","name","type") VALUES (67,'Security personel','s');
INSERT INTO "positions" ("posid","name","type") VALUES (68,'Coding specialist','s');
INSERT INTO "positions" ("posid","name","type") VALUES (69,'Radiologist','d');
INSERT INTO "positions" ("posid","name","type") VALUES (70,'Medical record clerk','s');
INSERT INTO "positions" ("posid","name","type") VALUES (71,'Clinical assistant','s');
INSERT INTO "positions" ("posid","name","type") VALUES (72,'Porter','s');
INSERT INTO "positions" ("posid","name","type") VALUES (73,'Human resources manager','s');
INSERT INTO "positions" ("posid","name","type") VALUES (74,'Ward clerk','s');
INSERT INTO "positions" ("posid","name","type") VALUES (75,'Nutrition Assistant','s');
INSERT INTO "positions" ("posid","name","type") VALUES (76,'Neurologist','d');
INSERT INTO "positions" ("posid","name","type") VALUES (77,'Orthopedic','d');
INSERT INTO "positions" ("posid","name","type") VALUES (78,'Oncologist','d');
INSERT INTO "positions" ("posid","name","type") VALUES (79,'Psychiatrist','d');
INSERT INTO "positions" ("posid","name","type") VALUES (80,'General surgery','d');
INSERT INTO "positions" ("posid","name","type") VALUES (81,'Cleaner','s');
INSERT INTO "positions" ("posid","name","type") VALUES (82,'Secretary','s');
INSERT INTO "relative" ("rid","rname","rtype","rphone","rpid") VALUES (8,'Richard Gere','Father','20 36475664','P000001');
INSERT INTO "relative" ("rid","rname","rtype","rphone","rpid") VALUES (9,'Margaret Match','Mother','2422 33344','P000002');
INSERT INTO "relative" ("rid","rname","rtype","rphone","rpid") VALUES (10,'Mary stone','Mother','21 111 1111 ','P000003');
INSERT INTO "relative" ("rid","rname","rtype","rphone","rpid") VALUES (11,'Herry Kissinger','Father','20 3444 56','P000004');
INSERT INTO "relative" ("rid","rname","rtype","rphone","rpid") VALUES (12,'Mary Higgins','Mother','233 222 55','P000005');
INSERT INTO "relative" ("rid","rname","rtype","rphone","rpid") VALUES (13,'Nancy breton','Mother','3433 4456','P000006');
INSERT INTO "relative" ("rid","rname","rtype","rphone","rpid") VALUES (14,'Charles Bronson','Father','2224 3444','P000007');
INSERT INTO "relative" ("rid","rname","rtype","rphone","rpid") VALUES (15,'sherlock holmes','Father','23498786','P000008');
INSERT INTO "relative" ("rid","rname","rtype","rphone","rpid") VALUES (16,'Harrison Ford','Father','234787867','P000009');
INSERT INTO "relative" ("rid","rname","rtype","rphone","rpid") VALUES (17,'Barbara Streisand','Mother','34556789','P000010');
INSERT INTO "rooms" ("roomid","roomname","capacity","occupied","roomtype","depid","status") VALUES (5,'A1',3,1,'General',18,'Empty slots');
INSERT INTO "rooms" ("roomid","roomname","capacity","occupied","roomtype","depid","status") VALUES (6,'A2',3,0,'General',18,'');
INSERT INTO "rooms" ("roomid","roomname","capacity","occupied","roomtype","depid","status") VALUES (7,'A3',2,1,'General',18,'Empty');
INSERT INTO "rooms" ("roomid","roomname","capacity","occupied","roomtype","depid","status") VALUES (8,'A4',1,0,'General',1,'Empty');
INSERT INTO "specializations" ("sid","name","type") VALUES (1,'Pediatrics','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (2,'Cardiology','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (3,'Orthopedics','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (4,'Neonatology','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (5,'Oncology','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (6,'Emergency Care','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (7,'General Surgery','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (8,'Internal Medicine','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (9,'Neurology','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (10,'Dermatology','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (11,'ENT Specialist','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (12,'Cleaner','s');
INSERT INTO "specializations" ("sid","name","type") VALUES (13,'Security Guard','s');
INSERT INTO "specializations" ("sid","name","type") VALUES (14,'Janitor','s');
INSERT INTO "specializations" ("sid","name","type") VALUES (15,'Cook','s');
INSERT INTO "specializations" ("sid","name","type") VALUES (16,'Radiologist','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (18,'Driver','s');
INSERT INTO "specializations" ("sid","name","type") VALUES (19,'Accident and emergency medicine','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (20,'Allergology','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (21,'Anaesthetics','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (22,'Biological hematology','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (24,'Child psychiatry','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (25,'Clinical biology','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (26,'Clinical chemistry','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (27,'Clinical neurophysiology','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (28,'Clinical radiology','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (29,'Dental, oral and maxillo-facial surgery','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (30,'Dermato-venerology','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (32,'Endocrinology','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (33,'Gastro-enterologic surgery','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (34,'Gastroenterology','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (35,'General hematology','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (36,'General Practice','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (38,'Geriatrics','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (39,'Immunology','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (40,'Infectious diseases','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (42,'Laboratory medicine','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (43,'Maxillo-facial surgery','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (44,'Microbiology','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (45,'Nephrology','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (46,'Neuro-psychiatry','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (48,'Neurosurgery','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (49,'Nuclear medicine','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (50,'Obstetrics and gynecology','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (51,'Occupational medicine','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (52,'Ophthalmology','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (53,'Orthopaedics','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (54,'Otorhinolaryngology','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (55,'Paediatric surgery','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (57,'Pathology','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (58,'Pharmacology','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (59,'Physical medicine and rehabilitation','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (60,'Plastic surgery','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (61,'Podiatric Medicine','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (62,'Podiatric Surgery','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (63,'Psychiatry','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (64,'Public health and Preventive Medicine','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (65,'Radiology','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (66,'Radiotherapy','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (67,'Respiratory medicine','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (68,'Rheumatology','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (69,'Stomatology','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (70,'Thoracic surgery','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (71,'Tropical medicine','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (72,'Urology','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (73,'Vascular surgery','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (74,'Venereology','d');
INSERT INTO "specializations" ("sid","name","type") VALUES (75,'Pediatrics','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (76,'Cardiology','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (77,'Orthopedics','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (78,'Neonatology','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (79,'Oncology','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (80,'Emergency Care','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (81,'General Surgery','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (82,'Internal Medicine','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (83,'Neurology','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (84,'Dermatology','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (85,'ENT Specialist','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (90,'Radiologist','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (91,'Receptionist','s');
INSERT INTO "specializations" ("sid","name","type") VALUES (93,'Accident and emergency medicine','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (94,'Allergology','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (95,'Anaesthetics','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (96,'Biological hematology','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (97,'Child psychiatry','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (98,'Clinical biology','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (99,'Clinical chemistry','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (100,'Clinical neurophysiology','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (101,'Clinical radiology','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (102,'Dental, oral and maxillo-facial surgery','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (103,'Dermato-venerology','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (104,'Endocrinology','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (105,'Gastro-enterologic surgery','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (106,'Gastroenterology','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (107,'General hematology','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (108,'General Practice','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (109,'Geriatrics','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (110,'Immunology','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (111,'Infectious diseases','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (112,'Laboratory medicine','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (113,'Maxillo-facial surgery','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (114,'Microbiology','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (115,'Nephrology','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (116,'Neuro-psychiatry','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (117,'Neurosurgery','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (118,'Nuclear medicine','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (119,'Obstetrics and gynecology','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (120,'Occupational medicine','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (121,'Ophthalmology','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (122,'Orthopaedics','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (123,'Otorhinolaryngology','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (124,'Paediatric surgery','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (125,'Pathology','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (126,'Pharmacology','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (127,'Physical medicine and rehabilitation','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (128,'Plastic surgery','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (129,'Podiatric Medicine','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (130,'Podiatric Surgery','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (131,'Psychiatry','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (132,'Public health and Preventive Medicine','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (133,'Radiology','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (134,'Radiotherapy','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (135,'Respiratory medicine','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (136,'Rheumatology','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (137,'Stomatology','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (138,'Thoracic surgery','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (139,'Tropical medicine','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (140,'Urology','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (141,'Vascular surgery','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (142,'Venereology','n');
INSERT INTO "specializations" ("sid","name","type") VALUES (145,'Porter','s');
INSERT INTO "specializations" ("sid","name","type") VALUES (146,'Assistant','s');
INSERT INTO "specializations" ("sid","name","type") VALUES (147,'Clerk','s');
INSERT INTO "specializations" ("sid","name","type") VALUES (148,'IT specialist','s');
INSERT INTO "specializations" ("sid","name","type") VALUES (149,'Xray-techniciant ','s');
INSERT INTO "specializations" ("sid","name","type") VALUES (150,'Technician','s');
INSERT INTO "specializations" ("sid","name","type") VALUES (151,'Pharmacist','s');
INSERT INTO "specializations" ("sid","name","type") VALUES (152,'Admin','s');
INSERT INTO "specializations" ("sid","name","type") VALUES (153,'Worker','s');
INSERT INTO "specializations" ("sid","name","type") VALUES (154,'Secretary','s');
INSERT INTO "test" ("tid","tname","price") VALUES (1,'X-RAY',100);
INSERT INTO "test" ("tid","tname","price") VALUES (2,'BLOOD TEST',300);
INSERT INTO "test" ("tid","tname","price") VALUES (3,'URINE TEST',100);
INSERT INTO "test" ("tid","tname","price") VALUES (4,'MRI SCAN',1200);
INSERT INTO "test" ("tid","tname","price") VALUES (5,'ENDOSCOPY',3000);
INSERT INTO "test" ("tid","tname","price") VALUES (6,'BIOPSY',3500);
INSERT INTO "test" ("tid","tname","price") VALUES (7,'ULTRASOUND',900);
INSERT INTO "test" ("tid","tname","price") VALUES (8,'CT-SCAN',1100);
INSERT INTO "test" ("tid","tname","price") VALUES (9,'CBC',350);
INSERT INTO "test" ("tid","tname","price") VALUES (10,'FLU TEST',1500);
INSERT INTO "visit_records" ("vid","vdate","pid","docid","posid","symptoms","disease","diagnosis","next_vdate","adid","depid") VALUES (1,'2026-01-01','P000009','D001',18,'sy','di','diag','','8',1);
COMMIT;
